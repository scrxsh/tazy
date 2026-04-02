package com.project.sales_manager_ml.predictionModules;

import com.project.sales_manager_ml.modules.sellsManager.VentaModel;
import com.project.sales_manager_ml.modules.sellsManager.VentaRepository;
import com.project.sales_manager_ml.modules.sellsManager.details.DetalleVentaModel;
import com.project.sales_manager_ml.modules.sellsManager.details.DetalleVentaRepository;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaDataService {

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    DetalleVentaRepository detalleVentaRepository;


    public INDArray cargarVentas(){
        List<VentaModel> ventas = ventaRepository.findAll();

        if (ventas.isEmpty()){
            throw new IllegalStateException("No hay ninguna venta registrada");
        }

        List<double[]> filas = new ArrayList<>();
        for(VentaModel venta: ventas){
            List<DetalleVentaModel> detalles = detalleVentaRepository.findByVentaId(venta.getId());
            for (DetalleVentaModel detalle: detalles){
                filas.add(new double[]{venta.getTotalPagado(), detalle.getCantidad()});
            }
        }

        if(filas.isEmpty()){
            throw new IllegalStateException("No hay datos disponibles en detalles");
        }

        double[][] datos = filas.toArray(new double[0][]);

        return Nd4j.create(datos);
    }
}
