package com.project.sales_manager_ml.predictionModules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/prediccion")
@CrossOrigin("*")

public class PrediccionController {
    @Autowired
    PrediccionService prediccionService;

    @PostMapping("/entrenar")
    public ResponseEntity<String> entrenarModelo() {
        prediccionService.inicializarModelo();
        prediccionService.entrenarModelo();
        return ResponseEntity.ok("ENTRENADO");
    }

    @GetMapping("/valor_venta")
    public ResponseEntity<?> predecirVenta(@RequestParam double precio){
        try {
            double prediccionRealizada = prediccionService.predecirVenta(precio);
            return ResponseEntity.ok(prediccionRealizada);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
