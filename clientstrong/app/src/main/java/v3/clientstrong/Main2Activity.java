package v3.clientstrong;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView firstName = (TextView) findViewById(R.id.first_name);
        final TextView lastName = (TextView) findViewById(R.id.lastname);

        /**
         * Processes GET with access_token request to get JSONObject;
         */
        String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members/3";

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

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
    }
}