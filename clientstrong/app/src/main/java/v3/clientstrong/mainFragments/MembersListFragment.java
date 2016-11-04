package v3.clientstrong.mainFragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import v3.clientstrong.R;
import v3.clientstrong.adapters.MembersListAdapter;
import v3.clientstrong.models.Member;
import v3.clientstrong.requests.MembersListRequest;
import v3.clientstrong.requests.RequestManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MembersListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
// * Use the {@link MembersListFragment#} factory method to
 * create an instance of this fragment.
 */
public class MembersListFragment extends Fragment {

    private static RecyclerView mMembersListView;
    private SwipeRefreshLayout swipeContainer;

    public interface OnFragmentInteractionListener { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_members_list, container, false);
        mMembersListView = (RecyclerView) root.findViewById(R.id.members_list);
        mMembersListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMembersListView.setHasFixedSize(true);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);



        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
//                fetchTimelineAsync(0);
                requestMemberList("members");
            }
        });

// Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        if (isNetworkConnected())
            requestMemberList("members");
        else
            try {
                localRequestForFakeData();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return root;
    }

    private void localRequestForFakeData() throws IOException {
        Reader reader = new InputStreamReader(getActivity().getAssets().open("MembersList.json"));
        ArrayList<Member> peopleList = new Gson().fromJson(reader, new TypeToken<ArrayList<Member>>(){}.getType());
        populateMemberList(peopleList);
    }

    public void requestMemberList(String endPoint) {
        MembersListRequest jsObjRequest = new MembersListRequest(endPoint, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Member> peopleList = new Gson().fromJson(response.toString(),
                        new TypeToken<ArrayList<Member>>(){}.getType());
                populateMemberList(peopleList);
                swipeContainer.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(getActivity().getTitle().toString(), "onError: " + error.getMessage());
                swipeContainer.setRefreshing(false);
            }
        });

        RequestManager.getInstance(getContext()).addToRequestQueue(jsObjRequest);
    }

    public void populateMemberList(ArrayList<Member> peopleList) {
        MembersListAdapter adapter = new MembersListAdapter(this, peopleList, mMembersListView);
        mMembersListView.setAdapter(adapter);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
