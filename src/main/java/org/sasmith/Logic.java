package org.sasmith;

public class Logic {
    Board board;
    boolean winState, failState;
    int MinesLeft;
    boolean firstClick;
    public Logic(Board board){
        this.board = board;
        MinesLeft = board.getNumMines();
        winState = false;
        failState = false;
        firstClick = true;
    }

    public void update(int x, int y, int click){
        if(firstClick){
            board.start(x, y);
            firstClick = false;
        }
        if(click == 0){
            leftClick(x, y);
        } else {
            rightClick(x, y);
        }
    }

    private void leftClick(int x, int y){
        if(board.getNode(x, y).isFlagged() || board.getNode(x, y).isSeen()){

        } else if (board.getNode(x, y).isMine()){
            lose();
        } else {
            reveal(x, y);
        }
    }

    private void rightClick(int x, int y){
        if(!board.getNode(x, y).isSeen()){
            if(board.getNode(x, y).isFlagged()){
                board.getNode(x, y).setFlagged(false);
            } else {
                board.getNode(x, y).setFlagged(true);
            }
        } else {
            if(board.countFlags(x, y) >= board.getNode(x, y).getAdj()){
                for(int i = y-1; i < y+2; i++){
                    for (int j = x-1; j < x+2; j++) {
                        if(i < 0 || i >= board.getX() || j < 0 || j >= board.getY() || board.getNode(j, i).isSeen() || board.getNode(j, i).isFlagged()){
                            continue;
                        } else {
                            reveal(j, i);
                        }
                    }
                }
            }
        }
    }

    private void lose(){
        this.failState = true;
    }

    private void reveal(int x, int y){
        board.getNode(x, y).setSeen(true);
        if(board.getNode(x, y).isMine()){
            lose();
        }
        if(board.getNode(x, y).getAdj() == 0){
            for(int i = y-1; i < y+2; i++){
                for (int j = x-1; j < x+2; j++) {
                    if(i < 0 || i >= board.getX() || j < 0 || j >= board.getY() || board.getNode(j, i).isSeen() || board.getNode(j, i).isFlagged()){
                        continue;
                    } else {
                        reveal(j, i);
                    }
                }
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean getFailState(){
        return failState;
    }
}
