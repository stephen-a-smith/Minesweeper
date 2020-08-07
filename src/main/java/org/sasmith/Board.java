package org.sasmith;

/**
 * Raw Dfinition of a Baord
 */
public class Board {
    private Node[][] board;
    private int numMines, x, y;

    /**
     * Constructor for unspecified Mine number,
     * Number of mines generated is randomly within 10-20% of the total area of the board
     * @param x - The Width of the Board
     * @param y - The height of the Board */
    public Board(int x, int y){
        this(x, y, (int) ((Math.random()*10 + 10)/100 * x * y));
    }

    /**
     * Full Constructor, initializes the board
     * @param x - The Width of the Board
     * @param y - The height of the Board
     * @param numMines - The number of Mines*/
    public Board(int x, int y, int numMines){
        this.numMines = numMines;
        this.x = x;
        this.y = y;
        this.board = new Node[y][x];
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                this.board[i][j] = new Node();
            }
        }

    }
    /**
     * Initializes the board at the start of the game
     * Used to make mine generation only happen after the first click
     * While still allowing the board to have been created.
     * @param x - X coordinate of first click
     * @param y - Y coordinate of first click */
    public void start(int x, int y){
        placeMines(x, y);
        placeNums();
    }

    /**
     * Places mines on the board such that the first click will never be a mine
     * Also ensure that the correct number of mines get placed
     * @param x - The X coordinate of first click
     * @param y - The Y coordinate of first click */
    private void placeMines(int x, int y){
        for(int i = 0; i < numMines; i++){
            int ranX = (int) (Math.random() * this.x);
            int ranY = (int) (Math.random() * this.y);

            //don't place mine on first click or another mine
            if(ranX == x && ranY == y || board[ranY][ranX].isMine()){
                i--;
                continue;
            }
            board[ranY][ranX].setMine(true);
        }
    }
    /**
     * Assigns the correct value to each Node for how many adjacent mines they have*/
    private void placeNums(){
        //for every node
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                board[i][j].setAdj(countMines(j, i));
            }
        }
    }

    /**
     * Counts all the mines adjacent to a particular point on our Board
     * @param x - The X coordinate of our target Node
     * @param y - The Y coordinate of our target Node */
    private int countMines(int x, int y){
        int adj = 0;
        for(int i = y-1; i <= y+1; i++){
            for(int j = x-1; j <= x+1; j++){
                if(i < 0 || i >= this.y || j < 0 || j >= this.x) {
                    continue;
                }else if(board[i][j].isMine()){
                    adj++;
                }
            }
        }
        return adj;
    }

    public int countFlags(int x, int y){
        int adj = 0;
        for(int i = y-1; i <= y+1; i++){
            for(int j = x-1; j <= x+1; j++){
                if(i < 0 || i >= this.y || j < 0 || j >= this.x) {
                    continue;
                }else if(board[i][j].isFlagged()){
                    adj++;
                }
            }
        }
        return adj;
    }

    public Node[][] getBoard() {
        return board;
    }

    public int getNumMines() {
        return numMines;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getNode(int x, int y){
        return board[y][x];
    }
}
