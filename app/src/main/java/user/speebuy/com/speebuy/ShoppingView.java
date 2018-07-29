package user.speebuy.com.speebuy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 */

public class ShoppingView extends AppCompatActivity {

    ArrayList<ShoppingProducts> added_products;
    ArrayList<Products> products;

    String uid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_view);
        Bundle extras = getIntent().getExtras();
        products=new ArrayList<Products>();
        added_products=new ArrayList<ShoppingProducts>();
        setBottomCart();
        View float_view=(View)findViewById(R.id.float_view);
        Button buy=(Button)float_view.findViewById(R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View deliveryview=(View)findViewById(R.id.delivery_view);
                View list_stage=(View)findViewById(R.id.list_stage);
                deliveryview.setVisibility(View.VISIBLE);
                list_stage.setVisibility(View.INVISIBLE);
                Button order_send=(Button)findViewById(R.id.order_now);
                order_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        int sumprice=0;
                        for(ShoppingProducts getpdt:added_products)
                        {
                            rootRef.child("orders").child("products").child(getpdt.id).setValue(getpdt.qty+"");
                            sumprice+=getpdt.offer_price;
                        }
                        rootRef.child("orders").child("price").setValue(sumprice+"");
                        RadioButton pickup=(RadioButton)findViewById(R.id.pickup);
                        boolean pkup=false;
                        if(pickup.isChecked())
                            pkup=true;
                        rootRef.child("orders").child("pickup").setValue(pkup);


                    }
                });
            }
        });
        added_products=new ArrayList<ShoppingProducts>();
        if(extras != null) {
            uid = extras.getString("shop_id");
            getProducts();
        }
    }
    void getProducts()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Categories").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                int count = 0;
                for(DataSnapshot ds1:dataSnapshot.getChildren())
                {
                    count+=ds1.getChildrenCount();
                    for(DataSnapshot ds2:ds1.getChildren())
                    {
                        ++i;
                        String p_id=ds2.getValue().toString();
                        final int finalCount = count;
                        final int finalI = i;
                        rootRef.child("Products").child(p_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Products newproduct=new Products();
                                newproduct.name=dataSnapshot.child("name").getValue().toString();
                                newproduct.offer_price=Integer.parseInt(dataSnapshot.child("offer_price").getValue().toString());
                                newproduct.real_price=Integer.parseInt(dataSnapshot.child("real_price").getValue().toString());
                                newproduct.p_id=dataSnapshot.child("product_id").getValue().toString();
                                newproduct.price_rate=dataSnapshot.child("price_rate").getValue().toString();
                                newproduct.img_link=dataSnapshot.child("img_url").getValue().toString();
                                newproduct.category=dataSnapshot.child("category").getValue().toString();
                                products.add(newproduct);
                                if(finalI == finalCount)
                                {
                                    ProductListAdapter productListAdapter=new ProductListAdapter();
                                    productListAdapter.getData(products,ShoppingView.this);
                                    ListView prodctlist=(ListView)findViewById(R.id.shop_list);
                                    prodctlist.setAdapter(productListAdapter);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void addProduct(String id,int quantity)
    {
        for(Products pdt:products)
        {
            if(pdt.p_id.equals(id))
            {
                ShoppingProducts newaddedpdt=new ShoppingProducts();
                newaddedpdt.id=id;
                newaddedpdt.name=pdt.name;
                newaddedpdt.offer_price=pdt.offer_price;
                newaddedpdt.real_price=pdt.real_price;
                newaddedpdt.qty=quantity;
                boolean entr=false;
                for(ShoppingProducts spdt:added_products)
                    if(spdt.id==id)
                    {
                        entr=true;
                        added_products.set(added_products.indexOf(spdt), newaddedpdt);
                    }
                 if(entr==false)
                     added_products.add(newaddedpdt);
            }
        }
        setBottomCart();
    }

    void setBottomCart()
    {
        View float_view=(View)findViewById(R.id.float_view);
        TextView itm=(TextView)float_view.findViewById(R.id.num_item);
        TextView totcost=(TextView)float_view.findViewById(R.id.tot_cost);
        TextView savedcost=(TextView)float_view.findViewById(R.id.saved_cost);

        int itms=0,totalcst=0,svcst=0;
        for(ShoppingProducts spdt:added_products)
        {
            itms+=spdt.qty;
            totalcst+=spdt.real_price*spdt.qty;
            svcst+=spdt.offer_price*spdt.qty;
        }
        totcost.setText("₹"+totalcst);
        itm.setText(itms+"");
        savedcost.setText("₹"+svcst);
    }
}
