// Imports
// 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ---------------------------------------------------------------------
 * CLASE: PanelMenuPrincipal
 * ---------------------------------------------------------------------
 * REQUISITO: Herencia (extends JPanel).
 * Muestra el menú de selección principal de la aplicación.
 */
public class PanelMenuPrincipal extends JPanel {

    // Referencia a la ventana principal para poder cambiar de panel
    private SimuladorTallerGUI mainApp;

    /**
     * ---------------------------------------------------------------------
     * MÉTODO: Constructor
     * ---------------------------------------------------------------------
     * @param mainApp 
     */
    public PanelMenuPrincipal(SimuladorTallerGUI mainApp) {
        // Guardamos la referencia a la ventana principal
        this.mainApp = mainApp;

        // -CONFIGURAR EL LAYOUT 
        this.setLayout(new BorderLayout(10, 10)); 
        
        // CREAR COMPONENTES (Objetos) 
        
        // Título
        JLabel lblTitulo = new JLabel("Menú Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente más grande

        // Panel para los botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 20, 20));
        
        // Añadimos un borde vacío para que los botones no estén pegados al borde
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        // c. Los 6 botones (basados en tu PDF pág. 36)
        JButton btnMaquinas = new JButton("1. Gestión de Máquinas");
        JButton btnConsumos = new JButton("2. Registrar Consumo");
        JButton btnTarifas = new JButton("3. Actualizar Tarifa");
        JButton btnReportes = new JButton("4. Ver Reportes"); 
        JButton btnOperarios = new JButton("5. Gestión Operarios"); 
        JButton btnSalir = new JButton("6. Salir");

        // AÑADIR COMPONENTES AL PANEL 
        
        // Añadimos los botones al panel de botones
        panelBotones.add(btnMaquinas);
        panelBotones.add(btnConsumos);
        panelBotones.add(btnTarifas);
        panelBotones.add(btnReportes);
        panelBotones.add(btnOperarios);
        panelBotones.add(btnSalir);

        // Añadimos el título al NORTE (arriba) de este panel
        this.add(lblTitulo, BorderLayout.NORTH);
        // Añadimos el panel de botones al CENTRO de este panel
        this.add(panelBotones, BorderLayout.CENTER);

        // LÓGICA DE BOTONES 
        
        // Botón 1: Gestión de Máquinas
        btnMaquinas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainApp.mostrarPanel("PANEL_MAQUINAS");
            }
        });

        // Botón 2: Registrar Consumo
        btnConsumos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainApp.mostrarPanel("PANEL_CONSUMOS");
            }
        });

        // Botón 3: Actualizar Tarifa
        btnTarifas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainApp.mostrarPanel("PANEL_TARIFAS");
            }
        });

        // Botón 4: Ver Reportes 
        btnReportes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainApp.mostrarPanel("PANEL_REPORTES");
            }
        });
        
        // Botón 5: Gestión Operarios 
        btnOperarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainApp.mostrarPanel("PANEL_OPERARIOS");
            }
        });
        
        // Botón 6: Salir
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                    mainApp.getFrame(),
                    "¿Estás seguro de que deseas salir?",
                    "Confirmar Salida",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    System.exit(0); // Cierra la aplicación
                }
            }
        });
    }
    
} 