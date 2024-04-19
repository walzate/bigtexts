package co.edu.javeriana.bigtexts.persistence;

import co.edu.javeriana.bigtexts.entities.BigtextsFile;
import co.edu.javeriana.bigtexts.entities.ExecutionLog;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import java.io.File;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Gestor de conexión con la base de datos
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class PersistenceManager {

    /**
     * La instancia singleton del entityManager
     */
    private static EntityManager em;
    /**
     * La instancia singleton de la fábrica de entity managers
     */
    private static EntityManagerFactory emf;

    /**
     * Método que almacena en la BD un log de ejecución
     *
     * @param executionLog El objeto a almacenar
     */
    public static void persistExecutionLog(ExecutionLog executionLog) {
        getEm().getTransaction().begin();
        getEm().persist(executionLog);
        getEm().getTransaction().commit();
    }

    /**
     * Método que almacena la información de los archivos cargados en un pipeline por auditoría
     * @param executionLog El log padre
     * @param fileNames La lista de los nombres de arvhivos
     * @param targetFolder El folder en el cual se encuentran los archivos
     */
    public static void persistBigTextsFiles(ExecutionLog executionLog, List<String> fileNames, String targetFolder) {
        for(String fileName:fileNames){
            File file = new File(BigTextsConstants.BIG_DFAULT_FTP_FOLDER + targetFolder + "/" + fileName);
            BigtextsFile bigTextsFile = new BigtextsFile();
            bigTextsFile.setIdExecutionLog(executionLog);
            bigTextsFile.setName(fileName);
            //http://www.mkyong.com/java/how-to-get-file-size-in-java/
            bigTextsFile.setWeight(file.length());
            persistBigTextsFile(bigTextsFile);            
        }
    }
    
    /**
     * Método que almacena los datos un archivo de los que se procesan en
     * bigtexts para auditoria
     *
     * @param bigTextsFile El objeto a almacenar
     */
    public static void persistBigTextsFile(BigtextsFile bigTextsFile) {
        getEm().getTransaction().begin();
        getEm().persist(bigTextsFile);
        getEm().getTransaction().commit();
    }

    /**
     * Método que elimina un log por el id
     *
     * @param id Llave primaria del log
     */
    public static void removeExecutionLog(Integer id) {
        ExecutionLog found = getEm().find(ExecutionLog.class, id);
        getEm().getTransaction().begin();
        getEm().remove(found);
        getEm().getTransaction().commit();
    }

    /**
     * Método que retorna todos los logs
     *
     * @return La lista de logs
     */
    public static List<ExecutionLog> getAllExecutionLogs() {
        List<ExecutionLog> result;
        Query query = getEm().createNamedQuery("ExecutionLog.findAll");
        result = query.getResultList();
        return result;
    }

    /**
     * Método que retorna una sóla instancia del Entity Manager
     *
     * @return La instancia singleton
     */
    private static EntityManager getEm() {
        if (em == null) {
            em = getEmf().createEntityManager();
        }
        return em;
    }

    /**
     * Método que retorna una sóla instancia del Entity Manager Factory
     *
     * @return La instancia singleton
     */
    private static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(BigTextsConstants.BIG_TEXTS_PERSISTENCE_UNIT);
        }
        return emf;
    }

    /**
     * Método que realiza la desconexión tanto del entity manager y del entity
     * manager factory
     */
    public static void disconnect() {
        getEm().close();
        getEmf().close();
    }
}
