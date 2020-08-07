package org.sasmith;
/**
 * Defines a Node, the smallest unit on the Minesweeper Board
 *
 */
public class Node {
    private boolean isMine, isSeen, isFlagged;
    private int numAdj;

    /**
     * Nodes are by default: Not a mine, Hidden, Not Flagged, and have no Adjacent Mines*/
    public Node(){
        isMine = false;
        isSeen = false;
        isFlagged = false;
        numAdj = 0;
    }

    /**
     * @return Boolean representing if this Node is a mine */
    public boolean isMine(){
        return isMine;
    }

    /**
     * Sets this Node's Mine status
     * @param b - Sets this to a mine if passed True, revokes mine status if passed false
     */
    public void setMine(boolean b){
        isMine = b;
    }

    /**
     * @return Boolean based on if this node has been revealed
     */
    public boolean isSeen(){
        return isSeen;
    }

    /**
     * Sets this node to be hidden or visible
     * @param b - Sets Node to visible if true, hidden if false
     */
    public void setSeen(boolean b){
        isSeen = b;
    }

    /**
     * @return Boolean based on if this Node is flagged
     */
    public boolean isFlagged(){
        return isFlagged;
    }

    /**
     * Sets or clears a Node's Flagged status
     * @param b - Flags Node if set to true, revokes flag if set to false
     */
    public void setFlagged(boolean b){
        isFlagged = b;
    }

    /**
     * @return Number of Adjacent Mines as an Integer
     */
    public int getAdj(){
        return numAdj;
    }

    /**
     * Sets the number of Adjacent Mines
     * @param i - Number of Adjacent mines to this Node
     */
    public void setAdj(int i){
        numAdj = i;
    }
}