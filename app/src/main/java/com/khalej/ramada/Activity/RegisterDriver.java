package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;
import com.khalej.ramada.model.contact_general_user;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RegisterDriver extends AppCompatActivity {
    ImageView image,carid,driverLicencies;
    TextInputEditText name,email,type,model,password,phone;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ProgressDialog progressDialog;
    AppCompatButton confrim;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;
    private  static final int IMAGEA = 100;
    private  static final int IMAGEB = 99;
    private  static final int IMAGED = 97;
    private  static final int IMAGEC= 98;
    private static final int CAMERA_REQUEST_A = 1;
    private static final int CAMERA_REQUEST_B = 2;
    private static final int CAMERA_REQUEST_C = 3;
    private static final int CAMERA_REQUEST_D = 4;
    String imagePathA,imagePathB,imagePathC,imagePathD;
    String mediaPathA,mediaPathB,mediaPathC,mediaPathD;
    contact_general_user contactList;
    apiinterface_home apiinterface;
    Intent intent;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        setContentView(R.layout.activity_register_driver);
        Intialize();
        intent=getIntent();
        phone.setText(intent.getStringExtra("phone"));
        phone.setText("0106096779322");
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(RegisterDriver.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(RegisterDriver.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        image.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    showPictureDialogA();
                }
            }
        });
        carid.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    showPictureDialogB();
                }
            }
        });
        driverLicencies.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    showPictureDialogC();
                }
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()||email.getText().toString().isEmpty()||password.getText().toString().isEmpty()||
                type.getText().toString().isEmpty()||model.getText().toString().isEmpty()||phone.getText().toString().isEmpty()){
                    Toast.makeText(RegisterDriver.this,"قم بملئ الحقول الفارغه أولاً",Toast.LENGTH_LONG).show();
                    return;
                }
               fetchInfo();
            }
        });
    }
   public void Intialize(){
        image=findViewById(R.id.image);
        carid=findViewById(R.id.photo1);
        driverLicencies=findViewById(R.id.photo2);
        name=findViewById(R.id.textInputEditTextname);
        email=findViewById(R.id.textInputEditTextemail);
        password=findViewById(R.id.textInputEditTextpassword);
        type=findViewById(R.id.textInputEditTexttype);
        model=findViewById(R.id.textInputEditTextmodel);
       phone=findViewById(R.id.textInputEditTextphone);
        confrim=findViewById(R.id.appCompatButtonRegisterservcies); }
    private void showPictureDialogA(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallaryA();
                                break;
                            case 1:
                                takePhotoFromCameraA();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallaryA() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGEA);

    }

    private void takePhotoFromCameraA() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(RegisterDriver.this, getPackageName(), image);
            imagePathA=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_A);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void showPictureDialogB(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallaryB();
                                break;
                            case 1:
                                takePhotoFromCameraB();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallaryB() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGEB);

    }

    private void takePhotoFromCameraB() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(RegisterDriver.this, getPackageName(), image);
            imagePathB=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_B);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void showPictureDialogC(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallaryC();
                                break;
                            case 1:
                                takePhotoFromCameraC();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallaryC() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGEC);

    }

    private void takePhotoFromCameraC() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(RegisterDriver.this, getPackageName(), image);
            imagePathC=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_C);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_A && resultCode == Activity.RESULT_OK)
        {

            mediaPathA = imagePathA;
            String name=random();

            mediaPathA=resizeAndCompressImageBeforeSend(RegisterDriver.this,mediaPathA  ,name);

            image.setImageBitmap(BitmapFactory.decodeFile(mediaPathA));

        }
        if(requestCode == IMAGEA && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPathA = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            String namee=random();

            mediaPathA=resizeAndCompressImageBeforeSend(RegisterDriver.this,mediaPathA,namee);

            image.setImageBitmap(BitmapFactory.decodeFile(mediaPathA));
            cursor.close();
        }
        if (requestCode == CAMERA_REQUEST_B && resultCode == Activity.RESULT_OK)
        {

            mediaPathB = imagePathB;
            String name=random();

            mediaPathB=resizeAndCompressImageBeforeSend(RegisterDriver.this,mediaPathB  ,name);

           carid.setImageBitmap(BitmapFactory.decodeFile(mediaPathB));

        }
        if(requestCode == IMAGEB && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPathB = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            String namee=random();

            mediaPathB=resizeAndCompressImageBeforeSend(RegisterDriver.this,mediaPathB,namee);

            carid.setImageBitmap(BitmapFactory.decodeFile(mediaPathB));
            cursor.close();
        }
        if (requestCode == CAMERA_REQUEST_C && resultCode == Activity.RESULT_OK)
        {

            mediaPathC = imagePathC;
            String name=random();

            mediaPathC=resizeAndCompressImageBeforeSend(RegisterDriver.this,mediaPathC  ,name);

            driverLicencies.setImageBitmap(BitmapFactory.decodeFile(mediaPathC));

        }
        if(requestCode == IMAGEC && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPathC = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            String namee=random();

            mediaPathC=resizeAndCompressImageBeforeSend(RegisterDriver.this,mediaPathC,namee);

            driverLicencies.setImageBitmap(BitmapFactory.decodeFile(mediaPathC));
            cursor.close();
        }

    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    public static String resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName){
        final int MAX_IMAGE_SIZE = 300 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath,options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return  context.getCacheDir()+fileName;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag,"inSampleSize: "+inSampleSize);
        return inSampleSize;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                try {


                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST_User);
                }
                catch (Exception e){}
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void fetchInfo() {
        progressDialog = ProgressDialog.show(RegisterDriver.this, "جاري أنشاء حساب سائق", "Please wait...", false, false);
        progressDialog.show();
        String image="";
        File fileA = null;
        try{
            fileA = new File(mediaPathA);}
        catch (Exception e){
            Toast.makeText(RegisterDriver.this,"من فضلك قم بتحديد صوره شخصية",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        MultipartBody.Part fileToUploadA  = null;
        try {
            // Parsing any Media type file
            RequestBody requestBodyId = RequestBody.create(MediaType.parse("*/*"), fileA);
            fileToUploadA = MultipartBody.Part.createFormData("profile_picture", fileA.getName(), requestBodyId);
        }
        catch (Exception e){
            Toast.makeText(RegisterDriver.this,"من فضلك قم بتحديد صوره شخصية",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        File fileB = null;
        try{
            fileB = new File(mediaPathB);}
        catch (Exception e){
            Toast.makeText(RegisterDriver.this,"من فضلك قم بتحديد صوره رخصة السيارة",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        MultipartBody.Part fileToUploadB  = null;
        try {
            // Parsing any Media type file
            RequestBody requestBodyId = RequestBody.create(MediaType.parse("*/*"), fileB);
            fileToUploadB = MultipartBody.Part.createFormData("id_card", fileB.getName(), requestBodyId);
        }
        catch (Exception e){
            Toast.makeText(RegisterDriver.this,"من فضلك قم بتحديد صوره رخصة السيارة",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        File fileC = null;
        try{
            fileC = new File(mediaPathC);}
        catch (Exception e){
            Toast.makeText(RegisterDriver.this,"من فضلك قم بتحديد صوره رخصة القيادة",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        MultipartBody.Part fileToUploadC = null;
        try {
            // Parsing any Media type file
            RequestBody requestBodyId = RequestBody.create(MediaType.parse("*/*"), fileC);
            fileToUploadC = MultipartBody.Part.createFormData("driving_license", fileC.getName(), requestBodyId);
        }
        catch (Exception e){
            Toast.makeText(RegisterDriver.this,"من فضلك قم بتحديد صوره رخصة القيادة",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        RequestBody namee = RequestBody.create(MediaType.parse("text/plain"),name.getText().toString());
        RequestBody emaile=RequestBody.create(MediaType.parse("text/plain"),email.getText().toString());
        RequestBody phonee=RequestBody.create(MediaType.parse("text/plain"),phone.getText().toString());
        RequestBody passworde=RequestBody.create(MediaType.parse("text/plain"),password.getText().toString());
        RequestBody typee=RequestBody.create(MediaType.parse("text/plain"),type.getText().toString());
        RequestBody modele=RequestBody.create(MediaType.parse("text/plain"),model.getText().toString());
        RequestBody typeee=RequestBody.create(MediaType.parse("text/plain"),"driver");
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user> call = apiinterface.getcontacts_newaccount_driver(fileToUploadA,fileToUploadB,fileToUploadC
                ,namee,passworde,emaile,phonee,typee,modele,typeee
        );
        call.enqueue(new Callback<contact_general_user>() {
            @Override
            public void onResponse(Call<contact_general_user> call, Response<contact_general_user> response) {
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(RegisterDriver.this,"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                contactList = response.body();

                try{
                    progressDialog.dismiss();
                    edt.putString("token",contactList.getPayload().getToken());
                    edt.putString("remember","yes");
                    edt.apply();

                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(RegisterDriver.this);
                    dlgAlert.setMessage("تم تسجيل الدخول بنجاح");
                    dlgAlert.setTitle("Ramada");
                    dlgAlert.setIcon(R.drawable.logo);

                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    startActivity(new Intent(RegisterDriver.this,MainDriver.class));}
                catch (Exception e){
                    Toast.makeText(RegisterDriver.this, "هناك خطأ حدث الرجاء المحاولة مرة اخري ", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<contact_general_user> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterDriver.this,t+"",Toast.LENGTH_LONG).show();

            }
        });
    }
}
