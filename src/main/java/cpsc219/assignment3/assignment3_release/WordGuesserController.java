package cpsc219.assignment3.assignment3_release;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WordGuesserController {

    GameRunner myGame;
    int wrongGuesses = 0;
    final int MAX_CHANCES = 6;

    @FXML
    private Label statusLabel;
    @FXML
    private TextField guessField;
    @FXML
    private GridPane wordGrid;

    public WordGuesserController() {
        myGame = new GameRunner();
        statusLabel = new Label();
        guessField = new TextField();
        wordGrid = new GridPane();

        for (int i = 0; i < wordGrid.getRowCount(); i++) {
            for (int j = 0; j < wordGrid.getColumnCount(); j++) {
                wordGrid.add(new Label(), i, j);
            }
        }
    }

    public WordGuesserController(GameRunner myGame) {
        this.myGame = myGame;
        statusLabel = new Label();
        guessField = new TextField();
    }

    @FXML
    protected void onGuessButtonClick() {
        String currentWord = guessField.getText();
        Guess currentGuess = myGame.processInput(currentWord);

        // Valid guess check
        if (!myGame.wasGoodGuess()) {
            statusLabel.setText("Invalid guess. Word is not valid");
        } else {
            updateStatusBasedOnGuess(currentGuess);
        }

        // Check if game is over
        if (myGame.isGameOver() || currentGuess.hasWin()) {
            if (currentGuess.hasWin()) {
                displayWinningRow(currentGuess);
            } else {
                displayCorrectWordRow();
            }
        } else {
            guessField.clear(); // Clear the guess
        }
    }

    private void updateStatusBasedOnGuess(Guess currentGuess) {
        wrongGuesses++;

        StringBuilder statusMessage = new StringBuilder();
        int[] letterStatuses = currentGuess.getLetterStatus();
        char[] secretWordChars = currentGuess.getSecretWord().toCharArray();

        // Clear the wordGrid before updating
        wordGrid.getChildren().clear();

        // Build a status message and populate the wordGrid based on the letter status
        for (int i = 0; i < letterStatuses.length; i++) {
            char letter = secretWordChars[i];
            int status = letterStatuses[i];

            Label letterLabel = new Label();

            // Set font and size
            letterLabel.setFont(Font.font("Arial", 20));

            // Set color based on letter status
            if (status == Guess.LetterStates.EXACT_MATCH.ordinal()) {
                // Correct letter
                letterLabel.setTextFill(Color.GREEN);
                letterLabel.setText(String.valueOf(letter));
            } else if (status == Guess.LetterStates.IN_WORD.ordinal()) {
                // Letter is in the word but not in the correct position
                letterLabel.setTextFill(Color.ORANGE);
                letterLabel.setText(String.valueOf(letter));
            } else {
                // No match
                letterLabel.setTextFill(Color.RED);
                // Display the wrong input until after the 6th guess
                if (wrongGuesses <= MAX_CHANCES) {
                    letterLabel.setText(guessField.getText().length() > i ? String.valueOf(guessField.getText().charAt(i)) : "");
                } else {
                    letterLabel.setText("");
                }
            }

            // Add the letterLabel to the wordGrid
            wordGrid.add(letterLabel, i, 0);
        }
    }

    private void displayWinningRow(Guess currentGuess) {
        // Display all green letters with the correct word in the last row
        char[] secretWordChars = currentGuess.getSecretWord().toCharArray();
        for (int i = 0; i < secretWordChars.length; i++) {
            Label letterLabel = new Label(String.valueOf(secretWordChars[i]));
            letterLabel.setFont(Font.font("Arial", 20));
            letterLabel.setTextFill(Color.GREEN);
            wordGrid.add(letterLabel, i, 1);
        }
    }

    private void displayCorrectWordRow() {
        // Display the correct word in the last row with orange letters
        String correctWord = myGame.getCorrectWord();
        char[] correctWordChars = correctWord.toCharArray();
        for (int i = 0; i < correctWordChars.length; i++) {
            Label letterLabel = new Label(String.valueOf(correctWordChars[i]));
            letterLabel.setFont(Font.font("Arial", 20));
            letterLabel.setTextFill(Color.ORANGE);
            wordGrid.add(letterLabel, i, 1);
        }
    }
}