/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.scene;

import org.joml.Vector3f;

/**
 *
 * @author Jernej
 */
public class Square extends SceneObject {

    public Square(Vector3f position, Vector3f rotation) {
        super(position, rotation);
    }

    @Override
    protected void updateObject() {
    }


    @Override
    protected float[] readArray() {
        return new float[]{
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,};
    }

}
