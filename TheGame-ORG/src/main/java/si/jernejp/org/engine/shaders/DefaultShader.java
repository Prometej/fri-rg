/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.engine.shaders;

import org.joml.Matrix4f;

/**
 *
 * @author Jernej
 */
public class DefaultShader extends AbstractShader {

    private static final String VIEW_MATRIX_UNIFORM = "modelViewMatrix";
    private static final String PROJECTION_MATRIX_UNIFORM = "projectionMatrix";
    private final int viewMatrixUniformLocation;
    private final int projectionMatrixUniformLocation;
    
    public DefaultShader() {
        super();
        viewMatrixUniformLocation = createUniform(VIEW_MATRIX_UNIFORM);
        projectionMatrixUniformLocation = createUniform(PROJECTION_MATRIX_UNIFORM);
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        setUniform(projectionMatrixUniformLocation, projectionMatrix);
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        setUniform(viewMatrixUniformLocation, viewMatrix);
    }

}
