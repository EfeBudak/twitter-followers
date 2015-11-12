package com.pasha.efebudak.sahibindentwitterfollowers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pasha.efebudak.sahibindentwitterfollowers.R;
import com.pasha.efebudak.sahibindentwitterfollowers.models.FollowerListModel;

/**
 * Created by efebudak on 11/11/15.
 */
public class FollowerListAdapter extends BaseAdapter {

    private Context context;
    private FollowerListModel followerListModel;

    public FollowerListAdapter(
            Context context,
            FollowerListModel followerListModel) {

        this.context = context;
        this.followerListModel = followerListModel;
    }

    @Override
    public int getCount() {
        return followerListModel.getFollowerModelList().size();
    }

    @Override
    public Object getItem(int i) {
        return followerListModel.getFollowerModelList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {

            final LayoutInflater layoutInflater
                    = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.follower_list_item, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.textViewFollowerName
                    = (TextView) view.findViewById(R.id.follower_list_item_text_view);

            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textViewFollowerName.setText(
                followerListModel.getFollowerModelList().get(i).getName());

        return view;
    }

    static class ViewHolder {
        TextView textViewFollowerName;
    }

}
