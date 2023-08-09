package com.fpoly.asm_android2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.asm_android2.Database.AccountDAO;
import com.fpoly.asm_android2.Database.DBHelper;
import com.fpoly.asm_android2.Models.NguoiDung;

import java.util.ArrayList;
import java.util.Random;

public class FragmentForgotPassword extends Fragment {
    View view;
    EditText edtOTP;
    int check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AccountDAO accountDAO = new AccountDAO(new DBHelper(getContext()), getContext());
        ArrayList<NguoiDung> list = accountDAO.getList();

        EditText edtGetUsername = view.findViewById(R.id.edtUserNameFG);
        Button btnConfirmFG = view.findViewById(R.id.btnConfirmFP);
        int OTP = new Random().nextInt(1000);

        btnConfirmFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (edtGetUsername.getText().toString().equals(list.get(i).getTenDangNhap())) {
                        Toast.makeText(getContext(), "Mã của bạn là: " + OTP, Toast.LENGTH_LONG).show();
                        String getHoVaTen = list.get(i).getHoVaTen();
                        FragmentConfirmPassForgot fragment = new FragmentConfirmPassForgot();
                        Bundle bundle = new Bundle();
                        bundle.putString("key_user", edtGetUsername.getText().toString()); // Put anything what you want
                        bundle.putString("ho_va_ten", getHoVaTen);
                        getParentFragmentManager().setFragmentResult("key", bundle);
                        createAlertOTP(OTP);
                        check++;

                    }
                }
                if (check == 0){
                    Toast.makeText(getContext(), "Tài khoản không tồn tại !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    int confirmOTP;

    void createAlertOTP(int OTP) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.alert_forgotpass, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();

        Button btnConfirm = view1.findViewById(R.id.btnConfirmOTP);
        Button btnCancel = view1.findViewById(R.id.btnCancel);
        edtOTP = view1.findViewById(R.id.edt_OTP);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parseOTP = edtOTP.getText().toString().trim();
                if (parseOTP.length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống mã OTP", Toast.LENGTH_SHORT).show();
                } else {
                    confirmOTP = Integer.parseInt(parseOTP);
                }

                if (checkOTP()) {

                } else if (confirmOTP == OTP) {
                    ((ActivityForgotPassword) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fgpass, new FragmentConfirmPassForgot()).commit();
                    alertDialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    boolean checkOTP() {
        try {
            int checkOTP = Integer.parseInt(edtOTP.getText().toString());
            if (checkOTP < 0) {
                Toast.makeText(getContext(), "OTP phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "OTP phải là số !", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}