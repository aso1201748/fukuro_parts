package com.android.fukuro;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private final static String DB_NAME = "fukuro.db";
	private final static int DB_VERSION = 1;

	public DBHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// データベース作成時の処理を記述する。

		// テーブル作成
		// ユーザテーブル
		String sql1 = "CREATE TABLE User ( " +
						"user_id TEXT, " +
						"user_name TEXT NOT NULL, " +
						"PRIMARY KEY(user_id))";
		// カテゴリテーブル
		String sql2 = "CREATE TABLE Category ( " +
				"category_id TEXT, " +
				"category_name TEXT NOT NULL, " +
				"PRIMARY KEY(category_id))";
		// 画像テーブル
		String sql3 = "CREATE TABLE Item ( " +
				"item_id TEXT, " +
				"item TEXT NOT NULL, " +
				"category_id TEXT NOT NULL, " +
				"memo TEXT NOT NULL, " +
				"PRIMARY KEY(item_id), " +
				"FOREIGN KEY(category_id)REFERENCES Category(category_id))";
		// マイリストテーブル
		String sql4 = "CREATE TABLE Mylist ( " +
				"mylist_id TEXT, " +
				"thambnail TEXT NOT NULL, " +
				"PRIMARY KEY(mylist_id))";
		// マイリスト構成テーブル
		String sql5 = "CREATE TABLE Mylistmaking ( " +
				"mylist_id TEXT, " +
				"item_id TEXT, " +
				"item_position TEXT, " +
				"PRIMARY KEY(mylist_id,item_id), " +
				"FOREIGN KEY(mylist_id)REFERENCES Mylist(mylist_id), " +
				"FOREIGN KEY(item_id)REFERENCES Item(item_id))";
		// ランキングテーブル
		String sql6 = "CREATE TABLE Ranking ( " +
				"ranking_id TEXT, " +
				"user_id TEXT NOT NULL, " +
				"ranking_thambnail TEXT, " +
				"ranking_item TEXT, " +
				"date NUMERIC, " +
				"good NUMERIC, " +
				"PRIMARY KEY(ranking_id), " +
				"FOREIGN KEY(user_id)REFERENCES User(user_id))";

		//SQL文の実行
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sql6);

		//データ挿入
		// ユーザテーブ
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000001','相沢')");
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000002','宍戸')");
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000003','父さん')");
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000004','笹井')");
		// カテゴリテーブル
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('1','Tシャツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('2','シャツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('3','ニット・カーディガン')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('4','ジャケット・コート')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('5','パンツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('6','ショートパンツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('7','全身')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void InsertItem(SQLiteDatabase db, String name, String category, String memo){
		String picpath = name;
		String categorynum = category;
		String memovalue = memo;
		String ID = "0001";
		ArrayList<String> ItemList = new ArrayList<String>();

		String sql2 = "select Item_id from Item order by Item_id";

		Cursor c2 = db.rawQuery(sql2, null);

		c2.moveToFirst();

		for(int i = 0; i < c2.getCount(); i++){
			ItemList.add(c2.getString(0));
			c2.moveToNext();
		}

		int j = 0;

		while(j < ItemList.size()){

			ID = String.format("%04d", j + 1);

			if(!(ID.equals(ItemList.get(j)))){
				Log.d("checkif1",ID);
				Log.d("checkif2",ItemList.get(j));
				break;
			}
			Log.d("checkelse",ID);
			j++;
			ID = String.format("%04d", j + 1);
		}

		Log.d("checkinsertbe",ID);
		String sql = "INSERT INTO Item(item_id,item, category_id, memo) VALUES(\""+ ID +"\",\"" + picpath + "\",\"" + categorynum + "\",\"" + memovalue +"\")";
		db.execSQL(sql);
	}

}
