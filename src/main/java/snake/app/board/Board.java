package snake.app.board;
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
import snake.app.body.Apple;
import snake.app.body.SnakeBody;
import snake.app.directions.Directions;
import snake.app.point.Point;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * @author Alisher Urunov
 */

public class Board extends JPanel {
    private int TICK = 75; // Determines how fast games goes
    public boolean gameOver = true; // Stop / start gameplay boolean
    private Dimension blockSize = new Dimension(); // For storing block sizes (screen size / factor)
    private Thread boardThread; // Game loop
    List<SnakeBody> snake = new ArrayList<>(); // Snake
    List<Apple> foods = new ArrayList<>(); // Food on board
    public Directions pressed = Directions.RIGHT; // Initial Snake direction
    private JLabel score; // Score label



    public void init() {
        // Listen on key presses
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println(e);
                if (e.getKeyCode() == KeyEvent.VK_UP && pressed != Directions.DOWN) {
                    pressed = Directions.TOP;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && pressed != Directions.TOP) {
                    pressed = Directions.DOWN;

                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT && pressed != Directions.RIGHT) {
                    pressed = Directions.LEFT;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && pressed != Directions.LEFT) {
                    pressed = Directions.RIGHT;
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();

        // Generate snake
        snake.clear();
        foods.clear();
        snake.add(new SnakeBody(true, new Point(2, 0), new Point(2, 0)));
        snake.add(new SnakeBody(false, new Point(1, 0), new Point(1, 0)));
        snake.add(new SnakeBody(false, new Point(0, 0), new Point(0, 0)));
        updateBlockSize();
        pressed = Directions.RIGHT;


        // LOOP THREAD

        boardThread = new Thread(() -> {
            while (!gameOver) {
                try {
                    Thread.sleep(TICK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateBlockSize();
                for (int i = 0; i < snake.size(); i++) {
                    Point current = snake.get(i).getPosition();
                    //System.out.println("______________"+snake.size());
                    //System.out.println(current);
                    snake.get(i).setPreviousPosition(new Point(current.x, current.y));
                    if (i == 0) {
                        //System.out.println("Head "+i);
                        switch (pressed) {
                            case TOP:
                                snake.get(i).setPosition(new Point(current.x, current.y - 1));
                                break;
                            case DOWN:

                                snake.get(i).setPosition(new Point(current.x, current.y + 1));

                                break;
                            case LEFT:

                                snake.get(i).setPosition(new Point(current.x - 1, current.y));

                                break;
                            case RIGHT:

                                snake.get(i).setPosition(new Point(current.x + 1, current.y));

                                break;
                        }

                    } else {
                        //System.out.println("Tail "+i);
                        Point previousPosition = snake.get(i - 1).getPreviousPosition();
                        //System.out.println(previousPosition);
                        snake.get(i).setPosition(new Point(previousPosition.x, previousPosition.y));
                        // System.out.println("______________");
                    }

                }

                // Check if player lost or ate food then repaint
                checkForGameOver();
                checkForFoodEaten();
                repaint();
            }
        });


        // Generate one food at start
        foods.add(generateOneFood());

    }

    /**
     * Loops over snake and foods arrays. If one of positions match
     * adds snake body part and generates food
     */
    public void checkForFoodEaten() {
        Iterator<SnakeBody> snakeIterator = snake.iterator();
        Iterator<Apple> foodIterator = foods.iterator();
        List<SnakeBody> tempSnake = new ArrayList<>();
        List<Apple> tempFood = new ArrayList<>();
        while (foodIterator.hasNext()) {
            Apple currentApple = foodIterator.next();
            while (snakeIterator.hasNext()) {
                SnakeBody currentBody = snakeIterator.next();
                if (currentApple.getPosition().equals(currentBody.getPosition())) {
                    Point position = snake.get(snake.size() - 1).getPreviousPosition();
                    tempSnake.add(new SnakeBody(false, position, position));
                    foodIterator.remove();
                    tempFood.add(generateOneFood());
                }
            }
        }
        snake.addAll(tempSnake);
        foods.addAll(tempFood);
            score.setText(String.valueOf(Integer.parseInt(score.getText()) + tempSnake.size()));
    }

    /**
     * Generates one food on board
     * @return Apple with unique position to snake bodies
     */
    public Apple generateOneFood() {
        Apple apple;
        apple = new Apple(new Point((int) (Math.random() * blockSize.width), (int) (Math.random() * blockSize.height)));
        for (SnakeBody body : snake) {
            while (body.getPosition().equals(apple.getPosition())) {
                apple = new Apple(new Point((int) (Math.random() * blockSize.width), (int) (Math.random() * blockSize.height)));
            }
        }

        return apple;
    }


    /**
     * Loops over snake bodies and checks if head is intact with counter
     * parts or walls
     * Changes gameOver boolean
     */
    public void checkForGameOver() {

        Point headPosition = snake.get(0).getPosition();

        if (headPosition.x < 0 || headPosition.y < 0 || headPosition.x > getWidth() / blockSize.width || headPosition.y > getHeight() / blockSize.height) {
            gameOver = true;
        }
        for (SnakeBody body : snake) {
            if (body.getPosition().equals(headPosition) & !body.isHead()) {
                gameOver = true;
                break;
            }
        }

    }

    /**
     * Calculates desired block sizes
     * depending on BLOCK_SIZE_FACTOR
     */
    public void updateBlockSize() {
        int ratio = getWidth() >= getHeight() ? getWidth() / getHeight() : getHeight() / getWidth();
        int BLOCK_SIZE_FACTOR = 25;
        blockSize.width = getWidth() / BLOCK_SIZE_FACTOR / ratio;
        blockSize.height = getHeight() / BLOCK_SIZE_FACTOR;
//        System.out.println(blockSize.width);
//        System.out.println(blockSize.height);
    }

    /**
     * Starts or stops game loop depending on gameOver boolean
     */
    public void startTicking() {
        if (!gameOver) {
            boardThread.start();
        } else {
            try {
                boardThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        for (SnakeBody body : snake) {
            g.fillRect(blockSize.width * body.getPosition().x, blockSize.height * body.getPosition().y, blockSize.width - 1, blockSize.height - 1);
        }
        g.setColor(Color.GREEN);
        for (Apple food : foods) {
            g.fillRect(blockSize.width * food.getPosition().x, blockSize.height * food.getPosition().y, blockSize.width - 1, blockSize.height - 1);
        }
    }

    public List<SnakeBody> getSnake() {
        return snake;
    }

    public void setScore(JLabel score) {
        this.score = score;
    }
}