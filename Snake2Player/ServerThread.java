import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Snake2P game;
    private Socket player;

    public ServerThread(Socket player, Snake2P game) throws Exception {
        this.player = player;
        this.game = game;

        in = new BufferedReader(new InputStreamReader(player.getInputStream()));
        out = new PrintWriter(player.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = in.readLine()) != null) {
                // Send the input to the game
                game.handleInput(input);

                // Wait for the game to update and get the updated game matrix
                String[][] updatedGameMatrix = game.getUpdatedGameMatrix();

                // Send the updated game matrix to the client
                for (String[] row : updatedGameMatrix) {
                    out.println(String.join(",", row));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}