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
import jitsu.ben.uk.consumerest.bean.FingerLock;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.FingerLockDataSource;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;


public class NewFingerLock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fingerlock);

        populateGradeSpinner();
    }

    public void save(View view){
        TextView numberView = (TextView) findViewById(R.id.new_fingerlock_number);
		Spinner gradeSelector = (Spinner) findViewById(R.id.new_fingerlock_grade);
        TextView descriptionView = (TextView) findViewById(R.id.new_fingerlock_description);

        String number = numberView.getText().toString();
		String description = descriptionView.getText().toString();


		Grade grade = (Grade)gradeSelector.getSelectedItem();

        FingerLock fingerLock = new FingerLock(number, grade, description);

		FingerLockDataSource dataSource = new FingerLockDataSource(this.getBaseContext());
		dataSource.newFingerLock(fingerLock);
		dataSource.close();

		Toast.makeText(this, R.string.fingerlock_created, Toast.LENGTH_SHORT).show();
    }

    private void populateGradeSpinner(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();


		Spinner gradeSelector = (Spinner) findViewById(R.id.new_fingerlock_grade);
		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(gradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		gradeSelector.setAdapter(gradeAllAdapter);

	}

}
