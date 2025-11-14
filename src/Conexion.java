import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    // Datos de conexión XAMMPP
    private static final String URL = "jdbc:mysql://localhost:3307/proyecto_calculo_energetico";
    private static final String USER = "root";
    private static final String PASS = ""; 

    // Método estático para obtener la conexión
    public static Connection getConexion() {
        Connection con = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Intentamos conectar
            con = DriverManager.getConnection(URL, USER, PASS);
            
            // System.out.println("¡Conexión exitosa a la Base de Datos!");

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el Driver MySQL.", "Error BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a MySQL. Verifique XAMPP.", "Error BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return con;
    }
    
    public static void main(String[] args) {
        Connection prueba = Conexion.getConexion();
        if (prueba != null) {
            JOptionPane.showMessageDialog(null, "¡Conexión Exitosa!");
        }
    }
}