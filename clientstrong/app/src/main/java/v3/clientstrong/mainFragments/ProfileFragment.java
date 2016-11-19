package v3.clientstrong.mainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import v3.clientstrong.R;
import v3.clientstrong.SessionManager;
import v3.clientstrong.customViews.ProfileContactCard;
import v3.clientstrong.models.Member;
import v3.clientstrong.requests.MemberProfileRequest;
import v3.clientstrong.requests.RequestManager;

import static v3.clientstrong.SessionManager.KEY_TOKEN;

/**
 * Fragment to display information related to member's profile.
 * Created by @runquest.
 * Last modified: 2016-10-24
 */

public class ProfileFragment extends Fragment {

    private ProfileContactCard mAddress;
    private ProfileContactCard mPhone;
    private ProfileContactCard mEmail;
    private ProfileContactCard mBirthday;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SessionManager sessionManager = new SessionManager(getActivity());
        String token = sessionManager.getUserDetails().get(KEY_TOKEN);

        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra(Intent.EXTRA_TEXT);
        request("members/" + id, token);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAddress = (ProfileContactCard) view.findViewById(R.id.address);
        mPhone = (ProfileContactCard) view.findViewById(R.id.phone);
        mEmail = (ProfileContactCard) view.findViewById(R.id.email);
        mBirthday = (ProfileContactCard) view.findViewById(R.id.birthday);

        mAddress.setCustomDrawable(getResources().getDrawable(R.drawable.ic_action_home));
        mPhone.setCustomDrawable(getResources().getDrawable(R.drawable.ic_phone));
        mEmail.setCustomDrawable(getResources().getDrawable(R.drawable.ic_email));
        mBirthday.setCustomDrawable(getResources().getDrawable(R.drawable.ic_cake));

        return view;
    }

    private void request(String endPoint, String token) {
        MemberProfileRequest memberProfileRequest = new MemberProfileRequest(endPoint, token, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Member member = new Gson().fromJson(response.toString(),
                        new TypeToken<Member>(){}.getType());
                populateScreen(member);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG", error.toString());
                getActivity().finish();
            }
        });

        RequestManager.getInstance(getActivity()).addToRequestQueue(memberProfileRequest);
    }

    private void populateScreen(Member member) {
        mAddress.setContent(member.address);
        mPhone.setContent(member.mobile);
        mEmail.setContent(member.email);
        mBirthday.setContent(member.birthday);
    }
}
