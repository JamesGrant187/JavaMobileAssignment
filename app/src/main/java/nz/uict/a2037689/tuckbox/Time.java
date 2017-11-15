package nz.uict.a2037689.tuckbox;

import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Locale;
import java.util.TimeZone;

import static nz.uict.a2037689.tuckbox.R.id.etPickDate;

//Time information for the order
public class Time extends AppCompatActivity {

    //Create two static variables for time/date
    public static String time, date;

    //Declare information for the widget spinner I'm using. (After some research, it's apparently just a TextView object that works like a spinner)
    MaterialBetterSpinner materialBetterSpinner;
    //Create a Calendar object and pass in NZ as a timezone.
    Calendar myCalendar = Calendar.getInstance(TimeZone.getTimeZone("NZ"));
    //Create a variable for passing information through each section.
    EditText edittext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);


        //Spinner widget information, it's fancy!
        //Create a string and pass in the timewindows array from strings.xml for each delivery time slot available
        String[] aTime = getResources().getStringArray(R.array.timewindows);
        //Create the spinner and pass in xml information (spTimeWindow)
        materialBetterSpinner = (MaterialBetterSpinner) findViewById(R.id.spTimeWindow);
        //Create an array object and set it's layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Time.this, android.R.layout.simple_dropdown_item_1line, aTime);
        //Set the spinner to the array
        materialBetterSpinner.setAdapter(adapter);


        //Set the global variable edittext to etPickDate EditText on the form
        edittext = (EditText) findViewById(etPickDate);
        //Create a DatePickerDialog box to allow the user to pick a date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            //Set variables for year, month and day.
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                //Set calendar options to the given variables
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        //When the user clicks the specific date, set the edittext variable.
        edittext.setOnClickListener(new View.OnClickListener() {

            //Create DatePickerDialog on click of the Date EditText on the form
            //Only show current day if the time is before 10AM in the morning
            //Due to limits of 10AM cut off time for delivery on current day
            @Override
            public void onClick(View v) {
                //Create the dialog box with this activity.
                DatePickerDialog dateDialog = new DatePickerDialog(Time.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //Limit user options to tomorrow if the time is greater than 10AM in the morning.
                if(Calendar.getInstance(TimeZone.getTimeZone("NZ")).get(Calendar.HOUR_OF_DAY) >= 10){
                    dateDialog.getDatePicker().setMinDate(Calendar.getInstance(TimeZone.getTimeZone("NZ")).getTimeInMillis()+86400000);
                }
                else
                {   //If the time is before 10AM, allow today to be picked. (Android studio didn't like this until I put in -1000 for some reason.
                    dateDialog.getDatePicker().setMinDate(Calendar.getInstance(TimeZone.getTimeZone("NZ")).getTimeInMillis()-1000);
                }

                //Display date picker.
                dateDialog.show();


            }
        });





    }

    //Update the EditText field on the form when the user picks a date.
    private void updateLabel() {

        //Set a variable for the date format
        String myFormat = "dd/MM/yyyy";
        //Create a SimpleDateFormat object
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //Set edittext with date information.
        edittext.setText(sdf.format(myCalendar.getTime()));
    }


    //When Continue is pressed, check that information has been entered into the UI.
    public void onClickTimeContinue(View view) {

        //If the two UI steps are empty display a Toast popup asking to select a Time/Date.
       if (((MaterialBetterSpinner)findViewById(R.id.spTimeWindow)).getText().toString().isEmpty()||((EditText)findViewById(R.id.etPickDate)).getText().toString().isEmpty())
       {
           Toast.makeText(Time.this, "Please select Time/Date", Toast.LENGTH_SHORT).show();

       }
       //If the two steps have been completed then go to ordering a meal, setting time/date variables to what has been selected for future use.
        else {

           //Set time/date global variables for use elsewhere/database.
           time =((MaterialBetterSpinner)findViewById(R.id.spTimeWindow)).getText().toString();
           date = ((EditText)findViewById(R.id.etPickDate)).getText().toString();
           UserManager.user.setOrderTime(time);
           UserManager.user.setOrderDate(date);


           //Move to next activity, Address.
           Intent intent = new Intent(this, Address.class);
           startActivity(intent);
       }

    }


}
