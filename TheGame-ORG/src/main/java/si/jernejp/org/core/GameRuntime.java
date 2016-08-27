/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.core;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;
import si.jernejp.org.core.devices.MouseDevice;
import si.jernejp.org.core.devices.KeyboardDevice;
import si.jernejp.org.core.devices.WindowDevice;
import si.jernejp.org.engine.GameEngine;

/**
 *
 * @author Jernej
 */
public class GameRuntime {

    private final GameEngine game;
    private final WindowDevice windowDevices;
    private final MouseDevice inputDevices;
    private long lastTimestamp;
    private final static int FPS = 1000 / 1;
    private final KeyboardDevice keyboardDevice;

    public GameRuntime(GameEngine game, WindowDevice windowDevices, MouseDevice inputDevices, KeyboardDevice keyboardDevice) {
        this.game = game;
        this.windowDevices = windowDevices;
        this.inputDevices = inputDevices;
        this.keyboardDevice = keyboardDevice;
    }

    public void loop() throws InterruptedException {
        long diff = 0;
        while (!glfwWindowShouldClose(windowDevices.windowId)) {
            glViewport(0, 0, windowDevices.width, windowDevices.height);
            //input
            game.input(inputDevices, keyboardDevice);
            //update
            game.update(diff);
            glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
            //render
            game.render();
            glfwSwapBuffers(windowDevices.windowId);
            glfwPollEvents();
            diff = syncFps();
        }
        game.shutdown();
    }

    private long syncFps() throws InterruptedException {
        long currentTimestamp = System.currentTimeMillis();
        long timeSpent = currentTimestamp - lastTimestamp;
        long timeLeft = FPS - timeSpent;
        if (timeLeft > 0) {
            Thread.sleep(timeLeft);
        }
        currentTimestamp = System.currentTimeMillis();
        long diff = currentTimestamp - lastTimestamp;
        lastTimestamp = currentTimestamp;
        return diff;
    }
    
}
