package service;

import model.ModalidadAlquiler;

import java.util.ArrayList;
import java.util.List;

public class ModalidadService {

    private final List<ModalidadAlquiler> modalidades;

    public ModalidadService() {
        this.modalidades = new ArrayList<>();
    }

    public void registrarModalidad(
            ModalidadAlquiler modalidad) {

        if (modalidad == null) {
            throw new IllegalArgumentException(
                    "La modalidad no puede ser nula."
            );
        }

        if (buscarPorCodigo(modalidad.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una modalidad con el código: "
                            + modalidad.getCodigo()
            );
        }

        modalidades.add(modalidad);
    }

    public List<ModalidadAlquiler> obtenerModalidades() {
        return new ArrayList<>(modalidades);
    }

    public ModalidadAlquiler buscarPorCodigo(
            String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (ModalidadAlquiler modalidad : modalidades) {

            if (modalidad != null
                    && codigo.equals(modalidad.getCodigo())) {

                return modalidad;
            }
        }

        return null;
    }

    public boolean existeModalidad(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }
}
