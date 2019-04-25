package myproject.travelpms;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Kelas.SharedVariable;
import Kelas.UserModel;
import Kelas.Utils;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    Intent i;
    EditText etUsername, etPassword,etConfirmPassword,etAlamat,etNope,etEmail;

    Button signUpButton;
    UserModel userDaftarModel;
    private String time,levelUser;
    private SweetAlertDialog pDialogLoading;
    HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        signUpButton = (Button) findViewById(R.id.signUpBtn);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etNope = findViewById(R.id.etNope);
        etAlamat = findViewById(R.id.etAlamat);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });


        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading");
        pDialogLoading.setCancelable(false);


    }



    private void checkValidation() {

        // Get all edittext texts
        String getFullName = etUsername.getText().toString();
        String getPassword = etPassword.getText().toString();
        String getEmailId = etEmail.getText().toString();
        String getNope = etNope.getText().toString();
        String getAlamat = etAlamat.getText().toString();
        String getConfirmPassword = etConfirmPassword.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("") || getConfirmPassword.length() == 0
                || getNope.equals("") || getNope.length() == 0
                || getAlamat.equals("") || getAlamat.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0) {

            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Semua field harus diisi")
                    .setTitleText("Oops..")
                    .show();
            //check valid email
        }
        else if (!m.find()) {
            new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Email yang anda masukkan tidak valid")
                    .setTitleText("Oops..")
                    .setConfirmText("Siap")
                    .show();
        }
        // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword)) {
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Konfirmasi password tidak valid")
                    .setTitleText("Oops..")
                    .show();
        }
        // Else do signup or do your stuff
        else
            pDialogLoading.show();
            signUp(getFullName,getPassword,getEmailId,getAlamat,getNope);

    }



    private void signUp(final String username, final String passwordUser,final String email,
                    final String alamat,final String nope){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... strings) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("namaPelanggan", username));
                nameValuePairs.add(new BasicNameValuePair("password", passwordUser));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("alamat", alamat));
                nameValuePairs.add(new BasicNameValuePair("no_hape", nope));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            SharedVariable.ipServer+"/User/simpanUserData/");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }

                return "success";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200){
                    pDialogLoading.dismiss();
                    etUsername.setText("");
                    etPassword.setText("");
                    etConfirmPassword.setText("");
                    etNope.setText("");
                    etAlamat.setText("");
                    etEmail.setText("");

                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText("Pendaftaran Berhasil, silakan cek email anda untuk memverifikasi pendaftaran")
                            .setTitleText("Pendaftaran Berhasil")
                            .show();
                }else {
                    pDialogLoading.dismiss();
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Terjadi kesalahan, coba lagi nanti")
                            .setTitleText("Error")
                            .show();
                }

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(username,passwordUser,email,alamat,nope);

    }


}
