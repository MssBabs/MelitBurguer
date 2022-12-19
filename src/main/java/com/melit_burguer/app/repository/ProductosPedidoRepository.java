package com.melit_burguer.app.repository;

import com.melit_burguer.app.domain.ProductosPedido;
import com.melit_burguer.app.service.dto.ProductosPedidoDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductosPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductosPedidoRepository extends JpaRepository<ProductosPedido, Long> {
    /**SPRING Repository:
     * JpaRepository<ProductosPedido, Long>     -> Define la entidad(ProductosPedido)
     * ProductosPedido                          -> Objeto ProductosPedido (la tabla)
     * findAllByPedidoId()                      -> Busca todos los productospedidos sobre una lista de pedidos
     * (List<Long> pedidoIds, Pageable page)    -> parametros
     */
    Page<ProductosPedido> findAllByPedidoId(List<Long> pedidoIds, Pageable page);

}
