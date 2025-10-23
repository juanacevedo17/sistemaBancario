import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para demostrar el funcionamiento del sistema bancario
 * @author juan acevedo
 */
public class SistemaBancario {
    public static void main(String[] args) {
        List<CuentaBancaria> cuentas = new ArrayList<>();
        
        // Crear diferentes tipos de cuentas
        cuentas.add(new CuentaCorriente("Juan Pérez", 5000.0, "CC-001"));
        cuentas.add(new CuentaAhorros("María García", 10000.0, "CA-001"));
        cuentas.add(new CuentaEmpresarial("nike", 50000.0, "CE-001"));
        
        System.out.println("=== SISTEMA BANCARIO ===");
        System.out.println("Cuentas creadas:");
        for (CuentaBancaria cuenta : cuentas) {
            System.out.println(cuenta);
        }
        
        System.out.println("\n=== DEMOSTRACIÓN DE OPERACIONES ===");
        
        // Probar operaciones con manejo de excepciones
        for (CuentaBancaria cuenta : cuentas) {
            System.out.println("\n--- Operaciones con " + cuenta.getTitular() + " ---");
            
            try {
                // Depósito
                cuenta.depositar(1000.0);
                System.out.println("Depósito exitoso. Nuevo saldo: $" + cuenta.getSaldo());
                
                // Retiro
                double montoRetiro = 500.0;
                cuenta.retirar(montoRetiro);
                System.out.println("Retiro de $" + montoRetiro + " exitoso. Nuevo saldo: $" + cuenta.getSaldo());
                
                // Calcular intereses
                double intereses = cuenta.calcularIntereses();
                System.out.println("Intereses calculados: $" + intereses);
                
            } catch (SaldoInsuficienteException e) {
                System.err.println("Error en retiro: " + e.getMessage());
                
            } catch (OperacionInvalidaException e) {
                System.err.println("Error en operación: " + e.getMessage());
                
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("\n=== PRUEBAS DE EXCEPCIONES ENCADENADAS ===");
        
        // Probar excepciones específicas
        CuentaBancaria cuentaPrueba = new CuentaCorriente("Test User", 100.0, "TEST-001");
        
        try {
            // Intentar retiro mayor al saldo (debería lanzar SaldoInsuficienteException)
            cuentaPrueba.retirar(500.0);
        } catch (SaldoInsuficienteException e) {
            System.err.println("Excepción capturada: " + e.getClass().getSimpleName());
            System.err.println("Mensaje: " + e.getMessage());
        } catch (OperacionInvalidaException e) {
            System.err.println("Excepción capturada: " + e.getClass().getSimpleName());
            System.err.println("Mensaje: " + e.getMessage());
        }
        
        try {
            // Intentar retiro con monto inválido (debería lanzar OperacionInvalidaException)
            cuentaPrueba.retirar(-50.0);
        } catch (SaldoInsuficienteException | OperacionInvalidaException e) {
            System.err.println("Excepción capturada: " + e.getClass().getSimpleName());
            System.err.println("Mensaje: " + e.getMessage());
        }
        
        System.out.println("\n=== ESTADO FINAL DE CUENTAS ===");
        for (CuentaBancaria cuenta : cuentas) {
            System.out.println(cuenta);
        }
        
        System.out.println("\nLas transacciones han sido guardadas en: " + CuentaBancaria.ARCHIVO_TRANSACCIONES);
    }
}