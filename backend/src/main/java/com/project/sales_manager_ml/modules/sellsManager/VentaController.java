package com.project.sales_manager_ml.modules.sellsManager;

import com.project.sales_manager_ml.modules.invoiceGenerator.FacturacionService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/ventas")
@CrossOrigin("*")
public class VentaController {

    @Autowired
    VentaService ventaService;
    @Autowired
    FacturacionService facturacionService;

    @GetMapping("/todos")
    public List<VentaModel> obtenerTodo(){
        return ventaService.obtenerTodosVentas();
    }

    @GetMapping("/info/{id}")
    public VentaModel obtenerPorId(@PathVariable Long id){
      return ventaService.obtenerVentaPorId(id);
    }

    @PostMapping("/crear")
    public VentaModel crear(@RequestBody Map<String, Object> datos){
        VentaModel venta = new VentaModel();

        Long idCliente = Long.valueOf(datos.get("id_cliente").toString());
        Long idEmpleado = Long.valueOf(datos.get("id_empleado").toString());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> detallesVenta = (List<Map<String, Object>>) datos.get("detalles");
        return ventaService.crearVentas(venta, detallesVenta, idCliente, idEmpleado);
    }



    @GetMapping("/factura/pdf/{id}")
    public ResponseEntity<byte[]> exportPDF(@PathVariable Long id) {
        try {
            byte[] reportContent = facturacionService.exportarFacturaVenta(id);

            LocalDateTime fechaActual = LocalDateTime.now();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "FEV" + id + "-" + fechaActual + ".pdf");

            return ResponseEntity.ok().headers(headers).body(reportContent);

        } catch (JRException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}



