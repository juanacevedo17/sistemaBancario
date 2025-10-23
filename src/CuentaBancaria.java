import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase base que representa una cuenta bancaria genérica
 * @author juan acevedo
 */
public abstract class CuentaBancaria {
    protected String titular;
    protected double saldo;
    protected String numeroCuenta;
    protected static final String ARCHIVO_TRANSACCIONES = "transacciones.csv";
    
    /**
     * Constructor de la cuenta bancaria
     * @param titular Titular de la cuenta
     * @param saldo Saldo inicial
     * @param numeroCuenta Número de cuenta único
     */
    public CuentaBancaria(String titular, double saldo, String numeroCuenta) {
        this.titular = titular;
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
    }
    
    /**
     * Realiza un depósito en la cuenta
     * @param monto Monto a depositar
     * @throws OperacionInvalidaException Si el monto es inválido
     */
    public void depositar(double monto) throws OperacionInvalidaException {
        if (monto <= 0) {
            throw new OperacionInvalidaException("El monto a depositar debe ser mayor a cero");
        }
        
        this.saldo += monto;
        guardarTransaccion("DEPOSITO", monto, "Éxito");
    }
    
    /**
     * Realiza un retiro de la cuenta
     * @param monto Monto a retirar
     * @throws SaldoInsuficienteException Si no hay saldo suficiente
     * @throws OperacionInvalidaException Si el monto es inválido
     */
    public void retirar(double monto) throws SaldoInsuficienteException, OperacionInvalidaException {
        if (monto <= 0) {
            throw new OperacionInvalidaException("El monto a retirar debe ser mayor a cero");
        }
        
        if (monto > saldo) {
            guardarTransaccion("RETIRO", monto, "Fallo - Saldo insuficiente");
            throw new SaldoInsuficienteException("Saldo insuficiente. Saldo actual: " + saldo);
        }
        
        this.saldo -= monto;
        guardarTransaccion("RETIRO", monto, "Éxito");
    }
    
    /**
     * Método abstracto para calcular intereses
     * @return Monto de intereses calculados
     */
    public abstract double calcularIntereses();
    
    /**
     * Guarda una transacción en el archivo CSV
     * @param tipo Tipo de transacción
     * @param monto Monto de la transacción
     * @param estado Estado de la transacción
     */
    protected void guardarTransaccion(String tipo, double monto, String estado) {
        try (FileWriter fw = new FileWriter(ARCHIVO_TRANSACCIONES, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String linea = String.format("%s,%s,%s,%.2f,%s,%s", 
                fecha, numeroCuenta, titular, monto, tipo, estado);
            
            // Verificar si el archivo está vacío para escribir el encabezado
            File archivo = new File(ARCHIVO_TRANSACCIONES);
            if (archivo.length() == 0) {
                out.println("FECHA,NUMERO_CUENTA,TITULAR,MONTO,TIPO,ESTADO");
            }
            
            out.println(linea);
            
        } catch (IOException e) {
            System.err.println("Error al guardar transacción: " + e.getMessage());
        }
    }
    
    // Getters
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public String getNumeroCuenta() { return numeroCuenta; }
    
    /**
     * Representación en string de la cuenta
     * @return String con la información de la cuenta
     */
    @Override
    public String toString() {
        return String.format("Cuenta: %s, Titular: %s, Saldo: $%.2f", 
            numeroCuenta, titular, saldo);
    }
}