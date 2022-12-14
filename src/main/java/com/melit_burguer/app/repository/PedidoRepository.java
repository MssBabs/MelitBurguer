package com.melit_burguer.app.repository;

import com.melit_burguer.app.domain.Pedido;
import com.melit_burguer.app.domain.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    //////cambiarlo a pedido
    //Obtener datos del producto para select del modal
    @Query("SELECT p FROM Producto p")
    Page<Producto> findAllProductos(Pageable pageable);


}
