package model;

import java.util.List;

public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private double tarifaDiaria;
    private List<Reserva> reservas;

public Vehiculo(String placa, String marca, String modelo, int anio, double tarifaDiaria) {
    this.placa = placa;
    this.marca = marca;
    this.modelo = modelo;
    this.anio = anio;
    this.tarifaDiaria = tarifaDiaria;
}

public String getPlaca() {
    return placa;
}
public void setPlaca(String placa) {
    this.placa = placa;
}

public String getMarca() {
    return marca;
}
public void setMarca(String marca) {
    this.marca = marca;
}

public String getModelo() {
    return modelo;
}
public void setModelo(String modelo) {
    this.modelo = modelo;
}

public int getAnio() {
    return anio;
}
public void setAnio(int anio) {
    this.anio = anio;
}

public double getTarifaDiaria() {
    return tarifaDiaria;
}
public void setTarifaDiaria(double tarifaDiaria) {
    this.tarifaDiaria = tarifaDiaria;
}

}
