package de.lmu.bio.ifi;

import de.lmu.bio.ifi.basicpackage.OthelloBoard;
import szte.mi.Move;
import szte.mi.Player;

import java.util.List;
import java.util.Random;

public class Bot implements Player {
    Othello game;
    boolean p1 = false;
    Setting s = Setting.MINMAX;
    @Override
    public void init(int order, long t, Random rnd) {
        this.p1 = (order == 0);
        this.game = new Othello();
    }

    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        return bestMove(prevMove, t);
    }

    public Bot() {}

    public Bot(Othello game) {
        this.game = game;
    }

    public Bot(Othello game, Setting s) {
        this.game = game;
        this.s = s;
    }
    private Move bestMove(Move prevMove, long t_left) {

        // Input opponent move
        if (prevMove != null) game.makeMove(!p1, prevMove);
        else {
            if (game.turn != 0) game.turn_p1 = !game.turn_p1;   // OPP SKIPPED
        }

        List<Move> moves = game.getPossibleMoves(p1);
        if (moves == null || moves.isEmpty()) {
            game.turn_p1 = !game.turn_p1;
            return null;
        }

        if (s == Setting.RANDOM) {      // RANDOM
            Move nextMove = null;
            nextMove = moves.get((int) Math.floor(Math.random() * moves.size()));
            game.makeMove(p1, nextMove);
            return nextMove;
        }
        else if (s == Setting.MAX_VALUE){       // GREEDY
            Move bestMove = moves.get(0);
            int maxSCORE = 0;
            for (Move m : moves){
                if (checkCorner(m)){
                    bestMove = m;
                    break;
                }
                int value = game.checkVALUE(p1, m.x, m.y);
                if (value > maxSCORE) {
                    bestMove = m;
                    maxSCORE = value;
                }
            }
            game.makeMove(p1, bestMove);
            return bestMove;
        }
        else if (s == Setting.SCORE_TABLE) {      // SCORE BOARD DEPTH 1
            Move bestMove = moves.get(0);
            int maxSCORE = 0;
            for (Move m : moves){
                int[][] temp = new int[8][8];
                Othello tempOT = game.clone();
                tempOT.makeMove(p1, m);
                int value = tempOT.checkScore(p1);
                if (value > maxSCORE) {
                    bestMove = m;
                    maxSCORE = value;
                }
            }
            System.out.println("Best move is: " + bestMove.toString() + " with value: " + maxSCORE);
            game.makeMove(p1, bestMove);
            return bestMove;
        }
        else if (s == Setting.MINMAX) {

            Move bestMove = null;
            int alpha = Integer.MIN_VALUE;

            // TODO: MINMAX PARAMETERS
            int depth = 4;
            if (game.turn > 25) {
                depth = 5;
            }


             long startTime = System.currentTimeMillis();        // Calculate time limit for this move
             long timePerMove = t_left / (32 - game.turn + 1);

            for (Move m : moves) {                          // Simulate single turn MAXING
                int[][] temp = new int[8][8];
                Othello tempOT = game.clone();
                tempOT.makeMove(p1, m);

                int value = minmax(tempOT, depth, false, alpha, Integer.MAX_VALUE);

                if (value >= alpha){
                    alpha = value;
                    bestMove = m;
                }

                     if ((System.currentTimeMillis() - startTime) > (timePerMove - 100)) break;
                }

            if (bestMove != null) {
                System.out.println("Best move is: " + bestMove.toString() + " with value: " + alpha + " for the opponent");
                game.makeMove(p1, bestMove);
                return bestMove;
            } else {
                System.out.println("No valid moves");
                game.turn_p1 = !game.turn_p1;
                return null;
            }
        }
        else {
            throw new RuntimeException("Nuh uh Setting wrong");
        }
    }

    private int minmax(Othello ot, int depth, boolean maxing, int alpha, int beta) {        // https://github.com/anushree27/Othello/blob/master/othello.java
        List<Move> moves = ot.getPossibleMoves(ot.turn_p1);
        if (depth == 0 || ot.gameStatus() != GameStatus.RUNNING) {
            return ot.checkScore(this.p1);      // Always check BOTs score
        }
        if (moves.isEmpty()) {
            return maxing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        int value;
        if (maxing) {       // MAX
            value = Integer.MIN_VALUE;
            for (Move m : moves) {
                Othello tempOT = ot.clone();
                tempOT.makeMove(ot.turn_p1, m);
                value = Math.max(value, minmax(tempOT, depth - 1, false, alpha, beta));
                alpha = Math.max(alpha, value);
                if (beta <= alpha) break;
            }
        }
        else {              // MIN
            value = Integer.MAX_VALUE;
            for (Move m : moves) {
                Othello tempOT = ot.clone();
                tempOT.makeMove(ot.turn_p1, m);
                value = Math.min(value, minmax(tempOT, depth - 1, true, alpha, beta));
                beta = Math.min(beta, value);
                if (beta <= alpha) break;
            }
        }
        return value;
    }


    private boolean checkCorner(Move move){
        int dif =  Math.abs(move.x - move.y);
        return dif == 7 || (dif == 0 && (move.x == 0 || move.x == 7));
    }
}
