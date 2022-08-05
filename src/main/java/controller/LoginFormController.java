package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Set;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtPasswordReEnter;
    public JFXButton btnRegister;
    public JFXButton btnLogin;
    String userName="";
    String password="";

    public void initialize() throws IOException {
        if (userName.trim().equals("")){
            btnLogin.setVisible(false);
            btnRegister.setVisible(true);
        } else {
            btnLogin.setVisible(true);
            btnRegister.setVisible(false);
        }
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        if (txtPassword.getText().isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Password cannot be blank").showAndWait();
            return;
        }
        if (! txtPassword.getText().equals(txtPasswordReEnter.getText())){
            new Alert(Alert.AlertType.ERROR, "Both password should be match").showAndWait();
            return;
        }
        password=txtPassword.getText();
        userName=txtUserName.getText();

        txtUserName.clear();
        txtPassword.clear();
        txtPasswordReEnter.clear();
        txtPasswordReEnter.setVisible(false);

        btnRegister.setVisible(false);
        btnLogin.setVisible(true);
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        if (txtUserName.getText().equals(userName) && txtPassword.getText().equals(password)){
            //navigate to welcome form
        } else {
            new Alert(Alert.AlertType.ERROR, "User name and Password mismatched").showAndWait();
        }
    }
}
