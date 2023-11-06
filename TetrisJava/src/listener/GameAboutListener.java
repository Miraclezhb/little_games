package listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAboutListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(250, 100);
        jDialog.setTitle("游戏说明");
        Box box = Box.createVerticalBox();

        JLabel about1 = new JLabel("方向左右键调整左右位置");
        JLabel about2 = new JLabel("方向下键加速下落");
        JLabel about3 = new JLabel("空格键控制旋转");

        about1.setAlignmentX(Component.CENTER_ALIGNMENT);
        about2.setAlignmentX(Component.CENTER_ALIGNMENT);
        about3.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(about1);
        box.add(Box.createVerticalStrut(5));
        box.add(about2);
        box.add(Box.createVerticalStrut(5));
        box.add(about3);

        jDialog.add(box, BorderLayout.CENTER);
        // 设置新窗体弹出后不可以操作原窗体
        jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
    }
}
