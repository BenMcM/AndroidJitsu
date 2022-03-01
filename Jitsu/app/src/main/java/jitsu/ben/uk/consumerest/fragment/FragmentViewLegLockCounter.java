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
import jitsu.ben.uk.consumerest.bean.LegLockCounter;
import jitsu.ben.uk.consumerest.database.LegLockCounterDataSource;


public class FragmentViewLegLockCounter extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_LEGLOCKCOUNTER = "leglockcounterBean";

    private LegLockCounter mLegLockCounter;

    public FragmentViewLegLockCounter() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.leglockcounter_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.leglockcounter_edit_details_view);
		super.onStart();
	}

	public static FragmentViewLegLockCounter newInstance(LegLockCounter legLockCounter, Boolean detailIsChecked) {
		FragmentViewLegLockCounter fragment = new FragmentViewLegLockCounter();
		Bundle args = new Bundle();
		args.putParcelable(ARG_LEGLOCKCOUNTER, legLockCounter);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLegLockCounter = getArguments().getParcelable(ARG_LEGLOCKCOUNTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_leglockcounter, container, false);
        populateUI();
        return rootView;

    }

	public void onSaveEdit(){
		mLegLockCounter = getLegLockCounterFromEditFields();

		LegLockCounterDataSource dataSource = new LegLockCounterDataSource(rootView.getContext());
		dataSource.updateLegLockCounter(mLegLockCounter);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.leglockcounter_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    private void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.leglockcounter_number_value);
		numberText.setText(mLegLockCounter.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.leglockcounter_grade_value);
		gradeText.setText(mLegLockCounter.getGrade().getName());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.leglockcounter_description_value);
		descriptionText.setText(mLegLockCounter.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.leglockcounter_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.leglockcounter_edit_description_value);
		descriptionEdit.setText(mLegLockCounter.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){
		return allGrades.indexOf(mLegLockCounter.getGrade());
	}

	public LegLockCounter getLegLockCounterFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.leglockcounter_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.leglockcounter_edit_grade_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.leglockcounter_description_value);

		Integer id = mLegLockCounter.getId();
		String number = numberText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		return new LegLockCounter(id, number, grade, description);
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
