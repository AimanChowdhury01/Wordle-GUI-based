package cpsc219.assignment3.assignment3_release;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRunner {
    private final List<String> validWords;
    private final State gameState;

    public GameRunner() {
        validWords = loadWords("sgb-words.txt");
        gameState = new State(new ArrayList<>(validWords)); // Pass a deep copy of validWords
    }

    // Load valid words
    private List<String> loadWords(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.strip());
            }
        } catch (IOException e) {
            System.err.println("There seems to be an issue with " + filename);
        }
        return Collections.unmodifiableList(words); // Return a list
    }

    // Getter method for accessing validWord immutable list
    public List<String> getValidWords() {
        return validWords;
    }

    // Getter method for accessing the gameState
    public State getGameState() {
        return gameState;
    }

    // Process input, check if it's a good guess, and return the newest guess
    public Guess processInput(String newWord) {
        Guess newestGuess;
        if (newWord == null) {
            newestGuess = new Guess();
        } else {
            newestGuess = gameState.makeGuess(newWord.toLowerCase());
        }
        return newestGuess;
    }

    // Check if the last guess was a good guess
    public boolean wasGoodGuess() {
        return gameState.getLastResult() != GuessResult.INVALID;
    }

    // Check if the game is over
    public boolean isGameOver() {
        return gameState.checkWin(6);
    }

    // Get the correct word
    public String getCorrectWord() {
        return gameState.getSecretWord();
    }
}

