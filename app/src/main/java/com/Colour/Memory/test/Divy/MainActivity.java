package com.Colour.Memory.test.Divy;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import Adapter_all.Adapter_all_view;
import Call_back.Get_data;
import Call_back.Get_value;
import Controller_all.Controller;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Controller controller;
    private TextView score;
    private Button high_score;
    private RecyclerView cardList;
    private Adapter_all_view adapterAllView;
    private ArrayList<HashMap<String,Object>> data,data_high_score;
    private HashMap<String,Object> data_sub;
    private Integer [] array_image = {R.drawable.colour1,R.drawable.colour2,R.drawable.colour3,R.drawable.colour4,
            R.drawable.colour5,R.drawable.colour6,R.drawable.colour7,R.drawable.colour8,R.drawable.colour1,
            R.drawable.colour2,R.drawable.colour3,R.drawable.colour4,R.drawable.colour5,R.drawable.colour6,
            R.drawable.colour7,R.drawable.colour8};
    private Get_value get_value;
    private Get_data get_data;
    private int type;
    private String Colour_Memory = "Colour_Memory" ,score_value,query = "Select * from Colour_Memory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        controller = new Controller();
        controller.init_db(context);

        set_action_bar();

        initialise();
    }

    private void initialise() {
       get_value = new Get_value() {
           @Override
           public void value_pass(String value) {
               score_value = value;
               score.setText(getString(R.string.score)+" "+ score_value);
           }
       } ;

        get_data = new Get_data() {
            @Override
            public void get_pass(Object... args) {
                type = (int) args[0];

                switch (type){
                    case 0:
                        String name = (String) args[1];
                        controller.Db_data_add(name,score_value,Colour_Memory);
                        break;

                }
            }
        };


        cardList = (RecyclerView) findViewById(R.id.cardList);
        data = new ArrayList<>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                Random r = new Random(array_image.length);
                Set<Integer> mySet = new HashSet<Integer>();
                while (mySet.size() < 16) {
                    int idx = r.nextInt(array_image.length);
                    mySet.add(idx);
                }

                Iterator itr = mySet.iterator();
                call_method(itr.next(),itr.next(),itr.next(),itr.next(),0);
                call_method(itr.next(),itr.next(),itr.next(),itr.next(),4);
                call_method(itr.next(),itr.next(),itr.next(),itr.next(),8);
                call_method(itr.next(),itr.next(),itr.next(),itr.next(),12);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        adapterAllView = new Adapter_all_view(context,data,R.layout.cardlist_adapter,getString(R.string.cardList),cardList,get_value);
        cardList.setLayoutManager(gridLayoutManager);
        cardList.setAdapter(adapterAllView);
    }

    private void call_method(Object img1, Object img2, Object img3, Object img4, int val) {
        data_sub = new HashMap<>();
        data_sub.put("card_item_img1",array_image[(int) img1]);
        data_sub.put("card_item_id1",val);

        data_sub.put("card_item_img2",array_image[(int)img2]);
        data_sub.put("card_item_id2",val+1);

        data_sub.put("card_item_img3",array_image[(int)img3]);
        data_sub.put("card_item_id3",val+2);

        data_sub.put("card_item_img4",array_image[(int)img4]);
        data_sub.put("card_item_id4",val+3);

        data.add(data_sub);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.high_score:
                if (controller.is_data_available(query)){
                    data_high_score = new ArrayList<>();
                }else {
                    data_high_score = controller.read_data(query);
                    controller.dialog_custom(context,R.layout.dialog_high_score,1,get_data);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        controller.dialog_custom(context,R.layout.dialog_submit,0,get_data);
    }

    private void set_action_bar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        score = (TextView) toolbar.findViewById(R.id.score);
        high_score = (Button) toolbar.findViewById(R.id.high_score);
        score.setOnClickListener(this);
        high_score.setOnClickListener(this);

        score.setText("");
    }

}