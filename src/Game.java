import java.util.*;

public class Game {
    private Movies movies;
    private Player player1;
    private Player player2;
    private String prevMovie;
    private HashSet<String> moviesPlayed;
    private int roundsPlayed;
    private boolean turn; // true for player1, false for player2
    private String winner;
    private String autocompleteFile;
    private LinkedList<AbstractMap.Entry<String, List<String>>> lastFivePlayed;

    /**
     * Initializes the Game object with two players and a movie database file.
     * @param fileName Path to the file containing movie data.
     * @param player1Name Name of Player 1.
     * @param player2Name Name of Player 2.
     * @param objectiveGenre1 Objective genre for Player 1.
     * @param objectiveGenre2 Objective genre for Player 2.
     */
    public Game(String fileName, String player1Name, String player2Name,
                String objectiveGenre1, String objectiveGenre2) {
        this.movies = new Movies(fileName);
        this.autocompleteFile = "src/autocomplete.txt";
        this.player1 = new Player(player1Name, objectiveGenre1, 5);
        this.player2 = new Player(player2Name, objectiveGenre2, 5);
        this.moviesPlayed = new HashSet<>();
        this.lastFivePlayed = new LinkedList<>();
        this.turn = true;
        this.roundsPlayed = 0;
        this.winner = null;
        this.prevMovie = movies.getRandomMovie();
        moviesPlayed.add(prevMovie);
        lastFivePlayed.add(new AbstractMap.SimpleEntry<>(prevMovie, new ArrayList<>()));
    }

    /**
     * Updates the game state with the movie played by the current player.
     * Validates the move, updates player progress, and tracks game status.
     * @param moviePlayed The movie chosen by the current player.
     * @param player The username of the player making the move.
     * @return True if the move was valid, false otherwise.
     */
    public boolean update(String moviePlayed, String player) {
        // Check that movie hasn't already been used
        if (moviesPlayed.contains(moviePlayed)) {
            System.out.println(moviePlayed + " has already been played.");
            return false;
        }

        // Validate the connection between the last movie and the new movie
        List<String> links = movies.getConnection(prevMovie, moviePlayed);
        if (links.isEmpty()) {
            return false;
        }

        // Retrieve genres of the played movie
        List<String> genres = movies.getMovieGenres(moviePlayed);
        boolean valid = false;

        // Determine which player is making the move
        if (player.equals(player1.getUsername())) {
            valid = player1.handleMovie(links, genres);
            if (player1.hasMetObjective()) {
                winner = player1.getUsername();
            }
        } else if (player.equals(player2.getUsername())) {
            valid = player2.handleMovie(links, genres);
            if (player2.hasMetObjective()) {
                winner = player2.getUsername();
            }
        }

        // If the move was valid, update game state
        if (valid) {
            moviesPlayed.add(moviePlayed);
            lastFivePlayed.addFirst(new AbstractMap.SimpleEntry<>(moviePlayed, links));
            if (lastFivePlayed.size() > 5) {
                lastFivePlayed.removeLast();
            }

            prevMovie = moviePlayed; // Update the last played movie
            roundsPlayed++;          // Increment the round count
            turn = !turn;
        }

        return valid;
    }

    /**
     * Checks if the game is over.
     * @return True if a player has won, false otherwise.
     */
    public boolean isGameOver() {
        return winner != null;
    }

    /**
     * Gets the winner of the game.
     * @return The username of the winning player, or null if no winner yet.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Determines whose turn it is to play.
     * @return The username of the player whose turn it is.
     */
    public String getWhosTurn() {
        return turn ? player1.getUsername() : player2.getUsername();
    }

    /**
     * Retrieves Player 1's progress as a percentage.
     * @return Player 1's progress percentage.
     */
    public double progressPlayer1() {
        return player1.progressSoFar();
    }

    /**
     * Retrieves Player 2's progress as a percentage.
     * @return Player 2's progress percentage.
     */
    public double progressPlayer2() {
        return player2.progressSoFar();
    }

    /**
     * Gets Player 1's username.
     * @return Player 1's username.
     */
    public String usernamePlayer1() {
        return player1.getUsername();
    }

    /**
     * Gets Player 2's username.
     * @return Player 2's username.
     */
    public String usernamePlayer2() {
        return player2.getUsername();
    }

    /**
     * Provides a visual representation of link usage for Player 1.
     * @return Map of Player 1's links and their usage display.
     */
    public Map<String, String> getPlayer1LinkUsageDisplay() {
        return player1.getLinkUsageDisplay();
    }

    /**
     * Provides a visual representation of link usage for Player 2.
     * @return Map of Player 2's links and their usage display.
     */
    public Map<String, String> getPlayer2LinkUsageDisplay() {
        return player2.getLinkUsageDisplay();
    }

    /**
     * Retrieves the list of the last five movies played with their connections.
     * @return A list of the last five played movies and their connections.
     */
    public LinkedList<AbstractMap.Entry<String, List<String>>> getLastFivePlayed() {
        return lastFivePlayed;
    }

    /**
     * Gets the total number of rounds played.
     * @return The number of rounds played.
     */
    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    /**
     * Gets the filename of the autocomplete file.
     * @return The path of the autocomplete file.
     */
    public String getAutocompleteFileName() {
        return autocompleteFile;
    }

    /**
     * Gets the current movie being played.
     * @return The title of the current movie.
     */
    public String getCurrentMovie() {
        return prevMovie;
    }
}