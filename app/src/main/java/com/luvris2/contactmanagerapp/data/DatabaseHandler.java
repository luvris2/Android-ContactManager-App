package com.luvris2.contactmanagerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.luvris2.contactmanagerapp.model.Contact;
import com.luvris2.contactmanagerapp.util.Util;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    // 데이터베이스 SQL 다루는 클래스
    // SQLiteOpenHelper 추상클래스를 상속 받는 클래스, 추상 메소드 정의

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // 테이블을 설정하는 메소드
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQLite : VARCHAR -> text
        String CREATE_CONTACT_TABLE = "create table " + Util.TABLE_NAME
                + "(" + Util.KEY_ID + " integer primary key, "
                + Util.KEY_NAME + " text, "
                + Util.KEY_PHONE + " text )";

        Log.i("MyContact", "테이블 생성문 : " + CREATE_CONTACT_TABLE);

        // SQL문 실행 명령어
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    // 데이터베이스를 제어하는 메소드
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 기존의 테이블을 삭제하고 새로운 테이블 생성
        String DROP_TABLE = "drop table " + Util.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        onCreate(sqLiteDatabase);
    }
    // TODO 데이터베이스 인서트 메소드
    // DB CRUD 관련 명령어 (Insert)
    public void addContact(Contact contact){
        // 데이스베이스 호출
        SQLiteDatabase db = this.getWritableDatabase();

        // 데이터베이스 insert
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.name);
        values.put(Util.KEY_PHONE, contact.phone);
        db.insert(Util.TABLE_NAME, null, values);

        // 자원 해제
        db.close();
    }
    // TODO 데이터베이스 엑세스 메소드
    // 주소록 데이터 가져오기
    // - 1개의 주소록 데이터만 가져오기 : id로 가져오기 select * from contact where id = n;
    public Contact getContact(int id){
        // 데이터 베이스 호출
        SQLiteDatabase db = this.getWritableDatabase();

        // SQL 쿼리문 작성
        Cursor cursor = db.rawQuery("select * from contact where id = " + id, null);
        // Cursor cursor = db.rawQuery("select * from contact where id = ?", new String[]{""+id});

        if (cursor != null) {
            cursor.moveToFirst();
        }

        // 인덱스의 첫번째 컬럼(id)를 가져오는 방법
        // cursor.getInt(0); or getString(0)

        // DB 데이터를 메모리에 저장하고 CPU 로 처리
        Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        contact.id = cursor.getInt(0);
        contact.name = cursor.getString(1);
        contact.phone = cursor.getString(2);

        cursor.close();

        return contact;
    }

    // - 주소록 전체 데이터 가져오기 : select * from contact;
    public ArrayList<Contact> getAllContact() {
        // 데이터 베이스 호출
        SQLiteDatabase db = this.getWritableDatabase();

        // SQL 쿼리문 작성
        Cursor cursor = db.rawQuery("select * from contact", null);
        // Cursor cursor = db.rawQuery("select * from contact where id = ?", new String[]{""+id});

        ArrayList<Contact> contactList = new ArrayList<>();

        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++) {
            // DB 데이터를 메모리에 저장하고 CPU 로 처리
            Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            contactList.add(contact);
            cursor.moveToNext();
        }

        // 위의 for 와 같은 의미
//        if(cursor.moveToFirst()) {
//            do {
//                Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
//                contactList.add(contact);
//            } while(cursor.moveToNext());
//        }

        cursor.close();

        return contactList;
    }
    // TODO 데이터베이스 업데이트 메소드
    // DB CRUD 관련 명령어 (Update)
    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.name);
        values.put(Util.KEY_PHONE, contact.phone);

        // db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?", new String[]{contact.id+""});
        db.execSQL("update contact set name = ?, phone = ? where id = ?", new String[]{contact.name, contact.phone, contact.id+""});
        db.close();
    }
    // TODO 데이터베이스 딜리트 메소드
    // DB CRUD 관련 명령어 (Delete)
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from contact where id = ?", new String[]{contact.id+""});
        db.close();
    }
}
