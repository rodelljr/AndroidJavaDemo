package com.java.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ResponseModel implements Parcelable {

    @SerializedName("message")
    private String mMessage;

    @SerializedName("duration")
    private long mDuration;

    @SerializedName("risetime")
    private long mRisetime;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public long getRisetime() {
        return mRisetime;
    }

    public void setRisetime(long mRisetime) {
        this.mRisetime = mRisetime;
    }

    protected ResponseModel(Parcel in) {
        if(in.readString() == null)
            mMessage = "";
        if(in.readByte() != 0)
            mDuration = in.readLong();

        if(in.readByte() != 0)
            mRisetime = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(mMessage == null) {
            dest.writeString("");
        } else {
            dest.writeString(mMessage);
        }

        if(mDuration == 0) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mDuration);
        }

        if(mRisetime == 0) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mRisetime);
        }
    }

    public static final Creator<ResponseModel> CREATOR = new Creator<ResponseModel>() {
        @Override
        public ResponseModel createFromParcel(Parcel parcel) {
            return new ResponseModel(parcel);
        }

        @Override
        public ResponseModel[] newArray(int size) {
            return new ResponseModel[size];
        }
    };
}
