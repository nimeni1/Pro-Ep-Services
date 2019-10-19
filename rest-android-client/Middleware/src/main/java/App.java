import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String fxml = "middleware.fxml";
        URL url  = getClass().getClassLoader().getResource( fxml );
        if (url != null) {
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("Middleware");
            primaryStage.setScene(new Scene(root, 473, 384));
            primaryStage.setOnCloseRequest(t -> {
                Platform.exit();
                System.exit(0);
            });

            primaryStage.show();
        } else {
            System.err.println("Error: Could not load frame from "+ fxml);
        }
    }
}
