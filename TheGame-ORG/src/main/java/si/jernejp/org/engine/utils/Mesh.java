/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  si.jernejp.org.engine.utils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jernej
 */
public class Mesh {

    public FloatBuffer positions;
    public FloatBuffer normals;
    public int numVertices;
    public float boundingSphereRadius;
    public List<MeshObject> objects = new ArrayList<MeshObject>();
}
