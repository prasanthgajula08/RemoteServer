package sample;

import javafx.application.Platform;
import javafx.fxml.Initializable;

import Udp.Main_Server;
import Udp.ServerHandler;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import java.io.IOException;

public class Controller implements Initializable {
    public Label sendReceiveLog;
    public Label ipValue;
    public Button startButton;
    public Button stopButton;
    public TextField passwordtf;
    public File passFile;
    public Button closeButton;
    public Button minButton;
    public static boolean isRunning;
    ServerHandler serverHandler;
    private static final String iconImageLoc = "http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/16/Places-server-database-icon.png";

    public void closeWindow(){
        if(isRunning) {
            javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Server is still running! App is in system tray, You can close it there.", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                sample.Main.stage.close();
            }
        }
        else{
            sample.Main.stage.close();
        }
    }

    public void minWindow(){
        sample.Main.stage.setIconified(true);
    }

    public void showButtons() throws IOException {
        startButton.setVisible(true);
        stopButton.setVisible(false);
        BufferedWriter writer = new BufferedWriter(new FileWriter(passFile));
        writer.write(passwordtf.getText());
        writer.close();
    }

    public void StartServer() throws IOException {
        isRunning = true;
        startButton.setVisible(false);
        stopButton.setVisible(true);
        sendReceiveLog.setText("Started Server....");
        BufferedReader br = new BufferedReader(new FileReader(passFile));
        String password = br.readLine();
        //write startserver code here
        serverHandler = new ServerHandler(password);
        serverHandler.start();
    }

    public void StopServer(){
        isRunning = false;
        startButton.setVisible(true);
        stopButton.setVisible(false);
        sendReceiveLog.setText("Server stopped.");

        //Write stopserver code here
        if(serverHandler!=null){
            serverHandler.end();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.setImplicitExit(false);

        sample.Main.stage.initStyle(StageStyle.UNDECORATED);

        isRunning = false;
        startButton.setVisible(false);
        stopButton.setVisible(false);
        passFile = new File("password.txt");
        try {
            ipValue.setText(InetAddress.getLocalHost().getHostAddress()+":6969");
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            if(!passFile.exists()){
                BufferedWriter writer = new BufferedWriter(new FileWriter(passFile));
                writer.write("");
                writer.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(passFile));
            String st = br.readLine();
            passwordtf.setText(st);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAppToTray() {
        try {
            java.awt.Toolkit.getDefaultToolkit();

            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(
                    iconImageLoc
            );
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            java.awt.MenuItem openItem = new java.awt.MenuItem("PcRemoteServer");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    private void showStage() {
        if (sample.Main.stage != null) {
            sample.Main.stage.show();
            sample.Main.stage.toFront();
        }
    }

}

