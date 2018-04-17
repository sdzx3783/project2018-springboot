package com.hotent.core.excel.editor.listener;

import com.hotent.core.excel.Excel;
import com.hotent.core.excel.editor.CellEditor;

public interface CellValueListener {
	void onValueChange(CellEditor arg0, Object arg1, int arg2, int arg3, Excel arg4);
}