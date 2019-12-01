package ca.georgebrown.comp3074.project.Item;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import ca.georgebrown.comp3074.project.Backpack.AddBackpackActivity;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class AddItemActivity extends BaseActivity {

    ImageButton addPhoto;
    ImageView imgItem;
    ImageView imgQRCode;
    ImageButton addQRCode;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    static final int STORAGE_PERMISSION_REQUEST_CODE = 200;
    public final static int QRcodeWidth = 500 ;
    TextView itemName;
    Bitmap bitmap;
    Button btnExport;
    Button btnSave;

    ItemsDAO itemTable;
    Item addItem = new Item(1, "", "");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_add_item, null, false);
        drawer.addView(contentView, 0);
        itemTable = new ItemsDAO(this);
        addPhoto = findViewById(R.id.btnAddPhoto);
        imgItem = findViewById(R.id.imgItem);
        imgQRCode =  findViewById(R.id.imgQrCode);
        btnSave = findViewById(R.id.btnSave);
        btnExport = findViewById(R.id.btnExport);
        addQRCode = findViewById(R.id.btnAddQRCode);
        itemName = findViewById(R.id.txtItemName);
        final TextView itemDesc = findViewById(R.id.txtItemDescription);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        final ArrayList<Item> list = (ArrayList<Item>)getIntent().getSerializableExtra("ListItems");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(view.getContext(), ItemsActivity.class);
                addItemIntent.putExtra("ValidatedUser", validatedUser);

                if(itemName.getText().toString().equals(""))
                {
                    Toast.makeText(AddItemActivity.this, "Item name cannot be blank", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addItem.setItem_Name(itemName.getText().toString());
                    addItem.setDescription(itemDesc.getText().toString());
                    itemTable.addItem(addItem, validatedUser);
                    setResult(1, addItemIntent);
                    finish();
                }

            }
        });

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission("storage")) {
                    exportCode();
                } else {
                    requestPermission("storage");
                }
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        addQRCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(!itemName.getText().toString().equals("") && !itemName.getText().equals(null)) {
                    try {
                        bitmap = TextToImageEncode(itemName.getText().toString());

                        imgQRCode.setImageBitmap(bitmap);

                        ByteArrayOutputStream bosQR = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bosQR);
                        byte[] qrArray = bosQR.toByteArray();

                        addItem.setItem_QR_Code(qrArray);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        if (checkPermission("camera")) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            requestPermission("camera");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgItem.setImageBitmap(imageBitmap);

            ByteArrayOutputStream bosPic = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bosPic);
            byte[] picArray = bosPic.toByteArray();

            addItem.setItem_Picture(picArray);
        }
    }

    private void exportCode(){
        if(bitmap != null)
        {
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"title", null);
            Log.d("Path", path);
            Uri screenshotUri = Uri.parse(path);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setType("application/image");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{validatedUser.getEmail()});
            i.putExtra(Intent.EXTRA_SUBJECT, "QR Code");
            i.putExtra(Intent.EXTRA_TEXT   , "This is the QR Code for " + itemName.getText().toString());
            i.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(AddItemActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(AddItemActivity.this, "There is no QR Code generated", Toast.LENGTH_SHORT).show();
        }
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public boolean checkPermission(String permission){
        if (permission == "camera") {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            return true;
        }
        if (permission == "storage"){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            return true;
        }
        return false;
    }

    private void requestPermission(String permission) {
        if (permission == "camera") {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
        if (permission == "storage"){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    exportCode();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
