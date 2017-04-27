package assignment;

import assignment.core.CrashController;
import assignment.core.RootController;
import assignment.db.Database;
import assignment.util.Auth;
import assignment.util.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public Stage primaryStage;



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Catch unhandled errors and display the crash
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            initCrashLayout(throwable);
            System.out.println("Handler caught exception: " + throwable.getMessage());
        });

        try {
            initRootLayout();
        } catch (Throwable throwable) {
            System.out.println("Handler caught exception: " + throwable.getMessage());
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

    }

    public void initAuthRegisterLayout() {

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