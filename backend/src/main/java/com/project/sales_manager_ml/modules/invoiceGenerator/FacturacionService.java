package com.project.sales_manager_ml.modules.invoiceGenerator;

import com.project.sales_manager_ml.modules.sellsManager.VentaModel;
import com.project.sales_manager_ml.modules.sellsManager.VentaRepository;
import com.project.sales_manager_ml.modules.sellsManager.details.DetalleVentaModel;
import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacturacionService {
    @Autowired
    VentaRepository ventaRepository;


    public byte[] exportarFacturaVenta(Long id) throws JRException {
        try {

            VentaModel venta = ventaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Venta no encontrada con ID: " + id));

            List<Map<String, Object>> detallesReporte = new ArrayList<>();

            for (DetalleVentaModel detalle: venta.getDetalleVenta()){
                Map<String, Object> item = new HashMap<>();
                Map<String, Object> producto = new HashMap<>();
                producto.put("nombre", detalle.getProducto().getNombre());
                producto.put("precio", detalle.getProducto().getPrecio());
                producto.put("PIva", detalle.getProducto().getPIva());

                Float subtotalSinIva = detalle.getProducto().getPrecio() * detalle.getCantidad();
                Float valorIva = detalle.getProducto().getPrecio() * detalle.getProducto().getPIva();
                Float valorIvaInicial = valorIva * detalle.getCantidad();
                Float subtotal = detalle.getProducto().getPrecioFinal() * detalle.getCantidad();

                item.put("producto", producto);
                item.put("unidades", detalle.getCantidad());

                item.put("subtotalSinIva", subtotalSinIva);
                item.put("valorIva", valorIvaInicial);
                item.put("subtotal", subtotal);

                detallesReporte.add(item);
            }

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detallesReporte);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ds", ds);

            List<Map<String,Object>> ventas = new ArrayList<>();
            Map<String, Object> ventaMap = new HashMap<>();

            Map<String, Object> cliente = new HashMap<>();
            cliente.put("nombre", venta.getCliente().getNombre());
            cliente.put("NDocumento", venta.getCliente().getNDocumento());
            cliente.put("telefono", venta.getCliente().getTelefono());
            cliente.put("email", venta.getCliente().getEmail());

            Map<String, Object> empleado = new HashMap<>();
            empleado.put("nombre", venta.getEmpleado().getNombre());


            ventaMap.put("id", venta.getId());
            ventaMap.put("cliente", cliente);
            ventaMap.put("empleado", empleado);
            ventaMap.put("fechaVenta", venta.getFechaVenta());
            ventaMap.put("valorTotalIva", venta.getTotalIva());
            ventaMap.put("totalPagado", venta.getTotalPagado());

            ventas.add(ventaMap);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ventas);

            InputStream reportStream = getClass().getResourceAsStream("/reports/factura_venta.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new JRException("Error al generar el reporte de la venta: " + e.getMessage());
        }
    }
}
