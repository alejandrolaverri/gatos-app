package com.alejandromo.gatos_app;

import com.alejandromo.gatos_app.service.CatsService;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int optionMenu = -1;
        String[] buttoms = { "1. Ver gatos" , "2. Ver favoritos", "3. Salir" };

        do {
           // Menu principal
           String option = (String) JOptionPane.showInputDialog(
                   null,
                   "Gatos Java",
                   "Menu principal",
                   JOptionPane.INFORMATION_MESSAGE,
                   null,
                   buttoms,
                   buttoms[0]
           );

           // Validación de la opción seleccionada por el usuario
           for (int i = 0; i < buttoms.length; i++) {
               if (option.equals(buttoms[i])) {
                   optionMenu = i;
               }
           }

           switch (optionMenu) {
               case 0 -> {
                   CatsService.seeRandomCats();
               }
               case 1 -> {
                   CatsService.seeFavoriteCats();
               }
               default -> {}
           }

        } while (optionMenu != 1);
    }
}