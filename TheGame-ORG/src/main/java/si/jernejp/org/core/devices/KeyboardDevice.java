/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.core.devices;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

/**
 *
 * @author Jernej
 */
public class KeyboardDevice {

    private final boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST];

    public void keyboardCallback(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_UNKNOWN) {
            return;
        }
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
        }
        if (action == GLFW_PRESS || action == GLFW_REPEAT) {
            keyDown[key] = true;
        } else {
            keyDown[key] = false;
        }

    }
}
