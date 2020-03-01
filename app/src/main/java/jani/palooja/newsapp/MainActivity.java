package jani.palooja.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RSSParser.ObserverInterface {

    private Toolbar toolbar;
    ImageButton searchButton;

    NewsListAdapter adapter;
    ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopUp.class);
                startActivityForResult(intent, 1);
            }
        });

        newsListView = (ListView) findViewById(R.id.newsListView);
    }

    @Override
    public void onSuccess(ArrayList<NewsData> news) {
        Collections.shuffle(news);
        adapter = new NewsListAdapter(this, news);
        newsListView.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<NewsSource> sources = new ArrayList<>();

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String keyword = data.getStringExtra("keyword");

                boolean ilta_sanomat_checked = data.getBooleanExtra("ilta_sanomat_checked", false);
                if (ilta_sanomat_checked) {
                    sources.add( new NewsSource("https://www.is.fi/rss/tuoreimmat.xml", "Ilta-Sanomat", "https://www.is.fi/assets/images/is-logo-rss.bfb093e38b9bc45a.png"));
                }

                boolean yle_uutiset_checked = data.getBooleanExtra("yle_uutiset_checked", false);
                if (yle_uutiset_checked) {
                    sources.add(new NewsSource("https://feeds.yle.fi/uutiset/v1/recent.rss?publisherIds=YLE_UUTISET", "YLE", "https://yle.fi/uutiset/public/img/yle_uutiset_black.png"));
                }

                boolean helsingin_sanomat_checked = data.getBooleanExtra("helsingin_sanomat_checked", false);
                if (helsingin_sanomat_checked) {
                    sources.add(new NewsSource("https://www.hs.fi/rss/tuoreimmat.xml", "Helsingin Sanomat", "https://blogs.helsinki.fi/tynkkynen/files/2018/02/HS-300x182.png"));
                }

                boolean kaleva_checked = data.getBooleanExtra("kaleva_checked", false);
                if (kaleva_checked) {
                    sources.add(new NewsSource("https://www.kaleva.fi/rss/show/", "Kaleva", "https://res-3.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1441464508/nrwsz5tkhzw9ivn3yvd2.png"));
                }

                new RSSParser(this, this, sources).execute(keyword);

            }
        }
    }

}
