/**
 * CLASE: MaquinaException
 * REQUISITO: Tratamiento y manejo de excepciones.
 * * Esta es una clase de Excepción personalizada. "Hereda" (extends)
 * de la clase 'Exception' base de Java.
 *
 * La creamos para poder lanzar y capturar errores que sean
 * específicos de nuestro sistema (ej: "Máquina no encontrada").
 */
public class MaquinaException extends Exception {

    /**
     * REQUISITO: Constructores.
     * Un constructor que simplemente toma un mensaje de error
     * y se lo pasa al "padre" (la clase 'Exception').
     * @param message El mensaje que describe el error.
     */
    public MaquinaException(String message) {
        super(message);
    }
    
}