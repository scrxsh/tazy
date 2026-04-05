package com.project.sales_manager_ml.modules.inventoryManager;

import com.project.sales_manager_ml.modules.providersManager.ProveedorRepository;
import com.project.sales_manager_ml.modules.sellsManager.details.DetalleVentaModel;
import com.project.sales_manager_ml.uploadFiles.CargaArchivosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    CargaArchivosService cargaArchivosService;


    String url = "http://localhost:8080/uploads/images/";

    public List<ProductoModel> obtenerTodosProductos() {
        List<ProductoModel> productos = productoRepository.findAll();
        productos = productos.stream().peek(producto -> producto.setImgPrenda(url+producto.getImgPrenda())).collect(Collectors.toList());
        return productos;
    }

    public ProductoModel obtenerProductoPorId (Long id_producto){
        Optional<ProductoModel> producto = productoRepository.findById(id_producto);
        ProductoModel productoImg = producto.get();
        productoImg.setImgPrenda(url+productoImg.getImgPrenda());
        return producto.orElse(null);
    }
    public List<DetalleVentaModel> obtenerProductoVendido(Long id_producto){
        ProductoModel producto = productoRepository.findById(id_producto).orElse(null);
        return Objects.requireNonNull(producto).getDetalleVentas();
    }

    public ProductoModel crearProducto(ProductoModel producto, MultipartFile file, Long id_proveedor) throws IOException {
        producto.setProveedor(proveedorRepository.findById(id_proveedor).orElse(null));
        comprobacionImagen(file);
        String urlImg = cargaArchivosService.guardarArchivo(file, producto.getNombre());
        producto.setImgPrenda(urlImg);
        return productoRepository.save(producto);
    }


    public ProductoModel actualizarProducto(ProductoModel producto, MultipartFile file, Long id_proveedor) throws IOException{
        if (producto.getId() != null){
            producto.setProveedor(proveedorRepository.findById(id_proveedor).orElse(null));
            comprobacionImagen(file);
            String urlImg = cargaArchivosService.guardarArchivo(file, producto.getNombre());
            producto.setImgPrenda(urlImg);
            return productoRepository.save(producto);
        }
        return null;
    }

    public void borrarProductos(Long id){
        ProductoModel producto = productoRepository.findById(id).get();
        String imgPrenda = producto.getImgPrenda();
        cargaArchivosService.eliminarArchivo(imgPrenda);
        productoRepository.delete(producto);
    }

    public void comprobacionImagen (MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new IllegalArgumentException("Archivo vacio");
        }
    }
}
