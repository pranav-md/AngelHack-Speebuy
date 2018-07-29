package user.speebuy.com.speebuy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Shops> shops;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,MyFirebaseMessagingService.class));
        progressBar=new ProgressDialog(MainActivity.this);
        progressBar.show();
        shops=new ArrayList<Shops>();
        getShops();
    }
    void getShops()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Log.e("FIREBS","Yo getshops");
        rootRef.child("Shop").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("FIREBS","inside the inner loop "+dataSnapshot.getValue());
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Log.e("FIREBS","inside the loop "+ds.getKey());
                    Shops newshop=new Shops();
                    newshop.shop_id=ds.child("shop_id").getValue().toString();
                    newshop.distance=Float.parseFloat(ds.child("distance").getValue().toString());
                    newshop.name=ds.child("name").getValue().toString();
                    newshop.img_url=ds.child("img_url").getValue().toString();
                    newshop.address=ds.child("address").getValue().toString();
                    newshop.delivery=(boolean)ds.child("delivery").getValue();
                    newshop.lat=(double)ds.child("lat").getValue();
                    newshop.lng=(double)ds.child("lng").getValue();
                    newshop.open=(boolean)ds.child("open").getValue();
                    newshop.rating=(int)Integer.parseInt(ds.child("rating").getValue().toString());
                    shops.add(newshop);
                }
                progressBar.dismiss();
                progressBar.cancel();
                ShopListAdapter shopListAdapter=new ShopListAdapter();
                shopListAdapter.getData(shops,MainActivity.this);
                ListView listView=(ListView)findViewById(R.id.shop_list);
                listView.setAdapter(shopListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
