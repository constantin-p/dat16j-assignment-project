package assignment.widgets;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

    public NumberTextField(String text) {
        super(text);
    }

    public NumberTextField(int number) {
        super(String.valueOf(number));
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }
}
