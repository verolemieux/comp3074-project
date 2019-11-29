//package ca.georgebrown.comp3074.project.QRCode;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.google.zxing.Result;
//
//import java.util.logging.Logger;
//
//
//public class QrCodeScanner extends AppCompatActivity
//    implements ZXingScannerView.ResultHandler {
//
//    private ZXingScannerView mScannerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mScannerView = new ZXingScannerView(this);
//        setContentView(mScannerView);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        // Register ourselves as a handler for scan results.
//        mScannerView.setResultHandler(this);
//        // Start camera on resume
//        mScannerView.startCamera();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        // Stop camera on pause
//        mScannerView.stopCamera();
//    }
//
//    @Override
//    public void handleResult(Result rawResult) {
////        // Do something with the result here
////        // Prints scan results
////        Logger.verbose("result", rawResult.getText());
////        // Prints the scan format (qrcode, pdf417 etc.)
////        Logger.verbose("result", rawResult.getBarcodeFormat().toString());
////        //If you would like to resume scanning, call this method below:
////        //mScannerView.resumeCameraPreview(this);
//        Intent intent = new Intent();
//        intent.putExtra("SCAN_RESULT", rawResult.getText());
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//}
