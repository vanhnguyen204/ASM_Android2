package com.fpoly.asm_android2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.asm_android2.Database.DBHelperProduct;
import com.fpoly.asm_android2.Database.ProductDAO;
import com.fpoly.asm_android2.Models.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class FragmentProductManagement extends Fragment {
    ProductDAO productDAO;
    ArrayList<SanPham> list = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);
        return view;
    }

    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerViewProductAdapter recyclerViewProductAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).toolbar.setTitle("Quản lý sản phẩm");
        productDAO = new ProductDAO(new DBHelperProduct(getActivity()), getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        list = productDAO.getListproduct();
        recyclerViewProductAdapter = new RecyclerViewProductAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewProductAdapter);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog();
            }
        });
    }

    EditText edtNameProduct, edtPrice, edtQuatity;

    Button btnAddProduct;

    public void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Style_AlertDialog_Corner);
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.custom_alertdialog, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        edtNameProduct = view1.findViewById(R.id.edt_nameProduct);
        edtPrice = view1.findViewById(R.id.edt_price);
        edtQuatity = view1.findViewById(R.id.edt_quatity);
        btnAddProduct = view1.findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNameProduct = edtNameProduct.getText().toString().trim();
                String getPrice = edtPrice.getText().toString().trim();
                String getQuatity = edtQuatity.getText().toString().trim();
                if (checkValues()) {

                } else if (getNameProduct.length() == 0) {
                    Toast.makeText(getActivity(), "Không được để trống tên sản phẩm !", Toast.LENGTH_SHORT).show();
                } else if (getPrice.length() == 0) {
                    Toast.makeText(getActivity(), "Không được để trống giá sản phẩm !", Toast.LENGTH_SHORT).show();
                } else if (getQuatity.length() == 0) {
                    Toast.makeText(getActivity(), "Không được để trống số lượng sản phẩm !", Toast.LENGTH_SHORT).show();
                } else {
                    SanPham sanPham = new SanPham(new Random().nextInt(10000), getNameProduct, Integer.parseInt(getPrice), Integer.parseInt(getQuatity));
                    list.add(sanPham);
                    productDAO.insertProduct(sanPham);
                    recyclerViewProductAdapter.notifyItemInserted(list.size());
                    alertDialog.dismiss();

                }
            }
        });
    }

    boolean checkValues() {
        try {
            int price = Integer.parseInt(edtPrice.getText().toString().trim());
            if (price < 0) {
                Toast.makeText(getContext(), "Giá phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Giá phải là số nguyên dương !", Toast.LENGTH_SHORT).show();
            return true;
        }
        try {
            int quatity = Integer.parseInt(edtQuatity.getText().toString().trim());
            if (quatity < 0) {
                Toast.makeText(getContext(), "Số lượng phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Số lượng phải là số nguyên dương !", Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }
}