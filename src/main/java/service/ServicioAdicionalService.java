package service;

import model.ServicioAdicional;

import java.util.ArrayList;
import java.util.List;

public class ServicioAdicionalService {

    private final List<ServicioAdicional> servicios;

    public ServicioAdicionalService() {
        this.servicios = new ArrayList<>();
    }

    public void registrarServicio(
            ServicioAdicional servicio) {

        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio no puede ser nulo."
            );
        }

        if (buscarPorCodigo(servicio.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un servicio con el código: "
                            + servicio.getCodigo()
            );
        }

        servicios.add(servicio);
    }

    public List<ServicioAdicional> obtenerServicios() {
        return new ArrayList<>(servicios);
    }

    public ServicioAdicional buscarPorCodigo(
            String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (ServicioAdicional servicio : servicios) {

            if (servicio != null
                    && codigo.equals(servicio.getCodigo())) {

                return servicio;
            }
        }

        return null;
    }

    public List<ServicioAdicional>
    obtenerServiciosDisponibles() {

        List<ServicioAdicional> disponibles =
                new ArrayList<>();

        for (ServicioAdicional servicio : servicios) {

            if (servicio != null
                    && servicio.estaDisponible()) {

                disponibles.add(servicio);
            }
        }

        return disponibles;
    }
}