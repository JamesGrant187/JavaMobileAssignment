package nz.uict.a2037689.tuckbox;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//Pass order information into a viewable way
//https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial


public class OrderAdapter extends ArrayAdapter<OrderInformation> implements View.OnClickListener{



    //View lookup
    private static class ViewHolder {
        TextView itemName;
        TextView itemDescription;
        ImageView itemImage;
    }

    public OrderAdapter(List<OrderInformation> data, Context context) {
        super(context, R.layout.layout_order_item,data);

    }


    @Override
    public void onClick(View v){
        int position=(Integer) v.getTag();
        Object object=getItem(position);
        OrderInformation orderInformation = (OrderInformation) object;
        //Handle clicking each order here
        Snackbar.make(v,orderInformation.name, Snackbar.LENGTH_INDEFINITE).show();
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        OrderInformation orderInformation = getItem(position);
        //Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; //View lookup cache stored in tag

        final View result;

        if(convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_order_item, parent, false);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemTitle);
            viewHolder.itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
            viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.itemImage);

            result=convertView;

            convertView.setTag(viewHolder);
        }   else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.itemName.setText(orderInformation.name);
        viewHolder.itemDescription.setText(orderInformation.description);
        viewHolder.itemImage.setImageDrawable(orderInformation.image);

        return convertView;

    }




}
