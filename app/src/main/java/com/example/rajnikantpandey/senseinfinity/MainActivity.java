package com.example.rajnikantpandey.senseinfinity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity{
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode,mobile,encodedString,image4,img1_1;
    TextView tv_sd_uid,tv_sd_name,tv_sd_gender,tv_sd_yob,tv_sd_co,tv_sd_vtc,tv_sd_po,tv_sd_dist,
            tv_sd_state,tv_sd_pc,tv_cancel_action,imagetext;
    String img1,img2,img3;
    StringBuffer totalimg;
    ImageView imageView,Aadharphoto;
    Bitmap image;
    Bitmap decodedBitmap;
    String   pureBase64Encoded;
    Bitmap decodedImage1,decodedImage2;
    String str1;
     byte[] decodedBytes;
    LinearLayout ll_scanned_data_wrapper,ll_data_wrapper,ll_action_button_wrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagetext=(TextView) findViewById(R.id.imagetext);
        tv_sd_uid = (TextView)findViewById(R.id.tv_sd_uid);
        tv_sd_name = (TextView)findViewById(R.id.tv_sd_name);
        tv_sd_gender = (TextView)findViewById(R.id.tv_sd_gender);
        tv_sd_yob = (TextView)findViewById(R.id.tv_sd_yob);
        tv_sd_co = (TextView)findViewById(R.id.tv_sd_co);
        tv_sd_vtc = (TextView)findViewById(R.id.tv_sd_vtc);
        tv_sd_po = (TextView)findViewById(R.id.tv_sd_po);
        tv_sd_dist = (TextView)findViewById(R.id.tv_sd_dist);
        tv_sd_state = (TextView)findViewById(R.id.tv_sd_state);
        tv_sd_pc = (TextView)findViewById(R.id.tv_sd_pc);
        tv_cancel_action = (TextView)findViewById(R.id.tv_cancel_action);
        ll_scanned_data_wrapper = (LinearLayout)findViewById(R.id.ll_scanned_data_wrapper);
        ll_data_wrapper = (LinearLayout)findViewById(R.id.ll_data_wrapper);
        ll_action_button_wrapper = (LinearLayout)findViewById(R.id.ll_action_button_wrapper);
        Aadharphoto=findViewById(R.id.Aadharphoto);
    }
    public void scanNow(View view)
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            return;
        }

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan a Aadharcard QR Code");
        integrator.setBeepEnabled(false);
        integrator.setCaptureActivity(BarCodeScanner.class);
        integrator.setOrientationLocked(true);
        //integrator.setResultDisplayDuration(500);
        integrator.setCameraId(0);// Use a specific camera of the device
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(scanningResult!=null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            if (scanContent != null && !scanContent.isEmpty()) {
                processScannedData(scanContent);
            } else {
                Toast.makeText(MainActivity.this, "scan cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        else{
                Toast.makeText(MainActivity.this,"No scan Data Received",Toast.LENGTH_SHORT).show();
            }
       // super.onActivityResult(requestCode, resultCode, data);
    }
    public void processScannedData(String scanData)
    {
        XmlPullParserFactory pullParserFactory;
        try {
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));

            // parse the XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("Rajdeol","Start document");
                }
                /*else if(eventType == XmlPullParser.START_TAG && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    uid = parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR);
                    //name
                    name = parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR);
                    //gender
                    gender = parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR);
                    // year of birth
                    yearOfBirth = parser.getAttributeValue(null,DataAttributes.AADHAR_YOB_ATTR);
                    // care of
                    careOf = parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR);
                    // village Tehsil
                    villageTehsil = parser.getAttributeValue(null,DataAttributes.AADHAR_VTC_ATTR);
                    // Post Office
                    postOffice = parser.getAttributeValue(null,DataAttributes.AADHAR_PO_ATTR);
                    // district
                    district = parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR);
                    // state
                    state = parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR);
                    // Post Code
                    postCode = parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR);*/
                else if(eventType == XmlPullParser.START_TAG && DataAttributes1.AADHAAR_DATA_TAG1.equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    name = parser.getAttributeValue(null,DataAttributes1.AADHAR_NAME_ATTR1);
                    //name
                    uid = parser.getAttributeValue(null,DataAttributes1.AADHAR_UID_ATTR1);
                    //gender
                    gender = parser.getAttributeValue(null,DataAttributes1.AADHAR_GENDER_ATTR1);
                    // year of birth
                    mobile = parser.getAttributeValue(null,DataAttributes1.AADHAR_MOBILE_ATTR1);
                    // care of
                    yearOfBirth = parser.getAttributeValue(null,DataAttributes1.AADHAR_YOB_ATTR1);

                   img1 = parser.getAttributeValue(null,DataAttributes1.AADHAR_IMAGE_ATTR1);
                    Log.d("img1",img1);
                  //  img2 = parser.getAttributeValue(null,DataAttributes1.AADHAR_IMAGE_ATTR2);
                  //  img3 = parser.getAttributeValue(null,DataAttributes1.AADHAR_IMAGE_ATTR3);
                    //totalimg.append(img2);
                  //  totalimg.append(img3);
img1_1="AAAADGpQICANCocKAAAAFGZ0eXBqcDIgAAAAAGpwMiAAAAAtanAyaAAAABZpaGRyAAAAyAAAAKAAAwcHAAAAAAAPY29scgEAAAAAABAAAAG3anAyY/9P/1EALwAAAAAAoAAAAMgAAAAAAAAAAAAAAKAAAADIAAAAAAAAAAAAAwcBAQcBAQcBAf9SAAwAAAABAQUEBAAA/1wAI0JvGG7qbupuvGcAZwBm4l9MX0xfZEgDSANIRU/ST9JPYf9kACIAAUNyZWF0ZWQgYnk6IEpKMjAwMCB2ZXJzaW9uIDQuMf+QAAoAAAAAASMAAf9SAAwAAAABAQUEBAAA/5PH0GgdhnysBnXUemUtxgyA/jf9VbU+FlKeXLtF2cHxDTWKcZw3kO6emKk3oJHB8Q44OS75TbOizl6xxrUmncPkJQ+UjB8Q8ARi2l0HFLQ/HtPDOx9pHu8ATRsMWl851tu/7a/cPYxeeoAhFd84O2NkEpN8g1M0p8mZwHCANLBQHMB0cCQECGxZoRTD4WIfEeg6aEnxKjNSgxYiX5TlRrijfSeb1EqCD3ooHiszXIrP6rmZoiOcIesIaIVoxSVV5wbB8GcEJQdzxR6BQ4YvUZVGjQPwNkKAgMOqw6cAmJ0hdvaO1LUIGcOy/iJCAVexOHBtQsqlrMWDznieM6w6lgiAgICAgICAgP/Z";

                    // village Tehsil
                    //address = parser.getAttributeValue(null,DataAttributes1.AADHAR_ADDRESS_ATTR1);
                    // Post Office
                   // district = parser.getAttributeValue(null,DataAttributes1.AADHAR_IMAGE_ATTR2);
                   // encodedString = totalimg.toString();
                 //   Log.d("img3",img3);
                 //   Log.d("encodedString",encodedString);
                   //  str1=getResources().getString(R.string.BarCode);
                   // pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
                   // decodedBytes = Base64.decode(str1,Base64.URL_SAFE);
                   //  decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                   //  Log.d("tag",decodedBitmap.toString());
                   //  decodedBitmap=Bitmap.createScaledBitmap(decodedBitmap,120,120);
                    //encode image to base64 string
                   // ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aadhaar_logo);
                  //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                  //  byte[] imageBytes = baos.toByteArray();
                   // String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                   //  String imageString1=getResources().getString(R.string.aadhar3);
                   // Log.d("aadhar logo",imageString1);
                    //decode base64 string to image
                   byte[] imageBytes1 = Base64.decode(img1, Base64.DEFAULT);
                     decodedImage1 = BitmapFactory.decodeByteArray(imageBytes1, 0, imageBytes1.length);
                   // image.setImageBitmap(decodedImage);


                } else if(eventType == XmlPullParser.END_TAG) {
                    Log.d("Rajdeol","End tag "+parser.getName());

                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d("Rajdeol","Text "+parser.getText());

                }
                // update eventType
                eventType = parser.next();
            }

            // display the data on screen
            displayScannedData();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void displayScannedData(){
        ll_data_wrapper.setVisibility(View.GONE);
        ll_scanned_data_wrapper.setVisibility(View.VISIBLE);
        ll_action_button_wrapper.setVisibility(View.VISIBLE);

        // clear old values if any
        tv_sd_uid.setText("");
        tv_sd_name.setText("");
        tv_sd_gender.setText("");
        tv_sd_yob.setText("");
        tv_sd_co.setText("");
        tv_sd_vtc.setText("");
        tv_sd_po.setText("");
        tv_sd_dist.setText("");
        tv_sd_state.setText("");
        tv_sd_pc.setText("");

        // update UI Elements
       // StringBuffer stringBuffer=new StringBuffer(uid);
       // stringBuffer.replace(0,8,"********");
        tv_sd_uid.setText(uid);
        tv_sd_name.setText(name);
        tv_sd_gender.setText(gender);
        tv_sd_yob.setText(yearOfBirth);
        //tv_sd_co.setText(careOf);
       // tv_sd_vtc.setText(villageTehsil);
        //tv_sd_po.setText(postOffice);
        tv_sd_dist.setText(district);
        tv_sd_state.setText(mobile);
        Aadharphoto.setImageBitmap(decodedImage1);
        Aadharphoto.setImageBitmap(Bitmap.createScaledBitmap(decodedImage1,Aadharphoto.getWidth(),Aadharphoto.getHeight(),true));
        //imagetext.setText(encodedString);
       // tv_sd_pc.setText(postCode);
    }
  /*  public void saveData(View view)
    {
        SharedPreferences sharedPreferences=getSharedPreferences(uid,this.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
       // ModelClass modelClass=new ModelClass(uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode);
        editor.putString("uid",uid);
        editor.putString("name",name);
        editor.putString("gender",gender);
        editor.putString("yearOfBirth",yearOfBirth);
        editor.putString("careOf",careOf);
        editor.putString("villageTehsil",villageTehsil);
        editor.putString("postOffice",postOffice);
        editor.putString("district",district);
        editor.putString("state",state);
        editor.putString("postCode",postCode);
        editor.commit();
        Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show();
    }
    public void showSavedCards(View view)
    {
        Intent intent=new Intent(this,SavedDataActivity.class);
        startActivity(intent);
    }
    public void showHome(View view)
    {
        ll_data_wrapper.setVisibility(View.VISIBLE);
        ll_scanned_data_wrapper.setVisibility(View.GONE);
        ll_action_button_wrapper.setVisibility(View.GONE);
    }*/

}
