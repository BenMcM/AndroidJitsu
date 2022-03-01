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
import jitsu.ben.uk.consumerest.bean.GroundWork;
import jitsu.ben.uk.consumerest.database.GroundWorkDataSource;


public class FragmentViewGroundWork extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_GROUNDWORK = "groundworkBean";

    private GroundWork mGroundWork;

    public FragmentViewGroundWork() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.groundwork_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.groundwork_edit_details_view);
		super.onStart();
	}

	public static FragmentViewGroundWork newInstance(GroundWork groundWork, Boolean detailIsChecked) {
		FragmentViewGroundWork fragment = new FragmentViewGroundWork();
		Bundle args = new Bundle();
		args.putParcelable(ARG_GROUNDWORK, groundWork);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroundWork = getArguments().getParcelable(ARG_GROUNDWORK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_groundwork, container, false);
        populateUI();
        return rootView;

    }

	public void onSaveEdit(){
    	mGroundWork = getGroundWorkFromEditFields();

		GroundWorkDataSource dataSource = new GroundWorkDataSource(rootView.getContext());
		dataSource.updateGroundWork(mGroundWork);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.groundwork_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    private void populateUI(){
		TextView nameText = (TextView)rootView.findViewById(R.id.groundwork_name_value);
		nameText.setText(mGroundWork.getName());
		TextView translationText = (TextView)rootView.findViewById(R.id.groundwork_translation_value);
		translationText.setText(mGroundWork.getTranslation());
		TextView gradeText = (TextView)rootView.findViewById(R.id.groundwork_grade_value);
		gradeText.setText(mGroundWork.getGrade().getName());
		TextView startPositionText = (TextView)rootView.findViewById(R.id.groundwork_startposition_value);
		startPositionText.setText(mGroundWork.getStartPosition());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.groundwork_description_value);
		descriptionText.setText(mGroundWork.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		EditText translationEdit = (EditText)rootView.findViewById(R.id.groundwork_edit_translation_value);
		translationEdit.setText(mGroundWork.getTranslation());
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.groundwork_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText startPositionEdit = (EditText)rootView.findViewById(R.id.groundwork_edit_startposition_value);
		startPositionEdit.setText(mGroundWork.getStartPosition());
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.groundwork_edit_description_value);
		descriptionEdit.setText(mGroundWork.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mGroundWork.getGrade());
	}

	public GroundWork getGroundWorkFromEditFields(){
		TextView nameText = (TextView)rootView.findViewById(R.id.groundwork_name_value);
		EditText translationEdit = (EditText)rootView.findViewById(R.id.groundwork_edit_translation_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.groundwork_edit_grade_value);
		EditText startPositionEdit = (EditText)rootView.findViewById(R.id.groundwork_edit_startposition_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.groundwork_description_value);

		Integer id = mGroundWork.getId();
		String name = nameText.getText().toString();
		String translation = translationEdit.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String startPosition = startPositionEdit.getText().toString();
		String description = descriptionText.getText().toString();
		return new GroundWork(id, name, translation, grade, startPosition, description);
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
