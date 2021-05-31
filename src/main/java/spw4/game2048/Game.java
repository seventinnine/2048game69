package spw4.game2048;

import java.util.*;
import spw4.game2048.Tile;

public class Game {
    public Random random;
    public int startTileCount = 2;
    public boolean placeRandomTiles = true;
    public int winCondition = 2048;

    private final int size = 4;
    private int score = 0;
    private boolean moveOccurred = false;
    private boolean gameWon = false;
    private int moves = 0;

    private Tile[][] board;

    public Game() {
        board = new Tile[size][size];
    }

    private Game copyGame() {
        Game gameCpy = new Game();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameCpy.board[i][j] = new Tile(getTile(i, j).getValue());
            }
        }
        return gameCpy;
    }

    public int getScore() {
        return score;
    }

    public int getMoves() {
        return moves;
    }

    public int getValueAt(int x, int y) {
        return board[x][y].getValue();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getTile(i, j).equals(0)) return false;
            }
        }
        return true;
    }

    public boolean isOver() {
        if (isWon()) return true;
        if (!isBoardFull()) return false;
        Game gameCpy = copyGame();
        gameCpy.moveUp();
        gameCpy.moveDown();
        gameCpy.moveLeft();
        gameCpy.moveRight();
        return !gameCpy.moveOccurred;
    }

    public boolean isWon() {
        return gameWon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getTile(i, j).equals(0)) {
                    sb.append(".");
                } else {
                    sb.append(getTile(i, j));
                }
                if (j < size - 1) {
                    sb.append("\t");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private Tile getTile(int row, int col) {
        return board[row][col];
    }

    public void initialize() {
        if (random == null) {
            random = new Random();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile();
            }
        }

        for (int i = 0; i < startTileCount; i++) {
            spawnRandomTile();
        }
        moves = 0;
        score = 0;
        gameWon = false;
    }

    private void spawnRandomTile() {
        if (!placeRandomTiles) return;
        double numberProbability = random.nextDouble();
        getRandomEmptyTile().setValue((numberProbability < 0.9) ? 2 : 4);
    }

    private Tile getRandomEmptyTile() {
        Tile emptyTile;
        do {
            int randomNumber = random.nextInt(size*size);
            emptyTile = getTile(randomNumber/size, randomNumber%size);
        } while (!emptyTile.equals(0));
        return emptyTile;
    }

    public void move(Direction direction) {
        if (isOver() || isWon()) return;

        switch (direction) {
            case up:
                moveUp();
                break;
            case down:
                moveDown();
                break;
            case left:
                moveLeft();
                break;
            case right:
                moveRight();
                break;
        }
        uncheckTiles();
        if (moveOccurred) {
            spawnRandomTile();
            moves++;
            moveOccurred = false;
        }
    }

    private boolean isMergeable(Tile t1, Tile t2) {
        return !t1.isMergedInThisMove() && !t2.isMergedInThisMove()
                && t1.getValue() == t2.getValue();
    }

    private void mergeTiles(Tile source, Tile destination) {
        destination.setValue(destination.getValue() * 2);
        source.setValue(0);
        destination.setMergedInThisMove(true);
        score += destination.getValue();
        gameWon = destination.equals(winCondition);
    }

    private void handleMove(Tile source, Tile destination) {
        if (source.getValue() == 0) return;
        if (destination.equals(0)) {
            destination.setValue(source.getValue());
            source.setValue(0);
            moveOccurred = true;
        } else {
            if (isMergeable(source, destination)) {
                mergeTiles(source, destination);
                moveOccurred = true;
            }
        }
    }


    private void moveUp() {
        for (int k = 0; k < size - 1; k++) {
            for (int i = 1; i < size - k; i++) {
                for (int j = 0; j < size; j++) {
                    handleMove(getTile(i, j), getTile(i - 1, j));
                }
            }
        }
    }

    private void moveDown() {
        for (int k = 0; k < size - 1; k++) {
            for (int i = size - 2; i >= k; i--) {
                for (int j = 0; j < size; j++) {
                    handleMove(getTile(i, j), getTile(i + 1, j));
                }
            }
        }
    }

    private void moveLeft() {
        for (int k = 0; k < size - 1; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 1; j < size - k; j++) {
                    handleMove(getTile(i, j), getTile(i, j - 1));
                }
            }
        }
    }

    private void moveRight() {
        for (int k = 0; k < size - 1; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = size - 2; j >= k; j--) {
                    handleMove(getTile(i, j), getTile(i, j + 1));
                }
            }
        }
    }

    private void uncheckTiles() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getTile(i, j).setMergedInThisMove(false);
            }
        }
    }
}
