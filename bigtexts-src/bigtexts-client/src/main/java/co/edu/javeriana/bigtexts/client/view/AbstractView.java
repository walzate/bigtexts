package co.edu.javeriana.bigtexts.client.view;

import co.edu.javeriana.bigtexts.client.controller.AbstractController;
import org.apache.log4j.Logger;

/**
 * Clase abstracta que debe ser extendida por todos las vistas (patrón MVC) del
 * sistema, ofrece utilidades comunes a todos las vistas
 *
 * @author Wilson Alzate Calderón
 */
public abstract class AbstractView extends javax.swing.JFrame {

    /**
     * Log de la aplicación
     */
    private static final Logger logger = Logger.getLogger(
            AbstractController.class.getName());
    
    /**
     *
     */
    public AbstractView() {
    }
    
    /**
     * Método que permite el acceso al log
     *
     * @return La instancia del log
     */
    public Logger getLogger() {
        return logger;
    }
}
