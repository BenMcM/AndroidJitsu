package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.Kyusho;

public class KyushoDataSource extends JitsuDataSource {

	public KyushoDataSource (Context context){
		super(context);
	}

	public List<Kyusho> getKyushos() {
		String query = KYUSHU_BASEQUERY + ORDERBYGRADE;

		return getKyushosFromDB(query);
	}

	public List<Kyusho> getKyushosByGrade(int gradeId) {
		String query = KYUSHU_BASEQUERY + " AND k." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getKyushosFromDB(query);
	}

	public Kyusho getKyushoById(int id) {
		String query = KYUSHU_BASEQUERY + " AND k." + JitsuSQLiteHelper.FINGERLOCK_COLUMN_ID + " = " + id;

		return getKyushosFromDB(query).get(0);
	}

	public void newKyusho(Kyusho kyusho){
		ContentValues values = contentValuesFromKyusho(kyusho);

		database.insert(JitsuSQLiteHelper.KYUSHO_TABLE_NAME, null, values);
	}

	public void updateKyusho(Kyusho kyusho){
		ContentValues values = contentValuesFromKyusho(kyusho);

		database.update(JitsuSQLiteHelper.KYUSHO_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{kyusho.getId().toString()});

	}

	private List<Kyusho> getKyushosFromDB(String query){
		List<Kyusho> kyushos = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()){
			Kyusho kyusho = kyushoFromCursor(cursor);
			kyushos.add(kyusho);
		}
		// make sure to close the cursor
		cursor.close();
		return kyushos;
	}

	private Kyusho kyushoFromCursor(Cursor cursor){
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.KYUSHO_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.KYUSHO_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.KYUSHO_COLUMN_DESCRIPTION));

		return new Kyusho(id, number, grade, description);
	}

	private ContentValues contentValuesFromKyusho(Kyusho kyusho){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.KYUSHO_COLUMN_NUMBER, kyusho.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, kyusho.getGrade().getId());
		values.put(JitsuSQLiteHelper.KYUSHO_COLUMN_DESCRIPTION, kyusho.getDescription());

		return values;
	}

}
