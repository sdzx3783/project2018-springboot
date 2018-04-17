package com.hotent.core.bpm.graph.activiti;

import com.hotent.core.api.bpm.model.ITaskOpinion;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.bpm.graph.activiti.BPMNEdge;
import com.hotent.core.bpm.graph.activiti.DirectionType;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D.Double;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.impl.util.ReflectUtil;

public class ProcessDiagramCanvas {
	protected static final Logger LOGGER = Logger.getLogger(ProcessDiagramCanvas.class.getName());
	protected static final int ARROW_WIDTH = 5;
	protected static final int CONDITIONAL_INDICATOR_WIDTH = 16;
	protected static final int MARKER_WIDTH = 12;
	protected static Color DEFAULT_COLOR = new Color(0, 0, 0);
	protected static Color START_COLOR = new Color(113, 146, 75);
	protected static Color END_COLOR = new Color(210, 112, 0);
	protected static Color POOL_BOUNDARY_COLOR;
	protected static Color LANE_BOUNDARY_COLOR;
	protected static Color POOL_BACKGROUP_COLOR;
	protected static Color LANE_BACKGROUP_COLOR;
	protected static Color TASK_COLOR = new Color(255, 255, 204);
	protected static Color EVENT_BOUNDARY_COLOR = new Color(255, 255, 255);
	protected static Color CONDITIONAL_INDICATOR_COLOR = new Color(255, 255, 255);
	protected static Color HIGHLIGHT_COLOR;
	protected static Stroke THIN_TASK_BORDER_STROKE;
	protected static Stroke THICK_TASK_BORDER_STROKE;
	protected static Stroke THICK2_TASK_BORDER_STROKE;
	protected static Stroke GATEWAY_TYPE_STROKE;
	protected static Stroke END_EVENT_STROKE;
	protected static Stroke START_EVENT_STROKE;
	protected static Stroke MULTI_INSTANCE_STROKE;
	protected static Stroke LABEL_STROKE;
	protected static int ICON_SIZE;
	protected static Image USERTASK_IMAGE;
	protected static Image SCRIPTTASK_IMAGE;
	protected static Image SERVICETASK_IMAGE;
	protected static Image RECEIVETASK_IMAGE;
	protected static Image SENDTASK_IMAGE;
	protected static Image MANUALTASK_IMAGE;
	protected static Image TIMER_IMAGE;
	protected static Image ERROR_THROW_IMAGE;
	protected static Image ERROR_CATCH_IMAGE;
	protected static Image CALLACTIVITY_IMAGE;
	protected int canvasWidth;
	protected int canvasHeight;
	protected int minX;
	protected int minY;
	protected BufferedImage processDiagram;
	protected Graphics2D g;
	protected FontMetrics fontMetrics;
	protected boolean closed;
	protected static Map<Short, Color> colorsMap;

	public ProcessDiagramCanvas(int width, int height) {
		this.canvasWidth = -1;
		this.canvasHeight = -1;
		this.minX = -1;
		this.minY = -1;
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.processDiagram = new BufferedImage(width, height, 2);
		this.g = this.processDiagram.createGraphics();
		this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.g.setPaint(Color.black);
		Font font = new Font("宋体", 1, 12);
		this.g.setFont(font);
		this.fontMetrics = this.g.getFontMetrics();
	}

	public ProcessDiagramCanvas(int width, int height, int minX, int minY) {
		this(width, height);
		this.minX = minX;
		this.minY = minY;
	}

	public InputStream generateImage(String imageType) {
		if (this.closed) {
			throw new ActivitiException("ProcessDiagramGenerator already closed");
		} else {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			try {
				this.minX = this.minX <= 5 ? 5 : this.minX;
				this.minY = this.minY <= 5 ? 5 : this.minY;
				BufferedImage e = this.processDiagram;
				if (this.minX >= 0 && this.minY >= 0) {
					e = this.processDiagram.getSubimage(this.minX - 5, this.minY - 5, this.canvasWidth - this.minX + 5,
							this.canvasHeight - this.minY + 5);
				}

				ImageIO.write(e, imageType, out);
			} catch (IOException arg6) {
				throw new ActivitiException("Error while generating process image", arg6);
			} finally {
				IoUtil.closeSilently(out);
			}

			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public void close() {
		this.g.dispose();
		this.closed = true;
	}

	public void drawNoneStartEvent(String name, int x, int y, int width, int height) {
		this.drawStartEvent(name, x, y, width, height, (Image) null);
	}

	public void drawTimerStartEvent(String name, int x, int y, int width, int height) {
		this.drawStartEvent(name, x, y, width, height, TIMER_IMAGE);
	}

	public void drawStartEvent(String name, int x, int y, int width, int height, Image image) {
		Paint originalPaint = this.g.getPaint();
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(START_COLOR);
		this.g.setStroke(START_EVENT_STROKE);
		this.g.draw(new Double((double) x, (double) y, (double) width, (double) height));
		if (image != null) {
			this.g.drawImage(image, x, y, width, height, (ImageObserver) null);
		}

		this.g.setStroke(originalStroke);
		this.g.setPaint(originalPaint);
	}

	public void drawNoneEndEvent(String name, int x, int y, int width, int height) {
		Paint originalPaint = this.g.getPaint();
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(END_COLOR);
		this.g.setStroke(END_EVENT_STROKE);
		this.g.draw(new Double((double) x, (double) y, (double) width, (double) height));
		this.g.setStroke(originalStroke);
		this.g.setPaint(originalPaint);
	}

	public void drawErrorEndEvent(String name, int x, int y, int width, int height) {
		this.drawNoneEndEvent(name, x, y, width, height);
		this.g.drawImage(ERROR_THROW_IMAGE, x + 3, y + 3, width - 6, height - 6, (ImageObserver) null);
	}

	public void drawCatchingEvent(int x, int y, int width, int height, Image image) {
		Double outerCircle = new Double((double) x, (double) y, (double) width, (double) height);
		int innerCircleX = x + 3;
		int innerCircleY = y + 3;
		int innerCircleWidth = width - 6;
		int innerCircleHeight = height - 6;
		Double innerCircle = new Double((double) innerCircleX, (double) innerCircleY, (double) innerCircleWidth,
				(double) innerCircleHeight);
		Paint originalPaint = this.g.getPaint();
		this.g.setPaint(EVENT_BOUNDARY_COLOR);
		this.g.fill(outerCircle);
		this.g.setPaint(originalPaint);
		this.g.draw(outerCircle);
		this.g.draw(innerCircle);
		this.g.drawImage(image, innerCircleX, innerCircleY, innerCircleWidth, innerCircleHeight, (ImageObserver) null);
	}

	public void drawCatchingTimerEvent(int x, int y, int width, int height) {
		this.drawCatchingEvent(x, y, width, height, TIMER_IMAGE);
	}

	public void drawCatchingErroEvent(int x, int y, int width, int height) {
		this.drawCatchingEvent(x, y, width, height, ERROR_CATCH_IMAGE);
	}

	public void drawSequenceflow(int srcX, int srcY, int targetX, int targetY, boolean conditional) {
		java.awt.geom.Line2D.Double line = new java.awt.geom.Line2D.Double((double) srcX, (double) srcY,
				(double) targetX, (double) targetY);
		this.g.draw(line);
		this.drawArrowHead(line);
		if (conditional) {
			this.drawConditionalSequenceFlowIndicator(line);
		}

	}

	public void drawSequenceflowWithoutArrow(int srcX, int srcY, int targetX, int targetY, boolean conditional) {
		java.awt.geom.Line2D.Double line = new java.awt.geom.Line2D.Double((double) srcX, (double) srcY,
				(double) targetX, (double) targetY);
		this.g.draw(line);
		if (conditional) {
			this.drawConditionalSequenceFlowIndicator(line);
		}

	}

	public void drawSequenceflow(List<java.awt.geom.Point2D.Double> points) {
		for (int i = 0; i < points.size() - 1; ++i) {
			java.awt.geom.Line2D.Double line = new java.awt.geom.Line2D.Double(
					((java.awt.geom.Point2D.Double) points.get(i)).getX(),
					((java.awt.geom.Point2D.Double) points.get(i)).getY(),
					((java.awt.geom.Point2D.Double) points.get(i + 1)).getX(),
					((java.awt.geom.Point2D.Double) points.get(i + 1)).getY());
			this.g.draw(line);
			if (i == points.size() - 2) {
				this.drawArrowHead(line);
			}
		}

	}

	public void drawSequenceflowWidthLabel(BPMNEdge bpmnEdge) {
		this.drawSequenceflow(bpmnEdge.getPoints());
		byte flag = 0;
		DirectionType directionType = bpmnEdge.getDirection();
		if (directionType == DirectionType.UpToDown) {
			flag = 1;
		} else if (directionType == DirectionType.DownToUp) {
			flag = 2;
		} else if (directionType == DirectionType.LeftToRight) {
			flag = 3;
		} else if (directionType == DirectionType.RightToLef) {
			flag = 4;
		}

		this.drawSequenceflowLabel(bpmnEdge.getName(), (int) bpmnEdge.getMidpoint().getX(),
				(int) bpmnEdge.getMidpoint().getY(), flag);
	}

	public void drawSequenceflowLabel(String name, int x, int y, int flag) {
		if (name != null) {
			int drawX = x;
			int drawY = y;
			switch (flag) {
				case 1 :
					drawX = x + this.g.getFontMetrics().getHeight() / 2;
					drawY = y;
					break;
				case 2 :
					drawX = x - this.g.getFontMetrics().stringWidth(name) - this.g.getFontMetrics().getHeight() / 2;
					drawY = y + this.g.getFontMetrics().getHeight();
					break;
				case 3 :
					drawX = x - this.g.getFontMetrics().stringWidth(name) / 2;
					drawY = y - this.g.getFontMetrics().getHeight() / 2;
					break;
				case 4 :
					drawX = x - this.g.getFontMetrics().stringWidth(name) / 2;
					drawY = y + this.g.getFontMetrics().getHeight();
			}

			Paint originalPaint = this.g.getPaint();
			Stroke originalStroke = this.g.getStroke();
			this.g.setPaint(new Color(80, 160, 240));
			this.g.setStroke(LABEL_STROKE);
			this.g.drawString(name, drawX, drawY);
			this.g.setStroke(originalStroke);
			this.g.setPaint(originalPaint);
		}
	}

	public void drawArrowHead(java.awt.geom.Line2D.Double line) {
		byte doubleArrowWidth = 10;
		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(0, 0);
		arrowHead.addPoint(-5, -doubleArrowWidth);
		arrowHead.addPoint(5, -doubleArrowWidth);
		AffineTransform transformation = new AffineTransform();
		transformation.setToIdentity();
		double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
		transformation.translate(line.x2, line.y2);
		transformation.rotate(angle - 1.570796326794897D);
		AffineTransform originalTransformation = this.g.getTransform();
		this.g.setTransform(transformation);
		this.g.fill(arrowHead);
		this.g.setTransform(originalTransformation);
	}

	public void drawConditionalSequenceFlowIndicator(java.awt.geom.Line2D.Double line) {
		byte horizontal = 11;
		int halfOfHorizontal = horizontal / 2;
		byte halfOfVertical = 8;
		Polygon conditionalIndicator = new Polygon();
		conditionalIndicator.addPoint(0, 0);
		conditionalIndicator.addPoint(-halfOfHorizontal, halfOfVertical);
		conditionalIndicator.addPoint(0, 16);
		conditionalIndicator.addPoint(halfOfHorizontal, halfOfVertical);
		AffineTransform transformation = new AffineTransform();
		transformation.setToIdentity();
		double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
		transformation.translate(line.x1, line.y1);
		transformation.rotate(angle - 1.570796326794897D);
		AffineTransform originalTransformation = this.g.getTransform();
		this.g.setTransform(transformation);
		this.g.draw(conditionalIndicator);
		Paint originalPaint = this.g.getPaint();
		this.g.setPaint(CONDITIONAL_INDICATOR_COLOR);
		this.g.fill(conditionalIndicator);
		this.g.setPaint(originalPaint);
		this.g.setTransform(originalTransformation);
	}

	public void drawTask(String name, int x, int y, int width, int height) {
		this.drawTask(name, x, y, width, height, false);
	}

	protected void drawTask(String name, int x, int y, int width, int height, boolean thickBorder) {
		Paint originalPaint = this.g.getPaint();
		this.g.setPaint(TASK_COLOR);
		java.awt.geom.RoundRectangle2D.Double rect = new java.awt.geom.RoundRectangle2D.Double((double) x, (double) y,
				(double) width, (double) height, 20.0D, 20.0D);
		this.g.fill(rect);
		this.g.setPaint(originalPaint);
		if (thickBorder) {
			Stroke nameAry = this.g.getStroke();
			this.g.setStroke(THICK_TASK_BORDER_STROKE);
			this.g.draw(rect);
			this.g.setStroke(nameAry);
		} else {
			this.g.draw(rect);
		}

		if (name != null) {
			String[] arg14 = this.fitTextToWidthAndBreak(name, width);
			int offsetY = this.fontMetrics.getHeight();
			int textY = y + (height - this.fontMetrics.getHeight()) / 2;

			for (int i = 0; i < arg14.length; ++i) {
				String l = arg14[i];
				int textX = x + (width - this.fontMetrics.stringWidth(l)) / 2;
				this.g.drawString(l, textX, textY + i * offsetY);
			}
		}

	}

	private String[] fitTextToWidthAndBreak(String original, int width) {
		String line1 = "";
		String tmp1 = "";
		String line2 = "";
		int maxWidth = width - 10;
		if (this.fontMetrics.stringWidth(original) <= maxWidth) {
			return new String[]{original};
		} else {
			while (this.fontMetrics.stringWidth(tmp1) <= maxWidth) {
				line1 = tmp1;
				tmp1 = original.substring(0, tmp1.length() + 1);
			}

			line2 = original.substring(line1.length(), original.length());
			if (this.fontMetrics.stringWidth(line2) <= maxWidth) {
				return new String[]{line1, line2};
			} else {
				while (this.fontMetrics.stringWidth(line2 + "...") > maxWidth && line2.length() > 0) {
					line2 = line2.substring(0, line2.length() - 1);
				}

				if (!line2.equals(original)) {
					line2 = line2 + "...";
				}

				return new String[]{line1, line2};
			}
		}
	}

	protected String fitTextToWidth(String original, int width) {
		String line1 = "";
		String tmp1 = "";
		String line2 = "";
		int maxWidth = width - 10;
		if (this.fontMetrics.stringWidth(original) <= maxWidth) {
			return original;
		} else {
			while (this.fontMetrics.stringWidth(tmp1) <= maxWidth) {
				line1 = tmp1;
				tmp1 = original.substring(0, tmp1.length() + 1);
			}

			for (line2 = original.substring(line1.length(),
					original.length()); this.fontMetrics.stringWidth(line2 + "...") > maxWidth
							&& line2.length() > 0; line2 = line2.substring(0, line2.length() - 1)) {
				;
			}

			if (!line2.equals(original)) {
				line2 = line2 + "...";
			}

			return line1 + "\r\n" + line2;
		}
	}

	public void drawUserTask(String name, int x, int y, int width, int height) {
		this.drawTask(name, x, y, width, height);
		int ix = x + (width - ICON_SIZE) / 2;
		int iy = y + height - ICON_SIZE;
		this.g.drawImage(USERTASK_IMAGE, ix, iy, ICON_SIZE, ICON_SIZE, (ImageObserver) null);
	}

	public void drawScriptTask(String name, int x, int y, int width, int height) {
		this.drawTask(name, x, y, width, height);
		this.g.drawImage(SCRIPTTASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, (ImageObserver) null);
	}

	public void drawServiceTask(String name, int x, int y, int width, int height) {
		this.drawTask(name, x, y, width, height);
		this.g.drawImage(SERVICETASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, (ImageObserver) null);
	}

	public void drawReceiveTask(String name, int x, int y, int width, int height) {
		this.drawTask(name, x, y, width, height);
		this.g.drawImage(RECEIVETASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, (ImageObserver) null);
	}

	public void drawSendTask(String name, int x, int y, int width, int height) {
		this.drawTask(name, x, y, width, height);
		this.g.drawImage(SENDTASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, (ImageObserver) null);
	}

	public void drawManualTask(String name, int x, int y, int width, int height) {
		this.drawTask(name, x, y, width, height);
		this.g.drawImage(MANUALTASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, (ImageObserver) null);
	}

	public void drawExpandedSubProcess(String name, int x, int y, int width, int height) {
		java.awt.geom.RoundRectangle2D.Double rect = new java.awt.geom.RoundRectangle2D.Double((double) x, (double) y,
				(double) width, (double) height, 20.0D, 20.0D);
		this.g.draw(rect);
		String text = this.fitTextToWidth(name, width);
		this.g.drawString(text, x + 10, y + 15);
	}

	public void drawCollapsedSubProcess(String name, int x, int y, int width, int height) {
		this.drawCollapsedTask(name, x, y, width, height, false);
	}

	public void drawCollapsedCallActivity(String name, int x, int y, int width, int height) {
		this.drawCollapsedTask(name, x, y, width, height, false);
		this.g.drawImage(CALLACTIVITY_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, (ImageObserver) null);
	}

	protected void drawCollapsedTask(String name, int x, int y, int width, int height, boolean thickBorder) {
		this.drawTask(name, x, y, width, height, thickBorder);
	}

	public void drawCollapsedMarker(int x, int y, int width, int height) {
		byte rectangleWidth = 12;
		byte rectangleHeight = 12;
		Rectangle rect = new Rectangle(x + (width - rectangleWidth) / 2, y + height - rectangleHeight - 3,
				rectangleWidth, rectangleHeight);
		this.g.draw(rect);
		java.awt.geom.Line2D.Double line = new java.awt.geom.Line2D.Double(rect.getCenterX(), rect.getY() + 2.0D,
				rect.getCenterX(), rect.getMaxY() - 2.0D);
		this.g.draw(line);
		line = new java.awt.geom.Line2D.Double(rect.getMinX() + 2.0D, rect.getCenterY(), rect.getMaxX() - 2.0D,
				rect.getCenterY());
		this.g.draw(line);
	}

	public void drawActivityMarkers(int x, int y, int width, int height, boolean multiInstanceSequential,
			boolean multiInstanceParallel, boolean collapsed) {
		if (collapsed) {
			if (!multiInstanceSequential && !multiInstanceParallel) {
				this.drawCollapsedMarker(x, y, width, height);
			} else {
				this.drawCollapsedMarker(x - 6 - 2, y, width, height);
				if (multiInstanceSequential) {
					this.drawMultiInstanceMarker(true, x + 6 + 2, y, width, height);
				} else if (multiInstanceParallel) {
					this.drawMultiInstanceMarker(false, x + 6 + 2, y, width, height);
				}
			}
		} else if (multiInstanceSequential) {
			this.drawMultiInstanceMarker(false, x, y, width, height);
		} else if (multiInstanceParallel) {
			this.drawMultiInstanceMarker(true, x, y, width, height);
		}

	}

	public void drawGateway(String name, int x, int y, int width, int height) {
		Polygon rhombus = new Polygon();
		rhombus.addPoint(x, y + height / 2);
		rhombus.addPoint(x + width / 2, y + height);
		rhombus.addPoint(x + width, y + height / 2);
		rhombus.addPoint(x + width / 2, y);
		this.g.draw(rhombus);
		if (name != null) {
			int textX = x + width / 4 - this.fontMetrics.stringWidth(name) - this.fontMetrics.getHeight() / 4;
			int textY = y + height / 4 - this.fontMetrics.getHeight() / 4;
			this.g.drawString(name, textX, textY);
		}

	}

	public void drawParallelGateway(String name, int x, int y, int width, int height) {
		this.drawGateway(name, x, y, width, height);
		Stroke orginalStroke = this.g.getStroke();
		this.g.setStroke(GATEWAY_TYPE_STROKE);
		java.awt.geom.Line2D.Double line = new java.awt.geom.Line2D.Double((double) (x + 10), (double) (y + height / 2),
				(double) (x + width - 10), (double) (y + height / 2));
		this.g.draw(line);
		line = new java.awt.geom.Line2D.Double((double) (x + width / 2), (double) (y + height - 10),
				(double) (x + width / 2), (double) (y + 10));
		this.g.draw(line);
		this.g.setStroke(orginalStroke);
	}

	public void drawExclusiveGateway(String name, int x, int y, int width, int height) {
		this.drawGateway(name, x, y, width, height);
		int quarterWidth = width / 4;
		int quarterHeight = height / 4;
		Stroke orginalStroke = this.g.getStroke();
		this.g.setStroke(GATEWAY_TYPE_STROKE);
		java.awt.geom.Line2D.Double line = new java.awt.geom.Line2D.Double((double) (x + quarterWidth + 3),
				(double) (y + quarterHeight + 3), (double) (x + 3 * quarterWidth - 3),
				(double) (y + 3 * quarterHeight - 3));
		this.g.draw(line);
		line = new java.awt.geom.Line2D.Double((double) (x + quarterWidth + 3), (double) (y + 3 * quarterHeight - 3),
				(double) (x + 3 * quarterWidth - 3), (double) (y + quarterHeight + 3));
		this.g.draw(line);
		this.g.setStroke(orginalStroke);
	}

	public void drawInclusiveGateway(String name, int x, int y, int width, int height) {
		this.drawGateway(name, x, y, width, height);
		int diameter = width / 2;
		Stroke orginalStroke = this.g.getStroke();
		this.g.setStroke(GATEWAY_TYPE_STROKE);
		Double circle = new Double((double) ((width - diameter) / 2 + x), (double) ((height - diameter) / 2 + y),
				(double) diameter, (double) diameter);
		this.g.draw(circle);
		this.g.setStroke(orginalStroke);
	}

	public void drawMultiInstanceMarker(boolean sequential, int x, int y, int width, int height) {
		byte rectangleWidth = 12;
		byte rectangleHeight = 12;
		int lineX = x + (width - rectangleWidth) / 2 + ICON_SIZE;
		int lineY = y + height - rectangleHeight - 3;
		Stroke orginalStroke = this.g.getStroke();
		this.g.setStroke(MULTI_INSTANCE_STROKE);
		if (sequential) {
			this.g.draw(new java.awt.geom.Line2D.Double((double) lineX, (double) lineY, (double) lineX,
					(double) (lineY + rectangleHeight)));
			this.g.draw(new java.awt.geom.Line2D.Double((double) (lineX + rectangleWidth / 2), (double) lineY,
					(double) (lineX + rectangleWidth / 2), (double) (lineY + rectangleHeight)));
			this.g.draw(new java.awt.geom.Line2D.Double((double) (lineX + rectangleWidth), (double) lineY,
					(double) (lineX + rectangleWidth), (double) (lineY + rectangleHeight)));
		} else {
			this.g.draw(new java.awt.geom.Line2D.Double((double) lineX, (double) lineY,
					(double) (lineX + rectangleWidth), (double) lineY));
			this.g.draw(new java.awt.geom.Line2D.Double((double) lineX, (double) (lineY + rectangleHeight / 2),
					(double) (lineX + rectangleWidth), (double) (lineY + rectangleHeight / 2)));
			this.g.draw(new java.awt.geom.Line2D.Double((double) lineX, (double) (lineY + rectangleHeight),
					(double) (lineX + rectangleWidth), (double) (lineY + rectangleHeight)));
		}

		this.g.setStroke(orginalStroke);
	}

	public void drawHighLight(int x, int y, int width, int height) {
		Paint originalPaint = this.g.getPaint();
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(HIGHLIGHT_COLOR);
		this.g.setStroke(THICK_TASK_BORDER_STROKE);
		java.awt.geom.RoundRectangle2D.Double rect = new java.awt.geom.RoundRectangle2D.Double((double) x, (double) y,
				(double) width, (double) height, 20.0D, 20.0D);
		this.g.draw(rect);
		this.g.setPaint(originalPaint);
		this.g.setStroke(originalStroke);
	}

	public void drawHighLight(int x, int y, int width, int height, Short status) {
		Paint originalPaint = this.g.getPaint();
		Stroke originalStroke = this.g.getStroke();
		Color color = (Color) colorsMap.get(status);
		this.g.setPaint(color);
		if (status != ITaskOpinion.STATUS_INIT) {
			this.g.setStroke(THICK_TASK_BORDER_STROKE);
		} else {
			this.g.setStroke(THIN_TASK_BORDER_STROKE);
		}

		java.awt.geom.RoundRectangle2D.Double rect = new java.awt.geom.RoundRectangle2D.Double((double) x, (double) y,
				(double) width, (double) height, 20.0D, 20.0D);
		this.g.draw(rect);
		this.g.setPaint(originalPaint);
		this.g.setStroke(originalStroke);
	}

	public void drawHighLight(int x, int y, int width, int height, String colorStr) {
		boolean isNormal = false;
		if (colorStr == null) {
			colorStr = "#000000";
			isNormal = true;
		}

		Color color = Color.decode(colorStr);
		Paint originalPaint = this.g.getPaint();
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(color);
		if (isNormal) {
			this.g.setStroke(THICK2_TASK_BORDER_STROKE);
		} else {
			this.g.setStroke(THICK_TASK_BORDER_STROKE);
		}

		java.awt.geom.RoundRectangle2D.Double rect = new java.awt.geom.RoundRectangle2D.Double((double) x, (double) y,
				(double) width, (double) height, 20.0D, 20.0D);
		this.g.draw(rect);
		this.g.setPaint(originalPaint);
		this.g.setStroke(originalStroke);
	}

	public void drawHPool(String name, int x, int y, int width, int height) {
		java.awt.geom.Rectangle2D.Double rect = new java.awt.geom.Rectangle2D.Double((double) x, (double) y,
				(double) width, (double) height);
		Paint originalPaint = this.g.getPaint();
		this.g.setPaint(POOL_BACKGROUP_COLOR);
		java.awt.geom.RoundRectangle2D.Double roundRectangle2D = new java.awt.geom.RoundRectangle2D.Double((double) x,
				(double) y, 20.0D, (double) height, 0.0D, 0.0D);
		this.g.fill(roundRectangle2D);
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(POOL_BOUNDARY_COLOR);
		this.g.draw(rect);
		int textLen = this.fontMetrics.stringWidth(name);
		int textX = x + this.fontMetrics.getHeight() / 2;
		int textY = y + height / 2 - textLen / 2;
		AffineTransform oldAffineTransform = this.g.getTransform();
		AffineTransform newAffineTransform = AffineTransform.getRotateInstance(-1.5707963267948966D, (double) textX,
				(double) textY);
		this.g.setTransform(newAffineTransform);
		this.g.setPaint(DEFAULT_COLOR);
		this.g.drawString(name, textX - textLen, textY + this.fontMetrics.getHeight() / 2);
		this.g.setTransform(oldAffineTransform);
		this.g.setStroke(originalStroke);
		this.g.setPaint(originalPaint);
	}

	public void drawHLane(String name, int x, int y, int width, int height) {
		java.awt.geom.Rectangle2D.Double rect = new java.awt.geom.Rectangle2D.Double((double) x, (double) y,
				(double) width, (double) height);
		Paint originalPaint = this.g.getPaint();
		this.g.setPaint(LANE_BACKGROUP_COLOR);
		java.awt.geom.RoundRectangle2D.Double roundRectangle2D = new java.awt.geom.RoundRectangle2D.Double((double) x,
				(double) y, 20.0D, (double) height, 0.0D, 0.0D);
		this.g.fill(roundRectangle2D);
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(LANE_BOUNDARY_COLOR);
		this.g.draw(rect);
		int textLen = this.fontMetrics.stringWidth(name);
		int textX = x + this.fontMetrics.getHeight() / 2;
		int textY = y + height / 2 - textLen / 2;
		AffineTransform oldAffineTransform = this.g.getTransform();
		AffineTransform newAffineTransform = AffineTransform.getRotateInstance(-1.5707963267948966D, (double) textX,
				(double) textY);
		this.g.setTransform(newAffineTransform);
		this.g.setPaint(DEFAULT_COLOR);
		this.g.drawString(name, textX - textLen, textY + this.fontMetrics.getHeight() / 2);
		this.g.setTransform(oldAffineTransform);
		this.g.setStroke(originalStroke);
		this.g.setPaint(originalPaint);
	}

	public void drawVPool(String name, int x, int y, int width, int height) {
		java.awt.geom.Rectangle2D.Double rect = new java.awt.geom.Rectangle2D.Double((double) x, (double) y,
				(double) width, (double) height);
		Paint originalPaint = this.g.getPaint();
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(POOL_BACKGROUP_COLOR);
		java.awt.geom.RoundRectangle2D.Double roundRectangle2D = new java.awt.geom.RoundRectangle2D.Double((double) x,
				(double) y, (double) width, 20.0D, 0.0D, 0.0D);
		this.g.fill(roundRectangle2D);
		this.g.setPaint(POOL_BOUNDARY_COLOR);
		this.g.draw(rect);
		name = this.fitTextToWidth(name, width);
		int textLen = this.fontMetrics.stringWidth(name);
		int textY = y + this.fontMetrics.getHeight();
		int textX = x + width / 2 - textLen / 2;
		this.g.setPaint(DEFAULT_COLOR);
		this.g.drawString(name, textX, textY);
		this.g.setStroke(originalStroke);
		this.g.setPaint(originalPaint);
	}

	public void drawVLane(String name, int x, int y, int width, int height) {
		java.awt.geom.Rectangle2D.Double rect = new java.awt.geom.Rectangle2D.Double((double) x, (double) y,
				(double) width, (double) height);
		Paint originalPaint = this.g.getPaint();
		Stroke originalStroke = this.g.getStroke();
		this.g.setPaint(LANE_BACKGROUP_COLOR);
		java.awt.geom.RoundRectangle2D.Double roundRectangle2D = new java.awt.geom.RoundRectangle2D.Double((double) x,
				(double) y, (double) width, 20.0D, 0.0D, 0.0D);
		this.g.fill(roundRectangle2D);
		this.g.setPaint(LANE_BOUNDARY_COLOR);
		this.g.draw(rect);
		name = this.fitTextToWidth(name, width);
		int textLen = this.fontMetrics.stringWidth(name);
		int textY = y + this.fontMetrics.getHeight();
		int textX = x + width / 2 - textLen / 2;
		this.g.setPaint(DEFAULT_COLOR);
		this.g.drawString(name, textX, textY);
		this.g.setStroke(originalStroke);
		this.g.setPaint(originalPaint);
	}

	public static Color getColor(String colorString) {
		String tempCOLOR = PropertyUtil.getByAlias(colorString);
		Color color = null;
		if (!"".equals(tempCOLOR) && tempCOLOR != null) {
			String[] COLOR_Strings = tempCOLOR.split(",");
			color = new Color(Integer.parseInt(COLOR_Strings[0]), Integer.parseInt(COLOR_Strings[1]),
					Integer.parseInt(COLOR_Strings[2]));
		}

		return color;
	}

	static {
		HIGHLIGHT_COLOR = Color.RED;
		THIN_TASK_BORDER_STROKE = new BasicStroke(1.0F);
		THICK_TASK_BORDER_STROKE = new BasicStroke(3.0F);
		THICK2_TASK_BORDER_STROKE = new BasicStroke(2.0F);
		GATEWAY_TYPE_STROKE = new BasicStroke(3.0F);
		END_EVENT_STROKE = new BasicStroke(3.0F);
		START_EVENT_STROKE = new BasicStroke(3.0F);
		MULTI_INSTANCE_STROKE = new BasicStroke(1.3F);
		LABEL_STROKE = new BasicStroke(1.0F);
		ICON_SIZE = 16;
		colorsMap = new HashMap();

		try {
			USERTASK_IMAGE = ImageIO.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/user.png"));
			SCRIPTTASK_IMAGE = ImageIO
					.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/script.png"));
			SERVICETASK_IMAGE = ImageIO
					.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/service.png"));
			RECEIVETASK_IMAGE = ImageIO
					.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/receive.png"));
			SENDTASK_IMAGE = ImageIO.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/send.png"));
			MANUALTASK_IMAGE = ImageIO
					.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/manual.png"));
			TIMER_IMAGE = ImageIO.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/timer.png"));
			ERROR_THROW_IMAGE = ImageIO
					.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/error_throw.png"));
			ERROR_CATCH_IMAGE = ImageIO
					.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/error_catch.png"));
			CALLACTIVITY_IMAGE = ImageIO
					.read(ReflectUtil.getResourceAsStream("com/hotent/core/bpm/graph/image/call_activity.png"));
			colorsMap.put(ITaskOpinion.STATUS_INIT, Color.darkGray);
			colorsMap.put(ITaskOpinion.STATUS_AGREE, Color.green);
			colorsMap.put(ITaskOpinion.STATUS_ABANDON, Color.orange);
			colorsMap.put(ITaskOpinion.STATUS_CHECKING, Color.red);
			colorsMap.put(ITaskOpinion.STATUS_REFUSE, Color.blue);
			colorsMap.put(ITaskOpinion.STATUS_REJECT, new Color(138, 9, 2));
			colorsMap.put(ITaskOpinion.STATUS_RECOVER, new Color(2, 59, 98));
			colorsMap.put(ITaskOpinion.STATUS_PASSED, new Color(51, 136, 72));
			colorsMap.put(ITaskOpinion.STATUS_NOT_PASSED, new Color(130, 183, 215));
			Color e = getColor("POOL_BACKGROUP_COLOR");
			Color pool_boundary_color_temp = getColor("LANE_BOUNDARY_COLOR");
			if (e != null) {
				POOL_BACKGROUP_COLOR = e;
				LANE_BACKGROUP_COLOR = e;
			} else {
				POOL_BACKGROUP_COLOR = new Color(211, 215, 212);
				LANE_BACKGROUP_COLOR = new Color(211, 215, 212);
			}

			if (pool_boundary_color_temp != null) {
				POOL_BOUNDARY_COLOR = pool_boundary_color_temp;
				LANE_BOUNDARY_COLOR = pool_boundary_color_temp;
			} else {
				POOL_BOUNDARY_COLOR = new Color(138, 140, 142);
				LANE_BOUNDARY_COLOR = new Color(138, 140, 142);
			}
		} catch (IOException arg1) {
			LOGGER.warning("Could not load image for process diagram creation: " + arg1.getMessage());
		}

	}
}