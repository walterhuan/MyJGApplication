package com.walter.sc.myjgapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.walter.sc.NavigationView.NavigationViewActivity;
import com.walter.sc.customControl.CustomCActivity;
import com.walter.sc.czboke.CZBKMainActivity;
import com.walter.sc.eventbus.EventBusActivity;
import com.walter.sc.greendao.NoteActivity;
import com.walter.sc.handheld.HandHeldActivity;
import com.walter.sc.okhttp.OKHttpActivity;
import com.walter.sc.tablayout.MyTabActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {
    private static final ButterKnife.Action<View> ALPHA_FADE = new ButterKnife.Action<View>() {

        @Override
        public void apply(View view, int index) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
            alphaAnimation.setFillBefore(true);
            alphaAnimation.setDuration(500);
            alphaAnimation.setStartOffset(index * 100);
            view.startAnimation(alphaAnimation);
        }
    };

    @Bind(R.id.title)
    TextView myTitle;

    @Bind(R.id.subtitle)
    TextView mySubTitle;

    @Bind(R.id.hello)
    Button myButton;

    @Bind(R.id.list_of_things)
    ListView myListOfThings;

    @Bind(R.id.footer)
    TextView myFooter;

    @Bind({R.id.title,R.id.subtitle,R.id.hello})
    List<View> headerViews;

    private MyButterKnifeAdapter adapter;
    
    @OnClick(R.id.hello)
    void sayHello(){
        Toast.makeText(this,"Hello views!",Toast.LENGTH_SHORT).show();
        ButterKnife.apply(headerViews,ALPHA_FADE);
    }

    @OnLongClick(R.id.hello)
    boolean sayGetOffMe(){
        Toast.makeText(this,"get off me!",Toast.LENGTH_SHORT).show();
        return true;
    }

    @OnItemClick(R.id.list_of_things)
    void OnItemClickList(int position){
        Toast.makeText(this,adapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
        switch (position){
            case 0:
                Intent intent = new Intent(this, OKHttpActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(this, NoteActivity.class);
                startActivity(intent1);
                break;
            case 2:
                SoundPool soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100);
                int soundid = soundpool.load("/etc/Scan_new.ogg", 1);
                soundpool.play(soundid, 1, 1, 0, 0, 1);

                break;
            case 3:
                Intent intent3 = new Intent(this, EventBusActivity.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(this, NavigationViewActivity.class);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(this, MyTabActivity.class);
                startActivity(intent5);
                break;
            case 6:
                Intent intent6 = new Intent(this, HandHeldActivity.class);
                startActivity(intent6);
                break;
            case 7:
                Intent intent7 = new Intent(this, CustomCActivity.class);
                startActivity(intent7);
                break;
            case 8:
                Intent intent8 = new Intent(this, CZBKMainActivity.class);
                startActivity(intent8);
                break;


        }

    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        myTitle.setText("Butter Knife");
        mySubTitle.setText("Field and method binding for Android views.");
        myFooter.setText("by Jake Wharton");
        myButton.setText("Say Hello");

        adapter = new MyButterKnifeAdapter(this);
        myListOfThings.setAdapter(adapter);


    }
}
