// Imports
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * ---------------------------------------------------------------------
 * CLASE: PanelConsumos
 * ---------------------------------------------------------------------
 * REQUISITO: Herencia (extends JPanel).
 * Panel para registrar nuevas horas de uso y ver el historial.
 */
public class PanelConsumos extends JPanel {

    // --- 1. ATRIBUTOS ---
    private SimuladorTallerGUI mainApp;
    private SistemaGestion sistema;

    // Componentes gráficos
    private JComboBox<Maquina> comboMaquinas; // Menú desplegable de Máquinas
    private JTextField txtHoras;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private JTable tablaConsumos;
    private DefaultTableModel tableModel;

    /**
     * ---------------------------------------------------------------------
     * MÉTODO: Constructor
     * ---------------------------------------------------------------------
     */
    public PanelConsumos(SimuladorTallerGUI mainApp) {
        this.mainApp = mainApp;
        this.sistema = mainApp.getSistema();

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 2. CREAR PANEL DEL FORMULARIO (NORTE) ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Consumo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0: Seleccionar Máquina
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Máquina:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        this.comboMaquinas = new JComboBox<>(); // Creamos el JComboBox
        panelFormulario.add(comboMaquinas, gbc);

        // Fila 1: Horas de Uso
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Horas de Uso:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        this.txtHoras = new JTextField(10);
        panelFormulario.add(txtHoras, gbc);

        // Fila 2: Botón Registrar
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        this.btnRegistrar = new JButton("Registrar Consumo");
        panelFormulario.add(btnRegistrar, gbc);
        
        this.add(panelFormulario, BorderLayout.NORTH);

        // --- 3. CREAR TABLA DEL HISTORIAL (CENTRO) ---
        JLabel lblHistorial = new JLabel("Historial de Consumos", SwingConstants.CENTER);
        lblHistorial.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] columnas = {"Fecha", "Máquina", "Horas", "Consumo (kWh)", "Costo ($)"};
        this.tableModel = new DefaultTableModel(columnas, 0) {
            // Hacemos que la tabla no sea editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tablaConsumos = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tablaConsumos);

        // Usamos un panel central para poner el título sobre la tabla
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(lblHistorial, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        
        this.add(panelCentro, BorderLayout.CENTER);
        
        // --- 4. PANEL DE NAVEGACIÓN (SUR) ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnVolver = new JButton("Volver al Menú");
        panelSur.add(btnVolver);
        this.add(panelSur, BorderLayout.SOUTH);

        // --- 5. LÓGICA DE BOTONES ---
        btnVolver.addActionListener(e -> mainApp.mostrarPanel("MENU_PRINCIPAL"));
        btnRegistrar.addActionListener(e -> registrarConsumo());
    }

    /**
     * ---------------------------------------------------------------------
     * MÉTODO: registrarConsumo
     * ---------------------------------------------------------------------
     * Llama al "cerebro" para guardar el nuevo consumo.
     */
    private void registrarConsumo() {
        // REQUISITO: Manejo de Excepciones (try-catch)
        try {
            // 1. Obtener la máquina seleccionada del JComboBox
            // (El JComboBox ahora almacena objetos 'Maquina')
            Maquina maquinaSeleccionada = (Maquina) comboMaquinas.getSelectedItem();
            
            // 2. Obtener las horas
            String horasStr = txtHoras.getText().trim();
            if (horasStr.isEmpty()) {
                throw new MaquinaException("Debe ingresar las horas de uso.");
            }
            
            double horas = Double.parseDouble(horasStr);
            
            // 3. Llamar al "Cerebro"
            // El cerebro se encarga de guardar en la BD
            sistema.registrarConsumo(maquinaSeleccionada, horas);
            
            // 4. Éxito
            JOptionPane.showMessageDialog(this, "Consumo registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // 5. Actualizar la vista y limpiar campos
            actualizarPanel(); // Refresca tanto el combo como la tabla
            txtHoras.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Las horas deben ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (MaquinaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime el error en la consola
        }
    }

    /**
     * ---------------------------------------------------------------------
     * MÉTODO: actualizarPanel
     * ---------------------------------------------------------------------
     * REQUISITO: Estructuras Repetitivas (bucles 'for')
     * Refresca TODOS los datos de este panel (Combo y Tabla) desde la BD.
     * Se debe llamar CADA VEZ que se muestra el panel.
     */
    public void actualizarPanel() {
        
        // --- 1. Actualizar el JComboBox (el menú desplegable) ---
        
        Object itemSeleccionado = comboMaquinas.getSelectedItem();
        comboMaquinas.removeAllItems(); // Borramos los items viejos
        
        // Llenamos el combo con la lista FRESCA de máquinas desde la BD
        List<Maquina> maquinas = sistema.getMaquinas();
        for (Maquina maquina : maquinas) {
            // (El JComboBox usará automáticamente el método 'toString()'
            // de la clase Maquina para mostrar el nombre)
            comboMaquinas.addItem(maquina); 
        }
        comboMaquinas.setSelectedItem(itemSeleccionado); // Re-selecciona


        // --- 2. Actualizar la JTable (el historial) ---
        
        tableModel.setRowCount(0); // Borramos las filas viejas
        
        // Llenamos la tabla con el historial FRESCO de consumos desde la BD
        List<Consumo> historial = sistema.getHistorialConsumos();
        for (Consumo consumo : historial) {
            Object[] fila = {
                consumo.getFecha(), // La fecha
                
                // --- ¡ESTA ES LA LÍNEA CORREGIDA! ---
                consumo.getNombreMaquina(), // Lee el String del nombre
                // --- FIN DE LA CORRECCIÓN ---
                
                consumo.getHorasDeUso(),
                String.format("%.2f", consumo.getKwhConsumidos()), // kWh con 2 decimales
                String.format("%.2f", consumo.getCostoDelConsumo()) // Costo con 2 decimales
            };
            tableModel.addRow(fila);
        }
    }

} 