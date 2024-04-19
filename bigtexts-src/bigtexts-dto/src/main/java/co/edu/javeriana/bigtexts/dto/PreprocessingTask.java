package co.edu.javeriana.bigtexts.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Clase que representa las tareas de preprocesamiento, usada tanto para el
 * catálogo como para establecer las tareas de preprocesamiento que se van a
 * ejecutar
 *
 * @author Wilson Alzate Calderón
 */
@XmlRootElement
@XmlType(propOrder = {"name", "requiredJars", "implementationClass", "commands", "parameters"})
public class PreprocessingTask {

    /**
     * Nombre con el cual se va a identificar a la tarea de preprocesamiento
     */
    private String name;
    /**
     * Lista de las librerías necesarias para ejecutar la tarea de
     * preprocesamiento
     */
    private List<String> requiredJars;
    /**
     * Es la lista de comandos, usualmente UDFs, que se ejecutan para la tarea
     * de preprocesamiento
     */
    private List<String> commands;
    /**
     * Lista de parámetros que se le pueden establecer a la tarea de
     * preprocesamiento
     */
    private List<PreprocessingTaskParameter> parameters;
    
    /**
     * Nombre cualificado de la clase que implementa el UDF
     */
    private String implementationClass;

    /**
     * Método accesor del nombre de la tarea de preprocesamiento
     *
     * @return el nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Método que modifica el nombre de la tarea de preprocesamiento
     *
     * @param name 
     */
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que retorna la lista de librerías requeridas
     *
     * @return la lista
     */
    public List<String> getRequiredJars() {
        return requiredJars;
    }

    /**
     * Método que modifica la Lista de las librerías necesarias para ejecutar la
     * tarea de preprocesamiento
     *
     * @param requiredJars Lista de las librerías necesarias para ejecutar la
     * tarea de preprocesamiento
     */
    @XmlElement(name = "requiredJar")
    @XmlElementWrapper
    public void setRequiredJars(List<String> requiredJars) {
        this.requiredJars = requiredJars;
    }

    /**
     * Método que accede a la lista de comandos, usualmente UDFs, que se
     * ejecutan para la tarea de preprocesamiento
     *
     * @return La lista
     */
    public List<String> getCommands() {
        return commands;
    }

    /**
     * Método que modifica la lista de comandos, usualmente UDFs, que se
     * ejecutan para la tarea de preprocesamiento
     *
     * @param commands La nueva lista de comandos, usualmente UDFs, que se
     * ejecutan para la tarea de preprocesamiento
     */
    @XmlElement(name = "command")
    @XmlElementWrapper
    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    /**
     * Método que permite acceder a la Lista de parámetros que se le pueden
     * establecer a la tarea de preprocesamiento
     *
     * @return la la Lista de parámetros que se le pueden establecer a la tarea
     * de preprocesamiento
     */
    public List<PreprocessingTaskParameter> getParameters() {
        return parameters;
    }

    /**
     * Método que permite modificar la Lista de parámetros que se le pueden
     * establecer a la tarea de preprocesamiento
     *
     * @param parameters la Lista de parámetros que se le pueden establecer a la
     * tarea de preprocesamiento
     */
    @XmlElement(name = "parameter")
    @XmlElementWrapper
    public void setParameters(List<PreprocessingTaskParameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Método que retorna el valor de la clase que implementa el UDF
     * @return el valor de la clase que implementa el UDF
     */
    public String getImplementationClass() {
        return implementationClass;
    }

    /**
     * Método que modifica el valor de la clase que implementa el UDF
     * @param implementationClass el nuevo valor de la clase que implementa el UDF
     */
    @XmlElement
    public void setImplementationClass(String implementationClass) {
        this.implementationClass = implementationClass;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 41 * hash + (this.requiredJars != null ? this.requiredJars.hashCode() : 0);
        hash = 41 * hash + (this.commands != null ? this.commands.hashCode() : 0);
        hash = 41 * hash + (this.parameters != null ? this.parameters.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PreprocessingTask other = (PreprocessingTask) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.requiredJars != other.requiredJars && (this.requiredJars == null || !this.requiredJars.equals(other.requiredJars))) {
            return false;
        }
        if (this.commands != other.commands && (this.commands == null || !this.commands.equals(other.commands))) {
            return false;
        }
        if (this.parameters != other.parameters && (this.parameters == null || !this.parameters.equals(other.parameters))) {
            return false;
        }
        return true;
    }   
    
    /**
     * Método que escribe en texto el contenido del objeto
     *
     * @return Un String con el contenido del objeto
     */
    @Override
    public String toString() {
        return "PreprocessingTask{" + "name=" + name + ", requiredJars=" + requiredJars + ", commands=" + commands + ", parameters=" + parameters + '}';
    }
}
