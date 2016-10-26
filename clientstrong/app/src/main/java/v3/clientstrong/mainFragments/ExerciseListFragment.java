package v3.clientstrong.mainFragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import v3.clientstrong.R;
import v3.clientstrong.adapters.ExerciseListAdapter;
import v3.clientstrong.models.Exercise;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class ExerciseListFragment extends Fragment {

    private static RecyclerView mExerciseListView;
    public interface OnFragmentInteractionListener { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        mExerciseListView = (RecyclerView) root.findViewById(R.id.exercise_list);
        mExerciseListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExerciseListView.setHasFixedSize(true);

//        if (isNetworkConnected())
//            requestExerciseList("exercises");
//        else
            try {
                localRequestForFakeData();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return root;
    }

    private void localRequestForFakeData() throws IOException {
        Reader reader = new InputStreamReader(getActivity().getAssets().open("ExerciseList.json"));
        ArrayList<Exercise> exerciseList = new Gson().fromJson(reader, new TypeToken<ArrayList<Exercise>>(){}.getType());
        populateExerciseList(exerciseList);
    }

    public void populateExerciseList(ArrayList<Exercise> exerciseList) {
        ExerciseListAdapter adapter = new ExerciseListAdapter(this, exerciseList, mExerciseListView);
        mExerciseListView.setAdapter(adapter);
    }




    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
