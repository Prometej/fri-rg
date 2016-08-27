/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.engine.utils;

import org.joml.Vector3f;

public class MeshObject {

    public String name;
    public int first;
    public int count;
    public Vector3f min = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
    public Vector3f max = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);

    public String toString() {
        return name + "(" + min + " " + max + ")";
    }
}
