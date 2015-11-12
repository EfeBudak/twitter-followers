package com.pasha.efebudak.sahibindentwitterfollowers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasha.efebudak.sahibindentwitterfollowers.R;
import com.pasha.efebudak.sahibindentwitterfollowers.models.FollowerModel;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by efebudak on 12/11/15.
 */
public class FollowerDetailFragment extends Fragment {

    private static final String KEY_FOLLOWER_MODEL = "keyFollowerModel";
    private FollowerModel followerModel;

    @Bind(R.id.fragment_follower_detail_image_view_profile)
    ImageView imageViewProfile;
    @Bind(R.id.fragment_follower_detail_text_view_name)
    TextView textViewName;
    @Bind(R.id.fragment_follower_detail_text_view_created_at)
    TextView textViewCreatedAt;
    @Bind(R.id.fragment_follower_detail_text_view_friend_count)
    TextView textViewFriendCount;

    public static FollowerDetailFragment newInstance(FollowerModel followerModel) {

        final FollowerDetailFragment followerDetailFragment = new FollowerDetailFragment();
        final Bundle bundle = new Bundle();

        bundle.putParcelable(KEY_FOLLOWER_MODEL, followerModel);
        followerDetailFragment.setArguments(bundle);

        return followerDetailFragment;

    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        followerModel = getArguments().getParcelable(KEY_FOLLOWER_MODEL);

        final View view
                = inflater.inflate(R.layout.fragment_follower_detail, container, false);
        ButterKnife.bind(this, view);

        setDetailData();

        return view;
    }

    private void setDetailData() {

        Picasso.with(getActivity())
                .load(followerModel.getProfileImageUrl())
                .resize(250, 250)
                .into(imageViewProfile);
        textViewName.setText(followerModel.getName());
        textViewCreatedAt.setText(followerModel.getCreatedAt());
        textViewFriendCount.setText(String.valueOf(followerModel.getFriendsCount()));

    }
}
