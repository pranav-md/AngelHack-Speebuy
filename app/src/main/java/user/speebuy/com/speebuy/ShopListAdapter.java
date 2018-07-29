package user.speebuy.com.speebuy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by User on 28-Jul-18.
 */

public class ShopListAdapter extends BaseAdapter {

    ArrayList<Shops> shops;
    Context context;
    void getData(ArrayList<Shops> shops, Context context)
    {
        this.shops=shops;
        this.context=context;
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.shop_listitem, null);
        TextView name=(TextView)view.findViewById(R.id.shop_name);
        TextView open=(TextView)view.findViewById(R.id.shop_open);
        TextView distance=(TextView)view.findViewById(R.id.shop_distance);
        TextView delivery=(TextView)view.findViewById(R.id.shop_delivery);
        ImageView img=(ImageView)view.findViewById(R.id.img);
        Log.e("IMGURL",shops.get(position).img_url);
        Glide.with(context).load(shops.get(position).img_url).into(img);
        RatingBar rating=(RatingBar)view.findViewById(R.id.rating);
        name.setText(shops.get(position).name);
        if(shops.get(position).open)
            open.setText("Open Now");
        else
            open.setText("Closed");
        distance.setText(shops.get(position).distance+" KM far away");
        if(shops.get(position).delivery)
            delivery.setText("Delivery available");
        else
            delivery.setText("Delivery unavailable");
        rating.setRating(shops.get(position).rating);
        view.setTag(shops.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shops shops=(Shops) v.getTag();
                Intent intent=new Intent(context,ShopView.class);
                intent.putExtra("shop_id",shops.shop_id);
                intent.putExtra("Name",shops.name);
                intent.putExtra("Address",shops.address);
                intent.putExtra("Image",shops.img_url);

                if(shops.open)
                    intent.putExtra("Open","Open now");
                else
                    intent.putExtra("Open","Closed");
                intent.putExtra("Distance","1 Km away");
                if(shops.delivery)
                    intent.putExtra("Delivery","Delivery available");
                else
                    intent.putExtra("Delivery","Delivery unavailable");
                intent.putExtra("Rating",shops.rating);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
