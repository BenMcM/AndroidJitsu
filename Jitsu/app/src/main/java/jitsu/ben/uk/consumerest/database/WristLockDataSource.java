package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.WristLock;

public class WristLockDataSource extends JitsuDataSource {

	public WristLockDataSource (Context context){
		super(context);
	}

	public List<WristLock> getWristLocks() {
		String query = WRISTLOCK_BASEQUERY + ORDERBYGRADE;

		return getWristLocksFromDB(query);
	}

	public List<WristLock> getWristLocksByGrade(int gradeId) {
		String query = WRISTLOCK_BASEQUERY + " AND w." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getWristLocksFromDB(query);
	}

	public WristLock getWristLockById(int id) {
		String query = WRISTLOCK_BASEQUERY + " AND w." + JitsuSQLiteHelper.WRISTLOCK_COLUMN_ID + " = " + id;

		return getWristLocksFromDB(query).get(0);
	}

	public void newWristLock(WristLock wristLock){
		ContentValues values = contentValuesFromWristLock(wristLock);

		database.insert(JitsuSQLiteHelper.WRISTLOCK_TABLE_NAME, null, values);
	}

	public void updateWristLock(WristLock wristLock){
		ContentValues values = contentValuesFromWristLock(wristLock);

		database.update(JitsuSQLiteHelper.WRISTLOCK_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{wristLock.getId().toString()});
	}

	private List<WristLock> getWristLocksFromDB(String query){
		List<WristLock> wristLocks = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()){
			WristLock wristLock = wristLockFromCursor(cursor);
			wristLocks.add(wristLock);
		}
		// make sure to close the cursor
		cursor.close();
		return wristLocks;
	}

	private WristLock wristLockFromCursor(Cursor cursor){
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.WRISTLOCK_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.WRISTLOCK_COLUMN_NUMBER));
		String entrance = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.WRISTLOCK_COLUMN_ENTRANCE));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.WRISTLOCK_COLUMN_DESCRIPTION));

		return new WristLock(id, number, grade, entrance, description);
	}

	private ContentValues contentValuesFromWristLock(WristLock wristLock){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.WRISTLOCK_COLUMN_NUMBER, wristLock.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, wristLock.getGrade().getId());
		values.put(JitsuSQLiteHelper.WRISTLOCK_COLUMN_ENTRANCE, wristLock.getEntrance());
		values.put(JitsuSQLiteHelper.WRISTLOCK_COLUMN_DESCRIPTION, wristLock.getDescription());

		return values;
	}
}
