/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.execution;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import co.edu.javeriana.bigtexts.entities.ExecutionLog;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import co.edu.javeriana.bigtexts.enums.DeliveryMethodEnum;
import co.edu.javeriana.bigtexts.hdfs.HDFSManager;
import co.edu.javeriana.bigtexts.persistence.PersistenceManager;
import co.edu.javeriana.bigtexts.pig.PigManager;
import co.edu.javeriana.bigtexts.util.DateUtils;
import co.edu.javeriana.bigtexts.util.XMLUtils;
import java.util.Date;

/**
 * Clase encargada de ejecutar un Pipeline en la infraestructura de BigTexts
 *
 * @author Wilson Alzate Calderón
 */
public class ExecutionManager {

    /**
     * Método encargado de ejecutar un Pipeline en la infraestructura de
     * BigTexts
     *
     * @param pipeline El pipeline a ejecutar
     */
    public static void execute(Pipeline pipeline, String pipelineXML) throws Exception {        
	
	if(pipelineXML == null){
	    pipelineXML = XMLUtils.marshalObject(pipeline, null);
	}
        
        ExecutionLog executionLog = new ExecutionLog();
        executionLog.setInitialDate(new Date());
        long startTime = System.currentTimeMillis();
        
        HDFSManager.copyFilesToHDFS(pipeline);
        String status = PigManager.runQuery(pipeline);
        executionLog.setStatus(status);        
        
        executionLog.setPipeline(pipelineXML);
        Date uploadingDate = DateUtils.getDateFromString(pipeline.getTargetFolder(), BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT);
        executionLog.setUploadingDate(uploadingDate);

        int aliveDatanodes = HDFSManager.getNumberOfActiveDatanodes();
        executionLog.setNumberOfSlaves(aliveDatanodes);

        //Se dejan los archivos en el directorio del FTP
        if (DeliveryMethodEnum.FTP.equals(pipeline.getDeliveryMethod())) {
            HDFSManager.copyFilesToLocal(pipeline, true);
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        
        String tasks="";
        int i = 0;
        for(PreprocessingTask task:pipeline.getPreprocessingTasks()){
            tasks+=task.getName();
            if(i<pipeline.getPreprocessingTasks().size()-1){
                tasks+=",";
            }
        }
        executionLog.setPreprocessingTasks(tasks);
        
        executionLog.setFinalDate(new Date());        
        executionLog.setTotalTime(duration);

        PersistenceManager.persistExecutionLog(executionLog);
        //Se almacena la información de los archivos para postrior análisis
        PersistenceManager.persistBigTextsFiles(executionLog, pipeline.getFiles(), pipeline.getTargetFolder());
        //PersistenceManager.disconnect();
        HDFSManager.delete(pipeline.getTargetFolder());        
    }
}
