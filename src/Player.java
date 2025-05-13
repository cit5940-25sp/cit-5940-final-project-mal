import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String username;
    private int progress;
    private String objectiveGenre;
    private int objectiveAmount;
    //(e.g. actor name) and how many times (max 3)
    private HashMap<String, Integer> linksUsed;

    /**
     * Constructor that initializes a new Player with specified username and objective.
     * @param username The username of the player.
     * @param objectiveGenre The target genre for the player's objective.
     * @param objectiveNumber The number of movies to complete the objective.
     */
    public Player (String username, String objectiveGenre, int objectiveNumber) {
        this.username = username;
        this.objectiveGenre = objectiveGenre;
        this.objectiveAmount = objectiveNumber;
        this.linksUsed = new HashMap<>();
        this.progress = 0;
    }

    /**
     * Handles a movie play by recording connections and checking genre match.
     * This method records the usage of each connection (actor) and updates the player's progress
     * if the played movie's genre matches the player's objective genre.
     *
     * @param connections List of connections (e.g., actors) between movies.
     * @param genres List of genres for the current movie.
     * @return True if a connection was successfully used, false otherwise.
     */
    public boolean handleMovie(List<String> connections, List<String> genres) {
        boolean usedConnectionThisTurn = false;

        for (String connection : connections) {
            int count = linksUsed.getOrDefault(connection, 0);
            if (count < 3) {
                linksUsed.put(connection, count + 1);
                usedConnectionThisTurn = true;
            }
        }

        if (usedConnectionThisTurn &&
                genres.stream().anyMatch(genre -> genre.equalsIgnoreCase(objectiveGenre))) {
            progress++;
        }

        return usedConnectionThisTurn;
    }

    /**
     * Generates a visual representation of the usage count of each link (actor/connection).
     * Each link is displayed with ❌ for each time it has been used (up to 3),
     * and ▪️ for remaining unused slots. This provides a clear view of link usage.
     *
     * Example:
     * - A link used 2 times: ❌❌▪️
     * - A link used 0 times: ▪️▪️▪️
     * @return A map where the key is the link (actor name) and the value is a visual representation of its usage.
     */
    public Map<String, String> getLinkUsageDisplay() {
        Map<String, String> usageVisual = new HashMap<>();

        for (Map.Entry<String, Integer> entry : linksUsed.entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue();
            StringBuilder bar = new StringBuilder();

            for (int i = 0; i < count; i++) {
                bar.append("❌");
            }
            for (int i = count; i < 3; i++) {
                bar.append("▪️");
            }

            usageVisual.put(name, bar.toString());
        }

        return usageVisual;
    }

    /**
     * Checks if the player has met their objective.
     * @return True if the player has completed their objective, false otherwise.
     */
    public boolean hasMetObjective() {
        return (objectiveAmount - progress) == 0;
    }

    /**
     * Calculates the player’s progress as a percentage.
     * @return The progress percentage towards the objective.
     */
    public double progressSoFar() {
        return (progress * 100.0) / objectiveAmount;
    }

    /**
     * Gets the player's username.
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the player's objective genre.
     * @return The objective genre of the player.
     */
    public String getObjectiveGenre() {
        return objectiveGenre;
    }
}