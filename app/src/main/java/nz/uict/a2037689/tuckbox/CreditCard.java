package nz.uict.a2037689.tuckbox;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CreditCard extends AppCompatActivity {

    //Spinner textview
    MaterialBetterSpinner materialBetterSpinner;
    //Variable for keeping track of creditcard number
    private EditText etCreditCardNumber, etCreditCardCVV;
    //Count for automatic hyphens
    int count = 0;

    private String expMonth;
    private String expYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        etCreditCardNumber = (EditText) findViewById(R.id.etEnterCreditCardNumber);
        etCreditCardCVV = (EditText) findViewById(R.id.etCreditCVV);

        //Using a TextWatcher, place hypens into the credit card number for every fourth character
        etCreditCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (count <= etCreditCardNumber.getText().toString().length()
                        && (etCreditCardNumber.getText().toString().length() == 4
                        || etCreditCardNumber.getText().toString().length() == 9
                        || etCreditCardNumber.getText().toString().length() == 14)) {
                    etCreditCardNumber.setText(etCreditCardNumber.getText().toString() + "-");
                    int pos = etCreditCardNumber.getText().length();
                    etCreditCardNumber.setSelection(pos);
                } else if (count >= etCreditCardNumber.getText().toString().length()
                        && (etCreditCardNumber.getText().toString().length() == 4
                        || etCreditCardNumber.getText().toString().length() == 9
                        || etCreditCardNumber.getText().toString().length() == 14)) {
                    etCreditCardNumber.setText(etCreditCardNumber.getText().toString().substring(0, etCreditCardNumber.getText().toString().length() - 1));
                    int pos = etCreditCardNumber.getText().length();
                    etCreditCardNumber.setSelection(pos);
                }
                count = etCreditCardNumber.getText().toString().length();
            }
        });


        //Two Arrays to populate expiry date selection
        List<Integer> months = new ArrayList<>();
        List<Integer> years = new ArrayList<>();


        //For loops to populate spinners, year takes today's year and adds in 4 more.
        for (int i = 1; i <= 12; i++)
            months.add(i);
        int currentYear = Calendar.getInstance(TimeZone.getTimeZone("NZ")).get(Calendar.YEAR);
        for (int i = 0; i < 5; i++)
            years.add(currentYear + i);


        //Expiry Month Spinner
        materialBetterSpinner = (MaterialBetterSpinner) findViewById(R.id.spExpiryMonth);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<>(CreditCard.this, android.R.layout.simple_dropdown_item_1line, months.toArray(new Integer[0]));
        materialBetterSpinner.setAdapter(adapterMonth);



        //Expiry Year Spinner
        materialBetterSpinner = (MaterialBetterSpinner) findViewById(R.id.spExpiryYear);
        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<>(CreditCard.this, android.R.layout.simple_dropdown_item_1line, years.toArray(new Integer[0]));
        materialBetterSpinner.setAdapter(adapterYear);


    }

    //https://howtodoinjava.com/regex/java-regex-validate-credit-card-numbers/
    public void onClickValidateCC(View view) {
        String cardNumber = etCreditCardNumber.getText().toString();
        //This got a lot smaller, didn't need American cards.
        //Takes mastercard/visa at this point, can be changed to include more.
        //4 is Visa, 5 is Mastercard.
        boolean matched = cardNumber.matches("^((4[0-9]{3})|(5[1-5][0-9]{2}))-[0-9]{4}-[0-9]{4}-[0-9]{4}$");
        Snackbar.make(view, matched ? "Success" : "Credit Card Number Invalid!", Snackbar.LENGTH_SHORT).show();


        if (matched) {

            expMonth =((MaterialBetterSpinner)findViewById(R.id.spExpiryMonth)).getText().toString();
            expYear =((MaterialBetterSpinner)findViewById(R.id.spExpiryYear)).getText().toString();

            UserManager.user.setOrderCreditCard(etCreditCardNumber.getText().toString());
            UserManager.user.setOrderCreditExp(expMonth + " " + expYear);
            UserManager.user.setOrderCreditCVV(etCreditCardCVV.getText().toString());

            Intent intent = new Intent(this, Confirmation.class);
            startActivity(intent);
        }

    }


}
