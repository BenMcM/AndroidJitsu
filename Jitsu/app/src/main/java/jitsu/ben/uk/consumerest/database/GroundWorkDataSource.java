package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.GroundWork;

public class GroundWorkDataSource extends JitsuDataSource {

	public GroundWorkDataSource(Context context){
		super(context);
	}

	public List<GroundWork> getGroundWorks() {
		String query = GROUNDWORK_BASEQUERY + ORDERBYGRADE;

		return getGroundWorksFromDB(query);
	}

	public List<GroundWork> getGroundWorksByGrade(int gradeId) {
		String query = GROUNDWORK_BASEQUERY + " AND a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getGroundWorksFromDB(query);
	}

	public GroundWork getGroundWorkById(int id) {
		String query = GROUNDWORK_BASEQUERY + " AND a." + JitsuSQLiteHelper.GROUNDWORK_COLUMN_ID + " = " + id;

		return getGroundWorksFromDB(query).get(0);
	}

	public void newGroundWork(GroundWork groundWork){
		ContentValues values = contentValuesFromGroundWork(groundWork);

		database.insert(JitsuSQLiteHelper.GROUNDWORK_TABLE_NAME, null, values);
	}

	public void updateGroundWork(GroundWork groundWork){
		ContentValues values = contentValuesFromGroundWork(groundWork);

		database.update(JitsuSQLiteHelper.GROUNDWORK_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{groundWork.getId().toString()});

	}

	private List<GroundWork> getGroundWorksFromDB(String query){
		List<GroundWork> groundWorkList = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()){
			GroundWork groundWork = groundWorkFromCursor(cursor);
			groundWorkList.add(groundWork);
		}
		// make sure to close the cursor
		cursor.close();
		return groundWorkList;
	}

	private GroundWork groundWorkFromCursor(Cursor cursor){
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.GROUNDWORK_COLUMN_ID));
		String name = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.GROUNDWORK_COLUMN_NAME));
		String translation = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.GROUNDWORK_COLUMN_TRANSLATION));
		String startPosition = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.GROUNDWORK_COLUMN_STARTPOSITION));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.GROUNDWORK_COLUMN_DESCRIPTION));

		return new GroundWork(id, name, translation, grade, startPosition, description);
	}

	private ContentValues contentValuesFromGroundWork(GroundWork groundWork){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.GROUNDWORK_COLUMN_NAME, groundWork.getName());
		values.put(JitsuSQLiteHelper.GROUNDWORK_COLUMN_TRANSLATION, groundWork.getTranslation());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, groundWork.getGrade().getId());
		values.put(JitsuSQLiteHelper.GROUNDWORK_COLUMN_STARTPOSITION, groundWork.getStartPosition());
		values.put(JitsuSQLiteHelper.GROUNDWORK_COLUMN_DESCRIPTION, groundWork.getDescription());

		return values;
	}
}
