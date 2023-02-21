package com.alejandromo.gatos_app.model;

import io.github.cdimascio.dotenv.Dotenv;

public class FavoriteCat {
    private final Dotenv dotenv = Dotenv.load();
    private String id;
    private String imageId;
    private String apiKey = dotenv.get("API_KEY");
    public ImageX image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ImageX getImage() {
        return image;
    }

    public void setImage(ImageX image) {
        this.image = image;
    }
}
