package com.melit_burguer.app.repository;

import com.melit_burguer.app.domain.Recompensa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recompensa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {

}
