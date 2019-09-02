package snake.app.board;

import snake.app.body.Apple;
import snake.app.body.SnakeBody;
import snake.app.directions.Directions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Board extends JPanel {
    public int TICK = 150;
    public int BLOCK_SIZE_FACTOR = 25;
    public boolean gameOver = true;
    public Dimension blockSize = new Dimension();
    private Thread boardThread;
    List<SnakeBody> snake = new ArrayList<>();
    List<Apple> foods = new ArrayList<>();
    public Directions pressed = Directions.RIGHT;
    JLabel score;



    public Board() {
    }

    public void init() {
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
                    boardThread.sleep(TICK);
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
                checkForGameOver();
                checkForFoodEaten();
                repaint();
            }
        });
        
        generateOneFood();
        
    }

    private void checkForFoodEaten() {
        Iterator<SnakeBody> snakeIterator = snake.iterator();
        Iterator<Apple> foodIterator = foods.iterator();
        List<SnakeBody> tempSnake = new ArrayList<>();
        List<Apple> tempFood = new ArrayList<>();
        while(foodIterator.hasNext()){
            Apple currentApple = foodIterator.next();
            while(snakeIterator.hasNext()){
                SnakeBody currentBody = snakeIterator.next();
                if (currentApple.getPosition().equals(currentBody.getPosition())){
                    Point position = snake.get(snake.size()-1).getPreviousPosition();
                    tempSnake.add(new SnakeBody(false,position,position));
                    foodIterator.remove();
                    tempFood.add(new Apple(new Point((int)(Math.random()*blockSize.width),(int)(Math.random()*blockSize.height))));
                }
            }
        }
        snake.addAll(tempSnake);
        foods.addAll(tempFood);
        score.setText(String.valueOf(Integer.valueOf(score.getText())+tempSnake.size()));
    }

    private void generateOneFood() {
        foods.add(new Apple(new Point((int)(Math.random()*blockSize.width),(int)(Math.random()*blockSize.height))));
    }

    private void checkForGameOver() {

        Point headPosition = snake.get(0).position;

        if (headPosition.x < 0 || headPosition.y < 0 || headPosition.x > blockSize.width || headPosition.y > blockSize.height) {
            gameOver = true;
        }
        for (SnakeBody body : snake) {
            if (body.getPosition().equals(headPosition) & !body.isHead) {
                gameOver = true;
            }
        }

    }

    private void updateBlockSize() {
        int ratio = getWidth() / getHeight();
        blockSize.width = getWidth() / BLOCK_SIZE_FACTOR / ratio;
        blockSize.height = getHeight() / BLOCK_SIZE_FACTOR;
//        System.out.println(blockSize.width);
//        System.out.println(blockSize.height);
    }

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

    public JLabel getScore() {
        return score;
    }

    public void setScore(JLabel score) {
        this.score = score;
    }
}
