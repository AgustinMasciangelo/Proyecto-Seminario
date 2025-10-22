// Imports
import javax.swing.*;
import java.awt.*; 

/**
 CLASE PRINCIPAL DE LA APP
 1. Es el punto de entrada
 2. Crea la ventana principal (JFrame).
 3. Contiene la instancia del "cerebro" (SistemaGestion).
 4. Administra el cambio de pantallas (con CardLayout).
 */
public class SimuladorTallerGUI {

    // ATRIBUTOS
    
    // Componentes de la Vista (GUI)
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Referencia al "Cerebro" (Lógica)
    private SistemaGestion sistema;

    // Pantallas
    private PanelMenuPrincipal panelMenuPrincipal;
    private PanelMaquinas panelMaquinas;
    private PanelTarifas panelTarifas;
    private PanelConsumos panelConsumos;
    private PanelReportes panelReportes;  //"EN DESARROLLO" PARA EL TP N°4
    private PanelOperarios panelOperarios; //"EN DESARROLLO" PARA EL TP N°4
    
    
   //Lo primero que se ejecuta cuando corremos el programa.
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimuladorTallerGUI().crearYMostrarGUI();
            }
        });
    }

    
    //Este método construye la ventana, crea el "cerebro"y prepara todos los paneles (pantallas).
    
    public void crearYMostrarGUI() {
        
        // 1. Crear el Cerebro 
        this.sistema = new SistemaGestion();

        // 2. Configurar la Ventana
        this.frame = new JFrame("Simulador de Consumo Eléctrico - Taller CNC");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al tocar la 'X'
        this.frame.setSize(new Dimension(800, 600)); // Resolucion de la ventana
        this.frame.setLocationRelativeTo(null); // Centrar en la pantalla

        // 3. Configurar el "Director de Escena"
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout); // El panel principal usa este layout

        // Creacion de los panales
        
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

        // Panel de Reportes (En desarrollo)
        // Lo creamos, pero el botón en el menú estará desactivado.
        this.panelReportes = new PanelReportes(this);
        this.mainPanel.add(panelReportes, "PANEL_REPORTES");

        // Panel de Operarios
        this.panelOperarios = new PanelOperarios(this);
        this.mainPanel.add(panelOperarios, "PANEL_OPERARIOS");
        
        this.frame.add(mainPanel); // Añadimos el panel principal a la ventana
        this.cardLayout.show(mainPanel, "MENU_PRINCIPAL"); // Mostramos el menú primero
        this.frame.setVisible(true); // Hacemos visible la ventana
    }

    // Este método público es llamado por los paneles para cambiar de pantalla.
    // También actualiza el contenido de los paneles que lo necesitan.
     
    public void mostrarPanel(String nombrePanel) {
        
        // Lógica de actualización de vistas:
    
        if (nombrePanel.equals("PANEL_MAQUINAS")) {
            panelMaquinas.actualizarTabla();
        }
        if (nombrePanel.equals("PANEL_TARIFAS")) {
            panelTarifas.actualizarTarifaActual();
        }
        if (nombrePanel.equals("PANEL_CONSUMOS")) {
            panelConsumos.actualizarPanel();
        }
        
        // Código para el TP4 (actualmente desactivado)
        // if (nombrePanel.equals("PANEL_REPORTES")) {
        //     panelReportes.actualizarReporte();
        // }
        
        // (PanelOperarios no necesita actualización)
        
        // Finalmente, le decimos al CardLayout que muestre el panel solicitado
        this.cardLayout.show(mainPanel, nombrePanel);
    }

    public SistemaGestion getSistema() { 
        return this.sistema; 
    }
    
    public JFrame getFrame() { 
        return this.frame; 
    }

} 