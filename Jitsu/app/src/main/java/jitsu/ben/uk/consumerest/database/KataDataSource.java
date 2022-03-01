package jitsu.ben.uk.consumerest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.Kata;
import jitsu.ben.uk.consumerest.bean.KataStep;

public class KataDataSource extends JitsuDataSource {

	public KataDataSource (Context context){
		super(context);
	}

	public List<Kata> getKatas() {
		String query = KATA_BASEQUERY + ORDERBYGRADE;

		return getKatasFromDB(query);
	}

	public List<Kata> getKatasByGrade(int gradeId) {
		String query = KATA_BASEQUERY + " AND k." + JitsuSQLiteHelper.FOREIGN_GRADE_ID + " = " + gradeId + ORDERBYGRADE;

		return getKatasFromDB(query);
	}

	public Kata getKataByid(int id) {
		String query = KATA_BASEQUERY + " AND k." + JitsuSQLiteHelper.KATA_COLUMN_ID + " = " + id;

		return getKatasFromDB(query).get(0);
	}

	public void newKata(Kata kata){
		ContentValues kataValues = contentValuesFromKata(kata);

		long kataId = database.insert(JitsuSQLiteHelper.KATA_TABLE_NAME, null, kataValues);

		createKataSteps(kata, kataId);
	}

	public void updateKata(Kata kata){
		deleteKataStepsForKata(kata);

		ContentValues kataValues = contentValuesFromKata(kata);

		database.update(JitsuSQLiteHelper.KATA_TABLE_NAME, kataValues, WHERE_ID_EQUALS, new String[]{kata.getId().toString()});

		createKataSteps(kata, kata.getId());
	}

	public void createKataSteps(Kata kata, long kataId){
		List<KataStep> kataSteps = kata.getSteps();

		for(KataStep step: kataSteps){
			ContentValues stepValues = contentValuesFromKataStep(step, kataId);

			database.insert(JitsuSQLiteHelper.KATASTEP_TABLE_NAME, null, stepValues);
		}
	}

	public List<KataStep> getKataStepsByKata(int kata_id) {
		String query = KATASTEP_BASEQUERY + kata_id;

		return getKataStepsFromDB(query);
	}

	private List<Kata> getKatasFromDB(String query) {
		List<Kata> katas = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			Kata kata = kataFromCursor(cursor);
			katas.add(kata);
		}
		// make sure to close the cursor
		cursor.close();

		for(Kata kata : katas){
			kata.setKataSteps(getKataStepsByKata(kata.getId()));
		}
		return katas;
	}

	private List<KataStep> getKataStepsFromDB(String query) {
		List<KataStep> kataSteps = new ArrayList<>();

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			KataStep kataStep = kataStepFromCursor(cursor);
			kataSteps.add(kataStep);
		}
		// make sure to close the cursor
		cursor.close();
		return kataSteps;
	}

	private void deleteKataStepsForKata (Kata kata){
		database.delete(JitsuSQLiteHelper.KATASTEP_TABLE_NAME, WHERE_FOREIGN_KATA_ID_EQUALS, new String[]{kata.getId().toString()});
	}

	private Kata kataFromCursor(Cursor cursor) {
		Grade grade = gradeFromCursor(cursor);

		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.KATA_COLUMN_ID));
		String name = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.KATA_COLUMN_NAME));
		String translation = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.KATA_COLUMN_TRANSLATION));

		return new Kata(id, name, grade, translation, null);
	}

	private KataStep kataStepFromCursor(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.KATASTEP_COLUMN_ID));
		int stepNumber = cursor.getInt(cursor.getColumnIndex(JitsuSQLiteHelper.KATASTEP_COLUMN_STEP_NUMBER));
		String description = cursor.getString(cursor.getColumnIndex(JitsuSQLiteHelper.KATASTEP_COLUMN_DESCRIPTION));

		return new KataStep(id, stepNumber, description);
	}

	private ContentValues contentValuesFromKata(Kata kata){
		ContentValues kataValues = new ContentValues();

		kataValues.put(JitsuSQLiteHelper.KATA_COLUMN_NAME, kata.getName());
		kataValues.put(JitsuSQLiteHelper.KATA_COLUMN_TRANSLATION, kata.getTranslation());
		kataValues.put(JitsuSQLiteHelper.FOREIGN_GRADE_ID, kata.getGrade().getId());

		return kataValues;
	}

	private ContentValues contentValuesFromKataStep(KataStep step, long kataId){
		ContentValues stepValues = new ContentValues();

		stepValues.put(JitsuSQLiteHelper.KATASTEP_COLUMN_STEP_NUMBER, step.getStepNumber());
		stepValues.put(JitsuSQLiteHelper.KATASTEP_COLUMN_DESCRIPTION, step.getDescription());
		stepValues.put(JitsuSQLiteHelper.KATASTEP_COLUMN_KATA_ID, kataId);

		return stepValues;
	}

	private String makePlaceholders(int len) {
		if (len < 1) {
			// It will lead to an invalid query anyway ..
			throw new RuntimeException("No placeholders");
		} else {
			StringBuilder sb = new StringBuilder(len * 2 - 1);
			sb.append("?");
			for (int i = 1; i < len; i++) {
				sb.append(",?");
			}
			return sb.toString();
		}
	}
}
