package jitsu.ben.uk.consumerest.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ben on 28/09/2016.
 */

public class Grade implements Parcelable{
    private Integer id;
    private Integer ordering;
    private String name;

    public Grade(){

    }
    public Grade(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    public Grade(Integer id, String name, Integer ordering){
        this.id = id;
        this.name = name;
        this.ordering = ordering;
    }

    protected Grade(Parcel in) {
        this.id = in.readInt();
        this.ordering = in.readInt();
        this.name = in.readString();
    }

    public Integer getId() {
        return id;
    }
    public Integer getOrdering() {
        return ordering;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(ordering);
        parcel.writeString(name);
    }

    @Override
	public boolean equals(Object o){
    	if(o == null){
    		return false;
		}
    	if(!(o instanceof Grade)){
    		return false;
		}
		if(o == this){
    		return true;
		}

		Grade grade = (Grade) o;
    	return (grade.getId().equals(this.id) && grade.getName().equals(this.name) && grade.getOrdering().equals(this.ordering));
	}

	@Override
	public int hashCode(){
    	int hash = 13;
    	hash = 7 *  hash + (this.getName() != null ? this.getName().hashCode() : 0);
    	hash = 11 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
		hash = 5 * hash + (this.getOrdering() != null ? this.getOrdering().hashCode() : 0);
    	return hash;
	}

	public static final Creator<Grade> CREATOR = new Creator<Grade>() {
		@Override
		public Grade createFromParcel(Parcel in) {
			return new Grade(in);
		}

		@Override
		public Grade[] newArray(int size) {
			return new Grade[size];
		}
	};
}
