package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;

/**
 * Created by ben on 19/09/2016.
 */

public class Throw implements JitsuBean{
    private Integer id;
    private String name;
    private String translation;
    private Grade grade;
    private String entrance;
    private String description;
    private String variation;

    public Throw(){

    }

    public Throw(Integer id, String name, String translation, Grade grade, String entrance, String description, String variation) {
        this.id = id;
        this.name = name;
        this.translation = translation;
        this.grade = grade;
        this.entrance = entrance;
        this.description = description;
        this.variation = variation;
    }

    public Throw(String name, String translation, Grade grade, String entrance, String description, String variation){
        this.name = name;
        this.translation = translation;
        this.grade = grade;
        this.entrance = entrance;
        this.description = description;
        this.variation = variation;
    }

	protected Throw(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.translation = in.readString();
		this.grade = in.readParcelable(Grade.class.getClassLoader());
		this.entrance = in.readString();
		this.description = in.readString();
		this.variation = in.readString();
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

    public String getTranslation() {
        return translation;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getEntrance() {
        return entrance;
    }

    public String getDescription() {
        return description;
    }

    public String getVariation() {
        return variation;
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
		parcel.writeString(entrance);
		parcel.writeString(description);
		parcel.writeString(variation);
	}

	public static final Creator<Throw> CREATOR = new Creator<Throw>() {
		@Override
		public Throw createFromParcel(Parcel in) {
			return new Throw(in);
		}

		@Override
		public Throw[] newArray(int size) {
			return new Throw[size];
		}
	};
}
