package configuracion;

public class ConfiguracionRentaCar {

    private static ConfiguracionRentaCar instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correo;
    private String paginaWeb;

    private ConfiguracionRentaCar() {
    }

    public static ConfiguracionRentaCar getInstancia() {

        if (instancia == null) {
            instancia = new ConfiguracionRentaCar();
        }

        return instancia;
    }

    public void configurar(
            String nombreComercial,
            String nit,
            String direccion,
            String telefono,
            String correo,
            String paginaWeb) {

        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.paginaWeb = paginaWeb;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
}