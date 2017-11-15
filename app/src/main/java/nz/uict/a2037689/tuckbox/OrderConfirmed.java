package nz.uict.a2037689.tuckbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OrderConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);



        UserManager.user.addToHistory();
        UserManager.user.getOrderList().clear();



    }

    public void onClickMainMenu (View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }





}
