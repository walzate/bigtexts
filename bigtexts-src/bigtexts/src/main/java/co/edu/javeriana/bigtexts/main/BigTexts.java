package co.edu.javeriana.bigtexts.main;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import co.edu.javeriana.bigtexts.execution.ExecutionManager;
import co.edu.javeriana.bigtexts.util.XMLUtils;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * Clase principal del servidor de BigTexts, recibe el mensaje de la cola y
 * ejecuta el pipeline
 *
 * @author Wilson Alzate Calderón
 */
public class BigTexts {

    /**
     * La instancia del log
     */
    private static final Logger logger = org.apache.log4j.Logger.getLogger(
            BigTexts.class.getName());
    /**
     * URL por defecto del servidor de colas, en este caso localhost
     */
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    /**
     * Método principal de la clase recibe el mensaje de la cola y ejecuta el
     * pipeline
     *
     * @param args Argumentos por pantalla
     */
    public static void main(String[] args) {
        try {
            // Getting JMS connection from the server
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Creating session for seding messages
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            // Getting the queue 'TESTQUEUE'
            Destination destination = session.createQueue(BigTextsConstants.BIG_TEXTS_QUEUE);

            // MessageConsumer is used for receiving (consuming) messages
            MessageConsumer consumer = session.createConsumer(destination);

            // Here we receive the message.
            // By default this call is blocking, which means it will wait
            // for a message to arrive on the queue.
            Message message = consumer.receive();

            // There are many types of Message and TextMessage
            // is just one of them. Producer sent us a TextMessage
            // so we must cast to it to get access to its .getText()
            // method.
            try {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String pipelineXML = textMessage.getText();
                    System.out.println("Received message "
                            + pipelineXML);
                    Pipeline pipeline = (Pipeline) XMLUtils.unmarshalObjectFromString(Pipeline.class, pipelineXML);

                    //Se ejecuta el pipeline
                    ExecutionManager.execute(pipeline, pipelineXML);

                    logger.info(pipeline.toString());
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            } finally {
                //Se deja esperando al servidor
                connection.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
