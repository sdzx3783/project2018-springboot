/*    1:     */ package com.hotent.core.util;
/*    2:     */ 
/*    3:     */ import java.io.PrintWriter;
/*    4:     */ import java.io.StringWriter;
/*    5:     */ import java.security.MessageDigest;
/*    6:     */ import java.text.DecimalFormat;
/*    7:     */ import java.util.ArrayList;
/*    8:     */ import java.util.Iterator;
/*    9:     */ import java.util.List;
/*   10:     */ import java.util.Map;
/*   11:     */ import java.util.Set;
/*   12:     */ import java.util.regex.Matcher;
/*   13:     */ import java.util.regex.Pattern;
/*   14:     */ import net.sf.json.JSONArray;
/*   15:     */ import net.sf.json.JSONException;
/*   16:     */ import net.sf.json.JSONObject;
/*   17:     */ import org.apache.commons.codec.binary.Base64;
/*   18:     */ import org.apache.commons.lang3.StringEscapeUtils;
/*   19:     */ import org.apache.commons.lang3.StringUtils;
/*   20:     */ import org.jsoup.Jsoup;
/*   21:     */ import org.jsoup.safety.Whitelist;
/*   22:     */ 
/*   23:     */ public class StringUtil
/*   24:     */ {
/*   25:     */   public static final char UNDERLINE = '_';
/*   26:     */   
/*   27:     */   public static String escape(String src)
/*   28:     */   {
/*   29:  41 */     StringBuffer tmp = new StringBuffer();
/*   30:  42 */     tmp.ensureCapacity(src.length() * 6);
/*   31:  44 */     for (int i = 0; i < src.length(); i++)
/*   32:     */     {
/*   33:  46 */       char j = src.charAt(i);
/*   34:  48 */       if ((Character.isDigit(j)) || (Character.isLowerCase(j)) || (Character.isUpperCase(j)))
/*   35:     */       {
/*   36:  50 */         tmp.append(j);
/*   37:     */       }
/*   38:  51 */       else if (j < 'Ā')
/*   39:     */       {
/*   40:  52 */         tmp.append("%");
/*   41:  53 */         if (j < '\020') {
/*   42:  54 */           tmp.append("0");
/*   43:     */         }
/*   44:  55 */         tmp.append(Integer.toString(j, 16));
/*   45:     */       }
/*   46:     */       else
/*   47:     */       {
/*   48:  57 */         tmp.append("%u");
/*   49:  58 */         tmp.append(Integer.toString(j, 16));
/*   50:     */       }
/*   51:     */     }
/*   52:  61 */     return tmp.toString();
/*   53:     */   }
/*   54:     */   
/*   55:     */   public static String replaceVariable(String template, Map<String, String> map)
/*   56:     */     throws Exception
/*   57:     */   {
/*   58:  75 */     Pattern regex = Pattern.compile("\\{(.*?)\\}");
/*   59:  76 */     Matcher regexMatcher = regex.matcher(template);
/*   60:  77 */     while (regexMatcher.find())
/*   61:     */     {
/*   62:  78 */       String key = regexMatcher.group(1);
/*   63:  79 */       String toReplace = regexMatcher.group(0);
/*   64:  80 */       String value = (String)map.get(key);
/*   65:  81 */       if (value != null) {
/*   66:  82 */         template = template.replace(toReplace, value);
/*   67:     */       } else {
/*   68:  84 */         throw new Exception("没有找到[" + key + "]对应的变量值，请检查表变量配置!");
/*   69:     */       }
/*   70:     */     }
/*   71:  87 */     return template;
/*   72:     */   }
/*   73:     */   
/*   74:     */   public static String unescape(String src)
/*   75:     */   {
/*   76:  97 */     StringBuffer tmp = new StringBuffer();
/*   77:  98 */     tmp.ensureCapacity(src.length());
/*   78:  99 */     int lastPos = 0;int pos = 0;
/*   79: 101 */     while (lastPos < src.length())
/*   80:     */     {
/*   81: 102 */       pos = src.indexOf("%", lastPos);
/*   82: 103 */       if (pos == lastPos)
/*   83:     */       {
/*   84: 104 */         if (src.charAt(pos + 1) == 'u')
/*   85:     */         {
/*   86: 105 */           char ch = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
/*   87:     */           
/*   88: 107 */           tmp.append(ch);
/*   89: 108 */           lastPos = pos + 6;
/*   90:     */         }
/*   91:     */         else
/*   92:     */         {
/*   93: 110 */           char ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
/*   94:     */           
/*   95: 112 */           tmp.append(ch);
/*   96: 113 */           lastPos = pos + 3;
/*   97:     */         }
/*   98:     */       }
/*   99: 116 */       else if (pos == -1)
/*  100:     */       {
/*  101: 117 */         tmp.append(src.substring(lastPos));
/*  102: 118 */         lastPos = src.length();
/*  103:     */       }
/*  104:     */       else
/*  105:     */       {
/*  106: 120 */         tmp.append(src.substring(lastPos, pos));
/*  107: 121 */         lastPos = pos;
/*  108:     */       }
/*  109:     */     }
/*  110: 125 */     return tmp.toString();
/*  111:     */   }
/*  112:     */   
/*  113:     */   public static boolean isExist(String content, String begin, String end)
/*  114:     */   {
/*  115: 140 */     String tmp = content.toLowerCase();
/*  116: 141 */     begin = begin.toLowerCase();
/*  117: 142 */     end = end.toLowerCase();
/*  118: 143 */     int beginIndex = tmp.indexOf(begin);
/*  119: 144 */     int endIndex = tmp.indexOf(end);
/*  120: 145 */     if ((beginIndex != -1) && (endIndex != -1) && (beginIndex < endIndex)) {
/*  121: 146 */       return true;
/*  122:     */     }
/*  123: 147 */     return false;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public static String trimPrefix(String toTrim, String trimStr)
/*  127:     */   {
/*  128: 158 */     while (toTrim.startsWith(trimStr)) {
/*  129: 159 */       toTrim = toTrim.substring(trimStr.length());
/*  130:     */     }
/*  131: 161 */     return toTrim;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public static String trimSufffix(String toTrim, String trimStr)
/*  135:     */   {
/*  136: 172 */     while (toTrim.endsWith(trimStr)) {
/*  137: 173 */       toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
/*  138:     */     }
/*  139: 175 */     return toTrim;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public static String trim(String toTrim, String trimStr)
/*  143:     */   {
/*  144: 186 */     return trimSufffix(trimPrefix(toTrim, trimStr), trimStr);
/*  145:     */   }
/*  146:     */   
/*  147:     */   public static String escapeHtml(String content)
/*  148:     */   {
/*  149: 196 */     return StringEscapeUtils.escapeHtml(content);
/*  150:     */   }
/*  151:     */   
/*  152:     */   public static String unescapeHtml(String content)
/*  153:     */   {
/*  154: 206 */     return StringEscapeUtils.unescapeHtml(content);
/*  155:     */   }
/*  156:     */   
/*  157:     */   public static boolean isEmpty(String str)
/*  158:     */   {
/*  159: 216 */     if (str == null) {
/*  160: 217 */       return true;
/*  161:     */     }
/*  162: 218 */     if (str.trim().equals("")) {
/*  163: 219 */       return true;
/*  164:     */     }
/*  165: 220 */     return false;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public static boolean isNotEmpty(String str)
/*  169:     */   {
/*  170: 230 */     return !isEmpty(str);
/*  171:     */   }
/*  172:     */   
/*  173:     */   public static String replaceVariable(String template, String repaceStr)
/*  174:     */   {
/*  175: 234 */     Pattern regex = Pattern.compile("\\{(.*?)\\}");
/*  176: 235 */     Matcher regexMatcher = regex.matcher(template);
/*  177: 236 */     if (regexMatcher.find())
/*  178:     */     {
/*  179: 237 */       String toReplace = regexMatcher.group(0);
/*  180: 238 */       template = template.replace(toReplace, repaceStr);
/*  181:     */     }
/*  182: 240 */     return template;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public static String subString(String str, int len, String chopped)
/*  186:     */   {
/*  187: 251 */     if ((str == null) || ("".equals(str))) {
/*  188: 252 */       return "";
/*  189:     */     }
/*  190: 253 */     char[] chars = str.toCharArray();
/*  191: 254 */     int cnLen = len * 2;
/*  192: 255 */     String tmp = "";
/*  193: 256 */     boolean isOver = false;
/*  194: 257 */     int iLen = 0;
/*  195: 258 */     for (int i = 0; i < chars.length; i++)
/*  196:     */     {
/*  197: 259 */       int iChar = chars[i];
/*  198: 260 */       if (iChar <= 128) {
/*  199: 261 */         iLen += 1;
/*  200:     */       } else {
/*  201: 263 */         iLen += 2;
/*  202:     */       }
/*  203: 264 */       if (iLen >= cnLen)
/*  204:     */       {
/*  205: 265 */         isOver = true;
/*  206: 266 */         break;
/*  207:     */       }
/*  208: 269 */       tmp = tmp + String.valueOf(chars[i]);
/*  209:     */     }
/*  210: 271 */     if (isOver) {
/*  211: 272 */       tmp = tmp + chopped;
/*  212:     */     }
/*  213: 274 */     return tmp;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public static String subString(String str)
/*  217:     */   {
/*  218: 284 */     int len = 25;
/*  219: 285 */     String tmp = AppConfigUtil.get("titleLen");
/*  220: 286 */     String regex = "<(?:(?:/([^>]+)>)|(?:!--([\\S|\\s]*?)-->)|(?:([^\\s/>]+)\\s*((?:(?:\"[^\"]*\")|(?:'[^']*')|[^\"'<>])*)/?>))";
/*  221: 287 */     Pattern pattern = Pattern.compile(regex);
/*  222: 288 */     Matcher matcher = pattern.matcher(str);
/*  223: 289 */     if (matcher.find()) {
/*  224: 290 */       return str;
/*  225:     */     }
/*  226: 292 */     if (isNotEmpty(tmp)) {
/*  227: 293 */       len = Integer.parseInt(tmp);
/*  228:     */     }
/*  229: 295 */     return subString(str, len, "...");
/*  230:     */   }
/*  231:     */   
/*  232:     */   public static boolean isNumberic(String s)
/*  233:     */   {
/*  234: 307 */     if (StringUtils.isEmpty(s)) {
/*  235: 308 */       return false;
/*  236:     */     }
/*  237: 309 */     boolean rtn = validByRegex("^[-+]{0,1}\\d*\\.{0,1}\\d+$", s);
/*  238: 310 */     if (rtn) {
/*  239: 311 */       return true;
/*  240:     */     }
/*  241: 313 */     return validByRegex("^0[x|X][\\da-eA-E]+$", s);
/*  242:     */   }
/*  243:     */   
/*  244:     */   public static boolean isInteger(String s)
/*  245:     */   {
/*  246: 323 */     boolean rtn = validByRegex("^[-+]{0,1}\\d*$", s);
/*  247: 324 */     return rtn;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public static boolean isEmail(String s)
/*  251:     */   {
/*  252: 335 */     boolean rtn = validByRegex("(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", s);
/*  253:     */     
/*  254: 337 */     return rtn;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public static boolean isMobile(String s)
/*  258:     */   {
/*  259: 347 */     boolean rtn = validByRegex("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", s);
/*  260:     */     
/*  261: 349 */     return rtn;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public static boolean isPhone(String s)
/*  265:     */   {
/*  266: 359 */     boolean rtn = validByRegex("(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?", s);
/*  267:     */     
/*  268: 361 */     return rtn;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public static boolean isZip(String s)
/*  272:     */   {
/*  273: 371 */     boolean rtn = validByRegex("^[0-9]{6}$", s);
/*  274: 372 */     return rtn;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public static boolean isQq(String s)
/*  278:     */   {
/*  279: 382 */     boolean rtn = validByRegex("^[1-9]\\d{4,9}$", s);
/*  280: 383 */     return rtn;
/*  281:     */   }
/*  282:     */   
/*  283:     */   public static boolean isIp(String s)
/*  284:     */   {
/*  285: 393 */     boolean rtn = validByRegex("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", s);
/*  286:     */     
/*  287:     */ 
/*  288: 396 */     return rtn;
/*  289:     */   }
/*  290:     */   
/*  291:     */   public static boolean isChinese(String s)
/*  292:     */   {
/*  293: 406 */     boolean rtn = validByRegex("^[一-龥]+$", s);
/*  294: 407 */     return rtn;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public static boolean isChrNum(String s)
/*  298:     */   {
/*  299: 417 */     boolean rtn = validByRegex("^([a-zA-Z0-9]+)$", s);
/*  300: 418 */     return rtn;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public static boolean isUrl(String url)
/*  304:     */   {
/*  305: 428 */     return validByRegex("(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url);
/*  306:     */   }
/*  307:     */   
/*  308:     */   public static Boolean isJson(String json)
/*  309:     */   {
/*  310: 440 */     if (isEmpty(json)) {
/*  311: 441 */       return Boolean.valueOf(false);
/*  312:     */     }
/*  313:     */     try
/*  314:     */     {
/*  315: 443 */       JSONObject.fromObject(json);
/*  316: 444 */       return Boolean.valueOf(true);
/*  317:     */     }
/*  318:     */     catch (JSONException e)
/*  319:     */     {
/*  320:     */       try
/*  321:     */       {
/*  322: 447 */         JSONArray.fromObject(json);
/*  323: 448 */         return Boolean.valueOf(true);
/*  324:     */       }
/*  325:     */       catch (JSONException ex) {}
/*  326:     */     }
/*  327: 450 */     return Boolean.valueOf(false);
/*  328:     */   }
/*  329:     */   
/*  330:     */   public static boolean validByRegex(String regex, String input)
/*  331:     */   {
/*  332: 463 */     Pattern p = Pattern.compile(regex, 2);
/*  333: 464 */     Matcher regexMatcher = p.matcher(input);
/*  334: 465 */     return regexMatcher.find();
/*  335:     */   }
/*  336:     */   
/*  337:     */   public static boolean isNumeric(String str)
/*  338:     */   {
/*  339: 475 */     int i = str.length();
/*  340:     */     do
/*  341:     */     {
/*  342: 475 */       i--;
/*  343: 475 */       if (i < 0) {
/*  344:     */         break;
/*  345:     */       }
/*  346: 476 */     } while (Character.isDigit(str.charAt(i)));
/*  347: 477 */     return false;
/*  348:     */     
/*  349:     */ 
/*  350: 480 */     return true;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public static String makeFirstLetterUpperCase(String newStr)
/*  354:     */   {
/*  355: 490 */     if (newStr.length() == 0) {
/*  356: 491 */       return newStr;
/*  357:     */     }
/*  358: 493 */     char[] oneChar = new char[1];
/*  359: 494 */     oneChar[0] = newStr.charAt(0);
/*  360: 495 */     String firstChar = new String(oneChar);
/*  361: 496 */     return firstChar.toUpperCase() + newStr.substring(1);
/*  362:     */   }
/*  363:     */   
/*  364:     */   public static String makeFirstLetterLowerCase(String newStr)
/*  365:     */   {
/*  366: 506 */     if (newStr.length() == 0) {
/*  367: 507 */       return newStr;
/*  368:     */     }
/*  369: 509 */     char[] oneChar = new char[1];
/*  370: 510 */     oneChar[0] = newStr.charAt(0);
/*  371: 511 */     String firstChar = new String(oneChar);
/*  372: 512 */     return firstChar.toLowerCase() + newStr.substring(1);
/*  373:     */   }
/*  374:     */   
/*  375:     */   public static String formatParamMsg(String message, Object... args)
/*  376:     */   {
/*  377: 523 */     for (int i = 0; i < args.length; i++) {
/*  378: 524 */       message = message.replace("{" + i + "}", args[i].toString());
/*  379:     */     }
/*  380: 526 */     return message;
/*  381:     */   }
/*  382:     */   
/*  383:     */   public static String formatParamMsg(String message, Map<String, ?> params)
/*  384:     */   {
/*  385: 536 */     if (params == null) {
/*  386: 537 */       return message;
/*  387:     */     }
/*  388: 538 */     Iterator<String> keyIts = params.keySet().iterator();
/*  389: 539 */     while (keyIts.hasNext())
/*  390:     */     {
/*  391: 540 */       String key = (String)keyIts.next();
/*  392: 541 */       Object val = params.get(key);
/*  393: 542 */       if (val != null) {
/*  394: 543 */         message = message.replace("${" + key + "}", val.toString());
/*  395:     */       }
/*  396:     */     }
/*  397: 546 */     return message;
/*  398:     */   }
/*  399:     */   
/*  400:     */   public static StringBuilder formatMsg(CharSequence msgWithFormat, boolean autoQuote, Object... args)
/*  401:     */   {
/*  402: 561 */     int argsLen = args.length;
/*  403: 562 */     boolean markFound = false;
/*  404:     */     
/*  405: 564 */     StringBuilder sb = new StringBuilder(msgWithFormat);
/*  406: 566 */     if (argsLen > 0)
/*  407:     */     {
/*  408: 567 */       for (int i = 0; i < argsLen; i++)
/*  409:     */       {
/*  410: 568 */         String flag = "%" + (i + 1);
/*  411: 569 */         int idx = sb.indexOf(flag);
/*  412: 571 */         while (idx >= 0)
/*  413:     */         {
/*  414: 572 */           markFound = true;
/*  415: 573 */           sb.replace(idx, idx + 2, toString(args[i], autoQuote));
/*  416: 574 */           idx = sb.indexOf(flag);
/*  417:     */         }
/*  418:     */       }
/*  419: 578 */       if ((args[(argsLen - 1)] instanceof Throwable))
/*  420:     */       {
/*  421: 579 */         StringWriter sw = new StringWriter();
/*  422: 580 */         ((Throwable)args[(argsLen - 1)]).printStackTrace(new PrintWriter(sw));
/*  423:     */         
/*  424: 582 */         sb.append("\n").append(sw.toString());
/*  425:     */       }
/*  426: 583 */       else if ((argsLen == 1) && (!markFound))
/*  427:     */       {
/*  428: 584 */         sb.append(args[(argsLen - 1)].toString());
/*  429:     */       }
/*  430:     */     }
/*  431: 587 */     return sb;
/*  432:     */   }
/*  433:     */   
/*  434:     */   public static StringBuilder formatMsg(String msgWithFormat, Object... args)
/*  435:     */   {
/*  436: 591 */     return formatMsg(new StringBuilder(msgWithFormat), true, args);
/*  437:     */   }
/*  438:     */   
/*  439:     */   public static String toString(Object obj, boolean autoQuote)
/*  440:     */   {
/*  441: 595 */     StringBuilder sb = new StringBuilder();
/*  442: 596 */     if (obj == null)
/*  443:     */     {
/*  444: 597 */       sb.append("NULL");
/*  445:     */     }
/*  446: 599 */     else if ((obj instanceof Object[]))
/*  447:     */     {
/*  448: 600 */       for (int i = 0; i < ((Object[])obj).length; i++) {
/*  449: 601 */         sb.append(((Object[])(Object[])obj)[i]).append(", ");
/*  450:     */       }
/*  451: 603 */       if (sb.length() > 0) {
/*  452: 604 */         sb.delete(sb.length() - 2, sb.length());
/*  453:     */       }
/*  454:     */     }
/*  455:     */     else
/*  456:     */     {
/*  457: 607 */       sb.append(obj.toString());
/*  458:     */     }
/*  459: 610 */     if ((autoQuote) && (sb.length() > 0) && ((sb.charAt(0) != '[') || (sb.charAt(sb.length() - 1) != ']')) && ((sb.charAt(0) != '{') || (sb.charAt(sb.length() - 1) != '}'))) {
/*  460: 614 */       sb.insert(0, "[").append("]");
/*  461:     */     }
/*  462: 616 */     return sb.toString();
/*  463:     */   }
/*  464:     */   
/*  465:     */   public static String returnSpace(String str)
/*  466:     */   {
/*  467: 620 */     String space = "";
/*  468: 621 */     if (!str.isEmpty())
/*  469:     */     {
/*  470: 622 */       String[] path = str.split("\\.");
/*  471: 623 */       for (int i = 0; i < path.length - 1; i++) {
/*  472: 624 */         space = space + "&nbsp;&emsp;";
/*  473:     */       }
/*  474:     */     }
/*  475: 627 */     return space;
/*  476:     */   }
/*  477:     */   
/*  478:     */   public static synchronized String encryptSha256(String inputStr)
/*  479:     */   {
/*  480:     */     try
/*  481:     */     {
/*  482: 639 */       MessageDigest md = MessageDigest.getInstance("SHA-256");
/*  483: 640 */       byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
/*  484: 641 */       return new String(Base64.encodeBase64(digest));
/*  485:     */     }
/*  486:     */     catch (Exception e) {}
/*  487: 643 */     return null;
/*  488:     */   }
/*  489:     */   
/*  490:     */   public static synchronized String encryptMd5(String inputStr)
/*  491:     */   {
/*  492:     */     try
/*  493:     */     {
/*  494: 649 */       MessageDigest md = MessageDigest.getInstance("MD5");
/*  495: 650 */       md.update(inputStr.getBytes());
/*  496: 651 */       byte[] digest = md.digest();
/*  497: 652 */       StringBuffer sb = new StringBuffer();
/*  498: 653 */       for (byte b : digest) {
/*  499: 654 */         sb.append(Integer.toHexString(b & 0xFF));
/*  500:     */       }
/*  501: 657 */       return sb.toString();
/*  502:     */     }
/*  503:     */     catch (Exception e) {}
/*  504: 659 */     return null;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public static String getArrayAsString(List<String> arr)
/*  508:     */   {
/*  509: 670 */     if ((arr == null) || (arr.size() == 0)) {
/*  510: 671 */       return "";
/*  511:     */     }
/*  512: 672 */     StringBuffer sb = new StringBuffer();
/*  513: 673 */     for (int i = 0; i < arr.size(); i++)
/*  514:     */     {
/*  515: 674 */       if (i > 0) {
/*  516: 675 */         sb.append(",");
/*  517:     */       }
/*  518: 676 */       sb.append((String)arr.get(i));
/*  519:     */     }
/*  520: 678 */     return sb.toString();
/*  521:     */   }
/*  522:     */   
/*  523:     */   public static String getArrayAsString(String[] arr)
/*  524:     */   {
/*  525: 688 */     if ((arr == null) || (arr.length == 0)) {
/*  526: 689 */       return "";
/*  527:     */     }
/*  528: 690 */     StringBuffer sb = new StringBuffer();
/*  529: 691 */     for (int i = 0; i < arr.length; i++)
/*  530:     */     {
/*  531: 692 */       if (i > 0) {
/*  532: 693 */         sb.append("#");
/*  533:     */       }
/*  534: 694 */       sb.append(arr[i]);
/*  535:     */     }
/*  536: 696 */     return sb.toString();
/*  537:     */   }
/*  538:     */   
/*  539:     */   public static String getSetAsString(Set<?> set)
/*  540:     */   {
/*  541: 706 */     if ((set == null) || (set.size() == 0)) {
/*  542: 707 */       return "";
/*  543:     */     }
/*  544: 708 */     StringBuffer sb = new StringBuffer();
/*  545: 709 */     int i = 0;
/*  546: 710 */     Iterator<?> it = set.iterator();
/*  547: 711 */     while (it.hasNext())
/*  548:     */     {
/*  549: 712 */       if (i++ > 0) {
/*  550: 713 */         sb.append(",");
/*  551:     */       }
/*  552: 714 */       sb.append(it.next().toString());
/*  553:     */     }
/*  554: 716 */     return sb.toString();
/*  555:     */   }
/*  556:     */   
/*  557:     */   public static String hangeToBig(double value)
/*  558:     */   {
/*  559: 727 */     char[] hunit = { '拾', '佰', '仟' };
/*  560: 728 */     char[] vunit = { '万', '亿' };
/*  561: 729 */     char[] digit = { 38646, '壹', 36144, '叁', 32902, '伍', 38470, '柒', '捌', '玖' };
/*  562: 730 */     String zheng = "整";
/*  563: 731 */     String jiao = "角";
/*  564: 732 */     String fen = "分";
/*  565: 733 */     char yuan = '圆';
/*  566: 734 */     long midVal = (value * 100.0D);
/*  567: 735 */     String valStr = String.valueOf(midVal);
/*  568:     */     
/*  569: 737 */     String head = valStr.substring(0, valStr.length() - 2);
/*  570: 738 */     int len = head.length();
/*  571: 739 */     if (len > 12) {
/*  572: 740 */       return "值过大";
/*  573:     */     }
/*  574: 742 */     String rail = valStr.substring(valStr.length() - 2);
/*  575:     */     
/*  576: 744 */     String prefix = "";
/*  577: 745 */     String suffix = "";
/*  578: 747 */     if (rail.equals("00")) {
/*  579: 748 */       suffix = zheng;
/*  580:     */     } else {
/*  581: 750 */       suffix = digit[(rail.charAt(0) - '0')] + jiao + digit[(rail.charAt(1) - '0')] + fen;
/*  582:     */     }
/*  583: 754 */     char[] chDig = head.toCharArray();
/*  584: 755 */     char zero = '0';
/*  585: 756 */     byte zeroSerNum = 0;
/*  586: 757 */     for (int i = 0; i < chDig.length; i++)
/*  587:     */     {
/*  588: 758 */       int idx = (chDig.length - i - 1) % 4;
/*  589: 759 */       int vidx = (chDig.length - i - 1) / 4;
/*  590: 760 */       if (chDig[i] == '0')
/*  591:     */       {
/*  592: 761 */         zeroSerNum = (byte)(zeroSerNum + 1);
/*  593: 762 */         if (zero == '0')
/*  594:     */         {
/*  595: 763 */           zero = digit[0];
/*  596:     */         }
/*  597: 764 */         else if ((idx == 0) && (vidx > 0) && (zeroSerNum < 4))
/*  598:     */         {
/*  599: 765 */           prefix = prefix + vunit[(vidx - 1)];
/*  600: 766 */           zero = '0';
/*  601:     */         }
/*  602:     */       }
/*  603:     */       else
/*  604:     */       {
/*  605: 770 */         zeroSerNum = 0;
/*  606: 771 */         if (zero != '0')
/*  607:     */         {
/*  608: 772 */           prefix = prefix + zero;
/*  609: 773 */           zero = '0';
/*  610:     */         }
/*  611: 775 */         prefix = prefix + digit[(chDig[i] - '0')];
/*  612: 776 */         if (idx > 0) {
/*  613: 777 */           prefix = prefix + hunit[(idx - 1)];
/*  614:     */         }
/*  615: 778 */         if ((idx == 0) && (vidx > 0)) {
/*  616: 779 */           prefix = prefix + vunit[(vidx - 1)];
/*  617:     */         }
/*  618:     */       }
/*  619:     */     }
/*  620: 783 */     if (prefix.length() > 0) {
/*  621: 784 */       prefix = prefix + yuan;
/*  622:     */     }
/*  623: 785 */     return prefix + suffix;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public static String jsonUnescape(String str)
/*  627:     */   {
/*  628: 796 */     return str.replace("&quot;", "\"").replace("&nuot;", "\n");
/*  629:     */   }
/*  630:     */   
/*  631:     */   public static String htmlEntityToString(String dataStr)
/*  632:     */   {
/*  633: 806 */     dataStr = dataStr.replace("&apos;", "'").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&");
/*  634:     */     
/*  635:     */ 
/*  636:     */ 
/*  637: 810 */     int start = 0;
/*  638: 811 */     int end = 0;
/*  639: 812 */     StringBuffer buffer = new StringBuffer();
/*  640: 814 */     while (start > -1)
/*  641:     */     {
/*  642: 815 */       int system = 10;
/*  643: 816 */       if (start == 0)
/*  644:     */       {
/*  645: 817 */         int t = dataStr.indexOf("&#");
/*  646: 818 */         if (start != t) {
/*  647: 819 */           start = t;
/*  648:     */         }
/*  649: 821 */         if (start > 0) {
/*  650: 822 */           buffer.append(dataStr.substring(0, start));
/*  651:     */         }
/*  652:     */       }
/*  653: 825 */       end = dataStr.indexOf(";", start + 2);
/*  654: 826 */       String charStr = "";
/*  655: 827 */       if (end != -1)
/*  656:     */       {
/*  657: 828 */         charStr = dataStr.substring(start + 2, end);
/*  658:     */         
/*  659: 830 */         char s = charStr.charAt(0);
/*  660: 831 */         if ((s == 'x') || (s == 'X'))
/*  661:     */         {
/*  662: 832 */           system = 16;
/*  663: 833 */           charStr = charStr.substring(1);
/*  664:     */         }
/*  665:     */       }
/*  666:     */       try
/*  667:     */       {
/*  668: 838 */         if (isNotEmpty(charStr))
/*  669:     */         {
/*  670: 839 */           char letter = (char)Integer.parseInt(charStr, system);
/*  671: 840 */           buffer.append(new Character(letter).toString());
/*  672:     */         }
/*  673:     */       }
/*  674:     */       catch (NumberFormatException e) {}
/*  675: 848 */       start = dataStr.indexOf("&#", end);
/*  676: 849 */       if (start - end > 1) {
/*  677: 850 */         buffer.append(dataStr.substring(end + 1, start));
/*  678:     */       }
/*  679: 854 */       if (start == -1)
/*  680:     */       {
/*  681: 855 */         int length = dataStr.length();
/*  682: 856 */         if (end + 1 != length) {
/*  683: 857 */           buffer.append(dataStr.substring(end + 1, length));
/*  684:     */         }
/*  685:     */       }
/*  686:     */     }
/*  687: 861 */     return buffer.toString();
/*  688:     */   }
/*  689:     */   
/*  690:     */   public static String stringToHtmlEntity(String str)
/*  691:     */   {
/*  692: 871 */     StringBuffer sb = new StringBuffer();
/*  693: 872 */     for (int i = 0; i < str.length(); i++)
/*  694:     */     {
/*  695: 873 */       char c = str.charAt(i);
/*  696: 875 */       switch (c)
/*  697:     */       {
/*  698:     */       case '\n': 
/*  699: 877 */         sb.append(c);
/*  700: 878 */         break;
/*  701:     */       case '<': 
/*  702: 881 */         sb.append("&lt;");
/*  703: 882 */         break;
/*  704:     */       case '>': 
/*  705: 885 */         sb.append("&gt;");
/*  706: 886 */         break;
/*  707:     */       case '&': 
/*  708: 889 */         sb.append("&amp;");
/*  709: 890 */         break;
/*  710:     */       case '\'': 
/*  711: 893 */         sb.append("&apos;");
/*  712: 894 */         break;
/*  713:     */       case '"': 
/*  714: 897 */         sb.append("&quot;");
/*  715: 898 */         break;
/*  716:     */       default: 
/*  717: 901 */         if ((c < ' ') || (c > '~'))
/*  718:     */         {
/*  719: 902 */           sb.append("&#x");
/*  720: 903 */           sb.append(Integer.toString(c, 16));
/*  721: 904 */           sb.append(';');
/*  722:     */         }
/*  723:     */         else
/*  724:     */         {
/*  725: 906 */           sb.append(c);
/*  726:     */         }
/*  727:     */         break;
/*  728:     */       }
/*  729:     */     }
/*  730: 910 */     return sb.toString();
/*  731:     */   }
/*  732:     */   
/*  733:     */   public static String encodingString(String str, String from, String to)
/*  734:     */   {
/*  735: 925 */     String result = str;
/*  736:     */     try
/*  737:     */     {
/*  738: 927 */       result = new String(str.getBytes(from), to);
/*  739:     */     }
/*  740:     */     catch (Exception e)
/*  741:     */     {
/*  742: 929 */       result = str;
/*  743:     */     }
/*  744: 931 */     return result;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public static String comdify(String value)
/*  748:     */   {
/*  749: 938 */     DecimalFormat df = null;
/*  750: 939 */     if (value.indexOf(".") > 0)
/*  751:     */     {
/*  752: 940 */       int i = value.length() - value.indexOf(".") - 1;
/*  753: 941 */       switch (i)
/*  754:     */       {
/*  755:     */       case 0: 
/*  756: 943 */         df = new DecimalFormat("###,##0");
/*  757: 944 */         break;
/*  758:     */       case 1: 
/*  759: 946 */         df = new DecimalFormat("###,##0.0");
/*  760: 947 */         break;
/*  761:     */       case 2: 
/*  762: 949 */         df = new DecimalFormat("###,##0.00");
/*  763: 950 */         break;
/*  764:     */       case 3: 
/*  765: 952 */         df = new DecimalFormat("###,##0.000");
/*  766: 953 */         break;
/*  767:     */       case 4: 
/*  768: 955 */         df = new DecimalFormat("###,##0.0000");
/*  769: 956 */         break;
/*  770:     */       default: 
/*  771: 958 */         df = new DecimalFormat("###,##0.00000");
/*  772:     */       }
/*  773:     */     }
/*  774:     */     else
/*  775:     */     {
/*  776: 963 */       df = new DecimalFormat("###,##0");
/*  777:     */     }
/*  778: 965 */     double number = 0.0D;
/*  779:     */     try
/*  780:     */     {
/*  781: 967 */       number = Double.parseDouble(value);
/*  782:     */     }
/*  783:     */     catch (Exception e)
/*  784:     */     {
/*  785: 969 */       number = 0.0D;
/*  786:     */     }
/*  787: 971 */     return df.format(number);
/*  788:     */   }
/*  789:     */   
/*  790:     */   public static String convertScriptLine(String arg, Boolean flag)
/*  791:     */   {
/*  792: 984 */     if (StringUtils.isEmpty(arg)) {
/*  793: 985 */       return arg;
/*  794:     */     }
/*  795: 986 */     String origStr = "\n";String targStr = "/n";
/*  796: 987 */     if (!flag.booleanValue())
/*  797:     */     {
/*  798: 988 */       origStr = "/n";
/*  799: 989 */       targStr = "\n";
/*  800:     */     }
/*  801: 991 */     String[] args = arg.split(origStr);
/*  802: 992 */     StringBuffer sb = new StringBuffer();
/*  803: 993 */     for (int i = 0; i < args.length; i++)
/*  804:     */     {
/*  805: 994 */       sb.append(args[i]);
/*  806: 995 */       if (args.length != i + 1) {
/*  807: 996 */         sb.append(targStr);
/*  808:     */       }
/*  809:     */     }
/*  810: 998 */     return sb.toString();
/*  811:     */   }
/*  812:     */   
/*  813:     */   public static String convertLine(String arg, Boolean flag)
/*  814:     */   {
/*  815:1011 */     if (StringUtils.isEmpty(arg)) {
/*  816:1012 */       return arg;
/*  817:     */     }
/*  818:1013 */     String origStr = "\n";String targStr = "/n";
/*  819:1014 */     if (!flag.booleanValue())
/*  820:     */     {
/*  821:1015 */       origStr = "/n";
/*  822:1016 */       targStr = "\n";
/*  823:     */     }
/*  824:1018 */     String[] args = arg.split(origStr);
/*  825:1019 */     StringBuffer sb = new StringBuffer();
/*  826:1020 */     for (int i = 0; i < args.length; i++)
/*  827:     */     {
/*  828:1021 */       sb.append(StringUtils.deleteWhitespace(args[i]));
/*  829:1022 */       if (args.length != i + 1) {
/*  830:1023 */         sb.append(targStr);
/*  831:     */       }
/*  832:     */     }
/*  833:1025 */     return sb.toString();
/*  834:     */   }
/*  835:     */   
/*  836:     */   public static String deleteWhitespaceLine(String arg)
/*  837:     */   {
/*  838:1036 */     if (StringUtils.isEmpty(arg)) {
/*  839:1037 */       return arg;
/*  840:     */     }
/*  841:1038 */     String origStr = "\n";
/*  842:1039 */     String[] args = arg.split(origStr);
/*  843:1040 */     StringBuffer sb = new StringBuffer();
/*  844:1041 */     for (int i = 0; i < args.length; i++)
/*  845:     */     {
/*  846:1042 */       sb.append(StringUtils.deleteWhitespace(args[i]));
/*  847:1043 */       if (args.length != i + 1) {
/*  848:1044 */         sb.append(origStr);
/*  849:     */       }
/*  850:     */     }
/*  851:1046 */     return sb.toString();
/*  852:     */   }
/*  853:     */   
/*  854:     */   public static String parseText(String arg)
/*  855:     */   {
/*  856:1056 */     if (StringUtils.isEmpty(arg)) {
/*  857:1057 */       return arg;
/*  858:     */     }
/*  859:1058 */     String[] args = arg.split("\n");
/*  860:1059 */     StringBuffer sb = new StringBuffer();
/*  861:1060 */     for (int i = 0; i < args.length; i++)
/*  862:     */     {
/*  863:1061 */       sb.append(args[i]);
/*  864:1062 */       if (args.length != i + 1) {
/*  865:1063 */         sb.append("</br>");
/*  866:     */       }
/*  867:     */     }
/*  868:1065 */     return sb.toString();
/*  869:     */   }
/*  870:     */   
/*  871:     */   public static String replaceNotVisable(String str)
/*  872:     */   {
/*  873:1075 */     char[] ary = str.toCharArray();
/*  874:1076 */     List<Character> list = new ArrayList();
/*  875:1077 */     for (int i = 0; i < ary.length; i++)
/*  876:     */     {
/*  877:1078 */       int c = ary[i];
/*  878:1079 */       if (isViable(c)) {
/*  879:1081 */         list.add(Character.valueOf((char)c));
/*  880:     */       }
/*  881:     */     }
/*  882:1083 */     Object[] aryc = (Object[])list.toArray();
/*  883:1084 */     char[] arycc = new char[aryc.length];
/*  884:1085 */     for (int i = 0; i < aryc.length; i++) {
/*  885:1086 */       arycc[i] = ((Character)aryc[i]).charValue();
/*  886:     */     }
/*  887:1088 */     String out = new String(arycc);
/*  888:1089 */     return out;
/*  889:     */   }
/*  890:     */   
/*  891:     */   private static boolean isViable(int i)
/*  892:     */   {
/*  893:1093 */     if ((i == 0) || (i == 13) || ((i >= 9) && (i <= 10)) || ((i >= 11) && (i <= 12)) || ((i >= 28) && (i <= 126)) || ((i >= 19968) && (i <= 40869))) {
/*  894:1095 */       return true;
/*  895:     */     }
/*  896:1097 */     return false;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public static String replaceAll(String toReplace, String replace, String replaceBy)
/*  900:     */   {
/*  901:1110 */     replaceBy = replaceBy.replaceAll("\\\\", "&#92");
/*  902:1111 */     replaceBy = replaceBy.replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot");
/*  903:1112 */     return toReplace.replaceAll(replace, replaceBy);
/*  904:     */   }
/*  905:     */   
/*  906:     */   public static String stringFormat2Json(String json)
/*  907:     */   {
/*  908:1116 */     StringBuilder sb = new StringBuilder();
/*  909:1117 */     int size = json.length();
/*  910:1118 */     for (int i = 0; i < size; i++)
/*  911:     */     {
/*  912:1119 */       char c = json.charAt(i);
/*  913:1120 */       switch (c)
/*  914:     */       {
/*  915:     */       case '\b': 
/*  916:1122 */         sb.append("\\b");
/*  917:1123 */         break;
/*  918:     */       case '\f': 
/*  919:1125 */         sb.append("\\f");
/*  920:1126 */         break;
/*  921:     */       case '\n': 
/*  922:1128 */         sb.append("\\n");
/*  923:1129 */         break;
/*  924:     */       case '\r': 
/*  925:1131 */         sb.append("\\r");
/*  926:1132 */         break;
/*  927:     */       case '\t': 
/*  928:1134 */         sb.append("\\t");
/*  929:1135 */         break;
/*  930:     */       case '\013': 
/*  931:     */       default: 
/*  932:1137 */         sb.append(c);
/*  933:     */       }
/*  934:     */     }
/*  935:1141 */     return sb.toString();
/*  936:     */   }
/*  937:     */   
/*  938:     */   public static String getNumber(Object value, Object isShowComdify, Object decimalValue, Object coinValue)
/*  939:     */   {
/*  940:1159 */     if (value == null) {
/*  941:1160 */       return "";
/*  942:     */     }
/*  943:1161 */     String val = value.toString();
/*  944:1163 */     if (isShowComdify != null)
/*  945:     */     {
/*  946:1164 */       int result = 0;
/*  947:     */       
/*  948:1166 */       String temp = isShowComdify.toString();
/*  949:1167 */       boolean isInteger = temp.matches("^[0-9]*$");
/*  950:1168 */       if (isInteger) {
/*  951:1169 */         result = Short.parseShort(temp);
/*  952:1171 */       } else if ("true".equals(temp)) {
/*  953:1172 */         result = 1;
/*  954:1173 */       } else if ("false".equals(temp)) {
/*  955:1174 */         result = 0;
/*  956:     */       }
/*  957:1177 */       Double douvalue = Double.valueOf(Double.parseDouble(val));
/*  958:1178 */       DecimalFormat df = new DecimalFormat("");
/*  959:1179 */       val = df.format(douvalue);
/*  960:1180 */       if (result != 1) {
/*  961:1181 */         val = val.replace(",", "");
/*  962:     */       }
/*  963:     */     }
/*  964:1185 */     if (decimalValue != null)
/*  965:     */     {
/*  966:1186 */       int len = Integer.parseInt(decimalValue.toString());
/*  967:1188 */       if (len > 0)
/*  968:     */       {
/*  969:1189 */         int idx = val.indexOf(".");
/*  970:1190 */         if (idx == -1)
/*  971:     */         {
/*  972:1191 */           val = val + "." + getZeroLen(len);
/*  973:     */         }
/*  974:     */         else
/*  975:     */         {
/*  976:1193 */           String intStr = val.substring(0, val.indexOf("."));
/*  977:1194 */           String decimal = val.substring(val.indexOf(".") + 1);
/*  978:1195 */           if (decimal.length() > len)
/*  979:     */           {
/*  980:1196 */             Double douvalue = Double.valueOf(Double.parseDouble(val.replace(",", "")));
/*  981:     */             
/*  982:1198 */             DecimalFormat df = new DecimalFormat("");
/*  983:1199 */             df.setMaximumFractionDigits(len);
/*  984:1200 */             String tmp = df.format(douvalue);
/*  985:1201 */             if (tmp.indexOf(".") == -1)
/*  986:     */             {
/*  987:1202 */               val = intStr + "." + getZeroLen(len);
/*  988:     */             }
/*  989:     */             else
/*  990:     */             {
/*  991:1204 */               decimal = tmp.substring(tmp.indexOf(".") + 1);
/*  992:1205 */               val = intStr + "." + decimal;
/*  993:     */             }
/*  994:     */           }
/*  995:1207 */           else if (decimal.length() < len)
/*  996:     */           {
/*  997:1208 */             int tmp = len - decimal.length();
/*  998:1209 */             val = val + getZeroLen(tmp);
/*  999:     */           }
/* 1000:     */         }
/* 1001:     */       }
/* 1002:     */     }
/* 1003:1216 */     boolean foundPoint = val.matches("^\\.\\d+");
/* 1004:1217 */     if (foundPoint) {
/* 1005:1218 */       val = 0 + val;
/* 1006:     */     }
/* 1007:1222 */     boolean foundNegativePoint = val.matches("^\\-.\\d+");
/* 1008:1223 */     if (foundNegativePoint) {
/* 1009:1224 */       val = val.replaceFirst("-", "-0");
/* 1010:     */     }
/* 1011:1227 */     if (coinValue != null) {
/* 1012:1228 */       val = coinValue.toString() + val;
/* 1013:     */     }
/* 1014:1230 */     return val;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   private static String getZeroLen(int len)
/* 1018:     */   {
/* 1019:1234 */     String str = "";
/* 1020:1235 */     for (int i = 0; i < len; i++) {
/* 1021:1236 */       str = str + "0";
/* 1022:     */     }
/* 1023:1238 */     return str;
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public static String removeHTMLTag(String htmlStr)
/* 1027:     */   {
/* 1028:1250 */     if (isEmpty(htmlStr)) {
/* 1029:1251 */       return "";
/* 1030:     */     }
/* 1031:1252 */     htmlStr = Jsoup.clean(htmlStr, Whitelist.none());
/* 1032:1253 */     htmlStr = htmlEntityToString(htmlStr);
/* 1033:1254 */     return htmlStr.trim();
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public static boolean contain(String str, String searchStr)
/* 1037:     */   {
/* 1038:1264 */     return contain(str, searchStr, ",", true);
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public static boolean contain(String str, String searchStr, String argumentSeparator, boolean isIgnoreCase)
/* 1042:     */   {
/* 1043:1274 */     if (isEmpty(str)) {
/* 1044:1275 */       return false;
/* 1045:     */     }
/* 1046:1276 */     if (isEmpty(argumentSeparator)) {
/* 1047:1277 */       argumentSeparator = ",";
/* 1048:     */     }
/* 1049:1278 */     String[] aryStr = str.split(argumentSeparator);
/* 1050:1279 */     return contain(aryStr, searchStr, isIgnoreCase);
/* 1051:     */   }
/* 1052:     */   
/* 1053:     */   public static boolean contain(String[] aryStr, String searchStr, boolean isIgnoreCase)
/* 1054:     */   {
/* 1055:1290 */     if (BeanUtils.isEmpty(aryStr)) {
/* 1056:1291 */       return false;
/* 1057:     */     }
/* 1058:1292 */     for (String str : aryStr) {
/* 1059:1293 */       if (isIgnoreCase)
/* 1060:     */       {
/* 1061:1294 */         if (str.equalsIgnoreCase(searchStr)) {
/* 1062:1295 */           return true;
/* 1063:     */         }
/* 1064:     */       }
/* 1065:1297 */       else if (str.equals(searchStr)) {
/* 1066:1298 */         return true;
/* 1067:     */       }
/* 1068:     */     }
/* 1069:1302 */     return false;
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public static int getCount(String str, int type)
/* 1073:     */   {
/* 1074:1312 */     int len = str.length();
/* 1075:1313 */     int chineseCount = 0;
/* 1076:1314 */     int letterCount = 0;
/* 1077:1315 */     int blankCount = 0;
/* 1078:1316 */     int numCount = 0;
/* 1079:1317 */     int otherCount = 0;
/* 1080:1318 */     for (int i = 0; i < len; i++)
/* 1081:     */     {
/* 1082:1319 */       char tem = str.charAt(i);
/* 1083:1320 */       Character.UnicodeBlock ub = Character.UnicodeBlock.of(tem);
/* 1084:1321 */       if (((tem > 'A') && (tem < 'Z')) || ((tem > 'a') && (tem < 'z'))) {
/* 1085:1322 */         letterCount++;
/* 1086:1323 */       } else if (tem == ' ') {
/* 1087:1324 */         blankCount++;
/* 1088:1325 */       } else if ((tem > '0') && (tem < '9')) {
/* 1089:1326 */         numCount++;
/* 1090:1327 */       } else if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) || (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)) {
/* 1091:1331 */         chineseCount++;
/* 1092:     */       } else {
/* 1093:1333 */         otherCount++;
/* 1094:     */       }
/* 1095:     */     }
/* 1096:1336 */     switch (type)
/* 1097:     */     {
/* 1098:     */     case -1: 
/* 1099:1337 */       return chineseCount;
/* 1100:     */     case 0: 
/* 1101:1338 */       return letterCount;
/* 1102:     */     case 1: 
/* 1103:1339 */       return blankCount;
/* 1104:     */     case 2: 
/* 1105:1340 */       return numCount;
/* 1106:     */     case 3: 
/* 1107:1341 */       return otherCount;
/* 1108:     */     }
/* 1109:1343 */     return otherCount;
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public static int getTotalSize(String str)
/* 1113:     */   {
/* 1114:1347 */     int chineseCount = getCount(str, -1);
/* 1115:1348 */     int letterCount = getCount(str, 0);
/* 1116:1349 */     int blankCount = getCount(str, 1);
/* 1117:1350 */     int numCount = getCount(str, 2);
/* 1118:1351 */     int otherCount = getCount(str, 3);
/* 1119:1352 */     return chineseCount + (letterCount + numCount) / 3 + blankCount / 4 + otherCount * 3 / 4;
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public static String getUrl(String url, String params)
/* 1123:     */   {
/* 1124:1362 */     if (isEmpty(url)) {
/* 1125:1363 */       return url;
/* 1126:     */     }
/* 1127:1364 */     if (url.indexOf("?") > 0)
/* 1128:     */     {
/* 1129:1365 */       if (isNotEmpty(params)) {
/* 1130:1366 */         url = url + "&" + params;
/* 1131:     */       } else {
/* 1132:1368 */         url = url + "?" + params;
/* 1133:     */       }
/* 1134:     */     }
/* 1135:1369 */     else if (isNotEmpty(params)) {
/* 1136:1370 */       url = url + "?" + params;
/* 1137:     */     }
/* 1138:1371 */     return url;
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public static String camelToUnderline(String param)
/* 1142:     */   {
/* 1143:1382 */     if ((param == null) || ("".equals(param.trim()))) {
/* 1144:1383 */       return "";
/* 1145:     */     }
/* 1146:1385 */     int len = param.length();
/* 1147:1386 */     StringBuilder sb = new StringBuilder(len);
/* 1148:1387 */     for (int i = 0; i < len; i++)
/* 1149:     */     {
/* 1150:1388 */       char c = param.charAt(i);
/* 1151:1389 */       if (Character.isUpperCase(c))
/* 1152:     */       {
/* 1153:1390 */         sb.append('_');
/* 1154:1391 */         sb.append(Character.toLowerCase(c));
/* 1155:     */       }
/* 1156:     */       else
/* 1157:     */       {
/* 1158:1393 */         sb.append(c);
/* 1159:     */       }
/* 1160:     */     }
/* 1161:1396 */     return sb.toString();
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public static String underlineToCamel(String param)
/* 1165:     */   {
/* 1166:1404 */     if ((param == null) || ("".equals(param.trim()))) {
/* 1167:1405 */       return "";
/* 1168:     */     }
/* 1169:1407 */     int len = param.length();
/* 1170:1408 */     StringBuilder sb = new StringBuilder(len);
/* 1171:1409 */     for (int i = 0; i < len; i++)
/* 1172:     */     {
/* 1173:1410 */       char c = param.charAt(i);
/* 1174:1411 */       if (c == '_')
/* 1175:     */       {
/* 1176:1412 */         i++;
/* 1177:1412 */         if (i < len) {
/* 1178:1413 */           sb.append(Character.toUpperCase(param.charAt(i)));
/* 1179:     */         }
/* 1180:     */       }
/* 1181:     */       else
/* 1182:     */       {
/* 1183:1416 */         sb.append(c);
/* 1184:     */       }
/* 1185:     */     }
/* 1186:1419 */     return sb.toString();
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public static List<String> stringToList(String str)
/* 1190:     */   {
/* 1191:1422 */     List<String> list = new ArrayList();
/* 1192:1423 */     if ((str != null) && (!str.equals("")))
/* 1193:     */     {
/* 1194:1424 */       if ((str.contains("[")) || (str.contains("]")))
/* 1195:     */       {
/* 1196:1425 */         String[] strs = str.split(",");
/* 1197:1426 */         for (String str1 : strs)
/* 1198:     */         {
/* 1199:1427 */           if (str1.contains("[")) {
/* 1200:1428 */             str1 = str1.replace("[", "");
/* 1201:     */           }
/* 1202:1430 */           if (str1.contains("]")) {
/* 1203:1431 */             str1 = str1.replace("]", "");
/* 1204:     */           }
/* 1205:1433 */           str1 = str1.replaceAll("\"", "");
/* 1206:1434 */           list.add(str1);
/* 1207:     */         }
/* 1208:1436 */         return list;
/* 1209:     */       }
/* 1210:1438 */       list.add(str);
/* 1211:     */     }
/* 1212:1441 */     return list;
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   public static String getParam(String mrthor)
/* 1216:     */   {
/* 1217:1447 */     Pattern p = Pattern.compile("\\(.*?\\)");
/* 1218:1448 */     Matcher m = p.matcher(mrthor);
/* 1219:1449 */     String param = null;
/* 1220:1450 */     while (m.find())
/* 1221:     */     {
/* 1222:1451 */       param = m.group().replaceAll("\\(\\)", "");
/* 1223:1452 */       param = param.replace("(", "");
/* 1224:1453 */       param = param.replace(")", "");
/* 1225:1454 */       param = param.replace("\"", "");
/* 1226:     */     }
/* 1227:1456 */     return param;
/* 1228:     */   }
/* 1229:     */   
/* 1230:     */   public static Object parserObject(Object obj, String type)
/* 1231:     */   {
/* 1232:1466 */     if (BeanUtils.isEmpty(obj)) {
/* 1233:1467 */       return null;
/* 1234:     */     }
/* 1235:1468 */     Object val = obj;
/* 1236:     */     try
/* 1237:     */     {
/* 1238:1470 */       String str = obj.toString();
/* 1239:1471 */       if (type.equalsIgnoreCase("string"))
/* 1240:     */       {
/* 1241:1472 */         val = str;
/* 1242:     */       }
/* 1243:1473 */       else if (type.equalsIgnoreCase("int"))
/* 1244:     */       {
/* 1245:1474 */         val = Integer.valueOf(Integer.parseInt(str));
/* 1246:     */       }
/* 1247:1475 */       else if (type.equalsIgnoreCase("float"))
/* 1248:     */       {
/* 1249:1476 */         val = Float.valueOf(Float.parseFloat(str));
/* 1250:     */       }
/* 1251:1477 */       else if (type.equalsIgnoreCase("double"))
/* 1252:     */       {
/* 1253:1478 */         val = Double.valueOf(Double.parseDouble(str));
/* 1254:     */       }
/* 1255:1479 */       else if (type.equalsIgnoreCase("byte"))
/* 1256:     */       {
/* 1257:1480 */         val = Byte.valueOf(Byte.parseByte(str));
/* 1258:     */       }
/* 1259:1481 */       else if (type.equalsIgnoreCase("short"))
/* 1260:     */       {
/* 1261:1482 */         val = Short.valueOf(Short.parseShort(str));
/* 1262:     */       }
/* 1263:1483 */       else if (type.equalsIgnoreCase("long"))
/* 1264:     */       {
/* 1265:1484 */         val = Long.valueOf(Long.parseLong(str));
/* 1266:     */       }
/* 1267:1485 */       else if (type.equalsIgnoreCase("boolean"))
/* 1268:     */       {
/* 1269:1486 */         if (StringUtils.isNumeric(str)) {
/* 1270:1487 */           val = Boolean.valueOf(Integer.parseInt(str) == 1);
/* 1271:     */         }
/* 1272:1488 */         val = Boolean.valueOf(Boolean.parseBoolean(str));
/* 1273:     */       }
/* 1274:1489 */       else if (type.equalsIgnoreCase("date"))
/* 1275:     */       {
/* 1276:1490 */         val = DateFormatUtil.parse(str);
/* 1277:     */       }
/* 1278:     */       else
/* 1279:     */       {
/* 1280:1492 */         val = str;
/* 1281:     */       }
/* 1282:     */     }
/* 1283:     */     catch (Exception e) {}
/* 1284:1497 */     return val;
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public static String join(String[] aryStr, String separator)
/* 1288:     */   {
/* 1289:1507 */     if (aryStr == null) {
/* 1290:1507 */       return null;
/* 1291:     */     }
/* 1292:1508 */     if (aryStr.length == 1) {
/* 1293:1509 */       return aryStr[0];
/* 1294:     */     }
/* 1295:1511 */     String str = "";
/* 1296:1512 */     for (int i = 0; i < aryStr.length; i++) {
/* 1297:1513 */       if (i == 0) {
/* 1298:1514 */         str = str + aryStr[i];
/* 1299:     */       } else {
/* 1300:1517 */         str = str + separator + aryStr[i];
/* 1301:     */       }
/* 1302:     */     }
/* 1303:1520 */     return str;
/* 1304:     */   }
/* 1305:     */   
/* 1306:     */   public static String toString(Object obj)
/* 1307:     */   {
/* 1308:1532 */     if (obj == null) {
/* 1309:1533 */       return "";
/* 1310:     */     }
/* 1311:1535 */     return obj.toString();
/* 1312:     */   }
/* 1313:     */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.util.StringUtil
 * JD-Core Version:    0.7.0.1
 */