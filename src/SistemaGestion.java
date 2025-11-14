import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CLASE: SistemaGestion (Modificada para TP4 - Fase 3)
 * ¡Versión final! Todo (Máquinas, Consumos, Tarifas) está conectado a la BD.
 */
public class SistemaGestion {

    // --- ATRIBUTOS (Modificados) ---
    private IMaquinaDAO maquinaDAO;
    private IConsumoDAO consumoDAO;
    private TarifaDAO tarifaDAO; // <-- CAMBIO: Añadido DAO de Tarifa
    
    // <-- CAMBIO CLAVE: Ya no necesitamos la variable tarifaPorKWh
    // private double tarifaPorKWh; 
    
    // <-- CAMBIO CLAVE: Eliminamos la lista temporal de consumos
    // private List<Consumo> historialConsumos; 

    /**
     * MÉTODO: Constructor (Modificado)
     */
    public SistemaGestion() {
        this.maquinaDAO = new MaquinaDAO();
        this.consumoDAO = new ConsumoDAO();
        this.tarifaDAO = new TarifaDAO(); // <-- CAMBIO: Inicializamos el DAO
        
        // <-- CAMBIO CLAVE: Ya no hay variables locales que inicializar
    }

    // --- ¡AQUÍ ESTÁ LA CORRECCIÓN! ---
    // Agregamos el cuerpo (el código) a los métodos
    
    /**
     * Agrega una nueva máquina usando el DAO (ahora guarda en MySQL).
     */
    public void agregarMaquina(String nombre, double potenciaEnWatts) throws MaquinaException {
        if (potenciaEnWatts <= 0) {
            throw new MaquinaException("Error: La potencia debe ser mayor que 0.");
        }
        
        try {
            // Creamos el objeto Maquina
            Maquina nuevaMaquina = new Maquina(nombre, potenciaEnWatts);
            
            // Llamamos al DAO para que lo guarde en la BD
            maquinaDAO.guardar(nuevaMaquina);
            
        } catch (Exception e) {
            // REQUISITO: Manejo de Excepciones SQL (la capturamos del DAO)
            // Convertimos la excepción genérica en nuestra excepción de negocio
            throw new MaquinaException("Error al guardar en BD: " + e.getMessage());
        }
    }
    
    /**
     * Busca una máquina (AÚN NO SE USA, PERO LO DEJAMOS)
     * (En el TP3 validaba duplicados, ahora la BD podría hacerlo)
     */
    public Maquina buscarMaquina(String nombre) throws MaquinaException {
        // Por ahora, esta función no es necesaria para la lógica principal
        // Podríamos implementarla con 'maquinaDAO.buscarPorNombre(nombre)'
        throw new MaquinaException("Función 'buscar' no implementada con BD.");
    }
    
    // --- FIN DE LA CORRECCIÓN ---
    
    /**
     * Registra un nuevo consumo (Modificado para leer la tarifa desde la BD)
     */
    public void registrarConsumo(Maquina maquina, double horasDeUso) throws MaquinaException {
        if (horasDeUso <= 0) {
            throw new MaquinaException("Error: Las horas de uso deben ser mayores que 0.");
        }
        if (maquina == null) {
            throw new MaquinaException("Error: Debe seleccionar una máquina.");
        }
        
        try {
            // 2. Cálculos
            double kwh = maquina.calcularConsumoKWh(horasDeUso);
            
            // <-- CAMBIO CLAVE: Lee la tarifa actual de la BD
            double tarifaActual = tarifaDAO.getTarifaActual(); 
            double costo = kwh * tarifaActual;

            // 3. Creamos el nuevo registro
            Consumo nuevoConsumo = new Consumo(maquina.getNombre(), horasDeUso, kwh, costo);
            
            // 4. Guardar en la BD usando el DAO
            consumoDAO.guardar(nuevoConsumo);
            
        } catch (Exception e) {
            throw new MaquinaException("Error al guardar consumo en BD: " + e.getMessage());
        }
    }

    // --- MÉTODOS GETTER y SETTER (Modificados) ---
    
    public List<Maquina> getMaquinas() {
        try {
            return maquinaDAO.listarTodas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error crítico al leer la BD de máquinas:\n" + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    /**
     * Actualiza la tarifa llamando al DAO.
     */
    public void setTarifa(double nuevaTarifa) throws MaquinaException {
        if (nuevaTarifa <= 0) {
            throw new MaquinaException("Error: La tarifa debe ser un valor positivo.");
        }
        
        try {
            // <-- CAMBIO CLAVE: Llama al DAO para hacer el UPDATE
            tarifaDAO.actualizarTarifa(nuevaTarifa);
        } catch (Exception e) {
            throw new MaquinaException("Error al actualizar tarifa en BD: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la tarifa actual llamando al DAO.
     */
    public double getTarifa() {
        try {
            // <-- CAMBIO CLAVE: Llama al DAO para hacer el SELECT
            return tarifaDAO.getTarifaActual();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error crítico al leer la tarifa:\n" + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            return 0.0; // Devuelve 0 si falla
        }
    }
    
    /**
     * Getter para el historial (Lee de la BD)
     */
    public List<Consumo> getHistorialConsumos() {
        try {
            return consumoDAO.listarTodos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error crítico al leer el historial:\n" + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }
}