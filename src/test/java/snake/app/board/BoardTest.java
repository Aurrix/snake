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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import snake.app.board.Board;
import snake.app.body.Apple;
import snake.app.body.SnakeBody;
import snake.app.directions.Directions;
import snake.app.point.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Alisher Urunov
 */
public class BoardTest {

    Board board;

    @Before
    public void setUp(){
        board = new Board();
    }


    @Test
    public void checkForFoodEatenTest(){
        Point snakeBodyPoint = new Point (1,1);
        board.snake.add(new SnakeBody(false, snakeBodyPoint,snakeBodyPoint ));
        board.foods.add(new Apple(snakeBodyPoint));
        board.setScore(new JLabel("0"));
        board.checkForFoodEaten();

        Assert.assertEquals(2,board.snake.size());
    }

    @Test
    public void checkForFoodEatenTailGenerationTest(){
        Point snakeBodyPoint = new Point (1,1);
        Point snakeBodyPrevious = new Point (5,5);
        board.snake.add(new SnakeBody(false, snakeBodyPoint,snakeBodyPrevious ));
        board.foods.add(new Apple(snakeBodyPoint));
        board.setScore(new JLabel("0"));
        board.checkForFoodEaten();

        Assert.assertEquals(board.snake.get(1).getPosition(),snakeBodyPrevious);

    }

    @Test
    public void generateOneFoodTest(){
        board.setSize(500,500);
        board.updateBlockSize();
        for(int i = 0; i < 5;i++){
            for(int j = 0;j<5;j++){
                snake.app.point.Point genPoint = new snake.app.point.Point(i,j);
                board.snake.add(new SnakeBody(false, genPoint,genPoint));
            }
        }

        for(int i = 0; i <20;i++){
            Assert.assertTrue(!board.snake.contains(board.generateOneFood()));
        }

    }

    @Test
    public void checkForGameOverTest(){
        board.gameOver = false;
        board.setSize(500,500);
        board.updateBlockSize();
        Point headPoint = new Point(0,0);
        board.snake.add(new SnakeBody(true,headPoint,headPoint));
        board.snake.add(new SnakeBody(false,headPoint,headPoint));
        board.checkForGameOver();

        Assert.assertTrue(board.gameOver);
    }



}
