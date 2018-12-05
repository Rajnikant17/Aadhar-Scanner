package com.example.rajnikantpandey.senseinfinity;

/**
 * Created by Rajnikant Pandey on 12/2/2018.
 */

public class ModelClass {
    String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode;

    public ModelClass(String uid,String name,String gender,String yearOfBirth,String careOf,String villageTehsil,String postOffice,
                      String district,String state,String postCode)
    {
        this.uid = uid;
        this.name = name;
        this.gender=gender;
        this.yearOfBirth = yearOfBirth;
        this.careOf = careOf;
        this.villageTehsil = villageTehsil;
        this.postOffice = postOffice;
        this.district = district;
        this.state = state;
        this.postCode = postCode;

    }

    public String getVillageTehsil() {
        return villageTehsil;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getState() {

        return state;
    }

    public String getDistrict() {

        return district;
    }

    public String getPostOffice() {

        return postOffice;
    }

    public String getCareOf() {

        return careOf;
    }

    public String getYearOfBirth() {

        return yearOfBirth;
    }

    public String getGender() {

        return gender;
    }

    public String getName() {

        return name;
    }
    public String getUid() {

        return uid;

    }

}
