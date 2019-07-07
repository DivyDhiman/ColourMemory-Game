package Adapter_all;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Call_back.Get_value;
import Functionality_all.FlipAnimation;
import Functionality_all.MyBounceInterpolator;
import com.Colour.Memory.test.Divy.R;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Adapter_all_view extends RecyclerView.Adapter<Adapter_all_view.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, Object>> data, flip_data = new ArrayList<>();
    private HashMap<String, Object> data_sub, flip_data_sub;
    private ArrayList<Integer> data_added = new ArrayList<>();
    private String type;
    private int layout_pass, count = 0;
    private Get_value get_value;
    private FlipAnimation flipAnimation;
    private RecyclerView cardList;
    private View view;
    private boolean clickable = true;
    private Animation myAnim;
    private MyBounceInterpolator interpolator;

    public Adapter_all_view(Object...args) {
        this.context = (Context) args[0];
        this.data = (ArrayList<HashMap<String, Object>>) args[1];
        this.layout_pass = (int) args[2];
        this.type = (String) args[3];

        if(type.equals(context.getString(R.string.cardList))){
            this.cardList = (RecyclerView) args[4];
            this.get_value = (Get_value) args[5];
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(layout_pass, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        data_sub = data.get(position);

        if (type.equals(context.getString(R.string.cardList))) {
            call_method(1, holder, position);
        }else if (type.equals(context.getString(R.string.card_high_score))){
            call_method(2, holder, position);
        }
    }

    private void call_method(int i, final ViewHolder holder, final int position) {
        switch (i) {
            case 1:

                holder.item_img_back1.setImageResource((Integer) data_sub.get("card_item_img1"));
                holder.item_img_back2.setImageResource((Integer) data_sub.get("card_item_img2"));
                holder.item_img_back3.setImageResource((Integer) data_sub.get("card_item_img3"));
                holder.item_img_back4.setImageResource((Integer) data_sub.get("card_item_img4"));

                holder.item_img_front1.setImageResource(R.drawable.card_bg);
                holder.item_img_front2.setImageResource(R.drawable.card_bg);
                holder.item_img_front3.setImageResource(R.drawable.card_bg);
                holder.item_img_front4.setImageResource(R.drawable.card_bg);

                holder.card1_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data_sub = data.get(position);
                        if (!data_added.contains(Integer.parseInt(data_sub.get("card_item_id1").toString()))) {
                            if (clickable) {
                                click_method(0, holder, position, data_sub.get("card_item_img1").toString(),
                                        Integer.parseInt(data_sub.get("card_item_id1").toString()));
                            }
                        }
                    }
                });

                holder.card2_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data_sub = data.get(position);
                        if (!data_added.contains(Integer.parseInt(data_sub.get("card_item_id2").toString()))) {
                            if (clickable) {
                                click_method(1, holder, position, data_sub.get("card_item_img2").toString(),
                                        Integer.parseInt(data_sub.get("card_item_id2").toString()));
                            }
                        }
                    }
                });
                holder.card3_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data_sub = data.get(position);
                        if (!data_added.contains(Integer.parseInt(data_sub.get("card_item_id3").toString()))) {
                            if (clickable) {
                                click_method(2, holder, position, data_sub.get("card_item_img3").toString(),
                                        Integer.parseInt(data_sub.get("card_item_id3").toString()));
                            }
                        }
                    }
                });
                holder.card4_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data_sub = data.get(position);
                        if (!data_added.contains(Integer.parseInt(data_sub.get("card_item_id4").toString()))) {
                            if (clickable) {
                                click_method(3, holder, position, data_sub.get("card_item_img4").toString(),
                                        Integer.parseInt(data_sub.get("card_item_id4").toString()));
                            }
                        }
                    }
                });


                break;

            case 2:

                holder.name.setText(data_sub.get("name").toString());
                holder.high_score.setText(data_sub.get("score").toString());

                break;
        }
    }

    private void click_method(int i, ViewHolder holder, int position, String image,int id) {
        View parent = null, child_front = null, child_back = null;

        switch (i) {
            case 0:
                parent = holder.card1_parent;
                child_front = holder.item_img_front1;
                child_back = holder.item_img_back1;
                break;
            case 1:
                parent = holder.card2_parent;
                child_front = holder.item_img_front2;
                child_back = holder.item_img_back2;
                break;
            case 2:
                parent = holder.card3_parent;
                child_front = holder.item_img_front3;
                child_back = holder.item_img_back3;
                break;
            case 3:
                parent = holder.card4_parent;
                child_front = holder.item_img_front4;
                child_back = holder.item_img_back4;
                break;
        }

        flip_data_sub = new HashMap<String, Object>();
        flipAnimation = new FlipAnimation(child_front, child_back);
        if (flip_data.size() > 0) {
            if (flip_data.get(0).get("pos").toString().equals(String.valueOf(position))) {
                if (child_front.getVisibility() == View.GONE) {
                    flipAnimation.reverse();
                    parent.startAnimation(flipAnimation);
                    flip_data = new ArrayList<HashMap<String, Object>>();
                } else {
                    if (flip_data.get(0).get("image").toString().equals(image)) {
                        parent.startAnimation(flipAnimation);
                        flip_data_sub.put("pos", position);
                        flip_data_sub.put("image", image);
                        flip_data_sub.put("type", i);
                        flip_data_sub.put("id", id);
                        flip_data.add(flip_data_sub);
                        check_method(0);
                    } else {
                        parent.startAnimation(flipAnimation);
                        flip_data_sub.put("pos", position);
                        flip_data_sub.put("image", image);
                        flip_data_sub.put("type", i);
                        flip_data_sub.put("id", id);
                        flip_data.add(flip_data_sub);
                        check_method(1);
                    }
                }
            } else {
                if (flip_data.get(0).get("image").toString().equals(image)) {
                    parent.startAnimation(flipAnimation);
                    flip_data_sub.put("pos", position);
                    flip_data_sub.put("image", image);
                    flip_data_sub.put("type", i);
                    flip_data_sub.put("id", id);
                    flip_data.add(flip_data_sub);
                    check_method(0);
                } else {
                    parent.startAnimation(flipAnimation);
                    flip_data_sub.put("pos", position);
                    flip_data_sub.put("image", image);
                    flip_data_sub.put("type", i);
                    flip_data_sub.put("id", id);
                    flip_data.add(flip_data_sub);
                    check_method(1);
                }
            }
        } else {
            data_sub = data.get(position);
            parent.startAnimation(flipAnimation);
            flip_data_sub.put("pos", position);
            flip_data_sub.put("image", image);
            flip_data_sub.put("type", i);
            flip_data_sub.put("id", id);
            flip_data.add(flip_data_sub);
        }

    }

    private void check_method(final int val) {
        clickable = false;

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            public void run() {
                ViewHolder holder_new;
                if (val == 0) {
                    flip_data_sub = flip_data.get(0);
                    view = cardList.getChildAt((Integer) flip_data_sub.get("pos"));
                    data_added.add((Integer) flip_data_sub.get("id"));
                    holder_new = new ViewHolder(view);
                    call_type((Integer) flip_data_sub.get("type") + 4, holder_new);

                    flip_data_sub = flip_data.get(1);
                    view = cardList.getChildAt((Integer) flip_data_sub.get("pos"));
                    data_added.add((Integer) flip_data_sub.get("id"));
                    holder_new = new ViewHolder(view);
                    call_type((Integer) flip_data_sub.get("type") + 4, holder_new);

                    count = count + 1;

                    if(data_added.size() == data.size()){
                        get_value.value_pass(String.valueOf(count));
                    }
                    flip_data = new ArrayList<HashMap<String, Object>>();
                } else {
                    flip_data_sub = flip_data.get(0);
                    view = cardList.getChildAt((Integer) flip_data_sub.get("pos"));

                    holder_new = new ViewHolder(view);

                    call_type((Integer) flip_data_sub.get("type"), holder_new);
                    flip_data_sub = flip_data.get(1);

                    view = cardList.getChildAt((Integer) flip_data_sub.get("pos"));
                    holder_new = new ViewHolder(view);
                    call_type((Integer) flip_data_sub.get("type"), holder_new);

                    count = count - 1;
                    flip_data = new ArrayList<HashMap<String, Object>>();
                }
            }
        }, 2000);
    }

    private void call_type(Integer type, ViewHolder holder_new) {
        switch (type) {
            case 0:
                flipAnimation = new FlipAnimation(holder_new.item_img_front1, holder_new.item_img_back1);
                flipAnimation.reverse();
                holder_new.card1_parent.startAnimation(flipAnimation);
                clickable = true;
                break;
            case 1:
                flipAnimation = new FlipAnimation(holder_new.item_img_front2, holder_new.item_img_back2);
                flipAnimation.reverse();
                holder_new.card2_parent.startAnimation(flipAnimation);
                clickable = true;
                break;
            case 2:
                flipAnimation = new FlipAnimation(holder_new.item_img_front3, holder_new.item_img_back3);
                flipAnimation.reverse();
                holder_new.card3_parent.startAnimation(flipAnimation);
                clickable = true;
                break;
            case 3:
                flipAnimation = new FlipAnimation(holder_new.item_img_front4, holder_new.item_img_back4);
                flipAnimation.reverse();
                holder_new.card4_parent.startAnimation(flipAnimation);
                clickable = true;
                break;

            case 4:
                myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                holder_new.item_img_front1.setVisibility(View.GONE);
                holder_new.item_img_back1.setVisibility(View.GONE);
                holder_new.match_card_1.setVisibility(View.VISIBLE);
                holder_new.card1_parent.startAnimation(myAnim);
                clickable = true;
                break;
            case 5:
                myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                holder_new.item_img_front2.setVisibility(View.GONE);
                holder_new.item_img_back2.setVisibility(View.GONE);
                holder_new.match_card_2.setVisibility(View.VISIBLE);
                holder_new.card2_parent.startAnimation(myAnim);
                clickable = true;
                break;

            case 6:
                myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                holder_new.item_img_front3.setVisibility(View.GONE);
                holder_new.item_img_back3.setVisibility(View.GONE);
                holder_new.match_card_3.setVisibility(View.VISIBLE);
                holder_new.card3_parent.startAnimation(myAnim);
                clickable = true;
                break;

            case 7:
                myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                holder_new.item_img_front4.setVisibility(View.GONE);
                holder_new.item_img_back4.setVisibility(View.GONE);
                holder_new.match_card_4.setVisibility(View.VISIBLE);
                holder_new.card4_parent.startAnimation(myAnim);
                clickable = true;
                break;
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout card1_parent, card2_parent, card3_parent, card4_parent, match_card_1, match_card_2, match_card_3, match_card_4;
        private ImageView item_img_front1, item_img_back1, item_img_front2, item_img_back2, item_img_front3, item_img_back3, item_img_front4, item_img_back4;
        private TextView name,high_score;

        public ViewHolder(View view) {
            super(view);
            if (type.equals(context.getString(R.string.cardList))) {
                card1_parent = (RelativeLayout) view.findViewById(R.id.card1_parent);
                card2_parent = (RelativeLayout) view.findViewById(R.id.card2_parent);
                card3_parent = (RelativeLayout) view.findViewById(R.id.card3_parent);
                card4_parent = (RelativeLayout) view.findViewById(R.id.card4_parent);

                item_img_front1 = (ImageView) view.findViewById(R.id.item_img_front1);
                item_img_back1 = (ImageView) view.findViewById(R.id.item_img_back1);
                item_img_front2 = (ImageView) view.findViewById(R.id.item_img_front2);
                item_img_back2 = (ImageView) view.findViewById(R.id.item_img_back2);
                item_img_front3 = (ImageView) view.findViewById(R.id.item_img_front3);
                item_img_back3 = (ImageView) view.findViewById(R.id.item_img_back3);
                item_img_front4 = (ImageView) view.findViewById(R.id.item_img_front4);
                item_img_back4 = (ImageView) view.findViewById(R.id.item_img_back4);

                match_card_1 = (RelativeLayout) view.findViewById(R.id.match_card_1);
                match_card_2 = (RelativeLayout) view.findViewById(R.id.match_card_2);
                match_card_3 = (RelativeLayout) view.findViewById(R.id.match_card_3);
                match_card_4 = (RelativeLayout) view.findViewById(R.id.match_card_4);


            }else if (type.equals(context.getString(R.string.card_high_score))){
                name = (TextView) view.findViewById(R.id.name);
                high_score = (TextView) view.findViewById(R.id.high_score);
            }
        }
    }
}