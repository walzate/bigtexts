package co.edu.javeriana.bigtexts.dto;

import co.edu.javeriana.bigtexts.enums.DeliveryMethodEnum;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa todo lo que BigTexts va a ejecutar, las tareas de
 * preprocesamiento, los archivos, y la forma de entrega
 *
 * @author Wilson Alzate Calderón
 */
@XmlRootElement
public class Pipeline {

    /**
     * Es el directorio en el que se almacenan todos los archivos en hdfs
     */
    private String targetFolder;
    /**
     * La lista de archivos a cargar
     */
    private List<String> files;
    /**
     * La lista de tareas de preprocesamiento en el orden que se va a ejecutar
     */
    private List<PreprocessingTask> preprocessingTasks;
    /**
     * La forma de entrega del resultado
     */
    private DeliveryMethodEnum deliveryMethod;

    /**
     * método que permite acceder al directorio destino
     *
     * @return El directorio destino
     */
    public String getTargetFolder() {
        return targetFolder;
    }

    /**
     * método que permite modificar el directorio destino
     *
     * @param targetFolder El nuevo directorio de destino
     */
    @XmlElement
    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    /**
     * Método que permite acceder a la lista de archivos que se van a ejecutar
     *
     * @return la lista de nombres de archivos
     */
    public List<String> getFiles() {
        if(files == null){
            files = new ArrayList<String>();
        }
        return files;
    }

    /**
     * Método que permite modificar la lista de archivos que se van a ejecutar
     *
     * @param files la nueva lista
     */
    @XmlElement
    @XmlElementWrapper
    public void setFiles(List<String> files) {
        this.files = files;
    }

    /**
     * Método que permite acceder a la lista de tareas de preprocesamiento que
     * se van a ejectutar sobre los arvhivos
     *
     * @return La lista de tareas de preprocesamiento
     */
    public List<PreprocessingTask> getPreprocessingTasks() {
        return preprocessingTasks;
    }

    /**
     * Método que permite modificar la lista de tareas de preprocesamiento que
     * se van a ejectutar sobre los arvhivos
     *
     * @param preprocessingTasks la nueva lista de tareas
     */
    @XmlElement
    @XmlElementWrapper
    public void setPreprocessingTasks(List<PreprocessingTask> preprocessingTasks) {
        this.preprocessingTasks = preprocessingTasks;
    }

    /**
     * Método que permite acceder a la forma de envío
     *
     * @return El método de envío seleccionado por el usuario
     */
    public DeliveryMethodEnum getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Método que permite modificar la forma de envío
     *
     * @param deliveryMethod la nueva forma de envío
     */
    @XmlElement
    public void setDeliveryMethod(DeliveryMethodEnum deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    /**
     * Método que escribe en texto el contenido del objeto
     *
     * @return Un String con el contenido del objeto
     */
    @Override
    public String toString() {
        return "Pipeline{" + " targetFolder=" + targetFolder + ", files=" + files + ", preprocessingTasks=" + preprocessingTasks + ", deliveryMethod=" + deliveryMethod + '}';
    }
}
