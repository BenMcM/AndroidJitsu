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
import jitsu.ben.uk.consumerest.bean.GroundWork;
import jitsu.ben.uk.consumerest.database.GroundWorkDataSource;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;


public class NewGroundWork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_groundwork);

        populateGradeSpinner();
    }

    public void save(View view){
        TextView nameView = (TextView) findViewById(R.id.new_groundwork_name);
        TextView translationView = (TextView) findViewById(R.id.new_groundwork_translation);
		Spinner gradeSelector = (Spinner) findViewById(R.id.new_groundwork_grade);
        TextView startPositionView = (TextView) findViewById(R.id.new_groundwork_startposition);
        TextView descriptionView = (TextView) findViewById(R.id.new_groundwork_description);

        String name = nameView.getText().toString();
        String translation = translationView.getText().toString();
		String startPosition = startPositionView.getText().toString();
		String description = descriptionView.getText().toString();


		Grade grade = (Grade)gradeSelector.getSelectedItem();

        GroundWork groundWork = new GroundWork(name, translation, grade, startPosition, description);

		GroundWorkDataSource dataSource = new GroundWorkDataSource(this.getBaseContext());
		dataSource.newGroundWork(groundWork);
		dataSource.close();

		Toast.makeText(this, R.string.groundwork_created, Toast.LENGTH_SHORT).show();
    }

    private void populateGradeSpinner(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();


		Spinner gradeSelector = (Spinner) findViewById(R.id.new_groundwork_grade);
		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(gradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		gradeSelector.setAdapter(gradeAllAdapter);

	}

}
