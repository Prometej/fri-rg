/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.engine;

import java.util.List;
import si.jernejp.org.scene.SceneObject;
import si.jernejp.org.scene.SceneTerrain;
import si.jernejp.org.scene.SceneWeather;

/**
 *
 * @author Jernej
 */
public class Scene {

    SceneTerrain terrain;
    SceneWeather weather;
    List<SceneObject> objects;
}
