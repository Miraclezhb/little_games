import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake extends JPanel implements KeyListener {

    final SnakePosition snakePosition = new SnakePosition();

    private boolean isNotPressed = true;

    final BeanNode beanNode;

    public Snake() {
        for (int i = 0; i < SnakeConfig.DEFAULT_LENGTH; i++) {
            snakePosition.addNode();
        }
        beanNode = new BeanNode();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.snakePosition.paintSnake(g);
        this.beanNode.paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 控制贪吃蛇移动
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        directionController(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * 方向操作器
     */
    private void directionController(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP -> switchDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> switchDirection(Direction.DOWN);
            case KeyEvent.VK_RIGHT -> switchDirection(Direction.RIGHT);
            case KeyEvent.VK_LEFT -> switchDirection(Direction.LEFT);
        }
    }

    private void switchDirection(Direction direction) {
        switch (direction) {
            case LEFT -> this.snakePosition.snakeNodeRedirect(Direction.LEFT);
            case RIGHT -> this.snakePosition.snakeNodeRedirect(Direction.RIGHT);
            case UP -> this.snakePosition.snakeNodeRedirect(Direction.UP);
            case DOWN -> this.snakePosition.snakeNodeRedirect(Direction.DOWN);
        }
        if (this.isNotPressed) {
            // 初始化方向控制线程
            Thread snakeDirectionControl = new Thread(new SnakeRedirect(this));
            snakeDirectionControl.start();
            this.isNotPressed = false;
        }
    }
}

/**
 * 控制贪吃蛇运动
 */
class SnakeRedirect implements Runnable {
    private final Snake snake;

    public SnakeRedirect(Snake snake) {
        this.snake = snake;
    }

    @Override
    @SuppressWarnings("all")
    public void run() {
        while (true) {
            try {
                Thread.sleep(SnakeConfig.FPS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (this.snake.snakePosition.handleSnakeNodes(this.snake.beanNode)) {
                break;
            }
            this.snake.repaint();
        }
    }
}