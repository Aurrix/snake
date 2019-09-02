package snake.app.body;

import java.awt.*;

public class Apple {

    public Apple(Point position) {
        this.position = position;
    }

    public Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
