package com.lechampalamaison.loc.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lechampalamaison.loc.lechampalamaison.Model.Event;
import com.lechampalamaison.loc.lechampalamaison.Model.Producer;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListEventArrayAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final ArrayList<Event> values;

    public ListEventArrayAdapter(Context context, ArrayList<Event> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_event, parent, false);

        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitleEvent);
        TextView textViewDesk = (TextView) rowView.findViewById(R.id.textViewDescriptionEvent);
        TextView textViewLocal = (TextView) rowView.findViewById(R.id.textViewDateEvent);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        long time = values.get(position).getDate().getTime();
        Date date = new Date(time);


        textViewTitle.setText(values.get(position).getTitle());
        textViewDesk.setText(values.get(position).getDescription());
        textViewLocal.setText(dateFormat.format(date));


        return rowView;
    }



}

