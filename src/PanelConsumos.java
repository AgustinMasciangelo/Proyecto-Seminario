// Imports
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * CLASE: PanelConsumos
 * REQUISITO: Herencia (extends JPanel).
 * Panel para registrar nuevas horas de uso y ver el historial.
 */
public class PanelConsumos extends JPanel {

    // ATRIBUTOS
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
     * MÉTODO: Constructor
     */
    public PanelConsumos(SimuladorTallerGUI mainApp) {
        this.mainApp = mainApp;
        this.sistema = mainApp.getSistema();

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // CREAR PANEL DEL FORMULARIO (NORTE)
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Consumo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0: Seleccionar Máquina
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Máquina:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        this.comboMaquinas = new JComboBox<>();
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

        // CREAR TABLA DEL HISTORIAL (CENTRO)
        JLabel lblHistorial = new JLabel("Historial de Consumos", SwingConstants.CENTER);
        lblHistorial.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] columnas = {"Fecha", "Máquina", "Horas", "Consumo (kWh)", "Costo ($)"};
        this.tableModel = new DefaultTableModel(columnas, 0);
        this.tablaConsumos = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tablaConsumos);

        // Usamos un panel central para poner el título sobre la tabla
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(lblHistorial, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        
        this.add(panelCentro, BorderLayout.CENTER);
        
        // PANEL DE NAVEGACIÓN (SUR)
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnVolver = new JButton("Volver al Menú");
        panelSur.add(btnVolver);
        this.add(panelSur, BorderLayout.SOUTH);

        // LÓGICA DE BOTONES 
        btnVolver.addActionListener(e -> mainApp.mostrarPanel("MENU_PRINCIPAL"));
        btnRegistrar.addActionListener(e -> registrarConsumo());
    }

    /**
     * MÉTODO: registrarConsumo
     * Llama al "cerebro" para guardar el nuevo consumo.
     */
    private void registrarConsumo() {
        // REQUISITO: Manejo de Excepciones (try-catch)
        try {
            // 1. Obtener la máquina seleccionada del JComboBox
            Maquina maquinaSeleccionada = (Maquina) comboMaquinas.getSelectedItem();
            
            // 2. Obtener las horas
            String horasStr = txtHoras.getText().trim();
            if (horasStr.isEmpty()) {
                throw new MaquinaException("Debe ingresar las horas de uso.");
            }
            
            double horas = Double.parseDouble(horasStr);
            
            // 3. Llamar al "Cerebro"
            sistema.registrarConsumo(maquinaSeleccionada, horas);
            
            // 4. Éxito
            JOptionPane.showMessageDialog(this, "Consumo registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // 5. Actualizar la vista y limpiar campos
            actualizarPanel();
            txtHoras.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Las horas deben ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (MaquinaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * MÉTODO: actualizarPanel
     * REQUISITO: Estructuras Repetitivas (bucles 'for')
     * Refresca TODOS los datos de este panel.
     * Se debe llamar CADA VEZ que se muestra el panel.
     */
    public void actualizarPanel() {
        // Actualizar el JComboBox (el menú desplegable)
        
        // Guardamos el item que estaba seleccionado (si había uno)
        Object itemSeleccionado = comboMaquinas.getSelectedItem();
        
        // Borramos todos los items viejos
        comboMaquinas.removeAllItems();
        
        // REQUISITO: Bucle 'for-each'
        // Volvemos a llenar el combo con la lista FRESCA de máquinas
        List<Maquina> maquinas = sistema.getMaquinas();
        for (Maquina maquina : maquinas) {
            // Usamos el "toString()" de la clase Maquina automáticamente
            comboMaquinas.addItem(maquina); 
        }
        
        comboMaquinas.setSelectedItem(itemSeleccionado);


        // Actualizar la JTable (el historial)
        
        // Borramos las filas viejas
        tableModel.setRowCount(0);
        
        // REQUISITO: Bucle 'for-each'
        // Llenamos la tabla con el historial FRESCO de consumos
        List<Consumo> historial = sistema.getHistorialConsumos();
        for (Consumo consumo : historial) {
            Object[] fila = {
                consumo.getFecha(), // La fecha
                consumo.getMaquina().getNombre(), // El nombre de la máquina
                consumo.getHorasDeUso(),
                String.format("%.2f", consumo.getKwhConsumidos()), // kWh con 2 decimales
                String.format("%.2f", consumo.getCostoDelConsumo()) // Costo con 2 decimales
            };
            tableModel.addRow(fila);
        }
    }

} 