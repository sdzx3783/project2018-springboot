package com.hotent.core.api.bpm.model;

public class ITaskOpinion {
	public static final Short STATUS_INIT = Short.valueOf((short) -2);
	public static final Short STATUS_CHECKING = Short.valueOf((short) -1);
	public static final Short STATUS_ABANDON = Short.valueOf((short) 0);
	public static final Short STATUS_AGREE = Short.valueOf((short) 1);
	public static final Short STATUS_REFUSE = Short.valueOf((short) 2);
	public static final Short STATUS_REJECT = Short.valueOf((short) 3);
	public static final Short STATUS_RECOVER = Short.valueOf((short) 4);
	public static final Short STATUS_PASSED = Short.valueOf((short) 5);
	public static final Short STATUS_NOT_PASSED = Short.valueOf((short) 6);
	public static final Short STATUS_NOTIFY = Short.valueOf((short) 7);
	public static final Short STATUS_CHANGEPATH = Short.valueOf((short) 8);
	public static final Short STATUS_ENDPROCESS = Short.valueOf((short) 14);
	public static final Short STATUS_COMMUNICATION = Short.valueOf((short) 15);
	public static final Short STATUS_COMMUN_FEEDBACK = Short.valueOf((short) 20);
	public static final Short STATUS_FINISHDIVERT = Short.valueOf((short) 16);
	public static final Short STATUS_DELEGATE = Short.valueOf((short) 21);
	public static final Short STATUS_DELEGATE_CANCEL = Short.valueOf((short) 22);
	public static final Short STATUS_CHANGE_ASIGNEE = Short.valueOf((short) 23);
	public static final Short STATUS_REJECT_TOSTART = Short.valueOf((short) 24);
	public static final Short STATUS_RECOVER_TOSTART = Short.valueOf((short) 25);
	public static final Short STATUS_REVOKED = Short.valueOf((short) 17);
	public static final Short STATUS_DELETE = Short.valueOf((short) 18);
	public static final Short STATUS_NOTIFY_COPY = Short.valueOf((short) 19);
	public static final Short STATUS_AGENT = Short.valueOf((short) 26);
	public static final Short STATUS_AGENT_CANCEL = Short.valueOf((short) 27);
	public static final Short STATUS_OPINION = Short.valueOf((short) 28);
	public static final Short STATUS_BACK_CANCEL = Short.valueOf((short) 29);
	public static final Short STATUS_REVOKED_CANCEL = Short.valueOf((short) 30);
	public static final Short STATUS_PASS_CANCEL = Short.valueOf((short) 31);
	public static final Short STATUS_REFUSE_CANCEL = Short.valueOf((short) 32);
	public static final Short STATUS_SUBMIT = Short.valueOf((short) 33);
	public static final Short STATUS_RESUBMIT = Short.valueOf((short) 34);
	public static final Short STATUS_INTERVENE = Short.valueOf((short) 35);
	public static final Short STATUS_RESTART_TASK = Short.valueOf((short) 36);
	public static final Short STATUS_EXECUTED = Short.valueOf((short) 37);
}