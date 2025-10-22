// Imports
// Por ahora no necesitamos importar nada, ya que usamos clases básicas de Java.

/**
 * CLASE: Maquina
 * Esta clase es el "Modelo" de nuestros datos. Es la plantilla para
 * crear objetos de tipo Máquina.
 *
 * REQUISITOS CUMPLIDOS:
 * - Abstracción: Define las propiedades y comportamientos esenciales de una máquina.
 * - Encapsulamiento: Usa 'private' para los atributos.
 * - Constructores: Tiene un constructor para inicializar objetos.
 */
public class Maquina {

    // ATRIBUTOS (Variables de instancia)
    // REQUISITO: Encapsulamiento.
    // Usamos 'private' para proteger los datos. Esto significa que
    // NADIE fuera de esta clase puede acceder o modificar estas variables
    // directamente. Deben usar los métodos públicos (getters/setters).
    
    private String nombre;
    private double potenciaEnWatts; 
    private String estado;

    /**
     * MÉTODO: Constructor
     * REQUISITO: Utilización de constructores.
     * Un constructor es un método especial que se llama AUTOMÁTICAMENTE
     * cuando se crea un nuevo objeto (una "instancia") de esta clase.
     * Su trabajo es inicializar los atributos.
     *
     * @param nombre El nombre de la máquina (ej: "Sierra CNC")
     * @param potenciaEnWatts La potencia (ej: 5500.0)
     */
    public Maquina(String nombre, double potenciaEnWatts) {
        
        // 'this.nombre' se refiere al atributo 'nombre' de ESTA CLASE.
        // 'nombre' (a secas) se refiere al parámetro 'nombre' que recibimos.
        this.nombre = nombre;
        this.potenciaEnWatts = potenciaEnWatts;
        
        this.estado = "activa"; 
    }

    //  MÉTODOS "GETTER" (Métodos de acceso)
    // REQUISITO: Encapsulamiento (parte 2).
    // Damos acceso de SÓLO LECTURA a nuestros atributos privados.
    // Un "getter" obtiene (get) y devuelve un valor.

    public String getNombre() {
        return this.nombre;
    }

    public double getPotenciaEnWatts() {
        return this.potenciaEnWatts;
    }
    
    public String getEstado() {
        return this.estado;
    }

    // MÉTODOS "SETTER" (Métodos de modificación)
    // REQUISITO: Encapsulamiento
    // Damos acceso de SÓLO ESCRITURA a nuestros atributos privados.
    // Un "setter" establece (set) un nuevo valor.
    // Aquí podemos añadir lógica de validación.

    /**
     * Cambia el estado de la máquina.
     * @param nuevoEstado El nuevo estado (ej: "mantenimiento", "inactiva")
     */
    public void setEstado(String nuevoEstado) {
        // REQUISITO: Estructuras Condicionales (if)
        // Podríamos validar que el estado sea uno de los permitidos.
        if (nuevoEstado.equals("activa") || nuevoEstado.equals("inactiva") || nuevoEstado.equals("mantenimiento")) {
            this.estado = nuevoEstado;
        }
    }
    
    /**
     * Cambia el nombre de la máquina.
     * @param nuevoNombre El nuevo nombre
     */
    public void setNombre(String nuevoNombre) {
        // Podríamos validar que el nombre no esté vacío.
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            this.nombre = nuevoNombre;
        }
    }

    // MÉTODOS DE COMPORTAMIENTO
    // Estas son las "cosas que la máquina puede hacer".
    
    /**
     * Calcula el consumo en Kilowatts-hora (kWh) para un tiempo dado.
     * @param horasDeUso Las horas que la máquina estuvo encendida.
     * @return El consumo total en kWh.
     */
    public double calcularConsumoKWh(double horasDeUso) {
        // 1. Convertir Watts a Kilowatts (dividiendo por 1000)
        double potenciaEnKW = this.potenciaEnWatts / 1000.0;
        
        // 2. Calcular kWh (Kilowatts * Horas)
        double consumoKWh = potenciaEnKW * horasDeUso;
        
        return consumoKWh;
    }
    
    /**
     * Método especial para mostrar el objeto como un texto legible.
     * Es muy útil para depurar y ver qué hay en la lista.
     */
    @Override
    public String toString() {
        return "Maquina [Nombre: " + this.nombre + 
               ", Potencia: " + this.potenciaEnWatts + "W" +
               ", Estado: " + this.estado + "]";
    }

}