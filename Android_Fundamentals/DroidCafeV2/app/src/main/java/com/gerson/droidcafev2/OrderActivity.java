package com.gerson.droidcafev2;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;


public class OrderActivity extends AppCompatActivity {

    //region STATICS
    public static void displayToast(View view, String message) {
        Toast
                .makeText(
                        view.getContext(),
                        message,
                        Toast.LENGTH_SHORT
                )
                .show();

        Snackbar
                .make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initRadioGroup();
        initSpinner();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            }
        );
    }

    public void initRadioGroup(){
        ((RadioButton)findViewById(R.id.radio_bt_sameday)).toggle();
    }

    public void initSpinner(){

        // Find the spinner.
        Spinner spinner = findViewById(R.id.phone_type_spinner);

        if (spinner != null) {
            //populate spinner from Resource String Array
            spinner.setAdapter(
                ArrayAdapter.createFromResource(
                    this,
                    R.array.phone_types,
                    android.R.layout.simple_spinner_item
                )
            );

            ((ArrayAdapter<CharSequence>)spinner.getAdapter())
                    .setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                    );

            spinner.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            displayToast(view,parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    }
            );
        }

    }

    public void leave(final View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(OrderActivity.this);
        alertBuilder.setTitle("Leave?");
        alertBuilder.setMessage("Click OK to save the order, Cancel to go back");
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                displayToast(view, "Order properly saved. Going back");
                finish();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                displayToast(view, "Canceling. Going back");
                finish();
            }
        });
        alertBuilder.show();
    }

    public void onRadioButtonClicked(@NotNull final View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked.
        switch (view.getId()) {
            case R.id.radio_bt_sameday:
                if (checked)
                    // Same day service
                    displayToast(view,"Same day");
                break;
            case R.id.radio_bt_pickup:
                if (checked)
                    // Pick up
                    displayToast(view,"Pickup");
                break;
            case R.id.radio_bt_onDate: {
                if (checked) {
                    displayToast(view,"Reservation for future date");

                    DateOrTimePickerFragment datePicker = new DateOrTimePickerFragment();
                    datePicker.show(getSupportFragmentManager(),"DATE_PICKER");
                    datePicker.addOnCalendarChangedListener(
                        new DateOrTimePickerFragment.OnCalendarChangedListener() {
                            @Override
                            public void onCalendarChanged(final Calendar calendar) {
                                //region region Log and Alert Date
                                Log.d("CALENDAR(DATE):",calendar.getTime().toString());
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                                alertBuilder.setTitle("Delivery Date");
                                alertBuilder.setMessage(
                                        "Order will be delivered on:\r\n"
                                                +
                                                //https://docs.oracle.com/javase/1.5.0/docs/api/java/util/Calendar.html#MONTH
                                                (calendar.get(Calendar.MONTH) + 1) + "/"
                                                +
                                                calendar.get(Calendar.DATE) + "/"
                                                +
                                                calendar.get(Calendar.YEAR)
                                );
                                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DateOrTimePickerFragment timePicker = new DateOrTimePickerFragment();
                                        timePicker.setPickerRole(DateOrTimePickerFragment.PICKER_ROLE.TIME_PICKER_ROLE);
                                        timePicker.setCalendar(calendar);
                                        timePicker.show(getSupportFragmentManager(),"TIME_PICKER");
                                        timePicker.addOnCalendarChangedListener(
                                                new DateOrTimePickerFragment.OnCalendarChangedListener() {
                                                    @Override
                                                    public void onCalendarChanged(Calendar calendar) {
                                                        //region Log and Alert Time
                                                        Log.d("CALENDAR(TIME):", calendar.getTime().toString());
                                                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                                                        alertBuilder.setTitle("Delivery Time");
                                                        String AM_PM = calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
                                                        alertBuilder.setMessage(
                                                                "Order will be delivered at:\r\n"
                                                                +
                                                                calendar.get(Calendar.HOUR) + ":"
                                                                +
                                                                calendar.get(Calendar.MINUTE) + " " + AM_PM
                                                        );
                                                        alertBuilder.setPositiveButton("OK", null);
                                                        alertBuilder.show();
                                                        //endregion
                                                    }
                                                }
                                        );
                                    }
                                });
                                alertBuilder.show();
                                //endregion
                            }
                        }
                    );

                }
                break;
            }
            default:
                // Do nothing.
                break;
        }
    }
}

