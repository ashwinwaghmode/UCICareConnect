package com.devool.ucicareconnect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.devool.ucicareconnect.R;
import com.devool.ucicareconnect.utils.PermissionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSkip;
    TextView tvHeading, tvAddPhoto;
    Typeface tf;
    ImageView imgBackArrow;
    String userChoosenTask, ImageToString;
    Bitmap bm;
    CircleImageView imgProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSkip = findViewById(R.id.btn_skip);
        tvHeading = findViewById(R.id.tv_heading);
        tvAddPhoto = findViewById(R.id.tv_add_photo);
        imgBackArrow = findViewById(R.id.img_back_arrow);
        imgProfilePhoto = findViewById(R.id.img_profile_photo);

        imgBackArrow.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        imgProfilePhoto.setOnClickListener(this);

        appyfontForAllViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_skip:
                Intent intent = new Intent(ProfileActivity.this,WelcomeScreensActivity.class);
                //intent.putExtra("password", edtCreatePassword.getText().toString());
                intent.putExtra("user_name", getIntent().getExtras().getString("user_name"));
                startActivity(intent);
                //startActivity(new Intent(ProfileActivity.this, WelcomeScreensActivity.class));
                break;
            case R.id.img_back_arrow:
                onBackPressed();
                break;
            case R.id.img_profile_photo:
                selectImage();
                break;
        }

    }

    private void appyfontForAllViews() {
        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Bold.otf");
        tvHeading.setTypeface(tf,Typeface.BOLD);

        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Bold.otf");
        tvAddPhoto.setTypeface(tf,Typeface.BOLD);

        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Medium.otf");
        btnSkip.setTypeface(tf);


    }

    private void selectImage() {
        final CharSequence[] items = {"Choose from Gallery", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Select Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= PermissionUtils.checkPermission(ProfileActivity.this);
                /*if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } */
                if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask="Choose from Gallery";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 10);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),5);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Gallery"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 5)
                onSelectFromGalleryResult(data);
            else if (requestCode == 10)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        bm=null;
        if (data != null) {
            try {
                tvAddPhoto.setText("Change Photo");
                btnSkip.setText("Next");
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                int nh = (int) ( bm.getHeight() * (512.0 / bm.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(bm, 512, nh, true);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                imgProfilePhoto.setImageBitmap(scaled);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ImageToString = getStringImage(bm);
        //imgTravelcam.setVisibility(View.GONE);
        Log.e(" Travel_gallery_image", " is "+ ImageToString);
    }

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgProfilePhoto.setImageBitmap(bm);
        //imgTravelcam.setVisibility(View.GONE);
        ImageToString = getStringImage(bm);
        System.out.println(" Travel_Camera_image"+  ImageToString);
    }

    private String getStringImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[]imageBytes=baos.toByteArray();
        String encodedImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }
}
