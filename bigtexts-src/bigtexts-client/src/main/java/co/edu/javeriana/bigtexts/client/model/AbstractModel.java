package co.edu.javeriana.bigtexts.client.model;

import co.edu.javeriana.bigtexts.client.controller.AbstractController;
import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import co.edu.javeriana.bigtexts.dto.PreprocessingTasksCatalog;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import co.edu.javeriana.bigtexts.util.XMLUtils;
import org.apache.log4j.Logger;

/**
 * Clase abstracta que debe ser extendida por todos los modelos (patrón MVC) del
 * sistema, ofrece utilidades comunes a todos los modelos
 *
 * @author Wilson Alzate Calderón
 */
public abstract class AbstractModel {

    /**
     * Log de la aplicación
     */
    private static final Logger logger = Logger.getLogger(
            AbstractController.class.getName());
    /**
     * Es el objeto que encapsula todas las parametrizaciones del proceso que el
     * usuario quiere ejecutar (tareas de preprocesamiento sobre archivos)
     */
    private Pipeline pipeline;
    /**
     * Es el catálogo de tareas de preprocesamiento con que cuenta el sistema,
     * se cargan de un XML que parametriza el usuario
     */
    private static PreprocessingTasksCatalog preprocessingTasksCatalog;

    /**
     * Método que permite el acceso al log
     *
     * @return La instancia del log
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Método que crea una sola instancia del pipeline y permite su acceso
     *
     * @return EL objeto Pipeline que se lleva en el sistema
     */
    public Pipeline getPipeline() {
        if (pipeline == null) {
            pipeline = new Pipeline();
        }
        return pipeline;
    }

    /**
     * Método que permite la modificación del objeto pipeline
     *
     * @param pipeline El nuevo objeto
     */
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * Método singleton para el acceso al catálogo de tareas de preprocesamiento
     *
     * @return La instancia del catálogo
     */
    public static PreprocessingTasksCatalog getPreprocessingTasksCatalog() {
        if (preprocessingTasksCatalog == null) {
            preprocessingTasksCatalog = (PreprocessingTasksCatalog) XMLUtils.unmarshalObjectFromFile(PreprocessingTasksCatalog.class, BigTextsConstants.BIG_TEXTS_PREPROCESSING_CATALOG);
            System.out.println(preprocessingTasksCatalog);
        }
        return preprocessingTasksCatalog;
    }

    /**
     * Método que dado el nombre de una tarea de preprocesamiento la consulta en
     * el catálogo y retorna su instancia
     *
     * @param name nombre por el cual se consulta la tarea de preprocesamieto
     * @return La instancia de la tarea de preprocesamiento si la encuentra,
     * null en caso contrario
     */
    public PreprocessingTask getPreprocessingTaskFromName(String name) {
        PreprocessingTask result = null;
        for (PreprocessingTask preprocessingTask : getPreprocessingTasksCatalog().getPreprocessingTasks()) {
            if (preprocessingTask.getName().equals(name)) {
                result = preprocessingTask;
            }
        }
        return result;
    }
}
