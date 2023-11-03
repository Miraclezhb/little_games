import javax.swing.*;
import java.awt.*;

public class TetrisGameStart extends JFrame{
    public TetrisGameStart() {
        this.setTitle("tetris");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout gridLayout = new GridLayout(3, 3, 5, 5);
        this.setLayout(gridLayout);
        startGUI(gridLayout);
    }

    public static void main(String[] args) {
        new TetrisGameStart();
    }

    private void startGUI(GridLayout gridLayout) {
        JLabel label = new JLabel("TETRIS",JLabel.CENTER);
        Font font = new Font("Times New Roman", Font.BOLD, 80);
        label.setForeground(Color.BLUE);

        JButton startGame = new JButton("start game");
        startGame.setFont(font);
        startGame.setSize(50,50);
        startGame.setHorizontalAlignment(SwingConstants.CENTER);

        JButton gameOver = new JButton("game over");
        gameOver.setFont(font);
        gameOver.setSize(50,50);
        gameOver.setHorizontalAlignment(SwingConstants.CENTER);

    }
}
