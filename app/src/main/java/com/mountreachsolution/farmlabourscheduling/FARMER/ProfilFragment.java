package com.mountreachsolution.farmlabourscheduling.FARMER;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Imagedatabse;
import com.mountreachsolution.farmlabourscheduling.LoginActivity;
import android.Manifest;
import com.mountreachsolution.farmlabourscheduling.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfilFragment extends Fragment {
    String number;
    Button btnlogout;
    String mobileNumber;
    CircleImageView imageView;
    ImageButton ivedit;
    private static final int PICK_IMAGE = 100;
    Imagedatabse dbHelper;
    FarmerRegistration farmerRegistration;
    Bitmap selectedBitmap;
    TextView tvname, tvage, tvadhareno, tvaddress, tvMobileNo;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private SharedPreferences sharedPreferences1;
    private static final String PREF_NAME = "MyPrefs";
    private static final String IMAGE_URI_KEY = "image_uri";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);
        number = sharedPreferences.getString("LoggedInNumber", "");

        // Initialize views
        imageView = view.findViewById(R.id.profileImage);
        ivedit = view.findViewById(R.id.editPhotoButton);
        btnlogout = view.findViewById(R.id.btnlogout);
        tvname = view.findViewById(R.id.tvnameValue);
        tvage = view.findViewById(R.id.agevalue);
        tvadhareno = view.findViewById(R.id.adharvalue);
        tvaddress = view.findViewById(R.id.addressValue);
        tvMobileNo = view.findViewById(R.id.Mobilenovalue);

        dbHelper = new Imagedatabse(getActivity());
        farmerRegistration = new FarmerRegistration(getActivity());

        sharedPreferences1 = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Load saved image URI (if available)
       loadImage();

        loaddata();

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });

        // Logout button click
        btnlogout.setOnClickListener(v -> logoutUser());


        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String imagePath = saveImageToInternalStorage(selectedImageUri);
                if (imagePath != null) {
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString(IMAGE_URI_KEY, imagePath);
                    editor.apply();
                    loadImage();
                } else {
                    Toast.makeText(getActivity(), "Failed to save image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            File directory = getActivity().getFilesDir();
            File file = new File(directory, "profile_image.jpg");

            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e("ImagePicker", "Error saving image: " + e.getMessage());
            return null;
        }
    }





    private void logoutUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("LoggedInNumber");
        editor.remove("LoggedInRole");
        editor.apply();


        Log.d("Logout", "User logged out, session cleared");


        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loaddata() {
        Cursor cursor = farmerRegistration.getUserDataByMobile(number);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String mobile = cursor.getString(cursor.getColumnIndex("mobileno"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String adhar = cursor.getString(cursor.getColumnIndex("adharno"));
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Userdata", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            tvname.setText(name);
            editor.putString("userName", name);
            tvMobileNo.setText(mobile);
            editor.putString("userMobile", mobile);
            tvage.setText(age);
            editor.putString("userAge", age);
            tvaddress.setText(address);
            editor.putString("userAddress", address);
            tvadhareno.setText(adhar);
            editor.putString("userAdhar", adhar);
            editor.apply();

            cursor.close();
        } else {
            Toast.makeText(getActivity(), "No user data found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadImage(); // Reload image when returning to the activity
    }

    private void loadImage() {
        String savedImagePath = sharedPreferences1.getString(IMAGE_URI_KEY, null);
        if (savedImagePath != null) {
            File imgFile = new File(savedImagePath);
            if (imgFile.exists()) {
                Glide.with(this)
                        .load(imgFile)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                Log.d("ImagePicker", "Image loaded from internal storage: " + savedImagePath);
            } else {
                Log.e("ImagePicker", "Saved image file not found");
            }
        } else {
            Log.d("ImagePicker", "No saved image path found in SharedPreferences");
        }
    }



}
