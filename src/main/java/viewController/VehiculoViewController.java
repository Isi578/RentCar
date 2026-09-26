package viewController;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Vehiculo;
import service.VehiculoService;

import java.util.Locale;

public class VehiculoViewController {

    @FXML
    private TextField buscarPlacaField;
    @FXML
    private TextField placaField;
    @FXML
    private TextField marcaField;
    @FXML
    private TextField modeloField;
    @FXML
    private TextField anioField;
    @FXML
    private TextField tipoField;
    @FXML
    private TextField tarifaDiariaField;
    @FXML
    private Label mensajeLabel;
    @FXML
    private TableView<Vehiculo> vehiculosTable;
    @FXML
    private TableColumn<Vehiculo, String> placaColumn;
    @FXML
    private TableColumn<Vehiculo, String> marcaColumn;
    @FXML
    private TableColumn<Vehiculo, String> modeloColumn;
    @FXML
    private TableColumn<Vehiculo, String> anioColumn;
    @FXML
    private TableColumn<Vehiculo, String> tipoColumn;
    @FXML
    private TableColumn<Vehiculo, String> tarifaDiariaColumn;

    private final VehiculoService vehiculoService =
            DatosCompartidos.getInstancia().getVehiculoService();
    private FilteredList<Vehiculo> vehiculosFiltrados;

    @FXML
    private void initialize() {
        placaColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getPlaca()));
        marcaColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getMarca()));
        modeloColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getModelo()));
        anioColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(Integer.toString(celda.getValue().getAnio())));
        tipoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getTipo()));
        tarifaDiariaColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(String.format(Locale.ROOT, "%.2f",
                        celda.getValue().getTarifaDiaria())));

        vehiculosFiltrados = new FilteredList<>(
                DatosCompartidos.getInstancia().getVehiculos(), vehiculo -> true);
        vehiculosTable.setItems(vehiculosFiltrados);
    }

    @FXML
    private void registrarVehiculo() {
        String placa = placaField.getText().trim();
        String marca = marcaField.getText().trim();
        String modelo = modeloField.getText().trim();
        String anioTexto = anioField.getText().trim();
        String tipo = tipoField.getText().trim();
        String tarifaTexto = tarifaDiariaField.getText().trim();

        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty()
                || anioTexto.isEmpty() || tipo.isEmpty() || tarifaTexto.isEmpty()) {
            mostrarMensaje("Completa todos los campos.", true);
            return;
        }

        int anio;
        double tarifa;
        try {
            anio = Integer.parseInt(anioTexto);
            tarifa = Double.parseDouble(tarifaTexto);
        } catch (NumberFormatException excepcion) {
            mostrarMensaje("El año y la tarifa deben ser numéricos.", true);
            return;
        }
        if (anio <= 0 || tarifa <= 0) {
            mostrarMensaje("El año y la tarifa deben ser mayores que cero.", true);
            return;
        }

        try {
            vehiculoService.registrarVehiculo(
                    new Vehiculo(placa, marca, modelo, anio, tipo, tarifa));
            limpiarFormulario();
            mostrarVehiculos();
            mostrarMensaje("Vehículo registrado correctamente.", false);
        } catch (IllegalArgumentException excepcion) {
            mostrarMensaje(excepcion.getMessage(), true);
        }
    }

    @FXML
    private void buscarVehiculo() {
        String placa = buscarPlacaField.getText().trim();
        if (placa.isEmpty()) {
            mostrarMensaje("Ingresa la placa que deseas buscar.", true);
            return;
        }

        vehiculosFiltrados.setPredicate(vehiculo ->
                vehiculo.getPlaca().toLowerCase(Locale.ROOT)
                        .contains(placa.toLowerCase(Locale.ROOT)));
        if (vehiculosFiltrados.isEmpty()) {
            mostrarMensaje("No se encontró un vehículo con esa placa.", true);
        } else {
            mostrarMensaje("Resultados de búsqueda: " + vehiculosFiltrados.size(), false);
        }
    }

    @FXML
    private void mostrarVehiculos() {
        buscarPlacaField.clear();
        vehiculosFiltrados.setPredicate(vehiculo -> true);
    }

    private void limpiarFormulario() {
        placaField.clear();
        marcaField.clear();
        modeloField.clear();
        anioField.clear();
        tipoField.clear();
        tarifaDiariaField.clear();
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError
                ? "-fx-text-fill: #b91c1c;"
                : "-fx-text-fill: #15803d;");
    }
}
