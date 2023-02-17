package com.alejandromo.gatos_app;

import io.github.cdimascio.dotenv.Dotenv;


public class Cats {
    private final Dotenv dotenv = Dotenv.load();
    private String id;
    private String url;
    private String apiKey = dotenv.get("API_KEY");
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
