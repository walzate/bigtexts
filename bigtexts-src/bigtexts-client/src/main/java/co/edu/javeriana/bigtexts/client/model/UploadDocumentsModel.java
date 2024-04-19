package co.edu.javeriana.bigtexts.client.model;

import co.edu.javeriana.bigtexts.client.ftp.FTPManager;
import co.edu.javeriana.bigtexts.dto.Pipeline;
import java.io.File;
import java.util.List;

/**
 * Modelo de la pantalla de carga de archivos
 *
 * @author Wilson Alzate Calderón
 */
public class UploadDocumentsModel extends AbstractModel {

    /**
     * Método que carga los archivos al FTP
     *
     * @param files La lista de archivos
     * @throws Exception
     */
    public void uploadFiles(List<File> files) throws Exception {
        for (File file : files) {
            FTPManager.uploadFile(file.getAbsolutePath(), file.getName(), getPipeline().getTargetFolder(), getPipeline());
        }
    }

    /**
     * Método que carga un archivo al FTP
     *
     * @param file El archivo a cargar
     * @param pipeline El pipeline que se va llenando
     * @throws Exception
     */
    public void uploadFile(File file, Pipeline pipeline) throws Exception {
        FTPManager.uploadFile(file.getAbsolutePath(), file.getName(), pipeline.getTargetFolder(), getPipeline());
    }
}
