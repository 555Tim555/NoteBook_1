package com.example.use.notebook_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Insert extends AppCompatActivity {


    /* 自建的資料庫類別 */
    private MyDB db = null;
    Cursor cursor;

    private EditText edtTitle,edtContent;
    private Button btnYes;
    String title,content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtContent = (EditText)findViewById(R.id.edtContent);
        btnYes = (Button)findViewById(R.id.btnYes);

        btnYes.setOnClickListener(btn_Yes_listener);


        db = new MyDB(this);  //建立 MyDB 物件
        db.open();

    }

    public Button.OnClickListener btn_Yes_listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            title = edtTitle.getText().toString();
            content = edtContent.getText().toString();

            if(db.append(title,content) > 0)
            {
                cursor = db.getAll(); //載入全部資料
            }
            //不做更新資料的原因是因為finish後返回主 Activity 時會重新再onCreate一次，所以會有UpdateAdapter

            Intent intent = new Intent();  //建立 Intent物件將
            intent.setClass(Insert.this,MainActivity.class);
            startActivity(intent);

            Clear();
            finish();  //結束此 Activity 即執行 onDestroy()
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void Clear()
    {
        edtTitle.setText("");
        edtContent.setText("");
    }
}
