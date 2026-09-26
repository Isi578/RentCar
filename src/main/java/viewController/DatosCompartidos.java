package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cliente;
import model.ModalidadAlquiler;
import model.Reserva;
import model.ServicioAdicional;
import model.Vehiculo;
import service.ModalidadService;
import service.ReservaService;
import service.VehiculoService;

public final class DatosCompartidos {

    private static final DatosCompartidos INSTANCIA = new DatosCompartidos();

    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private final ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList();
    private final ObservableList<ModalidadAlquiler> modalidades = FXCollections.observableArrayList();
    private final ObservableList<Reserva> reservas = FXCollections.observableArrayList();
    private final ObservableList<ServicioAdicional> serviciosAdicionales =
            FXCollections.observableArrayList();

    private final VehiculoService vehiculoService = new VehiculoService(vehiculos);
    private final ModalidadService modalidadService = new ModalidadService(modalidades);
    private final ReservaService reservaService = new ReservaService(reservas);

    private DatosCompartidos() {
    }

    public static DatosCompartidos getInstancia() {
        return INSTANCIA;
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }

    public ObservableList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ObservableList<ModalidadAlquiler> getModalidades() {
        return modalidades;
    }

    public ObservableList<Reserva> getReservas() {
        return reservas;
    }

    public ObservableList<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public VehiculoService getVehiculoService() {
        return vehiculoService;
    }

    public ModalidadService getModalidadService() {
        return modalidadService;
    }

    public ReservaService getReservaService() {
        return reservaService;
    }
}
