package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;

/**
 * Created by ben on 23/09/2016.
 */

public class Kyusho implements JitsuBean{
    private Integer id;
    private String number;
    private Grade grade;
    private String description;

    public Kyusho(){

    }

    public Kyusho(Integer id, String number, Grade grade, String description) {
        this.id = id;
        this.number = number;
        this.grade = grade;
        this.description = description;
    }

    public Kyusho(String number, Grade grade, String description){
		this.number = number;
		this.grade = grade;
		this.description = description;
	}

	protected Kyusho(Parcel in) {
		this.id = in.readInt();
		this.number = in.readString();
		this.grade = in.readParcelable(Grade.class.getClassLoader());
		this.description = in.readString();
	}

    @Override
    public String toString(){

        return "Kyusho " + getNumber();
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

	public static final Creator<Kyusho> CREATOR = new Creator<Kyusho>() {
		@Override
		public Kyusho createFromParcel(Parcel in) {
			return new Kyusho(in);
		}

		@Override
		public Kyusho[] newArray(int size) {
			return new Kyusho[size];
		}
	};
}
