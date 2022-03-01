package jitsu.ben.uk.consumerest;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

import jitsu.ben.uk.consumerest.bean.AnkleLock;
import jitsu.ben.uk.consumerest.bean.ArmLock;
import jitsu.ben.uk.consumerest.bean.ArmLockCounter;
import jitsu.ben.uk.consumerest.bean.FingerLock;
import jitsu.ben.uk.consumerest.bean.FingerLockApplication;
import jitsu.ben.uk.consumerest.bean.GroundWork;
import jitsu.ben.uk.consumerest.bean.Kata;
import jitsu.ben.uk.consumerest.bean.Kyusho;
import jitsu.ben.uk.consumerest.bean.LegLock;
import jitsu.ben.uk.consumerest.bean.LegLockCounter;
import jitsu.ben.uk.consumerest.bean.NeckLock;
import jitsu.ben.uk.consumerest.bean.Throw;
import jitsu.ben.uk.consumerest.bean.WristLock;
import jitsu.ben.uk.consumerest.constant.ViewerType;
import jitsu.ben.uk.consumerest.fragment.FragmentViewAnkleLock;
import jitsu.ben.uk.consumerest.fragment.FragmentViewArmLock;
import jitsu.ben.uk.consumerest.fragment.FragmentViewArmLockCounter;
import jitsu.ben.uk.consumerest.fragment.FragmentViewBean;
import jitsu.ben.uk.consumerest.fragment.FragmentViewFingerLock;
import jitsu.ben.uk.consumerest.fragment.FragmentViewFingerLockApplication;
import jitsu.ben.uk.consumerest.fragment.FragmentViewGroundWork;
import jitsu.ben.uk.consumerest.fragment.FragmentViewKata;
import jitsu.ben.uk.consumerest.fragment.FragmentViewKyusho;
import jitsu.ben.uk.consumerest.fragment.FragmentViewLegLock;
import jitsu.ben.uk.consumerest.fragment.FragmentViewLegLockCounter;
import jitsu.ben.uk.consumerest.fragment.FragmentViewNeckLock;
import jitsu.ben.uk.consumerest.fragment.FragmentViewThrow;
import jitsu.ben.uk.consumerest.fragment.FragmentViewWristLock;

import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_BEAN_ARRAY;
import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_BEAN_POSITION;
import static jitsu.ben.uk.consumerest.constant.Constants.INTENT_EXTRA_BEAN_VIEWER_CLASS;

public class ViewAll extends AppCompatActivity {

	private Parcelable[] jitsuBeans;
	private int[] viewerTypes;
	private Boolean detailsIsChecked;
	private ViewPager mPager;
	private FragmentPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all);

		Bundle recdData = getIntent().getExtras();
		jitsuBeans = recdData.getParcelableArray(INTENT_EXTRA_BEAN_ARRAY);
		viewerTypes = recdData.getIntArray(INTENT_EXTRA_BEAN_VIEWER_CLASS);
		int kyushoPosition = recdData.getInt(INTENT_EXTRA_BEAN_POSITION);

		Switch detailsSwitch = (Switch) findViewById(R.id.bean_details_button);
		detailsIsChecked = detailsSwitch.isChecked();

		mPager = (ViewPager)findViewById(R.id.bean_pager);
		mPagerAdapter = new ViewAll.BeanSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(kyushoPosition);

		detailsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				detailsIsChecked = isChecked;

				List<Fragment> fragments=  getSupportFragmentManager().getFragments();
				for(Fragment fragment : fragments){
					FragmentViewBean beanFragment = (FragmentViewBean) fragment;

					beanFragment.toggleDetails(isChecked);
				}
			}
		});

	}

	public void toggleDetails(View view){
		System.out.println("Toggling details");
		Switch detailsSwitch = (Switch) findViewById(R.id.bean_details_button);
		detailsSwitch.toggle();
	}

	private class BeanSlidePagerAdapter extends FragmentPagerAdapter {

		public BeanSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			int viewerType = viewerTypes[position];
			Fragment fragment;
			switch (viewerType){
				case ViewerType.THROW_FRAGMENT:
					fragment =  FragmentViewThrow.newInstance((Throw)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.KYUSHO_FRAGMENT:
					fragment =  FragmentViewKyusho.newInstance((Kyusho)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.KATA_FRAGMENT:
					fragment = FragmentViewKata.newInstance((Kata)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.ARMLOCK_FRAGMENT:
					fragment = FragmentViewArmLock.newInstance((ArmLock)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.WRISTLOCK_FRAGMENT:
					fragment = FragmentViewWristLock.newInstance((WristLock)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.LEGLOCK_FRAGMENT:
					fragment = FragmentViewLegLock.newInstance((LegLock)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.ANKLELOCK_FRAGMENT:
					fragment = FragmentViewAnkleLock.newInstance((AnkleLock)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.NECKLOCK_FRAGMENT:
					fragment = FragmentViewNeckLock.newInstance((NeckLock)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.FINGERLOCK_FRAGMENT:
					fragment = FragmentViewFingerLock.newInstance((FingerLock)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.ARMLOCKCOUNTER_FRAGMENT:
					fragment = FragmentViewArmLockCounter.newInstance((ArmLockCounter)jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.FINGERLOCKAPPLICATION_FRAGMENT:
					fragment = FragmentViewFingerLockApplication.newInstance((FingerLockApplication) jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.GROUNDWORK_FRAGMENT:
					fragment = FragmentViewGroundWork.newInstance((GroundWork) jitsuBeans[position], detailsIsChecked);
					break;
				case ViewerType.LEGLOCKCOUNTER_FRAGMENT:
					fragment = FragmentViewLegLockCounter.newInstance((LegLockCounter) jitsuBeans[position], detailsIsChecked);
					break;
				default:
					fragment = null;

			}
			return fragment;
		}

		@Override
		public int getCount() {
			return jitsuBeans.length;
		}
	}

}
