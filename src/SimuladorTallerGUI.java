// Imports
import javax.swing.*;
import java.awt.*; 

/**
 * ---------------------------------------------------------------------
 * CLASE: SimuladorTallerGUI (Versión Final TP4)
 * ---------------------------------------------------------------------
 * Esta es la clase PRINCIPAL de la aplicación.
 * 1. Es el punto de entrada (contiene el 'main').
 * 2. Crea la ventana principal (JFrame).
 * 3. Contiene la instancia del "cerebro" (SistemaGestion).
 * 4. Administra el cambio de pantallas (con CardLayout).
 */
public class SimuladorTallerGUI {

    // --- ATRIBUTOS ---
    
    // Componentes de la Vista (GUI)
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Referencia al cerebro
    private SistemaGestion sistema;

    // Los paneles
    private PanelMenuPrincipal panelMenuPrincipal;
    private PanelMaquinas panelMaquinas;
    private PanelTarifas panelTarifas;
    private PanelConsumos panelConsumos;
    private PanelReportes panelReportes;
    private PanelOperarios panelOperarios;
    
    /**
     * ---------------------------------------------------------------------
     * MÉTODO: main (Punto de Entrada)
     * ---------------------------------------------------------------------
     * Esto es lo primero que se ejecuta cuando se corre el programa.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Llama al método que construye la GUI
                new SimuladorTallerGUI().crearYMostrarGUI();
            }
        });
    }

    /**
     * ---------------------------------------------------------------------
     * MÉTODO: crearYMostrarGUI
     * ---------------------------------------------------------------------
     * Este método construye la ventana, crea el cerebro y
     * prepara todos los paneles (pantallas).
     */
    public void crearYMostrarGUI() {
        
        // 1. Crear el Cerebro
        this.sistema = new SistemaGestion();

        // 2. Configurar la Ventana (el JFrame)
        this.frame = new JFrame("Simulador de Consumo Eléctrico - Taller CNC");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.frame.setSize(new Dimension(800, 600)); 
        this.frame.setLocationRelativeTo(null); 

        // 3. Configurar el director de escena (CardLayout)
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout); 

        // --- 4. CREAR E INSTANCIAR LOS PANELES ---
        
        // Panel del Menú
        this.panelMenuPrincipal = new PanelMenuPrincipal(this);
        this.mainPanel.add(panelMenuPrincipal, "MENU_PRINCIPAL");
        
        // Panel de Máquinas
        this.panelMaquinas = new PanelMaquinas(this);
        this.mainPanel.add(panelMaquinas, "PANEL_MAQUINAS");

        // Panel de Tarifas
        this.panelTarifas = new PanelTarifas(this);
        this.mainPanel.add(panelTarifas, "PANEL_TARIFAS");
        
        // Panel de Consumos
        this.panelConsumos = new PanelConsumos(this);
        this.mainPanel.add(panelConsumos, "PANEL_CONSUMOS");

        // Panel de Reportes (nuevo)
        this.panelReportes = new PanelReportes(this);
        this.mainPanel.add(panelReportes, "PANEL_REPORTES");

        // Panel de Operarios (Placeholder)
        this.panelOperarios = new PanelOperarios(this);
        this.mainPanel.add(panelOperarios, "PANEL_OPERARIOS");
        
        
        // --- 5. MONTAR Y MOSTRAR ---
        this.frame.add(mainPanel); 
        this.cardLayout.show(mainPanel, "MENU_PRINCIPAL"); 
        this.frame.setVisible(true); // Hacemos visible la ventana
    }

    /**
     * ---------------------------------------------------------------------
     * MÉTODO: mostrarPanel
     * ---------------------------------------------------------------------
     */
    public void mostrarPanel(String nombrePanel) {
        
        // Lógica de actualización de vistas:
        // Cada vez que mostramos un panel, nos aseguramos de que
        // sus datos estén frescos 
        
        if (nombrePanel.equals("PANEL_MAQUINAS")) {
            panelMaquinas.actualizarTabla();
        }
        if (nombrePanel.equals("PANEL_TARIFAS")) {
            panelTarifas.actualizarTarifaActual();
        }
        if (nombrePanel.equals("PANEL_CONSUMOS")) {
            panelConsumos.actualizarPanel();
        }
        if (nombrePanel.equals("PANEL_REPORTES")) {
            panelReportes.actualizarReporte();
        }
        
        // Le decimos al CardLayout que muestre el panel solicitado
        this.cardLayout.show(mainPanel, nombrePanel);
    }
    
    /**
     * ---------------------------------------------------------------------
     * MÉTODOS "GETTER" (Para que los paneles accedan al Cerebro y la Ventana)
     * ---------------------------------------------------------------------
     */
    public SistemaGestion getSistema() { 
        return this.sistema; 
    }
    
    public JFrame getFrame() { 
        return this.frame; 
    }
}