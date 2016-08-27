/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.engine;

import org.joml.Vector2f;
import si.jernejp.org.core.devices.MouseDevice;
import si.jernejp.org.core.devices.KeyboardDevice;
import si.jernejp.org.engine.objects.Camera;

/**
 *
 * @author Jernej
 */
public class GameEngine {

    Camera camera;
    Transformation transformation;
    Renderer renderer;
    Scene scene;

    public GameEngine(Camera camera, Transformation transformation, Renderer renderer, Scene scene) {
        this.camera = camera;
        this.transformation = transformation;
        this.renderer = renderer;
        this.scene = scene;
    }

    public void input(MouseDevice mouseDevices, KeyboardDevice keyboardDevice) {
        mouseDevices.processInput();
        Vector2f displayVector = mouseDevices.getDisplayVector();
       // camera.moveRotation(displayVector.x, displayVector.y, 0);
        System.out.println(camera+ "    "+displayVector);
    }

    public void update(long diff) throws InterruptedException {
    }

    public void render() {
        renderer.render(transformation.getProjectionMatrix(), transformation.getViewMatrix(camera.position, camera.rotation), scene);
    }

    public void shutdown() {
        renderer.close();
    }

}
