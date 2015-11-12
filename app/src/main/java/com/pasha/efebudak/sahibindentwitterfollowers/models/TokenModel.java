package com.pasha.efebudak.sahibindentwitterfollowers.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by efebudak on 10/11/15.
 */
public class TokenModel implements Parcelable {

    @SerializedName("token_type")
    String tokenType;
    @SerializedName("access_token")
    String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tokenType);
        dest.writeString(this.accessToken);
    }

    public TokenModel() {
    }

    protected TokenModel(Parcel in) {
        this.tokenType = in.readString();
        this.accessToken = in.readString();
    }

    public static final Parcelable.Creator<TokenModel> CREATOR = new Parcelable.Creator<TokenModel>() {
        public TokenModel createFromParcel(Parcel source) {
            return new TokenModel(source);
        }

        public TokenModel[] newArray(int size) {
            return new TokenModel[size];
        }
    };
}
