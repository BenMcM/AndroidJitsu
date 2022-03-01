package jitsu.ben.uk.consumerest.bean;

import android.os.Parcelable;

/**
 * Created by ben on 28/09/2016.
 */

public interface JitsuBean extends Parcelable {
    public Integer getId();
    public Grade getGrade();
    public String getName();
}
