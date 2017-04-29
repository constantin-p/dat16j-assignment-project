package assignment.core;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;


public class CrashController {

    private Throwable throwable;

    @FXML
    private TextArea crashTextArea;

    @FXML
    private void initialize() {
    }

    public void initData(Throwable throwable) {
        this.throwable = throwable;
        showReport();
    }

    private void showReport() {
        StringWriter stackTraceWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTraceWriter));
        crashTextArea.setText(
                "OS Version:          " + System.getProperty("os.name") + " " +
                        System.getProperty("os.version") +
                "\n" +
                "System architecture: " + System.getProperty("os.arch") +
                "\n" +
                "Java Version:        " + System.getProperty("java.version") +
                "\n\n" +
                throwable.toString() +
                "\n\n" +
                stackTraceWriter.toString()
        );
    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void handleSendAction(ActionEvent event) {
        // TODO: Send report
        System.out.println("Send report");
        Platform.exit();
    }
}
