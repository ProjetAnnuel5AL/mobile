package com.lechampalamaison.loc.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lechampalamaison.loc.lechampalamaison.Model.Item;
import com.lechampalamaison.loc.lechampalamaison.Model.Producer;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ListProducerArrayAdapter extends ArrayAdapter<Producer> {
    private final Context context;
    private final ArrayList<Producer> values;

    public ListProducerArrayAdapter(Context context, ArrayList<Producer> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_producer, parent, false);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.imageViewProducer);
        String[] ext = values.get(position).getAvatarProducer().split("\\.");

        Producer p = values.get(position);
        String urlImg = Configuration.urlApi+"producerAvatar/"+values.get(position).getIdProducer()+"/img_resize/avatar_ms."+ext[ext.length-1];
        Picasso.get().load(urlImg).into(imageView);

        /*new DownloadImageTask((ImageView)rowView.findViewById(R.id.imageView))
                .execute(values.get(position).getUrlMainImg());
        Picasso.with(MainActivity.this).load(imgURL).into(ivmage);*/
        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewNameProducer);
        TextView textViewDesk = (TextView) rowView.findViewById(R.id.textViewRoleProducer);
        TextView textViewLocal = (TextView) rowView.findViewById(R.id.TextViewaddressProducer);




        textViewTitle.setText(values.get(position).getLastNameProducer()+" " + values.get(position).getFirstNameProducer());
        textViewDesk.setText(values.get(position).getRoleProducer());
        textViewLocal.setText(values.get(position).getAddressProducer());


        return rowView;
    }



}

