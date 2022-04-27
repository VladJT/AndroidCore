package jt.projects.androidcore.calculator;

class StringFormatter {
    public static String getNumberWithoutZerosAtStart(String d) {
        return d.replaceAll("^([-]*)[0]+([1-9])", "$1$2").replaceAll("^00","0");// удаляем нули перед числами
    }

    public static String getNumberWithoutZerosAtEnd(String d) {
        return d.replaceAll("\\.[0]+$", "");
    }
}
