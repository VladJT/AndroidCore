package jt.projects.androidcore.calculator;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;


class CalcData implements Parcelable {

    public static final String DIVIDE_BY_ZERO_ERROR = "Division by zero";

    private String resultInfoText;
    private BigDecimal number1;
    private BigDecimal number2;
    private BigDecimal result;
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
        resultInfoText = "";
    }

    public void setResultInfoText(String resultInfoText) {
        this.resultInfoText = resultInfoText;
    }

    public String getResultInfoText() {
        return resultInfoText;
    }

    public boolean isOperatorPressed() {
        return operatorPressed;
    }

    public void setOperatorPressed(boolean operatorPressed) {
        this.operatorPressed = operatorPressed;
    }

    public void setNumber(BigDecimal number) {
        if (number1 == null) {
            number1 = number;
        } else {
            number2 = number;
        }
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setOperator(String newOperator) {
        if (number1 == null) number1 = BigDecimal.ZERO;
        operator = newOperator;
        operatorPressed = true;
    }

    public String getOperator() {
        return operator;
    }

    public void setNumber1(BigDecimal number1) {
        this.number1 = number1;
    }

    public BigDecimal getNumber1() {
        return number1;
    }

    public BigDecimal getNumber2() {
        return number2;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected CalcData(Parcel in) {
//        operator = in.readString();
//        number1 = BigDecimal.valueOf(in.readDouble());
//        number2 = BigDecimal.valueOf(in.readDouble());
//        result = BigDecimal.valueOf(in.readDouble());
//        operatorPressed = in.readBoolean();
//        resultInfoText = in.readString();
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
        try {
//            dest.writeString(operator);
//            dest.writeValue(number1);
//            dest.writeValue(number2);
//            dest.writeValue(result);
//            dest.writeBoolean(operatorPressed);
//            dest.writeString(resultInfoText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String countResult() {
        try {
            if (!operator.equals("")) {
                if (number2 != null) {
                    switch (operator) {
                        case "+":
                            result = number1.add(number2);
                            break;
                        case "-":
                            result = number1.subtract(number2);
                            break;
                        case "/":
                            result = number1.divide(number2, 10, BigDecimal.ROUND_DOWN);
                            break;
                        case "*":
                            result = number1.multiply(number2);
                            break;
                    }
                }
            }
            result = result.stripTrailingZeros();
            return result.toPlainString();
        } catch (ArithmeticException e) {
            return e.getMessage();
        }
    }
}