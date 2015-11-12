package com.pasha.efebudak.sahibindentwitterfollowers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pasha.efebudak.sahibindentwitterfollowers.R;
import com.pasha.efebudak.sahibindentwitterfollowers.adapters.FollowerListAdapter;
import com.pasha.efebudak.sahibindentwitterfollowers.models.FollowerListModel;

/**
 * Created by efebudak on 11/11/15.
 */
public class FollowerListFragment extends Fragment {

    private static final String KEY_FOLLOWER_LIST_MODEL = "keyFollowerListModel";

    private ListView listViewFollowers;

    private FollowerListModel followerListModel;

    private FollowerListListener followerListListener;

    public interface FollowerListListener {

        void onItemClicked(int position);

        void callFollowerListService();

    }

    public static FollowerListFragment newInstance() {

        return new FollowerListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_follower_list, container, false);
        listViewFollowers = (ListView) view.findViewById(R.id.fragment_follower_list_view);

        if (savedInstanceState != null) {
            followerListModel = savedInstanceState.getParcelable(KEY_FOLLOWER_LIST_MODEL);
        }

        if (followerListModel == null) {
            followerListListener.callFollowerListService();
        } else {
            listViewFollowers.setAdapter(
                    new FollowerListAdapter(getActivity(), followerListModel));
        }

        listViewFollowers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                followerListListener.onItemClicked(i);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_FOLLOWER_LIST_MODEL, followerListModel);

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        followerListListener = (FollowerListListener) context;

    }

    @Override
    public void onDetach() {

        followerListListener = null;
        super.onDetach();

    }

    public void updateFollowerListModel(final FollowerListModel followerListModel) {

        this.followerListModel = followerListModel;

        listViewFollowers.setAdapter(
                new FollowerListAdapter(getActivity(), followerListModel));

    }

}
