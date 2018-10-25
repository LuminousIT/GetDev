package com.example.samth.getdevdata;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("login")
    String mName;

    @SerializedName("html_url")
    String mRank;

    @SerializedName("avatar_url")
    String imageView;

    public UserData(String name, String rank, String img) {
        this.mName = name;
        this.mRank = rank;
        this.imageView = img;
    }

    public String getmName() {
        return mName;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmRank() {
        return mRank;
    }

    public void setmRank(String mRank) {
        this.mRank = mRank;
    }
}
