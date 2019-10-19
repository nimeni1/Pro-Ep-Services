import com.rabbitmq.client.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class ReceiveMessage {

    private Channel channel;
    private String queue;
    private static final String IP = "192.168.192.43";

    public ReceiveMessage(String queue) {
        this.queue = queue;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("user");
        factory.setPassword("user");
        factory.setHost(IP);
        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queue, true, false, false, null);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    public void messageListener(DeliverCallback delivery, CancelCallback cancel) {
        try {
            channel.basicConsume(queue, false, "myConsumerTag", delivery,cancel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
