package com.uniovi.sdi2223entrega122.pageobjects;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class PO_Properties {
    public static int getSPANISH() {
        return SPANISH;
    }

    public static int getENGLISH() {
        return ENGLISH;
    }

    static private String Path;
    static final int SPANISH = 0;
    static final int ENGLISH = 1;
    static final Locale[] idioms = new Locale[]{new Locale("ES"), new Locale("EN")};

    public PO_Properties(String Path)
    {
        PO_Properties.Path = Path;
    }

	/**
	 * Locale is the index in languages array.
	 * @param prop
	 * @param locale
	 * @return
	 */
	public String getString(String prop, int locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(Path, idioms[locale]);
        String value = bundle.getString(prop);
        String result;
        //result = new String(value.getBytes(StandardCharsets.ISO_8859_1),  StandardCharsets.UTF_8);
        result = new String(value.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        return result;
    }


}
