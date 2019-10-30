package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedOutputStream;
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

import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class AddItemActivity extends AppCompatActivity {

    int maxId = 1;
    ImageButton addPhoto;
    ImageView imgItem;
    ImageView imgQRCode;
    ImageButton addQRCode;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static int QRcodeWidth = 500 ;
    TextView itemName;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
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
                for(Item i : list)
                {
                    maxId = i.maxValue(maxId, i.getItem_Id());
                }
                list.add(new Item(maxId+1, itemName.getText().toString(), itemDesc.getText().toString()));
                addItemIntent.putExtra("ValidatedUser", validatedUser);
                addItemIntent.putExtra("ListItems", list);
                setResult(1, addItemIntent);
                finish();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener()
        {
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
                try {
                    bitmap = TextToImageEncode(itemName.getText().toString());

                    imgQRCode.setImageBitmap(bitmap);
                    File file = new File("ca/georgebrown/comp3074/project/Item/Test.bmp");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();
                    /*File dir = new File("comp3074-project\\app\\src\\main\\Assets\\Test.bmp");
                    if(!dir.exists())
                    {
                        dir.mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(dir);{
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                    }*/
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgItem.setImageBitmap(imageBitmap);
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
}
