import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // definimos valores de ingreso, guardando el rut, el PIN y el saldo, por cada usuario
        HashMap<String, Integer> rutPin = new HashMap<String, Integer>(); // Hay un Hashmap que guarda como llave el rut que es un string y un PIN como su valor.
        HashMap<String, Integer> rutSaldo = new HashMap<String, Integer>(); // Hay un Hashmap que guarda como llave el rut que es un string y un SALDO como su valor.

        rutPin.put("18628214-0", 8899); // Para la llave el rut y defino el PIN
        rutPin.put("17785146-9", 5544); // Para la llave el rut y defino el PIN
        rutSaldo.put("18628214-0", 500000);// Para la llave el rut y defino el saldo
        rutSaldo.put("17785146-9", 500000); // Para la llave el rut y defino el saldo

        boolean habilitar_billetes = true; // si esta variable es verdadera funciona como cajero automatico que entrega billetes

        int[] billetes = new int[5]; // Tipos de billetes disponibles en el cajero

        billetes[0] = 20000; // Valores del mas alto al mas bajo
        billetes[1] = 10000;
        billetes[2] = 5000;
        billetes[3] = 2000;
        billetes[4] = 1000;

        Scanner lector = new Scanner(System.in);
        //inicio de cajero automatico con lectura de PIN
        System.out.println("Bienvenido al Cajero Automático");
        System.out.println("Para iniciar, por favor ingrese su RUT con dígito verificador. Ejemplo: 11222333-4");
        String rut = lector.next();

        int intentos = 3;
        while (true) { //Ciclo infinito que se quiebra mediante validamos información
            System.out.println("Ingrese su PIN:");
            int PIN = lector.nextInt();

            if (rutPin.containsKey(rut)) { // Busca en el HashMap si el rut ingresado existe.
                if (rutPin.get(rut) == PIN) { // Se obtiene el pin guardado y se compara con el pin ingresado.
                    System.out.println("Ingreso Correcto");
                    break;
                } else {
                    intentos--; // a los intentos le restamos 1
                    if (intentos == 0) { // si la cantidad de intentos es igual a 0
                        System.out.println("Ha superado el límite de intentos");
                        return; // Finaliza
                    }
                    System.out.print("Ingreso incorrecto, le quedan  ");
                    System.out.print(intentos);
                    System.out.println(" intentos");
                }
            }
        }
        while (true) { // MENU DE CAJERO AUTOMATICO
            System.out.println("Bienvenido al Menu");
            System.out.println("1. Ver saldo");
            System.out.println("2. Abonar dinero");
            System.out.println("3. Girar dinero");
            System.out.println("4. Cambiar PIN");
            System.out.println("5. Salir");
            System.out.print("Ingrese el número de la opción: ");

            int opcion = lector.nextInt(); // Permito ingreso por teclado de la opción

            System.out.println("");

            if (opcion == 1) {
                System.out.print("Su saldo es ");
                System.out.println(rutSaldo.get(rut)); // Imprimo saldo guardado
            } else if (opcion == 2) {
                System.out.println("Ingrese el monto que desea abonar. Solo números. Sin puntos ni símbolos.");
                int abono = lector.nextInt();
                if (abono < 1) {
                    System.out.println("ERROR: Los montos deben ser mayores a $0");
                    continue; // Se detiene aquí el ciclo actual y vuelve al menu.
                }
                rutSaldo.put(rut, rutSaldo.get(rut) + abono); // Guardamos en el HASHMAP usando la llave mas el abono.
                System.out.println("El monto ha sido abonado con éxito a su cuenta.");
            } else if (opcion == 3) {
                System.out.println("Ingrese el monto a girar");
                int giro = lector.nextInt();
                if (giro < 1) { // el monto ingresado no puede ser menor a uno.
                    System.out.println("ERROR: El monto debe ser mayor a $0");
                    continue; // se detiene aqui y vuelve al menu.
                }
                if (giro > rutSaldo.get(rut)) { // si giro es mayor al saldo guardado
                    System.out.println("ERROR: El monto debe no puede ser mayor a su saldo actual"); // entrego este error
                    continue; // se detiene aqui y vuelve al menu.
                }
                if(habilitar_billetes && giro%billetes[billetes.length-1] != 0) { // si está habilitado billetes pero el monto del giro no es divisible por el billete mas pequeno
                    System.out.print("ERROR: El monto debe no puede ser entregado por los billetes disponibles en este cajero, intente con un monto multiplo de "); // entrego este error
                    System.out.println(billetes[billetes.length-1]);
                    continue; // se detiene aqui y vuelve al menu.
                }
                rutSaldo.put(rut, rutSaldo.get(rut) - giro); // Guardo en el HASHMAP con la LLAVE rut EL SALDO MENOS EL giro.
                if(habilitar_billetes) {
                    for (int i = 0; i < billetes.length; i++) { //Instruccion de inicio, condicion de ciclo e instruccion final
                        int cuantos_billetes = giro / billetes[i];  // Cuantos voy a dar
                        if (cuantos_billetes > 0) {  // Quiere decir que se pueden dar billetes de este tipo (division entera)
                            giro = giro - (cuantos_billetes * billetes[i]);
                            System.out.print("Salen ");
                            System.out.print(cuantos_billetes);
                            System.out.print(" billetes de valor ");
                            System.out.print(billetes[i]);
                            System.out.print(" por un total de ");
                            System.out.println(cuantos_billetes * billetes[i]);
                        }
                        if (giro == 0) {
                            break;
                        }
                    }
                    System.out.println("Por favor, retire sus billetes. Giro realizado con éxito.");
                } else {
                    System.out.println("Giro realizado con éxito.");
                }
            } else if (opcion == 4) {
                System.out.println("Ingrese su PIN");
                int pin = lector.nextInt();
                if (rutPin.get(rut) != pin) { // si el pin guardado es distinto al pin ingresado
                    System.out.println("ERROR: PIN incorrecto. Vuelva a intentar.");
                    continue; // se detiene aqui y vuelve al menu.
                }
                System.out.println("Ingreso nuevo PIN");
                int NUEVOPIN = lector.nextInt();
                if (NUEVOPIN > 9999 || NUEVOPIN < 1000) { // hacemos la validacion con un rango.
                    System.out.println("ERROR: Ingreso incorrecto: el PIN debe ser de 4 digitos");
                    continue; // se detiene aqui y vuelve al menu.
                }

                System.out.println("Confirme su nuevo PIN");
                int CONFIRMACIONPIN = lector.nextInt();

                if (NUEVOPIN != CONFIRMACIONPIN) { // si el nuevo pin es distinto a la confirmacion de pin
                    System.out.println("ERROR: No ha podido cambiar su PIN con exito. Asegurese de que su nuevo pin ingresado sea el mismo que la confirmacion de su nuevo pin");
                    continue;
                }
                rutPin.put(rut, NUEVOPIN); // GUARDO NUEVO pin EN HASHMAP
                System.out.println("Su pin ha sido cambiado con exito");
            } else if (opcion == 5) {
                return;
            } else {
                System.out.println("Opcion invalida, intente nuevamente");
            }
        }
    }
}

