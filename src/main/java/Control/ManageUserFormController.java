package Control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import redis.clients.jedis.Jedis;
import util.User;

import java.io.IOException;
import java.util.Set;

public class ManageUserFormController {
    public JFXButton btnAdd;
    public JFXTextField txtName;
    public JFXTextField txtPassword;
    public JFXTextField txtSearch;
    public TableView<User> tblUse;

    private  Jedis jedis ;


    public void initialize() throws IOException {
        var redisConfFilePath = System.getProperty("user.dir") + "/redis.conf";
        Runtime.getRuntime().exec(new String[]{"redis-server",redisConfFilePath});

        tblUse.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUse.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("password"));

         jedis =  new Jedis("127.0.0.1",9099);



        Set<String> keys = jedis.keys("*");
        for (String name : keys) {
            tblUse.getItems().add(new User(name,jedis.get(name)));
        }

        Platform.runLater(() -> {
            btnAdd.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    jedis.shutdown();
                }
            });
        });

    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        tblUse.getItems().add(new User(txtName.getText(),txtPassword.getText()));
        jedis.set(txtName.getText(),txtPassword.getText());
        txtName.clear();
        txtPassword.clear();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }
}
