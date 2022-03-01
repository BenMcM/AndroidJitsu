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
import jitsu.ben.uk.consumerest.bean.LegLock;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.LegLockDataSource;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;


public class NewLegLock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_leglock);

        populateGradeSpinner();
    }

    public void save(View view){
        TextView numberView = (TextView) findViewById(R.id.new_leglock_number);
		Spinner gradeSelector = (Spinner) findViewById(R.id.new_leglock_grade);
        TextView descriptionView = (TextView) findViewById(R.id.new_leglock_description);

        String number = numberView.getText().toString();
		String description = descriptionView.getText().toString();


		Grade grade = (Grade)gradeSelector.getSelectedItem();

        LegLock legLock = new LegLock(number, grade, description);

		LegLockDataSource dataSource = new LegLockDataSource(this.getBaseContext());
		dataSource.newLegLock(legLock);
		dataSource.close();

		Toast.makeText(this, R.string.leglock_created, Toast.LENGTH_SHORT).show();
    }

    private void populateGradeSpinner(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();


		Spinner gradeSelector = (Spinner) findViewById(R.id.new_leglock_grade);
		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(gradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		gradeSelector.setAdapter(gradeAllAdapter);

	}

}
