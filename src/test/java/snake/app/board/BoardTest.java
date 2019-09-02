package snake.app.board;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import snake.app.board.Board;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BoardTest {

    Board board;

    @Before
    public void setUp(){
        board = new Board();
    }

    @Test
    public void directionChangeTest() {
        board.init();
        KeyEvent key = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_DOWN,'Z');
        board.getKeyListeners()[0].keyPressed(key);
        Assert.assertTrue(board.getSnake().get(0).equals(new Point(2,1)));
    }

    @Test
    public void checkForFoodEatenTest(){

    }

    @Test
    public void generateOneFoodTest(){

    }

    @Test
    public void checkForGameOverTest(){

    }



}
