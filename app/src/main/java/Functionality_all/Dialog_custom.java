package Functionality_all;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Colour.Memory.test.Divy.R;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter_all.Adapter_all_view;
import Call_back.Get_data;

@SuppressWarnings("unchecked")
public class Dialog_custom implements View.OnClickListener {
    private Get_data get_data;
    private Dialog infodialog;
    private EditText name;
    private Button cancel,submit,close;
    private TextView no_score;
    private RecyclerView score_list;
    private Context context;
    private int layout,type;
    private ArrayList<HashMap<String,Object>> data_high_score;

    public void dialog_custom(Object...args){
        this.context = (Context) args[0];
        this.type = (int) args[1];
        this.layout = (int) args[2];
        this.get_data = (Get_data) args[3];

        if(type == 1){
            this.data_high_score = (ArrayList<HashMap<String, Object>>) args[4];
        }

        infodialog = new Dialog(context);
        infodialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        infodialog.setContentView(layout);
        infodialog.setCanceledOnTouchOutside(false);
        infodialog.setCancelable(false);
        infodialog.show();

        call_layout(type);

    }

    private void call_layout(int type) {
        switch (type){
            case 0:
                name = (EditText) infodialog.findViewById(R.id.name);
                cancel = (Button) infodialog.findViewById(R.id.cancel);
                submit = (Button) infodialog.findViewById(R.id.submit);

                submit.setOnClickListener(this);
                cancel.setOnClickListener(this);
                break;

            case 1:
                close = (Button) infodialog.findViewById(R.id.close);
                no_score = (TextView) infodialog.findViewById(R.id.no_score);
                score_list = (RecyclerView) infodialog.findViewById(R.id.score_list);

                if(data_high_score.size()>0){
                    score_list.setVisibility(View.VISIBLE);
                    no_score.setVisibility(View.GONE);

                    LinearLayoutManager linear_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
                    Adapter_all_view adapterAllView = new Adapter_all_view(context,data_high_score,R.layout.high_score_adapter,context.getString(R.string.card_high_score));
                    score_list.setLayoutManager(linear_layout_manager);
                    score_list.setAdapter(adapterAllView);

                }else {
                    score_list.setVisibility(View.GONE);
                    no_score.setVisibility(View.VISIBLE);
                }

                break;
        }
    }


    public boolean EmptyCheck_edittext(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                infodialog.dismiss();
                break;

            case R.id.submit:
                if(EmptyCheck_edittext(name)){
                    name.setError(context.getString(R.string.empty_name));
                }else {
                    get_data.get_pass(0,name);
                    infodialog.dismiss();
                }
                break;

        }
    }
}
