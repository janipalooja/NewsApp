package jani.palooja.newsapp;

public class NewsData {

    private String link;
    private String title;
    private String description;
    private String enclosure;
    private String category;
    private String pubDate;
    private String sourceLogo;

    public NewsData(String link, String title, String description, String enclosure, String category, String pubDate, String sourceLogo) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.enclosure = enclosure;
        this.category = category;
        this.pubDate = pubDate;
        this.sourceLogo = sourceLogo;
    }

    public String getLink() {
        return this.link;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getEnclosure() {
        return this.enclosure;
    }

    public String getCategory() {
        return this.category;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public String getSourceLogo() {
        return this.sourceLogo;
    }

    ;

}
