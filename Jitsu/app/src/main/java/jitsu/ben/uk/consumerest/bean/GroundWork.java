package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;

/**
 * Created by ben on 23/09/2016.
 */

public class GroundWork implements JitsuBean{
    private Integer id;
    private String name;
    private String translation;
    private Grade grade;
	private String startPosition;
    private String description;

    public GroundWork(){

    }

    public GroundWork(Integer id, String name, String translation, Grade grade, String startPosition, String description) {
        this.id = id;
        this.name = name;
        this.translation = translation;
        this.grade = grade;
        this.startPosition = startPosition;
        this.description = description;
    }

    public GroundWork(String name, String translation, Grade grade, String startPosition, String description){
		this.name = name;
		this.translation = translation;
		this.grade = grade;
		this.startPosition = startPosition;
		this.description = description;
	}

	protected GroundWork(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.translation = in.readString();
		this.grade = in.readParcelable(Grade.class.getClassLoader());
		this.startPosition = in.readString();
		this.description = in.readString();
	}

    @Override
    public String toString(){

        return name;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getTranslation(){
    	return translation;
	}

    public Grade getGrade(){
        return grade;
    }

    public String getStartPosition(){
    	return startPosition;
	}

    public String getDescription(){
        return description;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(id);
		parcel.writeString(name);
		parcel.writeString(translation);
		parcel.writeParcelable(grade, i);
		parcel.writeString(startPosition);
		parcel.writeString(description);
	}

	public static final Creator<GroundWork> CREATOR = new Creator<GroundWork>() {
		@Override
		public GroundWork createFromParcel(Parcel in) {
			return new GroundWork(in);
		}

		@Override
		public GroundWork[] newArray(int size) {
			return new GroundWork[size];
		}
	};
}
