package com.hotent.core.util;

import com.hotent.core.util.AppConfigUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateFormatUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Character.UnicodeBlock;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class StringUtil {
	public static final char UNDERLINE = '_';

	public static String escape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (int i = 0; i < src.length(); ++i) {
			char j = src.charAt(i);
			if (!Character.isDigit(j) && !Character.isLowerCase(j) && !Character.isUpperCase(j)) {
				if (j < 256) {
					tmp.append("%");
					if (j < 16) {
						tmp.append("0");
					}

					tmp.append(Integer.toString(j, 16));
				} else {
					tmp.append("%u");
					tmp.append(Integer.toString(j, 16));
				}
			} else {
				tmp.append(j);
			}
		}

		return tmp.toString();
	}

	public static String replaceVariable(String template, Map<String, String> map) throws Exception {
		Pattern regex = Pattern.compile("\\{(.*?)\\}");

		String toReplace;
		String value;
		for (Matcher regexMatcher = regex.matcher(template); regexMatcher
				.find(); template = template.replace(toReplace, value)) {
			String key = regexMatcher.group(1);
			toReplace = regexMatcher.group(0);
			value = (String) map.get(key);
			if (value == null) {
				throw new Exception("没有找到[" + key + "]对应的变量值，请检查表变量配置!");
			}
		}

		return template;
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0;
		boolean pos = false;

		while (lastPos < src.length()) {
			int pos1 = src.indexOf("%", lastPos);
			if (pos1 == lastPos) {
				char ch;
				if (src.charAt(pos1 + 1) == 117) {
					ch = (char) Integer.parseInt(src.substring(pos1 + 2, pos1 + 6), 16);
					tmp.append(ch);
					lastPos = pos1 + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos1 + 1, pos1 + 3), 16);
					tmp.append(ch);
					lastPos = pos1 + 3;
				}
			} else if (pos1 == -1) {
				tmp.append(src.substring(lastPos));
				lastPos = src.length();
			} else {
				tmp.append(src.substring(lastPos, pos1));
				lastPos = pos1;
			}
		}

		return tmp.toString();
	}

	public static boolean isExist(String content, String begin, String end) {
		String tmp = content.toLowerCase();
		begin = begin.toLowerCase();
		end = end.toLowerCase();
		int beginIndex = tmp.indexOf(begin);
		int endIndex = tmp.indexOf(end);
		return beginIndex != -1 && endIndex != -1 && beginIndex < endIndex;
	}

	public static String trimPrefix(String toTrim, String trimStr) {
		while (toTrim.startsWith(trimStr)) {
			toTrim = toTrim.substring(trimStr.length());
		}

		return toTrim;
	}

	public static String trimSufffix(String toTrim, String trimStr) {
		while (toTrim.endsWith(trimStr)) {
			toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
		}

		return toTrim;
	}

	public static String trim(String toTrim, String trimStr) {
		return trimSufffix(trimPrefix(toTrim, trimStr), trimStr);
	}

	public static String escapeHtml(String content) {
		return StringEscapeUtils.escapeHtml(content);
	}

	public static String unescapeHtml(String content) {
		return StringEscapeUtils.unescapeHtml(content);
	}

	public static boolean isEmpty(String str) {
		return str == null ? true : str.trim().equals("");
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String replaceVariable(String template, String repaceStr) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		if (regexMatcher.find()) {
			String toReplace = regexMatcher.group(0);
			template = template.replace(toReplace, repaceStr);
		}

		return template;
	}

	public static String subString(String str, int len, String chopped) {
		if (str != null && !"".equals(str)) {
			char[] chars = str.toCharArray();
			int cnLen = len * 2;
			String tmp = "";
			boolean isOver = false;
			int iLen = 0;

			for (int i = 0; i < chars.length; ++i) {
				char iChar = chars[i];
				if (iChar <= 128) {
					++iLen;
				} else {
					iLen += 2;
				}

				if (iLen >= cnLen) {
					isOver = true;
					break;
				}

				tmp = tmp + String.valueOf(chars[i]);
			}

			if (isOver) {
				tmp = tmp + chopped;
			}

			return tmp;
		} else {
			return "";
		}
	}

	public static String subString(String str) {
		int len = 25;
		String tmp = AppConfigUtil.get("titleLen");
		String regex = "<(?:(?:/([^>]+)>)|(?:!--([\\S|\\s]*?)-->)|(?:([^\\s/>]+)\\s*((?:(?:\"[^\"]*\")|(?:\'[^\']*\')|[^\"\'<>])*)/?>))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return str;
		} else {
			if (isNotEmpty(tmp)) {
				len = Integer.parseInt(tmp);
			}

			return subString(str, len, "...");
		}
	}

	public static boolean isNumberic(String s) {
		if (StringUtils.isEmpty(s)) {
			return false;
		} else {
			boolean rtn = validByRegex("^[-+]{0,1}\\d*\\.{0,1}\\d+$", s);
			return rtn ? true : validByRegex("^0[x|X][\\da-eA-E]+$", s);
		}
	}

	public static boolean isInteger(String s) {
		boolean rtn = validByRegex("^[-+]{0,1}\\d*$", s);
		return rtn;
	}

	public static boolean isEmail(String s) {
		boolean rtn = validByRegex("(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", s);
		return rtn;
	}

	public static boolean isMobile(String s) {
		boolean rtn = validByRegex("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", s);
		return rtn;
	}

	public static boolean isPhone(String s) {
		boolean rtn = validByRegex("(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?", s);
		return rtn;
	}

	public static boolean isZip(String s) {
		boolean rtn = validByRegex("^[0-9]{6}$", s);
		return rtn;
	}

	public static boolean isQq(String s) {
		boolean rtn = validByRegex("^[1-9]\\d{4,9}$", s);
		return rtn;
	}

	public static boolean isIp(String s) {
		boolean rtn = validByRegex(
				"^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", s);
		return rtn;
	}

	public static boolean isChinese(String s) {
		boolean rtn = validByRegex("^[一-龥]+$", s);
		return rtn;
	}

	public static boolean isChrNum(String s) {
		boolean rtn = validByRegex("^([a-zA-Z0-9]+)$", s);
		return rtn;
	}

	public static boolean isUrl(String url) {
		return validByRegex("(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url);
	}

	/*public static Boolean isJson(String json) {
		if (isEmpty(json)) {
			return Boolean.valueOf(false);
		} else {
			try {
				JSONObject.fromObject(json);
				return Boolean.valueOf(true);
			} catch (JSONException arg3) {
				try {
					JSONArray.fromObject(json);
					return Boolean.valueOf(true);
				} catch (JSONException arg2) {
					return Boolean.valueOf(false);
				}
			}
		}
	}
*/
	public static boolean validByRegex(String regex, String input) {
		Pattern p = Pattern.compile(regex, 2);
		Matcher regexMatcher = p.matcher(input);
		return regexMatcher.find();
	}

	public static boolean isNumeric(String str) {
		int i = str.length();

		do {
			--i;
			if (i < 0) {
				return true;
			}
		} while (Character.isDigit(str.charAt(i)));

		return false;
	}

	public static String makeFirstLetterUpperCase(String newStr) {
		if (newStr.length() == 0) {
			return newStr;
		} else {
			char[] oneChar = new char[]{newStr.charAt(0)};
			String firstChar = new String(oneChar);
			return firstChar.toUpperCase() + newStr.substring(1);
		}
	}

	public static String makeFirstLetterLowerCase(String newStr) {
		if (newStr.length() == 0) {
			return newStr;
		} else {
			char[] oneChar = new char[]{newStr.charAt(0)};
			String firstChar = new String(oneChar);
			return firstChar.toLowerCase() + newStr.substring(1);
		}
	}

	public static String formatParamMsg(String message, Object... args) {
		for (int i = 0; i < args.length; ++i) {
			message = message.replace("{" + i + "}", args[i].toString());
		}

		return message;
	}

	public static String formatParamMsg(String message, Map<String, ?> params) {
		if (params == null) {
			return message;
		} else {
			Iterator keyIts = params.keySet().iterator();

			while (keyIts.hasNext()) {
				String key = (String) keyIts.next();
				Object val = params.get(key);
				if (val != null) {
					message = message.replace("${" + key + "}", val.toString());
				}
			}

			return message;
		}
	}

	public static StringBuilder formatMsg(CharSequence msgWithFormat, boolean autoQuote, Object... args) {
		int argsLen = args.length;
		boolean markFound = false;
		StringBuilder sb = new StringBuilder(msgWithFormat);
		if (argsLen > 0) {
			for (int sw = 0; sw < argsLen; ++sw) {
				String flag = "%" + (sw + 1);

				for (int idx = sb.indexOf(flag); idx >= 0; idx = sb.indexOf(flag)) {
					markFound = true;
					sb.replace(idx, idx + 2, toString(args[sw], autoQuote));
				}
			}

			if (args[argsLen - 1] instanceof Throwable) {
				StringWriter arg8 = new StringWriter();
				((Throwable) args[argsLen - 1]).printStackTrace(new PrintWriter(arg8));
				sb.append("\n").append(arg8.toString());
			} else if (argsLen == 1 && !markFound) {
				sb.append(args[argsLen - 1].toString());
			}
		}

		return sb;
	}

	public static StringBuilder formatMsg(String msgWithFormat, Object... args) {
		return formatMsg(new StringBuilder(msgWithFormat), true, args);
	}

	public static String toString(Object obj, boolean autoQuote) {
		StringBuilder sb = new StringBuilder();
		if (obj == null) {
			sb.append("NULL");
		} else if (obj instanceof Object[]) {
			for (int i = 0; i < ((Object[]) ((Object[]) obj)).length; ++i) {
				sb.append(((Object[]) ((Object[]) obj))[i]).append(", ");
			}

			if (sb.length() > 0) {
				sb.delete(sb.length() - 2, sb.length());
			}
		} else {
			sb.append(obj.toString());
		}

		if (autoQuote && sb.length() > 0 && (sb.charAt(0) != 91 || sb.charAt(sb.length() - 1) != 93)
				&& (sb.charAt(0) != 123 || sb.charAt(sb.length() - 1) != 125)) {
			sb.insert(0, "[").append("]");
		}

		return sb.toString();
	}

	public static String returnSpace(String str) {
		String space = "";
		if (!str.isEmpty()) {
			String[] path = str.split("\\.");

			for (int i = 0; i < path.length - 1; ++i) {
				space = space + "&nbsp;&emsp;";
			}
		}

		return space;
	}

	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest e = MessageDigest.getInstance("SHA-256");
			byte[] digest = e.digest(inputStr.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception arg2) {
			return null;
		}
	}

	public static synchronized String encryptMd5(String inputStr) {
		try {
			MessageDigest e = MessageDigest.getInstance("MD5");
			e.update(inputStr.getBytes());
			byte[] digest = e.digest();
			StringBuffer sb = new StringBuffer();
			byte[] arr$ = digest;
			int len$ = digest.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				byte b = arr$[i$];
				sb.append(Integer.toHexString(b & 255));
			}

			return sb.toString();
		} catch (Exception arg7) {
			return null;
		}
	}

	public static String getArrayAsString(List<String> arr) {
		if (arr != null && arr.size() != 0) {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < arr.size(); ++i) {
				if (i > 0) {
					sb.append(",");
				}

				sb.append((String) arr.get(i));
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static String getArrayAsString(String[] arr) {
		if (arr != null && arr.length != 0) {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < arr.length; ++i) {
				if (i > 0) {
					sb.append("#");
				}

				sb.append(arr[i]);
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static String getSetAsString(Set<?> set) {
		if (set != null && set.size() != 0) {
			StringBuffer sb = new StringBuffer();
			int i = 0;

			for (Iterator it = set.iterator(); it.hasNext(); sb.append(it.next().toString())) {
				if (i++ > 0) {
					sb.append(",");
				}
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static String hangeToBig(double value) {
		char[] hunit = new char[]{'拾', '佰', '仟'};
		char[] vunit = new char[]{'万', '亿'};
		char[] digit = new char[]{'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
		String zheng = "整";
		String jiao = "角";
		String fen = "分";
		char yuan = 22278;
		long midVal = (long) (value * 100.0D);
		String valStr = String.valueOf(midVal);
		String head = valStr.substring(0, valStr.length() - 2);
		int len = head.length();
		if (len > 12) {
			return "值过大";
		} else {
			String rail = valStr.substring(valStr.length() - 2);
			String prefix = "";
			String suffix = "";
			if (rail.equals("00")) {
				suffix = zheng;
			} else {
				suffix = digit[rail.charAt(0) - 48] + jiao + digit[rail.charAt(1) - 48] + fen;
			}

			char[] chDig = head.toCharArray();
			char zero = 48;
			byte zeroSerNum = 0;

			for (int i = 0; i < chDig.length; ++i) {
				int idx = (chDig.length - i - 1) % 4;
				int vidx = (chDig.length - i - 1) / 4;
				if (chDig[i] == 48) {
					++zeroSerNum;
					if (zero == 48) {
						zero = digit[0];
					} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
						prefix = prefix + vunit[vidx - 1];
						zero = 48;
					}
				} else {
					zeroSerNum = 0;
					if (zero != 48) {
						prefix = prefix + zero;
						zero = 48;
					}

					prefix = prefix + digit[chDig[i] - 48];
					if (idx > 0) {
						prefix = prefix + hunit[idx - 1];
					}

					if (idx == 0 && vidx > 0) {
						prefix = prefix + vunit[vidx - 1];
					}
				}
			}

			if (prefix.length() > 0) {
				prefix = prefix + yuan;
			}

			return prefix + suffix;
		}
	}

	public static String jsonUnescape(String str) {
		return str.replace("&quot;", "\"").replace("&nuot;", "\n");
	}

	public static String htmlEntityToString(String dataStr) {
		dataStr = dataStr.replace("&apos;", "\'").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<")
				.replace("&amp;", "&");
		int start = 0;
		boolean end = false;
		StringBuffer buffer = new StringBuffer();

		while (start > -1) {
			byte system = 10;
			if (start == 0) {
				int charStr = dataStr.indexOf("&#");
				if (start != charStr) {
					start = charStr;
				}

				if (start > 0) {
					buffer.append(dataStr.substring(0, start));
				}
			}

			int end1 = dataStr.indexOf(";", start + 2);
			String charStr1 = "";
			char length;
			if (end1 != -1) {
				charStr1 = dataStr.substring(start + 2, end1);
				length = charStr1.charAt(0);
				if (length == 120 || length == 88) {
					system = 16;
					charStr1 = charStr1.substring(1);
				}
			}

			try {
				if (isNotEmpty(charStr1)) {
					length = (char) Integer.parseInt(charStr1, system);
					buffer.append((new Character(length)).toString());
				}
			} catch (NumberFormatException arg6) {
				;
			}

			start = dataStr.indexOf("&#", end1);
			if (start - end1 > 1) {
				buffer.append(dataStr.substring(end1 + 1, start));
			}

			if (start == -1) {
				int length1 = dataStr.length();
				if (end1 + 1 != length1) {
					buffer.append(dataStr.substring(end1 + 1, length1));
				}
			}
		}

		return buffer.toString();
	}

	public static String stringToHtmlEntity(String str) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			switch (c) {
				case '\n' :
					sb.append(c);
					break;
				case '\"' :
					sb.append("&quot;");
					break;
				case '&' :
					sb.append("&amp;");
					break;
				case '\'' :
					sb.append("&apos;");
					break;
				case '<' :
					sb.append("&lt;");
					break;
				case '>' :
					sb.append("&gt;");
					break;
				default :
					if (c >= 32 && c <= 126) {
						sb.append(c);
					} else {
						sb.append("&#x");
						sb.append(Integer.toString(c, 16));
						sb.append(';');
					}
			}
		}

		return sb.toString();
	}

	public static String encodingString(String str, String from, String to) {
		String result;
		try {
			result = new String(str.getBytes(from), to);
		} catch (Exception arg4) {
			result = str;
		}

		return result;
	}

	public static String comdify(String value) {
		DecimalFormat df = null;
		if (value.indexOf(".") > 0) {
			int number = value.length() - value.indexOf(".") - 1;
			switch (number) {
				case 0 :
					df = new DecimalFormat("###,##0");
					break;
				case 1 :
					df = new DecimalFormat("###,##0.0");
					break;
				case 2 :
					df = new DecimalFormat("###,##0.00");
					break;
				case 3 :
					df = new DecimalFormat("###,##0.000");
					break;
				case 4 :
					df = new DecimalFormat("###,##0.0000");
					break;
				default :
					df = new DecimalFormat("###,##0.00000");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}

		double number1 = 0.0D;

		try {
			number1 = Double.parseDouble(value);
		} catch (Exception arg4) {
			number1 = 0.0D;
		}

		return df.format(number1);
	}

	public static String convertScriptLine(String arg, Boolean flag) {
		if (StringUtils.isEmpty(arg)) {
			return arg;
		} else {
			String origStr = "\n";
			String targStr = "/n";
			if (!flag.booleanValue()) {
				origStr = "/n";
				targStr = "\n";
			}

			String[] args = arg.split(origStr);
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < args.length; ++i) {
				sb.append(args[i]);
				if (args.length != i + 1) {
					sb.append(targStr);
				}
			}

			return sb.toString();
		}
	}

	public static String convertLine(String arg, Boolean flag) {
		if (StringUtils.isEmpty(arg)) {
			return arg;
		} else {
			String origStr = "\n";
			String targStr = "/n";
			if (!flag.booleanValue()) {
				origStr = "/n";
				targStr = "\n";
			}

			String[] args = arg.split(origStr);
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < args.length; ++i) {
				sb.append(StringUtils.deleteWhitespace(args[i]));
				if (args.length != i + 1) {
					sb.append(targStr);
				}
			}

			return sb.toString();
		}
	}

	public static String deleteWhitespaceLine(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return arg;
		} else {
			String origStr = "\n";
			String[] args = arg.split(origStr);
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < args.length; ++i) {
				sb.append(StringUtils.deleteWhitespace(args[i]));
				if (args.length != i + 1) {
					sb.append(origStr);
				}
			}

			return sb.toString();
		}
	}

	public static String parseText(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return arg;
		} else {
			String[] args = arg.split("\n");
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < args.length; ++i) {
				sb.append(args[i]);
				if (args.length != i + 1) {
					sb.append("</br>");
				}
			}

			return sb.toString();
		}
	}

	public static String replaceNotVisable(String str) {
		char[] ary = str.toCharArray();
		ArrayList list = new ArrayList();

		for (int aryc = 0; aryc < ary.length; ++aryc) {
			char arycc = ary[aryc];
			if (isViable(arycc)) {
				list.add(Character.valueOf((char) arycc));
			}
		}

		Object[] arg5 = (Object[]) list.toArray();
		char[] arg6 = new char[arg5.length];

		for (int out = 0; out < arg5.length; ++out) {
			arg6[out] = ((Character) arg5[out]).charValue();
		}

		String arg7 = new String(arg6);
		return arg7;
	}

	private static boolean isViable(int i) {
		return i == 0 || i == 13 || i >= 9 && i <= 10 || i >= 11 && i <= 12 || i >= 28 && i <= 126
				|| i >= 19968 && i <= '龥';
	}

	public static String replaceAll(String toReplace, String replace, String replaceBy) {
		replaceBy = replaceBy.replaceAll("\\\\", "&#92");
		replaceBy = replaceBy.replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot");
		return toReplace.replaceAll(replace, replaceBy);
	}

	public static String stringFormat2Json(String json) {
		StringBuilder sb = new StringBuilder();
		int size = json.length();

		for (int i = 0; i < size; ++i) {
			char c = json.charAt(i);
			switch (c) {
				case '\b' :
					sb.append("\\b");
					break;
				case '\t' :
					sb.append("\\t");
					break;
				case '\n' :
					sb.append("\\n");
					break;
				case '' :
				default :
					sb.append(c);
					break;
				case '\f' :
					sb.append("\\f");
					break;
				case '\r' :
					sb.append("\\r");
			}
		}

		return sb.toString();
	}

	public static String getNumber(Object value, Object isShowComdify, Object decimalValue, Object coinValue) {
		if (value == null) {
			return "";
		} else {
			String val = value.toString();
			if (isShowComdify != null) {
				short foundPoint = 0;
				String foundNegativePoint = isShowComdify.toString();
				boolean intStr = foundNegativePoint.matches("^[0-9]*$");
				if (intStr) {
					foundPoint = Short.parseShort(foundNegativePoint);
				} else if ("true".equals(foundNegativePoint)) {
					foundPoint = 1;
				} else if ("false".equals(foundNegativePoint)) {
					foundPoint = 0;
				}

				Double decimal = Double.valueOf(Double.parseDouble(val));
				DecimalFormat tmp = new DecimalFormat("");
				val = tmp.format(decimal);
				if (foundPoint != 1) {
					val = val.replace(",", "");
				}
			}

			if (decimalValue != null) {
				int foundPoint1 = Integer.parseInt(decimalValue.toString());
				if (foundPoint1 > 0) {
					int foundNegativePoint1 = val.indexOf(".");
					if (foundNegativePoint1 == -1) {
						val = val + "." + getZeroLen(foundPoint1);
					} else {
						String intStr1 = val.substring(0, val.indexOf("."));
						String decimal1 = val.substring(val.indexOf(".") + 1);
						if (decimal1.length() > foundPoint1) {
							Double tmp2 = Double.valueOf(Double.parseDouble(val.replace(",", "")));
							DecimalFormat df = new DecimalFormat("");
							df.setMaximumFractionDigits(foundPoint1);
							String tmp1 = df.format(tmp2);
							if (tmp1.indexOf(".") == -1) {
								val = intStr1 + "." + getZeroLen(foundPoint1);
							} else {
								decimal1 = tmp1.substring(tmp1.indexOf(".") + 1);
								val = intStr1 + "." + decimal1;
							}
						} else if (decimal1.length() < foundPoint1) {
							int tmp3 = foundPoint1 - decimal1.length();
							val = val + getZeroLen(tmp3);
						}
					}
				}
			}

			boolean foundPoint2 = val.matches("^\\.\\d+");
			if (foundPoint2) {
				val = 0 + val;
			}

			boolean foundNegativePoint2 = val.matches("^\\-.\\d+");
			if (foundNegativePoint2) {
				val = val.replaceFirst("-", "-0");
			}

			if (coinValue != null) {
				val = coinValue.toString() + val;
			}

			return val;
		}
	}

	private static String getZeroLen(int len) {
		String str = "";

		for (int i = 0; i < len; ++i) {
			str = str + "0";
		}

		return str;
	}

	public static String removeHTMLTag(String htmlStr) {
		if (isEmpty(htmlStr)) {
			return "";
		} else {
			htmlStr = Jsoup.clean(htmlStr, Whitelist.none());
			htmlStr = htmlEntityToString(htmlStr);
			return htmlStr.trim();
		}
	}

	public static boolean contain(String str, String searchStr) {
		return contain(str, searchStr, ",", true);
	}

	public static boolean contain(String str, String searchStr, String argumentSeparator, boolean isIgnoreCase) {
		if (isEmpty(str)) {
			return false;
		} else {
			if (isEmpty(argumentSeparator)) {
				argumentSeparator = ",";
			}

			String[] aryStr = str.split(argumentSeparator);
			return contain(aryStr, searchStr, isIgnoreCase);
		}
	}

	public static boolean contain(String[] aryStr, String searchStr, boolean isIgnoreCase) {
		if (BeanUtils.isEmpty(aryStr)) {
			return false;
		} else {
			String[] arr$ = aryStr;
			int len$ = aryStr.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String str = arr$[i$];
				if (isIgnoreCase) {
					if (str.equalsIgnoreCase(searchStr)) {
						return true;
					}
				} else if (str.equals(searchStr)) {
					return true;
				}
			}

			return false;
		}
	}

	public static int getCount(String str, int type) {
		int len = str.length();
		int chineseCount = 0;
		int letterCount = 0;
		int blankCount = 0;
		int numCount = 0;
		int otherCount = 0;

		for (int i = 0; i < len; ++i) {
			char tem = str.charAt(i);
			UnicodeBlock ub = UnicodeBlock.of(tem);
			if (tem > 65 && tem < 90 || tem > 97 && tem < 122) {
				++letterCount;
			} else if (tem == 32) {
				++blankCount;
			} else if (tem > 48 && tem < 57) {
				++numCount;
			} else if (ub != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS && ub != UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					&& ub != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					&& ub != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
					&& ub != UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					&& ub != UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS && ub != UnicodeBlock.GENERAL_PUNCTUATION) {
				++otherCount;
			} else {
				++chineseCount;
			}
		}

		switch (type) {
			case -1 :
				return chineseCount;
			case 0 :
				return letterCount;
			case 1 :
				return blankCount;
			case 2 :
				return numCount;
			case 3 :
				return otherCount;
			default :
				return otherCount;
		}
	}

	public static int getTotalSize(String str) {
		int chineseCount = getCount(str, -1);
		int letterCount = getCount(str, 0);
		int blankCount = getCount(str, 1);
		int numCount = getCount(str, 2);
		int otherCount = getCount(str, 3);
		return chineseCount + (letterCount + numCount) / 3 + blankCount / 4 + otherCount * 3 / 4;
	}

	public static String getUrl(String url, String params) {
		if (isEmpty(url)) {
			return url;
		} else {
			if (url.indexOf("?") > 0) {
				if (isNotEmpty(params)) {
					url = url + "&" + params;
				} else {
					url = url + "?" + params;
				}
			} else if (isNotEmpty(params)) {
				url = url + "?" + params;
			}

			return url;
		}
	}

	public static String camelToUnderline(String param) {
		if (param != null && !"".equals(param.trim())) {
			int len = param.length();
			StringBuilder sb = new StringBuilder(len);

			for (int i = 0; i < len; ++i) {
				char c = param.charAt(i);
				if (Character.isUpperCase(c)) {
					sb.append('_');
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append(c);
				}
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static String underlineToCamel(String param) {
		if (param != null && !"".equals(param.trim())) {
			int len = param.length();
			StringBuilder sb = new StringBuilder(len);

			for (int i = 0; i < len; ++i) {
				char c = param.charAt(i);
				if (c == 95) {
					++i;
					if (i < len) {
						sb.append(Character.toUpperCase(param.charAt(i)));
					}
				} else {
					sb.append(c);
				}
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static List<String> stringToList(String str) {
		ArrayList list = new ArrayList();
		if (str != null && !str.equals("")) {
			if (str.contains("[") || str.contains("]")) {
				String[] strs = str.split(",");
				String[] arr$ = strs;
				int len$ = strs.length;

				for (int i$ = 0; i$ < len$; ++i$) {
					String str1 = arr$[i$];
					if (str1.contains("[")) {
						str1 = str1.replace("[", "");
					}

					if (str1.contains("]")) {
						str1 = str1.replace("]", "");
					}

					str1 = str1.replaceAll("\"", "");
					list.add(str1);
				}

				return list;
			}

			list.add(str);
		}

		return list;
	}

	public static String getParam(String mrthor) {
		Pattern p = Pattern.compile("\\(.*?\\)");
		Matcher m = p.matcher(mrthor);

		String param;
		for (param = null; m.find(); param = param.replace("\"", "")) {
			param = m.group().replaceAll("\\(\\)", "");
			param = param.replace("(", "");
			param = param.replace(")", "");
		}

		return param;
	}

	public static Object parserObject(Object obj, String type) {
		if (BeanUtils.isEmpty(obj)) {
			return null;
		} else {
			Object val = obj;

			try {
				String e = obj.toString();
				if (type.equalsIgnoreCase("string")) {
					val = e;
				} else if (type.equalsIgnoreCase("int")) {
					val = Integer.valueOf(Integer.parseInt(e));
				} else if (type.equalsIgnoreCase("float")) {
					val = Float.valueOf(Float.parseFloat(e));
				} else if (type.equalsIgnoreCase("double")) {
					val = Double.valueOf(Double.parseDouble(e));
				} else if (type.equalsIgnoreCase("byte")) {
					val = Byte.valueOf(Byte.parseByte(e));
				} else if (type.equalsIgnoreCase("short")) {
					val = Short.valueOf(Short.parseShort(e));
				} else if (type.equalsIgnoreCase("long")) {
					val = Long.valueOf(Long.parseLong(e));
				} else if (type.equalsIgnoreCase("boolean")) {
					if (StringUtils.isNumeric(e)) {
						Boolean val1 = Boolean.valueOf(Integer.parseInt(e) == 1);
					}

					val = Boolean.valueOf(Boolean.parseBoolean(e));
				} else if (type.equalsIgnoreCase("date")) {
					val = DateFormatUtil.parse(e);
				} else {
					val = e;
				}
			} catch (Exception arg3) {
				;
			}

			return val;
		}
	}

	public static String join(String[] aryStr, String separator) {
		if (aryStr == null) {
			return null;
		} else if (aryStr.length == 1) {
			return aryStr[0];
		} else {
			String str = "";

			for (int i = 0; i < aryStr.length; ++i) {
				if (i == 0) {
					str = str + aryStr[i];
				} else {
					str = str + separator + aryStr[i];
				}
			}

			return str;
		}
	}

	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}
}