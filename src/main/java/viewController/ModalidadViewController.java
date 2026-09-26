package viewController;

import factory.ModalidadFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.EstadoModalidad;
import model.ModalidadAlquiler;
import service.ModalidadService;

import java.util.Locale;

public class ModalidadViewController {

    @FXML
    private TextField codigoField;
    @FXML
    private TextField nombreField;
    @FXML
    private ComboBox<String> tipoCombo;
    @FXML
    private ComboBox<EstadoModalidad> estadoCombo;
    @FXML
    private TextField precioDiarioField;
    @FXML
    private TextField duracionMinimaField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField beneficiosField;
    @FXML
    private Label mensajeLabel;
    @FXML
    private TableView<ModalidadAlquiler> modalidadesTable;
    @FXML
    private TableColumn<ModalidadAlquiler, String> codigoColumn;
    @FXML
    private TableColumn<ModalidadAlquiler, String> nombreColumn;
    @FXML
    private TableColumn<ModalidadAlquiler, String> tipoColumn;
    @FXML
    private TableColumn<ModalidadAlquiler, String> estadoColumn;
    @FXML
    private TableColumn<ModalidadAlquiler, String> precioColumn;
    @FXML
    private TableColumn<ModalidadAlquiler, String> duracionColumn;

    private final ModalidadService modalidadService =
            DatosCompartidos.getInstancia().getModalidadService();

    @FXML
    private void initialize() {
        tipoCombo.setItems(FXCollections.observableArrayList(
                "ECONOMICA", "EJECUTIVA", "PREMIUM"));
        estadoCombo.setItems(FXCollections.observableArrayList(EstadoModalidad.values()));
        estadoCombo.setValue(EstadoModalidad.DISPONIBLE);

        codigoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getCodigo()));
        nombreColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getNombre()));
        tipoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getClass().getSimpleName()));
        estadoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getEstadoModalidad().toString()));
        precioColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(String.format(Locale.ROOT, "%.2f",
                        celda.getValue().getPrecioDiario())));
        duracionColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(Integer.toString(
                        celda.getValue().getDuracionMinimaDias())));
        modalidadesTable.setItems(DatosCompartidos.getInstancia().getModalidades());
    }

    @FXML
    private void registrarModalidad() {
        String codigo = codigoField.getText().trim();
        String nombre = nombreField.getText().trim();
        String tipo = tipoCombo.getValue();
        String precioTexto = precioDiarioField.getText().trim();
        String duracionTexto = duracionMinimaField.getText().trim();
        String descripcion = descripcionField.getText().trim();
        String beneficios = beneficiosField.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || tipo == null
                || precioTexto.isEmpty() || duracionTexto.isEmpty()
                || descripcion.isEmpty() || beneficios.isEmpty()
                || estadoCombo.getValue() == null) {
            mostrarMensaje("Completa todos los campos.", true);
            return;
        }

        double precio;
        int duracionMinima;
        try {
            precio = Double.parseDouble(precioTexto);
            duracionMinima = Integer.parseInt(duracionTexto);
        } catch (NumberFormatException excepcion) {
            mostrarMensaje("El precio y la duración deben ser numéricos.", true);
            return;
        }
        if (precio <= 0 || duracionMinima <= 0) {
            mostrarMensaje("El precio y la duración deben ser mayores que cero.", true);
            return;
        }

        try {
            ModalidadAlquiler modalidad = ModalidadFactory.crear(
                    tipo,
                    codigo,
                    nombre,
                    descripcion,
                    duracionMinima,
                    precio,
                    estadoCombo.getValue(),
                    beneficios);
            modalidadService.registrarModalidad(modalidad);
            limpiarFormulario();
            mostrarMensaje("Modalidad registrada correctamente.", false);
        } catch (IllegalArgumentException excepcion) {
            mostrarMensaje(excepcion.getMessage(), true);
        }
    }

    private void limpiarFormulario() {
        codigoField.clear();
        nombreField.clear();
        tipoCombo.getSelectionModel().clearSelection();
        estadoCombo.setValue(EstadoModalidad.DISPONIBLE);
        precioDiarioField.clear();
        duracionMinimaField.clear();
        descripcionField.clear();
        beneficiosField.clear();
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError
                ? "-fx-text-fill: #b91c1c;"
                : "-fx-text-fill: #15803d;");
    }
}
