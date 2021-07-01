package com.example.otlobna.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class Details implements Parcelable {
    public final String name;

    public Details(String name) {
        this.name = name;
    }

    protected Details(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Details> CREATOR = new Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel in) {
            return new Details(in);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
