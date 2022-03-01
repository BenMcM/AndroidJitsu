package jitsu.ben.uk.consumerest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import jitsu.ben.uk.consumerest.R;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.Kata;
import jitsu.ben.uk.consumerest.bean.KataStep;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;
import jitsu.ben.uk.consumerest.database.KataDataSource;


public class NewKata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_kata);

        populateGradeSpinner();
    }

    public void save(View view){
        TextView nameView = (TextView) findViewById(R.id.new_kata_name);
		TextView translationView = (TextView) findViewById(R.id.new_kata_translation);
		Spinner gradeSelector = (Spinner) findViewById(R.id.new_kata_grade);
        TextView descriptionView = (TextView) findViewById(R.id.new_kata_description);

        String number = nameView.getText().toString();
		String translation = translationView.getText().toString();
		String description = descriptionView.getText().toString();

		Grade grade = (Grade)gradeSelector.getSelectedItem();

		List<KataStep> kataSteps = KataStep.getStepsFromString(description, System.getProperty("line.separator"));

        Kata kata = new Kata(number, grade, translation, kataSteps);

		KataDataSource dataSource = new KataDataSource(this.getBaseContext());
		dataSource.newKata(kata);
		dataSource.close();

		Toast.makeText(this, R.string.kata_created, Toast.LENGTH_SHORT).show();
    }

    private void populateGradeSpinner(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();


		Spinner gradeSelector = (Spinner) findViewById(R.id.new_kata_grade);
		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(gradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		gradeSelector.setAdapter(gradeAllAdapter);

	}

}
