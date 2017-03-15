package com.finalproject.reachyourfitnessgoals.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.finalproject.reachyourfitnessgoals.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_setExercise_inWeek extends Fragment implements VerticalStepperForm  {

    private VerticalStepperFormLayout verticalStepperForm;
    private LinearLayout daysStepContent;


    public fragment_setExercise_inWeek() {
        // Required empty public constructor
    }

    public static fragment_setExercise_inWeek newInstance() {
        fragment_setExercise_inWeek fragment = new fragment_setExercise_inWeek();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_set_exercise_in_week, container, false);

        String[] stepsTitles = getResources().getStringArray(R.array.steps_titles);
        int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) rootview.findViewById(R.id.stepper_stepView_setExe);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm,stepsTitles,this,getActivity())
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();

        return rootview;
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = createDayOfWeekStep();
                break;
            case 1:
                view = createSeleteDayStep();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {

    }

    @Override
    public void sendData() {

    }

    private View createDayOfWeekStep() {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        daysStepContent = (LinearLayout) inflater.inflate(R.layout.workout_per_week_layout, null, false);

        for(int i=3 ; i<=6 ; i++){
            LinearLayout dayLayout = getDayLayout(i);
            dayLayout.setOnClickListener(daySet);
        }


        return daysStepContent;
    }

    private View createSeleteDayStep() {
        View view = null;
        Spinner dropdown = new Spinner(getContext());
        String[] items = getResources().getStringArray(R.array.workoutPerweek);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        return dropdown;
    }

    private LinearLayout getDayLayout(int i) {
        int id = daysStepContent.getResources().getIdentifier(
                "day_" + i, "id",  getContext().getPackageName());
        return (LinearLayout) daysStepContent.findViewById(id);
    }

    private View.OnClickListener daySet = new View.OnClickListener(){
        int tempId;
        @Override
        public void onClick(View v) {
            for(int i=3 ; i<=6 ; i++){
                LinearLayout dayLayout = getDayLayout(i);
                dayLayout.setBackground(getResources().getDrawable(R.drawable.border_bottom));
                TextView oldDayText = (TextView) dayLayout.findViewById(R.id.day);
                oldDayText.setTextColor(getResources().getColor(R.color.colorBlack));
            }
            v.setBackgroundColor(getResources().getColor(R.color.colorBlack));
            TextView dayText = (TextView) v.findViewById(R.id.day);
            dayText.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    };
}