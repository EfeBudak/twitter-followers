package com.pasha.efebudak.sahibindentwitterfollowers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pasha.efebudak.sahibindentwitterfollowers.models.TokenModel;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_ACCESS_TOKEN = "keyAccessToken";

    private String accessToken;
    private final BroadcastReceiver broadcastReceiverToken = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            final Bundle bundle = intent.getExtras();
            final TokenModel tokenModel
                    = bundle.getParcelable(
                    GetTwitterAccessTokenService.KEY_TOKEN_MODEL);

            if (tokenModel != null) {

                Log.d("tokeeeen", "accesstoken: " + tokenModel.accessToken);
                accessToken = tokenModel.accessToken;
                //todo get sahibindencom followers

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(
                GetTwitterAccessTokenService.newIntent(
                        this));

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverToken,
                new IntentFilter(
                        GetTwitterAccessTokenService.ACCESS_TOKEN_INTENT_NAME));

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString(KEY_ACCESS_TOKEN, accessToken);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        accessToken = savedInstanceState.getString(KEY_ACCESS_TOKEN);

    }

    @Override
    protected void onDestroy() {



        super.onDestroy();

    }
}
