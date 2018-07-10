package com.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lechampalamaison.R;
import com.lechampalamaison.model.Item;
import com.lechampalamaison.model.Order;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ListOrdersArrayAdapter extends ArrayAdapter<Order> {
    private final Context context;
    private final ArrayList<Order> values;

    public ListOrdersArrayAdapter(Context context, ArrayList<Order> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_order, parent, false);

        return rowView;
    }



}

