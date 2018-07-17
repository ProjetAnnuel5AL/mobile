package com.lechampalamaison.loc.lechampalamaison.listarrayadaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lechampalamaison.loc.lechampalamaison.Model.Comment;
import com.lechampalamaison.loc.lechampalamaison.Model.Item;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListCommentArrayAdapter  extends ArrayAdapter<Comment> {
    private final Context context;
    private final ArrayList<Comment> values;

    public ListCommentArrayAdapter(Context context, ArrayList<Comment> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_comment, parent, false);


        TextView textViewDateComment = (TextView) rowView.findViewById(R.id.textViewDateComment);
        TextView textViewLoginComment = (TextView) rowView.findViewById(R.id.textViewLoginComment);
        TextView textViewEvaluationComment = (TextView) rowView.findViewById(R.id.textViewEvaluationComment);
        TextView textViewComment = (TextView) rowView.findViewById(R.id.textViewComment);

        textViewLoginComment.setText(values.get(position).getLoginComment().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        long time = values.get(position).getDateComment().getTime();
        Date date = new Date(time);
        textViewDateComment.setText(dateFormat.format(date));

        Double eval = (double) Math.round(values.get(position).getStars());
        NumberFormat format= NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);
        textViewEvaluationComment.setText(format.format(eval) +" Etoiles");
        textViewComment.setText(values.get(position).getComment());


        return rowView;
    }

}
