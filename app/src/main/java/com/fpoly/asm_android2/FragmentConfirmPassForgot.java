package com.fpoly.asm_android2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.asm_android2.Database.AccountDAO;
import com.fpoly.asm_android2.Database.DBHelper;
import com.fpoly.asm_android2.Models.NguoiDung;

public class FragmentConfirmPassForgot extends Fragment {
    View view;
    EditText edtPass, edtConfirmPass;
    Button btnConfirm;
    String getUser, getHoVaTen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_confirm_pass_forgot, container, false);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                getUser = result.getString("key_user");
                getHoVaTen = result.getString("ho_va_ten");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AccountDAO dao = new AccountDAO(new DBHelper(getContext()), getContext());
        edtPass = view.findViewById(R.id.edt_passWord_forgot);
        edtConfirmPass = view.findViewById(R.id.edt_confirm_password_forgot);
        btnConfirm = view.findViewById(R.id.btnConfirmPassForgot);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPass1 = edtPass.getText().toString().trim();
                String getConfirm = edtConfirmPass.getText().toString().trim();
                if (getPass1.length() == 0) {
                    Toast.makeText(getContext(), "Không đuọcw để trống mật khẩu! ", Toast.LENGTH_SHORT).show();
                } else if (getConfirm.length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
                } else if (!getConfirm.equals(getPass1)) {
                    Toast.makeText(getContext(), "Mật khẩu xác nhận không đúng !", Toast.LENGTH_SHORT).show();
                } else {
                    dao.updateForgotPasss(getUser, getPass1);
                    Toast.makeText(getContext(), "Lấy lại mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                    getActivity().finish();

                }
            }
        });

    }
}