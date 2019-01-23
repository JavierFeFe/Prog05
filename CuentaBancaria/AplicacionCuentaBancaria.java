package CuentaBancaria;

import java.util.Scanner;

public class AplicacionCuentaBancaria {
    public static void main (String[] args) {

        CuentaBancaria cuenta = null;
        String menu = //Menú que se mostrará
                "Opciones disponibles: \n\n" +
                "1 - Ver el número de cuenta completo.\n" +
                "2 - Ver el titular de la cuenta.\n" +
                "3 - Ver el código de la entidad.\n" +
                "4 - Ver el código de la oficina.\n" +
                "5 - Ver el número de la cuenta.\n" +
                "6 - Ver los dígitos de control de la cuenta.\n" +
                "7 - Realizar un ingreso.\n" +
                "8 - Retirar efectivo.\n" +
                "9 - Consultar saldo.\n" +
                "0 - Salir de la aplicación.\n\n" +
                "Seleccione una opción: ";
        //Para el nombre del titular existen tantas variantes (Por ejemplo nombres de empresa) que es muy dificil crear algún tipo de filtro.
        System.out.print("Introduce el nombre del titular: ");
        Scanner sc = new Scanner(System.in);
        String titular = sc.nextLine();
        boolean cuentValida = false;
        while (!cuentValida) { //bucle q fuerza a introducir un nº de cuenta válido
            System.out.print("Introduce el nº de cuenta: ");
            sc = new Scanner(System.in);
            //Una vez introducido el titular y el nº de cuenta estos datos no se podrán cambiar por lo q es necesario hacer la llamada al constructor para cada intento.
             cuenta = new CuentaBancaria(titular, sc.nextLine());
            if (cuenta.getError() == null) {
                System.out.println("La cuenta es válida.");
                cuentValida = true;
            } else {
                System.out.println("Cuenta inválida: " + cuenta.getError());
            }
        }
        boolean dentro = true;
        while (dentro){ //Se mostrará el menú nuevamente mientras no se pulse la opción 0.
            System.out.print(menu);
            sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion){ //Switch para las opciones del menú.
                case "1":
                    System.out.println("Nº de cuenta completo: " + cuenta.getCuentaCompleta()+"\n");
                    pausa();
                    break;
                case "2":
                    System.out.println("Titular: " + cuenta.getTitular()+"\n");
                    pausa();
                    break;
                case "3":
                    System.out.println("Código de Entidad: " + cuenta.getCodigoEntidad()+"\n");
                    pausa();
                    break;
                case "4":
                    System.out.println("Código de Oficina: " + cuenta.getOficina()+"\n");
                    pausa();
                    break;
                case "5":
                    System.out.println("Nº de cuenta: " + cuenta.getNumeroCuenta()+"\n");
                    pausa();
                    break;
                case "6":
                    System.out.println("Dígito de Control: " + cuenta.getDigitoControl()+"\n");
                    pausa();
                    break;
                case "7":  //Se ejecuta un Scanner para introducir la cantidad a ingresar
                    System.out.print("Introduzca la cantidad a ingresar: ");
                    sc = new Scanner(System.in);
                    System.out.println(cuenta.depositaSaldo(sc.nextLine())+"\n");
                    pausa();

                    break;
                case "8"://Se ejecuta un Scanner para introducir la cantidad a retirar
                    System.out.print("Introduzca la cantidad a retirar: ");
                    sc = new Scanner(System.in);
                    System.out.println(cuenta.retiraSaldo(sc.nextLine())+"\n");
                    pausa();
                    break;
                case "9":
                    System.out.println("Saldo: " + cuenta.getSaldo()+"€\n");
                    pausa();
                    break;
                case "0":
                    dentro = false;
                    break;
                default :
                    System.out.println("Opción inválida."+"\n");
                    pausa();
                    break;
            }
        }
    }
    private static void pausa(){//Método simple para crear una pausa
        System.out.println("Presione Entrar para continuar...");
        new Scanner(System.in).nextLine();
    }
}
