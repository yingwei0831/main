package com.jhhy.cuiweitourism.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelTripDaysListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InnerTravelFragmentTripDays#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InnerTravelFragmentTripDays extends Fragment implements AdapterView.OnItemClickListener {

    private int type;
    private String[] array = new String[]{"不限", "1天", "2天", "3天", "4天", "5天", "6天", "7天", "8天", "9天", "10天", "10天以上"};
    private List<String> list = new ArrayList<>();
    private InnerTravelTripDaysListViewAdapter adapter;

    public InnerTravelFragmentTripDays() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static InnerTravelFragmentTripDays newInstance(int type) {
        InnerTravelFragmentTripDays fragment = new InnerTravelFragmentTripDays();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        if(type == 1) {
//            view = inflater.inflate(R.layout.fragment_fragment_trip_days, container, false);
//            ListView listViewTripDays = (ListView) view.findViewById(R.id.layout_trip_days);
//            list.add("不限");
//            list.add("1天");
//            list.add("2天");
//            list.add("3天");
//            list.add("4天");
//            list.add("5天");
//            list.add("6天");
//            list.add("7天");
//            list.add("8天");
//            list.add("9天");
//            list.add("10天");
//            list.add("10天以上");
//            adapter = new InnerTravelTripDaysListViewAdapter(getContext(), list);
//            listViewTripDays.setAdapter(adapter);
//            listViewTripDays.setOnItemClickListener(this);
        } else if (type == 2){

        } else if (3 == type){

        }
        setupView(view);
        addListener();
        return view;
    }

    private void setupView(View view) {

    }

    private void addListener() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (type){
            case 1:
                String tripDays = list.get((int)l);
                Toast.makeText(getContext(), tripDays, Toast.LENGTH_SHORT).show();
                break;
            case 2:

                break;
            case 3:

                break;
        }
    }
}
