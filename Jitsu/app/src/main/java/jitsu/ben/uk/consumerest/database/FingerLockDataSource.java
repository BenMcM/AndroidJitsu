package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.FingerLock;
import jitsu.ben.uk.consumerest.bean.Grade;

public class FingerLockDataSource extends JitsuDataSource {

	public FingerLockDataSource(Context context){
		super(context);
	}

	public List<FingerLock> getFingerLocks() {
		String query = FINGERLOCK_BASEQUERY + ORDERBYGRADE;

		return getFingerLocksFromDB(query);
	}

	public List<FingerLock> getFingerLocksByGrade(int gradeId) {
		String query = FINGERLOCK_BASEQUERY + " AND f." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getFingerLocksFromDB(query);
	}

	public FingerLock getFingerLockById(int id) {
		String query = FINGERLOCK_BASEQUERY + " AND f." + JitsuSQLiteHelper.FINGERLOCK_COLUMN_ID + " = " + id;

		return getFingerLocksFromDB(query).get(0);
	}

	public void newFingerLock(FingerLock fingerLock){
		ContentValues values = contentValuesFromFingerLock(fingerLock);

		database.insert(JitsuSQLiteHelper.FINGERLOCK_TABLE_NAME, null, values);
	}

	public void updateFingerLock(FingerLock fingerLock){
		ContentValues values = contentValuesFromFingerLock(fingerLock);

		database.update(JitsuSQLiteHelper.FINGERLOCK_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{fingerLock.getId().toString()});
	}

	private List<FingerLock> getFingerLocksFromDB(String query) {
		List<FingerLock> fingerLocks = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			FingerLock fingerLock = fingerLockFromCursor(cursor);
			fingerLocks.add(fingerLock);
		}
		// make sure to close the cursor
		cursor.close();
		return fingerLocks;
	}

	private FingerLock fingerLockFromCursor(Cursor cursor) {
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.FINGERLOCK_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.FINGERLOCK_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.FINGERLOCK_COLUMN_DESCRIPTION));

		return new FingerLock(id, number, grade, description);
	}

	private ContentValues contentValuesFromFingerLock(FingerLock fingerLock){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.FINGERLOCK_COLUMN_NUMBER, fingerLock.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, fingerLock.getGrade().getId());
		values.put(JitsuSQLiteHelper.FINGERLOCK_COLUMN_DESCRIPTION, fingerLock.getDescription());

		return values;
	}
}
