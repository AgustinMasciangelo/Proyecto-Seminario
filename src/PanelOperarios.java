// Imports
import javax.swing.*;
import java.awt.*;

/**
 * CLASE PanelOperarios
 * REQUISITO: Herencia (extends JPanel).
 * Este panel está "en construcción".
 */
public class PanelOperarios extends JPanel {

    private SimuladorTallerGUI mainApp;

    public PanelOperarios(SimuladorTallerGUI mainApp) {
        this.mainApp = mainApp;
        this.setLayout(new BorderLayout());

        // Mensaje central
        JLabel lblMensaje = new JLabel("Módulo 'Gestión de Operarios' en construcción.", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 18));
        this.add(lblMensaje, BorderLayout.CENTER);

        // Botón para volver
        JButton btnVolver = new JButton("Volver al Menú");
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(btnVolver);
        
        this.add(panelSur, BorderLayout.SOUTH);

        // Acción del botón
        btnVolver.addActionListener(e -> mainApp.mostrarPanel("MENU_PRINCIPAL"));
    }
}