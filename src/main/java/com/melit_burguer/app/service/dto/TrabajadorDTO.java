package com.melit_burguer.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.melit_burguer.app.domain.Trabajador} entity.
 */
public class TrabajadorDTO implements Serializable {

    private Long id;

    private String nombre;

    private String apellido;

    private String dni;

    private Integer telefono;

    private String correo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrabajadorDTO trabajadorDTO = (TrabajadorDTO) o;
        if (trabajadorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trabajadorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrabajadorDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", dni='" + getDni() + "'" +
            ", telefono=" + getTelefono() +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
