package com.lechampalamaison.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lechampalamaison.R;
import com.lechampalamaison.model.CartItem;
import com.lechampalamaison.model.Item;

import java.util.List;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>{
    private List<CartItem> itemsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;

        private MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.tv_itemName);
            price = view.findViewById(R.id.tv_itemPrice);
        }
    }

    public CartListAdapter(List<CartItem> itemsList) {
        this.itemsList = itemsList;
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
        holder.price.setText(String.format(Locale.getDefault(), "%.2f", item.getItem().getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
