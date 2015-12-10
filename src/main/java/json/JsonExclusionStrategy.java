/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 *
 * @author MASC
 */
public class JsonExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fa) {
        return fa.getAnnotation(JsonExclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        return false;
    }
    
}
