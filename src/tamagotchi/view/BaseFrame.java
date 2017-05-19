// base frame

package tamagotchi.view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import tamagotchi.controller.AppController;

public class BaseFrame extends JFrame {
    private Panel basePanel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;

    public BaseFrame(AppController baseController) {
        basePanel = new Panel(baseController);
        setupFrame();
    }

    private void setupFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        this.setContentPane(basePanel);
        this.setTitle("Tamagotchi");
        this.setSize(800, 600);
        this.setLocation(width / 2 - this.getWidth() / 2, height / 2 - this.getHeight() / 2);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // create menus in base frame
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");

        menuItem = new JMenuItem("Login");
        menu.add(menuItem);
        menuItem = new JMenuItem("Register");
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Close");
        menu.add(menuItem);




        //menu.add(loginButton);
        //menu.add(registerButton);
        ///menu.addSeparator();
        //menu.add(closeButton);

        // set menu bar
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        // need to be always on bottom
        this.setVisible(true);

    }

}
