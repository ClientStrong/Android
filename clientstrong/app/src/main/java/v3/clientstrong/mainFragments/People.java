package v3.clientstrong.mainFragments;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import v3.clientstrong.R;
import v3.clientstrong.activities.ProfileScreen;
import v3.clientstrong.models.Person;
import v3.clientstrong.requests.PeopleListRequest;
import v3.clientstrong.requests.RequestManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link People.OnFragmentInteractionListener} interface
 * to handle interaction events.
// * Use the {@link People#} factory method to
 * create an instance of this fragment.
 */
public class People extends Fragment {

    private static RecyclerView mMembersListView;
    private boolean mFavorite = false;

    public interface OnFragmentInteractionListener {}

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

    public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

        private List<Person> memberList;

        public MembersAdapter(List<Person> memberList) {
            this.memberList = memberList;
        }

        @Override
        public int getItemCount() {
            return memberList.size();
        }

        @Override
        public void onBindViewHolder(MemberViewHolder memberViewHolder, int i) {
            Person member = memberList.get(i);
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
                Person item = memberList.get(itemPosition);

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

    public void populateMemberList(ArrayList<Person> peopleList) {
        MembersAdapter adapter = new MembersAdapter(peopleList);
        mMembersListView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            final OnFragmentInteractionListener fragmentInteractionContext
                    = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
