package com.kacyber.pos.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mzy on 2018/3/17.
 */

public class User {
    /**
     * "profile_pic": "",
     * "firstname": "",
     * "lastname": "",
     * "phone": "+918397871463",
     * "fb_token": "",
     * "cover_pic": "",
     * "user_id": "522106877051",
     * "dob": "1000-01-01",
     * "membership_id": "109215831519",
     * "location": "",
     * "login_type": "1",
     * "api_key": "f3eb25a8d2f74dbf95af92611",
     * "email": "gurmukhsingh555@gmail.com",
     * "total_following": 0
     */
    @SerializedName("profile_pic")
    @Expose
    public String profilePic;
    @SerializedName("designationName")
    @Expose
    public String designationName;
    @SerializedName("branchId")
    @Expose
    public String branchId;
    @SerializedName("busCompanyId")
    @Expose
    public String busCompanyId;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("fullName")
    @Expose
    public String fullName;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("username")
    @Expose
    public String username;
}
