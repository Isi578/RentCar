package service;

public class NumeroService {

    public boolean esNumeroPerfecto(long numero) {

        if (numero <= 1) {
            return false;
        }

        long sumaDivisores = 1;

        for (long divisor = 2;
             divisor <= numero / divisor;
             divisor++) {

            if (numero % divisor == 0) {

                sumaDivisores += divisor;

                long otroDivisor = numero / divisor;

                if (otroDivisor != divisor) {
                    sumaDivisores += otroDivisor;
                }
            }
        }

        return sumaDivisores == numero;
    }

    public boolean esTelefonoNumeroPerfecto(String telefono) {

        if (telefono == null || telefono.isBlank()) {
            return false;
        }

        try {

            long numero = Long.parseLong(telefono.trim());

            return esNumeroPerfecto(numero);

        } catch (NumberFormatException e) {

            return false;
        }
    }
}