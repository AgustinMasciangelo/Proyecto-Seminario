// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * CLASE: PanelTarifas
 * REQUISITO: Herencia (extends JPanel).
 * Panel para mostrar y actualizar la tarifa eléctrica del sistema.
 */
public class PanelTarifas extends JPanel {

    // ATRIBUTOS
    private SimuladorTallerGUI mainApp;
    private SistemaGestion sistema;

    // Componentes gráficos
    private JLabel lblTarifaActual;
    private JTextField txtNuevaTarifa;
    private JButton btnActualizar;
    private JButton btnVolver;

    /**
     * MÉTODO: Constructor
     */
    public PanelTarifas(SimuladorTallerGUI mainApp) {
        this.mainApp = mainApp;
        this.sistema = mainApp.getSistema(); // Obtenemos el cerebro

        // CONFIGURAR LAYOUT
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // CREAR COMPONENTES
        
        // Fila 0: Mostrar Tarifa Actual
        gbc.gridx = 0; gbc.gridy = 0;
        this.add(new JLabel("Tarifa Actual ($ por kWh):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        this.lblTarifaActual = new JLabel("Cargando..."); // Valor temporal
        lblTarifaActual.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(lblTarifaActual, gbc);
        
        // Fila 1: Ingresar Nueva Tarifa
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new JLabel("Nueva Tarifa:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        this.txtNuevaTarifa = new JTextField(10);
        this.add(txtNuevaTarifa, gbc);

        // Fila 2: Botones
        // Panel interno para los botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        this.btnActualizar = new JButton("Actualizar");
        this.btnVolver = new JButton("Volver al Menú");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(panelBotones, gbc);

        // CARGAR DATOS INICIALES
        actualizarTarifaActual(); // Llama al método para poner el valor correcto

        // LÓGICA DE BOTONES
        
        // Acción para "Volver"
        btnVolver.addActionListener(e -> mainApp.mostrarPanel("MENU_PRINCIPAL"));
        
        // Acción para "Actualizar"
        btnActualizar.addActionListener(e -> actualizarTarifa());
    }

    /**
     * MÉTODO: actualizarTarifa
     * Lógica para tomar la nueva tarifa, validarla y pasarla al "cerebro".
     */
    private void actualizarTarifa() {
        // REQUISITO: Manejo de Excepciones (try-catch)
        try {
            // 1. Obtener y convertir el dato
            String tarifaStr = txtNuevaTarifa.getText().trim();
            if (tarifaStr.isEmpty()) {
                throw new MaquinaException("El campo de tarifa no puede estar vacío.");
            }
            
            double nuevaTarifa = Double.parseDouble(tarifaStr);
            
            // 2. Llamar al cerebro (que ya tiene la validación de > 0)
            sistema.setTarifa(nuevaTarifa);
            
            // 3. Éxito: Mostrar mensaje y actualizar la vista
            JOptionPane.showMessageDialog(this,
                    "Tarifa actualizada con éxito.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            
            actualizarTarifaActual(); 
            txtNuevaTarifa.setText(""); 
            
        } catch (NumberFormatException e) {
            // Error si el usuario escribió "abc"
            JOptionPane.showMessageDialog(this,
                    "Error: La tarifa debe ser un número válido (ej: 15.75).",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
                    
        } catch (MaquinaException ex) {
            // Error si el "cerebro" lanzó una alarma (ej: número negativo)
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), // "Error: La tarifa debe ser un valor positivo."
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * MÉTODO: actualizarTarifaActual
     * Método "helper" para leer la tarifa del "cerebro" y
     * ponerla en el JLabel 'lblTarifaActual'.
     */
    public void actualizarTarifaActual() {
        // Formateamos el número para que muestre solo 2 decimales
        String tarifaFormateada = String.format("%.2f", sistema.getTarifa());
        this.lblTarifaActual.setText("$ " + tarifaFormateada);
    }

} 