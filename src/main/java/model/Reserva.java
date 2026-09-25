package model;

import java.time.LocalDate;
import java.util.List;

public class Reserva {
    private String codigo;
    private LocalDate fechaReserva;
    private LocalDate fechaIngreso;
    private LocalDate fechaFin;
    private double descuento;
    private List<ServicioAdicional> serviciosAdicionales;
    private Vehiculo vehiculo;
    private Cliente cliente;


public Reserva (String codigo, LocalDate fechaReserva, LocalDate fechaIngreso, LocalDate fechaFin, double descuento, List<ServicioAdicional> serviciosAdicionales) {
    this.codigo = codigo;
    this.fechaReserva = fechaReserva;
    this.fechaIngreso = fechaIngreso;
    this.fechaFin = fechaFin;
    this.descuento = descuento;
    this.serviciosAdicionales = serviciosAdicionales;
}

public String getCodigo() {
    return codigo;
}
public void setCodigo(String codigo) {
    this.codigo = codigo;
}

public LocalDate getFechaReserva() {
    return fechaReserva;
}

public void setFechaReserva(LocalDate fechaReserva) {
    this.fechaReserva = fechaReserva;
}

public LocalDate getFechaIngreso() {
    return fechaIngreso;
}
public void setFechaIngreso(LocalDate fechaIngreso) {
    this.fechaIngreso = fechaIngreso;
}

public LocalDate getFechaFin() {
    return fechaFin;
}
public void setFechaFin(LocalDate fechaFin) {
    this.fechaFin = fechaFin;
}

public double getDescuento() {
    return descuento;
}
public void setDescuento(double descuento) {
    this.descuento = descuento;
}

}
