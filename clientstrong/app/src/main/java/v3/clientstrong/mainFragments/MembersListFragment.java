package v3.clientstrong.mainFragments;

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

import java.util.ArrayList;

import v3.clientstrong.R;
import v3.clientstrong.adapters.MembersAdapter;
import v3.clientstrong.models.Person;
import v3.clientstrong.requests.PeopleListRequest;
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

        View root = inflater.inflate(R.layout.fragment_members, container, false);
        mMembersListView = (RecyclerView) root.findViewById(R.id.members_list);
        mMembersListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMembersListView.setHasFixedSize(true);
        requestMemberList("members");
        return root;
    }

    public void requestMemberList(String endPoint) {
        PeopleListRequest jsObjRequest = new PeopleListRequest(endPoint, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Person> peopleList = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<Person>>(){}.getType());
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

    public void populateMemberList(ArrayList<Person> peopleList) {
        MembersAdapter adapter = new MembersAdapter(this, peopleList, mMembersListView);
        mMembersListView.setAdapter(adapter);
    }
}
