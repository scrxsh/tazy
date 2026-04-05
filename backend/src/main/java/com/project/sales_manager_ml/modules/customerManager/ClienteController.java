package com.project.sales_manager_ml.modules.customerManager;

import com.project.sales_manager_ml.modules.sellsManager.VentaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@CrossOrigin("*")
public class ClienteController {

@Autowired
ClienteService clienteService;

    @GetMapping("/todos")
    public List<ClienteModel> obtenerTodo(){
        return clienteService.obtenerTodosClientes();
    }

    @GetMapping("/info/{id}")
    public  ClienteModel obtenerPorId(Long id){
        return  clienteService.obtenerPorId(id);
    }

    @GetMapping("{id_cliente}/compras")
    public ResponseEntity<List<VentaModel>> obtenerListaVentasProducto(@PathVariable Long id_cliente){
        List<VentaModel> compras = clienteService.obtenerComprasRealizadas(id_cliente);
        return ResponseEntity.ok(compras);
    }

    @PostMapping("/crear")
    public ClienteModel crear(@RequestBody ClienteModel clientes){
        return clienteService.crearClientes(clientes);
    }

    @PutMapping("/actualizar")
    public ClienteModel actualizar(@RequestBody ClienteModel clientes){
        return clienteService.actualizarClientes(clientes);
    }

    @DeleteMapping("/borrar/{id}")
    public void borrar(@PathVariable Long id){
        clienteService.borrarClientes(id);
    }


}
