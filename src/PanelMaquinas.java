// Imports
import javax.swing.*;
// Importamos 'DefaultTableModel' para manejar los datos de la tabla
import javax.swing.table.DefaultTableModel; 
import java.awt.*;
import java.awt.event.*;
import java.util.List; // Para usar la 'List' de máquinas

/**
 * CLASE: PanelMaquinas
 * REQUISITO: Herencia.
 * Esta clase hereda de JPanel.
 * Muestra el formulario para agregar máquinas y la tabla para listarlas.
 */
public class PanelMaquinas extends JPanel {

   // ATRIBUTOS
    private SimuladorTallerGUI mainApp; 
    private SistemaGestion sistema;    

    // Componentes gráficos
    private JTextField txtNombre;
    private JTextField txtPotencia;
    private JButton btnAgregar;
    private JButton btnVolver;

    // Componentes de la Tabla
    private JTable tablaMaquinas;
    private DefaultTableModel tableModel;

    /**
     * MÉTODO: Constructor
     * @param mainApp La instancia de la ventana principal
     */
    public PanelMaquinas(SimuladorTallerGUI mainApp) {
        this.mainApp = mainApp;
        // Obtenemos la instancia del cerebro desde la ventana principal
        this.sistema = mainApp.getSistema(); 

        // CONFIGURAR LAYOUT
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

       // CREAR PANEL DEL FORMULARIO (NORTE)
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Agregar Nueva Máquina"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        this.txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        // Fila 1: Potencia
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Potencia (en Watts):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        this.txtPotencia = new JTextField(20);
        panelFormulario.add(txtPotencia, gbc);

        // Fila 2: Botón Agregar
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        gbc.anchor = GridBagConstraints.CENTER;
        this.btnAgregar = new JButton("Agregar Máquina");
        panelFormulario.add(btnAgregar, gbc);
        
        // Añadimos el formulario al NORTE (arriba) del panel principal
        this.add(panelFormulario, BorderLayout.NORTH);

        // CREAR PANEL DE LA TABLA (CENTRO)
        // REQUISITO: Creación de Objetos
        
        // Definir las columnas
        String[] columnas = {"Nombre", "Potencia (W)", "Estado"};
        
        // Crear el "Modelo de la Tabla".
        this.tableModel = new DefaultTableModel(columnas, 0); 
        
        // Crear la JTable y pasarle el modelo
        this.tablaMaquinas = new JTable(tableModel);
        
        // Poner la tabla dentro de un JScrollPane
        // Esto hace que aparezcan las barras de scroll si hay muchas filas.
        JScrollPane scrollPane = new JScrollPane(tablaMaquinas);
        
        // Añadimos el scrollPane (que contiene la tabla) al CENTRO
        this.add(scrollPane, BorderLayout.CENTER);

        // CREAR PANEL DE NAVEGACIÓN (SUR)
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        this.btnVolver = new JButton("Volver al Menú");
        panelSur.add(btnVolver);
        
        // Añadimos el panel de navegación al SUR (abajo)
        this.add(panelSur, BorderLayout.SOUTH);

        // LÓGICA DE BOTONES (Listeners)
        
        // Acción para el botón "Volver"
        btnVolver.addActionListener(e -> mainApp.mostrarPanel("MENU_PRINCIPAL"));
        
        // Acción para el botón "Agregar"
        btnAgregar.addActionListener(e -> agregarMaquina());
    }

    /**
     * MÉTODO: agregarMaquina
     * Lógica para tomar los datos del formulario, pasarlos al "cerebro"
     * y actualizar la tabla.
     */
    private void agregarMaquina() {
        // Obtener los datos de los campos de texto
        String nombre = txtNombre.getText().trim(); 
        String potenciaStr = txtPotencia.getText().trim();
        
        // REQUISITOS: Manejo de Excepciones y Estructuras de Control (try-catch)
        try {
            // Validar entrada (aquí, no en el cerebro)
            if (nombre.isEmpty() || potenciaStr.isEmpty()) {
                throw new MaquinaException("El nombre y la potencia son obligatorios.");
            }
            
            double potencia;
            try {
                // Convertimos el texto de potencia a un número
                potencia = Double.parseDouble(potenciaStr);
            } catch (NumberFormatException e) {
                // Si el usuario escribe "abc" en lugar de "123"
                throw new MaquinaException("La potencia debe ser un número válido.");
            }

            // Llamar al cerebro 
            sistema.agregarMaquina(nombre, potencia);
            
            // Si todo salió bien, actualizar la tabla
            actualizarTabla();
            
            // Limpiar los campos de texto
            txtNombre.setText("");
            txtPotencia.setText("");
            
            // 6. Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "¡Máquina '" + nombre + "' agregada con éxito!", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (MaquinaException ex) {
            // REQUISITO: Manejo de Excepciones
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), // Mostramos el mensaje de la excepción
                "Error al Agregar Máquina", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * MÉTODO: actualizarTabla
     * REQUISITO: Estructuras Repetitivas (bucle for)
     * Borra la tabla y la vuelve a llenar con los datos
     * actualizados del cerebro (SistemaGestion).
     */
    public void actualizarTabla() {
        // Borrar todas las filas existentes
        tableModel.setRowCount(0); 
        
        // Obtener la lista fresca de máquinas desde el "cerebro"
        List<Maquina> maquinas = sistema.getMaquinas();
        
        // REQUISITO: Bucle 'for-each'
        // Recorremos la lista y añadimos cada máquina como una fila nueva
        for (Maquina maquina : maquinas) {
            Object[] fila = {
                maquina.getNombre(),
                maquina.getPotenciaEnWatts(),
                maquina.getEstado()
            };
            tableModel.addRow(fila);
        }
    }
}