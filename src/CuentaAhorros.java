/**
 * Cuenta de Ahorros que genera intereses mensuales
 * @author juan acevedo
 */
public class CuentaAhorros extends CuentaBancaria {
    private static final double TASA_INTERES = 0.02; // 2% mensual
    
    /**
     * Constructor de cuenta de ahorros
     * @param titular Titular de la cuenta
     * @param saldo Saldo inicial
     * @param numeroCuenta Número de cuenta
     */
    public CuentaAhorros(String titular, double saldo, String numeroCuenta) {
        super(titular, saldo, numeroCuenta);
    }
    
    /**
     * Calcula intereses para cuenta de ahorros
     * @return Monto de intereses generados
     */
    @Override
    public double calcularIntereses() {
        double intereses = saldo * TASA_INTERES;
        guardarTransaccion("INTERESES", intereses, "Intereses generados");
        return intereses;
    }
    
    /**
     * Aplica los intereses al saldo de la cuenta
     */
    public void aplicarIntereses() {
        double intereses = calcularIntereses();
        this.saldo += intereses;
    }
    
    /**
     * Representación específica de cuenta de ahorros
     * @return String con información de la cuenta
     */
    @Override
    public String toString() {
        return String.format("Cuenta Ahorros: %s, Titular: %s, Saldo: $%.2f, Tasa interés: %.1f%%", 
            numeroCuenta, titular, saldo, TASA_INTERES * 100);
    }
}
