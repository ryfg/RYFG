package com.finalproject.reachyourfitnessgoals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.finalproject.reachyourfitnessgoals.R;
import com.finalproject.reachyourfitnessgoals.database.handleTABLE_PROGRAM;
import com.finalproject.reachyourfitnessgoals.models.GoalData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class GoalActivity extends Activity {

    RadioGroup groupRadio;
    TextView confirm;
    GoalData goalData;
    EditText weightGoal;
    private handleTABLE_PROGRAM tableProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        tableProgram = new handleTABLE_PROGRAM(this);
        goalData = new GoalData();
        confirm = (TextView) findViewById(R.id.confirm_TextView_goal);
        groupRadio = (RadioGroup)findViewById(R.id.group_RadioButton_goal);
        weightGoal = (EditText)findViewById(R.id.weightGoal_EditText_goal);



        goalData.setKgPerWeek(1540);

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(i);
                selectKgPerWeek(radioButton);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalData.setWeightGoal(Float.parseFloat(weightGoal.getText().toString()));
                calDurationOfProgramExe(goalData);
                calDateOfProgram();
                tableProgram.addProgram(goalData);
            }
        });
    }


    public void selectKgPerWeek(View v){
        switch (v.getId()){
            case R.id.two_RadioButton_goal:
                goalData.setKgPerWeek(1540);
                Log.i("test","2");
                break;
            case R.id.five_RadioButton_goal:
                goalData.setKgPerWeek(3850);
                Log.i("test","5");
                break;
            case R.id.seven_RadioButton_goal:
                goalData.setKgPerWeek(6160);
                Log.i("test","7");
                break;
        }
    }


    public void calDurationOfProgramExe(GoalData data){
        float temp = data.getWeightGoal()*7700;
        temp = temp / data.getKgPerWeek();
        if(temp % 1 == 0){
            goalData.setTotalDuration((int)temp);
            Log.i("temp",goalData.getTotalDuration()+"");
        }else{
            goalData.setTotalDuration((int)temp+1);
            Log.i("temp2",goalData.getTotalDuration()+"");
        }

    }

    public void calDateOfProgram(){
        Calendar thaiTime = new GregorianCalendar(TimeZone.getTimeZone("GMT+07:00"));
        int year_begin = thaiTime.get(Calendar.YEAR);
        int month_begin = thaiTime.get(Calendar.MONTH)+1;
        int day_begin = thaiTime.get(Calendar.DAY_OF_MONTH);

        goalData.setYear_date_begin(year_begin);
        goalData.setMonth_date_begin(month_begin);
        goalData.setDay_date_begin(day_begin);

        Log.i("dateBegin",goalData.getDay_date_begin()+"");
        Log.i("dateBegin",goalData.getMonth_date_begin()+"");
        Log.i("dateBegin",goalData.getYear_date_begin()+"");

        int tempDateEnd = goalData.getTotalDuration() * 7;
        thaiTime.add(Calendar.DATE,tempDateEnd);
        int year_end = thaiTime.get(Calendar.YEAR);
        int month_end = thaiTime.get(Calendar.MONTH)+1;
        int day_end = thaiTime.get(Calendar.DAY_OF_MONTH);

        goalData.setYear_date_end(year_end);
        goalData.setMonth_date_end(month_end);
        goalData.setDay_date_end(day_end);

        Log.i("dateEnd",goalData.getDay_date_end()+"");
        Log.i("dateEnd",goalData.getMonth_date_end()+"");
        Log.i("dateEnd",goalData.getYear_date_end()+"");
    }
}
