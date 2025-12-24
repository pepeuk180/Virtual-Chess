package main.java.com.bichotasoft.chess.dominio.modelo.piezas;

import main.java.com.bichotasoft.chess.dominio.modelo.Pieza;
import main.java.com.bichotasoft.chess.dominio.modelo.Tablero;

public class Peon extends Pieza {

    private boolean esPrimerMovimiento = true;
    private int casillasPrimerMovimiento = 0;
    private int turnoPrimerMovimiento = 0;

    public Peon() {
        super("♟", 7);
    }

    public Peon(int Id, String color) {
        super("♟", 7, Id, color);
    }

    @Override
    public boolean validarMovimiento(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero) {
        boolean esValido = false;

        if(esMovimientoPeonValido(coordenadasOrigen, coordenadasDestino, tablero))    {
            esValido = true;
        } else if (esCapturaAlPasoValida(coordenadasOrigen, coordenadasDestino, tablero)) {
                esValido = true;
        }
        return esValido;
    }

    public void asignarEsPrimerMovimiento(boolean esPrimerMovimiento) {
        this.esPrimerMovimiento = esPrimerMovimiento;
    }

    public boolean obtenerEsPrimerMovimiento() {
        return this.esPrimerMovimiento;
    }

    public boolean esCapturaAlPasoValida(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero) {
        int filaOrigen = coordenadasOrigen[0];
        int columnaOrigen = coordenadasOrigen[1];
        int filaDestino = coordenadasDestino[0];
        int columnaDestino = coordenadasDestino[1];
        boolean esValido = false;

        if (tablero.obtenerPieza(filaDestino, columnaDestino) == null) {
            if (Math.abs(filaDestino - filaOrigen) == Math.abs(columnaDestino - columnaOrigen)) {
                int casillasDistancia = Math.abs(filaDestino - filaOrigen);
                Pieza piezaCapturada = tablero.obtenerPieza(filaOrigen, columnaDestino);
                if (piezaCapturada != null && piezaCapturada instanceof Peon && casillasDistancia == 1 && piezaCapturada.obtenerId() != this.obtenerId()) {
                    Peon peon = (Peon) piezaCapturada;
                    if (peon.obtenerCasillasPrimerMovimiento() == 2 && (tablero.obtenerTurnoActual() - peon.obtenerTurnoPrimerMovimiento()) == 1)
                        esValido = true;
                }
            }
        }

        return esValido;
    }

    public void asignarCasillasPrimerMovimiento(int numeroCasillas) {
        this.casillasPrimerMovimiento = numeroCasillas;
    }

    public void asignarTurnoPrimerMovimiento(int numeroTurno) {
        this.turnoPrimerMovimiento = numeroTurno;
    }

    public int obtenerCasillasPrimerMovimiento() {
        return this.casillasPrimerMovimiento;
    }

    public int obtenerTurnoPrimerMovimiento() {
        return this.turnoPrimerMovimiento;
    }
}
