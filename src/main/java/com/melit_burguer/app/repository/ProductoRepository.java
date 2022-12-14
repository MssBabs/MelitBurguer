package com.melit_burguer.app.repository;

import com.melit_burguer.app.domain.Producto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Get Producto by Types.
     *
     * @param pageable
     * @return getTypes
     */
    @Query("SELECT p FROM Producto p WHERE p.tipoProducto.id like :tipoProductoId")
    Page<Producto> getProductosByType(@Param("tipoProductoId")long tipoProductoId, Pageable pageable);
}
