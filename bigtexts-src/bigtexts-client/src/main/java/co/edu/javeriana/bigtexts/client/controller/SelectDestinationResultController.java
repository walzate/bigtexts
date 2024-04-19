package co.edu.javeriana.bigtexts.client.controller;

import co.edu.javeriana.bigtexts.client.model.AbstractModel;
import co.edu.javeriana.bigtexts.client.model.SelectDestinationResultModel;
import co.edu.javeriana.bigtexts.client.model.SelectPreprocessingTasksModel;
import co.edu.javeriana.bigtexts.client.view.AbstractView;
import co.edu.javeriana.bigtexts.client.view.SelectDestinationResultView;
import co.edu.javeriana.bigtexts.client.view.SelectPreprocessingTasksView;
import co.edu.javeriana.bigtexts.enums.DeliveryMethodEnum;
import co.edu.javeriana.bigtexts.util.XMLUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador asociada a la pantalla de selección de destino del resultado en el
 * sistema (CU005-Execute preprocessing tasks)
 *
 * @author Wilson Alzate Calderón
 */
public class SelectDestinationResultController extends AbstractController {

    /**
     * Constructor en el que se asocia el modelo y la vista al controlador
     *
     * @param model El modelo a asociar
     * @param view La vista a asociar
     */
    public SelectDestinationResultController(AbstractModel model, AbstractView view) {
        super(model, view);
    }

    /**
     * Método en el cual se ejercen las tareas de control sobre la vista y el
     * modelo
     */
    @Override
    public void control() {
        ActionListener executeButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                executeButtonActionPerformed(actionEvent);
            }
        };
        ActionListener cancelButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancelButtonActionPerformed(actionEvent);
            }
        };
        if (getView() instanceof SelectDestinationResultView) {
            ((SelectDestinationResultView) getView()).getExecuteButton().addActionListener(executeButtonActionListener);
            ((SelectDestinationResultView) getView()).getCancelButton().addActionListener(cancelButtonActionListener);
            //Se carga el resúmen de lo seleccionado
            String pipeLineXML = XMLUtils.marshalObject(getModel().getPipeline(), null);
            ((SelectDestinationResultView) getView()).getPipelineTextArea().setText(pipeLineXML);
        }
    }

    /**
     * Método relacionado con la acción ejectuar del sistema su objetivo es
     * ejecutar las tareas de preprocesamiento
     *
     * @param evt el evento click
     */
    private void executeButtonActionPerformed(ActionEvent evt) {
        getLogger().info("executeButtonActionPerformed(ActionEvent evt)");

        getLogger().info(getModel().getPipeline().toString());
        try {            
            getModel().getPipeline().setDeliveryMethod((DeliveryMethodEnum)((SelectDestinationResultView) getView()).getDeliveryMethodComboBox().getSelectedItem());
            ((SelectDestinationResultModel) getModel()).executeBigTexts();
            JOptionPane.showMessageDialog(getView(), "Se ha enviado de manera exitosa el mensaje al servidor de BigTexts", "Error", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(getView(), "Se ha presentado el siguiente error : "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            getLogger().error(ex.getMessage());
        }
    }

    /**
     * Método relacionado con la acción cancelar de la pantalla, retorna a la
     * pantalla de selección de tareas de preprocesamiento
     *
     * @param evt el evento click
     */
    private void cancelButtonActionPerformed(ActionEvent evt) {
        getView().setVisible(false);

        AbstractModel model;
        AbstractView view;
        AbstractController controller;

        model = new SelectPreprocessingTasksModel();
        view = new SelectPreprocessingTasksView();

        model.setPipeline(getModel().getPipeline());

        view.setVisible(true);

        controller = new SelectPreprocessingTasksController(model, view);
        controller.control();
        getLogger().info("cancelButtonActionPerformed(ActionEvent evt)");
    }
}
