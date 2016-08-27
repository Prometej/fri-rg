/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import si.jernejp.org.core.events.WindowChangeListener;

/**
 *
 * @author Jernej
 */
public class Transformation implements WindowChangeListener {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private float aspectRatio;
    private final Matrix4f projectionMatrix = new Matrix4f();
    private final Matrix4f viewMatrix = new Matrix4f();
    private final Vector3f upVector = new Vector3f(0, 1, 0);

    public void updateCamera(float rotateX, float rotateY, float x, float y, float z) {
        viewMatrix.rotationX(rotateX)
                .rotateY(rotateY)
                .translate(-x, -y, -z);
    }

    public Matrix4f getViewMatrix(Vector3f cameraPosition, Vector3f cameraLookAt) {
        return viewMatrix.setLookAt(cameraPosition, cameraLookAt, upVector);
    }

    public Matrix4f getProjectionMatrix() {
        //projectionMatrix.identity();
        //projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    @Override
    public void notifyChange(int width, int height) {
        aspectRatio = width / height;
    }
}
