package co.edu.javeriana.bigtexts.hdfs;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import co.edu.javeriana.bigtexts.util.PropertiesUtils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.log4j.Logger;

/**
 * Gestor de trabajos en Hadoop Distributed File System
 *
 * @author Wilson Alzate Calderón
 */
public class HDFSManager {

    /**
     * Instancia singleton del fileSystem
     */
    private static FileSystem fileSystem = null;
    /**
     * Log de la aplicación
     */
    private static final Logger logger = Logger.getLogger(
            HDFSManager.class.getName());

    /**
     * Método que copia una lista de archivos a HDFS
     *
     * @param pipeline El objeto en el que se encapsula todo el proceso de BigTexts
     * BigTexts
     * @throws Exception
     */
    public static void copyFilesToHDFS(Pipeline pipeline) throws Exception {
        for (String fileName : pipeline.getFiles()) {
            File file = new File(BigTextsConstants.BIG_DFAULT_FTP_FOLDER + pipeline.getTargetFolder() + "/" + fileName);
            getFileSystem().copyFromLocalFile(new Path(file.getAbsolutePath()),
                    new Path(PropertiesUtils.getProperty(BigTextsConstants.HADOOP_HDFS_HOME, BigTextsConstants.BIG_TEXTS_CONFIGURATION_FILE) + File.separator + pipeline.getTargetFolder() + File.separator + file.getName()));
        }

    }

    /**
     * Copia los archivos del HDFS al directorio del FTP
     * @param pipeline El objeto en el que se encapsula todo el proceso de BigTexts
     * @throws Exception 
     */
    public static void copyFilesToLocal(Pipeline pipeline, boolean hasDestinationSuffix) throws Exception {
        for (String fileName : pipeline.getFiles()) {
            String sourcePath= PropertiesUtils.getProperty(BigTextsConstants.HADOOP_HDFS_HOME, BigTextsConstants.BIG_TEXTS_CONFIGURATION_FILE) + File.separator + pipeline.getTargetFolder() + File.separator + fileName;
            if(hasDestinationSuffix){
                sourcePath+= BigTextsConstants.BIG_TEXTS_DESTINATION_SUFFIX;
            }
            String destinationPath= BigTextsConstants.BIG_DFAULT_FTP_FOLDER + pipeline.getTargetFolder() + "/" + fileName;                        
            if(hasDestinationSuffix){
                destinationPath+= BigTextsConstants.BIG_TEXTS_DESTINATION_SUFFIX;
            }
            File file = new File("destinationPath");
            if(file.exists()){
                file.delete();
            }
            getFileSystem().copyToLocalFile(new Path(sourcePath), new Path(destinationPath));            
        }        
    }
    
    /**
     * Método que retorna el número de datanodes activos
     *
     * @return Un entero con el valor, -1 si hay error
     * @see https://svn.apache.org/repos/asf/hadoop/common/tags/release-0.20.2/src/hdfs/org/apache/hadoop/hdfs/tools/DFSAdmin.java
     * @throws IOException
     */
    public static int getNumberOfActiveDatanodes() throws IOException {
        int result; 
        DatanodeInfo[] live = ((DistributedFileSystem) getFileSystem()).getClient().datanodeReport(DatanodeReportType.LIVE);
        result = live.length;
        return result;
    }

    /**
     * Método singleton para la conexión a HDFS
     *
     * @return La instancia del objeto de conexión con HDFS
     */
    private static FileSystem getFileSystem() {
        if (fileSystem == null) {
            try {
                Configuration conf = new Configuration();
                conf.addResource(new Path(PropertiesUtils.getProperty(BigTextsConstants.HADOOP_CORESITE_PATH, BigTextsConstants.BIG_TEXTS_CONFIGURATION_FILE)));
                conf.addResource(new Path(PropertiesUtils.getProperty(BigTextsConstants.HADOOP_HDFSSITE_PATH, BigTextsConstants.BIG_TEXTS_CONFIGURATION_FILE)));
                fileSystem = FileSystem.get(conf);
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
        return fileSystem;
    }
    
    public static void mkdir(String strPath){
        try {
            Path path = new Path(strPath);
            getFileSystem().mkdirs(path);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HDFSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void delete(String strPath){
        try {
            String sourcePath= PropertiesUtils.getProperty(BigTextsConstants.HADOOP_HDFS_HOME, BigTextsConstants.BIG_TEXTS_CONFIGURATION_FILE) + File.separator + strPath;
            Path path = new Path(sourcePath);
            getFileSystem().delete(path,true);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HDFSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean exists(String strPath){
        boolean result=false;
        try {
            Path path = new Path(strPath);
            result = getFileSystem().exists(path);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HDFSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }
}
