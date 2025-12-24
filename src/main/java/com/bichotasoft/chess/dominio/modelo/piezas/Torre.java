package main.java.com.bichotasoft.chess.dominio.modelo.piezas;
import main.java.com.bichotasoft.chess.dominio.modelo.*;

public class Torre extends Pieza {

    private boolean esPrimerMovimiento = true;

    public Torre() {
        super ("♜", 7);
    }
    public Torre(int Id , String color) {
        super ("♜", 7, Id, color);
    }

    @Override
    public boolean validarMovimiento(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero) {
        return (esMovimientoVerticalValido(coordenadasOrigen,coordenadasDestino, tablero, obtenerMaxCasillasMover()) ||
                esMovimientoHorizontalValido(coordenadasOrigen,coordenadasDestino, tablero, obtenerMaxCasillasMover()));
    }

    public void asignarEsPrimerMovimiento(boolean esPrimerMovimiento) {
        this.esPrimerMovimiento = esPrimerMovimiento;
    }

    public boolean obtenerEsPrimerMovimiento() {
        return this.esPrimerMovimiento;
    }
}