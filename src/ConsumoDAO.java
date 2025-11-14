
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * REQUISITO: Patrón DAO, Persistencia MySQL, Excepciones SQL, ArrayList.
 * Implementa la lógica SQL para la tabla 'consumos'.
 */
public class ConsumoDAO implements IConsumoDAO {

    @Override
    public void guardar(Consumo consumo) throws Exception {
        // Usamos el nombre de la tabla que definimos en el SQL
        String sql = "INSERT INTO consumos (nombre_maquina, fecha, horas_uso, kwh_consumidos, costo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, consumo.getNombreMaquina());
            stmt.setDate(2, Date.valueOf(consumo.getFecha())); // Convertir LocalDate a java.sql.Date
            stmt.setDouble(3, consumo.getHorasDeUso());
            stmt.setDouble(4, consumo.getKwhConsumidos());
            stmt.setDouble(5, consumo.getCostoDelConsumo());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new Exception("Error al guardar el consumo en la BD: " + e.getMessage());
        }
    }

    @Override
    public List<Consumo> listarTodos() throws Exception {
        List<Consumo> lista = new ArrayList<>();
        String sql = "SELECT * FROM consumos ORDER BY fecha DESC"; // Ordenar por fecha

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Obtenemos los datos de la fila
                String nombre = rs.getString("nombre_maquina");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                double horas = rs.getDouble("horas_uso");
                double kwh = rs.getDouble("kwh_consumidos");
                double costo = rs.getDouble("costo");
                
                Consumo consumo = new Consumo(nombre, horas, kwh, costo, fecha);
                
                lista.add(consumo);
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar el historial de consumos: " + e.getMessage());
        }
        return lista;
    }
}