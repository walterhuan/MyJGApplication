package com.walter.sc.greendao;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import com.walter.greendao.FlightInfo;
import com.walter.greendao.FlightInfoDao;
import com.walter.greendao.Note;
import com.walter.greendao.NoteDao;
import com.walter.sc.myjgapplication.BaseApplication;
import com.walter.sc.myjgapplication.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by huangxl on 2016/4/5.
 */
public class NoteActivity extends ListActivity {

    private Cursor cursor;
    public static final String TAG = "NoteActivity";
    private int fltnum=0;


    @Bind(R.id.editTextNote)
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        String textColum = NoteDao.Properties.Text.columnName;
        String orderBy = textColum + " COLLATE LOCALIZED ASC";
        cursor = getDb().query(getNoteDao().getTablename(),getNoteDao().getAllColumns(),null,null,null,null,orderBy);
        String[] from = {textColum,NoteDao.Properties.Comment.columnName};
        int[] to = {android.R.id.text1,android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,
                from,to,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);




    }

    private NoteDao getNoteDao(){
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return ((BaseApplication)this.getApplicationContext()).getDaoSession().getNoteDao();

    }

    private FlightInfoDao getFlightInfoDao(){
        return ((BaseApplication)this.getApplicationContext()).getDaoSession().getFlightInfoDao();
    }

    private SQLiteDatabase getDb(){
        // 通过 BaseApplication 类提供的 getDb() 获取具体 db
        return ((BaseApplication)this.getApplicationContext()).getDb();
    }


    @OnClick(R.id.buttonAdd)
    void add(View view){
        String noteText = editText.getText().toString();
        editText.setText("");
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
        Log.i(TAG,"buttonAdd");
        String comment = "Added on" +df.format(new Date());
        if (null==noteText || noteText.equals("")){
            ToastUtils.show(getApplicationContext(), "Please enter a note to add");

        }else{
            fltnum++;
            Note note = new Note(null,noteText,comment,new Date(),"aaa");
            FlightInfo fi = new FlightInfo(null,"3U888"+fltnum);
            getNoteDao().insert(note);
            getFlightInfoDao().insert(fi);

            Log.i(TAG, "Inserted new note   ID=" + note.getId() + "   date=" + note.getDate()+"   whatcall="+note.getWhatcall());
            cursor.requery();
        }
    }

    @OnClick(R.id.buttonQuery)
    void query(View view){
        String noteText = editText.getText().toString();
        editText.setText("");
        if (noteText==null || noteText.equals("")){
            ToastUtils.show(getApplicationContext(), "Please enter a note to query");

        }else{
            // Query 类代表了一个可以被重复执行的查询
           Query query = getNoteDao().queryBuilder()
                   .where(NoteDao.Properties.Text.eq(noteText))
                   .orderAsc(NoteDao.Properties.Date)
                   .build();
            // 查询结果以 List 返回
            List notes = query.list();
            ToastUtils.show(getApplicationContext(), "There have " + notes.size() + " records  "+" list="+notes.toString());

        }
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;


        //新表查询
        Cursor cursor2=getDb().query(getFlightInfoDao().getTablename(),getFlightInfoDao().getAllColumns(),null,null,null,null,null);
        for (cursor2.moveToFirst();!cursor2.isAfterLast();cursor2.moveToNext()){
            int idColumn = cursor2.getColumnIndex(FlightInfoDao.Properties.Id.columnName);
            int flightnumColumn=cursor2.getColumnIndex(FlightInfoDao.Properties.FlightNum.columnName);
            Long _id=cursor2.getLong(idColumn);
            String flightnum = cursor2.getString(flightnumColumn);
            Log.i(TAG,"id="+_id+"  flightNum="+flightnum);

        }

    }


    @OnItemClick(android.R.id.list)
    void OnItemClick(long id){
        Log.i(TAG, "id=" + id);
        // 删除操作，你可以通过「id」也可以一次性删除所有
        getNoteDao().deleteByKey(id);
        cursor.requery();
    }

}
