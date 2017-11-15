package nz.uict.a2037689.tuckbox;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static nz.uict.a2037689.tuckbox.UserManager.user;


public class MainMenu extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);




    }



    public void mainMenuOrderClick(View view){
        Intent intent = new Intent(this, Time.class);
        startActivity(intent);
    }


    public void LogOutClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




    //DISABLE BACK BUTTON
    @Override
    public void onBackPressed() {
    }

}
