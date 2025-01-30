package com.mountreachsolution.farmlabourscheduling.FARMER;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.framework.media.ImagePicker;
import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Imagedatabse;
import com.mountreachsolution.farmlabourscheduling.LoginActivity;
import com.mountreachsolution.farmlabourscheduling.R;

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
    TextView tvname,tvage,tvadhareno,tvaddress,tvMobileNo;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1; // Request code for picking image




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profil, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);
         number = sharedPreferences.getString("LoggedInNumber", "");

        // Initialize views
        imageView = view.findViewById(R.id.profileImage);
        ivedit = view.findViewById(R.id.editPhotoButton);
        btnlogout = view.findViewById(R.id.btnlogout);
        tvname=view.findViewById(R.id.tvnameValue);
        tvage=view.findViewById(R.id.agevalue);
        tvadhareno=view.findViewById(R.id.adharvalue);
        tvaddress=view.findViewById(R.id.addressValue);
        tvMobileNo=view.findViewById(R.id.Mobilenovalue);

        dbHelper = new Imagedatabse(getActivity());
        farmerRegistration=new FarmerRegistration(getActivity());

        loaddata();

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        // Logout button click
        btnlogout.setOnClickListener(v -> logoutUser());

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the gallery to pick an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        
        getImage();


        return view;
    }

    private void getImage() {
        String imageUri=dbHelper.getImageByNumber(number);

        if (imageUri != null) {
            // Use the imageUri (e.g., display the image in an ImageView)
            imageView.setImageURI(Uri.parse(imageUri));
        } else {
            // Handle the case when no image is found for the given number
            Toast.makeText(getActivity(), "No image found for this number", Toast.LENGTH_SHORT).show();
        }
    }

    private void logoutUser() {

            // Access SharedPreferences
            SharedPreferences sharedPreferences =getActivity().getSharedPreferences("UserSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Clear the saved session
            editor.clear(); // This will remove all stored data in the SharedPreferences
            editor.apply(); // Commit the changes

            // Redirect to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class); // Replace CurrentActivity with your current activity name
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);
            // Finish the current activity
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

        // Check if the result is OK and we have a valid data
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Display the selected image in ImageView
            imageView.setImageURI(imageUri);

            // Convert the image URI to String and insert it into the database
            // Use the appropriate number here
            dbHelper.insertData(number, imageUri.toString());

            // Optionally, show a toast or message indicating that the image is uploaded
            Toast.makeText(getActivity(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

}