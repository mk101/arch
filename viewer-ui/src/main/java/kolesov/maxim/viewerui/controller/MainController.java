package kolesov.maxim.viewerui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.FeignException;
import feign.Logger;
import feign.form.FormData;
import feign.form.FormEncoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import kolesov.maxim.viewerui.client.Client;
import kolesov.maxim.viewerui.listener.KafkaListener;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MainController {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @FXML
    private VBox root;

    @FXML
    private TextField messageField;

    private final KafkaListener kafkaListener = new KafkaListener(this::addToList);

    private final Client client;

    private String login;
    private String password;

    public MainController() {
        this.client = Feign.builder()
                .encoder(new FormEncoder(new JacksonEncoder()))
                .logger(new Slf4jLogger(log))
                .logLevel(Logger.Level.FULL)
                .target(Client.class, "http://localhost:8080");
    }

    public void stop() {
        kafkaListener.stop();
    }

    @FXML
    @SneakyThrows
    private void initialize() {
        String allRaw = client.getAll();
        List<String> all = OBJECT_MAPPER.readValue(allRaw, new TypeReference<>() {});
        for (String s : all) {
            addToList(s);
        }
        kafkaListener.listen();
    }

    @FXML
    private void sendText(ActionEvent event) {
        String text = messageField.getText();
        if (login == null || password == null) {
            setCredential();
            if (login == null || login.isBlank() || password == null || password.isBlank()) {
                return;
            }
        }

        try {
            client.sendText(text, getHeader());
        } catch (FeignException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    @SneakyThrows
    private void sendFile(ActionEvent event) {
        if (login == null || password == null) {
            setCredential();
            if (login == null || login.isBlank() || password == null || password.isBlank()) {
                return;
            }
        }

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file == null) {
            return;
        }

        try {
            client.sendFile("file", file, getHeader());
        } catch (FeignException e) {
            log.error("", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private String getHeader() {
        String base64 = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());

        return "Basic " + base64;
    }

    private void setCredential() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(20, 150, 10, 10));
        TextField loginField = new TextField();
        loginField.setPromptText("Login");
        TextField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        pane.add(loginField, 0, 0);
        pane.add(passwordField, 0, 1);

        dialog.getDialogPane().setContent(pane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonType) {
                return new Pair<>(loginField.getText(), passwordField.getText());
            }

            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            login = result.get().getKey();
            password = result.get().getValue();
        }
    }

    private void addToList(String value) {
        Platform.runLater(() -> {
            if (isImage(value)) {
                byte[] data = client.getImage(value);
                InputStream inputStream = new ByteArrayInputStream(data);
                Image image = new Image(inputStream);
                root.getChildren().add(new ImageView(image));
                return;
            }

            root.getChildren().add(new Label(value));
        });
    }

    private boolean isImage(String value) {
        final String regex = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\\..+$";
        return value.matches(regex);
    }

}