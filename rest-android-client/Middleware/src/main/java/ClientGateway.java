public abstract class ClientGateway {

    private SendMessage sendMessage = new SendMessage("middlewareToClient");
    private ReceiveMessage receiveMessage = new ReceiveMessage("clientToMiddleware");


    public ClientGateway() {
        receiveMessage.messageListener((consumerTag, message) -> {
            String text = new String(message.getBody());
            onReceived(text);
        }, consumerTag -> {
            System.out.println("Error "+ consumerTag);
        });
    }

    public void createAndSendMessage(String json) {
        sendMessage.createMessageAndSendMessage(json);
    }

    public abstract void onReceived(String json);
}
