package com.example.yemekle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Food> {

    private final LayoutInflater inflater;
    private final Context context;
    private RecyclerView.ViewHolder holder;
    private final ArrayList<Food> foods;

    public CustomAdapter(Context context,ArrayList<Food> foods)
    {
        super(context,0,foods);
        this.context=context;
        this.foods=foods;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Food getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return foods.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView=inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView textView=(TextView) convertView.findViewById(R.id.txtView);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.iconImage);

        Food food = foods.get(position);
        if (food !=null) {

            textView.setText(food.GetName());

            //picasso dbden çekilirse patlıyor
            Glide.with(convertView).load(food.GetImgUrl()).into(imageView);
        }
        return convertView;
    }
}
