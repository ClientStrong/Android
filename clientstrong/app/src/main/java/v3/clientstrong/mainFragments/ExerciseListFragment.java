package v3.clientstrong.mainFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import v3.clientstrong.R;
import v3.clientstrong.customViews.ProfileContactCard;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseListFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public ExerciseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ProfileContactCard profileContactCard1 = (ProfileContactCard) view.findViewById(R.id.myView1);
        ProfileContactCard profileContactCard2 = (ProfileContactCard) view.findViewById(R.id.myView2);

        profileContactCard1.setCustomDrawable(getResources().getDrawable(R.drawable.ic_email));
        profileContactCard2.setCustomDrawable(getResources().getDrawable(R.drawable.ic_star));

        profileContactCard1.setContent("Content 1");
        profileContactCard2.setContent("Something has been written");

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
