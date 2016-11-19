package v3.clientstrong.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by runquest.
 * Date: 2016-11-11
 */
public class LoginRequest extends JsonObjectRequest {


    public LoginRequest(String endPoint, String email, String password, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, getURL(endPoint), getObject(), listener, errorListener);
    }



    // Overrides header; necessary to set response type;
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Accept", "application/json");

        return params;
    }

    private static String getURL(String endPoint) {
        return RequestManager.getBaseUrl() + "login";
//        return RequestManager.getBaseUrl() + endPoint;
    }

    private static JSONObject getObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", "doc.dwarf@clientstrong.com");
            obj.put("password", "password");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }



    }