package co.edu.javeriana.bigtexts.client.controller;

import co.edu.javeriana.bigtexts.client.model.AbstractModel;
import co.edu.javeriana.bigtexts.client.model.SelectPreprocessingTasksModel;
import co.edu.javeriana.bigtexts.client.model.UploadDocumentsModel;
import co.edu.javeriana.bigtexts.client.view.AbstractView;
import co.edu.javeriana.bigtexts.client.view.SelectPreprocessingTasksView;
import co.edu.javeriana.bigtexts.client.view.UploadDocumentsView;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import co.edu.javeriana.bigtexts.util.DateUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador asociado a la pantalla de carga de archivos (CU001-Upload documents)
 *
 * @author Wilson Alzate Calderón
 */
public class UploadDocumentsController extends AbstractController {

    /**
     * Tarea con la que se ejecuta la carga de archivos
     */
    private Task task;
    /**
     * El archivo a cargar
     */
    private File[] files;

    /**
     * Constructor en el que se asocia el modelo y la vista al controlador
     *
     * @param model El modelo a asociar
     * @param view La vista a asociar
     */
    public UploadDocumentsController(AbstractModel model, AbstractView view) {
        super(model, view);
    }

    /**
     * Método en el cual se ejercen las tareas de control sobre la vista y el
     * modelo
     */
    @Override
    public void control() {
        ActionListener uploadFileActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                uploadFileButtonActionPerformed(actionEvent);
            }
        };
        ActionListener selectFileActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectFileButtonActionPerformed(actionEvent);
            }
        };
        ActionListener cancelButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancelButtonActionPerformed(actionEvent);
            }
        };
        ActionListener continueButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                continueButtonActionPerformed(actionEvent);
            }
        };
        if (getView() instanceof UploadDocumentsView) {
            ((UploadDocumentsView) getView()).getUploadFileButton().addActionListener(uploadFileActionListener);
            ((UploadDocumentsView) getView()).getSelectFileButton().addActionListener(selectFileActionListener);
            ((UploadDocumentsView) getView()).getCancelButton().addActionListener(cancelButtonActionListener);
            ((UploadDocumentsView) getView()).getContinueButton().addActionListener(continueButtonActionListener);
            ((UploadDocumentsView) getView()).getProgressBar().setValue(0);
            ((UploadDocumentsView) getView()).getProgressBar().setStringPainted(true);
        }
    }

    /**
     * Controlador del evento click del botón cargar, monta el archivo eh HDFS F
     *
     * @param evt El evento click
     */
    private void uploadFileButtonActionPerformed(ActionEvent evt) {
        getLogger().info("uploadFileButtonActionPerformed(ActionEvent evt)");
        if (files != null) {
            try {
                task = new Task();
                task.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getView(), "Se ha presentado el siguiente error al intentar cargar los documentos: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                getLogger().error(ex.getMessage());
            }
        }
    }

    /**
     * Controlador del evento click del botón seleccionar, abre la pantalla de
     * selección de archivos
     *
     * @param evt El evento click
     */
    private void selectFileButtonActionPerformed(ActionEvent evt) {
        getLogger().info("selectFileButtonActionPerformed(ActionEvent evt)");
        if (getView() instanceof UploadDocumentsView) {
            int returnVal = ((UploadDocumentsView) getView()).getFileChooser().showOpenDialog((UploadDocumentsView) getView());
            files = ((UploadDocumentsView) getView()).getFileChooser().getSelectedFiles();
            String fileNames = "";
            int i = 0;
            for (File file : files) {
                fileNames += file.getName();
                if (i < files.length - 1) {
                    fileNames += ",";
                }
                i++;
            }
            ((UploadDocumentsView) getView()).getDocumentsPathTextField().setText(fileNames);
            ((UploadDocumentsView) getView()).getDocumentsPathTextField().setEditable(false);
            ((UploadDocumentsView) getView()).getUploadFileButton().setEnabled(true);

        }
    }

    /**
     * Controlador del evento click del botón cancelar, Cancela la selección de
     * un archivo
     *
     * @param evt El evento click
     */
    private void cancelButtonActionPerformed(ActionEvent evt) {
        getLogger().info("cancelButtonActionPerformed(ActionEvent evt)");
        files = null;
        ((UploadDocumentsView) getView()).getDocumentsPathTextField().setText("");
        ((UploadDocumentsView) getView()).getDocumentsPathTextField().setEditable(true);
        ((UploadDocumentsView) getView()).getUploadFileButton().setEnabled(false);

    }

    /**
     * Controlador del evento click del botón continuar, pasa a la pantalla de
     * selección de tareas de preprocesamiento
     *
     * @param evt El evento click
     */
    private void continueButtonActionPerformed(ActionEvent evt) {
        getLogger().info("continueButtonActionPerformed(ActionEvent evt)");
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
    }

    /**
     * Clase auxiliar usada para el llamado al proceso de cargue y control del
     * progress bar
     */
    private class Task extends Thread {

        /**
         * Método que ejecuta la acción, carga los archivos al FTP y actualiza
         * el estado de la barra de progreso
         */
        @Override
        public void run() {
            try {
                String targetFolder = DateUtils.getFormatedDate(new Date(), BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT);
                ((UploadDocumentsModel) getModel()).getPipeline().setTargetFolder(targetFolder);
                ciclo:
                for (int i = 0; i < files.length; i++) {
                    final int progress = i;
                    final File file = files[i];
                    if (!file.getName().endsWith(BigTextsConstants.BIG_TEXTS_FILE_EXTENSION)) {
                        JOptionPane.showMessageDialog(getView(), "El archivo: " + file.getName() +" no tiene la extensión válida en el sistema: "+BigTextsConstants.BIG_TEXTS_FILE_EXTENSION, "Error", JOptionPane.ERROR_MESSAGE);
                        break ciclo;
                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    ((UploadDocumentsModel) getModel()).uploadFile(file, (getModel()).getPipeline());
                                    String[] newRow = new String[]{
                                        String.valueOf(progress), file.getName(), String.valueOf(file.getTotalSpace())
                                    };
                                    ((DefaultTableModel) ((UploadDocumentsView) getView()).getUploadedDocumentsTable().getModel()).addRow(newRow);
                                    ((UploadDocumentsView) getView()).getProgressBar().setValue((progress + 1) * 100 / files.length);                                    
				    JOptionPane.showMessageDialog(getView(), "Se han cargado de manera exitosa los documentos en el directorio: " + ((UploadDocumentsModel) getModel()).getPipeline().getTargetFolder() + " del FTP de BigTexts", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(getView(), "Se ha presentado el siguiente error al intentar cargar los documentos: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                                    getLogger().error(ex.getMessage());
                                }
                            }
                        });
                    }
                }                
            } catch (Exception ex) {
                Logger.getLogger(UploadDocumentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
