package org.sasmith;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Iterator;
import java.util.Optional;

public class Renderer extends Application {
    private Logic logic;
    private BorderPane window = createPanel(16, 16);

    /**
     * Standard JavaFX start method
     * @param stage - args
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        logic = new Logic(16, 16);

        Timeline update = new Timeline(new KeyFrame(Duration.millis(16), ev -> {
            update(((GridPane)window.getChildren().get(1)));
            stage.sizeToScene();
            stage.show();
        }));
        update.setCycleCount(Animation.INDEFINITE);
        update.play();
        Scene scene = new Scene(window);
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This is the update function for the Graphical Interface to Logic.
     * This is called 60 times a second, and adjusts what the Buttons display based on information passed from Logic
     * @param gp - The GridPane currently being operated on
     */
    public void update(GridPane gp){
        Iterator btnIter = gp.getChildren().iterator();
        while(btnIter.hasNext()){
            Button btn = ((Button)btnIter.next());
            String[] coord = btn.getId().split(",");
            int x = Integer.parseInt(coord[0]);
            int y = Integer.parseInt(coord[1]);

            if(logic.getBoard().getNode(x, y).isSeen()){
                btn.setText("" + logic.getBoard().getNode(x, y).getAdj());
            } else if(logic.getBoard().getNode(x, y).isFlagged()){
                btn.setText("*");
            } else {
                btn.setText("");
            }
        }
    }

    /**
     * Creates the inital BorderPane used for the program
     * @param x - Size of Minesweeper Grid Width
     * @param y - Size of Minesweeper Grid Height
     * @return BorderPane - BorderPane containing a GridPane and a MenuBar
     */
    private BorderPane createPanel(int x, int y){
        BorderPane panel = new BorderPane();
        panel.setTop(createMenu());
        panel.setCenter(createField(x, y));

        return panel;
    }

    /**
     * Creates the MenuBar used by the software
     * @return MenuBar - The MenuBar used by the software
     */
    private MenuBar createMenu(){
        MenuBar mb = new MenuBar();
        Menu m = new Menu("Game Options");

        MenuItem reset = new MenuItem("Reset");
        EventHandler<ActionEvent> resetEvent = e -> {
            System.out.println("handler");
            logic = new Logic(logic.getBoard().getX(), logic.getBoard().getY());
        };
        reset.setOnAction(resetEvent);

        MenuItem newGame = new MenuItem("New Game");
        EventHandler<ActionEvent> newGameEvent = e -> {
            int[] temp = newGamePopup();
            if(temp != null) {
                logic = new Logic(temp[0], temp[1]);
                window.getChildren().remove(1);
                window.setCenter(createField(temp[0], temp[1]));
            }
        };
        newGame.setOnAction(newGameEvent);

        m.getItems().addAll(reset, newGame);
        mb.getMenus().add(m);
        return mb;
    }

    /**
     * Creates a Dialog to allow the user to input new dimensions for their Minesweeper game.
     * @return int[] - [0] = The requested Width; [1] = The Requested Height
     */
    private int[] newGamePopup(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New Game");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        GridPane gp = new GridPane();

        TextField x = new TextField();
        x.setPromptText("X Coordinate");

        TextField y = new TextField();
        y.setPromptText("Y Coordinate");

        gp.add(x, 0, 0);
        gp.add(new Label("/"), 1, 0);
        gp.add(y, 2, 0);

        dialog.getDialogPane().setContent(gp);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                return new Pair<>(y.getText(), x.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        int[] res = new int[2];
        result.ifPresent(pair -> {
            res[0] = Integer.parseInt(pair.getKey());
            res[1] = Integer.parseInt(pair.getValue());
        });

        return res;
    }

    /**
     * Creates a GridPane full of the required number of Buttons for the Minesweeper Game
     * @param x - The Width of the Minesweeper Board
     * @param y - The Height of the Minesweeper Board
     * @return GridPane - The GridPane display of the Board
     */
    private GridPane createField(int x, int y){
        GridPane gp = new GridPane();
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gp.add(createButton(j, i), i, j);
            }
        }
        return gp;
    }

    /**
     * Creates Buttons that can be used to update the logic of the board
     * Stores their coordinates as a comma delimited string (x,y);
     * @param x - The X coordinate of the Button
     * @param y - The Y coordinate of the Button
     * @return Button - The Button with update functions set
     */
    private Button createButton(int x, int y){
        Button btn = new Button();
        btn.setPrefSize(30, 30);
        btn.setId(x + "," + y);
        btn.setOnMouseClicked((MouseEvent e) -> {
            if(e.getButton() == MouseButton.PRIMARY){
                logic.update(x, y, 0);
            } else {
                logic.update(x, y, 1);
            }
        });
        return btn;
    }

    private static void Main(String[] args){
        launch(args);
    }

}