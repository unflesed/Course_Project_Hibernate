import helpers.*;
import web_service.*;

import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        tablesFilling();
        UserHelper uh = new UserHelper();
        User user = new User();

        String userName = "";
        String password = "";
        String userRole = null;
        String input = ""; // переменная для получения запроса к базе данных


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

        System.out.println("Для выхода введите 'exit'");

        do {
            System.out.println("Введите необходимый запрос:");
            input = sc.next();
            if (userRole.equals("USER") && !input.equalsIgnoreCase("exit")) {
                userInput(input, user);
            }
            if ((userRole.equals("ADMIN") || userRole.equals("SUPER_ADMIN"))
                    && !input.equalsIgnoreCase("exit")) {
                userInputForAdmins(input, user);
            }
        }while(!input.equals("exit"));


    }

    public static void userInput(String input, User user){
        UserHelper uh = new UserHelper();
        IncidentHelper ih = new IncidentHelper();

        String strForSwitch = input.replaceAll("(?<=\\{)\\d+(?=\\})","");
        String idString = input.replaceAll("[^\\d]","");
        long id = 0;

        if (!idString.equals("")) {
            id = Long.parseLong(idString);
        }

        switch (strForSwitch){
            case "s_s_{}":
                uh.subscribeService(id, user.getId());
                break;
            case "u_s_{}":
                uh.unSubscribeService(id, user.getId());
                break;
            case "cr_i":
                Incident incident = new Incident();
                System.out.println("Введите название тикета: ");
                incident.setServiceName(sc.next());
                incident.setActive(true);
                sc.nextLine();
                System.out.println("Введите описание тикета: ");
                incident.setProblemDescription(sc.nextLine());
                incident.setUser(user);

                ih.addIncident(incident);
                System.out.println("Тикет успешно создан!");
                break;
            default:
                System.out.println("Вы ввели неверный запрос!");
                break;
        }
    }

    public static void userInputForAdmins(String input, User user){
        UserHelper uh = new UserHelper();
        IncidentHelper ih = new IncidentHelper();
        ProfileHelper ph = new ProfileHelper();
        UserRoleHelper urh = new UserRoleHelper();

        String strForSwitch = input.replaceAll("(?<=\\{)\\d+(?=\\})","");
        String idString = input.replaceAll("[^\\d]","");
        long id = 0;

        if (!idString.equals("")) {
            id = Long.parseLong(idString);
        }

        switch (strForSwitch){
            case "f_a_u":
                uh.getAllUsers();
                break;
            case "f_a_i":
                ih.getAllIncidents();
                break;
            case "f_a_a_i":
                ih.getAllActiveIncidents();
                break;
            case "f_u_b_{}":
                System.out.println(uh.getUserById(id));
                break;
            case "a_u":
                Profile profile = new Profile();
                System.out.println("Введите имя: ");
                profile.setFirstName(sc.next());
                System.out.println("Введите фамилию: ");
                profile.setLastName(sc.next());
                System.out.println("Введите email: ");
                profile.seteMail(sc.next());
                System.out.println("Введите телефон: ");
                profile.setPhoneNumber(sc.next());
                System.out.println("Введите почтовый индекс: ");
                profile.setPostalCode(sc.nextLong());

                User newUser = new User();
                System.out.println("Введите имя пользователя: ");
                newUser.setUser_name(sc.next());
                System.out.println("Введите пароль: ");
                newUser.setPassword(sc.next());
                newUser.setProfile(profile);
                System.out.println("Выберите права доступа (USER, ADMIN, SUPER_ADMIN): ");
                newUser.setUserRole(urh.getUserRole(sc.next()));

                ph.addProfile(profile);
                uh.addUser(newUser);
                System.out.println("Пользователь добавлен!");
                break;
            case "u_u_{}":
                User updateUser = uh.getUserById(id);
                Profile updateProfile = ph.getProfileById(updateUser.getProfile().getId());
                System.out.println("Введите имя: ");
                updateProfile.setFirstName(sc.next());
                System.out.println("Введите фамилию: ");
                updateProfile.setLastName(sc.next());
                System.out.println("Введите email: ");
                updateProfile.seteMail(sc.next());
                System.out.println("Введите телефон: ");
                updateProfile.setPhoneNumber(sc.next());
                System.out.println("Введите почтовый индекс: ");
                updateProfile.setPostalCode(sc.nextLong());

                System.out.println("Введите имя пользователя: ");
                updateUser.setUser_name(sc.next());
                System.out.println("Введите пароль: ");
                updateUser.setPassword(sc.next());
                updateUser.setProfile(updateProfile);

                ph.updateProfile(updateProfile);
                uh.updateUser(updateUser);

                System.out.println("Изменения успешно сохранены!");
                break;
            case "d_u_{}":
                uh.remove(uh.getUserById(id));
                System.out.println("Удаление прошло успешно!");
                break;
            case "s_s_{}":
                uh.subscribeService(id, user.getId());
                System.out.println("Вы подписались на услугу.");
                break;
            case "u_s_{}":
                uh.unSubscribeService(id, user.getId());
                System.out.println("Вы отписались от услуги.");
                break;
            case "cr_i":
                Incident incident = new Incident();
                System.out.println("Введите название тикета: ");
                incident.setServiceName(sc.next());
                incident.setActive(true);
                sc.nextLine();
                System.out.println("Введите описание тикета: ");
                incident.setProblemDescription(sc.nextLine());
                incident.setUser(user);

                ih.addIncident(incident);
                System.out.println("Тикет успешно создан!");
                break;
            case "cl_i_{}":
                ih.closeIncident(id);
                System.out.println("Тикет закрыт.");
                break;
            default:
                System.out.println("Вы ввели неверный запрос!");
                break;
        }
    }

    public static void tablesFilling(){
        UserHelper uh = new UserHelper();
        IncidentHelper ih = new IncidentHelper();
        ProfileHelper ph = new ProfileHelper();
        UserRoleHelper urh = new UserRoleHelper();
        ServiceHelper sh = new ServiceHelper();

        UserRole userRole1 = new UserRole("ADMIN", "Administrator");
        UserRole userRole2 = new UserRole("USER", "Simple_user");
        UserRole userRole3 = new UserRole("SUPER_ADMIN", "Super_Administrator");
        Profile profile1 = new Profile("Vasya", "Pupkin",
                "vasya@mail.ru", "+73333334333",123124);
        Profile profile2 = new Profile("Ivan", "Ivanov",
                "ivanov@mail.ru", "+3754534543",35222342);
        Profile profile3 = new Profile("Petr", "Petrov",
                "petrov@gmail.com", "+38035434453",132423423);
        User user1 = new User("Admin","123",profile1, userRole1);
        User user2 = new User("User_1","123",profile2, userRole2);
        User user3 = new User("Super","123",profile3, userRole3);
        Incident incident1 = new Incident("Incident_1", true,
                "Description", user1);
        Incident incident2 = new Incident("Incident_2", false,
                "Description", user2);
        Incident incident3 = new Incident("Incident_3", true,
                "Description", user3);
        Service service1 = new Service("Service_1", true, 200.0, 1);
        Service service2 = new Service("Service_2", false, 2032.0, 2);
        Service service3 = new Service("Service_3", false, 11.7, 3);

        user1.addService(service1);
        user1.addService(service2);
        user2.addService(service1);
        user3.addService(service3);

        sh.addService(service1);
        sh.addService(service2);
        sh.addService(service3);

        urh.addUserRole(userRole1);
        urh.addUserRole(userRole2);
        urh.addUserRole(userRole3);

        ph.addProfile(profile1);
        ph.addProfile(profile2);
        ph.addProfile(profile3);

        uh.addUser(user1);
        uh.addUser(user2);
        uh.addUser(user3);

        ih.addIncident(incident1);
        ih.addIncident(incident2);
        ih.addIncident(incident3);
    }
}
