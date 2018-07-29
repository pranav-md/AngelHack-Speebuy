package user.speebuy.com.speebuy;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 15-Jul-18.
 */

public class FirebaseTokenID extends FirebaseInstanceIdService
{
    @Override
    public void onCreate() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendTokenToDB(refreshedToken);

        super.onCreate();
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendTokenToDB(refreshedToken);
    }
    void sendTokenToDB(String token)
    {
        SharedPreferences shrdPref = getSharedPreferences("USER_CODE", MODE_PRIVATE);
        String uid = shrdPref.getString("code", "null");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Users").child(uid).child("token").setValue(token);
    }


}
