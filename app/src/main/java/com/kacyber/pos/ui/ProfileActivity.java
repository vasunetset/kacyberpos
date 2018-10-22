package com.kacyber.pos.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.entity.response.UserInfoRes;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.ImageUtils;
import com.rilixtech.CountryCodePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 账号信息
 */
public class ProfileActivity extends BaseActivity implements ImageUtils.ImageSelectCallback, BaseActivity.PermCallback, ApiResponse {

    @BindView(R.id.profileImage)
    CircleImageView profileImage;
    @BindView(R.id.userNameTV)
    TextView userNameTV;
    @BindView(R.id.designationTV)
    TextView designationTV;
    @BindView(R.id.firstNameET)
    EditText firstNameET;
    @BindView(R.id.lastNameET)
    EditText lastNameET;
    @BindView(R.id.countryCode)
    CountryCodePicker countryCode;
    @BindView(R.id.phoneNoET)
    EditText phoneNoET;
    @BindView(R.id.emailET)
    EditText emailET;
    private Call<JsonObject> logoutData;
    private Call<JsonObject> getUserProfile;
    private File selectImageFile;
    private String base64 = "";
    private Call upateProfile;

    /*public static void start(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }*/

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_profile;
    }

    @Override
    protected boolean hideNavigationIcon() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadData();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageDialog();
            }
        });
    }

    private void loadData() {
        getUserProfile = apiInterface.getUserProfile(GlobalStore.getToken(getApplicationContext()));
        apiHitAndHandle.makeApiCall(getUserProfile, this);
    }

    private void selectImageDialog() {
        if (checkPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, 123, this)) {

            ImageUtils.ImageSelect.Builder builder = new ImageUtils.ImageSelect.Builder(this,
                    this, 543).crop();
            builder.start();
        }
    }

    @Override
    public void permGranted(int resultCode) {
        selectImageDialog();
    }

    @Override
    public void permDenied(int resultCode) {

    }

    @Override
    public void onImageSelected(String imagePath, int resultCode) {
        Bitmap bitmap = ImageUtils.imageCompress(imagePath);
        //  selectImageFile = ImageUtils.bitmapToFile(bitmap, this);
        base64 = encodeTobase64(bitmap);
        profileImage.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
     /*   if (i == android.R.id.home) {
            onBackPressed();

        } else*/
        if (i == R.id.action_done) {
            validateData();
            //  finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void validateData() {
        String fname = firstNameET.getText().toString().trim();
        String lname = lastNameET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String phoneNo = phoneNoET.getText().toString().trim();
        //14 2.6=,15=2.90;


        if (fname.isEmpty()) {
            showToast("Please enter first name");
        } else if (lname.isEmpty()) {
            showToast("Please enter last name");
        } else if (email.isEmpty()) {
            showToast("Please enter email");
        } else if (phoneNo.isEmpty()) {
            showToast("Please enter phone no");
        } else if (!phoneNo.isEmpty() && !validateUsing_libphonenumber(countryCode.getSelectedCountryCode(), phoneNo)) {
            //  showToast("Please enter valid phone number");
            // TODO Toast already show
        } else {
            try {
                phoneNo = "+" + countryCode.getSelectedCountryCode() + phoneNo;
                HashMap<String, RequestBody> hashMa = new HashMap<>();
                hashMa.put("firstName", getRequestBodyParam(fname));
                hashMa.put("lastName", getRequestBodyParam(lname));
                hashMa.put("phone", getRequestBodyParam(phoneNo));
                hashMa.put("email", getRequestBodyParam(email));
                hashMa.put("profile_pic", getRequestBodyParam(base64));
                /*if (selectImageFile != null) {
                    RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), selectImageFile);
                    hashMa.put("profile_pic\"; filename=\"" + selectImageFile.getName() + "\" ", body);
                }*/
                upateProfile = apiInterface.updateUserProfile(GlobalStore.getToken(getApplicationContext()), hashMa);
                apiHitAndHandle.makeApiCall(upateProfile, true, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    public RequestBody getRequestBodyParam(String value) {
        return RequestBody.create(MediaType.parse("text/form-data"), value);
    }

    @Override
    public void onSuccess(Call call, Object object) {
        try {
            if (getUserProfile == call) {
                if (object != null) {

                    JSONObject jsonObject = new JSONObject(object.toString());
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        UserInfoRes userInfoRes = new Gson().fromJson(jsonObject.toString(), UserInfoRes.class);
                        saveUserInfo(jsonObject);
                        setData(userInfoRes);
                    }
                }
            } else if (upateProfile == call) {
                if (object != null) {

                    JSONObject jsonObject = new JSONObject(object.toString());
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        saveUserInfo(jsonObject);
                        showToast("Profile updated successfully");
                        onBackPressed();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveUserInfo(JSONObject jsonObject) {
        UserInfoRes userInfoRes = new Gson().fromJson(jsonObject.toString(), UserInfoRes.class);
        GlobalStore.saveUser(getApplicationContext(), userInfoRes.user);
    }

    private void setData(UserInfoRes userInfoRes) {
        int countryCodeNo;
        long phoneNo;

        firstNameET.setText(userInfoRes.user.firstName);
        lastNameET.setText(userInfoRes.user.lastName);
        emailET.setText(userInfoRes.user.email);
        userNameTV.setText(userInfoRes.user.username);
        designationTV.setText(userInfoRes.user.designationName);

        if (userInfoRes.user.profilePic != null && !userInfoRes.user.profilePic.isEmpty()) {
            Picasso.with(this).load(userInfoRes.user.profilePic).placeholder(R.drawable.icon_personal)
                    .error(R.drawable.ic_logo).into(profileImage);
        }
        if (userInfoRes.user.phone != null && !userInfoRes.user.phone.isEmpty()) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(getApplicationContext());
            try {
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(userInfoRes.user.phone, "");
                countryCodeNo = numberProto.getCountryCode();
                phoneNo = numberProto.getNationalNumber();
                phoneNoET.setText(new StringBuilder().append(phoneNo).append("").toString());
                countryCode.setCountryForPhoneCode(countryCodeNo);
            } catch (NumberParseException e) {
                System.err.println("NumberParseException was thrown: " + e.toString());
            }
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        showToast(errorMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...)
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageUtils.activityResult(requestCode, resultCode, data);

    }

}
