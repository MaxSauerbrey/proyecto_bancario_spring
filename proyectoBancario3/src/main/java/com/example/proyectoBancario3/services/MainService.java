package com.example.proyectoBancario3.services;

import com.example.proyectoBancario3.ProyectoBancario3Application;
import com.example.proyectoBancario3.entities.Client;
import com.example.proyectoBancario3.entities.User;
import com.example.proyectoBancario3.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//@Component
public class MainService {

    public MainService() {
        Client usuario1= new Client (null,"Juan","Carlos","123","juancarlos@gmail.com",new User(null, "Juan","Carlos","1554",0D));
        addClient(usuario1);
        Client usuario2= new Client (null,"Señor","Burns","123","burns@gmail.com",new User(null, "Señor","Burns","1554232",0D));
        addClient(usuario2);
        addClient(new Client (null,"Señor","Burns","123","burns@gmail.com",new User(null, "Señor","Burns","1554232",0D)));
    }

    static Scanner input = new Scanner(System.in).useDelimiter("\n");
    static List<Client> clients = new ArrayList<>();
    UserService userServ = new UserService();
    ApplicationContext context = SpringApplication.run(ProyectoBancario3Application.class);
    UserRepository repository = context.getBean(UserRepository.class);

    private void createClient() {
        Client client = new Client();
        Integer age = enterAge();
        String auxClientName, auxClientSurname, auxPhone;

        if (age > 17) {
            auxClientName = validateWord("nombre");
            client.setName(auxClientName);
            auxClientSurname = validateWord("apellido");
            client.setSurname(auxClientSurname);
            auxPhone = validateNumber("telefono");
            client.setPhone(auxPhone);
            client.setEmail(validatorString("email"));
            client.setUser(userServ.createUser());
            //!
            addClient(client);
            System.out.println("Cliente agregado.");
            //!
            updateClientArray();
        } else {
            System.out.println("Para crear una cuenta debe ser mayor de edad.");
        }
    }


     private void addClient(Client client) {
//        clients.add(client);
          repository.save(client);
    }


    private void updateClientArray() {
        clients = repository.findAll();
    }

    private Integer enterAge() {
        Integer age = 0;
        Boolean err;
        do {
            try {
                System.out.print("Ingrese su edad: ");
                age = input.nextInt();
                err = false;
            } catch (InputMismatchException e) {
                System.out.println("Error. Debe ingresar un numero.");
                err = true;
                input.nextLine();
            }
        } while (err);
        return age;
    }

    private String validateNumber(String str) {
        String auxNumber = validatorString(str);
        while (!numberValidator(auxNumber)) {
            System.out.println("Error.Ingrese solamente números.");
            auxNumber = validatorString(str);
        }
        return auxNumber;
    }

    private boolean numberValidator(String num) {
        Integer ASCII, count = 0;
        Character numberChar;
        Boolean ok = true;
        for (int i = 0; i < num.length(); i++) {
            numberChar = num.charAt(i);
            ASCII = (int) numberChar;
            if ((ASCII < 48 || ASCII > 57)) {
                count++;
                break;
            }
        }

        if (count > 0) ok = false;
        return ok;
    }

    private String validateWord(String str) {
        String auxWord = validatorString(str);
        while (!wordValidator(auxWord)) {
            System.out.println("Error.Ingrese solamente letras.");
            auxWord = validatorString(str);
        }
        return auxWord;
    }

    private Boolean wordValidator(String word) {
        Integer ASCII, count = 0;
        Character wordChar;
        Boolean ok = true;
        for (int i = 0; i < word.length(); i++) {
            wordChar = word.charAt(i);
            ASCII = (int) wordChar;
            if ((ASCII < 97 || ASCII > 122) && (ASCII < 65 || ASCII > 90)) {
                count++;
                break;
            }
        }

        if (count > 0) ok = false;
        return ok;
    }

    private static String validatorString(String msj) {
        String str;
        System.out.print("Ingrese el " + msj + " del cliente: ");
        str = input.next();
        while (str == null || str.isEmpty()) {
            System.out.print("Error. Ingrese el " + msj + " nuevamente: ");
            str = input.next();
        }
        return str;
    }

    private void userMenu() {
        String userName, pass;
        Integer index, opt = 0;
        Boolean err;

        System.out.print("\nIngrese su nombre de usuario: ");
        userName = input.next();
        index = getIndex(userName);

        System.out.print("\nIngrese su contraseña: ");
        pass = input.next();
        if (index == -1) System.out.println("\nNo existe un usuario con el alias ingresado.");
        else if (!pass.equals(clients.get(index).getUser().getPassword()))
            System.out.println("\nContraseña incorrecta.");
        else {
            System.out.println("\n\t\t  Bienvenido " + clients.get(index).getName() + ".");
            do {
                do {
                    System.out.print("\n\t\t\t Homebanking\n\n1- Ver saldo disponible.\n2- Ver sus datos personales.\n3- Deposito a otra cuenta.\n4- Salir.\n\nPor favor, elija una opción: ");
                    try {
                        opt = input.nextInt();
                        err = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Error. Solamente se pueden ingresar numeros.");
                        err = true;
                        input.nextLine();
                    }
                } while (err);
                switch (opt) {
                    case 1:
                        accountMoney(index);
                        break;
                    case 2:
                        personalInformation(index);
                        break;
                    case 3:
                        depositMoney(index);
                        break;
                    case 4:
                        System.out.println("\n\t\t  Adios " + clients.get(index).getName());
                        break;
                    default:
                        System.out.print("\nError.Ingrese una opcion correcta.\n");
                }
            } while (opt != 4);
        }
    }

    private void personalInformation(Integer index) {
        System.out.println(clients.get(index));
    }

    private void accountMoney(Integer index) {
        System.out.println("Su saldo disponible en cuenta es de: $" + clients.get(index).getUser().getSavingAccount());
    }

    private void depositMoney(Integer userIndex) {
        Integer depositIndex;
        String userTarget;
        Double amount = 0D;

        System.out.print("Ingrese el nombre de usuario a hacer el deposito: ");
        userTarget = input.next();
        depositIndex = getIndex(userTarget);

        if (depositIndex != -1) {
            amount = validateAmount(amount);
            transaction(userIndex, depositIndex, userTarget, amount);
        } else if (userIndex == depositIndex) {
            System.out.println("\nPara depositar en su cuenta elija la opcion autodeposito.");
        } else {
            System.out.println("\nNo existe un usuario con el alias ingresado.");
        }
    }

    private void transaction(Integer userIndex, Integer depositIndex, String userTarget, Double amount) {
        String ans;
        if (!checkCountAmount(userIndex, amount)) {
            System.out.println("Se depositaran $" + amount + " en la cuenta de " + userTarget + ".");
            do {
                System.out.println("Esta seguro de hacer esta operacion?(s/n): ");
                ans = input.next();
            } while (!ans.equalsIgnoreCase("s") && !ans.equalsIgnoreCase("n"));
            if (ans.equalsIgnoreCase("s")) {
                clients.get(userIndex).getUser().setSavingAccount(clients.get(userIndex).getUser().getSavingAccount() - amount);
                clients.get(depositIndex).getUser().setSavingAccount(clients.get(depositIndex).getUser().getSavingAccount() + amount);
                System.out.println("Operacion exitosa.");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } else System.out.println("El saldo de su cuenta es inferior al monto de la transacción.");
    }

    private Boolean checkCountAmount(Integer userIndex, Double amount) {
        return clients.get(userIndex).getUser().getSavingAccount() < amount;
    }

    private Double validateAmount(Double amount) {
        boolean err;
        do {
            try {
                System.out.print("Ingrese el monto a depositar: ");
                amount = input.nextDouble();
                err = false;
            } catch (InputMismatchException e) {
                System.out.println("Error. Solamente se pueden ingresar números.");
                err = true;
                input.nextLine();
            }
        } while (err);
        return amount;
    }

    private Integer getIndex(String user) {
        Integer index = -1;
        for (Client u : clients) {
            if (u.getUser().getUsername().equals(user)) {
                index = clients.indexOf(u);
                break;
            }
        }
        return index;
    }

    public void menu() {
        Integer opt = 0;
        Boolean err;
        System.out.println("\n\t.:Bienvenido al banco:.");
        do {
            do {
                System.out.print("\n\t\t\tMENU\n\n1.Ingresar como usuario.\n2.Crear usuario.\n3.Salir.\n\n");
                System.out.print("Por favor, elija una opción: ");
                try {
                    opt = input.nextInt();
                    err = false;
                } catch (InputMismatchException e) {
                    System.out.println("Error. Solamente se pueden ingresar numeros.");
                    err = true;
                    input.nextLine();
                }
            } while (err);
            switch (opt) {
                case 1:
                    this.userMenu();
                    break;
                case 2:
                    this.createClient();
                    break;
                case 3:
                    System.out.println("Muchas gracias por su visita.");
                    break;
                default:
                    System.out.print("\nError.Ingrese una opcion correcta.\n");
            }
        } while (opt != 3);
    }

}
