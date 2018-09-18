package com.amphora.tools;

import java.text.Normalizer;

/**
 * Created by rodolfo on 11/09/2018.
 */

public class VerificaString {

    static String normalizeString;

    public static String semCaracterEspecial (String texto){
        return normalizeString = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

    }
}
