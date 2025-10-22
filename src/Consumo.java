// Imports
//'LocalDate' para guardar la fecha del registro
import java.time.LocalDate;

/**
 * CLASE: Consumo
 * REQUISITO: Abstracción y Encapsulamiento.
 * Esta clase "modelo" representa un único evento de consumo eléctrico.
 * Guarda QUÉ máquina se usó, CUÁNTAS horas, y el COSTO de ese uso.
 */
public class Consumo {

    // ATRIBUTOS (private para Encapsulamiento)
    private Maquina maquina;        // La máquina que se usó
    private double horasDeUso;
    private double kwhConsumidos;
    private double costoDelConsumo;
    private LocalDate fecha;        // La fecha en que se registró

    /**
     * MÉTODO: Constructor
     * REQUISITO: Constructores.
     * Crea un nuevo registro de consumo.
     */
    public Consumo(Maquina maquina, double horasDeUso, double kwhConsumidos, double costoDelConsumo) {
        this.maquina = maquina;
        this.horasDeUso = horasDeUso;
        this.kwhConsumidos = kwhConsumidos;
        this.costoDelConsumo = costoDelConsumo;
        this.fecha = LocalDate.now(); // Guarda la fecha de hoy automáticamente
    }

    // MÉTODOS "GETTER"
    // REQUISITO: Encapsulamiento.
    // Damos acceso de SÓLO LECTURA a los datos del registro.
    // No creamos "setters" porque un registro, una vez creado, no debe modificarse.

    public Maquina getMaquina() {
        return maquina;
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