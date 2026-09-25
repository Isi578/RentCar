package service;

import model.Cliente;

import java.util.List;

public class ClienteService {

    public Cliente buscarPorTelefono(
            List<Cliente> clientes,
            String telefono) {

        if (clientes == null) {
            throw new IllegalArgumentException(
                    "La lista de clientes no puede ser nula."
            );
        }

        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException(
                    "El teléfono es obligatorio."
            );
        }

        String telefonoBuscado = telefono.trim();

        for (Cliente cliente : clientes) {

            if (cliente == null) {
                continue;
            }

            String telefonoCliente = cliente.getTelefono();

            if (telefonoCliente != null
                    && telefonoCliente.trim().equals(telefonoBuscado)) {

                return cliente;
            }
        }

        return null;
    }
}