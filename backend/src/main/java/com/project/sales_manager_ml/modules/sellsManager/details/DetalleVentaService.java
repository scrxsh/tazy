package com.project.sales_manager_ml.modules.sellsManager.details;

import com.project.sales_manager_ml.modules.inventoryManager.ProductoModel;
import com.project.sales_manager_ml.modules.inventoryManager.ProductoRepository;
import com.project.sales_manager_ml.modules.sellsManager.VentaModel;
import com.project.sales_manager_ml.modules.sellsManager.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DetalleVentaService {
    @Autowired
    DetalleVentaRepository detalleVentaRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    VentaRepository ventaRepository;

    public DetalleVentaModel crearDetalleVenta(DetalleVentaModel detalle_venta, Long id_producto){
        ProductoModel producto = productoRepository.findById(id_producto).orElse(null);
        detalle_venta.setProducto(producto);

        detalle_venta.setPrecioUnitario(Objects.requireNonNull(producto).getPrecioFinal());
        detalle_venta.setSubtotal(detalle_venta.getCantidad() * detalle_venta.getPrecioUnitario());

        producto.setStock(producto.getStock() - detalle_venta.getCantidad());
        productoRepository.save(producto);

        return detalleVentaRepository.save(detalle_venta);
    }

    public List<DetalleVentaModel> crearDetallesVenta(VentaModel venta, List<Map<String, Object>> detalles ){
        List<DetalleVentaModel> detallesVentas = new ArrayList<>();

        Float totalVenta = 0.0f;
        Float totalIva = 0.0f;

        for(Map<String, Object> detalleData : detalles){
            DetalleVentaModel detalle = new DetalleVentaModel();
            detalle.setCantidad((Integer) detalleData.get("cantidad"));
            detalle.setVenta(venta);
            Long idProducto = Long.valueOf(detalleData.get("id_producto").toString());

            DetalleVentaModel detalleCreado = crearDetalleVenta(detalle, idProducto);
            totalVenta += detalleCreado.getSubtotal();
            totalIva += detalleCreado.getSubtotal() * detalleCreado.getProducto().getPIva();

            detallesVentas.add(detalleCreado);
        }

        venta.setTotalPagado(totalVenta);
        venta.setTotalIva(totalIva);
        ventaRepository.save(venta);

        return detallesVentas;
    }

    public List<DetalleVentaModel> obtenerDetallesPorId(Long id_venta){
        return detalleVentaRepository.findByVentaId(id_venta);
    }
}
