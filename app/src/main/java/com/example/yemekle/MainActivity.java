package com.example.yemekle;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView animImg;
    TextView mainText;
    //.info/connected dbden ayrı olarak firebase'e bağlı olup olmadığını bool döndüren tek bir node içeriyo
    DatabaseReference controldb= FirebaseDatabase.getInstance("https://yemekle-f215d-default-rtdb.europe-west1.firebasedatabase.app/").getReference(".info/connected");
    boolean isConnectedV =false;
    boolean checkerchecker=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        animImg=findViewById(R.id.animView);
        mainText=findViewById(R.id.startView);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.welcomeanim);

        animImg.startAnimation(animation);


        if(IsInternetConnected())
        {

            DBcheck();

        }
        else Toast.makeText(MainActivity.this, "Lütfen İnternetinizi Açıp Uygulamayı Yeniden Başlatın", Toast.LENGTH_SHORT).show();

    }


    private void DBcheck() {

        controldb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Animation secondAnimation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.startanim);
                isConnectedV= (boolean) snapshot.getValue();
                if (isConnectedV)
                {
                    Toast.makeText(MainActivity.this, "Bağlantı Başarılı", Toast.LENGTH_SHORT).show();
                    animImg.setAlpha(0f);
                    mainText.startAnimation(secondAnimation);
                    new Handler().postDelayed(() -> {

                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        finish();
                    }, 4000);


                }
                else Toast.makeText(MainActivity.this, "DB bağlantı hatası", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "db sorgusu başarısız", Toast.LENGTH_SHORT).show();
                return;
            }

        });
    }

//    private void Checker()
//    {
//
//        if (IsDbConected()&&IsInternetConnected()&&checkerchecker)
//        {
//            Toast.makeText(this, "Diğer Sayfaya Geçiliyor", Toast.LENGTH_SHORT).show();
//
//
//        }
//        else Toast.makeText(this, "İnternet="+IsInternetConnected()+"Database="+IsDbConected(), Toast.LENGTH_SHORT).show();
//
//
//
//
//
//    }

    private boolean IsInternetConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        return connected;
    }
//    private boolean IsDbConected()
//    {
//
//
//        controldb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                isConnectedV= (boolean) snapshot.getValue();
//                Toast.makeText(MainActivity.this, "bağlantı durumu"+isConnectedV, Toast.LENGTH_SHORT).show();
//                //sonradan db bağlantısı sağlanırsa tekrar çalıştırsın diye
//                if (isConnectedV) Checker();
//                return;
//
//
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "dbbaşarısız", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//        });
//                return isConnectedV;
//    }

}