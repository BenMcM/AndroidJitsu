package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.AnkleLock;
import jitsu.ben.uk.consumerest.bean.Grade;

public class AnkleLockDataSource extends JitsuDataSource {

	public AnkleLockDataSource(Context context){
		super(context);
	}

	public List<AnkleLock> getAnkleLocks() {
		String query = ANKLELOCK_BASEQUERY + ORDERBYGRADE;

		return getAnkleLocksFromDB(query);
	}

	public List<AnkleLock> getAnkleLocksByGrade(int gradeId) {
		String query = ANKLELOCK_BASEQUERY + " AND a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getAnkleLocksFromDB(query);
	}

	public AnkleLock getAnkleLockById(int id) {
		String query = ANKLELOCK_BASEQUERY + " AND a." + JitsuSQLiteHelper.ANKLELOCK_COLUMN_ID + " = " + id;

		return getAnkleLocksFromDB(query).get(0);
	}

	public void newAnkleLock(AnkleLock ankleLock){
		ContentValues values = contentValuesFromAnkleLock(ankleLock);

		database.insert(JitsuSQLiteHelper.ANKLELOCK_TABLE_NAME, null, values);
	}

	public void updateAnkleLock(AnkleLock ankleLock){
		ContentValues values = contentValuesFromAnkleLock(ankleLock);

		database.update(JitsuSQLiteHelper.ANKLELOCK_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{ankleLock.getId().toString()});
	}

	private List<AnkleLock> getAnkleLocksFromDB(String query) {
		List<AnkleLock> ankleLocks = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			AnkleLock ankleLock = ankleLockFromCursor(cursor);
			ankleLocks.add(ankleLock);
		}
		// make sure to close the cursor
		cursor.close();
		return ankleLocks;
	}


	private AnkleLock ankleLockFromCursor(Cursor cursor) {
		Grade grade = super.gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.ANKLELOCK_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.ANKLELOCK_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.ANKLELOCK_COLUMN_DESCRIPTION));

		return new AnkleLock(id, number, grade, description);
	}

	private ContentValues contentValuesFromAnkleLock(AnkleLock ankleLock){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.ANKLELOCK_COLUMN_NUMBER, ankleLock.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, ankleLock.getGrade().getId());
		values.put(JitsuSQLiteHelper.ANKLELOCK_COLUMN_DESCRIPTION, ankleLock.getDescription());

		return values;
	}
}
