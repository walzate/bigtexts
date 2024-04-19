package co.edu.javeriana.bigtexts.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa La lista de tareas de preprocesamiento disponibles en el
 * sistema
 *
 * @author Wilson Alzate Calderón
 */
@XmlRootElement
public class PreprocessingTasksCatalog {

    /**
     * La lista de tareas de preprocesamiento disponibles en el sistema
     */
    private List<PreprocessingTask> preprocessingTasks;

    /**
     * Método que permite modificar La lista de tareas de preprocesamiento
     * disponibles en el sistema
     *
     * @return La lista de tareas de preprocesamiento disponibles en el sistema
     */
    public List<PreprocessingTask> getPreprocessingTasks() {
        return preprocessingTasks;
    }

    /**
     * Método que permite modificar el valor de la lista de tareas de
     * preprocesamiento disponibles en el sistema
     *
     * @param preprocessingTasksCatalog la nueva lista de tareas de
     * preprocesamiento disponibles en el sistema
     */
    @XmlElement(name = "preprocessingTask")
    @XmlElementWrapper
    public void setPreprocessingTasks(List<PreprocessingTask> preprocessingTasksCatalog) {
        this.preprocessingTasks = preprocessingTasksCatalog;
    }

    /**
     * Método que escribe en texto el contenido del objeto
     *
     * @return Un String con el contenido del objeto
     */
    @Override
    public String toString() {
        return "PreprocessingTasksCatalog{" + "preprocessingTasks=" + preprocessingTasks + '}';
    }
}
