package com.finalproject.reachyourfitnessgoals.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.finalproject.reachyourfitnessgoals.R;
import com.finalproject.reachyourfitnessgoals.activity.ExerciseActivity;
import com.finalproject.reachyourfitnessgoals.database.handleTABLE_EXERCISE;
import com.finalproject.reachyourfitnessgoals.database.handleTABLE_PROGRAM;
import com.finalproject.reachyourfitnessgoals.models.DateData;
import com.finalproject.reachyourfitnessgoals.models.ExerciseData;
import com.finalproject.reachyourfitnessgoals.models.GoalData;
import com.finalproject.reachyourfitnessgoals.setting.EventDecorator;


import com.finalproject.reachyourfitnessgoals.models.GlobalData;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_calendar extends Fragment {

    private static MaterialCalendarView MCV;
    private handleTABLE_PROGRAM handleTableProgram;
    private handleTABLE_EXERCISE handleTableExercise;
    private boolean checkUserUsed = true;
    private GoalData goalData;
    private ScrollView scrollView;
    private RelativeLayout layout;
    private Button setExeButton;
    private Button exeButton;
    NestedScrollView nestedScrollView;



    public fragment_calendar() {
        // Required empty public constructor
    }

    public static fragment_calendar newInstance() {
        fragment_calendar fragment = new fragment_calendar();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_calendar, container, false);

        nestedScrollView = (NestedScrollView)rootview.findViewById(R.id.scrollView_calendar);
        MaterialViewPagerHelper.registerScrollView(getActivity(), nestedScrollView);
        handleTableProgram = new handleTABLE_PROGRAM(getActivity());
        handleTableExercise = new handleTABLE_EXERCISE(getActivity());
        //goalData = handleTableProgram.getDateToCalendar();
        MCV = (MaterialCalendarView) rootview.findViewById(R.id.datePicker_DatePicker_calendar);
        layout = (RelativeLayout)  rootview.findViewById(R.id.layoutCalendar_RelativeLayout_calendar);
        setExeButton = (Button) rootview.findViewById(R.id.setExeOfDay_button_calendar);
        exeButton = (Button) rootview.findViewById(R.id.Exe_button_calendar);
        exeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExerciseActivity.class);
                startActivity(intent);
            }
        });
//        MCV.state().edit()
//                .setFirstDayOfWeek(Calendar.MONDAY)
//                .setMinimumDate(CalendarDay.from(goalData.getYear_date_begin(), goalData.getMonth_date_begin(), goalData.getDay_date_begin()))
//                .setMaximumDate(CalendarDay.from(goalData.getYear_date_end()+100, 12, 31))
//                .setCalendarDisplayMode(CalendarMode.MONTHS)
//                .commit();


        // Month begin 0 = january
        MCV.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2017, 3, 1))
                .setMaximumDate(CalendarDay.from(2017, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        MCV.setDateSelected(CalendarDay.today(),true);
        MCV.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                checkExeInDay(date);
            }
        });

        setExeButton.setOnClickListener(setExe);
        addEvent(handleTableExercise.getDateExerciseToCalendar(),getResources().getColor(R.color.colorPrimary));

        return rootview;
    }


    public void addEvent(ArrayList<DateData> dateDatas,int color){
            HashSet<CalendarDay> event = new HashSet<CalendarDay>();
            for(DateData date : dateDatas){
                event.add(CalendarDay.from(date.getYear(),date.getMonth(),date.getDay()));
            }

            MCV.addDecorator(new EventDecorator(color, event));
    }

    private void checkExeInDay(CalendarDay date){
        ExerciseData exerciseData = handleTableExercise.getDetailExercise(date);
        Log.i("exerciseData",exerciseData+"");
        if(exerciseData != null){
            if(exerciseData.getVdo_id().equals("ยังไม่ได้เลือกท่าออกกำลังกาย")){
                setExeButton.setVisibility(View.VISIBLE);
                setExeButton.setOnClickListener(setExe);
            }else {
                setExeButton.setVisibility(View.INVISIBLE);
            }

        }else{

        }
        addGlobalDate(date);

    }

    private void addGlobalDate(CalendarDay date){

        ((GlobalData) getActivity().getApplication()).setDateData(new DateData(date.getDay(),date.getMonth(),date.getYear()));

    }

    private View.OnClickListener setExe = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            fragment_dialog_inFragment_calendar dialogFragment = new fragment_dialog_inFragment_calendar ();
            dialogFragment.show(fm, "Sample Fragment");
        }
    };

    private View.OnClickListener syn = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };





}
