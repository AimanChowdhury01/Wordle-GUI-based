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
            statusLabel.setText("Invalid guess. Type in 5 letter word");
        } else {
            updateStatus(currentGuess);
        }

        // Check if game over
        if (myGame.isGameOver() || currentGuess.hasWin()) {
            if (currentGuess.hasWin()) {
                displayWinningRow(currentGuess);
            } else {
                displayCorrectWordRow();
            }
        } else {
            guessField.clear();
        }
    }

    private void updateStatus(Guess currentGuess) {
        String secretWord = currentGuess.getSecretWord().toLowerCase(); // Convert secret word to lowercase
        String guessedWord = guessField.getText().toLowerCase(); // Convert guessed word to lowercase just in case to avoid sensitivity issue, can remove in final version

        // Check if the guesse longer than 5 letters
        if (guessedWord.length() > 5) {
            statusLabel.setText("Invalid Guess, type in 5 letter word");
            return; // Skip further processing
        }

        // Check if the guess was a win
        boolean isWin = currentGuess.hasWin();

        // Decrement wrongGuesses if the guess was not a win
        if (!isWin) {
            wrongGuesses++;
        }

        // Decrement remaining attempts if the guess was a win
        int remainingAttempts = MAX_CHANCES - wrongGuesses;
        if (isWin) {
            remainingAttempts--;
        }

        // Check if the game is over and display appropriate message
        if (isWin) {
            statusLabel.setText("Congratulations! You've guessed the word!");
        } else if (remainingAttempts <= 0) {
            statusLabel.setText("Sorry, you've reached the maximum number of attempts.");
        } else {
            // Update status label to show remaining attempts
            statusLabel.setText("You have " + remainingAttempts + " attempts left.");
        }

        // Populate the wordGrid with previous guesses and their results
        for (int i = 0; i < secretWord.length(); i++) {
            char guessedChar = (i < guessedWord.length()) ? guessedWord.charAt(i) : ' ';
            char secretChar = secretWord.charAt(i);

            Label letterLabel = new Label(String.valueOf(guessedChar));
            letterLabel.setFont(Font.font("Arial", 20));

            // Determine the color of the guessed letter based on its correctness
            if (Character.toLowerCase(guessedChar) == secretChar) { // Compare lowercase characters
                letterLabel.setTextFill(Color.GREEN); // Correct letter
            } else if (secretWord.indexOf(Character.toLowerCase(guessedChar)) != -1) {
                letterLabel.setTextFill(Color.ORANGE); // Letter is in the word but not in the correct position
            } else {
                letterLabel.setTextFill(Color.RED); // No match
            }

            // Add the letterLabel to the wordGrid
            wordGrid.add(letterLabel, i, wrongGuesses - 1); // Display the guess in the corresponding row
        }

        // If the guess was a win, display the correct word in green in the next row
        if (isWin) {
            for (int i = 0; i < secretWord.length(); i++) {
                Label letterLabel = new Label(String.valueOf(secretWord.charAt(i)));
                letterLabel.setFont(Font.font("Arial", 20));
                letterLabel.setTextFill(Color.GREEN); // Correct word
                wordGrid.add(letterLabel, i, wrongGuesses); // Display the correct word in the next row
            }
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