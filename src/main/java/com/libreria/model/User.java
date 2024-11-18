package com.libreria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // Mapeamos el nombre del campo en la base de datos
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras.")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido solo puede contener letras.")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe tener un formato válido.")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres.")
    private String email;

    @NotNull(message = "La fecha de registro no puede ser nula.")
    @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaRegistro;

    @NotBlank(message = "La clave no puede estar vacía.")
    @Size(min = 8, message = "La clave debe tener al menos 8 caracteres.")
    private String clave;

    @NotBlank(message = "El estado no puede estar vacío.")
    @Pattern(regexp = "^(activo|incapacitado|despedido|renuncio)$", message = "El estado debe ser uno de los siguientes: activo, incapacitado, despedido, renuncio.")
    private String estado;

    @NotNull(message = "El acceso al sistema no puede estar vacío.")
    @Column(name = "acceso_sistema")
    private Boolean accesoSistema; // Cambiado de Integer a Boolean, que corresponde a un campo BOOLEAN en la base de datos.

    // Getters y Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getAccesoSistema() {
        return accesoSistema;
    }

    public void setAccesoSistema(Boolean accesoSistema) {
        this.accesoSistema = accesoSistema;
    }
}
