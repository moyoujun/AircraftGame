import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControl implements KeyListener {
    private HeroPlane heroPlane;
    private GameRun gameRun;

    public GameControl(GameRun gameRun) {
        this.gameRun = gameRun;
        this.heroPlane = gameRun.getHeroPlane();
    }

    public void keyPressed(KeyEvent e) {
        if(this.heroPlane.getLife() > 0) {
            int keyInt = e.getKeyCode();
            switch (keyInt) {
                case KeyEvent.VK_W: {
                    heroPlane.moveUp();
                    break;
                }
                case KeyEvent.VK_A: {
                    heroPlane.moveLeft();
                    break;
                }
                case KeyEvent.VK_S: {
                    heroPlane.moveDown();
                    break;
                }
                case KeyEvent.VK_D: {
                    heroPlane.moveRight();
                    break;
                }
                case KeyEvent.VK_ENTER: {
                    gameRun.pauseGame();
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
}
