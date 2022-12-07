package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.ClienteRecompensaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClienteRecompensa} and its DTO {@link ClienteRecompensaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, RecompensaMapper.class})
public interface ClienteRecompensaMapper extends EntityMapper<ClienteRecompensaDTO, ClienteRecompensa> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "recompensa.id", target = "recompensaId")
    ClienteRecompensaDTO toDto(ClienteRecompensa clienteRecompensa);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "recompensaId", target = "recompensa")
    ClienteRecompensa toEntity(ClienteRecompensaDTO clienteRecompensaDTO);

    default ClienteRecompensa fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClienteRecompensa clienteRecompensa = new ClienteRecompensa();
        clienteRecompensa.setId(id);
        return clienteRecompensa;
    }
}
