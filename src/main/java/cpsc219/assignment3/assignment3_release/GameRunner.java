package cpsc219.assignment3.assignment3_release;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameRunner {
    ArrayList<String> validWords;
    State gameState;

    public GameRunner() {
        validWords = new ArrayList<String>();
        try {
            FileReader fReader = new FileReader("sgb-words.txt");
            BufferedReader bReader = new BufferedReader(fReader);

            String currentLine;
            while ((currentLine = bReader.readLine()) != null) {
                validWords.add(currentLine.strip());
            }
        } catch (IOException ioE) {
            System.err.println("There seems to be an issue with sgb-words");
        }

        gameState = new State((ArrayList<String>) validWords.clone());
    }

    public Guess processInput(String newWord) {
        Guess newestGuess;
        if (newWord == null) {
            newestGuess = new Guess();
        }
        newestGuess = gameState.makeGuess(newWord.toLowerCase());
        return newestGuess;
    }

    public boolean wasGoodGuess() {
        return gameState.getLastResult() != GuessResult.INVALID;
    }

    public boolean isGameOver() {
        return gameState.checkWin(6);
    }

    public String getCorrectWord() {
        return gameState.secretWord;
    }
}