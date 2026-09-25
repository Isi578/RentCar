package model;

public class ModalidadAlquiler {
    private String codigo;
    private String nombre;
    private String descripcion;
    private int duracionMinimaDias;
    private double precioDiario;
    private EstadoModalidad estadoModalidad;
    private String beneficiario;


public ModalidadAlquiler(String codigo, String nombre, String descripcion, int duracionMinimaDias, double valorDiario,EstadoModalidad estadoModalidad, String beneficiario) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.duracionMinimaDias = duracionMinimaDias;
    this.precioDiario = valorDiario;
    this.estadoModalidad = estadoModalidad;
    this.beneficiario = beneficiario;
}

public String getCodigo() {
    return codigo;
}
public void setCodigo(String codigo) {
    this.codigo = codigo;
}

public String getNombre() {
    return nombre;
}
public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getDescripcion() {
    return descripcion;
}
public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public int getDuracionMinimaDias() {
    return duracionMinimaDias;
}
public void setDuracionMinimaDias(int duracionMinimaDias) {
    this.duracionMinimaDias = duracionMinimaDias;
}

public double getPrecioDiario() {
    return precioDiario;
}
public void setPrecioDiario(double precioDiario) {
    this.precioDiario = precioDiario;
}

public EstadoModalidad getEstadoModalidad() {
    return estadoModalidad;
}
public void setEstadoModalidad(EstadoModalidad estadoModalidad) {
    this.estadoModalidad = estadoModalidad;
}

public String getBeneficiario() {
    return beneficiario;
}
public void setBeneficiario(String beneficiario) {
    this.beneficiario = beneficiario;
}

}
