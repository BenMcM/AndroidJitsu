package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 23/09/2016.
 */

public class KataStep implements Comparable<KataStep>, Parcelable{
    private Integer id;
    private Integer stepNumber;
    private String description;

    public KataStep() {

    }

	public KataStep(Integer id, Integer stepNumber, String description) {
		this.id = id;
		this.stepNumber = stepNumber;
		this.description = description;
	}

	public KataStep(Integer stepNumber, String description) {
		this.stepNumber = stepNumber;
		this.description = description;
	}

	protected KataStep(Parcel in) {
		this.id = in.readInt();
		this.stepNumber = in.readInt();
		this.description = in.readString();
	}

	@Override
    public String toString(){
        return getStepNumber().toString();
    }

    public Integer getId(){
        return id;
    }

    public Integer getStepNumber(){
        return stepNumber;
    }

    public String getDescription(){
        return description;
    }

    public int compareTo(KataStep step){
        return this.stepNumber.compareTo(step.getStepNumber());
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(id);
		parcel.writeInt(stepNumber);
		parcel.writeString(description);
	}

	public static final Creator<KataStep> CREATOR = new Creator<KataStep>() {
		@Override
		public KataStep createFromParcel(Parcel in) {
			return new KataStep(in);
		}

		@Override
		public KataStep[] newArray(int size) {
			return new KataStep[size];
		}
	};

    public static List<KataStep> getStepsFromString(String description, String delimeter){
		String[] stepsArray = description.split(delimeter);
    	List<KataStep> kataSteps = new ArrayList<>();
		for(int i=0; i<stepsArray.length; i++){
			KataStep kataStep = new KataStep(i+1, stepsArray[i]);
			kataSteps.add(kataStep);
		}
		return kataSteps;
	}
}
