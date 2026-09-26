package viewController;

import configuracion.ConfiguracionRentaCar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConfiguracionViewController {

    @FXML
    private TextField nombreComercialField;
    @FXML
    private TextField nitField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField correoField;
    @FXML
    private TextField paginaWebField;
    @FXML
    private Label mensajeLabel;

    private final ConfiguracionRentaCar configuracion =
            ConfiguracionRentaCar.getInstancia();

    @FXML
    private void initialize() {
        nombreComercialField.setText(valor(configuracion.getNombreComercial()));
        nitField.setText(valor(configuracion.getNit()));
        direccionField.setText(valor(configuracion.getDireccion()));
        telefonoField.setText(valor(configuracion.getTelefono()));
        correoField.setText(valor(configuracion.getCorreo()));
        paginaWebField.setText(valor(configuracion.getPaginaWeb()));
    }

    @FXML
    private void guardarConfiguracion() {
        String nombreComercial = nombreComercialField.getText().trim();
        String nit = nitField.getText().trim();
        String direccion = direccionField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String correo = correoField.getText().trim();
        String paginaWeb = paginaWebField.getText().trim();

        if (nombreComercial.isEmpty() || nit.isEmpty() || direccion.isEmpty()
                || telefono.isEmpty() || correo.isEmpty()) {
            mostrarMensaje("Completa los campos obligatorios.", true);
            return;
        }
        if (!correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            mostrarMensaje("Ingresa un correo electrónico válido.", true);
            return;
        }

        configuracion.configurar(
                nombreComercial,
                nit,
                direccion,
                telefono,
                correo,
                paginaWeb);
        mostrarMensaje("Configuración guardada correctamente.", false);
    }

    private String valor(String texto) {
        return texto == null ? "" : texto;
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError
                ? "-fx-text-fill: #b91c1c;"
                : "-fx-text-fill: #15803d;");
    }
}
