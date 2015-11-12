package com.pasha.efebudak.sahibindentwitterfollowers.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by efebudak on 11/11/15.
 */
public class FollowerModel implements Parcelable {

    @SerializedName("name")
    String name;
    @SerializedName("friends_count")
    int friendsCount;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("profile_image_url")
    String profileImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public FollowerModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.friendsCount);
        dest.writeString(this.createdAt);
        dest.writeString(this.profileImageUrl);
    }

    protected FollowerModel(Parcel in) {
        this.name = in.readString();
        this.friendsCount = in.readInt();
        this.createdAt = in.readString();
        this.profileImageUrl = in.readString();
    }

    public static final Creator<FollowerModel> CREATOR = new Creator<FollowerModel>() {
        public FollowerModel createFromParcel(Parcel source) {
            return new FollowerModel(source);
        }

        public FollowerModel[] newArray(int size) {
            return new FollowerModel[size];
        }
    };
}
