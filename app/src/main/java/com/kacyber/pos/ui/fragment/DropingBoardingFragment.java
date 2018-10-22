package com.kacyber.pos.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.kacyber.pos.R;
import com.kacyber.pos.models.BookingModuleData;
import com.kacyber.pos.models.BusSeats;
import com.kacyber.pos.ui.BusLayoutActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class DropingBoardingFragment extends DialogFragment implements View.OnClickListener {


    BusLayoutActivity mLayoutActivity;
    ListView boardingPointList, droppingPointsList;
    ArrayList<BusSeats.BoardingPoint> boardingList = new ArrayList<>();
    ArrayList<BusSeats.DropingPoint> droppingList = new ArrayList<>();
    ImageView doneIV, backIV;

    BookingModuleData mModuleData;

    BusSeats.BoardingPoint mBoardingPoints;
    BusSeats.DropingPoint mDropingPoints;

    @SuppressLint("ValidFragment")
    public DropingBoardingFragment(BusLayoutActivity baseActivity,
                                   ArrayList<BusSeats.BoardingPoint> boardingList,
                                   ArrayList<BusSeats.DropingPoint> droppingList) {

        mLayoutActivity = baseActivity;
      //  this.mModuleData = bookingModule;
        this.boardingList = boardingList;
        this.droppingList = droppingList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.app.DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        //  dialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_droping_boarding, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mModuleData=BookingModuleData.getInstance();
        boardingPointList = (ListView) view.findViewById(R.id.boardingPointList);
        droppingPointsList = (ListView) view.findViewById(R.id.droppingPointsList);
        doneIV = (ImageView) view.findViewById(R.id.doneIV);
        backIV = (ImageView) view.findViewById(R.id.backIV);
        backIV.setOnClickListener(this);
        doneIV.setOnClickListener(this);
        setBoardingAdpter();
        setDroppingPointsList();


    }

    private void setBoardingAdpter() {
        String[] arr = new String[boardingList.size()];
        if (boardingList.size() > 0) {
            int selectedPostion = 0;
            for (int i = 0; i < boardingList.size(); i++) {
                arr[i] = boardingList.get(i).location;
                if (mModuleData.selectboardingPointsId == boardingList.get(i).id) {
                    selectedPostion = i;

                }
            }
            boardingPointList.setItemChecked(0, true);
            boardingPointList.setAdapter(new ArrayAdapter<String>(mLayoutActivity,
                    android.R.layout.simple_list_item_multiple_choice, arr));
            boardingPointList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            boardingPointList.setItemChecked(selectedPostion, true);
            mBoardingPoints = boardingList.get(boardingPointList.getCheckedItemPosition());
            // selectedBoarding = boardingList.get(boardingPointList.getCheckedItemPosition()).id;
            //TODO ITEM CLICK
            boardingPointList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //  selectedBoarding = boardingList.get(position).id;
                    mBoardingPoints = boardingList.get(position);
                }
            });
        }
    }

    private void setDroppingPointsList() {
        if (droppingList.size() > 0) {
            String[] arr = new String[droppingList.size()];
            int selectedPostion = 0;
            for (int i = 0; i < droppingList.size(); i++) {
                arr[i] = droppingList.get(i).location;
                if (mModuleData.selectDropingPointsId == droppingList.get(i).id) {
                    selectedPostion = i;
                }
            }
            droppingPointsList.setAdapter(new ArrayAdapter<String>(mLayoutActivity,
                    android.R.layout.simple_list_item_multiple_choice, arr));
            droppingPointsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            droppingPointsList.setItemChecked(selectedPostion, true);
            //   selectedDroping = droppingList.get(droppingPointsList.getCheckedItemPosition()).id;
            mDropingPoints = droppingList.get(droppingPointsList.getCheckedItemPosition());

            droppingPointsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //    selectedDroping = droppingList.get(position).id;
                    mDropingPoints = droppingList.get(position);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.backIV) {
            dismiss();
        } else if (i == R.id.doneIV) {
            if (mBoardingPoints == null || mDropingPoints == null) {
                Toast.makeText(mLayoutActivity, "Please select boarding and dropping points.", Toast.LENGTH_SHORT).show();
            } else {
                // dismiss();
             //   mLayoutActivity.setSelectedPoints(mBoardingPoints, mDropingPoints);
                // hi api
            }
        }
    }
}