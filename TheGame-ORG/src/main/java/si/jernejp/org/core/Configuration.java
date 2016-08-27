/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jernej
 */
public class Configuration {
    
    private Map<String, Object> configuration = new HashMap<>();
    
    public void add(String name, Object value){
        configuration.put(name, value);
    }
    
    public <T> T get(Class<T> clasz, String name) {
        return clasz.cast(configuration.get(name));
    }
    
}
