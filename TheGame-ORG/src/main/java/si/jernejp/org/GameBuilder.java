/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org;

import static org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR;
import si.jernejp.org.core.Configuration;
import si.jernejp.org.core.IGame;
import si.jernejp.org.core.devices.MouseDevice;
import si.jernejp.org.core.devices.KeyboardDevice;
import si.jernejp.org.core.devices.WindowDevice;

/**
 *
 * @author Jernej
 */
public class GameBuilder {
    
    Configuration configuration;
    WindowDevice windowDevice;
    MouseDevice inputDevice;
    KeyboardDevice keyboardDevice;
    IGame game;

 
    public GameBuilder(){
        windowDevice = new WindowDevice();
        inputDevice = new MouseDevice();
        keyboardDevice = new KeyboardDevice();
        inputDevice.selectCursorStrategy(inputDevice.new FPSCursor());
        configuration = new Configuration();
        configuration.add("windowed", false);
        configuration.add("title", "Test");
        configuration.add("cursorType", GLFW_CROSSHAIR_CURSOR);
    }

    
    public void start(){
        new Initialize(configuration, windowDevice, inputDevice, keyboardDevice, game).run();
    }
}
