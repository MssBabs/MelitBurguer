package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.RecompensaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recompensa} and its DTO {@link RecompensaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecompensaMapper extends EntityMapper<RecompensaDTO, Recompensa> {


    @Mapping(target = "clienteRecompensa", ignore = true)
    Recompensa toEntity(RecompensaDTO recompensaDTO);

    default Recompensa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recompensa recompensa = new Recompensa();
        recompensa.setId(id);
        return recompensa;
    }
}
