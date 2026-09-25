package service;

import model.Reserva;
import model.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaService {

    private final List<Reserva> reservas;

    public ReservaService() {
        this.reservas = new ArrayList<>();
    }

    public void registrarReserva(Reserva reserva) {

        if (reserva == null) {
            throw new IllegalArgumentException(
                    "La reserva no puede ser nula."
            );
        }

        if (buscarPorCodigo(reserva.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una reserva con el código: "
                            + reserva.getCodigo()
            );
        }

        if (!verificarDisponibilidadVehiculo(
                reserva.getVehiculo(),
                reserva.getFechaIngreso(),
                reserva.getFechaFin())) {

            throw new IllegalStateException(
                    "El vehículo no está disponible para ese período."
            );
        }

        reservas.add(reserva);
    }

    public List<Reserva> obtenerReservas() {
        return new ArrayList<>(reservas);
    }

    public Reserva buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (Reserva reserva : reservas) {

            if (reserva != null
                    && codigo.equals(reserva.getCodigo())) {

                return reserva;
            }
        }

        return null;
    }

    public boolean verificarDisponibilidadVehiculo(
            Vehiculo vehiculo,
            LocalDate fechaIngreso,
            LocalDate fechaFin) {

        if (vehiculo == null
                || fechaIngreso == null
                || fechaFin == null) {

            return false;
        }

        if (fechaFin.isBefore(fechaIngreso)) {
            return false;
        }

        for (Reserva reserva : reservas) {

            if (reserva == null
                    || reserva.getVehiculo() == null) {
                continue;
            }

            if (!vehiculo.getPlaca().equals(
                    reserva.getVehiculo().getPlaca())) {
                continue;
            }

            LocalDate inicioExistente =
                    reserva.getFechaIngreso();

            LocalDate finExistente =
                    reserva.getFechaFin();

            if (inicioExistente == null || finExistente == null) {
                continue;
            }

            boolean existeSolapamiento =
                    fechaIngreso.isBefore(finExistente)
                            && fechaFin.isAfter(inicioExistente);

            if (existeSolapamiento) {
                return false;
            }
        }

        return true;
    }

    public double calcularTotalReserva(String codigo) {

        Reserva reserva = buscarPorCodigo(codigo);

        if (reserva == null) {
            throw new IllegalArgumentException(
                    "No existe una reserva con el código: "
                            + codigo
            );
        }

        return reserva.calcularTotal();
    }
}
