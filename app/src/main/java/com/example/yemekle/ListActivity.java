package com.example.yemekle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Food> foods;
    ListView listView;
    CustomAdapter listViewAdapter;
    FirebaseDatabase database=FirebaseDatabase.getInstance("https://yemekle-f215d-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference yemeklerRef = database.getReference("Yemekler");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          return insets;
        });

        foods = new ArrayList<Food>();
        listView = (ListView) findViewById(R.id.listView);
        listViewAdapter = new CustomAdapter(this,foods);

        DBRead();
        ListSelect();



    }

    private void ListSelect() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent go =new Intent(ListActivity.this, FoodDisplay.class);
                go.putExtra("index",i);
                go.putExtra("foods",foods);
                startActivity(go);

            }
        });
    }

    private void DBRead()
    {
        yemeklerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long foodCount = snapshot.getChildrenCount();
                foods.clear();
                for (int i = 0;i< foodCount; i++) {

                    DataSnapshot foodSnapshot = snapshot.child(String.valueOf(i));
                    String name=foodSnapshot.child("isim").getValue(String.class);
                    String videoUrl=foodSnapshot.child("videourl").getValue(String.class);
                    String imgUrl=foodSnapshot.child("resimurl").getValue(String.class);
                    String text=foodSnapshot.child("açıklama").getValue(String.class);
                    Food food = new Food(name,videoUrl,imgUrl,text);
                    foods.add(food);

                }
                listView.setAdapter(listViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListActivity.this, "Hata= "+error, Toast.LENGTH_SHORT).show();
            }

        });
    }
}