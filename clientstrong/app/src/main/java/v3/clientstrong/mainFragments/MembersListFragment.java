package v3.clientstrong.mainFragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(getActivity().getTitle().toString(), "onError: " + error.getMessage());
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
