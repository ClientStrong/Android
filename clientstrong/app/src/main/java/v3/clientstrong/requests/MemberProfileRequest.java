package v3.clientstrong.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by runquest.
 * Date: 2016-10-19
 */

public class MemberProfileRequest extends JsonObjectRequest {

    public MemberProfileRequest(String endPoint, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, getURL(endPoint), null, listener, errorListener);
    }

    // Overrides header; necessary to set response type;
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Accept", "application/json");
//        SessionManager.getInstance().getUserDetails();
//        String tokenCode = SessionManager.KEY_TOKEN;
//        params.put("Authorization", "Token token=" + tokenCode);
        return params;
    }

    private static String getURL(String endPoint) {
        return RequestManager.getBaseUrl() + endPoint;
    }
}