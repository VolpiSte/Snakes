import java.awt.Point;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake2P extends Thread {
    private static final int ROWS = 20;
    private static final int COLS = 10;
    private static final int EMPTY = 0;
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;
    private static final int FRUIT = 3;

    private int[][] gameMatrix;
    private List<Point> player1Snake;
    private List<Point> player2Snake;
    private int player1Score;
    private int player2Score;
    private Direction player1Direction;
    private Direction player2Direction;

    public Snake2P(Socket socket1, Socket socket2) {
        // Initialize game state
        gameMatrix = new int[ROWS][COLS];
        player1Snake = new ArrayList<>();
        player2Snake = new ArrayList<>();
        player1Direction = Direction.RIGHT;  // Initial direction for player 1
        player2Direction = Direction.LEFT;   // Initial direction for player 2

        initializeGame();
    }

    private void initializeGame() {
        // Initialize game matrix
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameMatrix[i][j] = EMPTY;
            }
        }

        // Initialize player 1 snake
        player1Snake.add(new Point(0, 0));
        gameMatrix[0][0] = PLAYER1;

        // Initialize player 2 snake
        player2Snake.add(new Point(ROWS - 1, COLS - 1));
        gameMatrix[ROWS - 1][COLS - 1] = PLAYER2;

        // Place initial fruits
        placeFruit();
    }

    private void placeFruit() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(ROWS);
            col = random.nextInt(COLS);
        } while (gameMatrix[row][col] != EMPTY);

        gameMatrix[row][col] = FRUIT;
    }

    public void handleInput(String input) {
        int playerNumber = (input.equals("UP") || input.equals("DOWN")) ? 1 : 2;

        switch (playerNumber) {
            case 1:
                updateDirection(player1Direction, input);
                break;
            case 2:
                updateDirection(player2Direction, input);
                break;
            default:
                break;
        }
    }

    private void updateDirection(Direction direction, String input) {
        switch (input) {
            case "UP":
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                break;
            case "DOWN":
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                break;
            case "LEFT":
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                break;
            case "RIGHT":
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                break;
            default:
                break;
        }
    }

    private void movePlayer(List<Point> snake, Direction direction) {
        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case UP:
                newHead.setLocation(head.getX(), (head.getY() - 1 + ROWS) % ROWS);
                break;
            case DOWN:
                newHead.setLocation(head.getX(), (head.getY() + 1) % ROWS);
                break;
            case LEFT:
                newHead.setLocation((head.getX() - 1 + COLS) % COLS, head.getY());
                break;
            case RIGHT:
                newHead.setLocation((head.getX() + 1) % COLS, head.getY());
                break;
            default:
                break;
        }

        // Aggiorna la posizione della testa
        snake.add(0, newHead);

        // Rimuovi la coda se il serpente non ha mangiato un frutto
        if (!hasEatenFruit(snake)) {
            snake.remove(snake.size() - 1);
        }
    }

    private boolean hasEatenFruit(List<Point> snake) {
        //Implementa la logica per verificare se il serpente ha mangiato un frutto
        return false;
    }

    private void checkCollisions() {
        // TODO: Implementa la logica per verificare collisioni con pareti, altri serpenti e frutti
        
    }

    private void updateGameMatrix() {
        // TODO: Aggiorna la matrice di gioco in base allo stato corrente del gioco
    }

    private boolean checkWinConditions() {
        // TODO: Verifica le condizioni di vittoria/sconfitta e restituisci true se il gioco è finito
        return false;
    }

    public String[][] getUpdatedGameMatrix() {
        // TODO: Implement the logic to update the game state
        return null;
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
