/**
 * Cuenta Empresarial con límite de retiro
 * @author juan acevedo
 */
public class CuentaEmpresarial extends CuentaBancaria {
    private static final double LIMITE_RETIRO = 10000.0; // Límite de $10,000 por retiro
    
    /**
     * Constructor de cuenta empresarial
     * @param titular Titular de la cuenta
     * @param saldo Saldo inicial
     * @param numeroCuenta Número de cuenta
     */
    public CuentaEmpresarial(String titular, double saldo, String numeroCuenta) {
        super(titular, saldo, numeroCuenta);
    }
    
    /**
     * Realiza un retiro validando el límite empresarial
     * @param monto Monto a retirar
     * @throws SaldoInsuficienteException Si no hay saldo suficiente
     * @throws OperacionInvalidaException Si el monto excede el límite o es inválido
     */
    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, OperacionInvalidaException {
        if (monto <= 0) {
            throw new OperacionInvalidaException("El monto a retirar debe ser mayor a cero");
        }
        
        if (monto > LIMITE_RETIRO) {
            guardarTransaccion("RETIRO", monto, "Fallo - Excede límite empresarial");
            throw new OperacionInvalidaException(
                String.format("El monto $%.2f excede el límite de retiro empresarial de $%.2f", 
                    monto, LIMITE_RETIRO));
        }
        
        if (monto > saldo) {
            guardarTransaccion("RETIRO", monto, "Fallo - Saldo insuficiente");
            throw new SaldoInsuficienteException("Saldo insuficiente. Saldo actual: " + saldo);
        }
        
        this.saldo -= monto;
        guardarTransaccion("RETIRO", monto, "Éxito");
    }
    
    /**
     * Calcula intereses para cuenta empresarial (tasa especial)
     * @return Monto de intereses
     */
    @Override
    public double calcularIntereses() {
        // Cuentas empresariales tienen tasa de interés especial
        double tasaEspecial = saldo > 50000 ? 0.015 : 0.01; // 1.5% o 1% según saldo
        return saldo * tasaEspecial;
    }
    
    /**
     * Representación específica de cuenta empresarial
     * @return String con información de la cuenta
     */
    @Override
    public String toString() {
        return String.format("Cuenta Empresarial: %s, Titular: %s, Saldo: $%.2f, Límite retiro: $%.2f", 
            numeroCuenta, titular, saldo, LIMITE_RETIRO);
    }
}