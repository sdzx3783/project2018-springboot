package com.hotent.core.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinyinUtil {
	public static String getPinyin(String chinese) {
		return getPinyinZh_CN(convertStringByChinese(chinese));
	}

	public static String getPinyinToUpperCase(String chinese) {
		return getPinyinZh_CN(convertStringByChinese(chinese)).toUpperCase();
	}

	public static String getPinyinToLowerCase(String chinese) {
		return getPinyinZh_CN(convertStringByChinese(chinese)).toLowerCase();
	}

	public static String getPinyinFirstToUpperCase(String chinese) {
		return getPinyin(chinese);
	}

	private static HanyuPinyinOutputFormat getDefaultFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
		return format;
	}

	private static Set<String> convertStringByChinese(String chinese) {
      char[] chars = chinese.toCharArray();
      if(chinese != null && !chinese.trim().equalsIgnoreCase("")) {
         char[] srcChar = chinese.toCharArray();
         String[][] temp = new String[chinese.length()][];

         for(int pingyinArray = 0; pingyinArray < srcChar.length; ++pingyinArray) {
            char pinyin = srcChar[pingyinArray];
            if(!String.valueOf(pinyin).matches("[\\一-\\龥]+") && !String.valueOf(pinyin).matches("[\\〇]")) {
               temp[pingyinArray] = new String[]{String.valueOf(srcChar[pingyinArray])};
            } else {
               try {
                  temp[pingyinArray] = PinyinHelper.toHanyuPinyinStringArray(chars[pingyinArray], getDefaultFormat());
               } catch (BadHanyuPinyinOutputFormatCombination arg6) {
                  arg6.printStackTrace();
               }
            }
         }

         String[] arg7 = exchange(temp);
         HashSet arg8 = new HashSet();

         for(int i = 0; i < arg7.length; ++i) {
            arg8.add(arg7[i]);
         }

         return arg8;
      } else {
         return null;
      }
   }

	private static String[] exchange(String[][] strJaggedArray) {
		String[][] temp = doExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] doExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len < 2) {
			return strJaggedArray;
		} else {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int index = 0;

			int i;
			for (int newArray = 0; newArray < len1; ++newArray) {
				for (i = 0; i < len2; ++i) {
					temp[index] = capitalize(strJaggedArray[0][newArray]) + capitalize(strJaggedArray[1][i]);
					++index;
				}
			}

			String[][] arg8 = new String[len - 1][];

			for (i = 2; i < len; ++i) {
				arg8[i - 1] = strJaggedArray[i];
			}

			arg8[0] = temp;
			return doExchange(arg8);
		}
	}

	private static String capitalize(String s) {
		char[] ch = s.toCharArray();
		if (ch != null && ch.length > 0 && ch[0] >= 97 && ch[0] <= 122) {
			ch[0] = (char) (ch[0] - 32);
		}

		return new String(ch);
	}

	private static String getPinyinZh_CN(Set<String> stringSet) {
		StringBuilder str = new StringBuilder();
		int i = 0;

		for (Iterator i$ = stringSet.iterator(); i$.hasNext(); ++i) {
			String s = (String) i$.next();
			if (i == stringSet.size() - 1) {
				str.append(s);
			} else {
				str.append(s + ",");
			}
		}

		return str.toString();
	}

	public static String getPinYinHeadChar(String chinese) {
		StringBuffer pinyin = new StringBuffer();
		if (chinese != null && !chinese.trim().equalsIgnoreCase("")) {
			for (int j = 0; j < chinese.length(); ++j) {
				char word = chinese.charAt(j);
				String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
				if (pinyinArray != null) {
					pinyin.append(pinyinArray[0].charAt(0));
				} else {
					pinyin.append(word);
				}
			}
		}

		return pinyin.toString();
	}

	public static String strFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}\':;\',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String getPinYinHeadCharFilter(String chinese) {
		return strFilter(getPinYinHeadChar(chinese));
	}

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(PinyinUtil.class);
		String str = "〇的输￥￥#s，ldsa";
		logger.info("小写输出：" + getPinyinToLowerCase(str));
		logger.info("大写输出：" + getPinyinToUpperCase(str));
		logger.info("首字母大写输出：" + getPinyinFirstToUpperCase(str));
		logger.info("返回中文的首字母输出：" + getPinYinHeadChar(str));
		logger.info("返回中文的首字母并过滤特殊字符输出：" + getPinYinHeadCharFilter(str));
	}
}