import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * REQUISITO: Patrón de Diseño (DAO), Persistencia MySQL, Excepciones SQL, ArrayList.
 * Implementa el contrato IMaquinaDAO.
 * Contiene toda la lógica SQL para interactuar con la tabla 'maquinas'.
 */
public class MaquinaDAO implements IMaquinaDAO {

    @Override
    public void guardar(Maquina maquina) throws Exception {
        // Definir la consulta SQL
        // Usamos PreparedStatement para evitar Inyección SQL
        String sql = "INSERT INTO maquinas (nombre, potencia_watts, estado) VALUES (?, ?, ?)";
        
        // Usar try-with-resources para asegurar que la conexión se cierre
        // REQUISITO: Correcta aplicación de excepciones para MySQL
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Asignar valores a los '?'
            stmt.setString(1, maquina.getNombre());
            stmt.setDouble(2, maquina.getPotenciaEnWatts());
            stmt.setString(3, maquina.getEstado());
            
            // Ejecutar la consulta
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            // Capturamos la excepción SQL y la re-lanzamos 
            throw new Exception("Error al guardar la máquina en la BD: " + e.getMessage());
        }
    }

    @Override
    public List<Maquina> listarTodas() throws Exception {
        // REQUISITO: Utilización complementaria de ArrayList
        List<Maquina> lista = new ArrayList<>();
        String sql = "SELECT * FROM maquinas";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // REQUISITO: Estructuras Repetitivas
            // Recorrer los resultados
            while (rs.next()) {
                // Crear un objeto Maquina por cada fila
                String nombre = rs.getString("nombre");
                double potencia = rs.getDouble("potencia_watts");
                String estado = rs.getString("estado");
                
                Maquina maquina = new Maquina(nombre, potencia);
                maquina.setEstado(estado);
                
                // Añadir el objeto a la lista
                lista.add(maquina);
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar las máquinas: " + e.getMessage());
        }
        
        // Devolver la lista
        return lista;
    }
}