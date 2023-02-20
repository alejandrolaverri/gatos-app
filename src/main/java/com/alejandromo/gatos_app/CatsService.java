package com.alejandromo.gatos_app;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService {

    public static void seeRandomCats() throws IOException {
        // Obtención de los datos de la API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        // Eliminamos los corchetes
        jsonData = jsonData.substring(1);
        jsonData = jsonData.substring(0, jsonData.length()-1);

        // Conversion de JSON a la clase Cats
        Gson gson = new Gson();
        Cats cat = gson.fromJson(jsonData, Cats.class);

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

    private static void addCatAsFavorite(Cats cat) {

    }
}
