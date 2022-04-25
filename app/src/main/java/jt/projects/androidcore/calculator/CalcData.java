package jt.projects.androidcore.calculator;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;


class CalcData implements Parcelable {

    private Double number1;
    private Double number2;
    private Double result;
    private String operator;
    private boolean operatorPressed;

    public CalcData() {
        this.clear();
    }

    public void clear() {
        number1 = null;
        number2 = null;
        result = null;
        operator = "";
        operatorPressed = false;
    }

    public boolean isOperatorPressed() {
        return operatorPressed;
    }

    public void setOperatorPressed(boolean operatorPressed) {
        this.operatorPressed = operatorPressed;
    }

    public void setNumber(Double number) {
        if (number1 == null) {
            number1 = number;
        } else {
            number2 = number;
        }
    }

    public Double getResult() {
        return result;
    }

    public void setOperator(String newOperator) {
        if (number1 == null) number1 = 0.0;
        operator = newOperator;
        operatorPressed = true;
    }

    public String getOperator() {
        return operator;
    }

    public void setNumber1(Double number1) {
        this.number1 = number1;
    }

    public Double getNumber1() {
        return number1;
    }

    public Double getNumber2() {
        return number2;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected CalcData(Parcel in) {
        operator = in.readString();
        number1 = in.readDouble();
        number2 = in.readDouble();
        result = in.readDouble();
        operatorPressed = in.readBoolean();
    }

    public static final Creator<CalcData> CREATOR = new Creator<CalcData>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(operator);
        dest.writeDouble(number1);
        dest.writeDouble(number2);
        dest.writeDouble(result);
        dest.writeBoolean(operatorPressed);
    }

    public void countResult() {
        if (!operator.equals("")) {
            if (number2 != null) {
                switch (operator) {
                    case "+":
                        result = number1 + number2;
                        break;
                    case "-":
                        result = number1 - number2;
                        break;
                    case "/":
                        result = number1 / number2;
                        break;
                    case "*":
                        result = number1 * number2;
                        break;
                }
            }
        }
    }

}