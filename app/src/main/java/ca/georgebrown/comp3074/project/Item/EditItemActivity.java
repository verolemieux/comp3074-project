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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.net.URI;
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
    static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    static final int STORAGE_PERMISSION_REQUEST_CODE = 200;
    public final static int QRcodeWidth = 500 ;
    ImageView qrCode;
    ImageView imgItem;
    Bitmap bitmap;
    ImageButton addQRCode;
    ImageButton addPhoto;
    Bitmap qrBM;
    Button exportQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_edit_item, null, false);
        drawer.addView(contentView, 0);

        itemTable = new ItemsDAO(this);
        exportQR = findViewById(R.id.btnExport);
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

        final byte[] qrArr = editItem.getItem_QR_Code();

        if(qrArr != null) {
            qrBM = BitmapFactory.decodeByteArray(qrArr, 0, qrArr.length);
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
                /*if(!itemName.getText().toString().equals("") && !itemName.getText().equals(null)) {
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
                }*/


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("application/image");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"thibeau.jeremy@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                i.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(qrBM)));
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(EditItemActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        exportQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(qrBM != null)
                {
                    if (checkPermission("storage")) {
                        exportCode();
                    } else {
                        requestPermission("storage");
                    }
                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), qrBM,"title", null);
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
                        Toast.makeText(EditItemActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
                if(qrBM== null)
                {
                    Toast.makeText(EditItemActivity.this, "There is no QR Code generated", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemName.getText().toString().equals("")) {
                    Toast.makeText(EditItemActivity.this, "Item name cannot be blank", Toast.LENGTH_SHORT).show();
                } else {
                    ///////////////
                    if (!itemName.getText().toString().equals("") && !itemName.getText().equals(null)) {
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
    /*private void GrantReadStoragePermission(){
        if(Build.VERSION.SDK_INT >=23){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
            }
        }
    }*/
    private void exportCode(){
        if(qrBM != null)
        {
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), qrBM,"title", null);
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
                Toast.makeText(EditItemActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(EditItemActivity.this, "There is no QR Code generated", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        if (permission.equals("storage")){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            return true;
        }
        return false;
    }
    private void requestPermission(String permission) {
        if (permission.equals("storage")){
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


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
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
