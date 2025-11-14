import java.util.List;

public interface IMaquinaDAO {
    
    /**
     * Guarda una nueva máquina en la base de datos.
     * @param maquina El objeto Maquina a guardar.
     * @throws Exception Si ocurre un error de SQL.
     */
    void guardar(Maquina maquina) throws Exception;
    
    /**
     * Devuelve una lista de todas las máquinas de la base de datos.
     * @return Una lista de objetos Maquina.
     * @throws Exception 
     */
    List<Maquina> listarTodas() throws Exception;
    

}