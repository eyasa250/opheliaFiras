package com.example.returnto0.user;

import android.util.Base64;
import org.json.JSONObject;

public class JWTUtils {

    public static JSONObject decodeJWT(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            if (split.length == 3) { // Make sure the token is correctly structured
                String base64EncodedBody = split[1];
                String body = new String(Base64.decode(base64EncodedBody, Base64.URL_SAFE), "UTF-8");
                return new JSONObject(body);
            }
            return null; // Not a valid token format
        } catch (Exception e) {
            throw new Exception("Error decoding token: " + e.getMessage());
        }
    }
}
