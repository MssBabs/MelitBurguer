package com.melit_burguer.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.melit_burguer.app.domain.ClienteRecompensa} entity.
 */
public class ClienteRecompensaDTO implements Serializable {

    private Long id;

    private LocalDate fecha;


    private Long clienteId;

    private Long recompensaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getRecompensaId() {
        return recompensaId;
    }

    public void setRecompensaId(Long recompensaId) {
        this.recompensaId = recompensaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteRecompensaDTO clienteRecompensaDTO = (ClienteRecompensaDTO) o;
        if (clienteRecompensaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteRecompensaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteRecompensaDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", cliente=" + getClienteId() +
            ", recompensa=" + getRecompensaId() +
            "}";
    }
}
