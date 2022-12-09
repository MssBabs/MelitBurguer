package com.melit_burguer.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.melit_burguer.app.domain.Pedido} entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    private Double precioFinal;

    //private Double productosPedidosPrecioTotal;

    private LocalDate fecha;

    private Long estadoPedidoId;
    private String estadoPedidoNombre;

    private Long clienteId;
    private String clienteNombre;
    private String clienteApellido;

    private Long trabajadorId;
    private String trabajadorNombre;
    private String trabajadorApellido;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getEstadoPedidoId() {
        return estadoPedidoId;
    }

    public void setEstadoPedidoId(Long estadoPedidoId) {
        this.estadoPedidoId = estadoPedidoId;
    }

    public String getEstadoPedidoNombre() {
        return estadoPedidoNombre;
    }

    public void setEstadoPedidoNombre(String estadoPedidoNombre) {
        this.estadoPedidoNombre = estadoPedidoNombre;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteApellido() {
        return clienteApellido;
    }

    public void setClienteApellido(String clienteApellido) {
        this.clienteApellido = clienteApellido;
    }

    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public String getTrabajadorNombre() {
        return trabajadorNombre;
    }

    public void setTrabajadorNombre(String trabajadorNombre) {
        this.trabajadorNombre = trabajadorNombre;
    }

    public String getTrabajadorApellido() {
        return trabajadorApellido;
    }

    public void setTrabajadorApellido(String trabajadorApellido) {
        this.trabajadorApellido = trabajadorApellido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", precioFinal=" + getPrecioFinal() +
            ", fecha='" + getFecha() + "'" +
            ", estadoPedido=" + getEstadoPedidoId() +
            ", cliente=" + getClienteId() +
            ", trabajador=" + getTrabajadorId() +
            "}";
    }
}
