package snake.app.body;

import java.awt.*;

public class SnakeBody {

    private boolean isHead;
    private Point position;
    private Point previousPosition;

    public SnakeBody(boolean isHead, Point position, Point previousPosition) {
        this.isHead = isHead;
        this.position = position;
        this.previousPosition = previousPosition;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Point previousPosition) {
        this.previousPosition = previousPosition;
    }
}
