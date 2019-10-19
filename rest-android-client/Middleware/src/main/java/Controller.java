import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
//test
public class Controller implements Initializable {

    @FXML
    private ListView<String> lvListViewLines;

    private ClientGateway clientGateway = new ClientGateway() {
        @Override
        public void onReceived(String json) {
            driverGateway.createAndSendMessage(json);
            lvListViewLines.getItems().add(json);
            lvListViewLines.refresh();
        }
    };

    private DriverGateway driverGateway = new DriverGateway() {
        @Override
        public void onReceived(String json) {
            lvListViewLines.getItems().add(json);
            clientGateway.createAndSendMessage(json);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
