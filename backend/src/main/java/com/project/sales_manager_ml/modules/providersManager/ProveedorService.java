package com.project.sales_manager_ml.modules.providersManager;

import com.project.sales_manager_ml.modules.inventoryManager.ProductoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public List<ProveedorModel> obtenerTodosProveedores() {
        return proveedorRepository.findAll();
    }
    public List<ProductoModel> obtenerProductosPorProveedor(Long id_proovedor){
        ProveedorModel proveedor = proveedorRepository.findById(id_proovedor).orElse(null);
        return Objects.requireNonNull(proveedor).getProductsList();
    }
    public ProveedorModel crearProveedores(ProveedorModel proveedores){
        return proveedorRepository.save(proveedores);
    }

    public ProveedorModel actualizarProveedores(ProveedorModel proveedores){
        if (proveedores.getId() != null){
            return proveedorRepository.save(proveedores);
        }return null;
    }

    public void borrarProveedores(Long id){
        proveedorRepository.deleteById(id);
    }

}
