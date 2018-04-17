package com.hotent.core.model;

public interface FieldPool {
	short TEXT_INPUT = 1;
	short TEXTAREA = 2;
	short DICTIONARY = 3;
	short SELECTOR_USER_SINGLE = 4;
	short SELECTOR_USER_MULTI = 8;
	short SELECTOR_ROLE_SINGLE = 17;
	short SELECTOR_ROLE_MULTI = 5;
	short SELECTOR_ORG_SINGLE = 18;
	short SELECTOR_ORG_MULTI = 6;
	short SELECTOR_POSITION_SINGLE = 19;
	short SELECTOR_POSITION_MULTI = 7;
	short ATTACHEMENT = 9;
	short CKEDITOR = 10;
	short SELECT_INPUT = 11;
	short OFFICE_CONTROL = 12;
	short CHECKBOX = 13;
	short RADIO_INPUT = 14;
	short DATEPICKER = 15;
	short HIDEDOMAIN = 16;
	short SELECTOR_PROCESS_INSTANCE = 20;
	short WEBSIGN_CONTROL = 21;
	short PICTURE_SHOW_CONTROL = 22;
	String DATATYPE_VARCHAR = "varchar";
	String DATATYPE_CLOB = "clob";
	String DATATYPE_DATE = "date";
	String DATATYPE_NUMBER = "number";
}