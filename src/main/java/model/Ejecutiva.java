package model;

public class Ejecutiva extends ModalidadAlquiler {

    public Ejecutiva(
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
        return new Ejecutiva(
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