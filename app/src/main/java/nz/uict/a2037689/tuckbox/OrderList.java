package nz.uict.a2037689.tuckbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

public class OrderList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);




    }

@Override
protected void onStart(){

    super.onStart();

    ListView listView=(ListView)findViewById(R.id.lvOrders);

    final OrderAdapter adapter = new OrderAdapter(UserManager.user.getOrderList(), getApplicationContext());


    //SWIPE TO DELETE WIDGET
    final SwipeActionAdapter swipeAdapter = new SwipeActionAdapter(adapter);
    swipeAdapter.setListView(listView);
    swipeAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.layout_swipe_delete);

    swipeAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
        @Override
        public boolean hasActions(int position, SwipeDirection direction) {
            return direction.isLeft();
        }

        @Override
        public boolean shouldDismiss(int position, SwipeDirection direction) {
            return direction.isLeft();
        }

        @Override
        public void onSwipe(int[] position, SwipeDirection[] direction) {
            for (int i = 0; i < position.length; i++) {
                SwipeDirection dir = direction[i];
                int pos = position[i];
                if (!dir.isLeft())
                    continue;

                OrderInformation order = adapter.getItem(pos);
                UserManager.user.getOrderList().remove(order);
            }
            OrderList.this.onStart();
        }
    });

    listView.setAdapter(swipeAdapter);









}

    public void plusClick(View view){
        Intent intent = new Intent(this, MealMenu.class);
        startActivity(intent);
    }


    //Create next button
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.next) {
            Intent intent = new Intent(this, CreditCard.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
