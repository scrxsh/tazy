package com.project.sales_manager_ml.modules.sellsManager;

import com.project.sales_manager_ml.modules.customerManager.ClienteRepository;
import com.project.sales_manager_ml.modules.employesManager.EmpleadoRepository;
import com.project.sales_manager_ml.modules.sellsManager.details.DetalleVentaModel;
import com.project.sales_manager_ml.modules.sellsManager.details.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    DetalleVentaService detalleVentaService;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<VentaModel> obtenerTodosVentas() {
        return ventaRepository.findAll();
    }

    public VentaModel obtenerVentaPorId(Long id){
        return ventaRepository.findById(id).orElse(null);
    }

    public VentaModel crearVentas(VentaModel venta, List<Map<String, Object>> detallesVenta, Long id_cliente, Long id_empleado){

        venta.setCliente(clienteRepository.findById(id_cliente).orElse(null));
        venta.setEmpleado(empleadoRepository.findById(id_empleado).orElse(null));

        VentaModel ventaGuardada = ventaRepository.save(venta);

        List<DetalleVentaModel> detallesCreados = detalleVentaService.crearDetallesVenta(ventaGuardada, detallesVenta);
        ventaGuardada.setDetalleVenta(detallesCreados);
        return ventaRepository.findById(ventaGuardada.getId()).orElse(null);
    }


}
