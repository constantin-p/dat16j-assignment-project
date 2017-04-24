package assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Parent rootLayout;




    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TODO: Change this title based on the selected tab");

        initRootLayout();
//        showTournamentsUI();
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

//    public void showTournamentsUI() {
//        try {
//            // Load person overview.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
//            AnchorPane personOverview = (AnchorPane) loader.load();
//
//            // Load root layout from fxml file.
//            rootLayout = (BorderPane) FXMLLoader.load(getClass().getResource("view/root.fxml"));
//
//            // Set person overview into the center of root layout.
//            rootLayout.setCenter(personOverview);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    public static void main(String[] args) {
        launch(args);
    }
}
