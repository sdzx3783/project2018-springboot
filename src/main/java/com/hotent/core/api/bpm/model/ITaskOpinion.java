package com.hotent.core.api.bpm.model;

public class ITaskOpinion {
	public static final Short STATUS_INIT = Short.valueOf(-2);
	public static final Short STATUS_CHECKING = Short.valueOf(-1);
	public static final Short STATUS_ABANDON = Short.valueOf(0);
	public static final Short STATUS_AGREE = Short.valueOf(1);
	public static final Short STATUS_REFUSE = Short.valueOf(2);
	public static final Short STATUS_REJECT = Short.valueOf(3);
	public static final Short STATUS_RECOVER = Short.valueOf(4);
	public static final Short STATUS_PASSED = Short.valueOf(5);
	public static final Short STATUS_NOT_PASSED = Short.valueOf(6);
	public static final Short STATUS_NOTIFY = Short.valueOf(7);
	public static final Short STATUS_CHANGEPATH = Short.valueOf(8);
	public static final Short STATUS_ENDPROCESS = Short.valueOf(14);
	public static final Short STATUS_COMMUNICATION = Short.valueOf(15);
	public static final Short STATUS_COMMUN_FEEDBACK = Short.valueOf(20);
	public static final Short STATUS_FINISHDIVERT = Short.valueOf(16);
	public static final Short STATUS_DELEGATE = Short.valueOf(21);
	public static final Short STATUS_DELEGATE_CANCEL = Short.valueOf(22);
	public static final Short STATUS_CHANGE_ASIGNEE = Short.valueOf(23);
	public static final Short STATUS_REJECT_TOSTART = Short.valueOf(24);
	public static final Short STATUS_RECOVER_TOSTART = Short.valueOf(25);
	public static final Short STATUS_REVOKED = Short.valueOf(17);
	public static final Short STATUS_DELETE = Short.valueOf(18);
	public static final Short STATUS_NOTIFY_COPY = Short.valueOf(19);
	public static final Short STATUS_AGENT = Short.valueOf(26);
	public static final Short STATUS_AGENT_CANCEL = Short.valueOf(27);
	public static final Short STATUS_OPINION = Short.valueOf(28);
	public static final Short STATUS_BACK_CANCEL = Short.valueOf(29);
	public static final Short STATUS_REVOKED_CANCEL = Short.valueOf(30);
	public static final Short STATUS_PASS_CANCEL = Short.valueOf(31);
	public static final Short STATUS_REFUSE_CANCEL = Short.valueOf(32);
	public static final Short STATUS_SUBMIT = Short.valueOf(33);
	public static final Short STATUS_RESUBMIT = Short.valueOf(34);
	public static final Short STATUS_INTERVENE = Short.valueOf(35);
	public static final Short STATUS_RESTART_TASK = Short.valueOf(36);
	public static final Short STATUS_EXECUTED = Short.valueOf(37);
}