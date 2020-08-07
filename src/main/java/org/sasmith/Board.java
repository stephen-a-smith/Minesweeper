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
     * @param y - The Y coordinate of our target Node
     * @return int - The number of adjacent flags*/
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

    /**
     * Counts the number of Adjacent flags adjacent to an origin point
     * @param x Co-ordinate of origin
     * @param y Co-ordiante of origin
     * @return int - Number of Adjacent Flags
     */
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

    /**
     * @return int - Number of Adjacent Mines
     */
    public int getNumMines() {
        return numMines;
    }

    /**
     * @return int - Width of the Board
     */
    public int getX() {
        return x;
    }

    /**
     * @return int - Height of the Board
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the Node at the specified coordinates
     * @param x - The X coordinate of the requested Node
     * @param y - The Y coordinate of the requested Node
     * @return Node - The requested Node
     */
    public Node getNode(int x, int y){
        return board[y][x];
    }
}
