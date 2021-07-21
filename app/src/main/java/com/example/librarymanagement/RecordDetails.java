package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecordDetails extends AppCompatActivity {
    EditText t_uname,t_uauthor;
    Button update,delete;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        t_uname=(EditText)findViewById(R.id.upd_name);
        t_uauthor=(EditText)findViewById(R.id.upd_author);
        update=(Button)findViewById(R.id.update);
        delete=(Button)findViewById(R.id.delete);
        db=new DBHelper(this);
        Intent i=getIntent();
        int id=i.getIntExtra("b_id",-1);
        String bookname=i.getStringExtra("b_name");
        String bookauthor=i.getStringExtra("b_author");
        t_uname.setText(bookname);
        t_uauthor.setText(bookauthor);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete_record(id);
                Toast.makeText(RecordDetails.this,"DELETED SUCCESSFULLY",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecordDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookModel bookModel=new BookModel(id,t_uname.getText().toString(),t_uauthor.getText().toString());
                db.update_record(bookModel);
                Toast.makeText(RecordDetails.this,"UPDATED SUCCESSFULLY",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecordDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}