import javax.swing.*;
import java.awt.*;

public class GreedySnake extends JFrame {
    public GreedySnake() {
        // 初始化窗口设置
        this.setTitle("贪吃蛇");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        Snake snake = new Snake();
        SnakeConfig.screenHeight = this.getHeight();
        SnakeConfig.screenWidth = this.getWidth();
        this.setLayout(new GridLayout());
        this.add(snake);
        this.addKeyListener(snake);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new GreedySnake();
    }

}
