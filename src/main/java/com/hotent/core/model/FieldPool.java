package com.hotent.core.model;

public abstract interface FieldPool
{
  public static final short TEXT_INPUT = 1;
  public static final short TEXTAREA = 2;
  public static final short DICTIONARY = 3;
  public static final short SELECTOR_USER_SINGLE = 4;
  public static final short SELECTOR_USER_MULTI = 8;
  public static final short SELECTOR_ROLE_SINGLE = 17;
  public static final short SELECTOR_ROLE_MULTI = 5;
  public static final short SELECTOR_ORG_SINGLE = 18;
  public static final short SELECTOR_ORG_MULTI = 6;
  public static final short SELECTOR_POSITION_SINGLE = 19;
  public static final short SELECTOR_POSITION_MULTI = 7;
  public static final short ATTACHEMENT = 9;
  public static final short CKEDITOR = 10;
  public static final short SELECT_INPUT = 11;
  public static final short OFFICE_CONTROL = 12;
  public static final short CHECKBOX = 13;
  public static final short RADIO_INPUT = 14;
  public static final short DATEPICKER = 15;
  public static final short HIDEDOMAIN = 16;
  public static final short SELECTOR_PROCESS_INSTANCE = 20;
  public static final short WEBSIGN_CONTROL = 21;
  public static final short PICTURE_SHOW_CONTROL = 22;
  public static final String DATATYPE_VARCHAR = "varchar";
  public static final String DATATYPE_CLOB = "clob";
  public static final String DATATYPE_DATE = "date";
  public static final String DATATYPE_NUMBER = "number";
}


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.FieldPool
 * JD-Core Version:    0.7.0.1
 */