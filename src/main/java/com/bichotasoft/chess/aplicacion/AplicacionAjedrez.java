package main.java.com.bichotasoft.chess.aplicacion;

import main.java.com.bichotasoft.chess.configuracion.ConfiguracionJuego;
import main.java.com.bichotasoft.chess.controlador.ControladorAjedrez;

import java.util.Scanner;

public class AplicacionAjedrez {

    public static void main(String[] args) {

        ControladorAjedrez controlador = new ControladorAjedrez();
        Scanner sc = new Scanner(System.in);
        boolean finalizoEjecucion = false;
        int opcion = 0;
        while(!finalizoEjecucion) {
            mostrarMenu();
            try {
                opcion = sc.nextInt();
            } catch (Exception e) {
                System.out.println(ConfiguracionJuego.ERROR_VALIDADOR_NUMEROS);
                sc.nextLine();
            }

            switch(opcion) {
                case 1:
                    controlador.Jugar();
                    break;
                case 2:
                    controlador.seleccionarColores();
                    break;
                case 3:
                    finalizoEjecucion = true;
                    System.out.println("Vuelva pronto!");
                    break;
            }
            opcion = 0;
        }


    }

    public static void mostrarMenu(){

        System.out.println("                                                                                                         ");
        System.out.println("        █████╗      ██╗███████╗██████╗ ██████╗ ███████╗██████╗                                                          ");
        System.out.println("       ██╔══██╗     ██║██╔════ ██╔══██╗██╔══██╗██╔════╝╚════██╗                                                        ");
        System.out.println("       ███████║     ██║█████╗  ██║  ██║██║  ██║█████╗     ░██╔╝                                                       ");
        System.out.println("       ██╔══██║██   ██║██╔══╝  ██║  ██║██████╔╝██╔══╝    ██╔╝░                                                      ");
        System.out.println("       ██║  ██║╚█████╔╝███████╗██████╔╝██║═\\╝  ███████╗██████╔╝                                                      ");
        System.out.println("       ╚═╝  ╚═╝ ╚════╝ ╚══════╝╚═════╝ ██║  \\  ╚══════╝╚═════╝                                                              ");
        System.out.println("BY CODECRAFTERS                                     ");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                    ║");
        System.out.println("║               ███╗   ███╗███████╗███╗   ██╗██╗   ██╗               ║");
        System.out.println("║               ████╗ ████║██╔════╝████╗  ██║██║   ██║               ║ ");
        System.out.println("║               ██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║               ║ ");
        System.out.println("║               ██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║               ║ ");
        System.out.println("║               ██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝               ║ ");
        System.out.println("║               ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝                ║ ");
        System.out.println("║                                                                    ║ ");
        System.out.println("║                                                                    ║ ");
        System.out.println("║                                                                    ║");
        System.out.println("║                                                                    ║ ");
        System.out.println("║                                                                    ║");
        System.out.println("║                           1. INICIAR PARTIDA                       ║");
        System.out.println("║                   2. CONFIGURAR COLOR DE LAS PIEZAS                ║");
        System.out.println("║                                3. SALIR                            ║");
        System.out.println("║                                                                    ║");
        System.out.println("║                                                                    ║");
        System.out.println("║                                OPCIÓN:                             ║");
        System.out.println("║                                                                    ║");
        System.out.println("║                                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

    }
}