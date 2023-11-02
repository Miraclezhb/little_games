import java.awt.*;
import java.util.Random;

public class BeanNode extends SnakeNode {
    private final Random random = new Random();

    public BeanNode() {
        super(SnakeNodeType.BEAN, new PositionXY(200, 200));
    }

    public void paint(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(super.getX(), super.getY(), SnakeConfig.SNAKE_NODE_WIDTH, SnakeConfig.SNAKE_NODE_HEIGHT);
    }

    public void updateXY() {
        this.position.x = this.random.nextInt(SnakeConfig.screenWidth - 80);
        this.position.y = this.random.nextInt(SnakeConfig.screenHeight - 80);
    }
}
