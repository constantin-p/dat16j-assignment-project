package assignment;

import assignment.core.CrashController;
import assignment.core.LoginController;
import assignment.core.RegisterController;
import assignment.core.RootController;
import assignment.model.AuthAccount;
import assignment.util.AuthManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public Stage primaryStage;
    private AuthManager authManager = new AuthManager();


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        // Catch unhandled errors and display the crash
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            initCrashLayout(throwable);
            System.out.println("Handler caught exception: " + throwable.getMessage());
        });

        try {
            initAuthLoginLayout();
        } catch (Throwable throwable) {
            System.out.println("Handler caught exception: " + throwable.getMessage());
            throwable.printStackTrace();

            initCrashLayout(throwable);
        }
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/root.fxml"));
            Parent layout = loader.load();

            primaryStage.setScene(new Scene(layout));

            RootController controller = loader.getController();
            controller.initData(this);

            this.primaryStage.setTitle("Tournaments app");
            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(400);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initAuthLoginLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login.fxml"));
            Parent layout = loader.load();

            primaryStage.setScene(new Scene(layout));

            LoginController controller = loader.getController();
            controller.initData(authManager, () -> initAuthRegisterLayout(), () -> initRootLayout());

            this.primaryStage.setTitle("Login");
            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(300);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initAuthRegisterLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/register.fxml"));
            Parent layout = loader.load();

            primaryStage.setScene(new Scene(layout));

            RegisterController controller = loader.getController();
            controller.initData(authManager, () -> initAuthLoginLayout());

            this.primaryStage.setTitle("Login");
            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(300);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initCrashLayout(Throwable throwable) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/crash.fxml"));
            Parent layout = loader.load();

            primaryStage.setScene(new Scene(layout));

            CrashController controller = loader.getController();
            controller.initData(throwable);

            primaryStage.setTitle("Problem report for Tournament app");
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
