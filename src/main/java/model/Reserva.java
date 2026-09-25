package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {

    private String codigo;
    private LocalDate fechaReserva;
    private LocalDate fechaIngreso;
    private LocalDate fechaFin;
    private double descuento;

    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;

    private List<ServicioAdicional> serviciosAdicionales;

    public Reserva(
            String codigo,
            LocalDate fechaReserva,
            LocalDate fechaIngreso,
            LocalDate fechaFin,
            double descuento,
            Cliente cliente,
            Vehiculo vehiculo,
            ModalidadAlquiler modalidad,
            List<ServicioAdicional> serviciosAdicionales) {

        this.codigo = codigo;
        this.fechaReserva = fechaReserva;
        this.fechaIngreso = fechaIngreso;
        this.fechaFin = fechaFin;
        this.descuento = descuento;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.modalidad = modalidad;

        if (serviciosAdicionales == null) {
            this.serviciosAdicionales = new ArrayList<>();
        } else {
            this.serviciosAdicionales =
                    new ArrayList<>(serviciosAdicionales);
        }
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public ModalidadAlquiler getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadAlquiler modalidad) {
        this.modalidad = modalidad;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return new ArrayList<>(serviciosAdicionales);
    }

    public void setServiciosAdicionales(
            List<ServicioAdicional> serviciosAdicionales) {

        if (serviciosAdicionales == null) {
            this.serviciosAdicionales = new ArrayList<>();
        } else {
            this.serviciosAdicionales =
                    new ArrayList<>(serviciosAdicionales);
        }
    }

    public void agregarServicio(ServicioAdicional servicio) {

        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio no puede ser nulo."
            );
        }

        if (!servicio.estaDisponible()) {
            throw new IllegalStateException(
                    "El servicio no está disponible."
            );
        }

        serviciosAdicionales.add(servicio);
    }

    public int calcularDuracion() {

        if (fechaIngreso == null || fechaFin == null) {
            throw new IllegalStateException(
                    "Las fechas de ingreso y fin son obligatorias."
            );
        }

        if (fechaFin.isBefore(fechaIngreso)) {
            throw new IllegalStateException(
                    "La fecha fin no puede ser anterior a la fecha de ingreso."
            );
        }

        return (int) ChronoUnit.DAYS.between(
                fechaIngreso,
                fechaFin
        );
    }

    public double calcularSubtotal() {

        if (modalidad == null) {
            throw new IllegalStateException(
                    "La reserva debe tener una modalidad."
            );
        }

        int dias = calcularDuracion();

        return modalidad.calcularCosto(dias);
    }

    public double calcularCostoServicios() {

        double totalServicios = 0;

        for (ServicioAdicional servicio : serviciosAdicionales) {
            totalServicios += servicio.getPrecio();
        }

        return totalServicios;
    }

    public double calcularTotal() {

        double subtotal = calcularSubtotal();
        double servicios = calcularCostoServicios();

        double totalAntesDescuento = subtotal + servicios;

        return totalAntesDescuento - descuento;
    }
}
