package com.hav.cigar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationListingApi implements Serializable
{

@SerializedName("status")
@Expose
public String status;
@SerializedName("data")
@Expose
public Notification notificationListingResponse;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public Notification getNotificationListingResponse() {
return notificationListingResponse;
}

public void setNotificationListingResponse(Notification notificationListingResponse) {
this.notificationListingResponse = notificationListingResponse;
}

}