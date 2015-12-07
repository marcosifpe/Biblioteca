/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MASC
 */
abstract class JsonInterceptador {

    protected String getResult(String status, String mensage) {
        Gson gson = new Gson();
        Map jsonMap = new HashMap<String, String>();
        jsonMap.put("status", status);
        jsonMap.put("mensagem", mensage);
        return gson.toJson(jsonMap);
    }
}
