package model;

public abstract class ModalidadAlquiler {

    private String codigo;
    private String nombre;
    private String descripcion;
    private int duracionMinimaDias;
    private double precioDiario;
    private EstadoModalidad estadoModalidad;
    private String beneficios;

    public ModalidadAlquiler(
            String codigo,
            String nombre,
            String descripcion,
            int duracionMinimaDias,
            double precioDiario,
            EstadoModalidad estadoModalidad,
            String beneficios) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinimaDias = duracionMinimaDias;
        this.precioDiario = precioDiario;
        this.estadoModalidad = estadoModalidad;
        this.beneficios = beneficios;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMinimaDias() {
        return duracionMinimaDias;
    }

    public void setDuracionMinimaDias(int duracionMinimaDias) {
        this.duracionMinimaDias = duracionMinimaDias;
    }

    public double getPrecioDiario() {
        return precioDiario;
    }

    public void setPrecioDiario(double precioDiario) {
        this.precioDiario = precioDiario;
    }

    public EstadoModalidad getEstadoModalidad() {
        return estadoModalidad;
    }

    public void setEstadoModalidad(EstadoModalidad estadoModalidad) {
        this.estadoModalidad = estadoModalidad;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public double calcularCosto(int dias) {
        if (dias < duracionMinimaDias) {
            throw new IllegalArgumentException(
                    "La duración no puede ser menor a "
                            + duracionMinimaDias + " días."
            );
        }

        return dias * precioDiario;
    }

    public abstract ModalidadAlquiler clonar();
}
