package jitsu.ben.uk.consumerest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jitsu.ben.uk.consumerest.Adapter.JitsuArrayAdapter;
import jitsu.ben.uk.consumerest.bean.JitsuBean;
import jitsu.ben.uk.consumerest.constant.ListType;
import jitsu.ben.uk.consumerest.constant.ViewerType;
import jitsu.ben.uk.consumerest.database.AnkleLockDataSource;
import jitsu.ben.uk.consumerest.database.ArmLockCounterDataSource;
import jitsu.ben.uk.consumerest.database.ArmLockDataSource;
import jitsu.ben.uk.consumerest.database.FingerLockApplicationDataSource;
import jitsu.ben.uk.consumerest.database.FingerLockDataSource;
import jitsu.ben.uk.consumerest.database.GroundWorkDataSource;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;
import jitsu.ben.uk.consumerest.database.KataDataSource;
import jitsu.ben.uk.consumerest.database.KyushoDataSource;
import jitsu.ben.uk.consumerest.database.LegLockCounterDataSource;
import jitsu.ben.uk.consumerest.database.LegLockDataSource;
import jitsu.ben.uk.consumerest.database.NeckLockDataSource;
import jitsu.ben.uk.consumerest.database.ThrowsDataSource;
import jitsu.ben.uk.consumerest.database.WristLockDataSource;

import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_BEAN_ARRAY;
import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_BEAN_POSITION;
import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_BEAN_VIEWER_CLASS;
import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_GRADE;
import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_LIST_TYPE;


public class ListJitsuBeanActivity extends AppCompatActivity {

	protected List<Integer> viewers = new ArrayList<>();
	protected List<JitsuBean> mJitsuBeans;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_bean);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getJitsuBeans();
	}

	public void randomise(View view) {
		long seed = System.nanoTime();
		List<Integer> ordering = new ArrayList<>();
		List<Integer> tempViewers = new ArrayList<>(viewers);
		List<JitsuBean> tempBeans = new ArrayList<>(mJitsuBeans);
		for (int i = 0; i < mJitsuBeans.size(); i++) {
			ordering.add(i);
		}
		viewers.clear();
		mJitsuBeans.clear();
		Collections.shuffle(ordering, new Random(seed));
		for (int i = 0; i < ordering.size(); i++) {
			viewers.add(i, tempViewers.get(ordering.get(i)));
			mJitsuBeans.add(i, tempBeans.get(ordering.get(i)));
		}
		listNames(mJitsuBeans);
	}

	private void listNames(final List<JitsuBean> jitsuBeans) {

		mJitsuBeans = jitsuBeans;
		final Parcelable[] beans = jitsuBeans.toArray(new JitsuBean[0]);
		final int[] viewersArray = new int[viewers.size()];
		for (int i = 0; i < viewers.size(); i++) {
			viewersArray[i] = viewers.get(i);
		}

		ListView listView = (ListView) findViewById(R.id.id_list_all);
		JitsuArrayAdapter<JitsuBean> adapter = new JitsuArrayAdapter<>(listView.getContext(), android.R.layout.simple_dropdown_item_1line, jitsuBeans);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(parent.getContext(), ViewAll.class);
				intent.putExtra(INTENT_EXTRA_BEAN_POSITION, position);
				intent.putExtra(INTENT_EXTRA_BEAN_VIEWER_CLASS, viewersArray);
				intent.putExtra(INTENT_EXTRA_BEAN_ARRAY, beans);
				startActivity(intent);

			}
		});
		listView.setAdapter(adapter);

	}

	private void getJitsuBeans() {
		JitsuDataSource dataSource = new JitsuDataSource(this.getBaseContext());
		Bundle recdData = getIntent().getExtras();

		List<JitsuBean> jitsuBeans = new ArrayList<>();
		Integer listType = recdData.getInt(INTENT_EXTRA_LIST_TYPE);
		Integer gradeId = recdData.getInt(INTENT_EXTRA_GRADE);

		int counter = 0;
		if (listType.equals(ListType.THROW_LIST) || listType.equals(ListType.ALL_LIST)) {
			ThrowsDataSource ds = new ThrowsDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getThrowsByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getThrows());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.THROW_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.KYUSHO_LIST) || listType.equals(ListType.ALL_LIST)) {
			KyushoDataSource ds = new KyushoDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getKyushosByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getKyushos());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.KYUSHO_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.KATA_LIST) || listType.equals(ListType.ALL_LIST)) {
			KataDataSource ds = new KataDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getKatasByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getKatas());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.KATA_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.ARMLOCK_LIST) || listType.equals(ListType.ALL_LIST)) {
			ArmLockDataSource ds = new ArmLockDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getArmLocksByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getArmLocks());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.ARMLOCK_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.WRISTLOCK_LIST) || listType.equals(ListType.ALL_LIST)) {
			WristLockDataSource ds = new WristLockDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getWristLocksByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getWristLocks());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.WRISTLOCK_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.LEGLOCK_LIST) || listType.equals(ListType.ALL_LIST)) {
			LegLockDataSource ds = new LegLockDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getLegLocksByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getLegLocks());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.LEGLOCK_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.ANKLELOCK_LIST) || listType.equals(ListType.ALL_LIST)) {
			AnkleLockDataSource ds = new AnkleLockDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getAnkleLocksByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getAnkleLocks());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.ANKLELOCK_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.NECKLOCK_LIST) || listType.equals(ListType.ALL_LIST)) {
			NeckLockDataSource ds = new NeckLockDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getNeckLocksByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getNeckLocks());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.NECKLOCK_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.FINGERLOCK_LIST) || listType.equals(ListType.ALL_LIST)) {
			FingerLockDataSource ds = new FingerLockDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getFingerLocksByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getFingerLocks());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.FINGERLOCK_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.ARMLOCKCOUNTER_LIST) || listType.equals(ListType.ALL_LIST)) {
			ArmLockCounterDataSource ds = new ArmLockCounterDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getArmLockCounterssByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getArmLockCounters());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.ARMLOCKCOUNTER_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.FINGERLOCKAPPLICATION_LIST) || listType.equals(ListType.ALL_LIST)) {
			FingerLockApplicationDataSource ds = new FingerLockApplicationDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getFingerLockApplicationsByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getFingerLockApplications());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.FINGERLOCKAPPLICATION_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.GROUNDWORK_LIST) || listType.equals(ListType.ALL_LIST)) {
			GroundWorkDataSource ds = new GroundWorkDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getGroundWorksByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getGroundWorks());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.GROUNDWORK_FRAGMENT);
				counter++;
			}
		}
		if (listType.equals(ListType.LEGLOCKCOUNTER_LIST) || listType.equals(ListType.ALL_LIST)) {
			LegLockCounterDataSource ds = new LegLockCounterDataSource(this.getBaseContext());
			if (gradeId != null && gradeId >= 0) {
				jitsuBeans.addAll(ds.getLegLockCounterssByGrade(gradeId));
			} else {
				jitsuBeans.addAll(ds.getLegLockCounters());
			}
			ds.close();
			while (counter < jitsuBeans.size()) {
				viewers.add(ViewerType.LEGLOCKCOUNTER_FRAGMENT);
				counter++;
			}
		}
		dataSource.close();
		listNames(jitsuBeans);
	}
}
