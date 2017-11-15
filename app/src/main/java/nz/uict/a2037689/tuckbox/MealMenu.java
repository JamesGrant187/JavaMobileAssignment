package nz.uict.a2037689.tuckbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;


//List of meals available for selection
public class MealMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_menu);

    }


    public void orderFormContinueClick(View view) {
        //When continue is clicked it brings up Address.java
        Intent intent = new Intent(this, Address.class);
        startActivity(intent);
    }


    public void onFirstClick(View view) {

        //Add meals to the list using addMeal
        addMeal(0);

    }

    public void onSecondClick(View view) {


        addMeal(1);

    }

    public void onThirdClick(View view) {

        addMeal(2);
    }

    public void onFourthClick(View view) {

        addMeal(3);
    }


    public void addMeal(final int index) {

        //Create an array and pass in meal names
        final String[] mealNames = new String[]{
                getString(R.string.beefnoodle),
                getString(R.string.greensalad),
                getString(R.string.lambkorma),
                getString(R.string.openchicken),
        };

        //Create an array and pass in titles for when the user is selecting their meal options
        final String[] dialogTitles = new String[]{
                getString(R.string.beefnoodledialogtitle),
                getString(R.string.greensaladdialogtitle),
                getString(R.string.lambkormadialogtitle),
                getString(R.string.openchickendialogtitle),
        };

        //Create a multidimensional array that contains the meal options for each meal
        final String[][] mealOptions = new String[][]{
                {
                        getString(R.string.beefnoodleoptionone),
                        getString(R.string.beefnoodleoptiontwo),
                },
                {
                        getString(R.string.greensaladoptionone),
                        getString(R.string.greensaladoptiontwo),
                        getString(R.string.greensaladoptionthree),
                },
                {
                        getString(R.string.lambkormaoptionone),
                        getString(R.string.lambkormaoptiontwo),
                        getString(R.string.lambkormaoptionthree),
                },
                {
                        getString(R.string.openchickenoptionone),
                        getString(R.string.openchickenoptiontwo),
                        getString(R.string.openchickenoptionthree),
                }
        };
        //Create a Drawable array that contains the image for each meal.
        final Drawable[] mealImage = new Drawable[]{
                getDrawable(R.drawable.beefnoodlesaladsmall),
                getDrawable(R.drawable.greensaladsmall),
                getDrawable(R.drawable.lambkormasmall),
                getDrawable(R.drawable.openchickensandwhichsmall),


        };

        //Create the dialog box that opens to the user when a meal is selected, allowing them to pick an option.
        final AlertDialog.Builder optionsDialog = new AlertDialog.Builder(MealMenu.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        //Set the title for the dialogbox determined by which meal is selected (index).
        optionsDialog.setTitle(dialogTitles[index]);
        //Remove the option of just being able to click outside of the dialog to close it
        optionsDialog.setCancelable(false);
        //Create the list of items that are selectable in the dialog box.
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(MealMenu.this, android.R.layout.simple_list_item_single_choice);
        //Pass in meal options to the dialog box list determined on which meal has been selected (index)
        adapter.addAll(mealOptions[index]);


        //Display the alert dialog with an OnClickListener
        AlertDialog dialog = optionsDialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override

            //When the user taps on an option item, add it to the orderList object along with the title, the option the user selected and the image of the selected meal.
            public void onClick(DialogInterface dialogInterface, int i) {
                UserManager.user.getOrderList().add(new OrderInformation(mealNames[index], mealOptions[index][i], mealImage[index]));
                finish();
                //Close the dialog box
                dialogInterface.dismiss();
            }

            //Display a Cancel button on the dialog box to allow the user to return to the meal list.
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();

    }
}
