package com.example.use.notebook_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Modify extends AppCompatActivity {

    private Button btn_modify;
    private EditText edtTitle,edtContent;

    private MyDB db = null;
    Cursor cursor;
    int myid;

    String title,content;  //標題和內容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtContent = (EditText)findViewById(R.id.edtContent);
        btn_modify = (Button)findViewById(R.id.btn_modify);

        /* 傾聽事件 */
        btn_modify.setOnClickListener(btn_modify_listener);

        /* 取得資料 */
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        title = bundle.getString("title");
        content = bundle.getString("content");
        myid = bundle.getInt("id");

        /* 將資料顯示在對應的 EditView 中 */
        edtTitle.setText(title); //顯示標題
        edtContent.setText(content); //顯示內容

        db = new MyDB(this);  //建立 MyDB 物件
        db.open();
    }

    public Button.OnClickListener btn_modify_listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            title = edtTitle.getText().toString(); //顯示修改後的標題
            content = edtContent.getText().toString(); //顯示修改後的內容

            /* 更新修改後的內容 */
            if(db.update(myid,title,content))
            {
                cursor = db.getAll(); //載入全部資料
            }

            Intent intent = new Intent();
            intent.setClass(Modify.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
