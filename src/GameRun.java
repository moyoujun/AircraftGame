
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameRun extends JPanel {

    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage enemy;
    public static BufferedImage bullet;
    public static BufferedImage background;
    public static BufferedImage pause;
    public static BufferedImage menu;
    public static BufferedImage gameover;

    public static int panelWidth ;
    public static int panelHeight ;

    private HeroPlane heroPlane = new HeroPlane();
    private LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<EnemyPlane> enemyPlanes = new LinkedList<>();

    private int enterEnemyIndex = 0;
    private int shootBulletIndex = 0;
    private int score = 0;
    private Random random = new Random();
    public enum STATE {
        START, PAUSE, GAME_OVER, RUNNING
    }

    private STATE state = STATE.START;
    // 主流程控制
    Timer timer = new Timer();

    static {
        try {
            //必须处理异常。
            background = ImageIO.read(new File("resource/background.png"));
            hero0 = ImageIO.read(new File("resource/hero0.png"));
            hero1 = ImageIO.read(new File("resource/hero1.png"));
            bullet = ImageIO.read(new File("resource/bullet.png"));
            enemy = ImageIO.read(new File("resource/enemy.png"));
            pause = ImageIO.read(new File("resource/pause.png"));
            menu = ImageIO.read(new File("resource/menu.png"));
            gameover = ImageIO.read(new File("resource/gameover.png"));
            panelWidth = background.getWidth();
            panelHeight = background.getHeight();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HeroPlane getHeroPlane() {
        return heroPlane;
    }

    public void pauseGame() {
        switch (state) {
            case RUNNING:{
                state = STATE.PAUSE;
                break;
            }
            case PAUSE:{
                state = STATE.RUNNING;
                break;
            }
            case START: {
                state = STATE.RUNNING;
                break;
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        paintHero(g);
        paintEnemy(g);
        paintBullet(g);
        paintScore(g);
        paintState(g);
    }

    public void paintHero(Graphics g) {
        int x = heroPlane.getPosition_x() - heroPlane.getWidth()/2;
        int y = heroPlane.getPosition_y() - heroPlane.getHeight()/2;
        g.drawImage(heroPlane.getImage(), x, y, null);
    }

    public void paintEnemy(Graphics g) {
        for (EnemyPlane enemy:enemyPlanes) {
            int x = enemy.getPosition_x() - enemy.getWidth()/2;
            int y = enemy.getPosition_y() - enemy.getHeight()/2;
            g.drawImage(enemy.getImage(), x, y, null);
        }
    }

    public void paintBullet(Graphics g) {
        for (Bullet b: bullets) {
            int x = b.getPosition_x()- b.getWidth()/2;
            int y = b.getPosition_y()- b.getHeight()/2;
            g.drawImage(b.getImage(), x, y, null);
        }
    }

    public void paintScore(Graphics g) {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14); // 字体
        g.setColor(new Color(0x3A3B3B));
        g.setFont(font); // 设置字体
        g.drawString("SCORE:" + score, 10, 20); // 画分数
        g.drawString("LIFE:" + heroPlane.getLife(), 10, 40); // 画命
    }

    public void paintState(Graphics g) {
        switch (state) {
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case START:
                g.drawImage(menu, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameover, 0, 0, null);
                break;
        }
    }

    public void createNewEnemy() {
        enterEnemyIndex++;
        if (enterEnemyIndex%40 == 0) {
            EnemyPlane enemyPlane = new EnemyPlane();
            int x = random.nextInt(300);
            enemyPlane.setPosition_x(x);
            enemyPlane.setPosition_y(20);
            enemyPlanes.add(enemyPlane);
        }
    }

    public void shootBullet() {
        shootBulletIndex++;
        if (shootBulletIndex % 30 == 0) {
            Bullet[] bs = heroPlane.shootBullet();
            for (Bullet b:bs) {
                bullets.add(b);
            }
        }
    }

    public void deleteDeadFlyingObj() {
        //子弹飞出画面，判定死亡，从列表中删除
        LinkedList<Bullet> tempBullets = new LinkedList<>();
        for(Bullet bullet:bullets) {
            if (bullet.outOfBound()) {
                tempBullets.add(bullet);
            }
        }
        for (Bullet bullet:tempBullets) {
            bullets.remove(bullet);
        }

        //敌机飞出画面或者生命值为0，判定死亡，从列表中删除
        LinkedList<EnemyPlane> tempEnemyPlaneList = new LinkedList<>();
        for(EnemyPlane enemyPlane:enemyPlanes) {
            if (enemyPlane.outOfBound()) {
                this.score = enemyPlane.getPoint()/2 + this.score;
                tempEnemyPlaneList.add(enemyPlane);
            }
            else if (enemyPlane.getHealth()==0) {
                this.score = enemyPlane.getPoint() + this.score;
                tempEnemyPlaneList.add(enemyPlane);
            }
        }
        for(EnemyPlane enemyPlane:tempEnemyPlaneList) {
            enemyPlanes.remove(enemyPlane);
        }

    }

    public void checkGameOver () {
        for (EnemyPlane e:enemyPlanes) {
            if (heroPlane.collision(e)) {
                e.setHealth(0);
                heroPlane.substractLife();
                heroPlane.setBenefit(0);
                heroPlane.setInvincible(20);
                break;
            }
        }
        if (heroPlane.getLife()==0) {
            state = STATE.GAME_OVER;
        }
    }

    public void shooting() {
        for(EnemyPlane e:enemyPlanes) {
            for (Bullet b:bullets) {
                e.shootBy(b);
            }
        }
    }

    public void stepAction() {
        heroPlane.step();
        for (EnemyPlane e:enemyPlanes) {
            e.step();
        }
        for (Bullet b:bullets) {
            b.step();
        }
    }

    public void runGame () {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == STATE.RUNNING) { // 运行状态
                    createNewEnemy();
                    shootBullet();
                    stepAction(); // 走一步
                    shooting(); // 子弹打飞行物
                    deleteDeadFlyingObj(); // 删除越界飞行物及子弹
                    checkGameOver(); // 检查游戏结束
                }
                repaint(); // 重绘，调用paint()方法
            }

        }, 10, 10);
    }
}
