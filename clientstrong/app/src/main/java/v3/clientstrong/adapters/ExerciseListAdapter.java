package v3.clientstrong.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.util.ArrayList;
import java.util.List;

import v3.clientstrong.R;
import v3.clientstrong.mainFragments.ExerciseListFragment;
import v3.clientstrong.models.Exercise;

/**
 * Created by runquest.
 * Date: 2016-10-19
 */
public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

    private static String TAG = ExerciseListAdapter.class.getSimpleName();

    private ExerciseListFragment mContext;
    private List<Exercise> mExerciseList;
    private RecyclerView mExerciseListView;

    public ExerciseListAdapter(ExerciseListFragment fragment, ArrayList<Exercise> exerciseList, RecyclerView exerciseListView) {
        this.mContext = fragment;
        this.mExerciseList = exerciseList;
        this.mExerciseListView = exerciseListView;
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        Exercise exercise = mExerciseList.get(i);
        exerciseViewHolder.mExerciseName.setText(exercise.name);
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cell_exercise_list, viewGroup, false);
        return new ExerciseViewHolder(itemView);
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mExerciseName;

        ExerciseViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mExerciseName = (TextView) v.findViewById(R.id.exercise_name);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = mExerciseListView.getChildPosition(v);
            Exercise exerciseItem = mExerciseList.get(itemPosition);

            new BottomDialog.Builder(mContext.getActivity())
                    .setTitle(exerciseItem.name)
                    .setContent(exerciseItem.description)
                    .setPositiveBackgroundColorResource(R.color.colorAccent)
                    .setPositiveText("OK")
                    .show();
        }
    }
}
