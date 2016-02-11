








//package v3.clientstrong;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import org.json.JSONArray;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class MembersList extends AppCompatActivity {
////public class MembersList extends FragmentActivity {
////
//    private static ArrayList<Member> arrayOfUsers = new ArrayList<Member>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.members_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        requestMemberList();
//    }
//
//
//    // Server request to receive member list.
//
//    public void requestMemberList() {
//
//        /**
//         * Processes GET request to get JSONArray;
//         */
//
//        String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members";
//
//        JsonArrayRequest jsObjRequest = new JsonArrayRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//
//                        Gson gson = new Gson();
//                        JSONArray jsonArray = response;
//                        Type listType = new TypeToken<ArrayList<Member>>(){}.getType();
//
//                        ArrayList<Member> myModelList = gson.fromJson(jsonArray.toString(), listType);
//
//
//                        arrayOfUsers = myModelList;
//
//
//
//
//                        populateMemberList();
//
//                        Log.i("Response: ", response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("Response: ", error.toString());
//                    }
//                }) {
//
//            // Overrides header; necessary to set response type;
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Accept", "application/json");
//
//                return params;
//            }
//        };
//
//        RequestManager.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
//    }
//
//    public void populateMemberList() {
////
////        /*Creation of ArrayList: I'm going to add String
////       *elements so I made it of string type */
////        ArrayList<String> obj = new ArrayList<String>();
////
////	  /*This is how elements should be added to the array list*/
////        obj.add("Ajeet");
////        obj.add("Harry");
////        obj.add("Chaitanya");
////        obj.add("Steve");
////        obj.add("Anuj");
////
////
////
////        ArrayAdapter<String> itemsAdapter =
////                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, obj);
////
////        ListView listView = (ListView) findViewById(R.id.members_list);
////        listView.setAdapter(itemsAdapter);
//
//
////        // Construct the data source
////        ArrayList<Member> arrayOfMembers = new ArrayList<Member>();
////// Create the adapter to convert the array to views
////        MembersAdapter adapter = new MembersAdapter(this, arrayOfMembers);
////// Attach the adapter to a ListView
////        ListView listView = (ListView) findViewById(R.id.members_list);
////        listView.setAdapter(adapter);
//
//
//// Construct the data source
////        ArrayList<Member> arrayOfUsers = new ArrayList<Member>();
//// Create the adapter to convert the array to views
//        MembersAdapter adapter = new MembersAdapter(this, arrayOfUsers);
//// Attach the adapter to a ListView
//        ListView listView = (ListView) findViewById(R.id.members_list);
//        listView.setAdapter(adapter);
//
//
//    }
//
//
//    public class Member {
//        public String email;
//        public String password;
//        public String first_name;
//        public String last_name;
//        public String address;
//        public String mobile;
//        public String birthday;
//
//        public Member(String email, String password, String first_name, String last_name, String address, String mobile, String birthday) {
//            this.email = email;
//            this.password = password;
//            this.first_name = first_name;
//            this.last_name = last_name;
//            this.address = address;
//            this.mobile = mobile;
//            this.birthday = birthday;
//        }
//    }
//
//    public class MembersAdapter extends ArrayAdapter<Member> {
//        public MembersAdapter(Context context, ArrayList<Member> members) {
//            super(context, 0, members);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // Get the data item for this position
//            Member member = getItem(position);
//            // Check if an existing view is being reused, otherwise inflate the view
//            if (convertView == null) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.member_cell, parent, false);
//            }
//            // Lookup view for data population
//            TextView tvName = (TextView) convertView.findViewById(R.id.first_name_list);
//            TextView tvHome = (TextView) convertView.findViewById(R.id.email_list);
//            // Populate the data into the template view using the data object
//            tvName.setText(member.first_name);
//            tvHome.setText(member.email);
//            // Return the completed view to render on screen
//            return convertView;
//        }
//    }
//
//
//
//
//
//}
