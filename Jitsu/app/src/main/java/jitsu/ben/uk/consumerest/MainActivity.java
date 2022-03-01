package jitsu.ben.uk.consumerest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import jitsu.ben.uk.consumerest.activity.NewAnkleLock;
import jitsu.ben.uk.consumerest.activity.NewArmLock;
import jitsu.ben.uk.consumerest.activity.NewArmLockCounter;
import jitsu.ben.uk.consumerest.activity.NewFingerLock;
import jitsu.ben.uk.consumerest.activity.NewFingerLockApplication;
import jitsu.ben.uk.consumerest.activity.NewGroundWork;
import jitsu.ben.uk.consumerest.activity.NewKata;
import jitsu.ben.uk.consumerest.activity.NewKyusho;
import jitsu.ben.uk.consumerest.activity.NewLegLock;
import jitsu.ben.uk.consumerest.activity.NewLegLockCounter;
import jitsu.ben.uk.consumerest.activity.NewNeckLock;
import jitsu.ben.uk.consumerest.activity.NewThrow;
import jitsu.ben.uk.consumerest.activity.NewWristLock;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;
import jitsu.ben.uk.consumerest.database.JitsuSQLiteHelper;
import jitsu.ben.uk.consumerest.debug.DatabaseDebug;

import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_GRADE;
import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_LIST_TYPE;
import static jitsu.ben.uk.consumerest.constant.ListType.ALL_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.ANKLELOCK_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.ARMLOCKCOUNTER_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.ARMLOCK_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.FINGERLOCKAPPLICATION_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.FINGERLOCK_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.GROUNDWORK_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.KATA_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.KYUSHO_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.LEGLOCKCOUNTER_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.LEGLOCK_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.NECKLOCK_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.THROW_LIST;
import static jitsu.ben.uk.consumerest.constant.ListType.WRISTLOCK_LIST;


public class MainActivity extends AppCompatActivity {

	private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

		handleDrawClicks();

		getData();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void handleDrawClicks(){
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						// set item as selected to persist highlight
						menuItem.setChecked(true);
						// close drawer when item is tapped
						mDrawerLayout.closeDrawers();
						Intent intent;

						switch (menuItem.getItemId()) {
							case R.id.new_throw_menu_item:
								intent = new Intent(MainActivity.this, NewThrow.class);
								break;
							case R.id.new_anklelock_menu_item:
								intent = new Intent(MainActivity.this, NewAnkleLock.class);
								break;
							case R.id.new_armlock_menu_item:
								intent = new Intent(MainActivity.this, NewArmLock.class);
								break;
							case R.id.new_armlockcounter_menu_item:
								intent = new Intent(MainActivity.this, NewArmLockCounter.class);
								break;
							case R.id.new_fingerlock_menu_item:
								intent = new Intent(MainActivity.this, NewFingerLock.class);
								break;
							case R.id.new_fingerlockapplication_menu_item:
								intent = new Intent(MainActivity.this, NewFingerLockApplication.class);
								break;
							case R.id.new_groundwork_menu_item:
								intent = new Intent(MainActivity.this, NewGroundWork.class);
								break;
							case R.id.new_kata_menu_item:
								intent = new Intent(MainActivity.this, NewKata.class);
								break;
							case R.id.new_kyusho_menu_item:
								intent = new Intent(MainActivity.this, NewKyusho.class);
								break;
							case R.id.new_leglock_menu_item:
								intent = new Intent(MainActivity.this, NewLegLock.class);
								break;
							case R.id.new_leglockcounter_menu_item:
								intent = new Intent(MainActivity.this, NewLegLockCounter.class);
								break;
							case R.id.new_necklock_menu_item:
								intent = new Intent(MainActivity.this, NewNeckLock.class);
								break;
							case R.id.new_wristlock_menu_item:
								intent = new Intent(MainActivity.this, NewWristLock.class);
								break;
							default:
								intent = null;
						}

						if(intent!=null){
							startActivity(intent);
						}
						return true;
					}
				});
	}

    public void openAllByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_all);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, ALL_LIST);
        startActivity(intent);
    }

    public void openThrowsByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_throw);
        Grade grade = (Grade) gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, THROW_LIST);
        startActivity(intent);
    }

    public void openKyushoByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_kyusho);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, KYUSHO_LIST);
        startActivity(intent);
    }

    public void openKataByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_kata);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, KATA_LIST);
        startActivity(intent);
    }

    public void openGroundWorkByGrade(View view){
		Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_groundwork);
		Grade grade = (Grade)gradeSelector.getSelectedItem();

		Intent intent = new Intent(this, ListJitsuBeanActivity.class);
		intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
		intent.putExtra(INTENT_EXTRA_LIST_TYPE, GROUNDWORK_LIST);
		startActivity(intent);
	}

    public void openArmLockByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_armlock);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, ARMLOCK_LIST);
        startActivity(intent);
    }

    public void openWristLockByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_wristlock);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, WRISTLOCK_LIST);
        startActivity(intent);
    }

    public void openLegLockByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_leglock);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, LEGLOCK_LIST);
        startActivity(intent);
    }

    public void openAnkleLockByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_anklelock);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, ANKLELOCK_LIST);
        startActivity(intent);
    }

    public void openNeckLockByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_necklock);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, NECKLOCK_LIST);
        startActivity(intent);
    }

    public void openFingerLockByGrade(View view){
        Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_fingerlock);
        Grade grade = (Grade)gradeSelector.getSelectedItem();

        Intent intent = new Intent(this, ListJitsuBeanActivity.class);
        intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
        intent.putExtra(INTENT_EXTRA_LIST_TYPE, FINGERLOCK_LIST);
        startActivity(intent);
    }

	public void openArmLockCounterByGrade(View view){
		Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_armlockcounter);
		Grade grade = (Grade)gradeSelector.getSelectedItem();

		Intent intent = new Intent(this, ListJitsuBeanActivity.class);
		intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
		intent.putExtra(INTENT_EXTRA_LIST_TYPE, ARMLOCKCOUNTER_LIST);
		startActivity(intent);
	}

	public void openLegLockCounterByGrade(View view){
		Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_leglockcounter);
		Grade grade = (Grade)gradeSelector.getSelectedItem();

		Intent intent = new Intent(this, ListJitsuBeanActivity.class);
		intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
		intent.putExtra(INTENT_EXTRA_LIST_TYPE, LEGLOCKCOUNTER_LIST);
		startActivity(intent);
	}

	public void openFingerLockApplicationByGrade(View view){
		Spinner gradeSelector = (Spinner)findViewById(R.id.spinner_grade_fingerlockapplication);
		Grade grade = (Grade)gradeSelector.getSelectedItem();

		Intent intent = new Intent(this, ListJitsuBeanActivity.class);
		intent.putExtra(INTENT_EXTRA_GRADE, grade.getId());
		intent.putExtra(INTENT_EXTRA_LIST_TYPE, FINGERLOCKAPPLICATION_LIST);
		startActivity(intent);
	}

    public void exportDB(View view){
        DatabaseDebug debug = new DatabaseDebug(this.getBaseContext());
        debug.getDB();
    }

    public void importDB(View view){
        DatabaseDebug debug = new DatabaseDebug(this.getBaseContext());
        debug.importDB();
    }

    public void debug(View view){

		Intent intent = new Intent(this, NewThrow.class);
		startActivity(intent);

    }

    private void getData(){
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		List<Grade> allGrades = dataSource.getGrades();
		List<Grade> ankleLockGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.ANKLELOCK_TABLE_NAME);
		List<Grade> armLockGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.ARMLOCK_TABLE_NAME);
		List<Grade> armLockCounterGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.ARMLOCKCOUNTER_TABLE_NAME);
		List<Grade> fingerLockGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.FINGERLOCK_TABLE_NAME);
		List<Grade> fingerLockApplicationGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.FINGERLOCKAPPLICATION_TABLE_NAME);
		List<Grade> groundWorkGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.GROUNDWORK_TABLE_NAME);
		List<Grade> kataGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.KATA_TABLE_NAME);
		List<Grade> kyushoGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.KYUSHO_TABLE_NAME);
		List<Grade> legLockGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.LEGLOCK_TABLE_NAME);
		List<Grade> legLockCounterGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.LEGLOCKCOUNTER_TABLE_NAME);
		List<Grade> neckLockGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.NECKLOCK_TABLE_NAME);
		List<Grade> throwGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.THROW_TABLE_NAME);
		List<Grade> wristLockGrades = dataSource.getGradesForTechnique(JitsuSQLiteHelper.WRISTLOCK_TABLE_NAME);

		dataSource.close();

		Spinner allGradeSelector = (Spinner)findViewById(R.id.spinner_grade_all);
		Spinner ankleLockGradeSelector = (Spinner)findViewById(R.id.spinner_grade_anklelock);
		Spinner armLockGradeSelector = (Spinner)findViewById(R.id.spinner_grade_armlock);
		Spinner armLockCounterGradeSelector = (Spinner)findViewById(R.id.spinner_grade_armlockcounter);
		Spinner fingerLockGradeSelector = (Spinner)findViewById(R.id.spinner_grade_fingerlock);
		Spinner fingerLockApplicationGradeSelector = (Spinner)findViewById(R.id.spinner_grade_fingerlockapplication);
		Spinner groundWorkGradeSelector = (Spinner)findViewById(R.id.spinner_grade_groundwork);
		Spinner kataGradeSelector = (Spinner)findViewById(R.id.spinner_grade_kata);
		Spinner kyushoGradeSelector = (Spinner)findViewById(R.id.spinner_grade_kyusho);
		Spinner legLockGradeSelector = (Spinner)findViewById(R.id.spinner_grade_leglock);
		Spinner legLockCounterGradeSelector = (Spinner)findViewById(R.id.spinner_grade_leglockcounter);
		Spinner neckLockGradeSelector = (Spinner)findViewById(R.id.spinner_grade_necklock);
		Spinner throwGradeSelector = (Spinner)findViewById(R.id.spinner_grade_throw);
		Spinner wristLockGradeSelector = (Spinner)findViewById(R.id.spinner_grade_wristlock);

		Grade emptyGrade = new Grade(-1, "");
		allGrades.add(0, emptyGrade);
		ankleLockGrades.add(0, emptyGrade);
		armLockGrades.add(0, emptyGrade);
		armLockCounterGrades.add(0, emptyGrade);
		fingerLockGrades.add(0, emptyGrade);
		fingerLockApplicationGrades.add(0, emptyGrade);
		groundWorkGrades.add(0, emptyGrade);
		kataGrades.add(0, emptyGrade);
		kyushoGrades.add(0, emptyGrade);
		legLockGrades.add(0, emptyGrade);
		legLockCounterGrades.add(0, emptyGrade);
		neckLockGrades.add(0, emptyGrade);
		throwGrades.add(0, emptyGrade);
		wristLockGrades.add(0, emptyGrade);

		ArrayAdapter<Grade> gradeAllAdapter = new ArrayAdapter(throwGradeSelector.getContext(), android.R.layout.simple_spinner_item, allGrades);
		ArrayAdapter<Grade> gradeAnkleLockAdapter = new ArrayAdapter(ankleLockGradeSelector.getContext(), android.R.layout.simple_spinner_item, ankleLockGrades);
		ArrayAdapter<Grade> gradeArmLockAdapter = new ArrayAdapter(armLockGradeSelector.getContext(), android.R.layout.simple_spinner_item, armLockGrades);
		ArrayAdapter<Grade> gradeArmLockCounterAdapter = new ArrayAdapter(armLockCounterGradeSelector.getContext(), android.R.layout.simple_spinner_item, armLockCounterGrades);
		ArrayAdapter<Grade> gradeFingerLockAdapter = new ArrayAdapter(fingerLockGradeSelector.getContext(), android.R.layout.simple_spinner_item, fingerLockGrades);
		ArrayAdapter<Grade> gradeFingerLockApplicationAdapter = new ArrayAdapter(fingerLockApplicationGradeSelector.getContext(), android.R.layout.simple_spinner_item, fingerLockApplicationGrades);
		ArrayAdapter<Grade> gradeGroundWorkAdapter = new ArrayAdapter(groundWorkGradeSelector.getContext(), android.R.layout.simple_spinner_item, groundWorkGrades);
		ArrayAdapter<Grade> gradeKataAdapter = new ArrayAdapter(kataGradeSelector.getContext(), android.R.layout.simple_spinner_item, kataGrades);
		ArrayAdapter<Grade> gradeKyushoAdapter = new ArrayAdapter(kyushoGradeSelector.getContext(), android.R.layout.simple_spinner_item, kyushoGrades);
		ArrayAdapter<Grade> gradeLegLockAdapter = new ArrayAdapter(legLockGradeSelector.getContext(), android.R.layout.simple_spinner_item, legLockGrades);
		ArrayAdapter<Grade> gradeLegLockCounterAdapter = new ArrayAdapter(legLockCounterGradeSelector.getContext(), android.R.layout.simple_spinner_item, legLockCounterGrades);
		ArrayAdapter<Grade> gradeNeckLockAdapter = new ArrayAdapter(neckLockGradeSelector.getContext(), android.R.layout.simple_spinner_item, neckLockGrades);
		ArrayAdapter<Grade> gradeThrowAdapter = new ArrayAdapter(throwGradeSelector.getContext(), android.R.layout.simple_spinner_item, throwGrades);
		ArrayAdapter<Grade> gradeWristLockAdapter = new ArrayAdapter(wristLockGradeSelector.getContext(), android.R.layout.simple_spinner_item, wristLockGrades);

		allGradeSelector.setAdapter(gradeAllAdapter);
		ankleLockGradeSelector.setAdapter(gradeAnkleLockAdapter);
		armLockGradeSelector.setAdapter(gradeArmLockAdapter);
		armLockCounterGradeSelector.setAdapter(gradeArmLockCounterAdapter);
		fingerLockGradeSelector.setAdapter(gradeFingerLockAdapter);
		fingerLockApplicationGradeSelector.setAdapter(gradeFingerLockApplicationAdapter);
		groundWorkGradeSelector.setAdapter(gradeGroundWorkAdapter);
		kataGradeSelector.setAdapter(gradeKataAdapter);
		kyushoGradeSelector.setAdapter(gradeKyushoAdapter);
		legLockGradeSelector.setAdapter(gradeLegLockAdapter);
		legLockCounterGradeSelector.setAdapter(gradeLegLockCounterAdapter);
		neckLockGradeSelector.setAdapter(gradeNeckLockAdapter);
		throwGradeSelector.setAdapter(gradeThrowAdapter);
		wristLockGradeSelector.setAdapter(gradeWristLockAdapter);

	}

}


