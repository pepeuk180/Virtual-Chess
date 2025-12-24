package main.java.com.bichotasoft.chess.controlador;
import main.java.com.bichotasoft.chess.configuracion.ConfiguracionJuego;
import main.java.com.bichotasoft.chess.dominio.modelo.*;
import main.java.com.bichotasoft.chess.dominio.modelo.Tablero;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.*;
import java.util.Scanner;

public class ControladorAjedrez {
    private final Scanner teclado = new Scanner(System.in);
    private Tablero tablero;
    private Jugador jugador1 = new Jugador(new String []{"\u001B[30m", "Negro"}, 1);
    private Jugador jugador2 = new Jugador(new String []{"\u001B[37m", "Blanco"},2);
    private String[][] colores = {{"vacio", "vacio"}, {"\u001B[37m", "Negro"}, //[1][0] = codigo ; [1][1] = nombre
            {"\u001B[31m", "Rojo"}, {"\u001B[32m", "Verde"}, {"\u001B[33m", "Amarillo"}, {"\u001B[34m", "Azul"}, {"\u001B[35m", "Magenta"}, {"\u001B[36m", "Cian"}, {"\u001B[37m", "Blanco"}};

    public ControladorAjedrez() {

    }

    public void cargarJuego() {
        this.crearTablero();
        this.rellenarTablero();
    }

    public void Jugar() {
        boolean finJuego = false;
        this.cargarJuego();
        limpiarTerminal();
        while (!finJuego) {
            this.tablero.mostrarTablero();
            Jugador jugadorActual = (this.tablero.obtenerTurnoActual() % 2 == 0) ? jugador1 : jugador2;
            Jugador jugadorEnemigo = (jugadorActual == jugador1) ? jugador2 : jugador1;
            String colorJugador = jugadorActual.obtenerCodigoColor();
            String mensaje = "";
            jugadorActual.mostrarTiempoActual();
            jugadorActual.iniciarTiempo();
            if (jugadorActual.obtenerSegundosRestantes() <= 0){
                System.out.println(jugadorEnemigo.obtenerCodigoColor()+ "SE TE ACABO EL TIEMPO, EL JUGADOR " + jugadorEnemigo.obtenerNombreColor() + " GANA" + ConfiguracionJuego.COLOR_RESET);
                finJuego = true;
                continue;
            }
            if (esJaqueMate(jugadorActual)) {
                System.out.println(jugadorEnemigo.obtenerCodigoColor() + jugadorEnemigo.obtenerNombreColor() + ConfiguracionJuego.JAQUE_MATE);
                finJuego = true;
                continue;
            }
            if (esJaque(jugadorActual)) {
                System.out.println(ConfiguracionJuego.NOTIFICACION_JAQUE_1 + jugadorActual.obtenerNombreColor() + ConfiguracionJuego.NOTIFICACION_JAQUE_2);
                mensaje = ConfiguracionJuego.ERROR_JAQUE_1+ jugadorActual.obtenerNombreColor() +ConfiguracionJuego.ERROR_JAQUE_2;
            }else{
                System.out.println(colorJugador + ConfiguracionJuego.TURNO_JUGADOR + jugadorActual.obtenerNombreColor() + ConfiguracionJuego.COLOR_RESET);
                mensaje = ConfiguracionJuego.MOVIMIENTO_INVALIDO_JAQUE_1;
            }

            boolean puedePasarTurno = true;
            while (puedePasarTurno) {

                int[] coordenadaOrigen = new int[2];
                boolean seAcordaronTablas = false;
                boolean continuarJuego = true;

                while (continuarJuego) {
                    coordenadaOrigen = pedirCoordenadas(ConfiguracionJuego.ORIGEN);
                    if (coordenadaOrigen[0] == 10 && coordenadaOrigen[1] == 10) {
                        seAcordaronTablas = solicitarTablas(jugadorActual);
                        continuarJuego = !seAcordaronTablas;
                    } else {
                        continuarJuego = false;
                    }
                }
                if (seAcordaronTablas) {
                    System.out.println(ConfiguracionJuego.TABLAS_EMPATE_2);
                    finJuego = true;
                    puedePasarTurno = false;
                    continue;
                }
                int[] coordenadaDestino = pedirCoordenadas(ConfiguracionJuego.DESTINO);
                Pieza piezaOrigen = this.tablero.obtenerPieza(coordenadaOrigen[0], coordenadaOrigen[1]);
                boolean esMismaFila = (coordenadaOrigen[0] == coordenadaDestino[0]); //comparación para el enrroque
                if (!esJaque(jugadorActual)) {
                    if ((piezaOrigen instanceof Rey) && (piezaOrigen.obtenerEsPrimerMovimiento()) && (esMismaFila) && ((coordenadaDestino[1] == 2) || coordenadaDestino[1] == 6)) {
                        String tipoEnroque = this.obtenerTipoEnroque(coordenadaDestino);
                        if (this.sePuedeHacerEnroque(tipoEnroque, jugadorActual)) {
                            this.moverPiezasEnroque(tipoEnroque, jugadorActual);
                            this.tablero.mostrarTablero();
                            puedePasarTurno = false;
                        }
                        continue;
                    }
                }
                if (moverPieza(coordenadaOrigen, coordenadaDestino, colorJugador)) {
                    if (!esJaque(jugadorActual)) {
                        puedePasarTurno = false;
                    } else {
                        this.tablero.retrocederMovimiento();
                        this.mostrarTablero();
                        System.out.println(mensaje);
                        System.out.println(colorJugador + ConfiguracionJuego.TURNO_JUGADOR + jugadorActual.obtenerNombreColor() + ConfiguracionJuego.COLOR_RESET);
                    }
                }
            }
            if(hayTablas(jugadorActual, jugadorEnemigo)) {
                System.out.println(ConfiguracionJuego.TABLAS_EMPATE_1);
                finJuego = true;
            }
            jugadorActual.pausarTiempo();
            this.tablero.aumentarTurno();
            limpiarTerminal();
        }
        this.tablero.reiniciarTurnos();
        this.jugador1.reiniciarPiezas();
        this.jugador1.reiniciarTiempo();
        this.jugador2.reiniciarPiezas();
        this.jugador2.reiniciarTiempo();
        limpiarTerminal();
    }

    public void crearTablero() {
        this.tablero = new Tablero();
    }

    public void seleccionarColores() {
        limpiarTerminal();
        this.jugador1.asignarColor(solicitarColor(ConfiguracionJuego.JUGADOR_1));
        limpiarTerminal();
        this.jugador2.asignarColor(solicitarColor(ConfiguracionJuego.JUGADOR_2 ));
        limpiarTerminal();
    }

    public void rellenarTablero() {
        this.tablero.rellenarTablero(this.jugador1, this.jugador2);
    }

    public String[] solicitarColor(String jugador) {
        int opcion = 0;
        boolean enBucle = true;
        while (enBucle){
            try{
                mostrarColores();
                System.out.print(ConfiguracionJuego.SELECCIONA_OPCION);
                opcion = teclado.nextInt();
                if (opcion < colores.length && opcion >= 0){
                    enBucle = false;
                }else {
                    System.out.println(ConfiguracionJuego.ERROR_VALIDADOR_NUMEROS);
                }
            } catch (Exception e) {
                System.out.println(ConfiguracionJuego.ERROR_VALIDADOR_NUMEROS);
                teclado.nextLine();
            }
        }

        String[] color = {colores[opcion][0], colores[opcion][1]};
        this.eliminarColor(opcion);
        return color;
    }

    public void eliminarColor(int opcion) {
        String[][] nuevosColores = new String[this.colores.length - 1][this.colores[0].length];
        for (int i = 0, j = 0; i < this.colores.length; i++) {
            if (i != opcion) {
                nuevosColores[j++] = this.colores[i];
            }
        }
        this.colores = nuevosColores;
    }

    public void mostrarColores() {
        System.out.println(ConfiguracionJuego.CABECERA_SELECCION_COLOR);
        for (int i = 1; i < colores.length; i++) {
            System.out.println(i + ":" + this.colores[i][1]);
        }
    }

    public void mostrarTablero() {
        this.tablero.mostrarTablero();
    }

    private boolean esCasillaConPieza(int fila, int columna) {
        return (this.tablero.obtenerPieza(fila, columna) != null);
    }

    public boolean esEntradaValida(String coordenada, String direccion) {
        boolean verificado = false;
        int tamanioCoordenada = 2;
        if(coordenada.equals("1/2")) {
            verificado = true;
        }else if (coordenada.length() == tamanioCoordenada) {
            char columna = coordenada.charAt(0);
            char fila = coordenada.charAt(1);
            if ((columna >= 'A' && columna <= 'H') && (fila >= '1' && fila <= '8')) {
                verificado = true;
            } else {
                System.out.println(ConfiguracionJuego.ERROR_INDICES_COORDENADAS);
            }
        } else {
            System.out.println(ConfiguracionJuego.ERROR_TAMANO_COORDENADAS);
        }
        if(verificado && !coordenada.equals("1/2")) {
            int [] coordenadas = convertirStringArray(coordenada);
            if(tablero.obtenerPieza(coordenadas[0], coordenadas[1]) == null && direccion.equals(ConfiguracionJuego.ORIGEN)) {
                System.out.println(ConfiguracionJuego.ERROR_COORDENADA_VACIA);
                verificado = false;
            }
        }

        return verificado;
    }

    public boolean moverPieza(int[] coordenadasOrigen, int[] coordenadasDestino, String colorJugador) {
        boolean piezaMovida = false;
        int filaInicial = coordenadasOrigen[0];
        int columnaInicial = coordenadasOrigen[1];
        int filaDestino = coordenadasDestino[0];
        int columnaDestino = coordenadasDestino[1];
        if (esCasillaConPieza(filaInicial, columnaInicial)) {
            Pieza pieza = this.tablero.obtenerPieza(filaInicial, columnaInicial);
            if (piezaJugador(pieza, colorJugador)) {
                if (pieza.validarMovimiento(coordenadasOrigen, coordenadasDestino, this.tablero)) {
                    this.tablero.moverPieza(filaInicial, columnaInicial, filaDestino, columnaDestino);
                    piezaMovida = true;
                } else {
                    System.out.println(ConfiguracionJuego.ERROR_MOVIMIENTO_INVALIDO);
                }
            } else {
                System.out.println(ConfiguracionJuego.ERROR_SELECCION_PIEZA);
            }
        }
        return piezaMovida;
    }

    public boolean piezaJugador(Pieza pieza, String colorJugador) {
        return pieza.obtenerColorPieza().equals(colorJugador) ;
    }

    public String leerCoordenadas(String direccion) {
        String coordenada = "";
        do {
            System.out.print(ConfiguracionJuego.NOTIFICACION_INSERCION_COORDENADAS + direccion);
            coordenada = teclado.next().toUpperCase();
        } while (!esEntradaValida(coordenada, direccion));

        return coordenada;
    }

    public int[] convertirStringArray(String coordenadas) {
        int[] coordenadasArray = new int[2];
        if(coordenadas.equals("1/2")) {
            coordenadasArray[0] = 10;
            coordenadasArray[1] = 10;
        }else{
            int unicodeA = 65, tamanoFilaTablero = 8;
            char resto0 = '0';
            coordenadasArray[0] = tamanoFilaTablero - (coordenadas.charAt(1) - resto0);
            coordenadasArray[1] = coordenadas.charAt(0) - unicodeA;
        }
        return coordenadasArray;
    }

    public int[] pedirCoordenadas(String direccion){

        String entrada = leerCoordenadas(direccion);
        return convertirStringArray(entrada);
    }

    public boolean esJaque(Jugador jugador) {
        int[] posicionRey = this.tablero.obtenerPosicionRey(jugador);
        return esCasillaAtacada(posicionRey, jugador.obtenerId());
    }

    public String obtenerTipoEnroque(int[] coordenadasDestino) {
        String tipoEnroque = "";
        int columnaDestino = coordenadasDestino[1];
        if (columnaDestino == 2) {
            tipoEnroque = "largo";
        } else if (columnaDestino == 6){
            tipoEnroque = "corto";
        }
        return tipoEnroque;
    }

    public boolean esCasillaAtacada(int[] coordenadasDestino, int idJugadorTurnoActual) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza piezaOrigen = this.tablero.obtenerPieza(fila, columna);
                if (piezaOrigen != null && idJugadorTurnoActual != piezaOrigen.obtenerId()) {
                    int[] coordenadasOrigen = {fila, columna};
                    if (piezaOrigen.validarMovimiento(coordenadasOrigen, coordenadasDestino, this.tablero)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean sonCasillasValidasEnroque(String tipoEnroque, int idJugadorTurnoActual) {
        int direccion = (tipoEnroque.equals("corto")) ? 1 : -1;
        int columnaFinal = (direccion == 1) ? 6 : 2;
        int fila = (idJugadorTurnoActual == 1) ? 0 : 7;
        for (int columna = (4 + direccion); columna != (columnaFinal + direccion); columna += direccion) {
            int[] coordenadasCasilla = {fila, columna};
            if ((this.esCasillaAtacada(coordenadasCasilla, idJugadorTurnoActual) || (this.tablero.obtenerPieza(fila, columna) != null))) {
                System.out.println(ConfiguracionJuego.ERROR_ENROQUE_INVALIDO);
                return false;
            }
        }
        return true;
    }

    private Pieza obtenerTorreCorta(int idJugador) {
        if (idJugador == 1) {
            return this.tablero.obtenerPieza(0, 7);
        } else {
            return this.tablero.obtenerPieza(7, 7);
        }
    }

    private Pieza obtenerTorreLarga(int idJugador) {
        if (idJugador == 1) {
            return this.tablero.obtenerPieza(0, 0);
        } else {
            return this.tablero.obtenerPieza(7, 0);
        }
    }

    private Pieza obtenerTorreEnroque(String tipoEnroque, int idJugador) {
        if (tipoEnroque.equals("corto")) {
            return obtenerTorreCorta(idJugador);
        } else {
            return obtenerTorreLarga(idJugador);
        }
    }

    public boolean sePuedeHacerEnroque(String tipoEnroque, Jugador jugadorTurnoActual) {
        int[] coordenadasRey = this.tablero.obtenerPosicionRey(jugadorTurnoActual);
        Pieza rey = this.tablero.obtenerPieza(coordenadasRey[0], coordenadasRey[1]);
        if (this.esJaque(jugadorTurnoActual)) {
            return false;
        }
        Pieza torre = obtenerTorreEnroque(tipoEnroque, jugadorTurnoActual.obtenerId());
        if (torre == null || !rey.obtenerEsPrimerMovimiento() || !torre.obtenerEsPrimerMovimiento()) {
            return false;
        }
        return this.sonCasillasValidasEnroque(tipoEnroque, jugadorTurnoActual.obtenerId());
    }

    public void moverPiezasEnroque(String tipoEnroque, Jugador jugadorTurnoActual) {
        int fila = jugadorTurnoActual.obtenerId() == 1 ? 0 : 7;
        int columnaReyDestino;
        int columnaTorreDestino;
        int columnaTorreInicial;
        if (tipoEnroque.equals("corto")) {
            columnaReyDestino = 6;
            columnaTorreDestino = 5;
            columnaTorreInicial = 7;
        } else {
            columnaReyDestino = 2;
            columnaTorreDestino = 3;
            columnaTorreInicial = 0;
        }
        int columnaReyInicial = 4;
        this.tablero.moverPieza(fila, columnaReyInicial, fila, columnaReyDestino);
        this.tablero.moverPieza(fila, columnaTorreInicial, fila, columnaTorreDestino);
        System.out.println(ConfiguracionJuego.NOTIFICACION_ENROQUE_1 + tipoEnroque + ConfiguracionJuego.NOTIFICACION_ENROQUE_2);
    }

    public boolean hayReyAhogado(Jugador jugador) {
        int [] posicionRey = this.tablero.obtenerPosicionRey(jugador);
        int filaRey = posicionRey[0];
        int columnaRey = posicionRey[1];
        Pieza rey = this.tablero.obtenerPieza(posicionRey[0], posicionRey[1]);

        int[][] direcciones = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        int casillasPosibles = 0;

        for (int[] direccion : direcciones) {
            int filaNueva = filaRey + direccion[0];
            int columnaNueva = columnaRey + direccion[1];

            if (this.tablero.esCasillaDentroDelTablero(filaNueva, columnaNueva)) {
                int[] posicionPosible = {filaNueva, columnaNueva};
                if (!esCasillaAtacada(posicionPosible, jugador.obtenerId()) &&
                        rey.validarMovimiento(posicionRey, posicionPosible, this.tablero)) {
                    casillasPosibles++;
                }
            }
        }
        return !(casillasPosibles > 0);
    }

    public boolean hayTablas(Jugador jugador1, Jugador jugador2) {
        int piezasJ1 = 0;
        int piezasJ2 = 0;
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = this.tablero.obtenerPieza(fila, columna);
                if(pieza != null){
                    if (pieza.obtenerId() == jugador1.obtenerId()) {
                        piezasJ1++;
                    } else if (pieza.obtenerId() == jugador2.obtenerId()) {
                        piezasJ2++;
                    }
                }
            }
        }
        if (piezasJ1 == 1 && hayReyAhogado(jugador1)) {
            return true;
        }else if(piezasJ2 == 1 && hayReyAhogado(jugador2)){
            return true;
        }else if (piezasJ1 == 1 && !verificarPiezasSuficientes(jugador2)){
            return true;
        }else if (piezasJ2== 1 && !verificarPiezasSuficientes(jugador1)){
            return true;
        }
        return false;
    }

    public boolean verificarPiezasSuficientes(Jugador jugador) {
        int caballos = 0;
        int alfiles = 0;
        int piezas = 0;
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = this.tablero.obtenerPieza(fila, columna);
                if (pieza != null) {
                    if (pieza.obtenerId() == jugador.obtenerId()) {
                        if(pieza instanceof Caballo){
                            caballos++;
                        }else if (pieza instanceof Alfil){
                            alfiles++;
                        }else if (!(pieza instanceof Rey)){
                            piezas++;
                        }
                    }
                }
            }
        }
        if ((caballos == 1 && piezas == 0 && alfiles == 0) ||
                (alfiles == 2 && piezas == 0 && caballos == 0)) {
            return false;
        }
        return true;
    }

    public boolean solicitarTablas(Jugador jugador){
        limpiarTerminal();
        int opcion = 0;
        boolean enBucle = true;
        while (enBucle){
            try{
                System.out.println(ConfiguracionJuego.NOTIFICACION_SOLICITAR_TABLAS_1 + jugador.obtenerNombreColor() + ConfiguracionJuego.NOTIFICACION_SOLICITAR_TABLAS_2);
                System.out.println(ConfiguracionJuego.OPCIONES_TABLAS);
                opcion = teclado.nextInt();
                if (opcion == 1 || opcion == 2){
                    enBucle = false;
                }else{
                    System.out.println(ConfiguracionJuego.ERROR_VALIDADOR_NUMEROS);
                }
            } catch (Exception e) {
                System.out.println(ConfiguracionJuego.ERROR_VALIDADOR_NUMEROS);
                teclado.nextLine();
            }
        }
        boolean seAcepto = false;
        if(opcion == 1){
            seAcepto = true;
        }else{
            this.tablero.mostrarTablero();
            jugador.mostrarTiempoActual();
            System.out.println(jugador.obtenerCodigoColor() +  ConfiguracionJuego.TURNO_JUGADOR + jugador.obtenerNombreColor() + ConfiguracionJuego.COLOR_RESET);
        }
        return seAcepto;
    }

    private boolean elMovimientoEvitaJaque(Pieza pieza, int[] posicionOrigen, int[] posicionDestino, Jugador jugadorActual) {
        Pieza piezaDestino = this.tablero.obtenerPieza(posicionDestino[0], posicionDestino[1]);
        if (pieza.validarMovimiento(posicionOrigen, posicionDestino, this.tablero)) {
            boolean esPrimerMovimientoPieza = pieza.obtenerEsPrimerMovimiento();
            this.tablero.moverPieza(posicionOrigen[0], posicionOrigen[1], posicionDestino[0], posicionDestino[1]);
            boolean sigueEnJaque = esJaque(jugadorActual);
            this.tablero.retrocederJugada(posicionOrigen[0], posicionOrigen[1], posicionDestino[0], posicionDestino[1], piezaDestino);
            if (esPrimerMovimientoPieza) {
                pieza.asignarEsPrimerMovimiento(true);
            }
            return !sigueEnJaque;
        }
        return false;
    }

    private boolean puedeMoverRey(Jugador jugadorActual) {
        int[] posicionRey = this.tablero.obtenerPosicionRey(jugadorActual);
        int filaRey = posicionRey[0];
        int columnaRey = posicionRey[1];
        Pieza rey = this.tablero.obtenerPieza(filaRey, columnaRey);
        for (int direccionFila = -1; direccionFila <= 1; direccionFila++) {
            for (int direccionColumna = -1; direccionColumna <= 1; direccionColumna++) {
                if (direccionFila == 0 && direccionColumna == 0) continue;
                int filaDestino = filaRey + direccionFila;
                int columnaDestino = columnaRey + direccionColumna;
                int[] posicionDestino = {filaDestino, columnaDestino};
                if (this.tablero.esCasillaDentroDelTablero(filaDestino, columnaDestino)) {
                    if (this.elMovimientoEvitaJaque(rey, posicionRey, posicionDestino, jugadorActual)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean puedePiezaBloquearJaque(Jugador jugadorActual) {
        for (int filaOrigen = 0; filaOrigen < 8; filaOrigen++) {
            for (int columnaOrigen = 0; columnaOrigen < 8; columnaOrigen++) {
                Pieza pieza = this.tablero.obtenerPieza(filaOrigen, columnaOrigen);
                if (pieza != null && pieza.obtenerId() == jugadorActual.obtenerId() && !(pieza instanceof Rey)) {
                    int[] posicionOrigen = {filaOrigen, columnaOrigen};
                    for (int filaDestino = 0; filaDestino < 8; filaDestino++) {
                        for (int columnaDestino = 0; columnaDestino < 8; columnaDestino++) {
                            int[] posicionDestino = {filaDestino, columnaDestino};
                            if (elMovimientoEvitaJaque(pieza, posicionOrigen, posicionDestino, jugadorActual)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean esJaqueMate(Jugador jugadorActual) {
        if (!esJaque(jugadorActual)) {
            return false;
        }
        if (puedeMoverRey(jugadorActual)) {
            return false;
        }
        if (puedePiezaBloquearJaque(jugadorActual)) {
            return false;
        }
        return true;
    }

    public void limpiarTerminal() {
        for (int i = 0; i < 60; i++) {
            System.out.println();
        }
    }
}