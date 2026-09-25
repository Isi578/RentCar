package model;

public class Premium extends ModalidadAlquiler {

    private String tipoCobertura;
    private int conductoresAdicionalesPermitidos;
    private String caracteristicasEspeciales;

    public Premium(
            String codigo,
            String nombre,
            String descripcion,
            int duracionMinimaDias,
            double precioDiario,
            EstadoModalidad estadoModalidad,
            String beneficios,
            String tipoCobertura,
            int conductoresAdicionalesPermitidos,
            String caracteristicasEspeciales) {

        super(
                codigo,
                nombre,
                descripcion,
                duracionMinimaDias,
                precioDiario,
                estadoModalidad,
                beneficios
        );

        this.tipoCobertura = tipoCobertura;
        this.conductoresAdicionalesPermitidos = conductoresAdicionalesPermitidos;
        this.caracteristicasEspeciales = caracteristicasEspeciales;
    }

    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public int getConductoresAdicionalesPermitidos() {
        return conductoresAdicionalesPermitidos;
    }

    public void setConductoresAdicionalesPermitidos(
            int conductoresAdicionalesPermitidos) {

        this.conductoresAdicionalesPermitidos =
                conductoresAdicionalesPermitidos;
    }

    public String getCaracteristicasEspeciales() {
        return caracteristicasEspeciales;
    }

    public void setCaracteristicasEspeciales(
            String caracteristicasEspeciales) {

        this.caracteristicasEspeciales =
                caracteristicasEspeciales;
    }

    @Override
    public ModalidadAlquiler clonar() {
        return new Premium(
                getCodigo(),
                getNombre(),
                getDescripcion(),
                getDuracionMinimaDias(),
                getPrecioDiario(),
                getEstadoModalidad(),
                getBeneficios(),
                getTipoCobertura(),
                getConductoresAdicionalesPermitidos(),
                getCaracteristicasEspeciales()
        );
    }
}
