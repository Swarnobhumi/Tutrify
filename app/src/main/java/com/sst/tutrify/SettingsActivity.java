package com.sst.tutrify;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class SettingsActivity extends AppCompatActivity {
    TextView functionText, stringText, keyWardText;
    Button functionBtn, stringBtn, keyWardBtn, btnRestore;
    int initColorFunction, initColorKeyWard, initColorString;
   public  static final String KEY_FUNCTION = "funcColor";
   public  static final String KEY_KEYWORD = "keyColor";
   public static final String KEY_STRING = "stringColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        functionText = findViewById(R.id.functionColor);
        stringText = findViewById(R.id.stringColor);
        keyWardText = findViewById(R.id.keyWardColor);

        functionBtn = findViewById(R.id.btnFunColor);
        stringBtn = findViewById(R.id.btnStringColor);
        keyWardBtn = findViewById(R.id.btnKeyWardColor);
        btnRestore = findViewById(R.id.btnReset);

        SharedPreferences sharedPreferences = getSharedPreferences("CodeColor", MODE_PRIVATE);

        initColorFunction = sharedPreferences.getString(KEY_FUNCTION, "").equals("")? Color.parseColor("#d1ee78"): Color.parseColor(sharedPreferences.getString("funcColor", ""));
        initColorKeyWard = sharedPreferences.getString(KEY_KEYWORD, "").equals("")? Color.parseColor("#cd83d4"): Color.parseColor(sharedPreferences.getString("keyColor", ""));
        initColorString = sharedPreferences.getString(KEY_STRING, "").equals("")? Color.parseColor("#51b33b"): Color.parseColor(sharedPreferences.getString("stringColor", ""));


        functionText.setTextColor(initColorFunction);
        stringText.setTextColor(initColorString);
        keyWardText.setTextColor(initColorKeyWard);

        functionBtn.setCompoundDrawableTintList(isColorDark(initColorFunction) ? ColorStateList.valueOf(Color.WHITE) : ColorStateList.valueOf(Color.BLACK));
        stringBtn.setCompoundDrawableTintList(isColorDark(initColorString) ? ColorStateList.valueOf(Color.WHITE) : ColorStateList.valueOf(Color.BLACK));
        keyWardBtn.setCompoundDrawableTintList(isColorDark(initColorKeyWard) ? ColorStateList.valueOf(Color.WHITE) : ColorStateList.valueOf(Color.BLACK));


        functionBtn.setBackgroundTintList(ColorStateList.valueOf(initColorFunction));
        stringBtn.setBackgroundTintList(ColorStateList.valueOf(initColorString));
        keyWardBtn.setBackgroundTintList(ColorStateList.valueOf(initColorKeyWard));


        functionBtn.setOnClickListener(v -> setUpColorDialog("Select color for Functions", functionBtn, functionText, initColorFunction, KEY_FUNCTION));

        stringBtn.setOnClickListener(v -> setUpColorDialog("Select color for Strings", stringBtn, stringText, initColorString, KEY_STRING));


        keyWardBtn.setOnClickListener(v -> setUpColorDialog("Select color for Keywords",keyWardBtn, keyWardText, initColorKeyWard, KEY_KEYWORD));

        btnRestore.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_KEYWORD, "");
            editor.putString(KEY_STRING, "");
            editor.putString(KEY_FUNCTION, "");
            editor.apply();

            functionBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#d1ee78")));
            functionText.setTextColor(Color.parseColor("#d1ee78"));

           stringBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#51b33b")));
           stringText.setTextColor(Color.parseColor("#51b33b"));

            keyWardBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#cd83d4")));
            keyWardText.setTextColor(Color.parseColor("#cd83d4"));

        });



    }
    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        // It's a dark color
        return !(darkness < 0.5); // It's a light color
    }


    public void setUpColorDialog(String heading, Button button,TextView textView, int initColor, String key){
        ColorPickerDialogBuilder
                .with(SettingsActivity.this)
                .setTitle(heading)
                .initialColor(initColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(selectedColor -> {

                })
                .setPositiveButton("Confirm", (d, lastSelectedColor, allColors) -> {
                    if(isColorDark(Color.parseColor("#"+Integer.toHexString(lastSelectedColor)))){
                        button.setCompoundDrawableTintList(ColorStateList.valueOf(Color.WHITE));
                    }else{
                        button.setCompoundDrawableTintList(ColorStateList.valueOf(Color.BLACK));
                    }
                    button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#"+Integer.toHexString(lastSelectedColor))));
                    textView.setTextColor(Color.parseColor("#"+Integer.toHexString(lastSelectedColor)));

                    SharedPreferences sharedPreferences = getSharedPreferences("CodeColor", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(key, "#"+Integer.toHexString(lastSelectedColor));
                    editor.apply();

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .build()
                .show();
           }
    }



