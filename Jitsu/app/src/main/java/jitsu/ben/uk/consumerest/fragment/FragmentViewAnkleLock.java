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
import jitsu.ben.uk.consumerest.bean.AnkleLock;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.AnkleLockDataSource;


public class FragmentViewAnkleLock extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
    private static final String ARG_ANKLELOCK = "ankelockBean";

    private AnkleLock mAnkleLock;

    public FragmentViewAnkleLock() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.anklelock_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.anklelock_edit_details_view);
		super.onStart();
	}

	public static FragmentViewAnkleLock newInstance(AnkleLock ankleLock, Boolean detailIsChecked) {
		FragmentViewAnkleLock fragment = new FragmentViewAnkleLock();
		Bundle args = new Bundle();
		args.putParcelable(ARG_ANKLELOCK, ankleLock);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
			mAnkleLock = getArguments().getParcelable(ARG_ANKLELOCK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_anklelock, container, false);
       	populateUI();
        return rootView;
    }

	public void onSaveEdit(){
		mAnkleLock = getAnkleLockFromEditFields();

		AnkleLockDataSource dataSource = new AnkleLockDataSource(rootView.getContext());
		dataSource.updateAnkleLock(mAnkleLock);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.anklelock_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

	private void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.anklelock_number_value);
		numberText.setText(mAnkleLock.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.anklelock_grade_value);
		gradeText.setText(mAnkleLock.getGrade().getName());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.anklelock_description_value);
		descriptionText.setText(mAnkleLock.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.anklelock_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.anklelock_edit_description_value);
		descriptionEdit.setText(mAnkleLock.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mAnkleLock.getGrade());
	}

	public AnkleLock getAnkleLockFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.anklelock_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.anklelock_edit_grade_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.anklelock_description_value);

		Integer id = mAnkleLock.getId();
		String number = numberText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();
		return new AnkleLock(id, number, grade, description);
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
