package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.ArmLock;
import jitsu.ben.uk.consumerest.bean.Grade;

public class ArmLockDataSource extends JitsuDataSource {

	public ArmLockDataSource(Context context){
		super(context);
	}

	public List<ArmLock> getArmLocks() {
		String query = ARMLOCK_BASEQUERY + ORDERBYGRADE;

		return getArmLocksFromDB(query);
	}

	public List<ArmLock> getArmLocksByGrade(int gradeId) {
		String query = ARMLOCK_BASEQUERY + " AND a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getArmLocksFromDB(query);
	}

	public ArmLock getArmLockById(int id) {
		String query = ARMLOCK_BASEQUERY + " AND a." + JitsuSQLiteHelper.ARMLOCK_COLUMN_ID + " = " + id;

		return getArmLocksFromDB(query).get(0);
	}

	public void newArmLock(ArmLock armLock){
		ContentValues values = contentValuesFromArmLock(armLock);

		database.insert(JitsuSQLiteHelper.ARMLOCK_TABLE_NAME, null, values);
	}

	public void updateArmLock(ArmLock armLock){
		ContentValues values = contentValuesFromArmLock(armLock);

		database.update(JitsuSQLiteHelper.ARMLOCK_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{armLock.getId().toString()});
	}

	private List<ArmLock> getArmLocksFromDB(String query) {
		List<ArmLock> armLocks = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			ArmLock armLock = armLockFromCursor(cursor);
			armLocks.add(armLock);
		}
		// make sure to close the cursor
		cursor.close();
		return armLocks;
	}

	private ArmLock armLockFromCursor(Cursor cursor) {
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.ARMLOCK_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.ARMLOCK_COLUMN_NUMBER));
		String entrance = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.ARMLOCK_COLUMN_ENTRANCE));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.ARMLOCK_COLUMN_DESCRIPTION));

		return new ArmLock(id, number, grade, entrance, description);
	}

	private ContentValues contentValuesFromArmLock(ArmLock armLock){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.ARMLOCK_COLUMN_NUMBER, armLock.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, armLock.getGrade().getId());
		values.put(JitsuSQLiteHelper.ARMLOCK_COLUMN_ENTRANCE, armLock.getEntrance());
		values.put(JitsuSQLiteHelper.ARMLOCK_COLUMN_DESCRIPTION, armLock.getDescription());

		return values;
	}
}
