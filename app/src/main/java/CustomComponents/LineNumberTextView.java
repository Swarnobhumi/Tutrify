package CustomComponents;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

public class LineNumberTextView extends androidx.appcompat.widget.AppCompatTextView {

    private TextView mainTextView;

    public LineNumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMainTextView(TextView mainTextView) {
        this.mainTextView = mainTextView;

        // Listen for text changes in the main TextView
        mainTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLineNumbers();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void updateLineNumbers() {
        if (mainTextView != null) {
            String text = mainTextView.getText().toString();
            String[] lines = text.split("\n");
            StringBuilder lineNumbers = new StringBuilder();

            for (int i = 1; i <= lines.length; i++) {
                lineNumbers.append(i).append("\n");
            }

            setText(lineNumbers.toString());
        }
    }
}
