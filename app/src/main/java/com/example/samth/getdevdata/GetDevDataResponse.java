package com.example.samth.getdevdata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDevDataResponse {

    @SerializedName("items")
    public List<GetDevData> dataItems;

    public List<GetDevData> getDataItems(){
        return dataItems;
    }

    public void setDataItems(List<GetDevData> dataItems) {
        this.dataItems = dataItems;
    }
}
