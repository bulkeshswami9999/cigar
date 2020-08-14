package com.hav.cigar.accept;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.model.NotificationApiResponse;
import com.hav.cigar.model.Payment;
import com.hav.cigar.startup.FrescoApplication;
import com.hav.cigar.startup.MainActivity;
import com.hav.cigar.utility.Constant;

import net.authorize.acceptsdk.AcceptSDKApiClient;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hav.cigar.startup.FrescoApplication.CHANNEL_1_ID;


public class AcceptFragment extends Fragment implements View.OnClickListener, EncryptTransactionCallback {


  private NotificationManagerCompat notificationManager;
  public static final String TAG = "WebCheckoutFragment";
//  private final String CARD_NUMBER = "4111111111111111";
//  private final String EXPIRATION_MONTH = "11";
//  private final String EXPIRATION_YEAR = "2017";
  private final String CVV = "256";
  private final String POSTAL_CODE = "98001";
  private final String CLIENT_KEY = "36D8VGQPxy875TAm2LL7CeT2BrqX2gkA6QJuqeNHhmT7YKXrE4fgccbRLwvt7y2Q";  // replace with your CLIENT KEY


  private final String API_LOGIN_ID = "7hqkYq96tDu"; // replace with your API LOGIN_ID

  private final int MIN_CARD_NUMBER_LENGTH = 13;
  private final int MIN_YEAR_LENGTH = 2;
  private final int MIN_MONTH_LENGTH = 2;
  private final int MIN_CVV_LENGTH = 3;
  private final String YEAR_PREFIX = "20";

  private Button checkoutButton;
  private EditText cardNumberView;
  private EditText monthView;
  private EditText yearView;
  private EditText cvvView;
  private EditText tvName;
  private EditText title;
  private EditText amount;

  private ProgressDialog progressDialog;
  private RelativeLayout responseLayout;
  private RelativeLayout custom_dialog1;

  private String cardNumber;
  private String month;
  private String year;
  private String cvv;

//  private String holderName;

  private AcceptSDKApiClient apiClient;
  FrescoApplication frescoApplication;
  public AcceptFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /*
       build an Accept SDK Api client to make API calls.
       parameters:
         1) Context - current context
         2) AcceptSDKApiClient.Environment - Authorize.net ENVIRONMENT
    */

    try {
      frescoApplication=(FrescoApplication) getActivity().getApplication();
      apiClient = new AcceptSDKApiClient.Builder(getActivity(),
          AcceptSDKApiClient.Environment.SANDBOX).connectionTimeout(
          4000) // optional connection time out in milliseconds
          .build();

    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    notificationManager = NotificationManagerCompat.from(getContext());
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_accept, container, false);
    initialize(view);
    return view;
  }

  private void initialize(View view) {

    cardNumberView = (EditText) view.findViewById(R.id.card_number_view);
    setUpCreditCardEditText();
    monthView = (EditText) view.findViewById(R.id.date_month_view);
    yearView = (EditText) view.findViewById(R.id.date_year_view);
    cvvView = (EditText) view.findViewById(R.id.security_code_view);
    responseLayout = (RelativeLayout) view.findViewById(R.id.response_layout);
    tvName = (EditText) view.findViewById(R.id.holder_name);
    checkoutButton = (Button) view.findViewById(R.id.button_checkout_order);
    checkoutButton.setOnClickListener(this);


  }


    @Override public void onClick(View v) {
    if (!areFormDetailsValid())
      return;
    progressDialog = ProgressDialog.show(getActivity(), this.getString(R.string.progress_title),
        this.getString(R.string.progress_message), true);
    if (responseLayout.getVisibility() == View.VISIBLE) responseLayout.setVisibility(View.GONE);

    try {
      EncryptTransactionObject transactionObject = prepareTransactionObject();


      /*
        Make a call to get Token API
        parameters:
          1) EncryptTransactionObject - The transactionObject for the current transaction
          2) callback - callback of transaction
       */
      apiClient.getTokenWithRequest(transactionObject, this);
    } catch (NullPointerException e) {
      // Handle exception transactionObject or callback is null.
      Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

      e.printStackTrace();
    }




  }

  /**
   * prepares a transaction object with dummy data to be used with the Gateway transactions
   */
  private EncryptTransactionObject prepareTransactionObject() {
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.
            createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);

    // create a transaction object by calling the predefined api for creation
    return TransactionObject.
        createTransactionObject(
            TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
        .cardData(prepareCardDataFromFields()) // card data to get Token
        .merchantAuthentication(merchantAuthentication).build();
  }

  private EncryptTransactionObject prepareTestTransactionObject() {
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.
            createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);

    // create a transaction object by calling the predefined api for creation
    return EncryptTransactionObject.
        createTransactionObject(
            TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
        .cardData(prepareTestCardData()) // card data to prepare token
        .merchantAuthentication(merchantAuthentication).build();
  }

  private CardData prepareTestCardData() {
    return new CardData.Builder(cardNumber, month, year).cvvCode(cvv)//cvv
        .cardHolderName("")
        .build();
  }

/* ---------------------- Callback Methods - Start -----------------------*/

  @Override public void onEncryptionFinished(EncryptTransactionResponse response) {
    hideSoftKeyboard();
//    if (responseLayout.getVisibility() != View.VISIBLE) responseLayout.setVisibility(View.VISIBLE);

     Log.d("==payment token",""+response.getDataDescriptor() + "\n" + getString(
             R.string.data_value) + response.getDataValue());
          doPayment();
  }

  private  void callNotificationApi()
  {

      APIInterface mInterface= APIClient.getClient().create(APIInterface.class);
      Call<NotificationApiResponse> call= mInterface.sendNotification(frescoApplication.getPreferences().getToken("user_id",""));
      call.enqueue(new Callback<NotificationApiResponse>()
      {
          @SuppressLint("SetTextI18n")
          @Override
          public void onResponse(Call<NotificationApiResponse> call, Response<NotificationApiResponse> response)
          {
              if(response.isSuccessful())
              {
                Toast.makeText(getActivity(), ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing()) progressDialog.dismiss();
                {
                  // -- Call Dialog / Show Dialog


                  AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                 //builder.setTitle("");
                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                  View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog1, (ViewGroup) getView(), false);
                  final TextView title = viewInflated.findViewById(R.id.title);
                  final TextView amount = viewInflated.findViewById(R.id.amount);
                  final Button btn_ok=viewInflated.findViewById(R.id.btn_ok);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                  builder.setView(viewInflated);
                  //Title and Amount
                  title.setText(response.body().getData().getMessage());
                  amount.setText(Constant.pay);

                 //Set my button
                  btn_ok.setOnClickListener(new View.OnClickListener()
                  {
                    @Override
                    public void onClick(View v)
                    {
                      Intent i = new Intent(getActivity(), MainActivity.class);
                      // set the new task and clear flags
                      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(i);
                      getActivity().finish();
                    }
                  });
                  builder.show();
                  // Set up the buttons
//                  builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                      dialog.dismiss();
//                      Intent i = new Intent(getActivity(), MainActivity.class);
//                      // set the new task and clear flags
//                      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                      startActivity(i);
//                      getActivity().finish();
//                    }
//                  });

                };

              }
          }

        @Override
        public void onFailure(Call<NotificationApiResponse> call, Throwable t)
        {
          Log.e("Call Notification Api",t.toString());
        }

      });

  }

  private void doPayment()
  {
      APIInterface mInterface= APIClient.getClient().create(APIInterface.class);
      String user_id= frescoApplication.getPreferences().getToken("user_id","");
      String pay_now="1";
      String cvv_=String.valueOf(cvv);
      String ids=Constant.ORDER_IDS;
      Call<Payment> call= mInterface.doPayment(cardNumber,year,month,Constant.pay,pay_now,cvv_,ids,user_id);
      call.enqueue(new Callback<Payment>() {
          @Override
          public void onResponse(Call<Payment> call, Response<Payment> response) {
              if(response.body().getStatus().equals("200"))
              {

//                Notification notification= new NotificationCompat.Builder(getContext(), CHANNEL_1_ID)
//                        .setSmallIcon(R.drawable.ic_menu_notifications)
//                        .setContentTitle(response.body().getMessage())
//                        .setContentText(response.body().getCustomer_id())
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                        .build();
//
//                notificationManager.notify(1, notification);
                callNotificationApi();
              } else {
                  Toast.makeText(getActivity() ,""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

              }
          }

          @Override
          public void onFailure(Call<Payment> call, Throwable t) {
            Log.e("Exception payment",t.toString());

          }
      });
  }
  @Override public void onErrorReceived(ErrorTransactionResponse errorResponse) {
    hideSoftKeyboard();
    if (responseLayout.getVisibility() != View.VISIBLE) responseLayout.setVisibility(View.VISIBLE);
    if (progressDialog.isShowing()) progressDialog.dismiss();

    Message error = errorResponse.getFirstErrorMessage();
    String errorString = getString(R.string.code) + error.getMessageCode() + "\n" +
        getString(R.string.message) + error.getMessageText();
      Toast.makeText(frescoApplication, ""+errorString, Toast.LENGTH_SHORT).show();
   Log.e("==payment error",errorString);
  }

/* ---------------------- Callback Methods - End -----------------------*/

  public void hideSoftKeyboard() {
    InputMethodManager keyboard =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (getActivity() != null && getActivity().getCurrentFocus() != null) {
      keyboard.hideSoftInputFromInputMethod(getActivity().getCurrentFocus().getWindowToken(), 0);
    }
  }

  private boolean areFormDetailsValid() {
    cardNumber = cardNumberView.getText().toString().replace(" ", "");
    month = monthView.getText().toString();
    cvv = cvvView.getText().toString();
    year = yearView.getText().toString();
//    holderName = tvName.getText().toString();

    if (isEmptyField()) {
      checkoutButton.startAnimation(
          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
      Toast.makeText(getActivity(), "Empty fields", Toast.LENGTH_LONG).show();
      return false;
    }

    year = YEAR_PREFIX + yearView.getText().toString();

    return validateFields();
  }

  private boolean isEmptyField() {
    return (cardNumber != null && cardNumber.isEmpty()) || (month != null && month.isEmpty()) || (
        year != null
            && year.isEmpty()) || (cvv != null && cvv.isEmpty());// ||  (holderName != null && holderName.isEmpty()
  }

  private boolean validateFields() {
    if (cardNumber.length() < MIN_CARD_NUMBER_LENGTH) {
      cardNumberView.requestFocus();
      cardNumberView.setError(getString(R.string.invalid_card_number));
      checkoutButton.startAnimation(
          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
      return false;
    }
    int monthNum = Integer.parseInt(month);
    if (monthNum < 1 || monthNum > 12) {
      monthView.requestFocus();
      monthView.setError(getString(R.string.invalid_month));
      checkoutButton.startAnimation(
          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
      return false;
    }
    if (month.length() < MIN_MONTH_LENGTH) {
      monthView.requestFocus();
      monthView.setError(getString(R.string.two_digit_month));
      checkoutButton.startAnimation(
          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
      return false;
    }
    if (year.length() < MIN_YEAR_LENGTH) {
      yearView.requestFocus();
      yearView.setError(getString(R.string.invalid_year));
      checkoutButton.startAnimation(
          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
      return false;
    }
    if (cvv.length() < MIN_CVV_LENGTH) {
      cvvView.requestFocus();
      cvvView.setError(getString(R.string.invalid_cvv));
      checkoutButton.startAnimation(
          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
      return false;
    }

//    if (holderName.length()==0) {
//      tvName.requestFocus();
//      tvName.setError(getString(R.string.invalid_cvv));
//      checkoutButton.startAnimation(
//          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
//      return false;
//    }
    return true;
  }

  private void setUpCreditCardEditText() {
    cardNumberView.addTextChangedListener(new TextWatcher() {
      private boolean spaceDeleted;

      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // check if a space was deleted
        CharSequence charDeleted = s.subSequence(start, start + count);
        spaceDeleted = " ".equals(charDeleted.toString());
      }

      public void afterTextChanged(Editable editable) {
        // disable text watcher
        cardNumberView.removeTextChangedListener(this);

        // record cursor position as setting the text in the textview
        // places the cursor at the end
        int cursorPosition = cardNumberView.getSelectionStart();
        String withSpaces = formatText(editable);
        cardNumberView.setText(withSpaces);
        // set the cursor at the last position + the spaces added since the
        // space are always added before the cursor
        cardNumberView.setSelection(cursorPosition + (withSpaces.length() - editable.length()));

        // if a space was deleted also deleted just move the cursor
        // before the space
        if (spaceDeleted) {
          cardNumberView.setSelection(cardNumberView.getSelectionStart() - 1);
          spaceDeleted = false;
        }

        // enable text watcher
        cardNumberView.addTextChangedListener(this);
      }

      private String formatText(CharSequence text) {
        StringBuilder formatted = new StringBuilder();
        int count = 0;
        for (int i = 0; i < text.length(); ++i) {
          if (Character.isDigit(text.charAt(i))) {
            if (count % 4 == 0 && count > 0) formatted.append(" ");
            formatted.append(text.charAt(i));
            ++count;
          }
        }
        return formatted.toString();
      }
    });
  }

  private CardData prepareCardDataFromFields() {

    return new CardData.Builder(cardNumber, month, year).cvvCode(cvv) //CVV Code is optional
        .build();
  }
}
