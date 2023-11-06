package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // 退出游戏
        System.exit(1);
    }
}
