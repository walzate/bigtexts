package co.edu.javeriana.bigtexts.sandbox;

import co.edu.javeriana.bigtexts.entities.ExecutionLog;
import co.edu.javeriana.bigtexts.persistence.PersistenceManager;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class Sandbox {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        //System.out.println(DateUtils.getFormatedDate(new Date(), BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT));
        ExecutionLog executionLog;
        executionLog = new ExecutionLog();
        executionLog.setInitialDate(new Date());
        executionLog.setFinalDate(new Date());
        PersistenceManager.persistExecutionLog(executionLog);
        //PersistenceManager.disconnect();
        
        List<ExecutionLog> logs =PersistenceManager.getAllExecutionLogs();
        PersistenceManager.disconnect();
        
        System.out.println("id" +"; initialDate" + "; finalDate" + "; totalTime" + "; uploadingDate"  + "; pipeline"  + "; numberOfSlaves"  + "; numberOfArchives" );
        
        for(ExecutionLog log :logs){
            //System.out.println(log.toCSV());
        }
    }

}
