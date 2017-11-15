package nz.uict.a2037689.tuckbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;




//Order Confirmation screen
public class Confirmation extends AppCompatActivity {


    //Set two string variables for time and date
    String time;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


        //Find the two textviews on activity_confirmation
        TextView tvTimeShow = (TextView) findViewById(R.id.tvTimeShow);
        TextView tvDateShow = (TextView) findViewById(R.id.tvDateShow);
        //Take time and date information from Time.java and display them on the form
        tvTimeShow.setText(Time.time);
        tvDateShow.setText(Time.date);



    }

    @Override
    protected void onStart() {

    //Call default behavior with super (Because it asked me to)
        super.onStart();
        //Grab listview orders from the layout
        ListView listView = (ListView) findViewById(R.id.lvOrders);
        //Create a new order to display selected orders
        OrderAdapter adapter = new OrderAdapter(UserManager.user.getOrderList(), getApplicationContext());
        //Display orders
        listView.setAdapter(adapter);
    }


    public void confirmOnClick(View view){
        //When confirm button is clicked, go to OrderConfirmed activity.
        Intent intent = new Intent(this, OrderConfirmed.class);
        startActivity(intent);

    }





}
