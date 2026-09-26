package viewController;

import builder.ReservaBuilder;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Cliente;
import model.EstadoModalidad;
import model.ModalidadAlquiler;
import model.Reserva;
import model.Vehiculo;
import service.ReporteService;
import service.ReservaService;

import java.time.LocalDate;
import java.util.Locale;

public class ReservaViewController {

    @FXML
    private TextField codigoField;
    @FXML
    private ComboBox<Cliente> clienteCombo;
    @FXML
    private ComboBox<Vehiculo> vehiculoCombo;
    @FXML
    private ComboBox<ModalidadAlquiler> modalidadCombo;
    @FXML
    private DatePicker fechaIngresoPicker;
    @FXML
    private DatePicker fechaFinPicker;
    @FXML
    private DatePicker reporteFechaInicioPicker;
    @FXML
    private DatePicker reporteFechaFinPicker;
    @FXML
    private TextField descuentoField;
    @FXML
    private Label mensajeLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label ingresosLabel;
    @FXML
    private TableView<Reserva> reservasTable;
    @FXML
    private TableColumn<Reserva, String> codigoColumn;
    @FXML
    private TableColumn<Reserva, String> clienteColumn;
    @FXML
    private TableColumn<Reserva, String> vehiculoColumn;
    @FXML
    private TableColumn<Reserva, String> modalidadColumn;
    @FXML
    private TableColumn<Reserva, String> fechaIngresoColumn;
    @FXML
    private TableColumn<Reserva, String> fechaFinColumn;
    @FXML
    private TableColumn<Reserva, String> totalColumn;

    private final DatosCompartidos datos = DatosCompartidos.getInstancia();
    private final ReservaService reservaService = datos.getReservaService();
    private final ReporteService reporteService = new ReporteService();

    @FXML
    private void initialize() {
        clienteCombo.setItems(datos.getClientes());
        vehiculoCombo.setItems(datos.getVehiculos());
        modalidadCombo.setItems(new FilteredList<>(datos.getModalidades(),
                modalidad -> modalidad.getEstadoModalidad() == EstadoModalidad.DISPONIBLE));

        clienteCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente == null ? "" : cliente.getNombreCompleto()
                        + " (" + cliente.getDocumento() + ")";
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });
        vehiculoCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Vehiculo vehiculo) {
                return vehiculo == null ? "" : vehiculo.getPlaca()
                        + " - " + vehiculo.getMarca() + " " + vehiculo.getModelo();
            }

            @Override
            public Vehiculo fromString(String texto) {
                return null;
            }
        });
        modalidadCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(ModalidadAlquiler modalidad) {
                return modalidad == null ? "" : modalidad.getNombre() + " - "
                        + formatoMoneda(modalidad.getPrecioDiario()) + " por día";
            }

            @Override
            public ModalidadAlquiler fromString(String texto) {
                return null;
            }
        });

        configurarColumnas();
        reservasTable.setItems(datos.getReservas());
        reservasTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, reserva) -> mostrarTotal(reserva));
        codigoField.setText(generarCodigo());
        fechaIngresoPicker.setValue(LocalDate.now());
        fechaFinPicker.setValue(LocalDate.now().plusDays(1));
        mensajeLabel.setText(obtenerEstadoCatalogos());
    }

    private void configurarColumnas() {
        codigoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getCodigo()));
        clienteColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getCliente().getNombreCompleto()));
        vehiculoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getVehiculo().getPlaca()));
        modalidadColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getModalidad().getNombre()));
        fechaIngresoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getFechaIngreso().toString()));
        fechaFinColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getFechaFin().toString()));
        totalColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(formatoMoneda(
                        reservaService.calcularTotalReserva(celda.getValue().getCodigo()))));
    }

    @FXML
    private void registrarReserva() {
        Cliente cliente = clienteCombo.getValue();
        Vehiculo vehiculo = vehiculoCombo.getValue();
        ModalidadAlquiler modalidad = modalidadCombo.getValue();
        LocalDate fechaIngreso = fechaIngresoPicker.getValue();
        LocalDate fechaFin = fechaFinPicker.getValue();

        if (cliente == null || vehiculo == null || modalidad == null
                || fechaIngreso == null || fechaFin == null) {
            mostrarMensaje("Selecciona cliente, vehículo, modalidad y ambas fechas.", true);
            return;
        }
        if (!fechaFin.isAfter(fechaIngreso)) {
            mostrarMensaje("La fecha de fin debe ser posterior a la fecha de ingreso.", true);
            return;
        }

        double descuento;
        try {
            descuento = Double.parseDouble(descuentoField.getText().trim());
        } catch (NumberFormatException excepcion) {
            mostrarMensaje("El descuento debe ser un número.", true);
            return;
        }
        if (descuento < 0) {
            mostrarMensaje("El descuento no puede ser negativo.", true);
            return;
        }

        try {
            Reserva reserva = new ReservaBuilder()
                    .codigo(codigoField.getText())
                    .fechaReserva(LocalDate.now())
                    .fechaIngreso(fechaIngreso)
                    .fechaFin(fechaFin)
                    .descuento(descuento)
                    .cliente(cliente)
                    .vehiculo(vehiculo)
                    .modalidad(modalidad)
                    .build();

            double total = reserva.calcularTotal();
            if (descuento > reserva.calcularSubtotal()) {
                mostrarMensaje("El descuento no puede superar el subtotal.", true);
                return;
            }

            reservaService.registrarReserva(reserva);
            reservasTable.getSelectionModel().select(reserva);
            totalLabel.setText("Total: " + formatoMoneda(total));
            mensajeLabel.setText("Reserva registrada correctamente.");
            codigoField.setText(generarCodigo());
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            mostrarMensaje(excepcion.getMessage(), true);
        }
    }

    @FXML
    private void calcularIngresos() {
        LocalDate fechaInicio = reporteFechaInicioPicker.getValue();
        LocalDate fechaFin = reporteFechaFinPicker.getValue();

        if (fechaInicio == null || fechaFin == null) {
            mostrarMensaje("Selecciona las fechas de inicio y fin del período.", true);
            return;
        }

        try {
            double ingresos = reporteService.calcularIngresosPorPeriodo(
                    datos.getReservas(), fechaInicio, fechaFin);
            ingresosLabel.setText("Ingresos: " + formatoMoneda(ingresos));
            mostrarMensaje("Ingresos calculados para el período seleccionado.", false);
        } catch (IllegalArgumentException excepcion) {
            mostrarMensaje(excepcion.getMessage(), true);
        }
    }

    private void mostrarTotal(Reserva reserva) {
        if (reserva == null) {
            totalLabel.setText("Total: --");
            return;
        }
        totalLabel.setText("Total: " + formatoMoneda(
                reservaService.calcularTotalReserva(reserva.getCodigo())));
    }

    private String generarCodigo() {
        int consecutivo = datos.getReservas().size() + 1;
        String codigo;
        do {
            codigo = String.format(Locale.ROOT, "RES-%04d", consecutivo++);
        } while (reservaService.buscarPorCodigo(codigo) != null);
        return codigo;
    }

    private String obtenerEstadoCatalogos() {
        if (datos.getClientes().isEmpty()) {
            return "Registra primero un cliente en la pestaña Clientes.";
        }
        if (datos.getVehiculos().isEmpty()) {
            return "Registra primero un vehículo en la pestaña Vehículos.";
        }
        if (modalidadCombo.getItems().isEmpty()) {
            return "Registra una modalidad disponible en la pestaña Modalidades.";
        }
        return "Selecciona los datos para crear la reserva.";
    }

    private String formatoMoneda(double valor) {
        return String.format(Locale.ROOT, "$ %.2f", valor);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError
                ? "-fx-text-fill: #b91c1c;"
                : "-fx-text-fill: #15803d;");
    }
}
