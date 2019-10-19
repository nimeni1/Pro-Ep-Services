public abstract class DriverGateway {

    private SendMessage sendMessage = new SendMessage("middlewareToDriver");
    private ReceiveMessage receiveMessage = new ReceiveMessage("driverToMiddleware");

    public DriverGateway() {
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
