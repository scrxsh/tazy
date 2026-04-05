package com.project.sales_manager_ml.modules.sellsManager.details;

import com.project.sales_manager_ml.modules.inventoryManager.ProductoModel;
import com.project.sales_manager_ml.modules.sellsManager.VentaModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_venta")

public class DetalleVentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false, name = "precio_unitario")
    private Float precioUnitario;
    @Column(nullable = false)
    private Float subtotal;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    ProductoModel producto;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    @JsonBackReference
    VentaModel venta;

}
