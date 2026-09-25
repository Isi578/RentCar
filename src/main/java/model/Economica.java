package model;

public class Economica extends ModalidadAlquiler {

    public Economica(
            String codigo,
            String nombre,
            String descripcion,
            int duracionMinimaDias,
            double precioDiario,
            EstadoModalidad estadoModalidad,
            String beneficios) {

        super(
                codigo,
                nombre,
                descripcion,
                duracionMinimaDias,
                precioDiario,
                estadoModalidad,
                beneficios
        );
    }

    @Override
    public ModalidadAlquiler clonar() {
        return new Economica(
                getCodigo(),
                getNombre(),
                getDescripcion(),
                getDuracionMinimaDias(),
                getPrecioDiario(),
                getEstadoModalidad(),
                getBeneficios()
        );
    }
}