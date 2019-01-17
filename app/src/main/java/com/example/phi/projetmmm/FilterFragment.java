package com.example.phi.projetmmm;

import android.app.Dialog;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.view.View;

import java.util.Calendar;




import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterFragment extends DialogFragment {

    @BindView(R.id.spinner_filter)
    Spinner spinner;

    @BindView(R.id.calendarView_filter)
    CalendarView calendarView;

    @BindView(R.id.button_filter)
    Button buttonFilter;

    private long dateLong = 0;
    private String date = "";

    public FilterFragment() {
    }

    public static FilterFragment newInstance(){
        FilterFragment filterFragment = new FilterFragment();
        return filterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter_dialog, container);
        ButterKnife.bind(this,view);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getActivity(),R.array.regions_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateLong = calendarView.getDate();
                date = year + "-" + (month+1) + "-" + dayOfMonth;
            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( getActivity() instanceof EvenementsActivity){
                    EvenementsActivity evenementsActivity = (EvenementsActivity) getActivity();

                    String region = spinner.getSelectedItem() != null ?
                            spinner.getSelectedItem().toString() : "";

                    getDialog().dismiss();
                    evenementsActivity.filterData(region,date);

                }
            }
        });

        Calendar maxDate = Calendar.getInstance();
        String maxdate = "14/10/2018";
        String parts[] = maxdate.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);


        maxDate.set(Calendar.YEAR,year);
        maxDate.set(Calendar.MONTH,month);
        maxDate.set(Calendar.DAY_OF_MONTH,day);
        calendarView.setMaxDate(maxDate.getTimeInMillis());

        Calendar minDate = Calendar.getInstance();
        String mindate = "6/10/2018";
        parts = mindate.split("/");

        day = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]);
        year = Integer.parseInt(parts[2]);

        minDate.set(Calendar.YEAR,year);
        minDate.set(Calendar.MONTH,month);
        minDate.set(Calendar.DAY_OF_MONTH,day);
        calendarView.setMinDate(minDate.getTimeInMillis());
        calendarView.setDate(0,false,false);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.setCanceledOnTouchOutside(true);
        }
    }

}
