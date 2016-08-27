/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL11.glClearColor;
import si.jernejp.org.engine.shaders.DefaultShader;
import si.jernejp.org.scene.Square;

/**
 *
 * @author Jernej
 */
public class Renderer {

    private final DefaultShader defaultShader = new DefaultShader();
    private final Square square = new Square(new Vector3f(0,0,0), new Vector3f());

    public Renderer() {
        square.init();
    }

    public void render(Matrix4f projectionMatrix, Matrix4f viewMatrix, Scene scene) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        defaultShader.bind();
        defaultShader.setProjectionMatrix(projectionMatrix);
        defaultShader.setViewMatrix(viewMatrix);
        square.render();
        defaultShader.unbind();
    }
    
    public void close(){
        defaultShader.close();
    }

}
