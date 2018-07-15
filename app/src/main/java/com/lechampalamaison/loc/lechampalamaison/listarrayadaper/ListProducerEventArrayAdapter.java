package com.lechampalamaison.loc.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lechampalamaison.loc.lechampalamaison.Model.Producer;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListProducerEventArrayAdapter extends ArrayAdapter<Producer> {
    private final Context context;
    private final ArrayList<Producer> values;

    public ListProducerEventArrayAdapter(Context context, ArrayList<Producer> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_producer_event, parent, false);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.imageViewProducerEvent);
        String[] ext = values.get(position).getAvatarProducer().split("\\.");

        Producer p = values.get(position);
        String urlImg = Configuration.urlApi+"producerAvatar/"+values.get(position).getIdProducer()+"/img_resize/avatar_ms."+ext[ext.length-1];
        Picasso.get().load(urlImg).into(imageView);


        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewNameProducerEvent);
        TextView textViewRoleProducerEvent = (TextView) rowView.findViewById(R.id.textViewRoleProducerEvent);
        TextView TextViewAddressProducerEvent = (TextView) rowView.findViewById(R.id.TextViewAddressProducerEvent);




        textViewTitle.setText(values.get(position).getLastNameProducer()+" " + values.get(position).getFirstNameProducer());
        textViewRoleProducerEvent.setText(values.get(position).getRoleProducer());
        TextViewAddressProducerEvent.setText(values.get(position).getAddressProducer());


        return rowView;
    }



}

