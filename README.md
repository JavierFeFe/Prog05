# Tarea para PROG05.
## Detalles de la tarea de esta unidad.
### Enunciado.
A lo largo de esta unidad has ido aprendiendo a crear tus propias clases así como sus distintos miembros (atributos y métodos). Has experimentando con la encapsulación y accesibilidad (modificadores de acceso a miembros), has creado miembros estáticos (de clase) y de instancia (de objeto), has escrito constructores para tus clases, has sobrecargado métodos y los has utilizado en pequeñas aplicaciones. También has tenido tu primer encuentro el concepto de herencia, que ya desarrollarás en unidades más avanzadas junto con otros conceptos avanzados de la Programación Orientada a Objetos.

Una vez finalizada la unidad se puede decir que tienes un dominio adecuado del lenguaje Java como para desarrollar tus propias clases y utilizarlas en una aplicación final que sea capaz de manipular un conjunto de datos simple. Dada esa premisa, esta tarea tendrá como objetivo escribir una pequeña aplicación en Java empleando algunos de los elementos que has aprendido a utilizar.

Se trata de desarrollar una aplicación Java en consola que permita gestionar una cuenta bancaria. Mediante un menú se podrán realizar determinas operaciones:

* Ver el número de cuenta completo (CCC - Código Cuenta Cliente).
* Ver el titular de la cuenta.
* Ver el código de la entidad.
* Ver el código de la oficina.
* Ver el número de la cuenta (solamente el número de cuenta, sin entidad, oficina ni dígitos de control).
* Ver los dígitos de control de la cuenta.
* Realizar un ingreso. Habrá que solicitar por teclado la cantidad que se desea ingresar.
* Retirar efectivo. Habrá que solicitar por teclado la cantidad que se desea retirar.
* Consultar saldo.
* Salir de la aplicación.

Antes de que aparezca este menú, el programa tendrá que solicitar al usuario los siguientes datos:

* Nombre del titular de la cuenta (con un máximo de caracteres).
* Código cuenta cliente (CCC) de la cuenta completo (entidad-oficina-dígitos de control-cuenta).
El programa deberá asegurarse que el CCC es válido mediante la comprobación de:

* El formato (cuatro dígitos de entidad, cuatro dígitos de oficina, dos dígitos de control y diez dígitos de número de cuenta).
* Los dígitos de control son válidos.

Además del programa principal de la aplicación (clase con una función main), habrá que escribir una clase CuentaBancaria que proporcione todas las herramientas necesarias para trabajar con este tipo de información:

* Constructor (o constructores) adecuados.
* Almacenamiento del nombre del titular (atributos).
* Almacenamiento del código de cuenta (atributos).
* Almacenamiento del saldo actual (atributos).
* Gestión de ingresos y depósitos (métodos de interfaz pública).
* Obtención del saldo (métodos de interfaz pública).
* Obtención de información sobre la cuenta: número de la cuenta, entidad, oficina, titular, etc. (métodos de interfaz pública).
* Aquellas herramientas auxiliares necesarias para poder trabajar cómodamente con el objeto. Algunas de esas herramientas podrán ser públicos y otras quizá no. Algunas podrán ser específicas de clase y otras podrán ser de objeto (métodos de objeto privados, métodos estáticos públicos, etc.).
  
Para trabajar con el número de cuenta debes utilizar el modelo de Código Cuenta Cliente (CCC), que está formado por cuatro campos: entidad - sucursal - dígito de control - número de cuenta. La idea es que puedas introducir el código de cuenta completo y que la clase disponga de un mecanismo para comprobar que ese código es válido. Si el código no es válido, se debería generar una excepción (y por supuesto no almacenar ese código de cuenta). Para ello podrías tener, por ejemplo, un método estático que permita validar códigos de cuenta.

En general, deberías incluir excepciones para controlar aquellos casos en los que el uso de un método no sea posible (intentar sacar más dinero del que hay en el saldo, intentar introducir un titular con más caracteres de los permitidos, intentar ingresar o retirar una cantidad negativa, etc.).

El código fuente Java de esta clase debería incluir comentarios en cada atributo (o en cada conjunto de atributos) y método (o en cada conjunto de métodos del mismo tipo) indicando su utilidad. El programa principal también debería incluir algunos comentarios explicativos sobre su funcionamiento y la utilización de objetos de la clase CuentaBancaria.

## Creación de la clase CuentaBancaria.java

La clase CuentaBancaria estará compuesta por un conjunto de atributos privados a los que se podrá acceder desde distintos métodos.
En el momento de la instanciación del objecto se asignará un valor único para titular y cuenta bancaria, desde el propio constructor se verificará si el nº de cuenta es válido, en caso de no serlo no se guardarán los valores de la cuenta en los atributos y guardará en el atributo codigoError el problema de la creación del objeto (en caso des ser null quiere decir que la cuenta es válida). Al no existir métodos setter para los distintos campos de la cuenta bancaría nos obligará a instanciar de nuevo el objeto (Con esto evito posibles errores al intentar cambiar cualquiera de los campos del nº de cuenta bancaria).

```Java
    private String titular, cuentaCompleta,codigoEntidad, oficina, digitoControl, numeroCuenta, codigoError;
    private double saldo;
````
*Creo los atributos necesarios.*

```Java
    public CuentaBancaria(String titular, String codigoEntidad, String oficina, String digitoControl, String numeroCuenta) {
        //Si no se verifica la cuenta se guarda un código de error en la variable codigoError
        if ( //Verifico que los datos introducidos tienen la longitud correcta y son de tipo numérico
                codigoEntidad.matches("\\d{4}")
                && oficina.matches("\\d{4}")
                && digitoControl.matches("\\d{2}")
                && numeroCuenta.matches("\\d{10}")
                ){
            if (CuentaBancaria.compruebaDC(codigoEntidad + oficina + digitoControl + numeroCuenta)) { //Compruebo el código DC
                this.titular = titular;
                this.codigoEntidad = codigoEntidad;
                this.oficina = oficina;
                this.digitoControl = digitoControl;
                this.numeroCuenta = numeroCuenta;
                this.cuentaCompleta = codigoEntidad + oficina + digitoControl + numeroCuenta;
                saldo = 0.0;
            }else {
                codigoError = "Verificación de cuenta incorrecta";
            }
        }else {
            codigoError = "Formato de cuenta incorrecto";
        }
    }
```
*Creo un constructor que permita introducir los distintos campos de forma individual.*
```Java
    public CuentaBancaria(String titular, String cuentaCompleta) {
        //Si no se verifica la cuenta se guarda un código de error en la variable codigoError
        //Verifico que la cadena de texto tenga un formato adecuado
        if (cuentaCompleta.matches("^\\d{20}$") || cuentaCompleta.matches("^\\d{4}\\s\\d{4}\\s\\d{2}\\s\\d{10}$")) {
            cuentaCompleta = cuentaCompleta.replaceAll(" ","");
            String[] cuenta = getArrayCuenta(cuentaCompleta);
            if (compruebaDC(cuentaCompleta)) { //Compruebo el código DC
                this.titular = titular;
                this.codigoEntidad = cuenta[0];
                this.oficina = cuenta[1];
                this.digitoControl = cuenta[2];
                this.numeroCuenta = cuenta[3];
                this.cuentaCompleta = cuentaCompleta;
                saldo = 0.0;
            } else {
                codigoError = "Verificación de cuenta incorrecta";
            }
        }else {
            codigoError = "Formato de cuenta incorrecto";
        }

    }
````
*Constructor que acepta un nº de cuenta en formato String, con o sin seperaciones por espacio, y verifica que sea correcta.*
```Java
    private static String[] getArrayCuenta(String cuenta){//Método que permite descomponer un String de cuenta bancaria en un array
        String entidad = cuenta.substring(0,4);
        String oficina = cuenta.substring(4,8);
        String dc = cuenta.substring(8,10);
        String num = cuenta.substring(10,20);
        return new String[]{entidad,oficina,dc,num};
    }
    public static boolean compruebaDC(String cuenta){ //Este método puede ser llamado desde cualquier otra clase para verificar la validez de una cuenta.
        String[] arrayCuenta = getArrayCuenta(cuenta); //Llamada al método que descompone la cuenta en un array de Strings
        int[] factores = {4,8,5,10,9,7,3,6}; //Primera comprobación.
        String cuentaTmp = arrayCuenta[0] + arrayCuenta[1];
        String[] digitos = cuentaTmp.split("");
        long suma = 0;
        for ( int i =0 ;i <8; i++ ){
            suma += Integer.parseInt(digitos[i]) * factores[i];
        }
        long digito1 = (11-(suma %11)) < 10 ?11-(suma %11):11-(suma %11)==10?1:0;
        int[] factores2 = {1,2,4,8,5,10,9,7,3,6}; //Segunda comprobación.
        cuentaTmp = arrayCuenta[3];
        digitos = cuentaTmp.split("");
        suma = 0;
        for ( int i =0 ;i <10; i++ ){
            suma += Integer.parseInt(digitos[i]) * factores2[i];
        }
        long digito2 = (11-(suma %11)) < 10 ?11-(suma %11):11-(suma %11)==10?1:0;
        if (arrayCuenta[2].equals(String.valueOf(digito1) + String.valueOf(digito2))){
            return true;
        }
        return false;
    }
````
*Métodos Estáticos que nos permiten verificar si un nº de cuenta es válido mediante el cálculo del Dígito de Control (el método compruebaDC es público, por lo que se podría llamar desde otra clase).*
```Java
    public String getError(){
        return codigoError;
    }
    public String getTitular() {
        return titular;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public String getOficina() {
        return oficina;
    }

    public String getDigitoControl() {
        return digitoControl;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getCuentaCompleta() {
        return cuentaCompleta;
    }
    public double getSaldo(){
        return saldo;
    }
````
*Getters básicos que devuelven los valores de los atributos principales.*
```Java
    public String depositaSaldo(double inc){
        if (inc > 0.0){//La cantidad debe ser un nº positivo mayor a 0
            saldo+=inc;
            return "Se han depositado " + inc + "€ en la cuenta.";
        }else{
            return "Cantidad no válida.";
        }
    }
    public String depositaSaldo(String inc){
        try
        {
            return depositaSaldo(Double.parseDouble(inc));
        }
        catch(NumberFormatException e)
        {
            return "Formato no válido."; //Controla el error en el formato y devuelve un String
        }
    }
    public String retiraSaldo(double red){
        if (red > 0.0) {//La cantidad debe ser un nº positivo mayor a 0
             if (red <= saldo) { //La cantidad a retirar debe ser igual o inferior al saldo total
                 saldo -= red;
                 return "Se han retirado " + red + "€ de la cuenta.";
             }else{
                 return "Saldo insuficiente.";
             }
        }else {
            return "Cantidad no válida.";
        }
    }
    public String retiraSaldo(String red){
        try
        {
            return retiraSaldo(Double.parseDouble(red));
        }
        catch(NumberFormatException e)
        {
            return "Formato no válido.";//Controla el error en el formato y devuelve un String
        }
    }
 ````
 *Estos son getters q hacen en cierta medida funciones de setters, en este caso los declaro así porque me interesa capturar los posibles errores a la hora de introducir los valores (sobrecarga de métodos que permiten introducir el valor tanto en formato String como en double).*
    
