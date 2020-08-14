package com.hav.cigar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hav.cigar.accept.AcceptFragment;
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

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT_CHECKOUT = "TAG_FRAGMENT_CHECKOUT";
    private ImageView ivBack;
    String pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ivBack=(ImageView)findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        launchAcceptFragment();
        Intent intent=getIntent();
        if(intent.getStringExtra("pay")!=null)
        {
            pay =intent.getStringExtra("pay");
            Constant.pay=pay;
        }

    }
    private void launchAcceptFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AcceptFragment checkoutFragment =
                (AcceptFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_CHECKOUT);
        if (checkoutFragment == null) {
            checkoutFragment = new AcceptFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.layout_container, checkoutFragment, TAG_FRAGMENT_CHECKOUT)
                    .commit();
        }
    }
}
