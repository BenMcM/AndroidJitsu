package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;

/**
 * Created by ben on 23/09/2016.
 */

public class AnkleLock implements JitsuBean{
    private Integer id;
    private String number;
    private Grade grade;
    private String description;

    public AnkleLock(){ }

    public AnkleLock(Integer id, String number, Grade grade, String description){
        this.id = id;
        this.number = number;
        this.grade = grade;
        this.description = description;
    }

    public AnkleLock(String number, Grade grade, String description){
    	this.number = number;
    	this.grade = grade;
    	this.description = description;
	}

	protected AnkleLock(Parcel in) {
		this.id = in.readInt();
		this.number = in.readString();
		this.grade = in.readParcelable(Grade.class.getClassLoader());
		this.description = in.readString();
	}

    @Override
    public String toString(){

        return "Ankle lock " + getNumber();
    }

    @Override
    public Integer getId(){
        return id.intValue();
    }

    public String getNumber(){
        return number;
    }

    @Override
    public String getName(){
        return getNumber();
    }

    @Override
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

    public static final Creator<AnkleLock> CREATOR = new Creator<AnkleLock>() {
        @Override
        public AnkleLock createFromParcel(Parcel in) {
            return new AnkleLock(in);
        }

        @Override
        public AnkleLock[] newArray(int size) {
            return new AnkleLock[size];
        }
    };
}
