package model;

public class Economica extends ModalidadAlquiler {

    public Economica(String codigo, String nombre, String descripcion, int duracionMinimaDias, double valorDiario, EstadoModalidad estadoModalidad, String beneficiario) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estadoModalidad, beneficiario);
    }
}
