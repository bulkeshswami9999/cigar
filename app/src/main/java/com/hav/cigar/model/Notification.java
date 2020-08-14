package com.hav.cigar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Notification implements Serializable
{

@SerializedName("message")
@Expose
private ArrayList<Message> message = null;


public ArrayList<Message> getMessage() {
return message;
}

public void setMessage(ArrayList<Message> message) {
this.message = message;
}

}