package jt.projects.androidcore.calculator;

import android.os.Parcel;
import android.os.Parcelable;


class CalcData implements Parcelable {

    private Double result;
    private Double number;
    private String operator;
    private String inputNumberString;
    private String resultInfoString;
    private boolean operatorPressed;


    public CalcData() {
        this.clear();
    }

    public void clear() {
        result = null;
        number = null;
        operator = "";
        inputNumberString = "";
        resultInfoString = "";
        operatorPressed = false;
    }

    public boolean isNeedClearInputNumberString() {
        return operatorPressed;
    }

    public void setNumber(Double number) {
        operatorPressed = false;
        if (result == null) {
            result = number;
        } else {
            this.number = number;
        }
    }

    public Double getNumber() {
        return number;
    }

    public Double getResult() {
        return result;
    }

    public void setOperator(String operator) {
        this.operator = operator;
        if (operator.equals("+")) {
            if (result == null) result = 0.0;
            if (number != null) {
                result += number;
            }
        }

        if (operator.equals("-")) {
            if (result == null) result = 0.0;
            if (number != null) {
                result -= number;
            }
        }

        resultInfoString = result + operator;
        operatorPressed = true;
    }

    protected CalcData(Parcel in) {
        resultInfoString = in.readString();
        inputNumberString = in.readString();
        operator = in.readString();
        result = in.readDouble();
        number = in.readDouble();
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
        dest.writeString(resultInfoString);
        dest.writeString(inputNumberString);
        dest.writeString(operator);
        dest.writeDouble(result);
        dest.writeDouble(number);
    }


    public void setInputNumberString(String inputNumberString) {
        this.inputNumberString = inputNumberString;
    }

    public String getInputNumberString() {
        return inputNumberString;
    }

    public String getResultInfoString() {
        //return result + operator + (number == null ? "" : number + "=");
        return resultInfoString;
    }

}

