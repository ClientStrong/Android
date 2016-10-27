package v3.clientstrong;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import v3.clientstrong.models.Exercise;

public class ExerciseItemFragment extends DialogFragment {

    private static String TAG = ExerciseItemFragment.class.getSimpleName();
    private static String EXERCISE = "Exercise";

    private Exercise mExerciseItem;

    public static Bundle newArgsBundle(Exercise exerciseItem) {

        Bundle args = new Bundle();
        args.putSerializable(EXERCISE, exerciseItem);
        return args;
    }

    public static ExerciseItemFragment newInstance(Exercise exerciseItem) {

        ExerciseItemFragment exerciseItemFragment = new ExerciseItemFragment();
        Bundle args = newArgsBundle(exerciseItem);
        exerciseItemFragment.setArguments(args);
        return exerciseItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mExerciseItem = (Exercise) getArguments().getSerializable(EXERCISE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageLoader.ImageCache imageCache = new BitmapLruCache();
        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()), imageCache);

        View view = inflater.inflate(R.layout.fragment_exercise_item, container, false);
        NetworkImageView exerciseImage = (NetworkImageView) view.findViewById(R.id.exercise_image);
        TextView exerciseName = (TextView) view.findViewById(R.id.exercise_name);

        exerciseImage.setImageUrl(mExerciseItem.image_url, imageLoader);
        exerciseName.setText(mExerciseItem.name);

        getDialog().setTitle("Simple Dialog");
        return view;
    }
}