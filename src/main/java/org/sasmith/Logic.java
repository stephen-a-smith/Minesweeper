package org.sasmith;

/**
 * The Logic Unit for the Minesweeper Game
 */
public class Logic {
  Board board;
  boolean winState, failState;
  int MinesLeft;
  boolean firstClick;

  /**
   * Default Initializer - Starts a new game with the specified dimensions
   *
   * @param x - The Width of the Game to be started
   * @param y - The Height of the Game to be started
   */
  public Logic(int x, int y) {
    System.out.println("NEW BOARD");
    this.board = new Board(x, y);
    MinesLeft = board.getNumMines();
    winState = false;
    failState = false;
    firstClick = true;
  }

  /**
   * Custom Initializer - Starts a game with specified dimensions and specified number of mines.
   *
   * @param x        - The Width of the Game to be started
   * @param y        - The Height of the game to be Started
   * @param numMines - The number of mines on the board
   */
  public Logic(int x, int y, int numMines) {
    this.board = new Board(x, y, numMines);
    MinesLeft = board.getNumMines();
    winState = false;
    failState = false;
    firstClick = true;
  }

  /**
   * Called to update the state of the game. Receives the coordinate to update and the type of click
   *
   * @param x     - The X coordinate of the Node to update
   * @param y     - The Y coordinate of the Node to update
   * @param click - 0 for Mouse1, 1 for Mouse2
   */
  public void update(int x, int y, int click) {
    //if you've lost, no more updates
    if (failState) {
      return;
    }

    //on the first click we init the board such that first click is not a mine
    if (firstClick) {
      board.start(x, y);
      firstClick = false;
    }
    if (click == 0) {
      leftClick(x, y);
    } else {
      rightClick(x, y);
    }
  }

  /**
   * Handles updates from a left click (or Mouse1).
   * Left Clicks can only reveal hidden Nodes
   *
   * @param x - The X coordinate of the Node to update
   * @param y - The Y coordinate of the Node to update
   */
  private void leftClick(int x, int y) {
    if (board.getNode(x, y).isFlagged() || board.getNode(x, y).isSeen()) {

    } else {
      reveal(x, y);
    }
  }

  /**
   * Handles updates from a right click (or Mouse2).
   * Right clicks can trigger the Flag state, or reveal all adjacent nodes when clicking a seen Node
   *
   * @param x - The X coordinate of the Node to update
   * @param y - The Y coordinate of the Node to update
   */
  private void rightClick(int x, int y) {
    if (!board.getNode(x, y).isSeen()) {
      if (board.getNode(x, y).isFlagged()) {
        board.getNode(x, y).setFlagged(false);
      } else {
        board.getNode(x, y).setFlagged(true);
      }
    } else {
      if (board.countFlags(x, y) >= board.getNode(x, y).getAdj()) {
        for (int i = y - 1; i < y + 2; i++) {
          for (int j = x - 1; j < x + 2; j++) {
            if (i < 0 || i >= board.getX() || j < 0 || j >= board.getY() || board.getNode(j, i).isSeen() || board.getNode(j, i).isFlagged()) {
              continue;
            } else {
              reveal(j, i);
            }
          }
        }
      }
    }
  }

  /**
   * Triggers the failState, disabling all updates for this Game.
   */
  private void lose() {
    this.failState = true;
  }

  /**
   * Reveals the node at the specified coordinate/
   * If the node is a 0, it will recursively operate on all adjacent hidden non-flagged nodes as well.
   *
   * @param x - The X coordinate of the Node to update
   * @param y - The Y coordinate of the Node to update
   */
  private void reveal(int x, int y) {
    board.getNode(x, y).setSeen(true);
    if (board.getNode(x, y).isMine()) {
      lose();
      return;
    }
    if (board.getNode(x, y).getAdj() == 0) {
      for (int i = y - 1; i < y + 2; i++) {
        for (int j = x - 1; j < x + 2; j++) {
          if (i < 0 || i >= board.getY() || j < 0 || j >= board.getX() || board.getNode(j, i).isSeen() || board.getNode(j, i).isFlagged()) {
            continue;
          } else {
            reveal(j, i);
          }
        }
      }
    }
  }

  /**
   * @return returns the Board currently held in Logic
   */
  public Board getBoard() {
    return board;
  }

  /**
   * @return The current failState of the Game
   */
  public boolean getFailState() {
    return failState;
  }
}
