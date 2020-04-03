package sample;

import Udp.Main_Server;
import Udp.ServerHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Controller {
    public Label sendReceiveLog;
    public Label ipValue;
    public Label password;
    public Button startButton;
    public Button stopButton;
    ServerHandler serverHandler;
    public void StartServer(ActionEvent actionEvent) throws UnknownHostException {
        startButton.setVisible(false);
        stopButton.setVisible(true);
        ipValue.setText(InetAddress.getLocalHost().getHostAddress()+":6969");
        String password = GeneratePassword();
        sendReceiveLog.setText("Started Server....");

        //write startserver code here

        serverHandler = new ServerHandler(password);
        serverHandler.start();
    }

    public void StopServer(ActionEvent actionEvent){
        startButton.setVisible(true);
        stopButton.setVisible(false);
        ipValue.setText("Null");
        password.setText("Null");
        sendReceiveLog.setText("Server stopped.");

        //Write stopserver code here
        if(serverHandler!=null){
            serverHandler.end();
        }
    }

    public String GeneratePassword(){
        int len = 10;
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";


        String values = Capital_chars + Small_chars + numbers + symbols;

        Random rndm_method = new Random();

        char[] password = new char[len];

        for (int i = 0; i < len; i++)
        {
            password[i] = values.charAt(rndm_method.nextInt(values.length()));

        }
        this.password.setText(String.valueOf(password));
        return String.valueOf(password);
    }
}

