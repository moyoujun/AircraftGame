/**
 * 子弹类，描述玩家发射出来的子弹。
*/
public class Bullet extends FlyingObject{
    private int hitpoint;
    private int speed;

    public Bullet(int position_x, int position_y, int benefit) {
        // 子弹的x坐标 y坐标 以及攻击增益。
        this.image = GameRun.bullet;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.position_x = position_x;
        this.position_y = position_y-height/2;
        this.hitpoint = 2 + benefit;
        this.speed = 3;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public void step() {
        this.position_y -= this.speed;
    }
}
