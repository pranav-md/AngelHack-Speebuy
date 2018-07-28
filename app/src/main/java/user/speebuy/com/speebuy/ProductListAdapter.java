package user.speebuy.com.speebuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 28-Jul-18.
 */

public class ProductListAdapter extends BaseAdapter {
   Context context;
   ArrayList<Products> productsArrayList;

    void getData(ArrayList<Products> productsArrayList,Context context)
    {
        this.productsArrayList=productsArrayList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return productsArrayList.size();
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
        View view = inflater.inflate(R.layout.product_list_item, null);
        TextView name=view.findViewById(R.id.product_name);
        TextView price=view.findViewById(R.id.product_price);
        TextView qty=view.findViewById(R.id.quantity);
        view.setTag(position);
        ImageButton plus=(ImageButton)view.findViewById(R.id.plus);
        plus.setTag(view);
        ImageButton minus=(ImageButton)view.findViewById(R.id.minus);
        minus.setTag(view);
        Button addbtn=(Button)view.findViewById(R.id.add_cart);
        addbtn.setTag(view);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=(View)v.getTag();
                int num=(int)view.getTag();
                String s_id = productsArrayList.get(num).p_id;
                ShoppingView shoppingView=(ShoppingView)context;
                shoppingView.addProduct(s_id,productsArrayList.get(num).qty);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=(View)v.getTag();
                int num=(int)view.getTag();
                TextView qty=(TextView)view.findViewById(R.id.quantity);
                TextView price=(TextView)view.findViewById(R.id.price);

                String s_id = productsArrayList.get(num).p_id;
                ++productsArrayList.get(num).qty;
                qty.setText(""+productsArrayList.get(num).qty);
                price.setText(""+productsArrayList.get(num).qty*productsArrayList.get(num).offer_price);
                }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=(View)v.getTag();
                int num=(int)view.getTag();
                TextView qty=(TextView)view.findViewById(R.id.quantity);
                TextView price=(TextView)view.findViewById(R.id.price);

                String s_id = productsArrayList.get(num).p_id;
                if(productsArrayList.get(num).qty!=0)
                    --productsArrayList.get(num).qty;
                qty.setText(""+productsArrayList.get(num).qty);
                price.setText(""+productsArrayList.get(num).qty*productsArrayList.get(num).offer_price);
                   }
        });

        name.setText(productsArrayList.get(position).name);
        price.setText(productsArrayList.get(position).real_price+" "+productsArrayList.get(position).price_rate);

        return view;
    }
}
