package viewController;

import factory.ModalidadFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.EstadoModalidad;
import model.ModalidadAlquiler;
import model.Premium;
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
    private Label coberturaLabel;
    @FXML
    private TextField coberturaField;
    @FXML
    private Label conductoresLabel;
    @FXML
    private TextField conductoresField;
    @FXML
    private Label caracteristicasLabel;
    @FXML
    private TextField caracteristicasField;
    @FXML
    private Label mensajeLabel;
    @FXML
    private Button guardarButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button cancelarButton;
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
    @FXML
    private TableColumn<ModalidadAlquiler, String> coberturaColumn;
    @FXML
    private TableColumn<ModalidadAlquiler, String> conductoresColumn;
    @FXML
    private TableColumn<ModalidadAlquiler, String> caracteristicasColumn;

    private final ModalidadService modalidadService =
            DatosCompartidos.getInstancia().getModalidadService();
    private ModalidadAlquiler modalidadEnEdicion;

    @FXML
    private void initialize() {
        tipoCombo.setItems(FXCollections.observableArrayList(
                "ECONOMICA", "EJECUTIVA", "PREMIUM"));
        tipoCombo.valueProperty().addListener(
                (observable, anterior, tipo) -> actualizarCamposPremium(tipo));
        actualizarCamposPremium(tipoCombo.getValue());
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
        coberturaColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue() instanceof Premium premium
                        ? premium.getTipoCobertura() : ""));
        conductoresColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue() instanceof Premium premium
                        ? Integer.toString(premium.getConductoresAdicionalesPermitidos()) : ""));
        caracteristicasColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue() instanceof Premium premium
                        ? premium.getCaracteristicasEspeciales() : ""));
        modalidadesTable.setItems(DatosCompartidos.getInstancia().getModalidades());
        modalidadesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, seleccionada) -> {
                    boolean haySeleccion = seleccionada != null;
                    editarButton.setDisable(!haySeleccion || modalidadEnEdicion != null);
                    eliminarButton.setDisable(!haySeleccion || modalidadEnEdicion != null);
                });
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
        String cobertura = coberturaField.getText().trim();
        String conductoresTexto = conductoresField.getText().trim();
        String caracteristicas = caracteristicasField.getText().trim();

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

        int conductoresAdicionales = 0;
        if ("PREMIUM".equals(tipo)) {
            if (cobertura.isEmpty() || conductoresTexto.isEmpty() || caracteristicas.isEmpty()) {
                mostrarMensaje("Completa los atributos adicionales de Premium.", true);
                return;
            }
            try {
                conductoresAdicionales = Integer.parseInt(conductoresTexto);
            } catch (NumberFormatException excepcion) {
                mostrarMensaje("Los conductores adicionales deben ser un número entero.", true);
                return;
            }
            if (conductoresAdicionales < 0) {
                mostrarMensaje("Los conductores adicionales no pueden ser negativos.", true);
                return;
            }
        }

        if (modalidadEnEdicion != null) {
            modalidadEnEdicion.setNombre(nombre);
            modalidadEnEdicion.setDescripcion(descripcion);
            modalidadEnEdicion.setDuracionMinimaDias(duracionMinima);
            modalidadEnEdicion.setPrecioDiario(precio);
            modalidadEnEdicion.setEstadoModalidad(estadoCombo.getValue());
            modalidadEnEdicion.setBeneficios(beneficios);
            if (modalidadEnEdicion instanceof Premium premium) {
                premium.setTipoCobertura(cobertura);
                premium.setConductoresAdicionalesPermitidos(conductoresAdicionales);
                premium.setCaracteristicasEspeciales(caracteristicas);
            }
            modalidadesTable.refresh();
            cancelarEdicion();
            mostrarMensaje("Modalidad actualizada correctamente.", false);
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
                    beneficios,
                    cobertura,
                    conductoresAdicionales,
                    caracteristicas);
            modalidadService.registrarModalidad(modalidad);
            limpiarFormulario();
            mostrarMensaje("Modalidad registrada correctamente.", false);
        } catch (IllegalArgumentException excepcion) {
            mostrarMensaje(excepcion.getMessage(), true);
        }
    }

    @FXML
    private void editarModalidad() {
        ModalidadAlquiler seleccionada =
                modalidadesTable.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Selecciona una modalidad para editar.", true);
            return;
        }

        modalidadEnEdicion = seleccionada;
        codigoField.setText(seleccionada.getCodigo());
        codigoField.setDisable(true);
        nombreField.setText(seleccionada.getNombre());
        tipoCombo.setValue(seleccionada.getClass().getSimpleName().toUpperCase(Locale.ROOT));
        tipoCombo.setDisable(true);
        estadoCombo.setValue(seleccionada.getEstadoModalidad());
        precioDiarioField.setText(Double.toString(seleccionada.getPrecioDiario()));
        duracionMinimaField.setText(
                Integer.toString(seleccionada.getDuracionMinimaDias()));
        descripcionField.setText(seleccionada.getDescripcion());
        beneficiosField.setText(seleccionada.getBeneficios());
        if (seleccionada instanceof Premium premium) {
            coberturaField.setText(premium.getTipoCobertura());
            conductoresField.setText(
                    Integer.toString(premium.getConductoresAdicionalesPermitidos()));
            caracteristicasField.setText(premium.getCaracteristicasEspeciales());
        }
        guardarButton.setText("Guardar cambios");
        cancelarButton.setDisable(false);
        editarButton.setDisable(true);
        eliminarButton.setDisable(true);
        mostrarMensaje("Editando: " + seleccionada.getNombre(), false);
    }

    @FXML
    private void eliminarModalidad() {
        ModalidadAlquiler seleccionada =
                modalidadesTable.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Selecciona una modalidad para eliminar.", true);
            return;
        }

        boolean utilizada = DatosCompartidos.getInstancia().getReservas().stream()
                .anyMatch(reserva -> reserva.getModalidad() == seleccionada);
        if (utilizada) {
            mostrarMensaje("No se puede eliminar una modalidad asociada a una reserva.", true);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar modalidad");
        confirmacion.setHeaderText("¿Eliminar " + seleccionada.getNombre() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");
        if (confirmacion.showAndWait().filter(
                respuesta -> respuesta == javafx.scene.control.ButtonType.OK).isEmpty()) {
            return;
        }

        DatosCompartidos.getInstancia().getModalidades().remove(seleccionada);
        mostrarMensaje("Modalidad eliminada correctamente.", false);
    }

    @FXML
    private void cancelarEdicion() {
        modalidadEnEdicion = null;
        codigoField.setDisable(false);
        tipoCombo.setDisable(false);
        guardarButton.setText("Registrar modalidad");
        cancelarButton.setDisable(true);
        limpiarFormulario();
        boolean haySeleccion = modalidadesTable.getSelectionModel().getSelectedItem() != null;
        editarButton.setDisable(!haySeleccion);
        eliminarButton.setDisable(!haySeleccion);
        mostrarMensaje("Edición cancelada.", false);
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
        coberturaField.clear();
        conductoresField.clear();
        caracteristicasField.clear();
        actualizarCamposPremium(tipoCombo.getValue());
    }

    private void actualizarCamposPremium(String tipo) {
        boolean premium = "PREMIUM".equals(tipo);
        coberturaLabel.setVisible(premium);
        coberturaLabel.setManaged(premium);
        coberturaField.setVisible(premium);
        coberturaField.setManaged(premium);
        conductoresLabel.setVisible(premium);
        conductoresLabel.setManaged(premium);
        conductoresField.setVisible(premium);
        conductoresField.setManaged(premium);
        caracteristicasLabel.setVisible(premium);
        caracteristicasLabel.setManaged(premium);
        caracteristicasField.setVisible(premium);
        caracteristicasField.setManaged(premium);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError
                ? "-fx-text-fill: #b91c1c;"
                : "-fx-text-fill: #15803d;");
    }
}
