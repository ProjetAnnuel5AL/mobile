package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lechampalamaison.loc.lechampalamaison.Fragment.CartFragment;
import com.lechampalamaison.loc.lechampalamaison.Model.CartItem;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.Order;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.FindAddressReponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.OrderResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.OrderClient;
import com.lechampalamaison.loc.lechampalamaison.api.service.UserClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lechampalamaison.loc.lechampalamaison.Activity.LoginActivity.PREFS_NAME_USER;

public class DeliveryActivity extends AppCompatActivity {

    public static int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Configuration.PAYPAL_CLIENT_ID);

    private Double amount = 0.0;

    SharedPreferences sharedpreferences;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    OrderClient orderClient = retrofit.create(OrderClient.class);
    UserClient userClient = retrofit.create(UserClient.class);

    private RadioButton rb_personnalAddress;
    private RadioButton rb_otherAddress;
    private EditText et_lastname;
    private EditText et_firstname;
    private RadioButton rb_femaleSexUser;
    private EditText et_addressUser;
    private EditText et_cityUser;
    private EditText et_cpUser;
    Button btnPaypal ;
    public static Activity fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        fa = this;
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        rb_personnalAddress = findViewById(R.id.rb_personnalAddress);
        rb_otherAddress = findViewById(R.id.rb_otherAddress);

        Call<FindAddressReponse> call = userClient.findAddress(loginUser, token);

        call.enqueue(new Callback<FindAddressReponse>() {
            @Override
            public void onResponse(Call<FindAddressReponse> call, Response<FindAddressReponse> response) {
                if(response.body().getCode() == 0){
                    if(response.body().getResult().getLastNameUser() == null ||response.body().getResult().getLastNameUser().equals("")){
                        rb_personnalAddress.setChecked(false);
                        rb_personnalAddress.setVisibility(View.INVISIBLE);
                        rb_otherAddress.setChecked(true);
                        rb_otherAddress.setVisibility(View.INVISIBLE);
                    }
                }else{
                    //Toast.makeText(getApplicationContext(), "Erreur lors du chargement de l'adresse", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<FindAddressReponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnPaypal = findViewById(R.id.btn_continueOrder);

        rb_personnalAddress = findViewById(R.id.rb_personnalAddress);
        et_lastname = findViewById(R.id.et_lastnameDelivery);
        et_firstname = findViewById(R.id.et_firstnameDelivery);
        rb_femaleSexUser = findViewById(R.id.rb_femaleSexeDelivery);
        et_addressUser = findViewById(R.id.et_addressDelivery);
        et_cityUser = findViewById(R.id.et_cityDelivery);
        et_cpUser = findViewById(R.id.et_cpDelivery);

        btnPaypal.setOnClickListener((View view) -> processPayment());
    }

    @Override
    protected void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmation != null) {
                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString(4);

                        JSONObject details = new JSONObject(paymentConfirmation.toJSONObject().toString());
                        String paymentId = details.getJSONObject("response").getString("id");

                        sharedpreferences = this.getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
                        String login = sharedpreferences.getString("login", "");
                        String token = sharedpreferences.getString("token", "");

                        List<Order.Cart> cartList = new ArrayList<>();

                        for (CartItem item : CartFragment.itemList) {

                            Order.Cart cartElement = new Order().new Cart(item.getItem().getId(), item.getItem().getQte(),
                                    item.getItem().getQteMax(), item.getItem().getUnit(), item.getItem().getCategory(),
                                    item.getItem().getProduct(), item.getItem().getTitle(), item.getItem().getPrixU(), item.getItem().getShippingCost(),
                                    item.getItem().getDeliveryTime(), item.getItem().getIdDeliveryItem(), "");
                            cartList.add(cartElement);


                        }


                        Order.PaymentDetail paymentDetail = new Order().new PaymentDetail(paymentId);

                        if (rb_personnalAddress.isChecked()) {
                            Call<FindAddressReponse> call = userClient.findAddress(login, token);
                            call.enqueue(new Callback<FindAddressReponse>() {
                                @Override
                                public void onResponse(Call<FindAddressReponse> call, Response<FindAddressReponse> response) {
                                    String lastName = response.body().getResult().getLastNameUser();
                                    String firstName = response.body().getResult().getFirstNameUser();
                                    String sex = response.body().getResult().getSexUser();
                                    String address = response.body().getResult().getAddressUser();
                                    String city = response.body().getResult().getCityUser();
                                    String cp = response.body().getResult().getCpUser();

                                    Order.DeliveryAddress deliveryAddress = new Order().new DeliveryAddress(lastName, firstName, sex, address, city, cp);

                                    Order order = new Order(login, token, cartList, paymentDetail, deliveryAddress);

                                    Call<OrderResponse> callOrder = orderClient.saveOrder(order);
                                    callOrder.enqueue(new Callback<OrderResponse>() {
                                        @Override
                                        public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                            if(response.body().getCode() == 0) {
                                                startActivity(new Intent(getApplicationContext(), PaymentDetailsActivity.class)
                                                        .putExtra("PaymentDetails", paymentDetails)
                                                        .putExtra("PaymentAmount", amount));
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Le paiement n'a pas été approuvé, vérifiez votre solde", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OrderResponse> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "La commande n'a pas pu être enregistrée", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<FindAddressReponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Une erreur est survenue", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            String lastName = et_lastname.getText().toString();
                            String firstName = et_firstname.getText().toString();
                            String sex;
                            String address = et_addressUser.getText().toString();
                            String city = et_cityUser.getText().toString();
                            String cp = et_cpUser.getText().toString();

                            if (rb_femaleSexUser.isChecked()) {
                                sex = "F";
                            } else {
                                sex = "M";
                            }

                            Order.DeliveryAddress deliveryAddress = new Order().new DeliveryAddress(lastName, firstName, sex, address, city, cp);

                            Order order = new Order(login, token, cartList, paymentDetail, deliveryAddress);

                            Call<OrderResponse> callOrder = orderClient.saveOrder(order);
                            callOrder.enqueue(new Callback<OrderResponse>() {
                                @Override
                                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                    if(response.body().getCode() == 0) {
                                        startActivity(new Intent(getApplicationContext(), PaymentDetailsActivity.class)
                                                .putExtra("PaymentDetails", paymentDetails)
                                                .putExtra("PaymentAmount", amount));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Le paiement n'a pas été approuvé, vérifiez votre solde", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "La commande n'a pas pu être enregistrée", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void processPayment() {

        sharedpreferences = getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
        String jsonCartSaved = sharedpreferences.getString("cart", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        List<CartItem> itemExistant = gson.fromJson(jsonCartSaved, type);

        double totalPaypal = 0.0;

        for(int i = 0; i<itemExistant.size(); i++){
            int qteTest = itemExistant.get(i).getQuantity();
            totalPaypal += (itemExistant.get(i).getItem().getPrixU() * qteTest) + itemExistant.get(i).getItem().getShippingCost();
        }

        NumberFormat format= NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);


        amount = totalPaypal;

        if (!rb_personnalAddress.isChecked()) {
            String lastName = et_lastname.getText().toString();
            String firstName = et_firstname.getText().toString();
            String address = et_addressUser.getText().toString();
            String city = et_cityUser.getText().toString();
            String cp = et_cpUser.getText().toString();

            if (lastName.equals("") || firstName.equals("") || address.equals("") || city.equals("") || cp.equals("")) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            } else {
                btnPaypal.setEnabled(false);
                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "EUR", "Total", PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);

            }
        } else {
                btnPaypal.setEnabled(false);

                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "EUR", "Total : ", PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);

        }
    }
}
