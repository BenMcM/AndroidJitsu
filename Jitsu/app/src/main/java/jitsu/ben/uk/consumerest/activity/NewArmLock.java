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
import jitsu.ben.uk.consumerest.bean.ArmLock;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.ArmLockDataSource;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;


public class NewArmLock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_armlock);

        populateGradeSpinner();
    }

    public void save(View view){
        TextView numberView = (TextView) findViewById(R.id.new_armlock_number);
		Spinner gradeSelector = (Spinner) findViewById(R.id.new_armlock_grade);
        TextView entranceView = (TextView) findViewById(R.id.new_armlock_entrance);
        TextView descriptionView = (TextView) findViewById(R.id.new_armlock_description);

        String number = numberView.getText().toString();
		String entrance = entranceView.getText().toString();
		String description = descriptionView.getText().toString();


		Grade grade = (Grade)gradeSelector.getSelectedItem();

        ArmLock armLock = new ArmLock(number, grade, entrance, description);

		ArmLockDataSource dataSource = new ArmLockDataSource(this.getBaseContext());
		dataSource.newArmLock(armLock);
		dataSource.close();

		Toast.makeText(this, R.string.armlock_created, Toast.LENGTH_SHORT).show();
    }

    private void populateGradeSpinner(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();


		Spinner gradeSelector = (Spinner) findViewById(R.id.new_armlock_grade);
		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(gradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		gradeSelector.setAdapter(gradeAllAdapter);

	}

}
