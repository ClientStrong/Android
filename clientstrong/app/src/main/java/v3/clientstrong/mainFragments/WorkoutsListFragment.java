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
import v3.clientstrong.adapters.WorkoutListAdapter;
import v3.clientstrong.models.Workout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class WorkoutsListFragment extends Fragment {
    private static RecyclerView mWorkoutListView;
    public interface OnFragmentInteractionListener { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workouts_list, container, false);
        mWorkoutListView = (RecyclerView) root.findViewById(R.id.workouts_list);
        mWorkoutListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWorkoutListView.setHasFixedSize(true);

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
        Reader reader = new InputStreamReader(getActivity().getAssets().open("WorkoutsList.json"));
        ArrayList<Workout> workoutList = new Gson().fromJson(reader, new TypeToken<ArrayList<Workout>>(){}.getType());
        populateWorkoutList(workoutList);
    }

    public void populateWorkoutList(ArrayList<Workout> workoutList) {
        WorkoutListAdapter adapter = new WorkoutListAdapter(this, workoutList, mWorkoutListView);
        mWorkoutListView.setAdapter(adapter);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
