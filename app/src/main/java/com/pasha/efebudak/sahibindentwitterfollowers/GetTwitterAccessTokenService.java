package com.pasha.efebudak.sahibindentwitterfollowers;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;

import com.pasha.efebudak.sahibindentwitterfollowers.models.TokenModel;

import java.net.URLEncoder;

import retrofit.RestAdapter;

/**
 * Created by efebudak on 10/11/15.
 */
public class GetTwitterAccessTokenService extends IntentService {

    public static final String ACCESS_TOKEN_INTENT_NAME = "accessTokenIntentName";
    public static final String KEY_TOKEN_MODEL = "keyTokenModel";

    private static final String DEFAULT_SERVICE_NAME = "getTwitterAccessTokenService";

    private static final String CONSUMER_KEY = "ioSlZmCm4Is5wPyAba6iOwPEJ";
    private static final String CONSUMER_SECRET = "2L3M4OjswltizueshfTjV45wMrMFOaHjlEeCmfYECZnQfdfCtI";

    private static final String END_POINT_URL = "https://api.twitter.com";
    private static final String AUTHENTICATION_TOKEN_TYPE = "Basic ";
    private static final String GRANT_TYPE_VALUE = "client_credentials";
    private static final String UTF_8 = "UTF-8";

    public GetTwitterAccessTokenService() {
        super(DEFAULT_SERVICE_NAME);
    }

    public GetTwitterAccessTokenService(String name) {
        super(name);
    }

    /**
     * @param context
     * @return
     */
    public static Intent newIntent(
            Context context) {

        return new Intent(context, GetTwitterAccessTokenService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final RestAdapter restAdapterTwitter = new RestAdapter.Builder()
                .setEndpoint(END_POINT_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        TwitterServices twitterServices = restAdapterTwitter.create(TwitterServices.class);

        TokenModel tokenModel;
        String base64EncodedString;

        try {

            String encodedConsumerKey
                    = URLEncoder.encode(CONSUMER_KEY, UTF_8);
            String encodedConsumerSecret
                    = URLEncoder.encode(CONSUMER_SECRET, UTF_8);
            String authString = encodedConsumerKey + ":" + encodedConsumerSecret;
            base64EncodedString
                    = Base64.encodeToString(authString.getBytes(UTF_8), Base64.NO_WRAP);

            tokenModel
                    = twitterServices.getAccessToken(
                    AUTHENTICATION_TOKEN_TYPE + base64EncodedString,
                    GRANT_TYPE_VALUE);

        } catch (Exception e) {
            tokenModel = null;
        }

        sendToken(tokenModel);

    }

    private void sendToken(TokenModel tokenModel) {

        Intent intent = new Intent(ACCESS_TOKEN_INTENT_NAME);
        Bundle bundle = new Bundle();

        bundle.putParcelable(KEY_TOKEN_MODEL, tokenModel);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
