package com.pasha.efebudak.sahibindentwitterfollowers;

import com.pasha.efebudak.sahibindentwitterfollowers.models.TokenModel;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by efebudak on 10/11/15.
 */
public interface TwitterServices {

    @FormUrlEncoded
    @POST("/oauth2/token")
    TokenModel getAccessToken(
            @Header("Authorization") String authorization,
            @Field("grant_type") String grantType);

}
