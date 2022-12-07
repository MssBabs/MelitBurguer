package com.melit_burguer.app.repository;

import com.melit_burguer.app.domain.ProductosPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductosPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductosPedidoRepository extends JpaRepository<ProductosPedido, Long> {

}
