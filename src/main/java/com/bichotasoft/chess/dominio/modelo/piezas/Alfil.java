package main.java.com.bichotasoft.chess.dominio.modelo.piezas;
import main.java.com.bichotasoft.chess.dominio.modelo.*;

public class Alfil extends Pieza {

    private boolean esPrimerMovimiento = true;

    public Alfil() {
        super ("♝",7);
    }
    public Alfil(int Id , String color) {
        super ("♝",7, Id, color);
    }

    @Override
    public boolean validarMovimiento(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero) {
        return esMovimientoDiagonalValido(coordenadasOrigen, coordenadasDestino, tablero, obtenerMaxCasillasMover());
    }

    public void asignarEsPrimerMovimiento(boolean esPrimerMovimiento) {
        this.esPrimerMovimiento = esPrimerMovimiento;
    }

    public boolean obtenerEsPrimerMovimiento() {
        return this.esPrimerMovimiento;
    }
}
