package v3.clientstrong.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import v3.clientstrong.activities.ProfileScreen;
import v3.clientstrong.requests.RequestManager;

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
    private boolean mFavorite = false;

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

        public String id;
        public String email;
        public String password;
        public String first_name;
        public String last_name;
        public String address;
        public String mobile;
        public String birthday;

        public Member(String id, String email, String password, String first_name, String last_name, String address, String mobile, String birthday) {
            this.id = id;
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
            memberViewHolder.fullName.setText(member.first_name + " " + member.last_name);
            memberViewHolder.setItem(memberList.get(i).toString());
            memberViewHolder.letter.setText(String.valueOf(member.first_name.charAt(0)).toUpperCase());

            //TODO: refactor with setFavorite;
            mFavorite = String.valueOf(member.first_name.charAt(0)).toUpperCase().equals("S");
            if (mFavorite) {
                mFavorite = true;
                memberViewHolder.star.setBackground(getResources().getDrawable(R.drawable.ic_star));
            } else {
                mFavorite = false;
                memberViewHolder.star.setBackground(getResources().getDrawable(R.drawable.ic_star_border));
            }
        }

        @Override
        public MemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.member_cell, viewGroup, false);

            return new MemberViewHolder(itemView);
        }

        public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
                 View.OnCreateContextMenuListener {
            TextView fullName;
            TextView letter;
            ImageView star;

            private String mItem;

            public MemberViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                v.setOnCreateContextMenuListener(this);
                fullName = (TextView) v.findViewById(R.id.first_name_list);
                letter = (TextView) v.findViewById(R.id.profile_image);
                star = (ImageView) v.findViewById(R.id.star);

                star.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //TODO: refactor with setFavorite;
                        if (mFavorite) {
                            mFavorite = false;
                            star.setBackground(getResources().getDrawable(R.drawable.ic_star_border));
                        } else {
                            mFavorite = true;
                            star.setBackground(getResources().getDrawable(R.drawable.ic_star));
                        }
                    }
                });
            }

            public void setItem(String item){
                mItem = item;
            }

            @Override
            public void onClick(View v) {
                int itemPosition = mMembersListView.getChildPosition(v);
                Member item = memberList.get(itemPosition);

                Intent intent = new Intent (getActivity(), ProfileScreen.class).putExtra(Intent.EXTRA_TEXT, item.id);
                startActivity(intent);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select The Action");
                menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
                menu.add(0, v.getId(), 0, "SMS");
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
