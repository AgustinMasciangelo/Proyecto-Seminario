import java.time.LocalDate;

/**
 * CLASE: Consumo 
 * Ahora guarda el nombre de la máquina (String) en lugar del
 * objeto Maquina, para facilitar la persistencia en la BD.
 */
public class Consumo {

    // ATRIBUTOS
    private String nombreMaquina; // Antes era 'Maquina maquina'
    private double horasDeUso;
    private double kwhConsumidos;
    private double costoDelConsumo;
    private LocalDate fecha;

    /**
     * MÉTODO: Constructor (Modificado)
     */
    public Consumo(String nombreMaquina, double horasDeUso, double kwhConsumidos, double costoDelConsumo) {
        this.nombreMaquina = nombreMaquina; // Cambio
        this.horasDeUso = horasDeUso;
        this.kwhConsumidos = kwhConsumidos;
        this.costoDelConsumo = costoDelConsumo;
        this.fecha = LocalDate.now(); 
    }
    
    /**
     * Constructor ADICIONAL para leer desde la BD (incluye la fecha)
     */
    public Consumo(String nombreMaquina, double horasDeUso, double kwhConsumidos, double costoDelConsumo, LocalDate fecha) {
        this.nombreMaquina = nombreMaquina;
        this.horasDeUso = horasDeUso;
        this.kwhConsumidos = kwhConsumidos;
        this.costoDelConsumo = costoDelConsumo;
        this.fecha = fecha; 
    }

    // MÉTODOS "GETTER

    public String getNombreMaquina() { // Antes era 'getMaquina()'
        return nombreMaquina;
    }

    public double getHorasDeUso() {
        return horasDeUso;
    }

    public double getKwhConsumidos() {
        return kwhConsumidos;
    }

    public double getCostoDelConsumo() {
        return costoDelConsumo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    
} 