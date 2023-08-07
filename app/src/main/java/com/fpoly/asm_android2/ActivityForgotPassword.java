package com.fpoly.asm_android2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passwors);
        FragmentForgotPassword fragmentForgotPassword = new FragmentForgotPassword();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fgpass, fragmentForgotPassword).commit();



    }

}