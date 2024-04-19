package co.edu.javeriana.bigtexts.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa los parámetros asociados a las tareas de
 * preprocesamiento que se usan tanto para el catálogo como para establecer las
 * tareas de preprocesamiento que se van a ejecutar
 *
 * @author Wilson Alzate Calderón
 */
@XmlRootElement
public class PreprocessingTaskParameter {

    /**
     * Nombre de la variable con el cual se va a identificar el parámetro de la
     * tarea de preprocesamiento
     */
    private String name;
    /**
     * Nombre para mostrar del parámetro de la tarea de preprocesamiento
     */
    private String label;
    /**
     * Valor de la variable con el cual se va a identificar el parámetro de la
     * tarea de preprocesamiento
     */
    private String value;
    /**
     * Tipo de dato de la variable con el cual se va a identificar el parámetro
     * de la tarea de preprocesamiento
     */
    private String type;
    /**
     * Valor por defecto de la variable con el cual se va a identificar el
     * parámetro de la tarea de preprocesamiento
     */
    private String defaultValue;
    /**
     * Descripción del parámetro
     */
    private String description;

    /**
     * Método que permite acceder al Nombre de la variable con el cual se va a
     * identificar el parámetro de la tarea de preprocesamiento
     *
     * @return El Nombre de la variable con el cual se va a identificar el
     * parámetro de la tarea de preprocesamiento
     */
    public String getName() {
        return name;
    }

    /**
     * Método que permite modificar el Nombre de la variable con el cual se va a
     * identificar el parámetro de la tarea de preprocesamiento
     *
     * @param name nuevo nombre de la variable con el cual se va a identificar
     * el parámetro de la tarea de preprocesamiento
     */
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que permite acceder al Nombre para mostrar del parámetro de la
     * tarea de preprocesamiento
     *
     * @return El Nombre para mostrar del parámetro de la tarea de
     * preprocesamiento
     */
    public String getLabel() {
        return label;
    }

    /**
     * Método que permite modificar el Nombre para mostrar del parámetro de la
     * tarea de preprocesamiento
     *
     * @param label Nuevo nombre para mostrar del parámetro de la tarea de
     * preprocesamiento
     */
    @XmlElement
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Método que permite acceder al valor de la variable con el cual se va a
     * identificar el parámetro de la tarea de preprocesamiento
     *
     * @return el valor de la variable con el cual se va a identificar el
     * parámetro de la tarea de preprocesamiento
     */
    public String getValue() {
        return value;
    }

    /**
     * Método que permite modificar el valor de la variable con el cual se va a
     * identificar el parámetro de la tarea de preprocesamiento
     *
     * @param value El nuevo valor de la variable con el cual se va a
     * identificar el parámetro de la tarea de preprocesamiento
     */
    @XmlElement
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Método que permite acceder al Tipo de dato de la variable con el cual se
     * va a identificar el parámetro de la tarea de preprocesamiento
     *
     * @return el Tipo de dato de la variable con el cual se va a identificar el
     * parámetro de la tarea de preprocesamiento
     */
    public String getType() {
        return type;
    }

    /**
     * Método que permite acceder al tipo de dato de la variable con el cual se
     * va a identificar el parámetro de la tarea de preprocesamiento
     *
     * @param type el nuevo tipo de dato de la variable con el cual se va a
     * identificar el parámetro de la tarea de preprocesamiento
     */
    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Método que permite acceder al valor por defecto de la variable con el
     * cual se va a identificar el parámetro de la tarea de preprocesamiento
     *
     * @return el valor por defecto de la variable con el cual se va a
     * identificar el parámetro de la tarea de preprocesamiento
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Método que permite modificar el valor por defecto de la variable con el
     * cual se va a identificar el parámetro de la tarea de preprocesamiento
     *
     * @param defaultValue el nuevo valor por defecto de la variable con el cual
     * se va a identificar el parámetro de la tarea de preprocesamiento
     */
    @XmlElement
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Método que permite acceder a la descripción del parámetro
     * @return La descripción del parámetro
     */
    public String getDescription() {
        return description;
    }

    /**
     * Método que permite modificar a la descripción del parámetro
     * @param description La nueva descripción del parámetro
     */
    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }   
    
    /**
     * Método que escribe en texto el contenido del objeto
     *
     * @return Un String con el contenido del objeto
     */
    @Override
    public String toString() {
        return "PreprocessingTaskParameter{" + "name=" + name + ", value=" + value + ", type=" + type + ", defaultValue=" + defaultValue + '}';
    }
}
