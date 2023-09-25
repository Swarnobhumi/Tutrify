package CustomComponents;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

import com.amrdeveloper.codeview.CodeView;
import com.sst.tutrify.SettingsActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxHighlightEditText extends CodeView {

    String[] keywords = {"False", "None", "True", "and", "as", "assert", "async", "await", "break", "class",
            "continue", "def", "del", "elif", "else", "except", "finally", "for", "from", "global",
            "if", "import", "in", "is", "lambda", "nonlocal", "not", "or", "pass", "raise", "return",
            "try", "while", "with", "yield"};


    String[] pythonFunctions = { "abs", "all", "any", "bin", "bool", "callable", "chr", "classmethod", "compile",
            "complex", "delattr", "dict", "dir", "divmod", "enumerate", "eval", "filter", "float", "format", "frozenset",
            "getattr", "globals", "hasattr", "hash", "help", "hex", "id", "input", "int", "isinstance", "issubclass", "iter", "len",
            "list", "locals", "map", "max", "memoryview", "min", "next", "object", "oct", "open", "ord", "pow", "print", "property",
            "range", "repr", "reversed", "round", "set", "setattr", "slice", "sorted", "staticmethod", "str", "sum", "super", "tuple",
            "type", "vars", "zip", "__import__" };

    String regexKeyword = "\\b(" + String.join("|", keywords) + ")\\b";
    String regexFunction =  "\\b(" + String.join("|", pythonFunctions) + ")\\b";


    private Pattern keywordsPattern = Pattern.compile(regexKeyword);
    private  Pattern functionPattern = Pattern.compile(regexFunction);

    private Pattern stringPattern = Pattern.compile("\"(.*?)\"");
    Pattern commentPattern = Pattern.compile("#.*$", Pattern.MULTILINE);




    public SyntaxHighlightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Here are the pairComplete map
        Map<Character, Character> pairCompleteMap = new HashMap<>();
        pairCompleteMap.put('{', '}');
        pairCompleteMap.put('[', ']');
        pairCompleteMap.put('(', ')');
        pairCompleteMap.put('<', '>');
        pairCompleteMap.put('"', '"');
        pairCompleteMap.put('\'', '\'');


        // initial codeView
      setPairCompleteMap(pairCompleteMap);
       enablePairComplete(true);
      enablePairCompleteCenterCursor(true);
        init();

    }

    private void init() {
        addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            applySyntaxHighlighting(editable);
        }
    };

    private void applySyntaxHighlighting(Editable editable) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CodeColor", Context.MODE_PRIVATE);
        String keyWordColor = sharedPreferences.getString(SettingsActivity.KEY_KEYWORD, "").equals("")?"#cd83d4":sharedPreferences.getString(SettingsActivity.KEY_KEYWORD, "");
        String stringColor = sharedPreferences.getString(SettingsActivity.KEY_STRING, "").equals("")?"#51b33b":sharedPreferences.getString(SettingsActivity.KEY_STRING, "");
        String funcColor = sharedPreferences.getString(SettingsActivity.KEY_FUNCTION, "").equals("")?"#d1ee78":sharedPreferences.getString(SettingsActivity.KEY_FUNCTION, "");

        // Clear existing spans
        ForegroundColorSpan[] spans = editable.getSpans(0, editable.length(), ForegroundColorSpan.class);
        for (ForegroundColorSpan span : spans) {
            editable.removeSpan(span);
        }

        // Apply keyword highlighting
        Matcher keywordsMatcher = keywordsPattern.matcher(editable);
        while (keywordsMatcher.find()) {
            int start = keywordsMatcher.start();
            int end = keywordsMatcher.end();
            editable.setSpan(new ForegroundColorSpan(Color.parseColor(keyWordColor)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        // Apply string highlighting
        Matcher stringMatcher = stringPattern.matcher(editable);
        while (stringMatcher.find()) {
            int start = stringMatcher.start(1); // Group 1 captures the actual string
            int end = stringMatcher.end(1);
            editable.setSpan(new ForegroundColorSpan(Color.parseColor(stringColor)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        //Apply function highlighting
        Matcher functionMatcher = functionPattern.matcher(editable);
        while (functionMatcher.find()) {
            int start = functionMatcher.start();
            int end = functionMatcher.end();
            editable.setSpan(new ForegroundColorSpan(Color.parseColor(funcColor)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        String text = editable.toString();
        Matcher matcher = commentPattern.matcher(text);

        while (matcher.find()) {
            int commentStart = matcher.start();
            int commentEnd = matcher.end();

            editable.setSpan(
                    new ForegroundColorSpan(0xFF808080), // Gray color for comments
                    commentStart,
                    commentEnd,
                    0
            );

        }


    }
}
