package com.gerson.droidcafe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.*;

public class DateOrTimePickerFragment
    extends DialogFragment
    implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    private Calendar calendar  = Calendar.getInstance();

    public Calendar getCalendar(){ return this.calendar; }

    public void setCalendar(@NonNull Calendar calendar){
        this.calendar = calendar;
        for( OnCalendarChangedListener onCalendarChangedListener : onCalendarChangedListenerList) {
            onCalendarChangedListener.onCalendarChanged(this.calendar);
        }
    }

    enum PICKER_ROLE { DATE_PICKER_ROLE, TIME_PICKER_ROLE }

    private PICKER_ROLE picker_role = PICKER_ROLE.DATE_PICKER_ROLE;

    public PICKER_ROLE getPickerRole() { return picker_role; }

    public void setPickerRole(PICKER_ROLE picker_role) { this.picker_role = picker_role; }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return
                this.picker_role == PICKER_ROLE.DATE_PICKER_ROLE
                        ?
                        new DatePickerDialog(
                                getActivity(),
                                this,
                                this.calendar.get(Calendar.YEAR),
                                this.calendar.get(Calendar.MONTH),
                                this.calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        :
                        new TimePickerDialog(
                                getActivity(),
                                this,
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                false
                        )
                ;
    }

    //define callback interface
    public interface OnCalendarChangedListener {
        void onCalendarChanged(Calendar calendar);
    }

    private List<OnCalendarChangedListener> onCalendarChangedListenerList = new LinkedList<OnCalendarChangedListener>();

    public void addOnCalendarChangedListener(@NonNull OnCalendarChangedListener onCalendarChangedListener) {
        if(!this.onCalendarChangedListenerList.contains(onCalendarChangedListener))
            this.onCalendarChangedListenerList.add(onCalendarChangedListener);
    }

    //TODO Implement Has and Remove

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.calendar.set(year,month,dayOfMonth);
        for( OnCalendarChangedListener onCalendarChangedListener : onCalendarChangedListenerList) {
            onCalendarChangedListener.onCalendarChanged(this.calendar);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                hourOfDay,
                minute
        );
        for( OnCalendarChangedListener onCalendarChangedListener : onCalendarChangedListenerList) {
            onCalendarChangedListener.onCalendarChanged(this.calendar);
        }
    }
}
