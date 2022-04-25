package jt.projects.androidcore.calculator;

import android.os.Parcel;
import android.os.Parcelable;


class CalcData implements Parcelable {

    private Double result;
    private Double number;
    private String operator;
    private String resultInfoString;
    private boolean operatorPressed;


    public CalcData() {
        this.clear();
    }

    public void clear() {
        result = null;
        number = null;
        operator = "";
        resultInfoString = "";
        operatorPressed = false;
    }

    public boolean isOperatorPressed() {
        return operatorPressed;
    }

    public void setOperatorPressed(boolean operatorPressed) {
        this.operatorPressed = operatorPressed;
    }

    public void setNumber(Double number) {
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

    public void setOperator(String newOperator) {
        if (result == null) result = 0.0;

        resultInfoString = result+"";
        if (!operator.equals("") || !operatorPressed) {

            if (operator.equals("+")) {
                if (number != null) {
                    result += number;
                }
            }

            if (operator.equals("-")) {
                if (number != null) {
                    result -= number;
                }
            }

        }

        resultInfoString += operator;
        if (number != null) {
            resultInfoString += number + "=";
        }
        this.operator = newOperator;
        operatorPressed = true;
    }

    protected CalcData(Parcel in) {
        resultInfoString = in.readString();
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
        dest.writeString(operator);
        dest.writeDouble(result);
        dest.writeDouble(number);
    }


    public String getInputNumberString() {
        return String.valueOf(result).replaceAll("\\.[0]+$", "");
    }

    public String getResultInfoString() {
        return resultInfoString;
    }

    public void countOperation() {
        setOperator(this.operator);
    }
}

