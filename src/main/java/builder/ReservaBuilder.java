package builder;

import model.Cliente;
import model.ModalidadAlquiler;
import model.Reserva;
import model.ServicioAdicional;
import model.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaBuilder {

    private String codigo;
    private LocalDate fechaReserva;
    private LocalDate fechaIngreso;
    private LocalDate fechaFin;
    private double descuento;

    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;

    private List<ServicioAdicional> serviciosAdicionales =
            new ArrayList<>();

    public ReservaBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ReservaBuilder fechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
        return this;
    }

    public ReservaBuilder fechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public ReservaBuilder fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public ReservaBuilder descuento(double descuento) {
        this.descuento = descuento;
        return this;
    }

    public ReservaBuilder cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public ReservaBuilder vehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        return this;
    }

    public ReservaBuilder modalidad(
            ModalidadAlquiler modalidad) {

        this.modalidad = modalidad;
        return this;
    }

    public ReservaBuilder agregarServicio(
            ServicioAdicional servicio) {

        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio no puede ser nulo."
            );
        }

        this.serviciosAdicionales.add(servicio);

        return this;
    }

    public Reserva build() {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalStateException(
                    "El código de la reserva es obligatorio."
            );
        }

        if (cliente == null) {
            throw new IllegalStateException(
                    "El cliente es obligatorio."
            );
        }

        if (vehiculo == null) {
            throw new IllegalStateException(
                    "El vehículo es obligatorio."
            );
        }

        if (modalidad == null) {
            throw new IllegalStateException(
                    "La modalidad es obligatoria."
            );
        }

        if (fechaReserva == null) {
            throw new IllegalStateException(
                    "La fecha de reserva es obligatoria."
            );
        }

        if (fechaIngreso == null) {
            throw new IllegalStateException(
                    "La fecha de ingreso es obligatoria."
            );
        }

        if (fechaFin == null) {
            throw new IllegalStateException(
                    "La fecha de fin es obligatoria."
            );
        }

        if (fechaFin.isBefore(fechaIngreso)) {
            throw new IllegalStateException(
                    "La fecha de fin no puede ser anterior a la fecha de ingreso."
            );
        }

        if (descuento < 0) {
            throw new IllegalArgumentException(
                    "El descuento no puede ser negativo."
            );
        }

        return new Reserva(
                codigo,
                fechaReserva,
                fechaIngreso,
                fechaFin,
                descuento,
                cliente,
                vehiculo,
                modalidad,
                serviciosAdicionales
        );
    }
}