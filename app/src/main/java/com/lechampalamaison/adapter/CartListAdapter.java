package com.lechampalamaison.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lechampalamaison.R;
import com.lechampalamaison.model.Item;

import java.util.List;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>{
    private List<Item> itemsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView price;

        private MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);
            price = view.findViewById(R.id.tv_price);
        }
    }

    public CartListAdapter(List<Item> itemsList) {
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
        Item item = itemsList.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.price.setText(String.format(Locale.getDefault(), "%.2f", item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
