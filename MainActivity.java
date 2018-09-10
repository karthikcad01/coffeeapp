package com.example.karthik.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        if (quantity <= 0) {
            quantity = 0;
        }
        CheckBox wc=(CheckBox)findViewById(R.id.wc);
        boolean iswc=wc.isChecked();

CheckBox choco =(CheckBox)findViewById(R.id.chocolate_checkbox);
boolean choc=choco.isChecked();

EditText name=(EditText)findViewById(R.id.name);
String names=name.getText().toString();
        String j=orderSummary(quantity,calculatePrice(iswc,choc),iswc,choc,names);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // for email only
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee ordering for " +names);
        intent.putExtra(Intent.EXTRA_TEXT,j);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(j);
    }

    private String calculatePrice(boolean wc,boolean choc) {
        String priceMessage;
       int w=quantity*10;
       if (wc) {
           w = w + 1;
       }
       if(choc) {
           w += 2;
       }
        priceMessage="Total : $"+ w;
        priceMessage=priceMessage + "\n Thank you !";
        return priceMessage;
    }
    public String orderSummary(int quantity,String p,boolean wc,boolean choco,String names){
        String a="Name: "+ names;
        String b=a+"\nQuantity:"+quantity+ "\nAdd whipped cream?"+ wc+"\nAdd chocolate ?"+ choco+"\n"+ p;
        return b;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
        public void display ( int number){
            TextView quantityTextView = (TextView) findViewById(R.id.value);
            quantityTextView.setText("" + number);
        }
    /*private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.value);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
        /**
         * This method displays the given text on the screen.
         */
   private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price);
        priceTextView.setText(message);
    }
    public void increment(View view){
        quantity++;
        display(quantity);

    }
    public void decrement(View view){
        quantity--;
        if(quantity<=0)
            quantity=0;
        display(quantity);
    }
  }
