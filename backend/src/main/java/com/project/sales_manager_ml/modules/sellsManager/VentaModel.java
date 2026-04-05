package com.project.sales_manager_ml.modules.sellsManager;

import com.project.sales_manager_ml.modules.customerManager.ClienteModel;
import com.project.sales_manager_ml.modules.employesManager.EmpleadoModel;
import com.project.sales_manager_ml.modules.sellsManager.details.DetalleVentaModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class VentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, name = "fecha_venta")
    private Date fechaVenta = new Date();
    @Column (nullable = false, name = "total_iva")
    private Float totalIva = 0.0f;
    @Column (nullable = false, name = "total_pagado")
    private Float totalPagado = 0.0f;


    @JsonManagedReference
    @OneToMany(mappedBy = "venta")
    List<DetalleVentaModel> detalleVenta;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    ClienteModel cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    EmpleadoModel empleado;



}
