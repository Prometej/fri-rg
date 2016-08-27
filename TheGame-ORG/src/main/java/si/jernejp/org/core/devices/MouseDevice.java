/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.core.devices;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import si.jernejp.org.core.events.WindowChangeListener;

/**
 *
 * @author Jernej
 */
public class MouseDevice implements WindowChangeListener {

    private static final float MOUSE_SENSITIVITY = 0.2f;
    private int halfWidth, halfHeight;

    private boolean leftMouseDown;
    private boolean rightMouseDown;

    public float prevxpos, prevypos;
    public float xpos, ypos;
    private final Vector2f displVec = new Vector2f();

    private CursorStrategy cursorStrategy;

    public void selectCursorStrategy(CursorStrategy cursorStrategy) {
        this.cursorStrategy = cursorStrategy;
    }

    public void processInput() {
        displVec.x = xpos * MOUSE_SENSITIVITY;
        displVec.y = ypos * MOUSE_SENSITIVITY;
        System.out.println(xpos+" "+ypos);
        xpos = 0;
        ypos = 0;
    }

    public Vector2f getDisplayVector() {
        System.out.println(displVec);
        return displVec;
    }

    public void mouseCallback(long window, int button, int action, int mods) {
        if (button == GLFW_MOUSE_BUTTON_LEFT) {
            if (action == GLFW_PRESS) {
                leftMouseDown = true;
            } else if (action == GLFW_RELEASE) {
                leftMouseDown = false;
            }
        } else if (button == GLFW_MOUSE_BUTTON_RIGHT) {
            if (action == GLFW_PRESS) {
                rightMouseDown = true;
            } else if (action == GLFW_RELEASE) {
                rightMouseDown = false;
            }
        }
    }

    // Notify window size change
    @Override
    public void notifyChange(int width, int height) {
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
    }

    // Notify mouse move
    public void cursorCallback(long window, double xpos, double ypos) {
        cursorStrategy.updateCursor(window, xpos, ypos);
    }

    ////////////////////////////////////////////////////////////////////////////
    // STRATEGIES //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public interface CursorStrategy {

        public void updateCursor(long window, double xpos, double ypos);

    }

    public class FPSCursor implements CursorStrategy {

        @Override
        public void updateCursor(long window, double newXpos, double newYpos) {
            System.out.println(window+" "+newXpos+" "+newYpos);
            xpos = (float) (halfWidth - newXpos);
            ypos = (float) (halfHeight - newYpos);
        }

    }

    public class RPSCursor implements CursorStrategy {

        @Override
        public void updateCursor(long window, double xpos, double ypos) {
        }

    }

}
