import java.awt.image.BufferedImage;

public abstract class FlyingObject {
    /** 飞行物
     *  包括敌机， 子弹， 玩家飞机
     */
    protected int position_x; //中心位置x坐标
    protected int position_y; //中心位置y坐标
    protected int width;      //宽度
    protected int height;     //高度
    protected BufferedImage image;//图片

    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean outOfBound() {
        double x_left = position_x - width*0.5;
        double x_right = position_x + width*0.5;
        double y_up = position_y - height*0.5;
        double y_down = position_y + height*0.5;
        if (x_left<0 || x_right>GameRun.panelWidth) {
            return true;
        }

        if (y_up<0 || y_down> GameRun.panelHeight) {
            return true;
        }

        return false;
    }

    public abstract void step(); //抽象方法，让对象移动一步。

    public boolean collision(FlyingObject fly) {
        // 判断对象有没有和其他对象碰撞
        int x = fly.position_x;
        int y = fly.position_y;
        return (Math.abs(x-this.position_x)<this.width*0.5 &&
            Math.abs(y-this.position_y)<this.height*0.5
        );
    }
}
