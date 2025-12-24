package main.java.com.bichotasoft.chess.dominio.modelo;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.*;

public class Tablero {

    private Pieza[][] tablero = new Pieza[8][8];
    private Pieza[][] tableroClon = new Pieza[8][8];
    private int contadorTurnos = 1;

    public Tablero() {
    }

    public void rellenarTablero(Jugador jugador1, Jugador jugador2) {
        Pieza[] piezasJ1 = jugador1.obtenerPiezas();
        Pieza[] piezasJ2 = jugador2.obtenerPiezas();
        for (int fila = 0; fila <= 1; fila++) {
            for (int columna = 0; columna <= 7; columna++) {
                if (fila == 0) {
                    this.tablero[fila][columna] = piezasJ1[columna];
                } else {
                    int nueva = columna + 8;
                    this.tablero[fila][columna] = piezasJ1[nueva];
                }
            }
        }
        for (int fila = 7; fila >= 6; fila--) {
            for (int columna = 0; columna <= 7; columna++) {
                if (fila == 7) {
                    this.tablero[fila][columna] = piezasJ2[columna];
                } else {
                    int nueva = columna + 8;
                    this.tablero[fila][columna] = piezasJ2[nueva];
                }
            }
        }
    }

    public void mostrarTablero() {
        System.out.println("    A B C D E F G H");
        System.out.println("  +-----------------+");
        for (int fila = 0; fila < 8; fila++) {
            System.out.print((8 - fila) + " | ");
            for (int columna = 0; columna < 8; columna++) {
                if (tablero[fila][columna] != null) {
                    System.out.print(this.tablero[fila][columna].obtenerSimbolo() + " ");
                } else {
                    if ((fila + columna) % 2 == 0) {
                        System.out.print("▭ ");
                    } else {
                        System.out.print("▮ ");
                    }
                }
            }
            System.out.println("| " + (8 - fila));
        }
        System.out.println("  +-----------------+");
        System.out.println("    A B C D E F G H\n");
    }

    public void moverPieza(int filaInicial, int columnaInicial, int filaFinal, int columnaFinal) {
        this.clonarTablero();
        this.obtenerPieza(filaInicial, columnaInicial).asignarEsPrimerMovimiento(false);
        Pieza pieza = obtenerPieza(filaInicial, columnaInicial);
        if ((filaFinal == 0 || filaFinal == 7) && pieza instanceof Peon){
            Pieza piezaPromocionada = pieza.promocionarPeon(pieza);
            this.tablero[filaFinal][columnaFinal] = piezaPromocionada;
            this.tablero[filaInicial][columnaInicial] = null;
        } else if(pieza instanceof Peon){
            Peon peon = (Peon) pieza;
            if(peon.esCapturaAlPasoValida(new int[]{filaInicial, columnaInicial}, new int[]{filaFinal, columnaFinal}, this)){
                this.tablero[filaFinal][columnaFinal] = this.tablero[filaInicial][columnaInicial];
                this.tablero[filaInicial][columnaInicial] = null;
                this.tablero[filaInicial][columnaFinal] = null;
            }else{
                this.tablero[filaFinal][columnaFinal] = this.tablero[filaInicial][columnaInicial];
                this.tablero[filaInicial][columnaInicial] = null;
            }
        }else {
            this.tablero[filaFinal][columnaFinal] = this.tablero[filaInicial][columnaInicial];
            this.tablero[filaInicial][columnaInicial] = null;
        }
    }

    public Pieza obtenerPieza(int fila, int columna) {
        return this.tablero[fila][columna];
    }
    public Pieza clonarPieza(int[] coordenada , Pieza piezaOriginal, int tablero){
        Pieza pieza;
        if(tablero == 0){
            pieza = this.tablero[coordenada[0]][coordenada[1]]; //tablero original
        }else{
            pieza = this.tableroClon[coordenada[0]][coordenada[1]]; //tablero clon
        }

        Pieza piezaclon = null;
        if(pieza instanceof Rey){
             piezaclon = new Rey(piezaOriginal.obtenerId() , piezaOriginal.obtenerColorPieza());
             boolean primerMovimiento = pieza.obtenerEsPrimerMovimiento();
             piezaclon.asignarEsPrimerMovimiento(primerMovimiento);

        }else if(pieza instanceof Dama){
            piezaclon = new Dama(piezaOriginal.obtenerId() , piezaOriginal.obtenerColorPieza());
            boolean primerMovimiento = pieza.obtenerEsPrimerMovimiento();
            piezaclon.asignarEsPrimerMovimiento(primerMovimiento);

        }else if(pieza instanceof Peon){
            piezaclon = new Peon(piezaOriginal.obtenerId() , piezaOriginal.obtenerColorPieza());
            boolean primerMovimiento = pieza.obtenerEsPrimerMovimiento();
            piezaclon.asignarEsPrimerMovimiento(primerMovimiento);

        }else if(pieza instanceof Alfil){
            piezaclon = new Alfil(piezaOriginal.obtenerId() , piezaOriginal.obtenerColorPieza());
            boolean primerMovimiento = pieza.obtenerEsPrimerMovimiento();
            piezaclon.asignarEsPrimerMovimiento(primerMovimiento);

        }else if(pieza instanceof Caballo){
            piezaclon = new Caballo(piezaOriginal.obtenerId() , piezaOriginal.obtenerColorPieza());
            boolean primerMovimiento = pieza.obtenerEsPrimerMovimiento();
            piezaclon.asignarEsPrimerMovimiento(primerMovimiento);

        }else if (pieza instanceof Torre){
            piezaclon = new Torre(piezaOriginal.obtenerId() , piezaOriginal.obtenerColorPieza());
            boolean primerMovimiento = pieza.obtenerEsPrimerMovimiento();
            piezaclon.asignarEsPrimerMovimiento(primerMovimiento);

        }
        return piezaclon;
    }

    public void clonarTablero() {

        for (int fila = 0; fila < this.tablero.length; fila++) {
            for (int columna = 0; columna < this.tablero[fila].length; columna++) {
                if (this.tablero[fila][columna] != null) {
                    Pieza pieza = this.obtenerPieza(fila, columna);
                    this.tableroClon[fila][columna] = clonarPieza(new int[]{fila, columna}, pieza, 0);
                } else {
                    this.tableroClon[fila][columna] = null;
                }
            }
        }
    }

    public void retrocederMovimiento(){
        for (int fila = 0; fila < this.tablero.length; fila++) {
            for (int columna = 0; columna < this.tablero[fila].length; columna++) {
                if (this.tableroClon[fila][columna] != null) {
                    Pieza pieza = this.tableroClon[fila][columna];
                    this.tablero[fila][columna] = clonarPieza(new int[]{fila, columna}, pieza,1);
                } else {
                    this.tablero[fila][columna] = null;
                }
            }
        }

    }

    public void retrocederJugada(int filaInicial, int columnaInicial, int filaFinal, int columnaFinal , Pieza pieza){
        this.tablero[filaInicial][columnaInicial] = this.tablero[filaFinal][columnaFinal];
        this.tablero[filaFinal][columnaFinal] = pieza;
    }


    public int[] obtenerPosicionRey(Jugador jugador) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = this.tablero[fila][columna];
                if (pieza instanceof Rey && pieza.obtenerId() == jugador.obtenerId()) {
                    return new int[] {fila, columna};
                }
            }
        }
        return null;
    }
    public boolean esCasillaDentroDelTablero(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 8;
    }

    public int obtenerTurnoActual() {
        return this.contadorTurnos;
    }

    public void aumentarTurno(){
        this.contadorTurnos++;
    }

    public void reiniciarTurnos(){
        this.contadorTurnos = 0;
    }
}
