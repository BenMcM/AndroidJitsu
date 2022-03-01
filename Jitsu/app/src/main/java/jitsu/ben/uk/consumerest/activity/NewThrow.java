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
import jitsu.ben.uk.consumerest.bean.Throw;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;
import jitsu.ben.uk.consumerest.database.ThrowsDataSource;


public class NewThrow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_throw);

        populateGradeSpinner();
    }

    public void save(View view){
        TextView nameView = (TextView) findViewById(R.id.new_throw_name);
        TextView translationView = (TextView) findViewById(R.id.new_throw_translation);
		Spinner gradeSelector = (Spinner) findViewById(R.id.new_throw_grade);
        TextView entranceView = (TextView) findViewById(R.id.new_throw_entrance);
        TextView descriptionView = (TextView) findViewById(R.id.new_throw_description);

        String name = nameView.getText().toString();
        String translation = translationView.getText().toString();
		String entrance = entranceView.getText().toString();
		String description = descriptionView.getText().toString();


		Grade grade = (Grade)gradeSelector.getSelectedItem();

        Throw jitsuThrow = new Throw(name, translation, grade, entrance, description, null);

		ThrowsDataSource dataSource = new ThrowsDataSource(this.getBaseContext());
		dataSource.newThrow(jitsuThrow);
		dataSource.close();

		Toast.makeText(this, R.string.throw_created, Toast.LENGTH_SHORT).show();
    }

    private void populateGradeSpinner(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();


		Spinner gradeSelector = (Spinner) findViewById(R.id.new_throw_grade);
		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(gradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		gradeSelector.setAdapter(gradeAllAdapter);

	}

}
