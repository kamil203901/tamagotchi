package tamagotchi.controller;

public class AppRunner {
    public static void main(String[] args) {
        //DBConnect connect = new DBConnect();
        //connect.deleteUser("kamil");
        //connect.getData();


        AppController baseApp = new AppController();
        LoginController loginController = new LoginController(baseApp);
        baseApp.start();
        //baseApp.login();
    }
}
