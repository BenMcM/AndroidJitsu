package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.ArmLockCounter;
import jitsu.ben.uk.consumerest.bean.Grade;

public class ArmLockCounterDataSource extends JitsuDataSource {

	public ArmLockCounterDataSource(Context context){
		super(context);
	}

	public List<ArmLockCounter> getArmLockCounters() {
		String query = ARMLOCKCOUNTER_BASEQUERY + ORDERBYGRADE;

		return getArmLockCountersFromDB(query);
	}

	public List<ArmLockCounter> getArmLockCounterssByGrade(int gradeId) {
		String query = ARMLOCKCOUNTER_BASEQUERY + " AND a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getArmLockCountersFromDB(query);
	}

	public ArmLockCounter getArmLockCounterById(int id) {
		String query = ARMLOCKCOUNTER_BASEQUERY + " AND a." + JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_ID + " = " + id;

		return getArmLockCountersFromDB(query).get(0);
	}

	public void newArmLockCounter(ArmLockCounter armLockCounter){
		ContentValues values = contentValuesFromArmLockCounter(armLockCounter);

		database.insert(JitsuSQLiteHelper.ARMLOCKCOUNTER_TABLE_NAME, null, values);
	}

	public void updateArmLockCounter(ArmLockCounter armLockCounter){
		ContentValues values = contentValuesFromArmLockCounter(armLockCounter);

		database.update(JitsuSQLiteHelper.ARMLOCKCOUNTER_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{armLockCounter.getId().toString()});
	}

	private List<ArmLockCounter> getArmLockCountersFromDB(String query) {
		List<ArmLockCounter> armLockCounters = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			ArmLockCounter armLockCounter = armLockCounterFromCursor(cursor);
			armLockCounters.add(armLockCounter);
		}
		// make sure to close the cursor
		cursor.close();
		return armLockCounters;
	}

	private ArmLockCounter armLockCounterFromCursor(Cursor cursor) {
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_DESCRIPTION));

		return new ArmLockCounter(id, number, grade, description);
	}

	private ContentValues contentValuesFromArmLockCounter(ArmLockCounter armLockCounter){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_NUMBER, armLockCounter.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, armLockCounter.getGrade().getId());
		values.put(JitsuSQLiteHelper.ARMLOCK_COLUMN_DESCRIPTION, armLockCounter.getDescription());

		return values;
	}
}
