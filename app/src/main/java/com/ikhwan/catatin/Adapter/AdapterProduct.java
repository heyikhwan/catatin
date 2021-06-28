package com.ikhwan.catatin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ikhwan.catatin.Activity.MainActivity;
import com.ikhwan.catatin.Model.Product;
import com.ikhwan.catatin.R;
import com.ikhwan.catatin.Response.ResponseProduct;
import com.ikhwan.catatin.Rest.ApiClient;
import com.ikhwan.catatin.Rest.ApiInterface;
import com.ikhwan.catatin.ui.laporanBarang.LaporanBarangFragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.HolderProduct> {
    private Context ctx;
    private List<Product> listProduct;
    private Integer idProduct;

    public AdapterProduct(Context ctx, List<Product> listProduct) {
        this.ctx = ctx;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderProduct holder = new HolderProduct(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        Product product = listProduct.get(position);

        NumberFormat formatRp = NumberFormat.getNumberInstance(new Locale("in", "ID"));

        holder.tvId.setText(String.valueOf(product.getId()));
        holder.tvName.setText(product.getName());
        holder.tvPurchasePrice.setText("Rp. " + String.valueOf(formatRp.format(product.getPurchase_price())));
        holder.tvSellingPrice.setText("Rp. " + String.valueOf(formatRp.format(product.getSelling_price())));
        holder.tvQty.setText(String.valueOf(product.getQty()));
        holder.tvEntryBy.setText(product.getEntry_by());
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class HolderProduct extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPurchasePrice, tvSellingPrice, tvQty, tvEntryBy;


        public HolderProduct(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPurchasePrice = itemView.findViewById(R.id.tv_purchase_price);
            tvSellingPrice = itemView.findViewById(R.id.tv_selling_price);
            tvQty = itemView.findViewById(R.id.tv_qty);
            tvEntryBy = itemView.findViewById(R.id.tv_entry_by);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Yakin ingin menghapus?");
                    dialogPesan.setCancelable(true);

                    idProduct = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteProduct();
                            dialog.dismiss();
                        }
                    });

                    dialogPesan.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    dialogPesan.show();

                    return false;
                }
            });
        }

        private void deleteProduct() {
            ApiInterface arData = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseProduct> deleteProduct = arData.deleteProduct(idProduct);

            deleteProduct.enqueue(new Callback<ResponseProduct>() {
                @Override
                public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                    String message = response.body().getMessage();
                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseProduct> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
