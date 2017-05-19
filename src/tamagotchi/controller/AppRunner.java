package tamagotchi.controller;

import tamagotchi.model.DBConnect;

public class AppRunner {
    public static void main(String[] args) {
        DBConnect connect = new DBConnect();
        connect.getData();
       // AppController baseApp = new AppController();
        //baseApp.start();
        //baseApp.login();
    }
}
