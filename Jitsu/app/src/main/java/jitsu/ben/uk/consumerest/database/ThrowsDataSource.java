package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.Throw;

public class ThrowsDataSource extends JitsuDataSource {

	public ThrowsDataSource(Context context){
		super(context);
	}

	public List<Throw> getThrows() {
		String query = THROW_BASEQUERY + ORDERBYGRADE;

		return getThrowsFromDB(query);
	}

	public List<Throw> getThrowsByGrade(int gradeId) {
		String query = THROW_BASEQUERY + " AND t." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getThrowsFromDB(query);
	}

	public Throw getThrowById(int id) {
		String query = THROW_BASEQUERY + " AND t." + JitsuSQLiteHelper.THROW_COLUMN_ID + " = " + id;

		return getThrowsFromDB(query).get(0);
	}

	public void newThrow(Throw jitsuThrow){
		ContentValues values = contentValuesFromThrow(jitsuThrow);

		database.insert(JitsuSQLiteHelper.THROW_TABLE_NAME, null, values);
	}

	public void updateThrow(Throw jitsuThrow){
		ContentValues values = contentValuesFromThrow(jitsuThrow);

		database.update(JitsuSQLiteHelper.THROW_TABLE_NAME, values, WHERE_ID_EQUALS, new String[]{jitsuThrow.getId().toString()});

	}

	private List<Throw> getThrowsFromDB(String query){
		List<Throw> throwList = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()){
			Throw jitsuThrow = throwFromCursor(cursor);
			throwList.add(jitsuThrow);
		}
		// make sure to close the cursor
		cursor.close();
		return throwList;
	}

	private Throw throwFromCursor(Cursor cursor){
		Grade grade = gradeFromCursor(cursor);
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.THROW_COLUMN_ID));
		String name = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.THROW_COLUMN_NAME));
		String translation = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.THROW_COLUMN_TRANSLATION));
		String entrance = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.THROW_COLUMN_ENTRANCE));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.THROW_COLUMN_DESCRIPTION));
		String variation = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.THROW_COLUMN_VARIATION));

		return new Throw(id, name, translation, grade, entrance, description, variation);
	}

	private ContentValues contentValuesFromThrow(Throw jitsuThrow){
		ContentValues values = new ContentValues();

		values.put(JitsuSQLiteHelper.THROW_COLUMN_NAME, jitsuThrow.getName());
		values.put(JitsuSQLiteHelper.THROW_COLUMN_TRANSLATION, jitsuThrow.getTranslation());
		values.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, jitsuThrow.getGrade().getId());
		values.put(JitsuSQLiteHelper.THROW_COLUMN_ENTRANCE, jitsuThrow.getEntrance());
		values.put(JitsuSQLiteHelper.THROW_COLUMN_DESCRIPTION, jitsuThrow.getDescription());
		values.put(JitsuSQLiteHelper.THROW_COLUMN_VARIATION, jitsuThrow.getVariation());

		return values;
	}
}
