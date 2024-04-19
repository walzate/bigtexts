package co.edu.javeriana.bigtexts.client.controller;

import co.edu.javeriana.bigtexts.client.model.AbstractModel;
import co.edu.javeriana.bigtexts.client.model.UploadDocumentsModel;
import co.edu.javeriana.bigtexts.client.view.AbstractView;
import co.edu.javeriana.bigtexts.client.view.HomeView;
import co.edu.javeriana.bigtexts.client.view.UploadDocumentsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador asociado a la pantalla Home del sistema
 *
 * @author Wilson Alzate Calderón
 */
public class HomeController extends AbstractController {

    /**
     * El listener del click en el botón de la pantalla Home
     */
    private ActionListener actionListener;

    /**
     * Constructor en el que se asocia el modelo y la vista al controlador
     *
     * @param model El modelo a asociar
     * @param view La vista a asociar
     */
    public HomeController(AbstractModel model, AbstractView view) {
        super(model, view);
    }

    /**
     * Método en el cual se ejercen las tareas de control sobre la vista y el
     * modelo
     */
    @Override
    public void control() {
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                startButtonActionPerformed(actionEvent);
            }
        };
        if (getView() instanceof HomeView) {
            ((HomeView) getView()).getStartButton().addActionListener(actionListener);
        }

    }

    /**
     * Acción relacionada al evento click del botón iniciar de la pantalla Home
     * Su objetivo es pasar a la pantalla de carga de documentos
     *
     * @param evt El evento click
     */
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
        getView().setVisible(false);

        AbstractModel model;
        AbstractView view;
        AbstractController controller;

        model = new UploadDocumentsModel();
        view = new UploadDocumentsView();

        view.setVisible(true);

        controller = new UploadDocumentsController(model, view);
        controller.control();
    }
}
