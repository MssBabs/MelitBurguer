package com.melit_burguer.app.repository;

import com.melit_burguer.app.domain.EstadoPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstadoPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Long> {

}
