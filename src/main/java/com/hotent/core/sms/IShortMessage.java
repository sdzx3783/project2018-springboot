package com.hotent.core.sms;

import java.util.List;

public interface IShortMessage {
	boolean sendSms(List<String> arg0, String arg1);
}