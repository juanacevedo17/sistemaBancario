/**
 * Cuenta Corriente que aplica comisión fija en retiros
 * @author juan acevedo
 */
public class CuentaCorriente extends CuentaBancaria {
    private static final double COMISION_RETIRO = 2.50;
    
    /**
     * Constructor de cuenta corriente
     * @param titular Titular de la cuenta
     * @param saldo Saldo inicial
     * @param numeroCuenta Número de cuenta
     */
    public CuentaCorriente(String titular, double saldo, String numeroCuenta) {
        super(titular, saldo, numeroCuenta);
    }
    
    /**
     * Realiza un retiro aplicando comisión
     * @param monto Monto a retirar
     * @throws SaldoInsuficienteException Si no hay saldo suficiente incluyendo comisión
     * @throws OperacionInvalidaException Si el monto es inválido
     */
    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, OperacionInvalidaException {
        double totalRetiro = monto + COMISION_RETIRO;
        
        if (monto <= 0) {
            throw new OperacionInvalidaException("El monto a retirar debe ser mayor a cero");
        }
        
        if (totalRetiro > saldo) {
            guardarTransaccion("RETIRO", monto, "Fallo - Saldo insuficiente con comisión");
            throw new SaldoInsuficienteException(
                String.format("Saldo insuficiente. Retiro: $%.2f + Comisión: $%.2f = $%.2f > Saldo: $%.2f", 
                    monto, COMISION_RETIRO, totalRetiro, saldo));
        }
        
        this.saldo -= totalRetiro;
        guardarTransaccion("RETIRO", monto, "Éxito - Comisión aplicada: $" + COMISION_RETIRO);
        guardarTransaccion("COMISION", COMISION_RETIRO, "Comisión por retiro");
    }
    
    /**
     * Calcula intereses para cuenta corriente (generalmente cero o muy bajos)
     * @return Monto de intereses
     */
    @Override
    public double calcularIntereses() {
        // Cuentas corrientes generalmente no generan intereses o son muy bajos
        return 0.0;
    }
    
    /**
     * Representación específica de cuenta corriente
     * @return String con información de la cuenta
     */
    @Override
    public String toString() {
        return String.format("Cuenta Corriente: %s, Titular: %s, Saldo: $%.2f, Comisión retiro: $%.2f", 
            numeroCuenta, titular, saldo, COMISION_RETIRO);
    }
}


