package com.example.use.notebook_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuPresenter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    /* 自建的資料庫類別 */
    private MyDB db = null;

    private ListView listview;
    //private Button btn_insert;
    private FloatingActionButton f_btn_add; //浮動按鈕

    Cursor cursor;
    long myid; //儲存 _id 的值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView)findViewById(R.id.listview);
        //btn_insert = (Button)findViewById(R.id.btn_insert);
        f_btn_add = (FloatingActionButton)findViewById(R.id.f_btn_add);

        Log.d("test","test yeah");
        /* 設定監聽事件 */
        //btn_insert.setOnClickListener(btn_insert_listener);
        listview.setOnItemClickListener(lst_listener);
        f_btn_add.setOnClickListener(f_btn_add_listener);

        db = new MyDB(this);  //建立 MyDB 物件
        db.open();
        cursor = db.getAll(); //載入全部資料
        UpdateAdapter(cursor); //載入資料表至 Listview 中
    }

    // ListView 監聽事件
    public ListView.OnItemClickListener lst_listener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor c = db.get(id);
            myid = id;

            /* 傳送資料到顯示頁面 */
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,ConTent.class);
            Bundle bundle = new Bundle();

            bundle.putString("title",c.getString(1));
            bundle.putString("content",c.getString(2));
            bundle.putString("MY_ID",String.valueOf(myid));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /* 更新資料 */
    public void UpdateAdapter(Cursor cursor){
        if(cursor != null && cursor.getCount() >= 0)
        {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, //包含兩個資料項
                    cursor, //資料的 cursor 物件
                    new String[]{"title"}, //要顯示的欄位
                    new int[]{android.R.id.text1},
                    0);
            listview.setAdapter(adapter);
        }
    }

    /* 新增一筆資料 */
    /*public Button.OnClickListener btn_insert_listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();  //建立 Intent 物件
            intent.setClass(MainActivity.this,Insert.class);
            startActivity(intent);

            finish();

        }
    };*/

    /* 新增一筆資料 */
    private View.OnClickListener f_btn_add_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();  //建立 Intent 物件
            intent.setClass(MainActivity.this,Insert.class);
            startActivity(intent);

            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
