package com.sst.tutrify;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import CustomComponents.SyntaxHighlightEditText;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class ComplierPage extends AppCompatActivity {
    BlurView blurView;
    ImageView btnRunCode;
    TextView outPutArea;

    SyntaxHighlightEditText codeEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complier_page);

        blurView = findViewById(R.id.blurViewComplier);
        btnRunCode = findViewById(R.id.btn_run_code);
        outPutArea = findViewById(R.id.outPutArea);
        codeEditText = findViewById(R.id.codeEditText);



        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(ComplierPage.this));
        }

        float radius = 10f;

        View decorView = getWindow().getDecorView();
        // ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);

        // Optional:
        // Set drawable to draw in the beginning of each blurred frame.
        // Can be used in case your layout has a lot of transparent space and your content
        // gets a too low alpha value after blur is applied.
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView, new RenderScriptBlur(getApplicationContext())) // or RenderEffectBlur
                .setFrameClearDrawable(windowBackground) // Optional
                .setBlurRadius(radius);

        btnRunCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pythonCode = codeEditText.getText().toString();
                executePythonCode(pythonCode);
            }
        });
    }

    private void executePythonCode(String code) {
        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("scripts").callAttr("main", code);
        outPutArea.setText(pyObject.toString());

         }

    }

