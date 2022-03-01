package jitsu.ben.uk.consumerest.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;

public class JitsuDataSource {
	protected final String GRADE_ID = "g_id";
	protected final String GRADE_NAME = "g_name";
	protected final String GRADE_COLUMNS = " g." + JitsuSQLiteHelper.GRADE_COLUMN_ID + " as " + GRADE_ID + ", g." + JitsuSQLiteHelper.GRADE_COLUMN_NAME + " as " + GRADE_NAME + ", g." + JitsuSQLiteHelper.GRADE_COLUMN_ORDERING;

	protected final String GRADE_BASEQUERY = "SELECT " + GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g";
	protected final String ANKLELOCK_BASEQUERY = "SELECT a." + JitsuSQLiteHelper.ANKLELOCK_COLUMN_ID + ", a." + JitsuSQLiteHelper.ANKLELOCK_COLUMN_NUMBER + ", a." + JitsuSQLiteHelper.ANKLELOCK_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.ANKLELOCK_TABLE_NAME + " a, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String ARMLOCK_BASEQUERY = "SELECT a." + JitsuSQLiteHelper.ARMLOCK_COLUMN_ID + ", a." + JitsuSQLiteHelper.ARMLOCK_COLUMN_NUMBER + ", a." + JitsuSQLiteHelper.ARMLOCK_COLUMN_ENTRANCE + ", a." + JitsuSQLiteHelper.ARMLOCK_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.ARMLOCK_TABLE_NAME + " a, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String ARMLOCKCOUNTER_BASEQUERY = "SELECT a." + JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_ID + ", a." + JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_NUMBER + ", a." + JitsuSQLiteHelper.ARMLOCKCOUNTER_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.ARMLOCKCOUNTER_TABLE_NAME + " a, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String FINGERLOCK_BASEQUERY = "SELECT f." + JitsuSQLiteHelper.FINGERLOCK_COLUMN_ID + ", f." + JitsuSQLiteHelper.FINGERLOCK_COLUMN_NUMBER + ", f." + JitsuSQLiteHelper.FINGERLOCK_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.FINGERLOCK_TABLE_NAME + " f, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE f." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String FINGERLOCKAPPLICATION_BASEQUERY = "SELECT a." + JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_ID + ", a." + JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_NUMBER + ", a." + JitsuSQLiteHelper.FINGERLOCKAPPLICATION_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.FINGERLOCKAPPLICATION_TABLE_NAME + " a, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String GROUNDWORK_BASEQUERY = "SELECT a." + JitsuSQLiteHelper.GROUNDWORK_COLUMN_ID + ", a." + JitsuSQLiteHelper.GROUNDWORK_COLUMN_NAME + ", a." + JitsuSQLiteHelper.GROUNDWORK_COLUMN_TRANSLATION
			+ ", a." + JitsuSQLiteHelper.GROUNDWORK_COLUMN_STARTPOSITION + ", a." + JitsuSQLiteHelper.GROUNDWORK_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.GROUNDWORK_TABLE_NAME + " a, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String KATA_BASEQUERY = "SELECT k." + JitsuSQLiteHelper.KATA_COLUMN_ID + ", k." + JitsuSQLiteHelper.KATA_COLUMN_NAME + ", k." + JitsuSQLiteHelper.KATA_COLUMN_TRANSLATION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.KATA_TABLE_NAME + " k, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE k." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String KATASTEP_BASEQUERY = "SELECT " + JitsuSQLiteHelper.KATASTEP_COLUMN_ID + ", " + JitsuSQLiteHelper.KATASTEP_COLUMN_STEP_NUMBER + ", " + JitsuSQLiteHelper.KATASTEP_COLUMN_DESCRIPTION
			+ " FROM " + JitsuSQLiteHelper.KATASTEP_TABLE_NAME + " WHERE " + JitsuSQLiteHelper.KATASTEP_COLUMN_KATA_ID + " = ";
	protected final String KYUSHU_BASEQUERY = "SELECT k." + JitsuSQLiteHelper.KYUSHO_COLUMN_ID + ", k." + JitsuSQLiteHelper.KYUSHO_COLUMN_NUMBER + ", k." + JitsuSQLiteHelper.KYUSHO_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.KYUSHO_TABLE_NAME + " k, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE k." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String LEGLOCK_BASEQUERY = "SELECT l." + JitsuSQLiteHelper.LEGLOCK_COLUMN_ID + ", l." + JitsuSQLiteHelper.LEGLOCK_COLUMN_NUMBER + ", l." + JitsuSQLiteHelper.LEGLOCK_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.LEGLOCK_TABLE_NAME + " l, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE l." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String LEGLOCKCOUNTER_BASEQUERY = "SELECT a." + JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_ID + ", a." + JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_NUMBER + ", a." + JitsuSQLiteHelper.LEGLOCKCOUNTER_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.LEGLOCKCOUNTER_TABLE_NAME + " a, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE a." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String NECKLOCK_BASEQUERY = "SELECT n." + JitsuSQLiteHelper.NECKLOCK_COLUMN_ID + ", n." + JitsuSQLiteHelper.NECKLOCK_COLUMN_NUMBER + ", n." + JitsuSQLiteHelper.NECKLOCK_COLUMN_DESCRIPTION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.NECKLOCK_TABLE_NAME + " n, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE n." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String THROW_BASEQUERY = "SELECT t." + JitsuSQLiteHelper.THROW_COLUMN_ID + ", t." + JitsuSQLiteHelper.THROW_COLUMN_NAME + ", t." + JitsuSQLiteHelper.THROW_COLUMN_TRANSLATION
			+ ", t." + JitsuSQLiteHelper.THROW_COLUMN_DESCRIPTION + ", t." + JitsuSQLiteHelper.THROW_COLUMN_ENTRANCE + ", t." + JitsuSQLiteHelper.THROW_COLUMN_VARIATION + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.THROW_TABLE_NAME + " t, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE t." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;
	protected final String WRISTLOCK_BASEQUERY = "SELECT w." + JitsuSQLiteHelper.WRISTLOCK_COLUMN_ID + ", w." + JitsuSQLiteHelper.WRISTLOCK_COLUMN_NUMBER + ", w." + JitsuSQLiteHelper.WRISTLOCK_COLUMN_DESCRIPTION + ", w." + JitsuSQLiteHelper.WRISTLOCK_COLUMN_ENTRANCE + ", "
			+ GRADE_COLUMNS + " FROM " + JitsuSQLiteHelper.WRISTLOCK_TABLE_NAME + " w, " + JitsuSQLiteHelper.GRADE_TABLE_NAME + " g"
			+ " WHERE w." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + GRADE_ID;

	protected final String WHERE_ID_EQUALS = JitsuSQLiteHelper.COLUMN_ID + "=?";
	protected final String WHERE_FOREIGN_KATA_ID_EQUALS = JitsuSQLiteHelper.KATASTEP_COLUMN_KATA_ID + "=?";

	protected final String ORDERBYGRADE = " ORDER BY g." + JitsuSQLiteHelper.GRADE_COLUMN_ORDERING;

	protected SQLiteDatabase database;
	protected JitsuSQLiteHelper dbHelper;


	public JitsuDataSource(Context context) {
		dbHelper = new JitsuSQLiteHelper(context);
		open();
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Grade> getGrades() {
		String query = GRADE_BASEQUERY + ORDERBYGRADE;

		return getGradesFromDB(query);
	}

	public List<Grade> getGradesForTechnique(String techniqueTable) {
		String query = GRADE_BASEQUERY + ", " + techniqueTable + " x "
				+ "WHERE " + GRADE_ID + " = x." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " GROUP BY " + GRADE_ID + ORDERBYGRADE;


		return getGradesFromDB(query);
	}

	private List<Grade> getGradesFromDB(String query) {
		List<Grade> grades = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			Grade grade = gradeFromCursor(cursor);
			grades.add(grade);
		}
		// make sure to close the cursor
		cursor.close();
		return grades;
	}

	protected Grade gradeFromCursor(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex(GRADE_ID));
		String name = cursor.getString(cursor.getColumnIndex(GRADE_NAME));
		int ordering = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.GRADE_COLUMN_ORDERING));
		return new Grade(id, name, ordering);
	}

}
