package com.example.librarymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String db_name="Library DB";
    public static final String tb_name="BOOK_DETAILS";
    public static final String b_id="BOOK_ID";
    public static final String b_name="BOOK_NAME";
    public static final String b_author="BOOK_AUTHOR";

    private static final String create_table="CREATE TABLE "+tb_name+"("+b_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +b_name+" TEXT NOT NULL, "+b_author+" TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context,db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tb_name);
        onCreate(db);
    }

    public long create_record(BookModel bookModel)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(b_name,bookModel.getName());
        cv.put(b_author,bookModel.getAuthor());
        long ins = db.insert(tb_name,null,cv);
        return ins;
    }

    public List <BookModel> view_record()
    {
        List<BookModel> details_list = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+tb_name+";",null);
        if(res.moveToFirst())
        {
            do {
                {
                    int c_id= res.getColumnIndex(b_id);
                    int c_name=res.getColumnIndex(b_name);
                    int c_author=res.getColumnIndex(b_author);
                    int bookid= res.getInt(c_id);
                    String bookname=res.getString(c_name);
                    String bookauthor=res.getString(c_author);
                    BookModel bookModel=new BookModel(bookid,bookname,bookauthor);
                    details_list.add(bookModel);
                }
            }while(res.moveToNext());
        }
        return details_list;
    }
    public boolean delete_record(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(tb_name,b_id+"="+id,null);
        Cursor res=db.rawQuery("SELECT * FROM "+tb_name,null);
        if (res.moveToFirst())
            return true;
        else
            return false;
    }
    public long update_record(BookModel bookModel)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(b_name,bookModel.getName());
        cv.put(b_author,bookModel.getAuthor());
        return db.update(tb_name,cv,b_id+"="+bookModel.getId(),null);
    }

}
