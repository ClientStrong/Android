package v3.clientstrong.mainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v3.clientstrong.R;
import v3.clientstrong.RequestManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Members.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Members#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Members extends Fragment {

    private static RecyclerView mMembersListView;
    private static ArrayList<Member> arrayOfUsers = new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment Members.
     */
    public static Members newInstance() {
        Members fragment = new Members();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_members, container, false);
        mMembersListView = (RecyclerView) root.findViewById(R.id.members_list);

        mMembersListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMembersListView.setLayoutManager(llm);

        requestMemberList();

        return root;
    }

    // Server request to receive member list.
    public void requestMemberList() {

        /**
         * Processes GET request to get JSONArray;
         */

        String url = "https://warm-refuge-4462.herokuapp.com/api/v1/members";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        Gson gson = new Gson();
                        JSONArray jsonArray = response;
                        Type listType = new TypeToken<ArrayList<Member>>(){}.getType();

                        ArrayList<Member> myModelList = gson.fromJson(jsonArray.toString(), listType);

                        arrayOfUsers = myModelList;

                        populateMemberList();

                        Log.i("Response: ", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Response: ", error.toString());
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

        RequestManager.getInstance(getContext()).addToRequestQueue(jsObjRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // MODEL

    public class Member {
        public String email;
        public String password;
        public String first_name;
        public String last_name;
        public String address;
        public String mobile;
        public String birthday;

        public Member(String email, String password, String first_name, String last_name, String address, String mobile, String birthday) {
            this.email = email;
            this.password = password;
            this.first_name = first_name;
            this.last_name = last_name;
            this.address = address;
            this.mobile = mobile;
            this.birthday = birthday;
        }
    }

    public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

        private List<Member> memberList;

        public MembersAdapter(List<Member> memberList) {
            this.memberList = memberList;
        }

        @Override
        public int getItemCount() {
            return memberList.size();
        }

        @Override
        public void onBindViewHolder(MemberViewHolder memberViewHolder, int i) {
            Member member = memberList.get(i);
            memberViewHolder.firstName.setText(member.first_name);
            memberViewHolder.email.setText(member.email);
            memberViewHolder.image.setImageResource(R.drawable.ic_action_emo_cool);
        }

        @Override
        public MemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.member_cell, viewGroup, false);

            return new MemberViewHolder(itemView);
        }

        public class MemberViewHolder extends RecyclerView.ViewHolder {
            protected TextView firstName;
            protected TextView email;
            protected ImageView image;

            public MemberViewHolder(View v) {
                super(v);
                firstName =  (TextView) v.findViewById(R.id.first_name_list);
                email = (TextView)  v.findViewById(R.id.email_list);
                image = (ImageView) v.findViewById(R.id.image);
            }
        }
    }

    public void populateMemberList() {
        MembersAdapter adapter = new MembersAdapter(arrayOfUsers);
        mMembersListView.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
