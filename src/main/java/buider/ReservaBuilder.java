package buider;

import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaBuilder {
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidadAlquiler;
    private LocalDate fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<ServicioAdicional> servicioAdicionals;
    private double descuento;
}
