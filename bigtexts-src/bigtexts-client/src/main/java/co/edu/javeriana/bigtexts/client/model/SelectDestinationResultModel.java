package co.edu.javeriana.bigtexts.client.model;

import co.edu.javeriana.bigtexts.client.queue.QueueManager;
import co.edu.javeriana.bigtexts.util.XMLUtils;

/**
 * Modelo de la pantalla de ejecución
 *
 * @author Wilson Alzate Calderón
 */
public class SelectDestinationResultModel extends AbstractModel {

    /**
     * Método que le envía el mensaje a la cola de BigTexts
     *
     * @throws Exception
     */
    public void executeBigTexts() throws Exception {
        String pipelineXML = XMLUtils.marshalObject(getPipeline(), null);
        QueueManager.sendMessage(pipelineXML);
    }
}
