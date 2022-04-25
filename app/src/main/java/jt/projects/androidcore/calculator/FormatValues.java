package jt.projects.androidcore.calculator;

class FormatValues {
    public static String getNumberWithoutZerosAtStart(String d) {
        return d.replaceAll("^([-]*)[0]+([1-9])", "$1$2");// удаляем нули перед числами
    }

    public static String getNumberWithoutZerosAtEnd(String d) {
        return d.replaceAll("\\.[0]+$", "");
    }
}
