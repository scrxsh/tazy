package com.project.sales_manager_ml.modules.sellsManager.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaModel, Long> {
    List<DetalleVentaModel> findByVentaId(Long ventaId);

}
