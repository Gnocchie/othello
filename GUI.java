package de.lmu.bio.ifi;

import de.lmu.bio.ifi.basicpackage.OT_Button;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import szte.mi.Move;
import javafx.scene.paint.Color;
import szte.mi.Player;

import java.awt.*;
import java.util.Random;

public class GUI extends Application implements EventHandler<MouseEvent> {

    OT_Button[][] grid = new OT_Button[8][8];
    Othello game = new Othello();
    Bot bot;
    Label head = new Label("Let it rip");
    Button reset = new Button("RESET");
    Button buttonAi = new Button("Enable Bot");
    public static void main(String[] args){launch(args);}

    @Override
    public void start(Stage window) throws Exception {
        BorderPane mainLayout = new BorderPane();
        GridPane layout = new GridPane();

        clearGrid(layout);      // Create playable squares

        EventHandler<MouseEvent> onClick = mouseEvent -> {      // Reset Button
            game = new Othello();
            clearGrid(layout);      // update grid to match new board
            if (bot != null) bot = new Bot(game, Setting.MINMAX);
            head.setText("!!! ANOTHER ONE !!!");
        };

        reset.addEventHandler(MouseEvent.MOUSE_CLICKED, onClick);

        EventHandler<MouseEvent> onClick2 = mouseEvent -> {      // AI Button
            if (bot == null) {
                bot = new Bot(game, Setting.MINMAX);
                buttonAi.setText("Disable AI");
            }
            else {
                bot = null;
                buttonAi.setText("Enable AI");
            }

        };
        buttonAi.addEventHandler(MouseEvent.MOUSE_CLICKED, onClick2); // AI BUTTON

        layout.setGridLinesVisible(true);

        mainLayout.setTop(this.head);
        mainLayout.setBottom(this.reset);
        mainLayout.setRight(this.buttonAi);
        mainLayout.setCenter(layout);

        window.setScene(new Scene(mainLayout,1000,1000));
        window.show();
    }


    @Override
    public void handle(MouseEvent e) {
        if(e.getSource() instanceof OT_Button && game.gameStatus() == GameStatus.RUNNING){
            OT_Button src = (OT_Button)e.getSource();
            System.out.println("Button Coordinates: " + src.x + "/" + src.y);
            if(src.value == 0 && game.makeMove(game.turn_p1, src.x, src.y)){      // Player Move
                src.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent m) -> {});
                updateGrid();
                updateStatus();

                if(bot != null && game.status == GameStatus.RUNNING) {        // Bot Move
                    Move coord = bot.nextMove(new Move(src.x, src.y), 1, 9000);

                    game.makeMove(bot.p1, coord.x, coord.y);
                    updateGrid();
                    updateStatus();
                }

            } else System.out.println("Invalid Move");      // Prompt for moves made on an invalid square

        }
    }

    private void clearGrid(GridPane layout){
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                OT_Button space = new OT_Button(game.getBoard()[i][j], j, i);
                space.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
                space.setPrefSize(100,100);
                layout.add(space, j, i);
                grid[i][j] = space;
            }
        }
    }
    private void updateGrid() {
        for (OT_Button[] row : grid){
            for (OT_Button square : row){
                square.setValue(game.getBoard()[square.y][square.x]);
            }
        }
    }

    public void updateStatus(){
        GameStatus status = game.status;
        if(status == GameStatus.PLAYER_1_WON) head.setText("Player 1 dunked on em");
        if (status == GameStatus.PLAYER_2_WON) head.setText("Player 2 demolishes");
        if (status == GameStatus.DRAW) head.setText("Friendship won, not you though");
        if (status == GameStatus.RUNNING){
            if(game.turn_p1) head.setText("Player 1's turn");
            else head.setText("Player 2's turn");
        }
    }
}
