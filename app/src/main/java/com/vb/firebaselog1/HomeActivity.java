package com.vb.firebaselog1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView tv_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        load();
    }

    private void load() {
        //firebase ile bağlantıyı kuruyoruz
        mAuth = FirebaseAuth.getInstance();
        tv_mail = (TextView) findViewById(R.id.tv);
        //Eğer bir user bağlandıysa onun var olup olmadığını kotnrol ediyoruz
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            //Bu kişinin mailini alıp textboxa basıyoruz ve olay bitiyor
            tv_mail.setText("Welcome \n" + user.getEmail());
        }
    }

    public void exit(View view) {
        mAuth.signOut();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));


    }
}
