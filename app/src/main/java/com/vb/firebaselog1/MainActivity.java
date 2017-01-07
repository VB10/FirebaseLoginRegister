package com.vb.firebaselog1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText Field_Mail,Field_Password;
    private FirebaseAuth  mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();

        //bizim dinleyici metodumuz bizim sistemde login olup olmama durumuza göre kontorl ediyor
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();   
                if (user!=null){
                    //eger login halindeyseniz hala direk ana sayfaya atıyor
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }
                else Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();

            }
        };
    
    }

    private void load() {
        //xml'den gelen verileri tanıttığımız alan
        mProgressDialog=new ProgressDialog(this);
        Field_Mail= (EditText) findViewById(R.id.et_mail);
        Field_Password= (EditText) findViewById(R.id.et_Password);
        //Firebase login işlemiyle artık servis ile bağlanıtyı kuruyor
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }




    public void connect(View view) {
        // Login butoununa  tıklanınca gerçekleşen olaylar
        mProgressDialog.setMessage("Loading..");
        mProgressDialog.show();
        String  email=Field_Mail.getText().toString();
        String  password=Field_Password.getText().toString();

        //Firebase ile tek metod ile mail ve passwordu alıp eğer var ise ona göre işlem yaptırıyoruz
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                
                if (!task.isSuccessful())

                    Toast.makeText(MainActivity.this, "Error Login", Toast.LENGTH_SHORT).show();
                else {

                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }
                mProgressDialog.dismiss();

            }
        });
    }
    public void regist(View view) {
        mProgressDialog.setMessage("Regist..");
        mProgressDialog.show();
        String  email=Field_Mail.getText().toString();
        String  password=Field_Password.getText().toString();

        //Firebase ile tek metod ile mail ve passwordu alıp kayıt edebiliyoruz
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "failed(Check network)",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                        }

                        mProgressDialog.dismiss();
                    }
                });


    }
}
