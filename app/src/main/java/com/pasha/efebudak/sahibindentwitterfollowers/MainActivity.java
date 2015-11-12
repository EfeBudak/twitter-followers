package com.pasha.efebudak.sahibindentwitterfollowers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.pasha.efebudak.sahibindentwitterfollowers.fragments.FollowerListFragment;
import com.pasha.efebudak.sahibindentwitterfollowers.models.FollowerListModel;
import com.pasha.efebudak.sahibindentwitterfollowers.models.TokenModel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FollowerListFragment.FollowerListListener {

    /**
     * onSaveInstanceState keys
     */
    private static final String KEY_FOLLOWER_LIST_MODEL = "keyFollowerListModel";
    private static final String TAG_FOLLOWER_LIST_FRAGMENT = "tagFollowerListFragment";

    private static final String KEY_ACCESS_TOKEN_SHARED_PREF = "keyAccessTokenSharedPref";

    private String accessToken;
    private FollowerListModel followerListModel;

    private boolean followerListServiceRequested = false;

    @Bind(R.id.main_frame_layout_container)
    FrameLayout frameLayoutContainer;

    private FollowerListFragment followerListFragment;

    //GetTwitterAccessTokenService is done
    private final BroadcastReceiver broadcastReceiverToken = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            final Bundle bundle = intent.getExtras();
            final TokenModel tokenModel
                    = bundle.getParcelable(
                    GetTwitterAccessTokenService.KEY_TOKEN_MODEL);

            if (tokenModel != null) {

                accessToken = tokenModel.getAccessToken();
                setAccessTokenToSharedPref(accessToken);

                if (followerListServiceRequested) {
                    startService(
                            GetFollowerListService.newIntent(
                                    MainActivity.this, accessToken));
                }

            }

        }
    };

    //GetFollowerListService is done
    private final BroadcastReceiver broadcastReceiverFollowerList = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            final Bundle bundle = intent.getExtras();
            final FollowerListModel followerListModel
                    = bundle.getParcelable(
                    GetFollowerListService.KEY_FOLLOWER_LIST_MODEL);

            if (followerListModel != null) {

                MainActivity.this.followerListModel = followerListModel;
                followerListFragment.updateFollowerListModel(followerListModel);

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAccessTokenFromSharedPref();

        ButterKnife.bind(this);

        if (savedInstanceState == null) {

            addFollowerListFragment();

        } else {

            followerListModel = savedInstanceState.getParcelable(KEY_FOLLOWER_LIST_MODEL);
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverToken,
                new IntentFilter(
                        GetTwitterAccessTokenService.ACCESS_TOKEN_INTENT_NAME));

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverFollowerList,
                new IntentFilter(
                        GetFollowerListService.FOLLOWER_LIST_INTENT_NAME));

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(KEY_FOLLOWER_LIST_MODEL, followerListModel);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        followerListModel = savedInstanceState.getParcelable(KEY_FOLLOWER_LIST_MODEL);

    }

    private void addFollowerListFragment() {

        followerListFragment = FollowerListFragment.newInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(
                R.id.main_frame_layout_container, followerListFragment, TAG_FOLLOWER_LIST_FRAGMENT);

        fragmentTransaction.commit();

    }

    private void setAccessTokenToSharedPref(String accessToken) {

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_ACCESS_TOKEN_SHARED_PREF, accessToken);
        editor.apply();

    }

    private void getAccessTokenFromSharedPref() {

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        accessToken = sharedPref.getString(KEY_ACCESS_TOKEN_SHARED_PREF, "");

        if (TextUtils.isEmpty(accessToken)) {

            startService(
                    GetTwitterAccessTokenService.newIntent(
                            this));

        }

    }

    @Override
    public void onItemClicked(int position) {

        //todo open detail fragment

    }

    @Override
    public void callFollowerListService() {

        if (!TextUtils.isEmpty(accessToken)) {

            startService(
                    GetFollowerListService.newIntent(
                            MainActivity.this, accessToken));

        } else {
            followerListServiceRequested = true;
        }

    }

}
