package com.alejandromo.gatos_app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int optionMenu = -1;
        String[] buttoms = { "1. Ver gatos" , "2. Salir" };

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
                   break;
               }
               default -> {
                   break;
               }
           }

        } while (optionMenu != 1);
    }
}