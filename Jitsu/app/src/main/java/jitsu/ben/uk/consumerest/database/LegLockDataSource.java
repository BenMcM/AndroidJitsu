package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.LegLock;

public class LegLockDataSource extends JitsuDataSource {

	public LegLockDataSource (Context context){
		super(context);
	}

	public List<LegLock> getLegLocks() {
		String query = LEGLOCK_BASEQUERY + ORDERBYGRADE;

		return getLegLocksFromDB(query);
	}

	public List<LegLock> getLegLocksByGrade(int gradeId) {
		String query = LEGLOCK_BASEQUERY + " AND l." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getLegLocksFromDB(query);
	}

	public LegLock getLegLockById(int id) {
		String query = LEGLOCK_BASEQUERY + " AND l." + JitsuSQLiteHelper.LEGLOCK_COLUMN_ID + " = " + id;

		return getLegLocksFromDB(query).get(0);
	}

	public void newLegLock(LegLock legLock){
		ContentValues values = contentValuesFromLegLock(legLock);

		database.insert(JitsuSQLiteHelper.LEGLOCK_TABLE_NAME, null, values);
	}

	public void updateLegLock(LegLock legLock){
		ContentValues values = contentValuesFromLegLock(legLock);

		database.update(JitsuSQLiteHelper.LEGLOCK_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{legLock.getId().toString()});
	}

	private List<LegLock> getLegLocksFromDB(String query){
		List<LegLock> legLocks = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()){
			LegLock legLock = legLockFromCursor(cursor);
			legLocks.add(legLock);
		}
		// make sure to close the cursor
		cursor.close();
		return legLocks;
	}

	private LegLock legLockFromCursor(Cursor cursor){
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.LEGLOCK_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.LEGLOCK_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.LEGLOCK_COLUMN_DESCRIPTION));

		return new LegLock(id, number, grade, description);
	}

	private ContentValues contentValuesFromLegLock(LegLock legLock){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.LEGLOCK_COLUMN_NUMBER, legLock.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, legLock.getGrade().getId());
		values.put(JitsuSQLiteHelper.LEGLOCK_COLUMN_DESCRIPTION, legLock.getDescription());

		return values;
	}

}
