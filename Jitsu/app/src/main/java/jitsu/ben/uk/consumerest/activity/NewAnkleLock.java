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
import jitsu.ben.uk.consumerest.bean.AnkleLock;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.AnkleLockDataSource;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;


public class NewAnkleLock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_anklelock);

        populateGradeSpinner();
    }

    public void save(View view){
        TextView numberView = (TextView) findViewById(R.id.new_anklelock_number);
		Spinner gradeSelector = (Spinner) findViewById(R.id.new_anklelock_grade);
        TextView descriptionView = (TextView) findViewById(R.id.new_anklelock_description);

        String number = numberView.getText().toString();
		String description = descriptionView.getText().toString();


		Grade grade = (Grade)gradeSelector.getSelectedItem();

        AnkleLock ankleLock = new AnkleLock(number, grade, description);

		AnkleLockDataSource dataSource = new AnkleLockDataSource(this.getBaseContext());
		dataSource.newAnkleLock(ankleLock);
		dataSource.close();

		Toast.makeText(this, R.string.anklelock_created, Toast.LENGTH_SHORT).show();
    }

    private void populateGradeSpinner(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();


		Spinner gradeSelector = (Spinner) findViewById(R.id.new_anklelock_grade);
		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(gradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		gradeSelector.setAdapter(gradeAllAdapter);

	}

}
