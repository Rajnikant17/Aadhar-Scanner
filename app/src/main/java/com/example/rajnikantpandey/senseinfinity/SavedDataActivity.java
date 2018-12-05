package com.example.rajnikantpandey.senseinfinity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SavedDataActivity extends AppCompatActivity {
   String uid1;
    String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode;
    TextView tv_sd_uid,tv_sd_name,tv_sd_gender,tv_sd_yob,tv_sd_co,tv_sd_vtc,tv_sd_po,tv_sd_dist,
            tv_sd_state,tv_sd_pc;
    LinearLayout ll_scanned_data_wrapper,ll_data_wrapper;
    SharedPreferences sharedPreferences;
    EditText edit;
    Button getdata;
    String editdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);
        edit=findViewById(R.id.edit);
        getdata=findViewById(R.id.show);
        ll_scanned_data_wrapper = (LinearLayout)findViewById(R.id.ll_scanned_data_wrapper1);
        ll_data_wrapper = (LinearLayout)findViewById(R.id.ll_data_wrapper1);
        tv_sd_uid = (TextView)findViewById(R.id.tv_sd_uid1);
        tv_sd_name = (TextView)findViewById(R.id.tv_sd_name1);
        tv_sd_gender = (TextView)findViewById(R.id.tv_sd_gender1);
        tv_sd_yob = (TextView)findViewById(R.id.tv_sd_yob1);
        tv_sd_co = (TextView)findViewById(R.id.tv_sd_co1);
        tv_sd_vtc = (TextView)findViewById(R.id.tv_sd_vtc1);
        tv_sd_po = (TextView)findViewById(R.id.tv_sd_po1);
        tv_sd_dist = (TextView)findViewById(R.id.tv_sd_dist1);
        tv_sd_state = (TextView)findViewById(R.id.tv_sd_state1);
        tv_sd_pc = (TextView)findViewById(R.id.tv_sd_pc1);

    }
    public void getdata(View view)
    {
        editdata=edit.getText().toString();
        sharedPreferences=getSharedPreferences(editdata,this.MODE_PRIVATE);
        uid= sharedPreferences.getString("uid","");
        name= sharedPreferences.getString("name","");
        gender=sharedPreferences.getString("gender","");
        yearOfBirth= sharedPreferences.getString("yearOfBirth","");
        careOf=sharedPreferences.getString("careOf","");
        villageTehsil= sharedPreferences.getString("villageTehsil","");
        postOffice= sharedPreferences.getString("postOffice","");
        district=sharedPreferences.getString("district","");
        state= sharedPreferences.getString("state","");
        postCode=sharedPreferences.getString("postCode","");

        ll_scanned_data_wrapper.setVisibility(View.VISIBLE);

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
        StringBuffer stringBuffer=new StringBuffer(uid);
        stringBuffer.replace(0,8,"********");
        tv_sd_uid.setText(stringBuffer);
        tv_sd_name.setText(name);
        tv_sd_gender.setText(gender);
        tv_sd_yob.setText(yearOfBirth);
        tv_sd_co.setText(careOf);
        tv_sd_vtc.setText(villageTehsil);
        tv_sd_po.setText(postOffice);
        tv_sd_dist.setText(district);
        tv_sd_state.setText(state);
        tv_sd_pc.setText(postCode);


    }
    public void delete(View view)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        getdata(view);
    }
}
