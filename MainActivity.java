package app.dev.idverification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_GALLERY_PERMISSION = 201;
    private static final int REQUEST_CAMERA_IMAGE = 100;
    private static final int REQUEST_GALLERY_IMAGE = 101;

    Button buttonCapture;
    TextView textViewData;
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCapture = findViewById(R.id.button_capture);
        textViewData = findViewById(R.id.text_data);
        imageView  = findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.id_scan);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALLERY_PERMISSION);
        }

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    openCamera();
                } else if (options[item].equals("Choose from Gallery")) {
                    openGallery();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA_IMAGE);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA_IMAGE) {
                bitmap = (Bitmap) data.getExtras().get("data");
                recognizeTextFromImage();
            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    recognizeTextFromImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void recognizeTextFromImage() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            stringBuilder.append(textBlock.getValue()).append("\n");
        }
        textRecognizer.release();
//        textViewData.setText(stringBuilder.toString());
        String targetText = "neil gogte";
        targetText = targetText.toLowerCase(Locale.ROOT);
        String OCRResult = stringBuilder.toString();
        if (OCRResult.toLowerCase(Locale.ROOT).contains("neil") || OCRResult.toLowerCase(Locale.ROOT).contains("gogte") || OCRResult.toLowerCase(Locale.ROOT).contains("ngit")) {
            //textViewData.setText("\u2714");
            textViewData.setTextColor(getResources().getColor(R.color.green));
            imageView.setImageResource(R.drawable.green_tick1);
            textViewData.setText("VALID");// prints a green tick (✔)
        } else {
            //textViewData.setText("\u2718");
            textViewData.setTextColor(getResources().getColor(R.color.red));
            imageView.setImageResource(R.drawable.red_cross1);
            textViewData.setText("INVALID");// prints a red cross (❌)
        }


    }


}

