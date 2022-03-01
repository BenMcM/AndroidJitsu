package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 23/09/2016.
 */

public class Kata implements JitsuBean{

    private Integer id;
    private String name;
    private Grade grade;
    private String translation;
    private List<KataStep> steps;

    public Kata(){

    }

    public Kata(Integer id, String name, Grade grade, String translation, List<KataStep> steps) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.translation = translation;
        this.steps = steps;
    }

	public Kata(String name, Grade grade, String translation, List<KataStep> steps) {
		this.name = name;
		this.grade = grade;
		this.translation = translation;
		this.steps = steps;
	}

	protected Kata(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.grade = in.readParcelable(Grade.class.getClassLoader());
		this.translation = in.readString();
		if (steps == null) {
			steps = new ArrayList();
		}
		in.readTypedList(steps, KataStep.CREATOR);
	}

    @Override
    public String toString(){
        return getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getTranslation() {
        return translation;
    }

    public List<KataStep> getSteps() {
        return steps;
    }

    public void setKataSteps(List<KataStep> kataSteps){
    	steps = kataSteps;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(id);
		parcel.writeString(name);
		parcel.writeParcelable(grade, i);
		parcel.writeString(translation);
		parcel.writeTypedList(steps);
	}

	public static final Creator<Kata> CREATOR = new Creator<Kata>() {
		@Override
		public Kata createFromParcel(Parcel in) {
			return new Kata(in);
		}

		@Override
		public Kata[] newArray(int size) {
			return new Kata[size];
		}
	};
}
