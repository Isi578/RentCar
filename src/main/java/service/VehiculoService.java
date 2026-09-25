package service;

import model.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class VehiculoService {

    private final List<Vehiculo> vehiculos;

    public VehiculoService() {
        this.vehiculos = new ArrayList<>();
    }

    public void registrarVehiculo(Vehiculo vehiculo) {

        if (vehiculo == null) {
            throw new IllegalArgumentException(
                    "El vehículo no puede ser nulo."
            );
        }

        if (buscarPorPlaca(vehiculo.getPlaca()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un vehículo con la placa: "
                            + vehiculo.getPlaca()
            );
        }

        vehiculos.add(vehiculo);
    }

    public List<Vehiculo> obtenerVehiculos() {
        return new ArrayList<>(vehiculos);
    }

    public Vehiculo buscarPorPlaca(String placa) {

        if (placa == null || placa.isBlank()) {
            return null;
        }

        for (Vehiculo vehiculo : vehiculos) {

            if (vehiculo != null
                    && placa.equalsIgnoreCase(
                    vehiculo.getPlaca())) {

                return vehiculo;
            }
        }

        return null;
    }

    public boolean existeVehiculo(String placa) {
        return buscarPorPlaca(placa) != null;
    }
}
