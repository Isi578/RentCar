package factory;

import model.Economica;
import model.Ejecutiva;
import model.EstadoModalidad;
import model.ModalidadAlquiler;
import model.Premium;

public class ModalidadFactory {

    public static ModalidadAlquiler crear(
            String tipo,
            String codigo,
            String nombre,
            String descripcion,
            int duracionMinimaDias,
            double precioDiario,
            EstadoModalidad estadoModalidad,
            String beneficios) {

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException(
                    "El tipo de modalidad es obligatorio."
            );
        }

        switch (tipo.trim().toUpperCase()) {

            case "ECONOMICA":
                return new Economica(
                        codigo,
                        nombre,
                        descripcion,
                        duracionMinimaDias,
                        precioDiario,
                        estadoModalidad,
                        beneficios
                );

            case "EJECUTIVA":
                return new Ejecutiva(
                        codigo,
                        nombre,
                        descripcion,
                        duracionMinimaDias,
                        precioDiario,
                        estadoModalidad,
                        beneficios
                );

            case "PREMIUM":
                return new Premium(
                        codigo,
                        nombre,
                        descripcion,
                        duracionMinimaDias,
                        precioDiario,
                        estadoModalidad,
                        beneficios,
                        "Cobertura completa",
                        0,
                        "Servicio premium"
                );

            default:
                throw new IllegalArgumentException(
                        "Tipo de modalidad no válido: " + tipo
                );
        }
    }
}