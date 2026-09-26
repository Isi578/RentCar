package viewController;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Cliente;
import service.ClienteService;
import service.NumeroService;

import java.time.LocalDate;

public class ClienteViewController {

    @FXML
    private TextField buscarField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField documentoField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField correoField;
    @FXML
    private TextField edadField;
    @FXML
    private TableView<Cliente> clientesTable;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> documentoColumn;
    @FXML
    private TableColumn<Cliente, String> telefonoColumn;
    @FXML
    private TableColumn<Cliente, String> correoColumn;
    @FXML
    private TableColumn<Cliente, String> edadColumn;
    @FXML
    private TableColumn<Cliente, String> fechaColumn;
    @FXML
    private Button guardarButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Label mensajeLabel;

    private final ObservableList<Cliente> clientes =
            DatosCompartidos.getInstancia().getClientes();
    private final ClienteService clienteService = new ClienteService();
    private final NumeroService numeroService = new NumeroService();
    private FilteredList<Cliente> clientesFiltrados;
    private Cliente clienteEnEdicion;

    @FXML
    private void initialize() {
        configurarColumnas();

        clientesFiltrados = new FilteredList<>(clientes, cliente -> true);
        clientesTable.setItems(clientesFiltrados);

        clientesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, seleccionado) -> actualizarBotonesSeleccion(seleccionado));
    }

    @FXML
    private void buscarCliente() {
        String telefono = buscarField.getText();
        if (telefono == null || telefono.isBlank()) {
            mostrarMensaje("Escribe el número de teléfono que deseas buscar.", true);
            return;
        }

        Cliente clienteEncontrado = clienteService.buscarPorTelefono(clientes, telefono);
        clientesFiltrados.setPredicate(cliente -> cliente == clienteEncontrado);
        buscarField.clear();
        if (clienteEncontrado == null) {
            mostrarMensaje("No se encontró un cliente con ese teléfono.", true);
            return;
        }

        clientesTable.getSelectionModel().select(clienteEncontrado);
        boolean telefonoPerfecto =
                numeroService.esTelefonoNumeroPerfecto(clienteEncontrado.getTelefono());
        mostrarMensaje(
                telefonoPerfecto
                        ? "Cliente encontrado: su teléfono es un número perfecto."
                        : "Cliente encontrado: su teléfono no es un número perfecto.",
                !telefonoPerfecto);
    }

    private void configurarColumnas() {
        nombreColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getNombreCompleto()));
        documentoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getDocumento()));
        telefonoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getTelefono()));
        correoColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getCorreo()));
        edadColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(Integer.toString(celda.getValue().getEdad())));
        fechaColumn.setCellValueFactory(celda ->
                new ReadOnlyStringWrapper(celda.getValue().getFechaRegistro().toString()));
    }

    @FXML
    private void guardarCliente() {
        String nombre = nombreField.getText().trim();
        String documento = documentoField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String correo = correoField.getText().trim();
        String edadTexto = edadField.getText().trim();

        if (nombre.isEmpty() || documento.isEmpty() || telefono.isEmpty()
                || correo.isEmpty() || edadTexto.isEmpty()) {
            mostrarMensaje("Completa todos los campos.", true);
            return;
        }
        boolean telefonoPerfecto = numeroService.esTelefonoNumeroPerfecto(telefono);
        if (!correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            mostrarMensaje("Ingresa un correo electrónico válido.", true);
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
        } catch (NumberFormatException excepcion) {
            mostrarMensaje("La edad debe ser un número entero.", true);
            return;
        }
        if (edad <= 0) {
            mostrarMensaje("La edad debe ser mayor que cero.", true);
            return;
        }

        boolean documentoDuplicado = clientes.stream()
                .anyMatch(cliente -> cliente != clienteEnEdicion
                        && cliente.getDocumento().equalsIgnoreCase(documento));
        if (documentoDuplicado) {
            mostrarMensaje("Ya existe un cliente con ese documento.", true);
            return;
        }

        if (clienteEnEdicion == null) {
            Cliente nuevoCliente = new Cliente(nombre, documento, telefono, correo, edad, LocalDate.now());
            clientes.add(nuevoCliente);
            buscarField.clear();
            clientesFiltrados.setPredicate(cliente -> true);
            clientesTable.getSelectionModel().select(nuevoCliente);
            limpiarCampos();
            mostrarMensaje(
                    telefonoPerfecto
                            ? "Cliente registrado correctamente."
                            : "Cliente registrado; el teléfono no es un número perfecto.",
                    !telefonoPerfecto);
        } else {
            clienteEnEdicion.setNombreCompleto(nombre);
            clienteEnEdicion.setDocumento(documento);
            clienteEnEdicion.setTelefono(telefono);
            clienteEnEdicion.setCorreo(correo);
            clienteEnEdicion.setEdad(edad);
            clientesTable.refresh();
            cancelarEdicion();
            mostrarMensaje(
                    telefonoPerfecto
                            ? "Cambios guardados correctamente."
                            : "Cambios guardados; el teléfono no es un número perfecto.",
                    !telefonoPerfecto);
        }
    }

    @FXML
    private void editarCliente() {
        Cliente seleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Selecciona un cliente para editar.", true);
            return;
        }

        clienteEnEdicion = seleccionado;
        nombreField.setText(seleccionado.getNombreCompleto());
        documentoField.setText(seleccionado.getDocumento());
        telefonoField.setText(seleccionado.getTelefono());
        correoField.setText(seleccionado.getCorreo());
        edadField.setText(Integer.toString(seleccionado.getEdad()));
        guardarButton.setText("Guardar cambios");
        cancelarButton.setDisable(false);
        mostrarMensaje("Editando: " + seleccionado.getNombreCompleto(), false);
    }

    @FXML
    private void eliminarCliente() {
        Cliente seleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Selecciona un cliente para eliminar.", true);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar cliente");
        confirmacion.setHeaderText("¿Eliminar a " + seleccionado.getNombreCompleto() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");
        if (confirmacion.showAndWait().filter(respuesta ->
                respuesta == javafx.scene.control.ButtonType.OK).isEmpty()) {
            return;
        }

        clientes.remove(seleccionado);
        if (clienteEnEdicion == seleccionado) {
            limpiarCampos();
        }
        mostrarMensaje("Cliente eliminado correctamente.", false);
    }

    @FXML
    private void cancelarEdicion() {
        limpiarCampos();
        mostrarMensaje("Edición cancelada.", false);
    }

    private void limpiarCampos() {
        clienteEnEdicion = null;
        nombreField.clear();
        documentoField.clear();
        telefonoField.clear();
        correoField.clear();
        edadField.clear();
        guardarButton.setText("Registrar cliente");
        cancelarButton.setDisable(true);
    }

    private void actualizarBotonesSeleccion(Cliente seleccionado) {
        boolean haySeleccion = seleccionado != null;
        editarButton.setDisable(!haySeleccion);
        eliminarButton.setDisable(!haySeleccion);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError
                ? "-fx-text-fill: #b91c1c;"
                : "-fx-text-fill: #15803d;");
    }
}
