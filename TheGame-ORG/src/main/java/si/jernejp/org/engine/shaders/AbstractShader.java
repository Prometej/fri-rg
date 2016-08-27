/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.engine.shaders;

import java.io.Closeable;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

/**
 *
 * @author Jernej
 */
public abstract class AbstractShader {

    private final int programId;
    private final int vertexShaderId;
    private final int fragmentShaderId;

    AbstractShader() {
        try {
            programId = glCreateProgram();
            Path vertexShaderPath = Paths.get(this.getClass().getResource(this.getClass().getSimpleName() + ".vs").toURI());
            Path fragmentShaderPath = Paths.get(this.getClass().getResource(this.getClass().getSimpleName() + ".fs").toURI());
            vertexShaderId = createShader(programId, new String(Files.readAllBytes(vertexShaderPath)), GL_VERTEX_SHADER);
            fragmentShaderId = createShader(programId, new String(Files.readAllBytes(fragmentShaderPath)), GL_FRAGMENT_SHADER);
            glLinkProgram(programId);
            if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
                throw new Exception("Error linking shader code: " + glGetShaderInfoLog(programId, 1024));
            }
            glValidateProgram(programId);
            if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
                throw new Exception("Error validating shader program: " + glGetShaderInfoLog(programId, 1024));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the Program." + ex);
        }
    }

    private static int createShader(int programId, String source, int type) throws Exception {
        int shaderId = glCreateShader(type);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Code: " + shaderId);
        }
        glShaderSource(shaderId, source);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }
        glAttachShader(programId, shaderId);
        return shaderId;
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void close() {
        unbind();
        if (programId != 0) {
            if (vertexShaderId != 0) {
                glDetachShader(programId, vertexShaderId);
            }
            if (fragmentShaderId != 0) {
                glDetachShader(programId, fragmentShaderId);
            }
            glDeleteProgram(programId);
        }
    }

    public int createUniform(String name) {
        int uniformLocation = glGetUniformLocation(programId, name);
        if (uniformLocation < 0) {
            throw new RuntimeException("Could not find uniform:" + name);
        }
        return uniformLocation;
    }

    public void setUniform(int location, Matrix4f value) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        value.get(fb);
        glUniformMatrix4fv(location, false, fb);
    }
}
