import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fly");
        GameRun gameRun = new GameRun();
        frame.add(gameRun); // 将面板添加到JFrame中
        frame.setSize(GameRun.panelWidth, GameRun.panelHeight); // 设置大小
        frame.setAlwaysOnTop(true); // 设置其总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
        //frame.setIconImage(new ImageIcon("source/icon.jpg").getImage()); // 设置窗体的图标
        frame.setLocationRelativeTo(null); // 设置窗体初始位置
        frame.setVisible(true); // 尽快调用paint
        GameControl gameControl = new GameControl(gameRun);
        frame.addKeyListener(gameControl);
        gameRun.runGame();
    }

}
