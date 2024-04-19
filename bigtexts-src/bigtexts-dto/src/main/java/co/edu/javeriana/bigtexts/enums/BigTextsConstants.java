package co.edu.javeriana.bigtexts.enums;

/**
 * Interfaz que contiene las constantes del sistema
 *
 * @author Wilson Alzate Calderón
 */
public interface BigTextsConstants {

    /**
     * Key de la ubicación del archivo de configuración de BigTexts
     */
    public static String BIG_TEXTS_CONFIGURATION_FILE = "bigtexts.properties";
    /**
     * Key de la ubicación del archivo core-site.xml de hadoop
     */
    public static String HADOOP_CORESITE_PATH = "hadoop.coresite.path";
    /**
     * Key de la ubicación del archivo hdfs-site.xml de hadoop
     */
    public static String HADOOP_HDFSSITE_PATH = "hadoop.hdfssite.path";
    /**
     * Key de la ubicación del archivo hdfs-site.xml de hadoop
     */
    public static String HADOOP_HDFS_HOME = "hadoop.hdfs.home";
    /**
     * Formato por defecto de las fechas en el sistema
     */
    public static String BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
    /**
     * Nombre del archivo que contiene el catálogo de tareas de preprocesamiento
     */
    public static String BIG_TEXTS_PREPROCESSING_CATALOG = "preprocessingTasks.xml";
    
    /**
     * Nombre de la cola en la que se montarán los mensajes de BigTexts
     */
    public static String BIG_TEXTS_QUEUE = "BIG_TEXTS_QUEUE";
    /**
     * El directorio donde se almacenan los arvhicos que llegan mediante FTP
     */
    public static String BIG_DFAULT_FTP_FOLDER = "/home/hduser/";
    /**
     * El username de conexión al FTP
     */
    public static String BIG_FTP_USERNAME = "hduser";
    /**
     * El Password de conexión al FRP
     */
    public static String BIG_FTP_PASSWORD = "123456";
    /**
     * El Password de conexión al FRP
     */
    public static String BIG_FTP_HOST = "localhost";
    /**
     * El nombre de la unidad de persistencia
     */
    public static String BIG_TEXTS_PERSISTENCE_UNIT = "BigTexts_PU";
    /**
     * Extensión válida
     */
    public static String BIG_TEXTS_FILE_EXTENSION = ".txt";
    /**
     * Extensión válida
     */
    public static String BIG_TEXTS_DESTINATION_SUFFIX = "-result";
}
