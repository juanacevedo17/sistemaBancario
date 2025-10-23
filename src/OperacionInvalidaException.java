/**
 * Excepción personalizada para operaciones inválidas
 */
public class OperacionInvalidaException extends Exception {
    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
    
    public OperacionInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}