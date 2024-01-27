import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JFrame frame;

    public Client(String serverAddress, int serverPort) throws Exception {
        socket = new Socket(serverAddress, serverPort);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Create and set up the window.
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void sendInputToServer() throws Exception {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Inserisci un input:");
            String input = keyboard.readLine();
            out.println(input);

            // Receive the updated game matrix from the server
            String[] rows = in.readLine().split(",");
            int[][] gameMatrix = new int[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                String[] cells = rows[i].split(",");
                gameMatrix[i] = new int[cells.length];
                for (int j = 0; j < cells.length; j++) {
                    gameMatrix[i][j] = Integer.parseInt(cells[j]);
                }
            }

            // Update the window with the new game matrix
            SwingUtilities.invokeLater(() -> drawGameMatrix(gameMatrix));
        }
    }

    private void drawGameMatrix(int[][] gameMatrix) {
        Graphics g = frame.getGraphics();
        for (int i = 0; i < gameMatrix.length; i++) {
            for (int j = 0; j < gameMatrix[i].length; j++) {
                switch (gameMatrix[i][j]) {
                    case 0:
                        g.setColor(Color.black);
                        break;
                    case 1:
                        g.setColor(Color.green);
                        break;
                    case 2:
                        g.setColor(Color.blue);
                        break;
                    case 3:
                        g.setColor(Color.red);
                        break;
                }
                g.fillRect(j * 10, i * 10, 10, 10);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 8080);
        client.sendInputToServer();
    }
}