package com.example.proyectoBancario3.services;

import com.example.proyectoBancario3.entities.Client;
import com.example.proyectoBancario3.entities.User;

import java.util.Scanner;

public class UserService {
    Scanner input = new Scanner(System.in);

    public User createUser() {
        User user = new User();
        String auxStringNumber;

        usernameValidator(user);
        user.setPassword(validatorString("contraseña"));
        cardValidator(user);
        user.setSavingAccount(0d);
        return user;
    }

    private String cardGenerator() {
        String auxStringNumber;
        Integer numeroAleatorio = (int) (Math.random()*99999999+1);
        auxStringNumber=(String.valueOf(numeroAleatorio)+String.valueOf(numeroAleatorio));
        return auxStringNumber;
    }

    private void cardValidator(User user){
        String auxCardNumber;
        do{
            auxCardNumber=cardGenerator();
        }while(uniqueNumberCard(auxCardNumber));
        user.setCardNumber(auxCardNumber);
    }

    private void usernameValidator(User user) {
        String auxUserName;
        do {
            auxUserName = (validatorString("nombre de usuario"));
        } while (uniqueUserName(auxUserName));
        user.setUsername(auxUserName);
    }


    private String validatorString(String msj) {
        String str;
        System.out.print("Ingrese el " + msj + " del cliente: ");
        str = input.next();

        while (str == null || str.isEmpty()) {
            System.out.print("Error. Ingrese el " + msj + " nuevamente: ");
            str = input.next();
        }
        return str;
    }

    private boolean uniqueUserName(String userName) {
        Boolean match = false;

        for (Client client : MainService.clients) {
            if (client.getUser().getUsername().equalsIgnoreCase(userName)) {
                System.out.println("Error. El nombre de usuario elegido ya esta utilizado.");
                match = true;
                break;
            }
        }
        return match;
    }

    private boolean uniqueNumberCard (String numberCard){
        Boolean match = false;
        for(Client client: MainService.clients){
            if(client.getUser().getCardNumber().equals(numberCard)){
                match= true;
                break;
            }
        }
        return match;
    }
}
