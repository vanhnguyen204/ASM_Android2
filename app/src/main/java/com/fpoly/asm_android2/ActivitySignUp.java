package com.fpoly.asm_android2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.asm_android2.Database.AccountDAO;
import com.fpoly.asm_android2.Database.DBHelper;
import com.fpoly.asm_android2.Models.NguoiDung;

public class ActivitySignUp extends AppCompatActivity {
    public static  String KEY_USERNAME_SIGNUP = "user_name";
    public static String KEY_PASSWORD_SIGNUP = "pass_word";
    Button btnSignUp;

    EditText edtUserName, edtPassword, edtComfirmPassWord, edtName;
    private void finId() {
        edtName = findViewById(R.id.edt_name);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtUserName = findViewById(R.id.edt_userName_signup);
        edtPassword = findViewById(R.id.edt_passWord_signUp);
        edtComfirmPassWord = findViewById(R.id.edt_comfirm_password_signup);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        finId();
        DBHelper dbHelper = new DBHelper(ActivitySignUp.this);
        AccountDAO accountDAO = new AccountDAO(dbHelper, ActivitySignUp.this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = edtName.getText().toString().trim();
                String getUserName = edtUserName.getText().toString().trim();
                String getPassWord = edtPassword.getText().toString().trim();
                String getConfirmPassWord = edtComfirmPassWord.getText().toString().trim();
                if (getName.length() == 0){
                    Toast.makeText(ActivitySignUp.this, "không được để trống họ và tên !", Toast.LENGTH_SHORT).show();
                }else if (getUserName.length() == 0){
                    Toast.makeText(ActivitySignUp.this, "không được để trống tên đăng nhập !", Toast.LENGTH_SHORT).show();
                } else if (getPassWord.length() == 0) {
                    Toast.makeText(ActivitySignUp.this, "Không được để trống mật khẩu !", Toast.LENGTH_SHORT).show();
                } else if (getConfirmPassWord.length() == 0) {
                    Toast.makeText(ActivitySignUp.this, "Bạn chưa xác nhận mật khẩu !", Toast.LENGTH_SHORT).show();
                } else if (!getPassWord.equals(getConfirmPassWord)) {
                    Toast.makeText(ActivitySignUp.this, "Mật khẩu xác nhận chưa đúng !", Toast.LENGTH_SHORT).show();
                }else{
                    NguoiDung nguoiDung = new NguoiDung(getUserName,getPassWord,getName);
                    accountDAO.insertNguoiDung(nguoiDung);

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_USERNAME_SIGNUP, getUserName);
                    bundle.putString(KEY_PASSWORD_SIGNUP,getPassWord);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }


}