package com.hotent.core.excel.editor;

import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.editor.AbstractRegionEditor;
import com.hotent.core.excel.editor.CellEditor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.ss.util.CellRangeAddress;

public class RegionEditor extends AbstractRegionEditor<RegionEditor> {
	private CellRangeAddress cellRange;

	public RegionEditor(int beginRow, int beginCol, int endRow, int endCol, ExcelContext context) {
		super(context);
		this.cellRange = new CellRangeAddress(beginRow, endRow, beginCol, endCol);
	}

	public RegionEditor(CellRangeAddress cellRange, ExcelContext context) {
		super(context);
		this.cellRange = cellRange;
	}

	public RegionEditor image(String imgPath) {
		ByteArrayOutputStream byteArrayOut = null;
		BufferedImage bufferImg = null;

		try {
			if (imgPath.startsWith("http")) {
				URL e = new URL(imgPath);
				URLConnection patr = e.openConnection();
				bufferImg = ImageIO.read(patr.getInputStream());
			} else {
				bufferImg = ImageIO.read(new File(imgPath));
			}

			HSSFClientAnchor e1 = new HSSFClientAnchor(10, 10, 0, 0, (short) this.cellRange.getFirstColumn(),
					this.cellRange.getFirstRow(), (short) (this.cellRange.getLastColumn() + 1),
					this.cellRange.getLastRow() + 1);
			e1.setAnchorType(3);
			HSSFPatriarch patr1 = this.ctx.getHSSFPatriarch(this.workingSheet);
			byteArrayOut = new ByteArrayOutputStream();
			ImageIO.write(bufferImg, "JPEG", byteArrayOut);
			int imageIndex = this.workBook.addPicture(byteArrayOut.toByteArray(), 5);
			patr1.createPicture(e1, imageIndex);
		} catch (IOException arg16) {
			arg16.printStackTrace();
		} catch (Exception arg17) {
			arg17.printStackTrace();
		} finally {
			try {
				byteArrayOut.close();
			} catch (Exception arg15) {
				;
			}

		}

		return this;
	}

	protected CellEditor newCellEditor() {
		CellEditor cellEditor = new CellEditor(this.ctx);

		for (int i = this.cellRange.getFirstRow(); i <= this.cellRange.getLastRow(); ++i) {
			for (int j = this.cellRange.getFirstColumn(); j <= this.cellRange.getLastColumn(); ++j) {
				cellEditor.add(i, j);
			}
		}

		return cellEditor;
	}

	protected CellEditor newBottomCellEditor() {
		CellEditor cellEditorBottom = new CellEditor(this.ctx);

		for (int i = this.cellRange.getFirstColumn(); i <= this.cellRange.getLastColumn(); ++i) {
			cellEditorBottom.add(this.cellRange.getLastRow(), i);
		}

		return cellEditorBottom;
	}

	protected CellEditor newLeftCellEditor() {
		CellEditor cellEditorLeft = new CellEditor(this.ctx);

		for (int i = this.cellRange.getFirstRow(); i <= this.cellRange.getLastRow(); ++i) {
			cellEditorLeft.add(i, this.cellRange.getFirstColumn());
		}

		return cellEditorLeft;
	}

	protected CellEditor newRightCellEditor() {
		CellEditor cellEditorRight = new CellEditor(this.ctx);

		for (int i = this.cellRange.getFirstRow(); i <= this.cellRange.getLastRow(); ++i) {
			cellEditorRight.add(i, this.cellRange.getLastColumn());
		}

		return cellEditorRight;
	}

	protected CellEditor newTopCellEditor() {
		CellEditor cellEditorTop = new CellEditor(this.ctx);

		for (int i = this.cellRange.getFirstColumn(); i <= this.cellRange.getLastColumn(); ++i) {
			cellEditorTop.add(this.cellRange.getFirstRow(), i);
		}

		return cellEditorTop;
	}

	protected CellRangeAddress getCellRange() {
		return this.cellRange;
	}
}