package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.LegLockCounter;
import jitsu.ben.uk.consumerest.bean.Grade;

public class LegLockCounterDataSource extends JitsuDataSource {

	public LegLockCounterDataSource(Context context){
		super(context);
	}

	public List<LegLockCounter> getLegLockCounters() {
		String query = LEGLOCKCOUNTER_BASEQUERY + ORDERBYGRADE;

		return getLegLockCountersFromDB(query);
	}

	public List<LegLockCounter> getLegLockCounterssByGrade(int gradeId) {
		String query = LEGLOCKCOUNTER_BASEQUERY + " AND a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getLegLockCountersFromDB(query);
	}

	public LegLockCounter getLegLockCounterById(int id) {
		String query = LEGLOCKCOUNTER_BASEQUERY + " AND a." + JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_ID + " = " + id;

		return getLegLockCountersFromDB(query).get(0);
	}

	public void newLegLockCounter(LegLockCounter legLockCounter){
		ContentValues values = contentValuesFromLegLockCounter(legLockCounter);

		database.insert(JitsuSQLiteHelper.LEGLOCKCOUNTER_TABLE_NAME, null, values);
	}

	public void updateLegLockCounter(LegLockCounter legLockCounter){
		ContentValues values = contentValuesFromLegLockCounter(legLockCounter);

		database.update(JitsuSQLiteHelper.LEGLOCKCOUNTER_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{legLockCounter.getId().toString()});
	}

	private List<LegLockCounter> getLegLockCountersFromDB(String query) {
		List<LegLockCounter> legLockCounters = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			LegLockCounter legLockCounter = legLockCounterFromCursor(cursor);
			legLockCounters.add(legLockCounter);
		}
		// make sure to close the cursor
		cursor.close();
		return legLockCounters;
	}

	private LegLockCounter legLockCounterFromCursor(Cursor cursor) {
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_DESCRIPTION));

		return new LegLockCounter(id, number, grade, description);
	}

	private ContentValues contentValuesFromLegLockCounter(LegLockCounter legLockCounter){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_NUMBER, legLockCounter.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, legLockCounter.getGrade().getId());
		values.put(JitsuSQLiteHelper.LEGLOCK_COLUMN_DESCRIPTION, legLockCounter.getDescription());

		return values;
	}
}
