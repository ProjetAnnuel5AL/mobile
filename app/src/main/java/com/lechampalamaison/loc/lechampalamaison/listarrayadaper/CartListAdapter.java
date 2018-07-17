package com.lechampalamaison.loc.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.lechampalamaison.loc.lechampalamaison.Activity.HomeActivity;
import com.lechampalamaison.loc.lechampalamaison.Activity.ItemActivity;
import com.lechampalamaison.loc.lechampalamaison.Fragment.CartFragment;
import com.lechampalamaison.loc.lechampalamaison.Model.CartItem;
import com.lechampalamaison.loc.lechampalamaison.R;


import java.util.List;
import java.util.Locale;

import static com.lechampalamaison.loc.lechampalamaison.Activity.LoginActivity.PREFS_NAME_USER;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>{
    private List<CartItem> itemsList;
    private TextView test;
    SharedPreferences sharedpreferences;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView quantity;
        private ImageView removeItem;
        private ImageView increaseQuantity;
        private ImageView decreaseQuantity;

        private MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.tv_itemName);
            price = view.findViewById(R.id.tv_itemPrice);
            quantity = view.findViewById(R.id.tv_itemQte);

            removeItem = view.findViewById(R.id.img_removeItem);

            removeItem.setOnClickListener((View v) -> {
                removeItem(getAdapterPosition());
            });

            increaseQuantity = view.findViewById(R.id.img_plusItem);

            increaseQuantity.setOnClickListener((View v) -> {
                increaseQuantity(getAdapterPosition());
            });

            decreaseQuantity = view.findViewById(R.id.img_minusItem);

            decreaseQuantity.setOnClickListener((View v) -> {
                decreaseQuantity(getAdapterPosition());
            });
        }
    }

    public CartListAdapter(List<CartItem> itemsList, TextView test, Context context) {
        this.itemsList = itemsList;
        this.test = test;
        this.context = context;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartItem item = itemsList.get(position);
        holder.name.setText(item.getItem().getTitle());
        holder.price.setText(String.format(Locale.getDefault(), "%.2f", item.getItem().getPrice()) + " €\nFrais de port : "+String.format(Locale.getDefault(), "%.2f", item.getItem().getShippingCost()) );
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        calculateTotal();

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String jsonCart = gson.toJson(itemsList);

        editor.putString("cart", jsonCart);
        editor.apply();
    }

    @Override
    public int getItemCount() {
        if (itemsList == null || itemsList.isEmpty()) {
            return 0;
        }

        return itemsList.size();
    }

    public void removeItem(int pos) {
        itemsList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, itemsList.size());
        notifyDataSetChanged();
        calculateTotal();

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String jsonCart = gson.toJson(itemsList);

        editor.putString("cart", jsonCart);
        editor.apply();
    }

    private void increaseQuantity(int pos) {
        int currentQantity = itemsList.get(pos).getQuantity();

        if (currentQantity < 10) {
            itemsList.get(pos).setQuantity(currentQantity + 1);
            notifyItemChanged(pos);
            notifyDataSetChanged();
            calculateTotal();
        }
    }

    private void decreaseQuantity(int pos) {
        int currentQantity = itemsList.get(pos).getQuantity();

        if (currentQantity > 1) {
            itemsList.get(pos).setQuantity(currentQantity - 1);
            notifyItemChanged(pos);
            notifyDataSetChanged();
            calculateTotal();
        }
    }

    private void calculateTotal(){
        double total = 0;

        for(CartItem item : itemsList) {
            total += item.getItem().getPrice() * item.getQuantity() + item.getItem().getShippingCost();
        }

        this.test.setText(String.valueOf(total) + " €");
    }
}
