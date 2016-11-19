package v3.clientstrong.mainFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Set up 1 action button
        inflater.inflate(R.menu.menu_members_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
//                Toast.makeText(getActivity(), "Send email", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());

                // set title
                alertDialogBuilder.setTitle("Log out");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

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
