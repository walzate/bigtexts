package co.edu.javeriana.bigtexts.client.main;

import co.edu.javeriana.bigtexts.client.controller.AbstractController;
import co.edu.javeriana.bigtexts.client.controller.HomeController;
import co.edu.javeriana.bigtexts.client.view.AbstractView;
import co.edu.javeriana.bigtexts.client.view.HomeView;

/**
 * Clase principal de la GUI de BigTexts
 *
 * @author Wilson Alzate Calderón
 */
public class BigTextsClient {

    /**
     * Método que lanza la GUI de BigTexts
     *
     * @param args Parámetros de la inicialización de la GUI
     */
    public static void main(String[] args) {
        AbstractView view;
        AbstractController controller;

        view = new HomeView();
        view.setVisible(true);

        controller = new HomeController(null, view);
        controller.control();        
    }
}
