package de.lmu.bio.ifi;

import de.lmu.bio.ifi.basicpackage.OthelloBoard;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Othello extends OthelloBoard implements Game {
    private final int X = 1;
    private final int Y = 2;
    public boolean turn_p1 = true;
    public int turn = 0;
    public GameStatus status = GameStatus.RUNNING;

    public Othello(){super();}
    public Othello(int[][] board, boolean turn_p1, int turn){
        this.board = board;
        this.turn_p1 = turn_p1;
        this.turn = turn;
    }
    @Override
    public boolean makeMove(boolean playerOne, int x, int y) {
        if (status == GameStatus.RUNNING){
            if (playerOne == turn_p1) {
                if (containsMove(getPossibleMoves(playerOne), new Move(x, y))) {
                    flipSTONES(playerOne, x, y);
                    turn_p1 = !turn_p1;
                    if (turn_p1) turn++;
                    status = gameStatus();
                    return true;
                }
                return false;
            }
            return false;
        }
        System.out.println("GAME OVER");
        return false;
    }
    @Override
    public GameStatus gameStatus() {

        // check if playable moves exist (GAME RUNNING)
        if (!getPossibleMoves(turn_p1).isEmpty()){
            return GameStatus.RUNNING;
        }
        else{
            Othello tempOT = this.clone();
            tempOT.turn_p1 = !turn_p1;
            if (!tempOT.getPossibleMoves(tempOT.turn_p1).isEmpty()) return GameStatus.RUNNING;
        }

        // CASE GAME OVER: check who won
        int sum1 = 0;
        int sum2 = 0;

        for (int[] row : board){
            for (int space : row){
                if (space == 1) sum1++;
                else if (space == 2) sum2++;
            }
        }

        if (sum1<sum2) return GameStatus.PLAYER_2_WON;
        else if (sum2<sum1) return GameStatus.PLAYER_1_WON;
        else return GameStatus.DRAW;

    }
    @Override
    public List<Move> getPossibleMoves(boolean playerOne) {
        if (playerOne != turn_p1) return null;

        List<Move> moves = new ArrayList<>();
        int player = 0;
        int opponent = 0;
        if (playerOne) {
            player = 1;
            opponent = 2;
        }
        else {
            player = 2;
            opponent = 1;
        }

        // ---------------------------- check rows ----------------------------

        for (int y=0; y<8; y++){
            int pivot = -1;
            boolean false_pivot = false;        // location of false pivot is irrelevant
            boolean enclosed = false;           // condition: indicates pivot is followed by opp. stone(s)
            boolean reverse_enclosed = false;   // condition: indicates false pivot is followed by opp. stone(s)

            for (int x=0; x<8; x++){

                if (board[y][x] == 0) {
                    if (false_pivot && reverse_enclosed) moves.add(new Move(x,y));
                    else pivot = x;
                    false_pivot = false;
                    enclosed = false;
                    reverse_enclosed = false;
                }

                else if (board[y][x] == opponent){
                    if (pivot>-1) enclosed = true;
                    else if (false_pivot) reverse_enclosed = true;
                }

                else if (board[y][x] == player) {
                    if (pivot>-1 && enclosed) {         // only opponent stones have followed the pivot
                        moves.add(new Move(pivot, y));
                    }
                    pivot = -1;
                    false_pivot = true;                // player stone allows for a possible move later
                    enclosed = false;
                    reverse_enclosed = false;
                }

            }
        }

        // ---------------------------- check columns ----------------------------

        for (int x=0; x<8; x++){
            int pivot = -1;
            boolean false_pivot = false;        // location of false pivot is irrelevant
            boolean enclosed = false;           // condition: indicates pivot is followed by opp. stone(s)
            boolean reverse_enclosed = false;   // condition: indicates false pivot is followed by opp. stone(s)

            for (int y=0; y<8; y++){

                if (board[y][x] == 0) {
                    if (false_pivot && reverse_enclosed) moves.add(new Move(x,y));
                    else pivot = y;
                    false_pivot = false;
                    enclosed = false;
                    reverse_enclosed = false;
                }

                else if (board[y][x] == opponent){
                    if (pivot>-1) enclosed = true;
                    else if (false_pivot) reverse_enclosed = true;
                }

                else if (board[y][x] == player) {
                    if (pivot>-1 && enclosed) {         // only opponent stones have followed the pivot
                        moves.add(new Move(x, pivot));
                    }
                    pivot = -1;
                    false_pivot = true;                // player stone allows for a possible move later
                    enclosed = false;
                    reverse_enclosed = false;
                }

            }
        }

        // ---------------------------- check diagonals ----------------------------

        // -------- Top Left to Bottom Right --------

        for (int y=0; y<6; y++){

            int pivot = -1;
            boolean false_pivot = false;        // location of false pivot is irrelevant
            boolean enclosed = false;           // condition: indicates pivot is followed by opp. stone(s)
            boolean reverse_enclosed = false;   // condition: indicates false pivot is followed by opp. stone(s)

            for (int x=y; x<8; x++){

                if (board[x-y][x] == 0) {
                    if (false_pivot && reverse_enclosed) moves.add(new Move(x,x-y));
                    else pivot = x;
                    false_pivot = false;
                    enclosed = false;
                    reverse_enclosed = false;
                }

                else if (board[x-y][x] == opponent){
                    if (pivot>-1) enclosed = true;
                    else if (false_pivot) reverse_enclosed = true;
                }

                else if (board[x-y][x] == player) {
                    if (pivot>-1 && enclosed) {         // only opponent stones have followed the pivot
                        moves.add(new Move(pivot,pivot-y));
                    }
                    pivot = -1;
                    false_pivot = true;                // player stone allows for a possible move later
                    enclosed = false;
                    reverse_enclosed = false;
                }

            }
        }
        for (int x=0; x<6; x++){

            int pivot = -1;
            boolean false_pivot = false;        // location of false pivot is irrelevant
            boolean enclosed = false;           // condition: indicates pivot is followed by opp. stone(s)
            boolean reverse_enclosed = false;   // condition: indicates false pivot is followed by opp. stone(s)

            for (int y=x; y<8; y++){

                if (board[y][y-x] == 0) {
                    if (false_pivot && reverse_enclosed) moves.add(new Move(y-x,y));
                    else pivot = y;
                    false_pivot = false;
                    enclosed = false;
                    reverse_enclosed = false;
                }

                else if (board[y][y-x] == opponent){
                    if (pivot>-1) enclosed = true;
                    else if (false_pivot) reverse_enclosed = true;
                }

                else if (board[y][y-x] == player) {
                    if (pivot>-1 && enclosed) {         // only opponent stones have followed the pivot
                        moves.add(new Move(pivot-x, pivot));
                    }
                    pivot = -1;
                    false_pivot = true;                // player stone allows for a possible move later
                    enclosed = false;
                    reverse_enclosed = false;
                }

            }
        }

        // -------- Top Right to Bottom Left --------

        for (int y=2; y<8; y++){

            int pivot = -1;
            boolean false_pivot = false;        // location of false pivot is irrelevant
            boolean enclosed = false;           // condition: indicates pivot is followed by opp. stone(s)
            boolean reverse_enclosed = false;   // condition: indicates false pivot is followed by opp. stone(s)

            for (int x=0; x<y+1; x++){

                if (board[y-x][x] == 0) {
                    if (false_pivot && reverse_enclosed) moves.add(new Move(x,y-x));
                    else pivot = x;
                    false_pivot = false;
                    enclosed = false;
                    reverse_enclosed = false;
                }

                else if (board[y-x][x] == opponent){
                    if (pivot>-1) enclosed = true;
                    else if (false_pivot) reverse_enclosed = true;
                }

                else if (board[y-x][x] == player) {
                    if (pivot>-1 && enclosed) {         // only opponent stones have followed the pivot
                        moves.add(new Move(pivot, y-pivot));
                    }
                    pivot = -1;
                    false_pivot = true;                // player stone allows for a possible move later
                    enclosed = false;
                    reverse_enclosed = false;
                }

            }
        }
        for (int y=0; y<6; y++){

            int pivot = -1;
            boolean false_pivot = false;        // location of false pivot is irrelevant
            boolean enclosed = false;           // condition: indicates pivot is followed by opp. stone(s)
            boolean reverse_enclosed = false;   // condition: indicates false pivot is followed by opp. stone(s)

            for (int x=y; x<8; x++){

                if (board[7-x+y][x] == 0) {
                    if (false_pivot && reverse_enclosed) moves.add(new Move(x,7-x+y));
                    else pivot = x;
                    false_pivot = false;
                    enclosed = false;
                    reverse_enclosed = false;
                }

                else if (board[7-x+y][x] == opponent){
                    if (pivot>-1) enclosed = true;
                    else if (false_pivot) reverse_enclosed = true;
                }

                else if (board[7-x+y][x] == player) {
                    if (pivot>-1 && enclosed) {         // only opponent stones have followed the pivot
                        moves.add(new Move(pivot,7-pivot+y));
                    }
                    pivot = -1;
                    false_pivot = true;                // player stone allows for a possible move later
                    enclosed = false;
                    reverse_enclosed = false;
                }

            }
        }

        return sortMoves(moves);
    }
    protected boolean containsMove(List<Move> moves, Move move){
        for (Move m : moves){
            if (move.x == m.x && move.y == m.y) return true;
        }
        return false;
    }
    protected void flipSTONES(boolean p1, int x, int y){
        int player = 0;
        int opponent = 0;
        if(p1){
            player = X;
            opponent = Y;
        }
        else{
            player = Y;
            opponent = X;
        }
        board[y][x] = player;

        for (int vy=-1; vy<2; vy++){
            for (int vx=-1; vx<2; vx++){
                List<Move> opp_stones = new ArrayList<>();
                int i = 1;
                try{
                    while (true) {
                        if (board[y + vy * i][x + vx * i] == opponent) {
                            opp_stones.add(new Move(x + vx * i, y + vy * i));
                        } else if (board[y + vy * i][x + vx * i] == player) {
                            flipSTONES(p1, opp_stones);
                            break;
                        } else if (board[y + vy * i][x + vx * i] == 0) {
                            break;
                        }
                        i++;
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}
            }
        }
    }
    protected void flipSTONES(boolean p1, List<Move> stones){
        int player = 0;
        if(p1) player = X;
        else player = Y;
        for(Move m : stones){
            board[m.y][m.x] = player;
        }
    }
    protected int checkVALUE(boolean p1, int x, int y){
        int player = 0;
        int opponent = 0;
        if(p1){
            player = X;
            opponent = Y;
        }
        else{
            player = Y;
            opponent = X;
        }

        int total_count = 0;
        for (int vy=-1; vy<2; vy++){
            for (int vx=-1; vx<2; vx++){
                int i = 1;
                int count = 0;
                try{
                    while (true) {
                        if (board[y + vy * i][x + vx * i] == opponent) {
                            count++;
                        } else if (board[y + vy * i][x + vx * i] == player) {
                            break;
                        } else if (board[y + vy * i][x + vx * i] == 0) {
                            count = 0;
                            break;
                        }
                        i++;
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {count = 0;}
                total_count += count;
            }
        }
        return total_count;
    }
    protected List<Move> sortMoves(List<Move> moves){
        List<String> moves_st = new ArrayList<>();
        List<Move> moves_new = new ArrayList<>();

        for (Move m : moves){
            String out = m.x + "/" + m.y;
            moves_st.add(out);
        }
        Collections.sort(moves_st);
        for (String s : moves_st){
            Move move = new Move(s.charAt(0) - '0', s.charAt(2) - '0');
            if (containsMove(moves_new, move)){
                continue;
            }
            moves_new.add(move);
        }
        return moves_new;
    }
    public String toString(){
        return super.toString();
    }

    public boolean makeMove(boolean p1, Move move) {
        return makeMove(p1, move.x, move.y);
    }
    @Override
    public Othello clone() {    //https://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java
        try {
            int[][] copy = new int[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                System.arraycopy(this.board[i], 0, copy[i], 0, board[i].length);
            }
            return new Othello(copy, turn_p1, turn);

        } catch (Exception e) {
            throw new RuntimeException("Cloning Fail", e);
        }
    }
    public int checkScore(boolean p1) {
        int p1_score = 0, p2_score = 0;
        int p1_disc = 0, p2_disc = 0;
        int player = p1 ? 1 : 2;
        int opponent = p1 ? 2 : 1;

        // DISC PARITY AND SCORE MATRIX COUNTS
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int square = board[i][j];
                if (square == player) {
                    p1_disc++;
                    p1_score += score[i][j];
                } else if (square == opponent) {
                    p2_disc++;
                    p2_score += score[i][j];
                }
            }
        }
        int positionScore = p1 ? (p1_score - p2_score) : (p2_score - p1_score);
        int discScore = p1 ? (p1_disc - p2_disc) : (p2_disc - p1_disc);

        // MOBILITY
        int p1_mobility = 0, p2_mobility = 0;
        if (turn_p1 == p1) {
            p1_mobility = getPossibleMoves(p1).size();
            p2_mobility = getPossibleMoves_OP().size();
        } else {
            p2_mobility = getPossibleMoves(!p1).size();
            p1_mobility = getPossibleMoves_OP().size();
        }
        int mobilityScore = p1 ? p1_mobility - p2_mobility : p2_mobility - p1_mobility;

        // STABILITY
        int stableP1 = countStableStones(true);
        int stableP2 = countStableStones(false);
        int stabilityScore = p1 ? (stableP1 - stableP2) : (stableP2 - stableP1);

        // FRONTIER STONES
        int p1_frontiers = countFrontierStones(player);
        int p2_frontiers = countFrontierStones(opponent);
        int frontierScore = p1 ? p1_frontiers - p2_frontiers : p2_frontiers - p1_frontiers;

        // PARITY SCORE
        int parityScore = parityScore(p1);

        // TOTAL SCORE
        int total =
                (positionScore * (50))    // 400 -> 300
                + (stabilityScore * 10)
                + (mobilityScore * 20)
                + (discScore * 5 * (turn / 5))       // 0 -> 600
                - (frontierScore * 5)
                // + (parityScore * 700 * (turn > 50 ? 2 : 1))
                ;
        return total;
    }

    public int countStableStones(boolean p1) {      // https://github.com/msravan/Othello/blob/master/StabilityEvaluation.java
        int stableCount = 0;
        int player = p1 ? 1 : 2;

        boolean[][] stable = new boolean[8][8];

        int[][] corners = {{0, 0}, {0, 7}, {7, 0}, {7, 7}};
        for (int[] corner : corners) {
            int x = corner[0], y = corner[1];
            if (board[x][y] == player) {
                isStable(stable, x, y, player);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (stable[i][j] && board[i][j] == player) {
                    stableCount++;
                }
            }
        }

        return stableCount;
    }
    private void isStable(boolean[][] stable, int x, int y, int player) {
        int dx = (x == 0) ? 1 : -1;
        int dy = (y == 0) ? 1 : -1;

        for (int i = x; i >= 0 && i < 8; i += dx) {
            if (board[i][y] == player) {
                stable[i][y] = true;
            } else {
                break;
            }
        }

        for (int j = y; j >= 0 && j < 8; j += dy) {
            if (board[x][j] == player) {
                stable[x][j] = true;
            } else {
                break;
            }
        }

        int i = x, j = y;
        while (i >= 0 && i < 8 && j >= 0 && j < 8) {
            if (board[i][j] == player) {
                stable[i][j] = true;
            } else {
                break;
            }
            i += dx;
            j += dy;
        }
    }
    private int countFrontierStones(int player) {   // inspired from: https://github.com/msravan/Othello/blob/master/FrontierEvaluation.java
        int frontierCount = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == player && isFrontierStone(i, j)) {
                    frontierCount++;
                }
            }
        }
        return frontierCount;
    }
    private boolean isFrontierStone(int row, int col) {
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int k = 0; k < 8; k++) {
            int newRow = row + dx[k];
            int newCol = col + dy[k];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 && board[newRow][newCol] == 0) {     // Check if adjacent square empty
                return true;
            }
        }
        return false;
    }
    private List<Move> getPossibleMoves_OP(){
        Othello clone = this.clone();
        clone.turn_p1 = !clone.turn_p1;
        return clone.getPossibleMoves(clone.turn_p1);
    }
    public int parityScore(boolean isPlayer1) {
        boolean[][] visited = new boolean[8][8];
        int oddRegions = 0, evenRegions = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0 && !visited[i][j]) {
                    int emptyCount = emptySpots(i, j, visited);
                    if (emptyCount % 2 == 0) {
                        evenRegions++;
                    } else {
                        oddRegions++;
                    }
                }
            }
        }

        return (isPlayer1 ? oddRegions - evenRegions : evenRegions - oddRegions);
    }
    private int emptySpots(int x, int y, boolean[][] visited) {
        int count = 0;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] {x, y});

        while (!stack.isEmpty()) {
            int[] square = stack.pop();
            int i = square[0], j = square[1];

            if (i < 0 || i >= 8 || j < 0 || j >= 8 || visited[i][j] || board[i][j] != 0) {
                continue;
            }

            visited[i][j] = true;
            count++;

            stack.push(new int[] {i - 1, j});
            stack.push(new int[] {i + 1, j});
            stack.push(new int[] {i, j - 1});
            stack.push(new int[] {i, j + 1});
        }

        return count;
    }


}

