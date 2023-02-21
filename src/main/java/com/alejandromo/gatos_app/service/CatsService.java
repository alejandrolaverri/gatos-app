package com.alejandromo.gatos_app.service;

import com.alejandromo.gatos_app.model.Cat;
import com.alejandromo.gatos_app.model.FavoriteCat;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import io.github.cdimascio.dotenv.Dotenv;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String BASE_URL = "https://api.thecatapi.com/v1/";
    private static final String SEARCH_ENDPOINT = BASE_URL + "images/search";
    private static final String FAVORITE_ENDPOINT = BASE_URL + "favourites";
    private static final OkHttpClient client = new OkHttpClient();

    public static void seeRandomCats() throws IOException {
        // Obtención de los datos de la API
        Request request = new Request.Builder().url(SEARCH_ENDPOINT).get().build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        // Eliminamos los corchetes
        jsonData = jsonData.substring(1);
        jsonData = jsonData.substring(0, jsonData.length()-1);

        // Conversion de JSON a la clase Cats
        Gson gson = new Gson();
        Cat cat = gson.fromJson(jsonData, Cat.class);

        // Redimensionar imagen
        Image image;
        try {
            URL url = new URL(cat.getUrl());
            image = ImageIO.read(url);

            ImageIcon catImageIcon = new ImageIcon(image);

            if (catImageIcon.getIconWidth() > 800) {
                Image background = catImageIcon.getImage();
                Image modified = background.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                catImageIcon = new ImageIcon(modified);
            }

            String menu = "Opciones: \n"
                          + "1. Ver otra imagen \n"
                          + "2. Favorito \n"
                          + "3. Volver \n";

            String[] buttoms = { "Ver otra imagen", "Favorito", "Volver" };

            String option = (String) JOptionPane.showInputDialog(null, menu, cat.getId(), JOptionPane.INFORMATION_MESSAGE, catImageIcon, buttoms, buttoms[0]);

            int selection = -1;

            // Validación de la opción seleccionada por el usuario
            for (int i = 0; i < buttoms.length; i++) {
                if (option.equals(buttoms[i])) {
                    selection = i;
                }
            }

            switch (selection) {
                case 0 -> seeRandomCats();
                case 1 -> addCatAsFavorite(cat);
                default -> {}
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void addCatAsFavorite(Cat cat) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"image_id\":\""+cat.getId()+"\"\n}");
            Request request = new Request.Builder()
                    .url(FAVORITE_ENDPOINT)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cat.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                response.body().close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void seeFavoriteCats() throws IOException {
        Request request = new Request.Builder()
                .url(FAVORITE_ENDPOINT)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", dotenv.get("API_KEY"))
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        if (!response.isSuccessful()) {
            response.body().close();
        }

        Gson gson = new Gson();

        FavoriteCat[] cats = gson.fromJson(jsonData, FavoriteCat[].class);
        if (cats.length > 0) {
            int min = 1;
            int max = cats.length;
            int random = (int) ((Math.random() * ((max - min) + 1)) + min);
            int index = random - 1;

            FavoriteCat favoriteCat = cats[index];

            Image image;
            try {
                URL url = new URL(favoriteCat.image.getUrl());
                image = ImageIO.read(url);

                ImageIcon catImageIcon = new ImageIcon(image);

                if (catImageIcon.getIconWidth() > 800) {
                    Image background = catImageIcon.getImage();
                    Image modified = background.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                    catImageIcon = new ImageIcon(modified);
                }

                String menu = "Opciones: \n"
                        + "1. Ver otra imagen \n"
                        + "2. Eliminar favorito \n"
                        + "3. Volver \n";

                String[] buttoms = { "Ver otra imagen", "Eliminar favorito", "Volver" };

                String option = (String) JOptionPane.showInputDialog(null, menu, favoriteCat.getId(), JOptionPane.INFORMATION_MESSAGE, catImageIcon, buttoms, buttoms[0]);

                int selection = -1;

                // Validación de la opción seleccionada por el usuario
                for (int i = 0; i < buttoms.length; i++) {
                    if (option.equals(buttoms[i])) {
                        selection = i;
                    }
                }

                switch (selection) {
                    case 0 -> seeFavoriteCats();
                    case 1 -> deleteFavorite(favoriteCat);
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private static void deleteFavorite(FavoriteCat favoriteCat) {
        try {
            Request request = new Request.Builder()
                    .url(FAVORITE_ENDPOINT + favoriteCat.getId())
                    .delete(null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", dotenv.get("API_KEY"))
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                response.body().close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
