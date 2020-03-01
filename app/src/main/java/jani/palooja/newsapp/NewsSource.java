package jani.palooja.newsapp;

public class NewsSource {

    private String url;
    private String name;
    private String logo;

    public NewsSource(String url, String name, String logo) {
        this.url = url;
        this.name = name;
        this.logo = logo;
    }

    public String getUrl() { return this.url; }
    public String getName() { return this.name; }
    public String getLogo() { return this.logo; }
}
