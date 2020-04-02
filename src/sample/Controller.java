package sample;

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

    public void StartServer(ActionEvent actionEvent) throws UnknownHostException {
        startButton.setVisible(false);
        stopButton.setVisible(true);
        ipValue.setText(InetAddress.getLocalHost().getHostAddress());
        GeneratePassword();
        sendReceiveLog.setText("Started Server....");
        //write startserver code here
    }

    public void StopServer(ActionEvent actionEvent){
        startButton.setVisible(true);
        stopButton.setVisible(false);
        ipValue.setText("Null");
        password.setText("Null");
        sendReceiveLog.setText("Server stopped.");
        //Write stopserver code here
    }

    public void GeneratePassword(){
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
    }
}

