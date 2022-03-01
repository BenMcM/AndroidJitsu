package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.NeckLock;

public class NeckLockDataSource extends JitsuDataSource {

	public NeckLockDataSource(Context context){
		super(context);
	}

	public List<NeckLock> getNeckLocks() {
		String query = NECKLOCK_BASEQUERY + ORDERBYGRADE;

		return getNeckLocksFromDB(query);
	}

	public List<NeckLock> getNeckLocksByGrade(int gradeId) {
		String query = NECKLOCK_BASEQUERY + " AND n." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getNeckLocksFromDB(query);
	}

	public NeckLock getNeckLockById(int id) {
		String query = NECKLOCK_BASEQUERY + " AND n." + JitsuSQLiteHelper.NECKLOCK_COLUMN_ID + " = " + id;

		return getNeckLocksFromDB(query).get(0);
	}

	public void newNeckLock(NeckLock neckLock){
		ContentValues values = contentValuesFromNeckLock(neckLock);

		database.insert(JitsuSQLiteHelper.NECKLOCK_TABLE_NAME, null, values);
	}

	public void updateNeckLock(NeckLock neckLock){
		ContentValues values = contentValuesFromNeckLock(neckLock);

		database.update(JitsuSQLiteHelper.NECKLOCK_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{neckLock.getId().toString()});
	}

	private List<NeckLock> getNeckLocksFromDB(String query){
		List<NeckLock> neckLocks = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()){
			NeckLock neckLock = neckLockFromCursor(cursor);
			neckLocks.add(neckLock);
		}
		// make sure to close the cursor
		cursor.close();
		return neckLocks;
	}

	private NeckLock neckLockFromCursor(Cursor cursor){
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.NECKLOCK_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.NECKLOCK_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.NECKLOCK_COLUMN_DESCRIPTION));

		return new NeckLock(id, number, grade, description);
	}

	private ContentValues contentValuesFromNeckLock(NeckLock neckLock){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.NECKLOCK_COLUMN_NUMBER, neckLock.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, neckLock.getGrade().getId());
		values.put(JitsuSQLiteHelper.NECKLOCK_COLUMN_DESCRIPTION, neckLock.getDescription());

		return values;
	}
}
