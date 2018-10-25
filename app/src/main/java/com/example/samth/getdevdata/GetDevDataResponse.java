package com.example.samth.getdevdata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDevDataResponse {

    @SerializedName("items")
    public List<UserData> dataItems;

    public List<UserData> getDataItems(){
        return dataItems;
    }

    public void setDataItems(List<UserData> dataItems) {
        this.dataItems = dataItems;
    }
}
