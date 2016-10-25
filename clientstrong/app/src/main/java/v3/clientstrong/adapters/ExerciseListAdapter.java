package v3.clientstrong.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener {
        TextView mExerciseName;

        ExerciseViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);

            mExerciseName = (TextView) v.findViewById(R.id.exercise_name);
        }

        void setItem(String item) {
//            mItem = item;
        }

        @Override
        public void onClick(View v) {
//            int itemPosition = mExerciseListView.getChildPosition(v);
//            Member item = mExerciseList.get(itemPosition);
//
//            Intent intent = new Intent(mContext.getActivity(), MembersProfile.class).putExtra(Intent.EXTRA_TEXT, item.id);
//            mContext.startActivity(intent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderTitle("Select The Action");
//            menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
//            menu.add(0, v.getId(), 0, "SMS");
        }
    }

//    public Drawable getBackground(boolean favorite) {
////        int drawable = R.drawable.ic_star_border;
////        if (favorite)
////            drawable = R.drawable.ic_star;
//        return mContext.getResources().getDrawable(drawable);
//    }
}
