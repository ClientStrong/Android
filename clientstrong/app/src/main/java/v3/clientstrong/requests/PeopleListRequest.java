package v3.clientstrong.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by runquest.
 * Date: 2016-10-19
 */
public class PeopleListRequest extends JsonArrayRequest {

    public PeopleListRequest(String endPoint, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(getURL(endPoint), listener, errorListener);
    }

    // Overrides header; necessary to set response type;
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Accept", "application/json");

        return params;
    }

    private static String getURL(String endPoint) {

        final String URL = String.format(RequestManager.getBaseUrl(), endPoint);
        return URL;
    }
}
