package user.speebuy.com.speebuy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 28-Jul-18.
 */

public class ShoppingProducts implements Parcelable {
    String id,name;
    int real_price,offer_price;
    int qty;

    protected ShoppingProducts(Parcel in) {
        id = in.readString();
        name = in.readString();
        real_price = in.readInt();
        offer_price = in.readInt();
        qty = in.readInt();
    }
    ShoppingProducts()
    {}
    public static final Creator<ShoppingProducts> CREATOR = new Creator<ShoppingProducts>() {
        @Override
        public ShoppingProducts createFromParcel(Parcel in) {
            return new ShoppingProducts(in);
        }

        @Override
        public ShoppingProducts[] newArray(int size) {
            return new ShoppingProducts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(real_price);
        dest.writeInt(offer_price);
        dest.writeInt(qty);
    }
}
