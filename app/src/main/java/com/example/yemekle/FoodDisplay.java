package com.example.yemekle;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FoodDisplay extends AppCompatActivity {


    int index;
    ArrayList<Food> foods;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_display);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        index=(int) getIntent().getExtras().getInt("index");
        foods=(ArrayList<Food>) getIntent().getExtras().getSerializable("foods");
        TextView expText=findViewById(R.id.expText);
        expText.setText(foods.get(index).GetText());


        webViewStart();



        //youtube videoViewden görüntülenme kabul etmiyor
//        VideoView videoView=(VideoView) findViewById(R.id.videoView);
//        Uri videoUri = Uri.parse(videoUrl);
//        videoView.setVideoURI(videoUri);

    }

    private void webViewStart() {

        webView=findViewById(R.id.YTwebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        String videoURL=foods.get(index).GetVideoUrl();
        String croppedYT = "<iframe width=\"414\" height=\"330\" src=\""+videoURL+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
        webView.loadDataWithBaseURL(null,croppedYT,"text/html","utf-8",null);
    }
}