package co.edu.javeriana.bigtexts.client.queue;

import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Clase con utilidades para la gestión de colas de mensajes
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class QueueManager {

    // URL of the JMS server.DEFAULT_BROKER_URL will just mean // that JMS server is on localhost
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    /**
     * Método que envía un mensaje a la cola de BigTexts
     *
     * @param message El mensaje a enviar
     * @throws Exception
     */
    public static void sendMessage(String message) throws Exception {
	try {

	    // Getting JMS connection from the server and starting it
	    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
	    Connection connection = connectionFactory.createConnection();
	    connection.start();

	    // JMS messages are sent and received using a Session. We will
	    // create here a non-transactional session object. If you want
	    // to use transactions you should set the first parameter to 'true'
	    Session session = connection.createSession(false,
		    Session.AUTO_ACKNOWLEDGE);

	    // Destination represents here our queue 'TESTQUEUE' on the
	    // JMS server. You don't have to do anything special on the
	    // server to create it, it will be created automatically.
	    Destination destination = session.createQueue(BigTextsConstants.BIG_TEXTS_QUEUE);

	    // MessageProducer is used for sending messages (as opposed
	    // to MessageConsumer which is used for receiving them)
	    MessageProducer producer = session.createProducer(destination);

	    // We will send a small text message saying 'Hello' in Japanese
	    TextMessage textMessage;
	    textMessage = session.createTextMessage(message);

	    // Here we are sending the message!
	    producer.send(textMessage);
	    System.out.println("Sent message '" + textMessage.getText() + "'");

	    connection.close();
	} catch (JMSException e) {
	    throw e;
	}
    }
}
