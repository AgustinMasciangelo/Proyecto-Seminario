// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelMenuPrincipal extends JPanel {

    private SimuladorTallerGUI mainApp;

    public PanelMenuPrincipal(SimuladorTallerGUI mainApp) {
        this.mainApp = mainApp;
        this.setLayout(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel("Menú Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton btnMaquinas = new JButton("1. Gestión de Máquinas");
        JButton btnConsumos = new JButton("2. Registrar Consumo");
        JButton btnTarifas = new JButton("3. Actualizar Tarifa");
        JButton btnReportes = new JButton("4. Ver Reportes (En Desarrollo)");
        JButton btnOperarios = new JButton("5. Gestión Operarios");
        JButton btnSalir = new JButton("6. Salir");

        panelBotones.add(btnMaquinas);
        panelBotones.add(btnConsumos);
        panelBotones.add(btnTarifas);
        panelBotones.add(btnReportes);
        panelBotones.add(btnOperarios);
        panelBotones.add(btnSalir);

        this.add(lblTitulo, BorderLayout.NORTH);
        this.add(panelBotones, BorderLayout.CENTER);

        // BOTONES
        btnMaquinas.addActionListener(e -> mainApp.mostrarPanel("PANEL_MAQUINAS"));
        btnConsumos.addActionListener(e -> mainApp.mostrarPanel("PANEL_CONSUMOS"));
        btnTarifas.addActionListener(e -> mainApp.mostrarPanel("PANEL_TARIFAS"));
        btnReportes.setEnabled(false);
        btnOperarios.addActionListener(e -> mainApp.mostrarPanel("PANEL_OPERARIOS"));
        
        btnSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                mainApp.getFrame(), 
                "¿Estás seguro de que deseas salir?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}