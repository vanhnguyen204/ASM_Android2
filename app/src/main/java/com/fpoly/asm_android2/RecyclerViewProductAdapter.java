package com.fpoly.asm_android2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.asm_android2.Database.DBHelperProduct;
import com.fpoly.asm_android2.Database.ProductDAO;
import com.fpoly.asm_android2.Models.SanPham;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductAdapter.Viewholder> {
    ArrayList<SanPham> list;

    EditText edtName, edtPrice, edtQuatityProduct;
    Button btnUpdate;

    public RecyclerViewProductAdapter(ArrayList<SanPham> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_item_recyclerview, parent, false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {

        SanPham sanPham = list.get(position);
        holder.name.setText(sanPham.getTenSp());
        holder.price_quatity.setText(sanPham.getGiaSp() + " VNĐ - SL: " + sanPham.getSoLuong());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAlertDialogUpdate(v.getContext(), position);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDAO productDAO = new ProductDAO(new DBHelperProduct(v.getContext()), v.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Bạn có muốn xoá sản phẩm này không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productDAO.deleteProduct(list.get(position));
                        list.remove(position);
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeRemoved(position, list.size());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView name, price_quatity, delete, update;
        ImageView imageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_nameOfProduct);
            price_quatity = itemView.findViewById(R.id.txt_price_quantity);
            delete = itemView.findViewById(R.id.txtDelete);
            update = itemView.findViewById(R.id.txtUpdate);
        }
    }

    void createAlertDialogUpdate(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Style_AlertDialog_Corner);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_alertdialog, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        edtName = view.findViewById(R.id.edt_nameProduct);
        edtPrice = view.findViewById(R.id.edt_price);
        edtQuatityProduct = view.findViewById(R.id.edt_quatity);
        btnUpdate = view.findViewById(R.id.btnAddProduct);
        btnUpdate.setText("Sửa");
        ProductDAO productDAO = new ProductDAO(new DBHelperProduct(context), context);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getNameProduct = edtName.getText().toString().trim();
                String getPrice = edtPrice.getText().toString().trim();
                String getQuatity = edtQuatityProduct.getText().toString().trim();
               if (checkValues(v.getContext())){

               }else if (getNameProduct.length() == 0) {
                    Toast.makeText(context, "Không được để trống tên sản phẩm !", Toast.LENGTH_SHORT).show();
                } else if (getPrice.length() == 0) {
                    Toast.makeText(context, "Không được để trống giá sản phẩm !", Toast.LENGTH_SHORT).show();
                } else if (getQuatity.length() == 0) {
                    Toast.makeText(context, "Không được để trống số lượng sản phẩm !", Toast.LENGTH_SHORT).show();
                } else {
                    SanPham sanPham = new SanPham(list.get(position).getMaSp(), getNameProduct, Integer.parseInt(getPrice), Integer.parseInt(getQuatity));
                    list.set(position, sanPham);
                    productDAO.updateProduct(sanPham);
                    notifyItemChanged(position);
                    notifyItemRangeChanged(position, list.size());
                    alertDialog.dismiss();

                }
            }
        });
    }
    boolean checkValues(Context context) {
        try {
            int price = Integer.parseInt(edtPrice.getText().toString().trim());
            if (price < 0) {
                Toast.makeText(context, "Giá phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Giá phải là số nguyên dương !", Toast.LENGTH_SHORT).show();
            return true;
        }
        try {
            int quatity = Integer.parseInt(edtQuatityProduct.getText().toString().trim());
            if (quatity < 0) {
                Toast.makeText(context, "Số lượng phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Số lượng phải là số nguyên dương !", Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }
}
