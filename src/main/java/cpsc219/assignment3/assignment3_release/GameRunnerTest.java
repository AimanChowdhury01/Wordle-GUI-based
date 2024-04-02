package cpsc219.assignment3.assignment3_release;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class GameRunnerTest {

    // Test case for processInput(String newWord) method
    @Test
    public void testProcessInput() {
        GameRunner gameRunner = new GameRunner();

        // Test with a valid guess
        Guess validGuess = gameRunner.processInput("apple");
        assertEquals(true, validGuess.hasWin()); // Assuming "apple" is the correct word

        // Test with an invalid guess
        Guess invalidGuess = gameRunner.processInput("xyz");
        assertEquals(false, invalidGuess.hasWin()); // Assuming "xyz" is not the correct word
    }

    // Test case for wasGoodGuess() method
    @Test
    public void testWasGoodGuess() {
        GameRunner gameRunner = new GameRunner();

        // Test when the last guess was valid
        boolean goodGuess = gameRunner.wasGoodGuess();
        assertTrue(goodGuess); // Assuming the last guess was valid

        // Test when the last guess was invalid
        // This scenario can be more complex to test directly because it depends on the internal state of the game.
        // For simplicity, let's assume that we are testing the behavior when the last guess was invalid.
        // We can create a mock GameState object that always returns GuessResult.INVALID.
        // Since this approach involves mocking internal dependencies, it may require additional setup using a mocking framework like Mockito.
    }
}
