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
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Imagedatabse;
import com.mountreachsolution.farmlabourscheduling.LoginActivity;
import android.Manifest;
import com.mountreachsolution.farmlabourscheduling.R;

import java.io.FileNotFoundException;
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

        loaddata();

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);


            }
        });

        // Logout button click
        btnlogout.setOnClickListener(v -> logoutUser());

       getImage();
        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void getImage() {
        String imageUriOrBase64 = dbHelper.getImageByNumber(number);
        if (imageUriOrBase64 != null) {
            try {
                // If the value is base64, decode it
                byte[] decodedString = Base64.decode(imageUriOrBase64, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                // Load the decoded Bitmap into the ImageView using Glide
                Glide.with(this)
                        .load(decodedBitmap)
                        .into(imageView);
            } catch (Exception e) {
                // If the image is a URI, you can load it as a normal image
                Glide.with(this)
                        .load(imageUriOrBase64)  // URI handling
                        .into(imageView);
            }
        } else {
            Toast.makeText(getActivity(), "Image not found!", Toast.LENGTH_SHORT).show();
        }
    }



    private void logoutUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("LoggedInNumber");  // Remove saved login information
        editor.remove("LoggedInRole");  // Remove saved role information
        editor.apply();

        // Optionally, log to check if it's cleared
        Log.d("Logout", "User logged out, session cleared");

        // Redirect to the login screen
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
            editor.apply();
            tvage.setText(age);
            tvaddress.setText(address);
            tvadhareno.setText(adhar);

            cursor.close();
        } else {
            Toast.makeText(getActivity(), "No user data found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                // Load the image using Glide or ImageView
                imageView.setImageURI(imageUri);

                // Insert the image URI into the database
                dbHelper.insertData(number, imageUri.toString());
                Toast.makeText(getActivity(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
