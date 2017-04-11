/**
 * Created by sajith on 4/11/17.
 */
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.*;


public class Controller{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ebank";

    Connection connection=null;
    Statement stmnt=null;
    PreparedStatement preparedStatement=null;

    @FXML
    TextField txtSetUsername,txtSetPassword,txtSetPosition,txtSetName;
    @FXML
    JFXTextField txtuserName;
    @FXML
    JFXPasswordField txtpassword;
    @FXML
    TextField txtMark;
    @FXML
    Pane paneRoot;

    public void dbConn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Connecting to the database");
        connection = DriverManager.getConnection(DB_URL, "root", "");
        stmnt = connection.createStatement();
    }

    @FXML
    public void login(ActionEvent actionEvent) {
        try {
            dbConn();
            String sql;
            sql = "SELECT * FROM employees";
            ResultSet rs = stmnt.executeQuery(sql);
            while (rs.next()){
                String usrName=rs.getString("username");
                String password = rs.getString("password");
                if(txtuserName.getText().equals(usrName)&&txtpassword.getText().equals(password)){

                    System.out.println("logged");
                    Pane paneLogin = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                    /*FadeTransition ft = new FadeTransition(Duration.millis(150), paneLogin);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();*/
                    paneRoot.getChildren().setAll(paneLogin);
                    /*Scene mainPageScene = new Scene(custPage);
                    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    appStage.setScene(mainPageScene);*/
                    if(rs.getString("position").equals("clark")){
                        setTxt(false);
                    }else{
                        setTxt(true);
                    }
                    break;
                }
            }

            rs.close();
            stmnt.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setTxt(boolean val){
        txtMark.setEditable(val);
    }

    public void register(){
        try {
            dbConn();
            String sql="INSERT INTO employees VALUES(?,?,?,?)";
            System.out.println(txtSetName.getText());
            preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setString(1,txtSetName.getText());
            preparedStatement.setString(2,txtSetPosition.getText());
            preparedStatement.setString(3,txtSetUsername.getText());
            preparedStatement.setInt(4,Integer.parseInt(txtSetPassword.getText()));
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewRegisterMenu(ActionEvent actionEvent) throws IOException {

        Parent regPage = FXMLLoader.load(getClass().getResource("register.fxml"));
        FadeTransition ft = new FadeTransition(Duration.millis(150), regPage);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        Scene mainPageScene = new Scene(regPage);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(mainPageScene);
    }


}
