import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket[] playerSockets = new Socket[2]; // Array to hold the two player sockets
        int playerCount = 0; // Counter for connected players

        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server avviato. In attesa di giocatori...");

            while (true) { // Infinite loop to accept new players
                playerSockets[playerCount] = serverSocket.accept();
                System.out.println("Player " + (playerCount + 1) + " has connected.");

                // Start a new ServerThread for the connected player
                ServerThread serverThread = new ServerThread(playerSockets[playerCount], null);
                new Thread(serverThread).start();

                playerCount++;

                if (playerCount == 2) { // Start a new game when two players have connected
                    Snake2P game = new Snake2P(playerSockets[0], playerSockets[1]);
                    new Thread(game).start();
                    System.out.println("Gioco avviato!");

                    // Reset playerSockets and playerCount for the next game
                    playerSockets = new Socket[2];
                    playerCount = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}