package de.lmu.bio.ifi.basicpackage;

public class OthelloBoard extends BasicBoard{
    public int[][] score = {        // https://github.com/benthecoder/othello-ai/blob/main/evals.py
            {120, -20, 20,  5,  5, 20, -20, 120},
            {-20, -40, -5, -5, -5, -5, -40, -20},
            { 20,  -5, 15,  3,  3, 15,  -5,  20},
            {  5,  -5,  3,  3,  3,  3,  -5,   5},
            {  5,  -5,  3,  3,  3,  3,  -5,   5},
            { 20,  -5, 15,  3,  3, 15,  -5,  20},
            {-20, -40, -5, -5, -5, -5, -40, -20},
            {120, -20, 20,  5,  5, 20, -20, 120}
    };
    public OthelloBoard(){
        setRules("Othello Rules");
        setBoardtype("othello");

        board = new int[8][8];
        board[3][3] = 2;
        board[4][4] = 2;
        board[3][4] = 1;
        board[4][3] = 1;
    }
    public String toString(){
        String out = "";
        String letter;
        for (int[] row : board){

            if(row[0] == 1) out += "X";
            else if(row[0] == 0) out += ".";
            else out += "O";

            for (int i=1; i<8 ;i++){
                out += " ";
                if(row[i] == 1) letter = "X";
                else if(row[i] == 0) letter = ".";
                else letter = "O";
                out += letter;
            }
            out += "\n";
        }
        return out;
    }
}