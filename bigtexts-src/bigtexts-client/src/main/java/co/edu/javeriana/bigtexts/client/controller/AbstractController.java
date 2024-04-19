package co.edu.javeriana.bigtexts.client.controller;

import co.edu.javeriana.bigtexts.client.model.AbstractModel;
import co.edu.javeriana.bigtexts.client.view.AbstractView;
import org.apache.log4j.Logger;

/**
 * Clase abstracta que debe ser extendida por todos los controladores (patrón
 * MVC) del sistema, ofrece utilidades comunes a todos los controladores
 *
 * @author Wilson Alzate Calderón
 */
public abstract class AbstractController {

    /**
     * El modelo asociado al controlador
     */
    private AbstractModel model;
    /**
     * La vista asociada al controlador
     */
    private AbstractView view;
    /**
     * La instancia del log
     */
    private static final Logger logger = Logger.getLogger(
            AbstractController.class.getName());

    /**
     * Constructor en el que se asocia el modelo y la vista al controlador
     *
     * @param model El modelo a asociar
     * @param view La vista a asociar
     */
    public AbstractController(AbstractModel model, AbstractView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Método en el cual se ejercen las tareas de control sobre la vista y el
     * modelo
     */
    public abstract void control();

    /**
     * Método que permite el acceso al modelo asociado
     *
     * @return El modelo asociado
     */
    public AbstractModel getModel() {
        return model;
    }

    /**
     * Método que permite la modificación al modelo asociado
     *
     * @param model El nuevo modelo a asociar
     */
    public void setModel(AbstractModel model) {
        this.model = model;
    }

    /**
     * Método que permite el acceso a la vista asociada
     *
     * @return La vista asociada
     */
    public AbstractView getView() {
        return view;
    }

    /**
     * Método que permite la modificación de la vista asociada
     *
     * @param view La nueva vista a asociar
     */
    public void setView(AbstractView view) {
        this.view = view;
    }

    /**
     * Método que permite acceder al log
     *
     * @return Instancia del Logger
     */
    public Logger getLogger() {
        return logger;
    }
}
