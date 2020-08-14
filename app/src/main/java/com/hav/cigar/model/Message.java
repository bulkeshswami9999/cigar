package com.hav.cigar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message implements Serializable
{

@SerializedName("id")
@Expose
private String id;
@SerializedName("customer_id")
@Expose
private String customerId;
@SerializedName("product_id")
@Expose
private String productId;
@SerializedName("transaction_id")
@Expose
private String transactionId;
@SerializedName("auth_code")
@Expose
private String authCode;
@SerializedName("response_code")
@Expose
private String responseCode;
@SerializedName("amount")
@Expose
private String amount;
@SerializedName("payment_status")
@Expose
private String paymentStatus;
@SerializedName("payment_response")
@Expose
private String paymentResponse;
@SerializedName("create_at")
@Expose
private String createAt;


public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getProductId() {
return productId;
}

public void setProductId(String productId) {
this.productId = productId;
}

public String getTransactionId() {
return transactionId;
}

public void setTransactionId(String transactionId) {
this.transactionId = transactionId;
}

public String getAuthCode() {
return authCode;
}

public void setAuthCode(String authCode) {
this.authCode = authCode;
}

public String getResponseCode() {
return responseCode;
}

public void setResponseCode(String responseCode) {
this.responseCode = responseCode;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getPaymentStatus() {
return paymentStatus;
}

public void setPaymentStatus(String paymentStatus) {
this.paymentStatus = paymentStatus;
}

public String getPaymentResponse() {
return paymentResponse;
}

public void setPaymentResponse(String paymentResponse) {
this.paymentResponse = paymentResponse;
}

public String getCreateAt() {
return createAt;
}

public void setCreateAt(String createAt) {
this.createAt = createAt;
}

}