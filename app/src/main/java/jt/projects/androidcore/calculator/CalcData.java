package jt.projects.androidcore.calculator;

import android.os.Parcel;
import android.os.Parcelable;


class CalcData implements Parcelable {
    private int counter;
    private String inputNumberString;

    public CalcData() {
        counter = 0;
        inputNumberString = "";
    }

    protected CalcData(Parcel in) {
        counter = in.readInt();
        inputNumberString = in.readString();
    }

    public static final Creator<CalcData> CREATOR = new Creator<CalcData>() {
        @Override
        public CalcData createFromParcel(Parcel in) {
            return new CalcData(in);
        }

        @Override
        public CalcData[] newArray(int size) {
            return new CalcData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(counter);
        dest.writeString(inputNumberString);
    }

    public void incrementCounter() {
        counter++;
    }

    public void setInputNumberString(String stInputNumber) {
        this.inputNumberString = stInputNumber;
    }

    public String getInputNumberString() {
        return inputNumberString;
    }
}

