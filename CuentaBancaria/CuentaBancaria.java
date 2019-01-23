package CuentaBancaria;

public class CuentaBancaria {
    private String titular, cuentaCompleta,codigoEntidad, oficina, digitoControl, numeroCuenta, codigoError;
    private double saldo;

    /**
     * @param titular Titular de la cuenta.
     * @param codigoEntidad Código de entidad.
     * @param oficina Código de oficina.
     * @param digitoControl Dígito de control.
     * @param numeroCuenta Número de cuenta.
     */
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
    /**
     * @param titular Titular de la cuenta.
     * @param cuentaCompleta Número de cuenta completo (todo junto o con espacios).
     */
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
    //Getters y Setters
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

    //Sobrecarga de método que permite introducir el saldo (tanto en retiradas como ingresos) tanto en formato String como en formato double,
    // al finalizar la operación devuelve el resultado en formato String.
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
}
