package v3.clientstrong;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        /**
         * Opens up new Main Activity;
         */

        ImageView newActivity = (ImageView) findViewById(R.id.memberListIcon);
        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });


        /**
         * Processes GET request to get JSONArray;
         */
        ImageView getRequest = (ImageView) findViewById(R.id.programListIcon);

        getRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView mTextView = (TextView) findViewById(R.id.text);

                String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members";

                JsonArrayRequest jsObjRequest = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                mTextView.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                mTextView.setText("Response: " + error.toString());

                            }
                        }) {

                    // Overrides header; necessary to set response type;
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Accept", "application/json");

                        return params;
                    }
                };

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
            }
        });

        /**
         * Processes POST request to get JSONArray;
         */
        ImageView postRequest = (ImageView) findViewById(R.id.invoiceIcon);

        postRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView mTextView = (TextView) findViewById(R.id.text);


                String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members";

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("email", "snowwhite5@cs.ca");
                params.put("password", "password");
                params.put("first_name", "snow12");
                params.put("last_name", "white12");
                params.put("address", "street somewhere");
                params.put("mobile", "234-234-3456");
                params.put("birthday", "1902-12-02");

                Map<String, Object> member = new HashMap<String, Object>();
                member.put("member", params);
                member.put("role_id", 2);

                JSONObject jsonBody = new JSONObject(member);

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (url, jsonBody, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                mTextView.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                mTextView.setText("Response: " + error.toString());

                            }
                        }) {

                    // Overrides header; necessary to set response type;
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Accept", "application/json");

                        return params;
                    }
                };

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
            }
        });

        /**
         * Processes GET with access_token request to get JSONObject;
         */
        ImageView getWithAccessRequest = (ImageView) findViewById(R.id.scheduleIcon);

        getWithAccessRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView mTextView = (TextView) findViewById(R.id.text);

                String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members/3";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                mTextView.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                mTextView.setText("Response: " + error.toString());

                            }
                        }) {

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
        });
    }
}

