package model;

public class Premium extends ModalidadAlquiler{

    private String tipoCobertura;
    private int conductoresAdicionalesPermitidos;
    private String caracteristicasEspeciales;


    public Premium (String codigo, String nombre, String descripcion, int duracionMinimaDias, double valorDiario, EstadoModalidad estadoModalidad, String beneficiario) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estadoModalidad, beneficiario);
    }
}
