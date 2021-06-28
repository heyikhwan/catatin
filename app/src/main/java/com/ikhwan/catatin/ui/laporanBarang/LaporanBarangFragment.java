package com.ikhwan.catatin.ui.laporanBarang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ikhwan.catatin.Adapter.AdapterProduct;
import com.ikhwan.catatin.Model.Product;
import com.ikhwan.catatin.R;
import com.ikhwan.catatin.Response.ResponseProduct;
import com.ikhwan.catatin.Rest.ApiClient;
import com.ikhwan.catatin.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanBarangFragment extends Fragment {

    public LaporanBarangFragment() {
        // Required empty public constructor
    }

    View view;

    private RecyclerView rvProduct;
    private RecyclerView.Adapter adProduct;
    private RecyclerView.LayoutManager lmProduct;
    private List<Product> listProduct = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_laporan_barang, container, false);

        rvProduct = view.findViewById(R.id.rv_data);
        lmProduct = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvProduct.setLayoutManager(lmProduct);
        readData();
        return view;
    }

    public void readData() {
        ApiInterface arData = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseProduct> tampilProduct = arData.readProduct();

        tampilProduct.enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                listProduct = response.body().getData();
                adProduct = new AdapterProduct(getActivity(), listProduct);
                rvProduct.setAdapter(adProduct);
                adProduct.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseProduct> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungi Server:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}