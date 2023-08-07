package com.fpoly.asm_android2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.asm_android2.Database.AccountDAO;
import com.fpoly.asm_android2.Database.DBHelper;
import com.fpoly.asm_android2.Models.NguoiDung;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {
    TextView txtSignUp;
    EditText edtuser, edtPass;
    Button btnLogin;

    TextView txtForGotPass;

    public void finId() {
        txtSignUp = findViewById(R.id.txtSignUp);
        edtuser = findViewById(R.id.edt_userName);
        edtPass = findViewById(R.id.edt_passWord);
        btnLogin = findViewById(R.id.btnLogin);
        txtForGotPass = findViewById(R.id.txt_forgot_pass);
    }

    ActivityResultLauncher<Intent> getDataSignUp = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String getUser = intent.getStringExtra(ActivitySignUp.KEY_USERNAME_SIGNUP);
                            String getPass = intent.getStringExtra(ActivitySignUp.KEY_PASSWORD_SIGNUP);
                            edtPass.setText(getPass);
                            edtuser.setText(getUser);
                        }

                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        finId();
        DBHelper dbHelper = new DBHelper(ActivityLogin.this);
        AccountDAO accountDAO = new AccountDAO(dbHelper, ActivityLogin.this);


        txtForGotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityForgotPassword.class);
                startActivity(intent);
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivitySignUp.class);
                getDataSignUp.launch(intent);
            }
        });

        TextView textView = findViewById(R.id.runText);
//        textView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_in));

        Animation animation = new TranslateAnimation(700, -980, 0, 0);
        animation.setDuration(9000);
        animation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animation.setRepeatCount(Animation.INFINITE);
        textView.setAnimation(animation);



        edtuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animationSet = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_in);
                edtuser.setAnimation(animationSet);
                animationSet.setDuration(500);
                edtuser.setVisibility(View.GONE);

                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            }
        });

        ArrayList<NguoiDung> list = accountDAO.getList();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUser = edtuser.getText().toString().trim();
                String getPass = edtPass.getText().toString().trim();
                NguoiDung nguoiDung = accountDAO.checkAccount();
                if (getUser.length() == 0) {
                    edtuser.setError("Null");

                    Toast.makeText(ActivityLogin.this, "Bạn chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                } else if (getPass.length() == 0) {
                    edtPass.setError("Null");
                    Toast.makeText(ActivityLogin.this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (getPass.equals(nguoiDung.getMatKhau()) && getUser.equals(nguoiDung.getTenDangNhap())) {

                    Toast.makeText(ActivityLogin.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ActivityLogin.this, "Đăng nhập thất bại !", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
}