package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.FingerLockApplication;
import jitsu.ben.uk.consumerest.bean.Grade;

public class FingerLockApplicationDataSource extends JitsuDataSource {

	public FingerLockApplicationDataSource(Context context){
		super(context);
	}

	public List<FingerLockApplication> getFingerLockApplications() {
		String query = FINGERLOCKAPPLICATION_BASEQUERY + ORDERBYGRADE;

		return getFingerLockApplicationsFromDB(query);
	}

	public List<FingerLockApplication> getFingerLockApplicationsByGrade(int gradeId) {
		String query = FINGERLOCKAPPLICATION_BASEQUERY + " AND a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getFingerLockApplicationsFromDB(query);
	}

	public FingerLockApplication getFingerLockApplicationById(int id) {
		String query = FINGERLOCKAPPLICATION_BASEQUERY + " AND a." + JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_ID + " = " + id;

		return getFingerLockApplicationsFromDB(query).get(0);
	}

	public void newFingerLockApplication(FingerLockApplication fingerLockApplication){
		ContentValues values = contentValuesFromFingerLockApplication(fingerLockApplication);

		database.insert(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_TABLE_NAME, null, values);
	}

	public void updateFingerLockApplication(FingerLockApplication fingerLockApplication){
		ContentValues values = contentValuesFromFingerLockApplication(fingerLockApplication);

		database.update(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{fingerLockApplication.getId().toString()});
	}

	private List<FingerLockApplication> getFingerLockApplicationsFromDB(String query) {
		List<FingerLockApplication> fingerLockApplications = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			FingerLockApplication fingerLockApplication = fingerLockApplicationFromCursor(cursor);
			fingerLockApplications.add(fingerLockApplication);
		}
		// make sure to close the cursor
		cursor.close();
		return fingerLockApplications;
	}

	private FingerLockApplication fingerLockApplicationFromCursor(Cursor cursor) {
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_ID));
		String number = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_DESCRIPTION));

		return new FingerLockApplication(id, number, grade, description);
	}

	private ContentValues contentValuesFromFingerLockApplication(FingerLockApplication fingerLockApplication){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_NUMBER, fingerLockApplication.getNumber());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, fingerLockApplication.getGrade().getId());
		values.put(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_DESCRIPTION, fingerLockApplication.getDescription());

		return values;
	}
}
