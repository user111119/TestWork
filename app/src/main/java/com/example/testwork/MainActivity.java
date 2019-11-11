package com.example.testwork;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public ViewPager viewPager;
    public PagerAdapter adapter;
    public PagerTabStrip pagerTabStrip;
    public int count=0;
    public  List<MyFragment> fragments;
    public TextView counter;
    public Button add,sub;
    public static int s=0;
    ArrayList<Integer> array;
    ArrayList<String> list;
    ArrayList<Integer> list2;
    Notification.Builder builder;
    Notification notification;
    NotificationManager notificationManager;
    Intent resultIntent;
    PendingIntent resultPendingIntent;
    String page;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        array = new ArrayList<Integer>();
        list = new ArrayList<String>();
        list2 = new ArrayList<Integer>();
        setContentView(R.layout.activity_main);
        counter =  (TextView) findViewById(R.id.counter);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager);
        sub = (Button) findViewById(R.id.div);
        sub.setVisibility(View.INVISIBLE);
        fragments = new ArrayList<>();

        count = count + 1;

        fragments.add((MyFragment) MyFragment.newInstance("Create notification"));
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(fragments.size()-1);
        counter.setText(count+"");
        Notification();


    }


    public void addF(View view) {
        count = count + 1;

        fragments.add((MyFragment) MyFragment.newInstance("Create notification"));

        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(fragments.size()-1);
        viewPager.setSelected(true);
        counter.setText(count+"");
        sub.setVisibility(View.VISIBLE);

    }

    public void divF(View view) {
        fragments.remove(count-1);
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(fragments.size()-1);
        for (int i = 0; i <list.size() ; i++) {
            String delimeter = ":";
            String[] subStr;
            String str = list.get(i);
            subStr = str.split(delimeter);
            String page = "";
            String id = "";
            page = subStr[0];
            id = subStr[1];

            if(Integer.valueOf(page) == count){
                list2.add(Integer.valueOf(id));

            }
        }

        for (int i = 0; i <list2.size() ; i++) {
            notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(list2.get(i));
        }

        list2.clear();
        count = count-1;
        viewPager.setSelected(true);
        counter.setText(count+"");
        if(count == 1){
            sub.setVisibility(View.INVISIBLE);
        }


    }

    public void clickNotification(View view) {
        Notification();
    }

    public void Notification(){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                page = (String) adapter.getPageTitle(position);

            }

        });

        if(page == null){
            page = "Page 1";
        }
        if(count == 1){
            page = "Page 1";
        }
        Toast.makeText(this, "Selecteted "+page, Toast.LENGTH_SHORT).show();
        showNotification("Notification", page);

    }

    private void showNotification(String title, String body) {
        int a = new Random().nextInt();

        array.add(a);

        resultIntent = new Intent(this, MyFragment.class);
        int b = Integer.parseInt(page.replaceAll("[\\D]", ""));
        resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(resultPendingIntent);

        builder.addAction(
                R.drawable.ic_launcher_background,
                "open",
                PendingIntent.getActivity(
                        this,
                        1,
                        new Intent(this, MyFragment.class).putExtra(title,b),
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
        );

        notification = builder.build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(a, notification);

        list.add(b+":"+a);


    }

}