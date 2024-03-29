package snake.app;
/**
 * Copyright 2002-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import snake.app.board.Board;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alisher Urunov
 */
public class SnakeApp {
    private static JFrame jFrame;
    private static JLabel scoreLabel;
    private static JLabel scoreCount;
    private static Board board;
    private static JButton startBtn;
    private static Graphics graphics;

    public static void main(String[] args) {
        initFrame();
        // Get graphics from board
        graphics = board.getGraphics();
        // Pass score label to game logic to track score
        board.setScore(scoreCount);
        // Start or stop game when button pressed
        startBtn.addActionListener(a -> {
            if (startBtn.getText().equals("Start")) {
                board.init();
                startBtn.setText("Stop");
                board.gameOver = false;
                board.startTicking();
            } else {
                startBtn.setText("Start");
                board.gameOver = true;
                board.startTicking();
                scoreCount.setText("0");
            }
        });
    }

    /**
     * Generates App GUI
     */
    private static void initFrame() {

        jFrame = new JFrame("Snake");
        jFrame.setSize(500, 500);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridBagLayout gridLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        jFrame.setLayout(gridLayout);

        board = new Board();
        board.setBackground(Color.white);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.95;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        jFrame.add(board, gbc);

        scoreLabel = new JLabel("Score: ");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weighty = 0.01;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        jFrame.add(scoreLabel, gbc);

        scoreCount = new JLabel("0");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.weightx = 0.9;
        gbc.weighty = 0.01;

        jFrame.add(scoreCount, gbc);

        startBtn = new JButton("Start");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = 0.01;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        jFrame.add(startBtn, gbc);


        jFrame.setVisible(true);

    }

}
