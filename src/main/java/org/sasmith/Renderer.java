package org.sasmith;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Iterator;

public class Renderer extends Application {
    public Logic logic;

    @Override
    public void start(Stage stage) throws Exception {
        Board board = new Board(16, 16);
        logic = new Logic(board);
        BorderPane window = createPanel(16, 16);
        Timeline update = new Timeline(new KeyFrame(Duration.millis(16), ev -> {
            update(((GridPane)window.getChildren().get(1)));
        }));
        update.setCycleCount(Animation.INDEFINITE);
        update.play();
        Scene scene = new Scene(window);
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();

    }


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

    private BorderPane createPanel(int x, int y){
        BorderPane panel = new BorderPane();
        panel.setTop(new MenuBar());
        panel.setCenter(createField(x, y));

        return panel;
    }

    private GridPane createField(int x, int y){
        GridPane gp = new GridPane();
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gp.add(createButton(j, i), i, j);
            }
        }
        gp.setOnMouseClicked((MouseEvent e) -> {
            if(!logic.getFailState()){update(gp)];
        });
        return gp;
    }

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