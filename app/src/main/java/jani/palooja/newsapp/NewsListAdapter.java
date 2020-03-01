package jani.palooja.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<NewsData> {

    // We'll define a context and we will create a list of type Post that will hold all of our Post objects
    private final Context context;
    private final ArrayList<NewsData> news;

    //constructor
    public NewsListAdapter(Context context, ArrayList<NewsData> list) {

        super(context, R.layout.news_list_item, list);
        this.context = context;
        this.news = list;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //connect to custom_post_layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_list_item, parent, false);

        //assign views
        ImageView newsImage = (ImageView) rowView.findViewById(R.id.newsImage);
        ImageView logo = (ImageView) rowView.findViewById(R.id.logo);
        TextView newsTitle = (TextView) rowView.findViewById(R.id.newsTitle);
        newsTitle.setText(news.get(position).getTitle());
        TextView description = (TextView) rowView.findViewById(R.id.description);
        description.setText(news.get(position).getDescription());
        newsImage.setClickable(true);
        newsImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.get(position).getLink()));
                context.startActivity(browserIntent);
            }
        });

        Glide
                .with(context)
                .load(news.get(position).getEnclosure())
                .override(width, height)
                .into(newsImage);

        Glide
                .with(context)
                .load(news.get(position).getSourceLogo())
                .into(logo);

        return rowView;
    }

}