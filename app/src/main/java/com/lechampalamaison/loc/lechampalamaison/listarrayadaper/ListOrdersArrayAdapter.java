package com.lechampalamaison.loc.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.lechampalamaison.loc.lechampalamaison.Model.Order;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.FindOrderLignesResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.OrderClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListOrdersArrayAdapter extends ArrayAdapter<Order> {
    private final Context context;
    private final ArrayList<Order> values;
    public static final String PREFS_NAME_USER = "USER";
    View rowView;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    OrderClient orderClient = retrofit.create(OrderClient.class);

    public ListOrdersArrayAdapter(Context context, ArrayList<Order> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         rowView = inflater.inflate(R.layout.list_order, parent, false);

        TextView textViewOrderId = (TextView) rowView.findViewById(R.id.textViewOrderId);
        textViewOrderId.setText("Commande n°"+values.get(position).getIdOrder());

        TextView textViewOrderDate = (TextView) rowView.findViewById(R.id.textViewOrderDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        long time = values.get(position).getDateOrder().getTime();
        Date date = new Date(time);
        textViewOrderDate.setText("Date  :"+dateFormat.format(date));

        TextView textViewOrderStatus = (TextView) rowView.findViewById(R.id.textViewOrderStatus);
        textViewOrderStatus.setText("Statut : "+values.get(position).getStatus());

        Double totalPrice = (double) Math.round(values.get(position).getTotalOrder() * 100) / 100;
        NumberFormat format= NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);
        TextView textViewTotalOrder = (TextView) rowView.findViewById(R.id.textViewTotalOrder);
        textViewTotalOrder.setText("Total : "+ format.format(totalPrice) + "€");

        TableLayout tableLayout = (TableLayout) rowView.findViewById(R.id.tableLayoutOrder);
        int padding_in_dp = 18;  // 6 dps
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);

        Call<FindOrderLignesResponse> call;
        SharedPreferences sharedPref = getContext().getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");
        String loginUser = sharedPref.getString("login", "");

        int idO = values.get(position).getIdOrder();
        call = orderClient.findOrderLignes(loginUser, token, idO);

        call.enqueue(new Callback<FindOrderLignesResponse>() {
            @Override
            public void onResponse(Call<FindOrderLignesResponse> call, Response<FindOrderLignesResponse> response) {
                if(response.body().getCode() == 0){
                    for(int i =0; i< response.body().getResult().length; i++){
                        TableRow tr = new TableRow(getContext());
                        tr.setGravity(Gravity.CENTER_HORIZONTAL);

                        TextView textViewQte = new TextView(getContext());
                        textViewQte.setText( response.body().getResult()[i].getQuantiteLigneOrder().toString()+" X");
                        textViewQte.setGravity(Gravity.CENTER_HORIZONTAL);
                        textViewQte.setTextColor(Color.parseColor("#000000"));

                        TextView textViewProduct = new TextView(getContext());
                        textViewProduct.setText( response.body().getResult()[i].getTitleLigneOrder().toString()+"\nVendeur : " + response.body().getResult()[i].getLoginUser());
                        textViewProduct.setGravity(Gravity.CENTER_HORIZONTAL);
                        textViewProduct.setTextColor(Color.parseColor("#000000"));
                        textViewProduct.setBackgroundResource(R.drawable.border_right);
                        textViewProduct.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                        textViewProduct.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);

                        Double price = (double) Math.round(response.body().getResult()[i].getPrixUnitaireLigneOrder() * 100) / 100;
                        Double shippingCost = (double) Math.round(response.body().getResult()[i].getShippingCostLigneOrder() * 100) / 100;
                        TextView textViewPriceU = new TextView(getContext());

                        textViewPriceU.setText(  format.format(price) + "€\nFrais de port "+  format.format(shippingCost)+"€");
                        textViewPriceU.setGravity(Gravity.CENTER_HORIZONTAL);
                        textViewPriceU.setTextColor(Color.parseColor("#000000"));
                        textViewPriceU.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                        textViewPriceU.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);

                        tr.addView(textViewQte);
                        tr.addView(textViewProduct);
                        tr.addView(textViewPriceU);

                        tableLayout.addView(tr);
                    }

                }else{

                }
            }

            @Override
            public void onFailure(Call<FindOrderLignesResponse> call, Throwable t) {
            }

        });


        return rowView;

    }



}

