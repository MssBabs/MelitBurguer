package com.melit_burguer.app.repository;

import com.melit_burguer.app.domain.ClienteRecompensa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClienteRecompensa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRecompensaRepository extends JpaRepository<ClienteRecompensa, Long> {

}
