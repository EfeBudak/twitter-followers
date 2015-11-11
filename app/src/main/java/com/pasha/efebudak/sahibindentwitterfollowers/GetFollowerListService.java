package com.pasha.efebudak.sahibindentwitterfollowers;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;

import com.pasha.efebudak.sahibindentwitterfollowers.models.FollowerListModel;

import java.net.URLEncoder;

import retrofit.RestAdapter;

/**
 * Created by efebudak on 11/11/15.
 */
public class GetFollowerListService extends IntentService {

    public static final String FOLLOWER_LIST_INTENT_NAME = "followerListIntentName";
    public static final String FOLLOWER_LIST_MODEL = "followerListModel";

    private static final String DEFAULT_GET_FOLLOWER_LIST_NAME = "defaultGetFollowerListName";
    private static final String KEY_BEARER_TOKEN = "keyBearerToken";

    private String bearerToken;

    public GetFollowerListService() {
        super(DEFAULT_GET_FOLLOWER_LIST_NAME);
    }

    public GetFollowerListService(String name) {
        super(name);
    }

    /**
     * @param context
     * @return
     */
    public static Intent newIntent(
            Context context,
            String bearerToken) {

        final Intent intent = new Intent(context, GetFollowerListService.class);
        final Bundle bundle = new Bundle();

        bundle.putString(KEY_BEARER_TOKEN, bearerToken);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final Bundle bundle = intent.getExtras();
        bearerToken = bundle.getString(KEY_BEARER_TOKEN);

        final RestAdapter restAdapterTwitter = new RestAdapter.Builder()
                .setEndpoint("https://api.twitter.com/1.1")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        TwitterServices twitterServices = restAdapterTwitter.create(TwitterServices.class);

        FollowerListModel followerListModel;

        try {


            followerListModel
                    = twitterServices.getFollowerList(
                    "Bearer " + bearerToken,
                    "sahibindencom",
                    "30");

        } catch (Exception e) {
            followerListModel = null;
        }

        sendFollowerListModel(followerListModel);

    }


    private void sendFollowerListModel(FollowerListModel followerListModel) {

        Intent intent = new Intent(FOLLOWER_LIST_INTENT_NAME);
        Bundle bundle = new Bundle();

        bundle.putParcelable(FOLLOWER_LIST_MODEL, followerListModel);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
