public class EnemyPlane extends FlyingObject {
    private int speed;
    private int point;
    private int health;

    public EnemyPlane() {
        this.image = GameRun.enemy;
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.speed = 3;
        this.health = 6;
        this.point = 5; //击中敌人获得的分数。
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void step() {
        this.position_y += this.speed;
    }

    public int getPoint() {
        return point;
    }

    public void shootBy(Bullet bullet) {
        // 中弹后减少生命值。
        if (this.collision(bullet)) {
            this.health -= bullet.getHitpoint();
        }
    }

}
