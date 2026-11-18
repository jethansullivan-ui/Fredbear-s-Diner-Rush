package com.fredbeardinerrush.controller;

import com.fredbeardinerrush.model.Direction;
import com.fredbeardinerrush.model.Position;
import com.fredbeardinerrush.service.CustomLayoutLoader;
import com.fredbeardinerrush.service.FileTallyPersistence;
import com.fredbeardinerrush.service.GameEngine;
import com.fredbeardinerrush.service.IMazeLoader;
import com.fredbeardinerrush.service.ITallyPersistence;
import com.fredbeardinerrush.service.SoundEffectPlayer;
import com.fredbeardinerrush.view.SplitScreenMazePane;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        String[] mapLayout = {
                "@@@@@@@@@@@@@@@@@@@@@",
                "@C........@....... C@",
                "@.@ @@@@@.@.@@@@@.@@@",
                "@.@.....@.@.....@...@",
                "@.@.@@@.@.@@@.@@@@@.@",
                "@.....@...........@.@",
                "@@@.@.@.@@@@@.@@@.@.@",
                "@...@.@.     .@ @.@.@",
                "@.@@@.@.@@ @@.@@@.@.@",
                "@C......@ S @......C@",
                "@@@@@@@@@@@@@@@@@@@@@",
                "#####################",
                "#C......# F #......C#",
                "#.#.###.## ##.#.###.#",
                "#.#.# #.     .#.#...#",
                "#.#.###.#####.#.#.###",
                "#.#...........#.....#",
                "#.#####.###.#.###.#.#",
                "#...#.....#.#.....#.#",
                "###.#####.#.##### #.#",
                "#C .......#........C#",
                "#####################"
        };

        IMazeLoader loader = new CustomLayoutLoader(mapLayout, 'F');
        ITallyPersistence persistence = new FileTallyPersistence("tally_data.txt");
        GameEngine engine = new GameEngine(21, 22, loader, persistence);
        SoundEffectPlayer soundPlayer = new SoundEffectPlayer();

        Position p1Spawn = loader.getSpawnPosition();
        Position p2Spawn = findSpawnPosition(mapLayout, 'S');

        engine.getPlayer1().setPosition(p1Spawn);
        engine.getPlayer2().setPosition(p2Spawn);

        engine.getPlayer1().setDirection(Direction.DOWN);
        engine.getPlayer2().setDirection(Direction.UP);

        int tileSize = 30;
        SplitScreenMazePane pane = new SplitScreenMazePane(engine.getMaze(), engine.getPlayer1(), engine.getPlayer2(),
                tileSize);

        Text statusText = new Text("Moving Sound Mode | Fredbear: WASD | Springbonnie: Arrows");
        statusText.setFont(Font.font(14));
        statusText.setFill(Color.WHITE);

        Text tallyText = new Text(
                "Tally -> Fredbear Wins: " + engine.getP1Wins() + " | Springbonnie Wins: " + engine.getP2Wins());
        tallyText.setFont(Font.font(14));
        tallyText.setFill(Color.YELLOW);

        Text replayHint = new Text("Press 'R' to Replay / Restart Round");
        replayHint.setFont(Font.font(12));
        replayHint.setFill(Color.LIGHTGRAY);

        VBox root = new VBox(8, statusText, tallyText, pane, replayHint);
        root.setStyle("-fx-background-color: #111; -fx-padding: 15;");

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if (code == KeyCode.R && engine.isGameOver()) {
                engine.resetMatch(p1Spawn, p2Spawn);
                engine.getPlayer1().setDirection(Direction.DOWN);
                engine.getPlayer2().setDirection(Direction.UP);
                statusText.setText("New Round Started! Let's Rush!");
                statusText.setFill(Color.WHITE);
                tallyText.setText("Tally -> Fredbear Wins: " + engine.getP1Wins() + " | Springbonnie Wins: "
                        + engine.getP2Wins());
            }

            // Fredbear controls
            if (code == KeyCode.W)
                engine.getPlayer1().setDirection(Direction.UP);
            if (code == KeyCode.S)
                engine.getPlayer1().setDirection(Direction.DOWN);
            if (code == KeyCode.A)
                engine.getPlayer1().setDirection(Direction.LEFT);
            if (code == KeyCode.D)
                engine.getPlayer1().setDirection(Direction.RIGHT);

            // Springbonnie controls
            if (code == KeyCode.UP)
                engine.getPlayer2().setDirection(Direction.UP);
            if (code == KeyCode.DOWN)
                engine.getPlayer2().setDirection(Direction.DOWN);
            if (code == KeyCode.LEFT)
                engine.getPlayer2().setDirection(Direction.LEFT);
            if (code == KeyCode.RIGHT)
                engine.getPlayer2().setDirection(Direction.RIGHT);
        });

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 150_000_000) {
                    // Only update and play sounds if the game is still active
                    if (!engine.isGameOver()) {
                        engine.updateParallel();

                        if (engine.didPlayer1Move()) {
                            soundPlayer.playFredbearMunch();
                        }
                        if (engine.didPlayer2Move()) {
                            soundPlayer.playSpringbonnieMunch();
                        }
                    }

                    pane.render();

                    if (engine.isGameOver()) {
                        statusText.setText(engine.getWinnerText() + " (Press 'R' to Replay)");
                        statusText.setFill(Color.RED);
                        tallyText.setText("Tally -> Fredbear Wins: " + engine.getP1Wins() + " | Springbonnie Wins: "
                                + engine.getP2Wins());
                    }
                    lastUpdate = now;
                }
            }
        };

        primaryStage.setTitle("Fredbear's Diner Rush");
        primaryStage.setScene(scene);
        primaryStage.show();

        timer.start();
    }

    private Position findSpawnPosition(String[] layout, char target) {
        for (int y = 0; y < layout.length; y++) {
            String row = layout[y];
            for (int x = 0; x < row.length(); x++) {
                if (row.charAt(x) == target) {
                    return new Position(x, y);
                }
            }
        }
        return new Position(1, 1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}