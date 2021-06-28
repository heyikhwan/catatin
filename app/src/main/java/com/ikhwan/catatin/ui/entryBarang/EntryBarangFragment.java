package com.ikhwan.catatin.ui.entryBarang;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ikhwan.catatin.R;
import com.ikhwan.catatin.Response.ResponseProduct;
import com.ikhwan.catatin.Rest.ApiClient;
import com.ikhwan.catatin.Rest.ApiInterface;
import com.ikhwan.catatin.ui.laporanBarang.LaporanBarangFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryBarangFragment extends Fragment {

    View view;
    private TextInputEditText etProductName, etPurchasePrice, etQty, etEntryBy;
    private Button btnAddProduct;
    private String name, entryBy, purchase_price, qty;
    private Integer harga_beli, selling_price, jumlah;

    public EntryBarangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_entry_barang, container, false);

        etProductName = view.findViewById(R.id.etProductName);
        etPurchasePrice = view.findViewById(R.id.etPurchasePrice);
        etQty = view.findViewById(R.id.etQty);
        etEntryBy = view.findViewById(R.id.etEntryBy);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etProductName.getText().toString();
                purchase_price = etPurchasePrice.getText().toString();
                qty = etQty.getText().toString();
                entryBy = etEntryBy.getText().toString();

                if (name.trim().equals("")) {
                    etProductName.setError("Nama Barang harus di isi");
                } else if (purchase_price.trim().equals("")) {
                    etPurchasePrice.setError("Harga beli harus di isi");
                } else if (qty.trim().equals("")) {
                    etQty.setError("Jumlah harus di isi");
                } else if (entryBy.trim().equals("")) {
                    etEntryBy.setError("Entry by harus di isi");
                } else {
                    harga_beli = Integer.parseInt(purchase_price);
                    selling_price = harga_beli + (harga_beli * 10 / 100);
                    jumlah = Integer.parseInt(qty);
                    createProduct();
                }

            }
        });

        return view;
    }

    private  void createProduct() {
        ApiInterface arData = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseProduct> addProduct = arData.createProduct(name, harga_beli, selling_price, jumlah, entryBy);

        addProduct.enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                String status = response.body().getStatus();
                String message = response.body().getMessage();

                Toast.makeText(getActivity(), "Status: " + status + " | " + message, Toast.LENGTH_SHORT).show();

                onStart();
            }

            @Override
            public void onFailure(Call<ResponseProduct> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungi Server:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        etProductName.getText().clear();
        etPurchasePrice.getText().clear();
        etQty.getText().clear();
        etEntryBy.getText().clear();
        etProductName.requestFocus();
    }
}