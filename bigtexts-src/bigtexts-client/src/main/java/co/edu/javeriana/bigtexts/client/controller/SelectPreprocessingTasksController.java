package co.edu.javeriana.bigtexts.client.controller;

import co.edu.javeriana.bigtexts.client.model.AbstractModel;
import co.edu.javeriana.bigtexts.client.model.SelectDestinationResultModel;
import co.edu.javeriana.bigtexts.client.model.UploadDocumentsModel;
import co.edu.javeriana.bigtexts.client.view.AbstractView;
import co.edu.javeriana.bigtexts.client.view.SelectDestinationResultView;
import co.edu.javeriana.bigtexts.client.view.SelectPreprocessingTasksView;
import co.edu.javeriana.bigtexts.client.view.UploadDocumentsView;
import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import co.edu.javeriana.bigtexts.dto.PreprocessingTaskParameter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador asociado a la pantalla de selección y parametrización de tareas
 * de preprocesamiento del resultado en el sistema (CU002-Select preprocessing
 * tasks, CU003-Establish execution order y CU004-Select destination result)
 *
 * @author Wilson Alzate Calderón
 */
public class SelectPreprocessingTasksController extends AbstractController {

    /**
     * Constructor en el que se asocia el modelo y la vista al controlador
     *
     * @param model El modelo a asociar
     * @param view La vista a asociar
     */
    public SelectPreprocessingTasksController(AbstractModel model, AbstractView view) {
	super(model, view);
    }
    /**
     * Variable temporal para evaluar cuando se ha seleccionado una tarea
     */
    private String selectedTask;

    /**
     * Método en el cual se ejercen las tareas de control sobre la vista y el
     * modelo
     */
    @Override
    public void control() {
	ActionListener continueButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		continueButtonActionPerformed(actionEvent);
	    }
	};
	ActionListener leftButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		leftButtonActionPerformed(actionEvent);
	    }
	};
	ActionListener leftAllButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		leftAllButtonActionPerformed(actionEvent);
	    }
	};
	ActionListener rightButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		rightButtonActionPerformed(actionEvent);
	    }
	};
	ActionListener rightAllButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		rightAllButtonActionPerformed(actionEvent);
	    }
	};

	ListSelectionListener parametersListActionListener = new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent lse) {
		parametersListSelected(lse);
	    }
	};

	ActionListener returnButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		returnButtonActionPerformed(actionEvent);
	    }
	};

	ActionListener upButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		upButtonActionPerformed(actionEvent);
	    }
	};

	ActionListener downButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		downButtonActionPerformed(actionEvent);
	    }
	};

	ActionListener acceptButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		acceptButtonActionPerformed(actionEvent);
	    }
	};

	ActionListener cancelButtonActionListener = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent actionEvent) {
		cancelButtonActionPerformed(actionEvent);
	    }
	};

	if (getView() instanceof SelectPreprocessingTasksView) {
	    ((SelectPreprocessingTasksView) getView()).getContinueButton().addActionListener(continueButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getLeftButton().addActionListener(leftButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getLeftAllButton().addActionListener(leftAllButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getRightButton().addActionListener(rightButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getRightAllButton().addActionListener(rightAllButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getTargetTasksList().addListSelectionListener(parametersListActionListener);
	    ((SelectPreprocessingTasksView) getView()).getReturnButton().addActionListener(returnButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getUpTaskButton().addActionListener(upButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getDownTaskButton().addActionListener(downButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getAcceptButton().addActionListener(acceptButtonActionListener);
	    ((SelectPreprocessingTasksView) getView()).getCancelButton().addActionListener(cancelButtonActionListener);
	}
    }

    /**
     * Controlador del evento click del botón continuar, pasa a la pantalla de
     * selección de resultado
     *
     * @param evt El evento click
     */
    private void continueButtonActionPerformed(ActionEvent evt) {
	getLogger().info("continueButtonActionPerformed(ActionEvent evt)");
	getView().setVisible(false);

	DefaultListModel targetModel = (DefaultListModel) ((SelectPreprocessingTasksView) getView()).getTargetTasksList().getModel();

	List<PreprocessingTask> preprocessingTasks;
	preprocessingTasks = new ArrayList<PreprocessingTask>();
	while (targetModel.elements().hasMoreElements()) {
	    String element = (String) targetModel.elements().nextElement();
	    PreprocessingTask preprocessingTask = getModel().getPreprocessingTaskFromName(element);
	    preprocessingTasks.add(preprocessingTask);
	    targetModel.removeElement(element);
	}
	getModel().getPipeline().setPreprocessingTasks(preprocessingTasks);

	AbstractModel model;
	AbstractView view;
	AbstractController controller;

	model = new SelectDestinationResultModel();
	view = new SelectDestinationResultView();

	model.setPipeline(getModel().getPipeline());

	view.setVisible(true);

	controller = new SelectDestinationResultController(model, view);
	controller.control();
    }

    /**
     * Controlador del evento click del botón izquierda, pasa una tarea de las
     * seleccionadas a las no seleccionadas
     *
     * @param evt El evento click
     */
    private void leftButtonActionPerformed(ActionEvent evt) {
	getLogger().info("leftButtonActionPerformed(ActionEvent evt)");
	JList source = ((SelectPreprocessingTasksView) getView()).getTargetTasksList();
	JList target = ((SelectPreprocessingTasksView) getView()).getSourceTasksList();
	moveElementsAmongLists(source, target);
    }

    /**
     * Método que pasa elementos de una lista origen a una lista destino
     *
     * @param source la lista origen
     * @param target la lista destino
     */
    public void moveElementsAmongLists(JList source, JList target) {
	List<Object> selectedElements = source.getSelectedValuesList();
	for (Object element : selectedElements) {

	    if (target.getModel() == null) {
		DefaultListModel listModel = new DefaultListModel();
		target.setModel(listModel);
	    }
	    if (source.getModel() == null) {
		DefaultListModel listModel = new DefaultListModel();
		source.setModel(listModel);
	    }
	    ((DefaultListModel) target.getModel()).addElement(element);
	}

	removeElementsFromList(selectedElements, source);
    }

    /**
     * Controlador del evento click del botón izquierda todas, Pasa todas las
     * tareas de seleccionadas a no seleccionadas
     *
     * @param evt El evento click
     */
    private void leftAllButtonActionPerformed(ActionEvent evt) {
	getLogger().info("leftAllButtonActionPerformed(ActionEvent evt)");
	DefaultListModel sourceModel = (DefaultListModel) ((SelectPreprocessingTasksView) getView()).getTargetTasksList().getModel();
	DefaultListModel targetModel = (DefaultListModel) ((SelectPreprocessingTasksView) getView()).getSourceTasksList().getModel();
	while (sourceModel.elements().hasMoreElements()) {
	    Object element = sourceModel.elements().nextElement();
	    targetModel.addElement(element);
	    sourceModel.removeElement(element);
	}
	sourceModel.removeAllElements();

    }

    /**
     * Controlador del evento click del botón derecha, Selecciona una tarea
     *
     * @param evt El evento click
     */
    private void rightButtonActionPerformed(ActionEvent evt) {
	getLogger().info("rightButtonActionPerformed(ActionEvent evt)");
	JList source = ((SelectPreprocessingTasksView) getView()).getSourceTasksList();
	JList target = ((SelectPreprocessingTasksView) getView()).getTargetTasksList();
	moveElementsAmongLists(source, target);	
    }

    /**
     * Método que dada una lista y un arreglo de indices, borra los elementos
     * correspondientes a los índices de la lista
     *
     * @param selectedElements la lista de elementos
     * @param source La lista de donde se quieren borrar los elementos
     */
    public void removeElementsFromList(List<Object> selectedElements, JList source) {
	for (Object element : selectedElements) {
	    ((DefaultListModel) source.getModel()).removeElement(element);
	}
    }

    /**
     * Controlador del evento click del botón derecha todas, Deselecciona todas
     * las tareas
     *
     * @param evt El evento click
     */
    private void rightAllButtonActionPerformed(ActionEvent evt) {
	getLogger().info("rightAllButtonActionPerformed(ActionEvent evt)");
	DefaultListModel sourceModel = (DefaultListModel) ((SelectPreprocessingTasksView) getView()).getSourceTasksList().getModel();
	DefaultListModel targetModel = (DefaultListModel) ((SelectPreprocessingTasksView) getView()).getTargetTasksList().getModel();
	while (sourceModel.elements().hasMoreElements()) {
	    Object element = sourceModel.elements().nextElement();
	    targetModel.addElement(element);
	    sourceModel.removeElement(element);
	}
	sourceModel.removeAllElements();
    }

    /**
     * Controlador del evento de selección de una tarea de preprocesamiento,
     * muestra el panel con las opciones de parametrización de la tarea
     *
     * @param lse
     */
    public void parametersListSelected(ListSelectionEvent lse) {
	getLogger().info("parametersListSelected(ListSelectionEvent lse)");
	if (((SelectPreprocessingTasksView) getView()).getTargetTasksList().getSelectedValue() != null) {
	    String selectedTaskLocal = ((SelectPreprocessingTasksView) getView()).getTargetTasksList().getSelectedValue().toString();
	    if (selectedTask == null || !selectedTask.equals(selectedTaskLocal)) {
		selectedTask = selectedTaskLocal;
		getLogger().info(selectedTask);
		PreprocessingTask preprocessingTask = getModel().getPreprocessingTaskFromName(selectedTask);
		((SelectPreprocessingTasksView) getView()).getParametersPanel().setVisible(true);
		((SelectPreprocessingTasksView) getView()).getParametersPanel().setBorder(javax.swing.BorderFactory.createTitledBorder("Parametrización de la tarea " + selectedTask));
		//se limpia la tabla de parámetros
		((DefaultTableModel) ((SelectPreprocessingTasksView) getView()).getParametersTable().getModel()).setRowCount(0);
		if (preprocessingTask != null && preprocessingTask.getParameters() != null) {
		    for (PreprocessingTaskParameter param : preprocessingTask.getParameters()) {
			Object[] newRow = new Object[]{
			    param.getLabel(), param.getDefaultValue(), param.getDescription()
			};
			((DefaultTableModel) ((SelectPreprocessingTasksView) getView()).getParametersTable().getModel()).addRow(newRow);
		    }
		}

	    }
	}
    }

    /**
     * Controlador del evento click del botón volver, retorna a la pantalla de
     * carga de documentos
     *
     * @param evt El evento click
     */
    private void returnButtonActionPerformed(ActionEvent actionEvent) {
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

    /**
     * Controlador del evento click del botón arriba, pasa la tarea de
     * preprocesamiento a una posición arriba
     *
     * @param evt El evento click
     */
    private void upButtonActionPerformed(ActionEvent actionEvent) {
	getLogger().info("upButtonActionPerformed(ActionEvent evt)");
	JList target = ((SelectPreprocessingTasksView) getView()).getTargetTasksList();
	DefaultListModel targetModel = ((DefaultListModel) target.getModel());
	int[] sourceIndex = target.getSelectedIndices();
	for (int index : sourceIndex) {
	    if (index > 0) {
		String element = (String) target.getModel().getElementAt(index);
		targetModel.removeElementAt(index);
		int newIndex = index - 1;
		targetModel.insertElementAt(element, newIndex);
		target.setSelectedIndex(newIndex);
	    }
	}
    }

    /**
     * Controlador del evento click del botón arriba, pasa la tarea de
     * preprocesamiento a una posición abajo
     *
     * @param evt El evento click
     */
    private void downButtonActionPerformed(ActionEvent actionEvent) {
	getLogger().info("downButtonActionPerformed(ActionEvent evt)");
	JList target = ((SelectPreprocessingTasksView) getView()).getTargetTasksList();
	DefaultListModel targetModel = ((DefaultListModel) target.getModel());
	int[] sourceIndex = target.getSelectedIndices();
	for (int index : sourceIndex) {
	    if (index < targetModel.size()) {
		String element = (String) target.getModel().getElementAt(index);
		targetModel.removeElementAt(index);
		int newIndex = index + 1;
		targetModel.insertElementAt(element, newIndex);
		target.setSelectedIndex(newIndex);
	    }
	}
    }

    /**
     * Controlador del evento click del botón aceptar, pasa los valores de la
     * taba de parámetros al pipeline
     *
     * @param actionEvent
     */
    private void acceptButtonActionPerformed(ActionEvent actionEvent) {
	getLogger().info("acceptButtonActionPerformed(ActionEvent actionEvent)");
	Vector elements = ((DefaultTableModel) ((SelectPreprocessingTasksView) getView()).getParametersTable().getModel()).getDataVector();
	PreprocessingTask preprocessingTask = getModel().getPreprocessingTaskFromName(selectedTask);
	for (int i = 0; i < elements.size(); i++) {
	    getLogger().info("acceptButtonActionPerformed(ActionEvent actionEvent) elements.get(i): " + elements.get(i));
	    Vector columna = (Vector) elements.get(i);
	    PreprocessingTaskParameter param = preprocessingTask.getParameters().get(i);
	    param.setValue((String) columna.get(1));
	    getLogger().info("acceptButtonActionPerformed(ActionEvent actionEvent) param: " + param.toString());
	}
    }

    /**
     * Controlador del evento click del botón cancelar, limpia la tabla de
     * parámetros
     *
     * @param actionEvent
     * @see
     * http://stackoverflow.com/questions/3879610/how-to-clear-contents-of-a-jtable
     */
    private void cancelButtonActionPerformed(ActionEvent actionEvent) {
	getLogger().info("cancelButtonActionPerformed(ActionEvent actionEvent)");
	//Se li mpia la tabla de parámetros
	((DefaultTableModel) ((SelectPreprocessingTasksView) getView()).getParametersTable().getModel()).setRowCount(0);
	//Se limpian los valores de los parámetros
	PreprocessingTask preprocessingTask = getModel().getPreprocessingTaskFromName(selectedTask);
	for (PreprocessingTaskParameter param : preprocessingTask.getParameters()) {
	    param.setValue(param.getDefaultValue());
	    //Se vuelve a cargar la tabla de parámetros
	    Object[] newRow = new Object[]{
		param.getLabel(), param.getDefaultValue(), param.getDescription()
	    };
	    ((DefaultTableModel) ((SelectPreprocessingTasksView) getView()).getParametersTable().getModel()).addRow(newRow);
	}
    }
}
