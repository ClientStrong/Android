package v3.clientstrong.mainFragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import github.nisrulz.recyclerviewhelper.RVHItemClickListener;
import github.nisrulz.recyclerviewhelper.RVHItemDividerDecoration;
import github.nisrulz.recyclerviewhelper.RVHItemTouchHelperCallback;
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
    private WorkoutListAdapter mAdapter;
    private ArrayList<Workout> workoutList;
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

        // Setup onItemTouchHandler to enable drag and drop , swipe left or right
        ItemTouchHelper.Callback callback = new RVHItemTouchHelperCallback(mAdapter, true, true,
                true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mWorkoutListView);

        // Set the divider in the recyclerview
        mWorkoutListView.addItemDecoration(new RVHItemDividerDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        // Set On Click Listener
        mWorkoutListView.addOnItemTouchListener(mRVHItemClickListener);

        return root;
    }

    private RVHItemClickListener mRVHItemClickListener = new RVHItemClickListener(getActivity(), new RVHItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String value = "Clicked Item " + workoutList.get(position) + " at " + position;

                Log.d("TAG", value);
                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
            }
        });



    private void localRequestForFakeData() throws IOException {
        Reader reader = new InputStreamReader(getActivity().getAssets().open("WorkoutsList.json"));
        workoutList = new Gson().fromJson(reader, new TypeToken<ArrayList<Workout>>(){}.getType());
        populateWorkoutList(workoutList);
    }

    public void populateWorkoutList(ArrayList<Workout> workoutList) {
        mAdapter = new WorkoutListAdapter(this, workoutList, mWorkoutListView);
        mWorkoutListView.setAdapter(mAdapter);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
