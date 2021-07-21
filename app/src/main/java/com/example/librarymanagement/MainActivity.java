package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button save;
    EditText t_name,t_author;
    ListView lv;
    TextView textview;
    ArrayAdapter<BookModel> bookModelArrayAdapter;
    private DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t_name=(EditText)findViewById(R.id.book_name);
        t_author=(EditText)findViewById(R.id.book_author);
        save=(Button)findViewById(R.id.save);
        lv=(ListView)findViewById(R.id.list_view);
        db= new DBHelper(this);
        Show_Record_Details();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t_name.getText().toString()==null&&t_author.getText().toString()==null)
                {
                    Toast.makeText(MainActivity.this,"INSERTION FAILED",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BookModel bookModel=new BookModel(-1,t_name.getText().toString(),t_author.getText().toString());
                    long val= db.create_record(bookModel);
                    if(val==-1)
                        Toast.makeText(MainActivity.this,"INSERTION FAILED",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,"INSERTION SUCCESS",Toast.LENGTH_SHORT).show();
                }
                Show_Record_Details();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookModel bookModelitem= (BookModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,RecordDetails.class);
                intent.putExtra("b_id",bookModelitem.getId());
                intent.putExtra("b_name",bookModelitem.getName());
                intent.putExtra("b_author",bookModelitem.getAuthor());
                startActivity(intent);
            }
        });

    }

    private void Show_Record_Details() {
        bookModelArrayAdapter = new ArrayAdapter<BookModel>(MainActivity.this, android.R.layout.simple_list_item_1, db.view_record());
        lv.setAdapter(bookModelArrayAdapter);
    }
}