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

import java.io.InputStream;
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
        //ImageView imageView = (ImageView)rowView.findViewById(R.id.imageView);
        new DownloadImageTask((ImageView)rowView.findViewById(R.id.imageView))
                .execute(values.get(position).getUrlMainImg());
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

