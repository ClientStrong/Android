package v3.clientstrong;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.FragmentPagerAdapter;
////import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TabHost;
//import android.widget.TextView;
////import android.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import v3.clientstrong.mainFragments.Invoices;
import v3.clientstrong.mainFragments.Members;
import v3.clientstrong.mainFragments.Schedule;

public class Dashboard extends AppCompatActivity implements Members.OnFragmentInteractionListener, Invoices.OnFragmentInteractionListener, Schedule.OnFragmentInteractionListener {

//    private Toolbar toolbar;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Members(), "Members");
        adapter.addFragment(new Invoices(), "Invoices");
        adapter.addFragment(new Schedule(), "Schedule");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dashboard);
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
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.addTab(tabLayout.newTab().setText("Member"));
//        tabLayout.addTab(tabLayout.newTab().setText("Exercise"));
//        tabLayout.addTab(tabLayout.newTab().setText("Invoice"));
//
//
//        /**
//         * Opens up new Main Activity;
//         */
//        TextView newActivity = (TextView) findViewById(R.id.show_member);
//        newActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(), MemberProfile.class);
//                startActivity(intent);
//            }
//        });
//
//
//        /**
//         * Processes GET request to get JSONArray;
//         */
//        TextView allMembersList = (TextView) findViewById(R.id.members_list);
//
//        allMembersList.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(), MembersList.class);
//                startActivity(intent);
//            }
//        });
//
//        /**
//         * Processes POST request to get JSONArray;
//         */
//        TextView createNewMembers = (TextView) findViewById(R.id.add_member);

//        createNewMembers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final TextView mTextView = (TextView) findViewById(R.id.text);
//
//                String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members";
//
//                Map<String, Object> params = new HashMap<String, Object>();
//                params.put("email", "snowwhite5@cs.ca");
//                params.put("password", "password");
//                params.put("first_name", "snow12");
//                params.put("last_name", "white12");
//                params.put("address", "street somewhere");
//                params.put("mobile", "234-234-3456");
//                params.put("birthday", "1902-12-02");
//
//                Map<String, Object> member = new HashMap<String, Object>();
//                member.put("member", params);
//                member.put("role_id", 2);
//
//                JSONObject jsonBody = new JSONObject(member);
//
//                JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                        (url, jsonBody, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                mTextView.setText("Response: " + response.toString());
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO Auto-generated method stub
//                                mTextView.setText("Response: " + error.toString());
//
//                            }
//                        }) {
//
//                    // Overrides header; necessary to set response type;
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("Accept", "application/json");
//
//                        return params;
//                    }
//                };
//
//                RequestManager.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
//            }
//        });

///**
// * Processes GET with access_token request to get JSONObject;
// */
//TextView getWithAccessRequest = (TextView) findViewById(R.id.empty);

//        getWithAccessRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final TextView mTextView = (TextView) findViewById(R.id.text);
//
//                String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members/3";
//
//                JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                mTextView.setText("Response: " + response.toString());
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO Auto-generated method stub
//                                mTextView.setText("Response: " + error.toString());
//
//                            }
//                        }) {
//
//                    // Overrides header; necessary to set response type;
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        String tokenCode = "mVjDPcx1saL6ko4FR7UEaQtt";
//                        params.put("Accept", "application/json");
//                        params.put("Authorization", "Token token=" + tokenCode);
//
//                        return params;
//                    }
//                };
//
//                RequestManager.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
//            }
//        });
//    }
//}

