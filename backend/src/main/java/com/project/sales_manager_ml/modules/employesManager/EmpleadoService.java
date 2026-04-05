package com.project.sales_manager_ml.modules.employesManager;

import com.project.sales_manager_ml.modules.sellsManager.VentaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<EmpleadoModel> obtenerTodosEmpleados() {
        return empleadoRepository.findAll();
    }

    public List<VentaModel> obtenerVentasRealizadas(Long id_empleado){
        EmpleadoModel empleado = empleadoRepository.findById(id_empleado).orElse(null);
        return Objects.requireNonNull(empleado).getVentasRealizadas();
    }

    public EmpleadoModel crearEmpleados(EmpleadoModel empleados){
        return empleadoRepository.save(empleados);
    }

    public EmpleadoModel actualizarEmpleados(EmpleadoModel empleados){
        if (empleados.getId() != null){
            return empleadoRepository.save(empleados);
        }return null;
    }

    public void borrarEmpleados(Long id){
        empleadoRepository.deleteById(id);
    }

}
