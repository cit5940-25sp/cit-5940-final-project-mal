import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class GameTest {
    Game game = new Game("src/tmdb_data.txt", "Player1",
            "Player2", "Comedy", "Drama");

    @Test
    public void testGameInitialization() {
        assertNotNull(game);
        assertEquals("Player1", game.usernamePlayer1());
        assertEquals("Player2", game.usernamePlayer2());
        assertNotNull(game.getCurrentMovie());
    }

    @Test
    public void testPlayerTurn() {
        assertEquals("Player1", game.getWhosTurn());
    }


    @Test
    public void testInvalidMoveAlreadyPlayed() {
        String movie = game.getCurrentMovie();
        assertFalse(game.update(movie, "Player1"));
    }

    @Test
    public void testGetLastFivePlayedHasOneElement() {
        assertEquals(1, game.getLastFivePlayed().size());
    }

    @Test
    public void testPlayerLinkUsageDisplay() {
        assertNotNull(game.getPlayer1LinkUsageDisplay());
        assertNotNull(game.getPlayer2LinkUsageDisplay());
    }

    @Test
    public void testRoundsPlayed() {
        assertEquals(0, game.getRoundsPlayed());
    }

    @Test
    public void testGameInitiallyNotOver() {
        assertFalse(game.isGameOver());
//        assertNull(game.getWinner(), "Winner should be null at the start");
    }

    @Test
    public void testAutocompleteFileName() {
        assertEquals("src/autocomplete.txt", game.getAutocompleteFileName());
    }

}