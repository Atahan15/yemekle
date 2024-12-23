package com.example.yemekle;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Food implements Serializable {


    private String name;
    private String videoUrl;
    private String imgUrl;
    private String text;
    URL url;

    public Food(String name,String videoUrl,String imgUrl,String text)
    {
        this.name=name;
        this.videoUrl=videoUrl;
        this.imgUrl=imgUrl;
        this.text=text;
        try {
            url=new URL(imgUrl);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public String GetName()
    {
        return this.name;
    }
    public String GetVideoUrl()
    {
        return this.videoUrl;
    }
    public String GetImgUrl()
    {
        return this.imgUrl;
    }
    public String GetText()
    {
        return this.text;
    }


}
