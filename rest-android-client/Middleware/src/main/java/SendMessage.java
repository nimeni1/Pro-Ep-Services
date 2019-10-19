import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendMessage {

    private Channel channel;
    private String queue;
    private static final String IP = "192.168.192.43";

    public SendMessage(String queue) {
        this.queue = queue;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("user");
        factory.setPassword("user");
        factory.setHost(IP);
        Connection connection;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queue,true,false,false,null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void createMessageAndSendMessage(String message) {
        try {
            channel.basicPublish("", queue, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
