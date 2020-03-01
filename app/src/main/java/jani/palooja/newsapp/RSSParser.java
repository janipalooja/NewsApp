package jani.palooja.newsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class RSSParser extends AsyncTask<String, Void, ArrayList<NewsData>> {

    ArrayList<NewsSource> sources = new ArrayList<>();
    ArrayList<NewsData> news = new ArrayList<>();
    Context context;

    public interface ObserverInterface {
        void onSuccess(ArrayList<NewsData> news);
    }

    private ObserverInterface observer;

    public RSSParser(Context context, ObserverInterface observer, ArrayList<NewsSource> sources) {
        this.context = context;
        this.observer = observer;
        this.sources = sources;
    }

    @Override
    protected ArrayList<NewsData> doInBackground(String... params) {
        return getXmlFromUrl(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<NewsData> news) {
        observer.onSuccess(news);
    }

    public ArrayList<NewsData> getXmlFromUrl(String keyword) {
        try {

            boolean keywordFound;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            for (int i = 0; i < sources.size(); i++) {

                Document doc = dBuilder.parse(sources.get(i).getUrl());
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("item");

                for (int j = 0; j < nList.getLength(); j++) {
                    Node nNode = nList.item(j);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        String link = eElement
                                .getElementsByTagName("link")
                                .item(0)
                                .getTextContent();
                        Log.d("link " + j, link);

                        String title = eElement
                                .getElementsByTagName("title")
                                .item(0)
                                .getTextContent();
                        Log.d("title " + j, title);

                        String description = eElement
                                .getElementsByTagName("description")
                                .item(0)
                                .getTextContent();
                        Log.d("description " + j, description);

                        String enclosure;
                        try {
                            enclosure = eElement
                                    .getElementsByTagName("enclosure")
                                    .item(0).getAttributes().getNamedItem("url").getNodeValue();
                            Log.d("enclosure " + j, enclosure);

                        } catch (Exception e) {
                            enclosure = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbudDbp1YoWcGkV2c_9aLDYbmmPgmCGDsgFnjCgXnO_BVlAQ8D&s";
                            Log.d("######### enclosure " + j, " on tyhja.... #########");
                            //TODO: handle exception
                            e.printStackTrace();
                        }

                        String category = eElement
                                .getElementsByTagName("category")
                                .item(0)
                                .getTextContent();
                        Log.d("category " + j, category);

                        String pubDate = eElement
                                .getElementsByTagName("pubDate")
                                .item(0)
                                .getTextContent();
                        Log.d("pubDate " + j, pubDate);

                        String sourceLogo = sources.get(i).getLogo();

                        keywordFound = title.matches("(?i).*" + keyword + ".*") || description.matches("(?i).*" + keyword + ".*");

                        if (keywordFound) {
                            news.add(new NewsData(link, title, description, enclosure, category, pubDate, sourceLogo));
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return news;
    }

}