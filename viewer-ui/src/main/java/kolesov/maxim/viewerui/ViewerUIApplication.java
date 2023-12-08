package kolesov.maxim.viewerui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kolesov.maxim.viewerui.controller.MainController;

import java.io.IOException;

public class ViewerUIApplication extends Application {

    private MainController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewerUIApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        controller = fxmlLoader.getController();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        controller.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}