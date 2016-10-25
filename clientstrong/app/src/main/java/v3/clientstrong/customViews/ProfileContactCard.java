package v3.clientstrong.customViews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import v3.clientstrong.R;

public class ProfileContactCard extends RelativeLayout {

    View mView;
    TextView mContent;
    ImageView mIcon;

    public ProfileContactCard(Context context) {
        super(context);
        init(context, null);
    }

    public ProfileContactCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        mView = inflate(context, R.layout.cell_contact_info, this);
        mContent = (TextView) mView.findViewById(R.id.content);
        mIcon = (ImageView) mView.findViewById(R.id.icon);
    }

    public void setCustomDrawable(Drawable drawable) {
        mIcon.setImageDrawable(drawable);
    }

    public void setContent(String string) {
        mContent.setText(string);
    }
}