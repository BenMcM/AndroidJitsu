package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;

/**
 * Created by ben on 23/09/2016.
 */

public class WristLock implements JitsuBean{
    private Integer id;
    private String number;
    private Grade grade;
    private String entrance;
    private String description;

    public WristLock(){

    }

    public WristLock(Integer id, String number, Grade grade, String entrance, String description) {
        this.id = id;
        this.number = number;
        this.grade = grade;
        this.entrance = entrance;
        this.description = description;
    }

	public WristLock(String number, Grade grade, String entrance, String description) {
		this.number = number;
		this.grade = grade;
		this.entrance = entrance;
		this.description = description;
	}

	protected WristLock(Parcel in) {
		this.id = in.readInt();
		this.number = in.readString();
		this.grade = in.readParcelable(Grade.class.getClassLoader());
		this.entrance = in.readString();
		this.description = in.readString();
	}

    @Override
    public String toString(){

        return "Wrist lock " + getNumber();
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

    public String getEntrance(){
        return entrance;
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
		parcel.writeString(entrance);
		parcel.writeString(description);
	}

	public static final Creator<WristLock> CREATOR = new Creator<WristLock>() {
		@Override
		public WristLock createFromParcel(Parcel in) {
			return new WristLock(in);
		}

		@Override
		public WristLock[] newArray(int size) {
			return new WristLock[size];
		}
	};
}
