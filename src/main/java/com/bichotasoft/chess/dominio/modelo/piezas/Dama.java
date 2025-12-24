package main.java.com.bichotasoft.chess.dominio.modelo.piezas;
import main.java.com.bichotasoft.chess.dominio.modelo.*;

public class Dama extends Pieza {

    private boolean esPrimerMovimiento = true;

    public Dama() {
        super ("♛", 7);
    }
    public Dama(int Id , String color) {
        super ("♛", 7, Id, color);
    }

    @Override
    public boolean validarMovimiento(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero) {
        return (esMovimientoVerticalValido(coordenadasOrigen,coordenadasDestino, tablero, obtenerMaxCasillasMover()) ||
                esMovimientoDiagonalValido(coordenadasOrigen,coordenadasDestino, tablero, obtenerMaxCasillasMover()) ||
                esMovimientoHorizontalValido(coordenadasOrigen,coordenadasDestino, tablero, obtenerMaxCasillasMover()));
    }

    public void asignarEsPrimerMovimiento(boolean esPrimerMovimiento) {
        this.esPrimerMovimiento = esPrimerMovimiento;
    }

    public boolean obtenerEsPrimerMovimiento() {
        return this.esPrimerMovimiento;
    }
}
