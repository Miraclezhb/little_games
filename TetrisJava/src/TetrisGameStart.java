import listener.GameAboutListener;
import listener.GameOverListener;
import listener.GameStartListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class TetrisGameStart extends JFrame {
    public TetrisGameStart() {
        this.setTitle("tetris");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startGUI();
    }

    public static void main(String[] args) {
        new TetrisGameStart();
    }

    private void startGUI() {
        Font font = new Font("黑体", Font.PLAIN, 60);
        // 创建纵向 Box 容器
        Box box = Box.createVerticalBox();
        // 标签
        JLabel label = createComponent(JLabel.class, "俄罗斯方块", font, null);

        font = new Font("宋体", Font.PLAIN, 30);
        // 按钮
        JButton gameStart = createComponent(JButton.class, "开始游戏", font, new GameStartListener());
        JButton gameAbout = createComponent(JButton.class, "游戏说明", font, new GameAboutListener());
        JButton gameOver = createComponent(JButton.class, "结束游戏", font, new GameOverListener());

        box.add(Box.createRigidArea(new Dimension(100, 200)));
        box.add(label);
        // 分隔两个纵向组件
        box.add(Box.createVerticalStrut(10));
        box.add(gameStart);
        box.add(Box.createVerticalStrut(10));
        box.add(gameAbout);
        box.add(Box.createVerticalStrut(10));
        box.add(gameOver);

        this.add(box, BorderLayout.CENTER);
    }

    /**
     * 创建组件，辅助方法，使用了 java 反射机制
     * @param clazz 组件 Class
     * @param text 组件名称或显示文字
     * @param font 字体格式
     * @param listener 监听器，可为空
     * @return 返回组件实例
     */
    private <T> T createComponent(Class<T> clazz, String text, Font font, ActionListener listener) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("setText", String.class).invoke(t, text);
            clazz.getMethod("setFont", Font.class).invoke(t, font);
            clazz.getMethod("setForeground", Color.class).invoke(t, Color.DARK_GRAY);
            // 这个方法可以设置组件在 Box 容器中水平居中
            clazz.getMethod("setAlignmentX", Float.TYPE).invoke(t, Component.CENTER_ALIGNMENT);
            if (listener != null) {
                // 添加按钮监听事件
                clazz.getMethod("addActionListener", ActionListener.class).invoke(t, listener);
            }
            return t;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
