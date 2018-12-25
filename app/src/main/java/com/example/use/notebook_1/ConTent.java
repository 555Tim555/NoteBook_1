package com.example.use.notebook_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ConTent extends AppCompatActivity {

    //private EditText edtContent,edtTitle;
    private Button btn_delete,btn_modify;
    private TextView txt_title,txt_Content;

    private MyDB db = null;
    Cursor cursor;

    String title;
    String content;
    int myid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_tent);

        //edtTitle = (EditText)findViewById(R.id.edtTitle);
        //edtContent = (EditText)findViewById(R.id.edtContent);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_modify = (Button)findViewById(R.id.btn_modify);
        txt_title = (TextView)findViewById(R.id.txt_title);
        txt_Content = (TextView)findViewById(R.id.txtContent);

        /* 傾聽事件 */
        btn_delete.setOnClickListener(btn_delete_listener);
        btn_modify.setOnClickListener(btn_modify_listener);

        /* 接收傳遞過來的資料，包含標題、內容、ID(目前的位置) */
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        title = bundle.getString("title"); //取得標題
        content = bundle.getString("content"); //取得內容
        myid = Integer.parseInt(bundle.getString("MY_ID")); //取得目前的所在位置

        /* 將資料顯示在對應的 EditView 中 */
        txt_title.setText("標題 " + title); //顯示標題
        txt_Content.setText("內容: " + "\r\n" + content); //顯示內容

        //edtContent.setText(content);

        db = new MyDB(this);  //建立 MyDB 物件
        db.open();
        cursor = db.getAll(); //載入全部資料
    }

    /* 刪除一筆資料 */
    public Button.OnClickListener btn_delete_listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            cursor = db.get(myid);  //取得目前的項目位置
            if(cursor != null && cursor.getCount() >= 0)
            {
                if(db.delete(myid))
                {
                    cursor = db.getAll(); //載入全部資料
                    //UpdateAdapter(cursor); //更新資料，載入資料表到 ListView 中

                    Intent intent = new Intent();  //建立 Intent物件將
                    intent.setClass(ConTent.this,MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        }
    };

    /* 進行編輯，將資料傳至編輯的頁面 */
    public  Button.OnClickListener btn_modify_listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ConTent.this,Modify.class);

            Bundle bundle = new Bundle();

            bundle.putInt("id",myid);
            bundle.putString("title",title);
            bundle.putString("content",content);

            intent.putExtras(bundle);

            startActivity(intent);
        }
    };
}
