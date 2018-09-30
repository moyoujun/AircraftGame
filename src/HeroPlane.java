import java.awt.image.BufferedImage;

public class HeroPlane extends FlyingObject{
    private BufferedImage[] images = {};
    private int index = 0 ;

    private int benefit;
    private int life;
    private int invincible;

    public HeroPlane() {
        this.life = 3;
        this.benefit = 0;
        this.images = new BufferedImage[] {GameRun.hero0, GameRun.hero1};
        this.image = images[0]; // 设置初始图片
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.position_x = 150;
        this.position_y = 550;
        this.invincible = 0;
    }

    public void setInvincible(int invincible) {
        this.invincible = invincible;
    }

    public int getInvincible() {
        return invincible;
    }

    public int getBenefit() {
        return benefit;
    }

    public void setBenefit(int benefit) {
        this.benefit = benefit;
    }

    public void addBenefit(int benefit) {
        this.benefit += benefit;
    }

    public void addLife(int i) {
        this.life += i;
    }

    public void substractLife() {
        this.life--;
    }

    public int getLife() {
        return life;
    }

    public void step() {
        image = images[index++/10%images.length]; //切换hero0 hero1;
        if (this.invincible>0) {
            this.invincible--;
        }
    }

    public void moveLeft() {
        System.out.println("Moving Left");
        this.position_x -=10;
    }

    public void moveRight() {
        System.out.println("Moving Right");
        this.position_x +=10;
    }

    public void moveUp() {
        System.out.println("Moving Forward");
        position_y -=10;
    }

    public void moveDown() {
        System.out.println("Moving BackUP");
        position_y +=10;
    }

    public Bullet[] shootBullet() {
        //每次在机翼上发射两枚子弹。
        Bullet[] bullets = new Bullet[2];
        bullets[0] = new Bullet(this.position_x - (this.width / 2), this.position_y, this.benefit);
        bullets[1] = new Bullet(this.position_x + (this.width / 2), this.position_y, this.benefit);
        return bullets;
    }
}
