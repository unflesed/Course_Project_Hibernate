import helpers.*;
import web_service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MainSupport.tablesFilling();
        UserHelper uh = new UserHelper();
        User user = new User();

        String userName = "";
        String password = "";
        String userRole = null;
        String input = ""; // переменная для получения запроса к базе данных

        //Проверка имени пользователя и пароля
        do {
            System.out.println("Введите имя пользователя: ");
            userName = sc.next();
            System.out.println("Введите пароль: ");
            password = sc.next();
            if ( uh.checkUserPassword(userName, password) != null) {
                userRole = uh.checkUserPassword(userName, password).getUserRole().getRole_name();
            }
            user = uh.checkUserPassword(userName, password);
            if (userRole == null) {
                System.out.println("Вы ввели неверное имя пользователя или пароль!");
            }
        }while (userRole == null);

        //Получение запроса от пользователя
        System.out.println("Для выхода введите 'exit'");

        do {
            System.out.println("Введите необходимый запрос:");
            input = sc.next();
            if (userRole.equals("USER") && !input.equalsIgnoreCase("exit")) {
                MainSupport.userInput(input, user);
            }
            if ((userRole.equals("ADMIN") || userRole.equals("SUPER_ADMIN"))
                    && !input.equalsIgnoreCase("exit")) {
                MainSupport.userInputForAdmins(input, user);
            }
        }while(!input.equals("exit"));


    }
}
