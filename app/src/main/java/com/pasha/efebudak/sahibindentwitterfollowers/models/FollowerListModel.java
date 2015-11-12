package com.pasha.efebudak.sahibindentwitterfollowers.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by efebudak on 11/11/15.
 */
public class FollowerListModel implements Parcelable {

    @SerializedName("users")
    List<FollowerModel> followerModelList;

    public List<FollowerModel> getFollowerModelList() {
        return followerModelList;
    }

    public void setFollowerModelList(List<FollowerModel> followerModelList) {
        this.followerModelList = followerModelList;
    }

    public FollowerListModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(followerModelList);
    }

    protected FollowerListModel(Parcel in) {
        this.followerModelList = in.createTypedArrayList(FollowerModel.CREATOR);
    }

    public static final Creator<FollowerListModel> CREATOR = new Creator<FollowerListModel>() {
        public FollowerListModel createFromParcel(Parcel source) {
            return new FollowerListModel(source);
        }

        public FollowerListModel[] newArray(int size) {
            return new FollowerListModel[size];
        }
    };
}
