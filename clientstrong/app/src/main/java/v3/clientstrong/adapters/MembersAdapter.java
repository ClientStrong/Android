package v3.clientstrong.adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import v3.clientstrong.R;
import v3.clientstrong.activities.ProfileScreen;
import v3.clientstrong.mainFragments.MembersListFragment;
import v3.clientstrong.models.Person;

/**
 * Created by runquest.
 * Date: 2016-10-19
 */
public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

    private MembersListFragment mContext;
    private List<Person> mMembersList;
    private RecyclerView mMembersListView;

    public MembersAdapter(MembersListFragment fragment, List<Person> membersList, RecyclerView membersListView) {
        this.mContext = fragment;
        this.mMembersList = membersList;
        this.mMembersListView = membersListView;
    }

    @Override
    public int getItemCount() {
        return mMembersList.size();
    }

    @Override
    public void onBindViewHolder(MemberViewHolder memberViewHolder, int i) {
        Person person = mMembersList.get(i);
        memberViewHolder.setItem(mMembersList.get(i).toString());
        memberViewHolder.mFullName.setText(person.getFullName());
        memberViewHolder.mLetterIcon.setText(person.getFirstNameLetter());
        memberViewHolder.mFavoriteIcon.setBackground(getBackground(person.isFavorite()));
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.member_cell, viewGroup, false);

        return new MemberViewHolder(itemView);
    }

    class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener {
        TextView mFullName;
        TextView mLetterIcon;
        ImageView mFavoriteIcon;

        MemberViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);

            mFullName = (TextView) v.findViewById(R.id.first_name_list);
            mLetterIcon = (TextView) v.findViewById(R.id.profile_image);
            mFavoriteIcon = (ImageView) v.findViewById(R.id.star);
        }

        void setItem(String item) {
//            mItem = item;
        }

        @Override
        public void onClick(View v) {
            int itemPosition = mMembersListView.getChildPosition(v);
            Person item = mMembersList.get(itemPosition);

            Intent intent = new Intent(mContext.getActivity(), ProfileScreen.class).putExtra(Intent.EXTRA_TEXT, item.id);
            mContext.startActivity(intent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
            menu.add(0, v.getId(), 0, "SMS");
        }
    }

    public Drawable getBackground(boolean favorite) {
        int drawable = R.drawable.ic_star_border;
        if (favorite)
            drawable = R.drawable.ic_star;
        return mContext.getResources().getDrawable(drawable);
    }
}
