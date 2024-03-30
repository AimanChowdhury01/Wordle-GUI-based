package cpsc219.assignment3.assignment3_release;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WordGuesserApp extends Application {

    GameRunner myGame;

    public WordGuesserApp() {
        myGame = new GameRunner();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordGuesserApp.class.getResource("word-guesser.fxml"));
        fxmlLoader.setController(new WordGuesserController(myGame));
        Scene scene = new Scene(fxmlLoader.load(), 480, 240);
        stage.setTitle("Word Guessing Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}