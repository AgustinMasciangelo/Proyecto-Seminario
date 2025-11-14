import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * REQUISITO: Patrón DAO, Persistencia MySQL, Excepciones SQL.
 * Maneja la lectura y escritura de la tarifa en la tabla 'tarifas'.
 */
public class TarifaDAO {

    /**
     * Obtiene la tarifa actual de la base de datos.
     * @return El valor (double) del costo por kWh.
     * @throws Exception Si ocurre un error de SQL.
     */
    public double getTarifaActual() throws Exception {
        String sql = "SELECT costo_kwh FROM tarifas WHERE id_tarifa = 1";
        double tarifa = 12.50; // Valor por defecto si la BD está vacía

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                tarifa = rs.getDouble("costo_kwh");
            }
        } catch (SQLException e) {
            throw new Exception("Error al leer la tarifa desde la BD: " + e.getMessage());
        }
        return tarifa;
    }

    /**
     * Actualiza la tarifa en la base de datos.
     * @param nuevaTarifa El nuevo costo por kWh.
     * @throws Exception Si ocurre un error de SQL.
     */
    public void actualizarTarifa(double nuevaTarifa) throws Exception {
        String sql = "UPDATE tarifas SET costo_kwh = ? WHERE id_tarifa = 1";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, nuevaTarifa);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new Exception("Error al actualizar la tarifa en la BD: " + e.getMessage());
        }
    }
}