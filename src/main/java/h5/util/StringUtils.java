/**
 *
 * @author Riozen
 * @date 2015-3-18 9:30:04
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package h5.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Riozenc
 */
public class StringUtils {

	public static final String EMPTY = "";
	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	// Empty checks
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a CharSequence is empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * CharSequence. That functionality is available in isBlank().
	 * </p>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is empty or null
	 * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
	 */
	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is whitespace, empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace
	 * @since 2.0
	 * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 格式化字符串为首字母大写的形式(例:Xxx)
	 *
	 * @param name
	 * @return
	 */
	public static String fristToUpper(String name) {
		String str = null;

		if (name.length() <= 1) {
			str = name.toUpperCase();
		} else {
			str = name.substring(0, 1).toUpperCase() + name.substring(1);
		}

		return str;
	}

	/**
	 * 全部转换大写xxXxx转化为XX_XXX
	 * 
	 * @param input
	 * @return
	 */
	public static String allToUpper(String input) {
		StringBuffer result = new StringBuffer();
		char[] temp = input.toCharArray();
		int length = temp.length;
		for (int i = 0; i < length; i++) {
			if (Character.isUpperCase(temp[i])) {
				result.append("_").append(temp[i]);
			} else {
				result.append(Character.toUpperCase(temp[i]));
			}

		}
		return result.toString();
	}

	/**
	 * 字符串转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 字节数组转换为字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * Tokenize the given {@code String} into a {@code String} array via a
	 * {@link StringTokenizer}.
	 * <p>
	 * Trims tokens and omits empty tokens.
	 * <p>
	 * The given {@code delimiters} string can consist of any number of delimiter
	 * characters. Each of those characters can be used to separate tokens. A
	 * delimiter is always a single character; for multi-character delimiters,
	 * consider using {@link #delimitedListToStringArray}.
	 * 
	 * @param str
	 *            the {@code String} to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as a {@code String} (each of
	 *            the characters is individually considered as a delimiter)
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given {@code String} into a {@code String} array via a
	 * {@link StringTokenizer}.
	 * <p>
	 * The given {@code delimiters} string can consist of any number of delimiter
	 * characters. Each of those characters can be used to separate tokens. A
	 * delimiter is always a single character; for multi-character delimiters,
	 * consider using {@link #delimitedListToStringArray}.
	 * 
	 * @param str
	 *            the {@code String} to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as a {@code String} (each of
	 *            the characters is individually considered as a delimiter)
	 * @param trimTokens
	 *            trim the tokens via {@link String#trim()}
	 * @param ignoreEmptyTokens
	 *            omit empty tokens from the result array (only applies to tokens
	 *            that are empty after trimming; StringTokenizer will not consider
	 *            subsequent delimiters as token in the first place).
	 * @return an array of the tokens ({@code null} if the input {@code String} was
	 *         {@code null})
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
			boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * Copy the given {@code Collection} into a {@code String} array.
	 * <p>
	 * The {@code Collection} must contain {@code String} elements only.
	 * 
	 * @param collection
	 *            the {@code Collection} to copy
	 * @return the {@code String} array ({@code null} if the supplied
	 *         {@code Collection} was {@code null})
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * eg:UserDAO->userDAO
	 * 
	 * @param name
	 * @return
	 */
	public static String decapitalize(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
			return name;
		}
		char chars[] = name.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}

	/**
	 * 将数据库字段转换为对象属性名 USER_NO --> userNo
	 * 
	 * @param name
	 * @return
	 */
	public static String h2s(String name) {
		StringBuffer sb = null;

		sb = new StringBuffer();

		if (name.indexOf("_") > 0) {

			char[] cs = name.toCharArray();
			int i = 0;

			// 特殊处理，属性aBc的set方法为setaBc
			if (cs[1] == '_') {
				sb.append(Character.toLowerCase(cs[0]));
				i = 1;
			}

			for (; i < cs.length; i++) {
				if (cs[i] == '_') {
					i++;
					sb.append(Character.toUpperCase(cs[i]));
				} else {
					sb.append(Character.toLowerCase(cs[i]));
				}
			}
		} else {
			sb.append(name.toLowerCase());
		}

		return sb.toString();
	}
}
