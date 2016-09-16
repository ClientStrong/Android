package v3.clientstrong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import v3.clientstrong.requests.RequestManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileScreenFragment extends Fragment {

    private TextView firstName;
    private TextView lastName;
    private String id;

    public ProfileScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile_screen, container, false);

        firstName = (TextView) root.findViewById(R.id.first_name);
        lastName = (TextView) root.findViewById(R.id.lastname);

        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra(Intent.EXTRA_TEXT);

        request(id);

        return root;
    }

    public void request(String id) {
        /**
         * Processes GET with access_token request to get JSONObject;
         */
        String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members/" + id;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String firstNameString = null;
                        String lastNameString = null;
                        try {
                            firstNameString = response.getString("first_name");
                            lastNameString = response.getString("last_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        firstName.setText(firstNameString);
                        lastName.setText(lastNameString);
                        Log.i("Response OK", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("Response ERROR", error.toString());

                    }
                })
        {

            // Overrides header; necessary to set response type;
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String tokenCode = "mVjDPcx1saL6ko4FR7UEaQtt";
                params.put("Accept", "application/json");
                params.put("Authorization", "Token token=" + tokenCode);

                return params;
            }
        };

        RequestManager.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
    }
}
