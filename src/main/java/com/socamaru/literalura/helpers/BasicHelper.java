package com.socamaru.literalura.helpers;

import java.util.List;

public class BasicHelper {
    public static <T> String formatArray(List<T> list, String separator, String finalCharacter) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i).toString());
            if(i < list.size() - 1) {
                stringBuilder.append(separator);
            }else{
                stringBuilder.append(finalCharacter);
            }
        }
        return stringBuilder.toString();
    }
}
