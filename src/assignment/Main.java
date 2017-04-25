package assignment;

import assignment.core.LoginController;
import assignment.core.RegisterController;
import assignment.core.TournamentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Stage authStage;
    private Parent rootLayout;




    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TODO: Change this title based on the selected tab");

       // initRootLayout();
//        showTournamentsUI();
        showAuthLoginLayout();
    }

    private boolean showAuth()
    {


        try {
            rootLayout = (Parent) FXMLLoader.load(getClass().getResource("view/login.fxml"));

            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(300);
            primaryStage.setTitle("Auth");
            primaryStage.show();

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showAuthLoginLayout(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login.fxml"));
            Parent layout = loader.load();

            LoginController controller = loader.<LoginController>getController();
            controller.init(this);
            primaryStage.setScene(new Scene(layout));

            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(300);
            primaryStage.setTitle("Login");
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAuthRegisterLayout(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/register.fxml"));
            Parent layout = loader.load();

            RegisterController controller = loader.<RegisterController>getController();
            controller.init(this);
            primaryStage.setScene(new Scene(layout));

            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(300);
            primaryStage.setTitle("Login");
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void initRootLayout() {
        try {
            rootLayout = (Parent) FXMLLoader.load(getClass().getResource("view/root.fxml"));

            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(400);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
