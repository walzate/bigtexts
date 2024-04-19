package co.edu.javeriana.bigtexts.client.ftp;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * Clase con utilidades para la gestión de archivos en FTP
 *
 * @author walzate
 */
public class FTPManager {

    /**
     * La instancia del log
     */
    private static final Logger logger = Logger.getLogger(
            FTPManager.class.getName());
    /**
     * Instancia del cliente FTP (Conexión al servidor FTP)
     */
    private static FTPClient ftpClient = null;

    /**
     * Método singleton para obtener la instancia del cliente FTP
     *
     * @return La instancia del cliente
     */
    private static FTPClient getFtpClient() throws Exception {
        if (ftpClient == null) {
            ftpClient = new FTPClient();
            int reply;
            String server = BigTextsConstants.BIG_FTP_HOST;
            String username = BigTextsConstants.BIG_FTP_USERNAME;
            String password = BigTextsConstants.BIG_FTP_PASSWORD;

            ftpClient.connect(server);
            
            logger.info("Connected to " + server + " on "+ ftpClient.getDefaultPort());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                throw new EOFException("FTP server refused connection.");
            }
            
            ftpClient.login(username, password);

            logger.info("Connected to " + server + ".");
            logger.info(ftpClient.getReplyString());
            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                throw new EOFException("FTP server refused connection.");
            }
        }
        return ftpClient;
    }

    /**
     * Método que carga un archivo al servidor FTP
     *
     * @param localFileFullName El path completo del archivo
     * @param fileName El nombre del archivo
     * @param hostDir El directorio destino
     * @param pipeline
     * @throws Exception
     */
    public static void uploadFile(String localFileFullName, String fileName, String hostDir, Pipeline pipeline)
            throws Exception {
        File file = new File(localFileFullName);
        if (file.isDirectory()) {
            for (File iter : file.listFiles()) {
                InputStream input = new FileInputStream(iter);
                getFtpClient().makeDirectory(hostDir);
                getFtpClient().storeFile(hostDir + "/" + iter.getName(), input);
                pipeline.getFiles().add(iter.getName());
                logger.info("File uploaded... " + iter.getName());
            }
        } else {
            InputStream input = new FileInputStream(new File(localFileFullName));
            getFtpClient().makeDirectory(hostDir);
            getFtpClient().storeFile(hostDir + "/" + fileName, input);
            pipeline.getFiles().add(fileName);
            logger.info("File uploaded... " + fileName);
        }

    }

    /**
     * Método que permite la desconexión del cliente FTP
     */
    public static void disconnect() throws Exception{
        if (getFtpClient().isConnected()) {
            try {
                getFtpClient().logout();
                getFtpClient().disconnect();
            } catch (IOException f) {
                logger.error(f.getMessage());
            }
        }
    }
}
