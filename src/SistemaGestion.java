// Imports
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE: SistemaGestion
 * Esta clase es el "Controlador" o "Cerebro".
 * Administra la lógica de negocio y la colección de máquinas.
 */
public class SistemaGestion {

    // ATRIBUTOS
    private List<Maquina> maquinas;
    private double tarifaPorKWh;

    private List<Consumo> historialConsumos;

    /**
     * MÉTODO: Constructor
     */
    public SistemaGestion() {
        this.maquinas = new ArrayList<>();
        this.tarifaPorKWh = 12.50; // Tarifa por defecto
        
        this.historialConsumos = new ArrayList<>();
    }

    // MÉTODOS DE GESTIÓN

    /**
     * Agrega una nueva máquina al sistema.
     */
    public void agregarMaquina(String nombre, double potenciaEnWatts) throws MaquinaException {
        
        // Validación 1: No permitir potencia negativa o cero.
        if (potenciaEnWatts <= 0) {
            throw new MaquinaException("Error: La potencia debe ser mayor que 0.");
        }
        
        // Validación 2: No permitir nombres duplicados
        for (Maquina maquinaExistente : this.maquinas) {
            if (maquinaExistente.getNombre().equalsIgnoreCase(nombre)) {
                throw new MaquinaException("Error: Ya existe una máquina con el nombre '" + nombre + "'.");
            }
        }

        // Creamos el nuevo objeto Maquina.
        Maquina nuevaMaquina = new Maquina(nombre, potenciaEnWatts);
        this.maquinas.add(nuevaMaquina);
    }

    /**
     * Busca una máquina en la lista por su nombre.
     */
    public Maquina buscarMaquina(String nombre) throws MaquinaException {
        
        for (Maquina maquina : this.maquinas) {
            if (maquina.getNombre().equalsIgnoreCase(nombre)) {
                return maquina;
            }
        }
        
        throw new MaquinaException("Error: No se encontró la máquina con el nombre '" + nombre + "'.");
    }
    
    /**
     *Registra un nuevo consumo
     */
    public void registrarConsumo(Maquina maquina, double horasDeUso) throws MaquinaException {
        // 1. Validación
        if (horasDeUso <= 0) {
            throw new MaquinaException("Error: Las horas de uso deben ser mayores que 0.");
        }
        if (maquina == null) {
            throw new MaquinaException("Error: Debe seleccionar una máquina.");
        }

        // 2. Cálculos
        double kwh = maquina.calcularConsumoKWh(horasDeUso);
        double costo = kwh * this.tarifaPorKWh;

        // 3. Creamos el nuevo registro
        Consumo nuevoConsumo = new Consumo(maquina, horasDeUso, kwh, costo);
        
        // 4. Guardar en el historial
        this.historialConsumos.add(nuevoConsumo);
    }


    // MÉTODOS GETTER y SETTER
    
    public List<Maquina> getMaquinas() {
        return this.maquinas;
    }

    public void setTarifa(double nuevaTarifa) throws MaquinaException {
        if (nuevaTarifa <= 0) {
            throw new MaquinaException("Error: La tarifa debe ser un valor positivo.");
        }
        this.tarifaPorKWh = nuevaTarifa;
    }
    
    public double getTarifa() {
        return this.tarifaPorKWh;
    }
    
    /**
     *Getter para el nuevo historial.
     */
    public List<Consumo> getHistorialConsumos() {
        return this.historialConsumos;
    }

} 