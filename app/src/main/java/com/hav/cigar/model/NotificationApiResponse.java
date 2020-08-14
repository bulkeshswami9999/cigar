package com.hav.cigar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationApiResponse  implements Serializable
{
    @SerializedName("status")
    @Expose
     String status;
    @SerializedName("data")
    @Expose
    NotificationData data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

}
