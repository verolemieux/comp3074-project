package ca.georgebrown.comp3074.project.Item;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.google.zxing.WriterException;

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
    public final static int QRcodeWidth = 500 ;
    TextView itemName;
    Bitmap bitmap;

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
        Button btnSave = findViewById(R.id.btnSave);
        itemName = findViewById(R.id.txtItemName);
        final TextView itemDesc = findViewById(R.id.txtItemDescription);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        final ArrayList<Item> list = (ArrayList<Item>)getIntent().getSerializableExtra("ListItems");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(view.getContext(), ItemsActivity.class);
                addItemIntent.putExtra("ValidatedUser", validatedUser);

                addItem.setItem_Name(itemName.getText().toString());
                addItem.setDescription(itemDesc.getText().toString());
                itemTable.addItem(addItem, validatedUser);
                setResult(1, addItemIntent);
                finish();
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
        addQRCode = findViewById(R.id.btnAddQRCode);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void dispatchTakePictureIntent() {
        final int MY_CAMERA_REQUEST_CODE = 100;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);  }
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
                        getResources().getColor(R.color.colorAccent):getResources().getColor(R.color.colorPrimary);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
