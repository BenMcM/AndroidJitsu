package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;

/**
 * Created by ben on 23/09/2016.
 */

public class LegLock implements JitsuBean{
    private Integer id;
    private String number;
    private Grade grade;
    private String description;

    public LegLock(){

    }

	public LegLock(Integer id, String number, Grade grade, String description) {
		this.id = id;
		this.number = number;
		this.grade = grade;
		this.description = description;
	}

	public LegLock(String number, Grade grade, String description) {
		this.number = number;
		this.grade = grade;
		this.description = description;
	}

	protected LegLock(Parcel in) {
		this.id = in.readInt();
		this.number = in.readString();
		this.grade = in.readParcelable(Grade.class.getClassLoader());
		this.description = in.readString();
	}

	@Override
    public String toString(){

        return "Leg lock " + getNumber();
    }

    public Integer getId(){
        return id;
    }

    public String getNumber(){
        return number;
    }

    public String getName(){
        return getNumber();
    }

    public Grade getGrade(){
        return grade;
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
		parcel.writeString(number);
		parcel.writeParcelable(grade, i);
		parcel.writeString(description);
	}

	public static final Creator<LegLock> CREATOR = new Creator<LegLock>() {
		@Override
		public LegLock createFromParcel(Parcel in) {
			return new LegLock(in);
		}

		@Override
		public LegLock[] newArray(int size) {
			return new LegLock[size];
		}
	};
}
