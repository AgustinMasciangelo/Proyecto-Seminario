// Imports
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map; // Map para agrupar los consumos
import java.util.stream.Collectors; //Streams de Java 8

/**
 * CLASE: PanelReportes
 * REQUISITO: Herencia (extends JPanel).
 * Panel para mostrar estadísticas globales y un resumen por máquina.
 */
public class PanelReportes extends JPanel {

    // ATRIBUTOS
    private SimuladorTallerGUI mainApp;
    private SistemaGestion sistema;

    // Componentes gráficos
    private JLabel lblTotalMaquinas;
    private JLabel lblTotalKWh;
    private JLabel lblCostoTotal;
    private JTable tablaResumen;
    private DefaultTableModel tableModel;
    private JButton btnVolver;

   //MÉTODO: Constructor
    
    public PanelReportes(SimuladorTallerGUI mainApp) {
        this.mainApp = mainApp;
        this.sistema = mainApp.getSistema();

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // PANEL DE ESTADÍSTICAS GLOBALES (NORTE)
        JPanel panelStats = new JPanel(new GridLayout(1, 3, 10, 10));
        panelStats.setBorder(BorderFactory.createTitledBorder("Reporte Global del Taller"));
        
        // Creamos los JLabels con valores temporales
        lblTotalMaquinas = new JLabel("Máquinas: 0");
        lblTotalKWh = new JLabel("Consumo Total: 0.00 kWh");
        lblCostoTotal = new JLabel("Costo Total: $ 0.00");
        
        Font fontStats = new Font("Arial", Font.BOLD, 16);
        lblTotalMaquinas.setFont(fontStats);
        lblTotalKWh.setFont(fontStats);
        lblCostoTotal.setFont(fontStats);
        
        // Centramos el texto
        lblTotalMaquinas.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalKWh.setHorizontalAlignment(SwingConstants.CENTER);
        lblCostoTotal.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelStats.add(lblTotalMaquinas);
        panelStats.add(lblTotalKWh);
        panelStats.add(lblCostoTotal);
        
        this.add(panelStats, BorderLayout.NORTH);

        // TABLA DE RESUMEN POR MÁQUINA (CENTRO)
        JLabel lblTituloTabla = new JLabel("Resumen por Máquina", SwingConstants.CENTER);
        lblTituloTabla.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] columnas = {"Máquina", "Total Horas", "Total Consumo (kWh)", "Total Costo ($)"};
        this.tableModel = new DefaultTableModel(columnas, 0);
        this.tablaResumen = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tablaResumen);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(lblTituloTabla, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        
        this.add(panelCentro, BorderLayout.CENTER);

        // PANEL DE NAVEGACIÓN (SUR)
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnVolver = new JButton("Volver al Menú");
        panelSur.add(btnVolver);
        this.add(panelSur, BorderLayout.SOUTH);

        // LÓGICA DE BOTONES
        btnVolver.addActionListener(e -> mainApp.mostrarPanel("MENU_PRINCIPAL"));
    }

    /**
     * MÉTODO: actualizarReporte
     * REQUISITO: Estructuras Repetitivas y Algoritmos.
     * Este método se llama CADA VEZ que se muestra el panel.
     * Lee TODOS los datos del "cerebro" y calcula los resúmenes.
     */
    public void actualizarReporte() {
        // OBTENER DATOS FRESCOS
        List<Maquina> maquinas = sistema.getMaquinas();
        List<Consumo> historial = sistema.getHistorialConsumos();
        
        // Total de Máquinas
        int totalMaquinas = maquinas.size();
        
        // REQUISITO: Bucle for-each y algoritmos
        // Usamos bucles para sumar
        double totalKWhGlobal = 0;
        double totalCostoGlobal = 0;
        for (Consumo c : historial) {
            totalKWhGlobal += c.getKwhConsumidos();
            totalCostoGlobal += c.getCostoDelConsumo();
        }

        // ACTUALIZAR LABELS GLOBALES
        lblTotalMaquinas.setText("Máquinas: " + totalMaquinas);
        lblTotalKWh.setText(String.format("Consumo Total: %.2f kWh", totalKWhGlobal));
        lblCostoTotal.setText(String.format("Costo Total: $ %.2f", totalCostoGlobal));
        
        // Borramos la tabla vieja
        tableModel.setRowCount(0);
        
        // REQUISITO: Algoritmo de Agrupación usando Streams y Map
        // Esto agrupa el historial por nombre de máquina y suma sus valores
        Map<String, List<Consumo>> consumosPorMaquina = historial.stream()
                .collect(Collectors.groupingBy(c -> c.getMaquina().getNombre()));

        // REQUISITO: Bucle for-each
        // Ahora recorremos el mapa de máquinas agrupadas
        for (String nombreMaquina : consumosPorMaquina.keySet()) {
            
            List<Consumo> consumosDeEstaMaquina = consumosPorMaquina.get(nombreMaquina);
            
            // Sumamos los totales para ESTA máquina
            double totalHoras = 0;
            double totalKWh = 0;
            double totalCosto = 0;
            
            for (Consumo c : consumosDeEstaMaquina) {
                totalHoras += c.getHorasDeUso();
                totalKWh += c.getKwhConsumidos();
                totalCosto += c.getCostoDelConsumo();
            }
            
            // Añadimos la fila a la tabla
            Object[] fila = {
                nombreMaquina,
                String.format("%.2f", totalHoras),
                String.format("%.2f", totalKWh),
                String.format("%.2f", totalCosto)
            };
            tableModel.addRow(fila);
        }
    }
}