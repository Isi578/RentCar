package service;

import model.Reserva;

import java.time.LocalDate;
import java.util.List;

public class ReporteService {

    public double calcularIngresosPorPeriodo(
            List<Reserva> reservas,
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        if (reservas == null) {
            throw new IllegalArgumentException(
                    "La lista de reservas no puede ser nula."
            );
        }

        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException(
                    "Las fechas del periodo son obligatorias."
            );
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException(
                    "La fecha final no puede ser anterior a la fecha inicial."
            );
        }

        double totalIngresos = 0;

        for (Reserva reserva : reservas) {

            if (reserva == null) {
                continue;
            }

            LocalDate fechaReserva = reserva.getFechaReserva();

            if (fechaReserva == null) {
                continue;
            }

            boolean estaEnPeriodo =
                    !fechaReserva.isBefore(fechaInicio)
                            && !fechaReserva.isAfter(fechaFin);

            if (estaEnPeriodo) {
                totalIngresos += reserva.calcularTotal();
            }
        }

        return totalIngresos;
    }
}