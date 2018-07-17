package com.lechampalamaison.loc.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.lechampalamaison.loc.lechampalamaison.Model.Item;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ListShopArrayAdapter  extends ArrayAdapter<Item> {
    private final Context context;
    private final ArrayList<Item> values;

    public ListShopArrayAdapter(Context context, ArrayList<Item> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.imageView);
        String[] ext = values.get(position).getFileExtensionsItem().split(";");
        String urlImg = Configuration.urlApi+"itemPhotos/"+values.get(position).getId()+"/img_resize/0_xs."+ext[0];
        Picasso.get().load(urlImg).into(imageView);
        /*new DownloadImageTask((ImageView)rowView.findViewById(R.id.imageView))
                .execute(values.get(position).getUrlMainImg());
        Picasso.with(MainActivity.this).load(imgURL).into(ivmage);*/
        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView textViewDesk = (TextView) rowView.findViewById(R.id.textViewDesk);
        TextView textViewLocal = (TextView) rowView.findViewById(R.id.TextViewLoca);
        TextView textViewPrice = (TextView) rowView.findViewById(R.id.textViewPrice);

        textViewTitle.setText(values.get(position).getTitle());
        textViewDesk.setText(values.get(position).getDescription());
        textViewLocal.setText(values.get(position).getLocalisation());
        Double price = (double) Math.round(values.get(position).getPrice() * 100) / 100;
        NumberFormat format= NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);
        textViewPrice.setText(format.format(price)  + "€");

        return rowView;
    }



}

