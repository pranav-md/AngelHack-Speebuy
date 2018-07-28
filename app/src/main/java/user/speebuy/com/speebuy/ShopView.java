package user.speebuy.com.speebuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by User on 28-Jul-18.
 */

public class ShopView extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_view);
        Intent intent=new Intent();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final String uid = extras.getString("shop_id");
            String name = extras.getString("Name");
            String open = extras.getString("Open");
            String address = extras.getString("Name");
            String distance = extras.getString("Distance");
            String delivery = extras.getString("Delivery");
            String rating = extras.getString("Rating");
            setShopView(name, address, open, distance, delivery, rating);

            Button btn = (Button) findViewById(R.id.startshop_button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShopView.this, ShoppingView.class);
                    intent.putExtra("shop_id", uid);
                    startActivity(intent);
                }
            });
        }
    }

    void setShopView(String name,String address,String open,String dist,String delivery,String rating)
    {
        TextView t_name=(TextView)findViewById(R.id.name_shop);
        TextView t_rating=(TextView)findViewById(R.id.rating_shop);
        TextView t_address=(TextView)findViewById(R.id.address_shop);
        TextView t_delvery=(TextView)findViewById(R.id.delivery_shop);

        t_name.setText(name);
        t_rating.setText(rating);
        t_address.setText(address);
        t_delvery.setText(delivery);
    }
}
