import java.util.List;

/**
 * REQUISITO: Inclusión pertinente de clases abstractas o interfaces.
 * Contrato para las operaciones de persistencia de la entidad Consumo.
 */
public interface IConsumoDAO {
    
    /**
     * Guarda un nuevo registro de consumo en la BD.
     * @param consumo El objeto Consumo a guardar.
     * @throws Exception Si ocurre un error de SQL.
     */
    void guardar(Consumo consumo) throws Exception;
    
    /**
     * Devuelve una lista de todo el historial de consumos.
     * @return Una lista de objetos Consumo.
     * @throws Exception Si ocurre un error de SQL.
     */
    List<Consumo> listarTodos() throws Exception;
}