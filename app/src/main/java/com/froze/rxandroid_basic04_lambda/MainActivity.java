package com.froze.rxandroid_basic04_lambda;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "RxAndroid_Basic04";
    Button btnLambda, btnmpa, btnflatmap, btnzip;
    TextView tv;
    ArrayList<String> datas = new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);
        btnLambda = (Button)findViewById(R.id.button);
        btnmpa = (Button)findViewById(R.id.button2);
        btnflatmap = (Button)findViewById(R.id.button3);
        btnzip = (Button)findViewById(R.id.button4);

        btnLambda.setOnClickListener(this);
        btnmpa.setOnClickListener(this);
        btnflatmap.setOnClickListener(this);
        btnzip.setOnClickListener(this);

        tv = (TextView)findViewById(R.id.textView);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button :
                doLambda();
                break;
            case R.id.button2 :
                doMap();
                break;
            case R.id.button3:
                doFlatMap();
                break;
            case R.id.button4:
                doZip();
                break;
        }
    }

    public void doLambda(){
        Observable<String> observable = Observable.just("I am Lambda!");

        observable.subscribe(
                item -> tv.setText(item),
                error -> Log.e("TAG",error.getMessage()),
                () -> Log.i("TAG","Completed")
        );
    }

    public void doMap() {
        Observable.from(new String[]{"dog", "bird", "chicken", "horse", "turtle", "rabbit", "tiger"})
            .map(item -> "["+item+"]")
            .subscribe(
                item -> datas.add(item),
                e -> e.printStackTrace(),
                () -> adapter.notifyDataSetChanged()
        );
    }

    public void doFlatMap() {
        Observable.from(new String[]{"dog", "bird", "chicken", "horse", "turtle", "rabbit", "tiger"})
                .flatMap(item -> Observable.from(new String[]{"name: "+item, item.getBytes()+""}))
                .subscribe(
                        item -> datas.add(item),
                        e -> e.printStackTrace(),
                        () -> adapter.notifyDataSetChanged()
                );
    }

    public void doZip(){
        Observable.zip(
                Observable.just("KIM HYOJUNG"),
                Observable.just("image.jpg"),
                (item1, item2) -> "Name:"+item1 + ", Profile image"+item2
        ).subscribe(
                zipped -> Log.w(TAG, "onNext item ="+zipped)
        );
    }
}
