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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EditItemActivity extends BaseActivity {

    TextView itemName;
    TextView itemDesc;
    Item editItem;
    User validatedUser;
    ItemsDAO itemTable;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static int QRcodeWidth = 500 ;
    ImageView qrCode;
    ImageView imgItem;
    Bitmap bitmap;
    ImageButton addQRCode;
    ImageButton addPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_edit_item, null, false);
        drawer.addView(contentView, 0);

        itemTable = new ItemsDAO(this);

        itemName = findViewById(R.id.txtItemName);
        itemDesc = findViewById(R.id.txtItemDescription);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        editItem = (Item) getIntent().getSerializableExtra("Item");
        itemName.setText(editItem.getItem_Name());
        itemDesc.setText(editItem.getDescription());
        imgItem = findViewById(R.id.imgItem);
        qrCode = findViewById(R.id.imgQrCode);
        addPhoto = findViewById(R.id.btnAddPhoto);
        addQRCode = findViewById(R.id.btnAddQRCode);

        byte[] qrArr = editItem.getItem_QR_Code();

        if(qrArr != null) {
            Bitmap qrBM = BitmapFactory.decodeByteArray(qrArr, 0, qrArr.length);
            qrCode.setImageBitmap(qrBM);
            editItem.setItem_QR_Code(qrArr);
        }

        addPhoto.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();
            }
        });

        byte[] itemPhoto = editItem.getItem_Picture();

        if(itemPhoto!= null)
        {
            Bitmap itemP = BitmapFactory.decodeByteArray(itemPhoto, 0, itemPhoto.length);
            imgItem.setImageBitmap(itemP);
            editItem.setItem_Picture(itemPhoto);
        }

        addQRCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(!itemName.getText().toString().equals("") && !itemName.getText().equals(null)) {
                    try {
                        bitmap = TextToImageEncode(itemName.getText().toString());

                        qrCode.setImageBitmap(bitmap);

                        ByteArrayOutputStream bosQR = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bosQR);
                        byte[] qrArray = bosQR.toByteArray();

                        editItem.setItem_QR_Code(qrArray);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///////////////
                if(!itemName.getText().toString().equals("") && !itemName.getText().equals(null)) {
                    try {
                        bitmap = TextToImageEncode(itemName.getText().toString());

                        qrCode.setImageBitmap(bitmap);

                        ByteArrayOutputStream bosQR = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bosQR);
                        byte[] qrArray = bosQR.toByteArray();

                        editItem.setItem_QR_Code(qrArray);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }

                ///////////////////


                editItem.setItem_Name(itemName.getText().toString());
                editItem.setDescription(itemDesc.getText().toString());
                Intent itemIntent = new Intent(v.getContext(), ItemsActivity.class);
                itemIntent.putExtra("ValidatedUser", validatedUser);
                itemTable.editItem(editItem, validatedUser, editItem.getItem_Id());
                setResult(2, itemIntent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemIntent = new Intent(v.getContext(), ItemsActivity.class);
                itemIntent.putExtra("ValidatedUser", validatedUser);
                itemTable.deleteItem(editItem.getItem_Id(), validatedUser);
                setResult(2, itemIntent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        final int MY_CAMERA_REQUEST_CODE = 100;
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

            editItem.setItem_Picture(picArray);
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
