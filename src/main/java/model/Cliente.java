package model;

import java.time.LocalDate;

public class Cliente {
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private String correo;
    private int edad;
    private LocalDate fechaRegistro;

public Cliente(String nombreCompleto, String documento, String telefono, String correo) {
    this.nombreCompleto = nombreCompleto;
    this.documento = documento;
    this.telefono = telefono;
    this.correo = correo;
    this.edad = 0;
    this.fechaRegistro = LocalDate.now();
}

public String getNombreCompleto() {
    return nombreCompleto;
}
public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
}

public String getDocumento() {
    return documento;
}
public void setDocumento(String documento) {
    this.documento = documento;
}

public String getTelefono() {
    return telefono;
}
public void setTelefono(String telefono) {
    this.telefono = telefono;
}

public String getCorreo() {
    return correo;
}
public void setCorreo(String correo) {
    this.correo = correo;
}

public int getEdad() {
    return edad;
}
public void setEdad(int edad) {
    this.edad = edad;
}

public LocalDate getFechaRegistro() {
    return fechaRegistro;
}
public void setFechaRegistro(LocalDate fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
}

}
