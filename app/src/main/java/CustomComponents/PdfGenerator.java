package CustomComponents;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static void generatePdf(Context context, View layoutView, String fileName) {


        PdfDocument pdfDocument = new PdfDocument();
        PageInfo pageInfo = new PageInfo.Builder(layoutView.getWidth(), layoutView.getHeight(), 1).create();
        Page page = pdfDocument.startPage(pageInfo);

        layoutView.draw(page.getCanvas());

        pdfDocument.finishPage(page);

        // Define the file path for the PDF
        File pdfFile = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            pdfFile = new File(context.getFilesDir(), "/"+fileName);
        }


        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();
            Toast.makeText(context, pdfFile.toString()+"loop", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");

// Get the PDF file URI using FileProvider
            Uri pdfUri = FileProvider.getUriForFile(context, "com.sst.tutrify.fileprovider", new File(pdfFile.toString()), fileName);

            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);

// Optionally, you can add a message to the share dialog.
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this PDF!");

// Set the package name for WhatsApp to ensure it opens directly.
            shareIntent.setPackage("com.whatsapp");

            context.startActivity(Intent.createChooser(shareIntent, "Share PDF"));

        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("kon", e.getMessage());
        }
    }
}
