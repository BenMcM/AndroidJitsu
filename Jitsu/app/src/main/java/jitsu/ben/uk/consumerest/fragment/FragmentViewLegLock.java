package jitsu.ben.uk.consumerest.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import jitsu.ben.uk.consumerest.R;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.LegLock;
import jitsu.ben.uk.consumerest.database.LegLockDataSource;


public class FragmentViewLegLock extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_LEGLOCK = "leglockBean";

    private LegLock mLegLock;

    public FragmentViewLegLock() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.leglock_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.leglock_edit_details_view);
		super.onStart();
	}

	public static FragmentViewLegLock newInstance(LegLock legLock, Boolean detailIsChecked) {
		FragmentViewLegLock fragment = new FragmentViewLegLock();
		Bundle args = new Bundle();
		args.putParcelable(ARG_LEGLOCK, legLock);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLegLock = getArguments().getParcelable(ARG_LEGLOCK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_leglock, container, false);
        populateUI();
        return rootView;

    }

	public void onSaveEdit(){
		mLegLock = getLegLockFromEditFields();

		LegLockDataSource dataSource = new LegLockDataSource(rootView.getContext());
		dataSource.updateLegLock(mLegLock);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.leglock_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    private void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.leglock_number_value);
		numberText.setText(mLegLock.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.leglock_grade_value);
		gradeText.setText(mLegLock.getGrade().getName());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.leglock_description_value);
		descriptionText.setText(mLegLock.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.leglock_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.leglock_edit_description_value);
		descriptionEdit.setText(mLegLock.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){
		return allGrades.indexOf(mLegLock.getGrade());
	}

	public LegLock getLegLockFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.leglock_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.leglock_edit_grade_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.leglock_description_value);

		Integer id = mLegLock.getId();
		String number = numberText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		return new LegLock(id, number, grade, description);
	}

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
