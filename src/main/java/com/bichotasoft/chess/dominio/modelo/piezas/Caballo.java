package main.java.com.bichotasoft.chess.dominio.modelo.piezas;
import main.java.com.bichotasoft.chess.dominio.modelo.*;

public class Caballo extends Pieza {

    private boolean esPrimerMovimiento = true;

    public Caballo( int Id , String color) {
        super ("♞" , Id , color);
    }

    @Override
    public boolean validarMovimiento(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero) {
        return esMovimientoEnLValido(coordenadasOrigen, coordenadasDestino, tablero);
    }

    public void asignarEsPrimerMovimiento(boolean esPrimerMovimiento) {
        this.esPrimerMovimiento = esPrimerMovimiento;
    }

    public boolean obtenerEsPrimerMovimiento() {
        return this.esPrimerMovimiento;
    }
}