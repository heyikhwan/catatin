package com.ikhwan.catatin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ikhwan.catatin.R;
import com.ikhwan.catatin.Rest.ApiClient;
import com.ikhwan.catatin.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etrUsername)
    TextInputEditText etrUsername;
    @BindView(R.id.etrPassword)
    TextInputEditText etrPassword;
    @BindView(R.id.etrFullName)
    TextInputEditText etrFullname;
    @BindView(R.id.etrEmail)
    TextInputEditText etrEmail;
    @BindView(R.id.etrAddress)
    TextInputEditText etrAddreess;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    String gender;

    ApiInterface apiService;
    ProgressDialog pd;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender = "male";
                    break;
            case R.id.radio_female:
                if (checked)
                    gender = "female";
                    break;
        }
    }

    private void signup() {
        Log.d(TAG,"Menjalankan method Signup");
        pd = ProgressDialog.show(this,null,"Sedang mendaftarkan account",true,false);
        String strUsername = etrUsername.getText().toString();
        String strPassword = etrPassword.getText().toString();
        String strNamaLengkap = etrFullname.getText().toString();
        String strEmail = etrEmail.getText().toString();
        String strAlamat = etrAddreess.getText().toString();

        Log.d(TAG,"mendapatkan data dari edit text");

        apiService.register(strUsername,strPassword,strNamaLengkap,strEmail,strAlamat, gender).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"berhasil memanggil api");
                if (response.isSuccessful()){
                    pd.dismiss();
                    Log.d(TAG,"succes mendapatkan api");
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG,t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}