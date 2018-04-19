package com.hotent.core.bpm.graph;

import com.hotent.core.bpm.graph.Link;
import com.hotent.core.bpm.graph.OrthogonalType;
import com.hotent.core.bpm.graph.Point;
import com.hotent.core.bpm.graph.Port;
import com.hotent.core.bpm.graph.PortType;
import com.hotent.core.bpm.graph.Shape;
import com.hotent.core.bpm.graph.ShapeType;
import com.hotent.core.bpm.graph.TransformUtil.1;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformUtil {
	public static int Offset = 24;
	public static int minLen = 4;
	protected static Logger logger = LoggerFactory.getLogger(TransformUtil.class);

	private static List<Point> calcLinkPoints(Link link) {
      new ArrayList();
      List points;
      switch(1.$SwitchMap$com$hotent$core$bpm$graph$ShapeType[link.getShapeType().ordinal()]) {
      case 1:
         points = calcStraightLinkPoints(link);
         break;
      case 2:
         points = calcFreeLinkPoints(link);
         break;
      case 3:
         points = calcOrthogonalLinkPoints(link);
         break;
      case 4:
         points = calcOrthogonalLinkPoints(link);
         break;
      default:
         points = calcOrthogonalLinkPoints(link);
      }

      return points;
   }

	private static List<Point> calcOrthogonalLinkPoints(Link link) {
      ArrayList points = new ArrayList();
      Shape startNode = link.getStartNode();
      Shape endNode = link.getEndNode();
      Port startPort = link.getStartPort();
      Port endPort = link.getEndPort();
      OrthogonalType type = calcOrthogonalLinkRelativPosition(startNode, endNode, startPort, endPort);
      switch(1.$SwitchMap$com$hotent$core$bpm$graph$OrthogonalType[type.ordinal()]) {
      case 1:
         logger.info("Top Top");
         points = caculateTopTop(startNode, endNode, startPort, endPort);
         break;
      case 2:
         logger.info("Top Left");
         points = caculateTopLeft(startNode, endNode, startPort, endPort);
         break;
      case 3:
         logger.info("Top Right");
         points = caculateTopRight(startNode, endNode, startPort, endPort);
         break;
      case 4:
         logger.info("Top Bottom");
         points = caculateTopBottom(startNode, endNode, startPort, endPort);
         break;
      case 5:
         logger.info(" Left Top ");
         points = caculateLeftTop(startNode, endNode, startPort, endPort);
         break;
      case 6:
         logger.info(" Left Left ");
         points = caculateLeftLeft(startNode, endNode, startPort, endPort);
         break;
      case 7:
         logger.info(" Left Right ");
         points = caculateLeftRight(startNode, endNode, startPort, endPort);
         break;
      case 8:
         logger.info(" Left Bottom ");
         points = caculateLeftBottom(startNode, endNode, startPort, endPort);
         break;
      case 9:
         logger.info(" Right Top ");
         points = caculateRightTop(startNode, endNode, startPort, endPort);
         break;
      case 10:
         logger.info(" Right Left ");
         points = caculateRightLeft(startNode, endNode, startPort, endPort);
         break;
      case 11:
         logger.info(" Right Right ");
         points = caculateRightRight(startNode, endNode, startPort, endPort);
         break;
      case 12:
         logger.info(" Right Bottom ");
         points = caculateRightBottom(startNode, endNode, startPort, endPort);
         break;
      case 13:
         logger.info(" Bottom Top ");
         points = caculateBottomTop(startNode, endNode, startPort, endPort);
         break;
      case 14:
         logger.info(" Bottom Left ");
         points = caculateBottomLeft(startNode, endNode, startPort, endPort);
         break;
      case 15:
         logger.info(" Bottom Right ");
         points = caculateBottomRight(startNode, endNode, startPort, endPort);
         break;
      case 16:
         logger.info(" Bottom Bottom ");
         points = caculateBottomBottom(startNode, endNode, startPort, endPort);
      }

      return points;
   }

	private static OrthogonalType calcOrthogonalLinkRelativPosition(Shape source, Shape target, Port startPort,
			Port endPort) {
		double startx = (double) source.getX() + (double) source.getW() * startPort.getX()
				+ startPort.getHorizontalOffset();
		double starty = (double) source.getY() + (double) source.getH() * startPort.getY()
				+ startPort.getVerticalOffset();
		Point start = new Point((float) startx, (float) starty);
		double endx = (double) target.getX() + (double) target.getW() * endPort.getX() + endPort.getHorizontalOffset();
		double endy = (double) target.getY() + (double) target.getH() * endPort.getY() + endPort.getVerticalOffset();
		Point end = new Point((float) endx, (float) endy);
		Point splt = new Point(source.getX(), source.getY());
		Point sprt = new Point(source.getX() + source.getW(), source.getY());
		Point sprb = new Point(source.getX() + source.getW(), source.getY() + source.getH());
		Point splb = new Point(source.getX(), source.getY() + source.getH());
		Point spc = new Point(source.getX() + source.getW() / 2.0F, source.getY() + source.getH() / 2.0F);
		Point tplt = new Point(target.getX(), target.getY());
		Point tprt = new Point(target.getX() + target.getW(), target.getY());
		Point tprb = new Point(target.getX() + target.getW(), target.getY() + target.getH());
		Point tplb = new Point(target.getX(), target.getY() + target.getH());
		Point tpc = new Point(target.getX() + target.getW() / 2.0F, target.getY() + target.getH() / 2.0F);
		if (isInsideTriangle(splt, sprt, spc, start)) {
			if (isInsideTriangle(tplt, tprt, tpc, end)) {
				return OrthogonalType.TopTop;
			}

			if (isInsideTriangle(tprt, tprb, tpc, end)) {
				return OrthogonalType.TopRight;
			}

			if (isInsideTriangle(tprb, tplb, tpc, end)) {
				return OrthogonalType.TopBottom;
			}

			if (isInsideTriangle(tplb, tplt, tpc, end)) {
				return OrthogonalType.TopLeft;
			}
		} else if (isInsideTriangle(sprt, sprb, spc, start)) {
			if (isInsideTriangle(tplt, tprt, tpc, end)) {
				return OrthogonalType.RightTop;
			}

			if (isInsideTriangle(tprt, tprb, tpc, end)) {
				return OrthogonalType.RightRight;
			}

			if (isInsideTriangle(tprb, tplb, tpc, end)) {
				return OrthogonalType.RightBottom;
			}

			if (isInsideTriangle(tplb, tplt, tpc, end)) {
				return OrthogonalType.RightLeft;
			}
		} else if (isInsideTriangle(sprb, splb, spc, start)) {
			if (isInsideTriangle(tplt, tprt, tpc, end)) {
				return OrthogonalType.BottomTop;
			}

			if (isInsideTriangle(tprt, tprb, tpc, end)) {
				return OrthogonalType.BottomRight;
			}

			if (isInsideTriangle(tprb, tplb, tpc, end)) {
				return OrthogonalType.BottomBottom;
			}

			if (isInsideTriangle(tplb, tplt, tpc, end)) {
				return OrthogonalType.BottomLeft;
			}
		} else if (isInsideTriangle(splb, splt, spc, start)) {
			if (isInsideTriangle(tplt, tprt, tpc, end)) {
				return OrthogonalType.LeftTop;
			}

			if (isInsideTriangle(tprt, tprb, tpc, end)) {
				return OrthogonalType.LeftRight;
			}

			if (isInsideTriangle(tprb, tplb, tpc, end)) {
				return OrthogonalType.LeftBottom;
			}

			if (isInsideTriangle(tplb, tplt, tpc, end)) {
				return OrthogonalType.LeftLeft;
			}
		}

		return OrthogonalType.RightLeft;
	}

	private static boolean isInsideTriangle(Point A, Point B, Point C, Point P) {
		float ab = (A.getX() - P.getX()) * (B.getY() - P.getY()) - (B.getX() - P.getX()) * (A.getY() - P.getY());
		float bc = (B.getX() - P.getX()) * (C.getY() - P.getY()) - (C.getX() - P.getX()) * (B.getY() - P.getY());
		float ca = (C.getX() - P.getX()) * (A.getY() - P.getY()) - (A.getX() - P.getX()) * (C.getY() - P.getY());
		return (ab >= 0.0F && bc >= 0.0F || ab <= 0.0F && bc <= 0.0F)
				&& (bc >= 0.0F && ca >= 0.0F || bc <= 0.0F && ca <= 0.0F);
	}

	private static List<Point> calcStraightLinkPoints(Link link) {
		link.getIntermediatePoints().clear();
		return calcFreeLinkPoints(link);
	}

	private static List<Point> calcFreeLinkPoints(Link link) {
		ArrayList points = new ArrayList();
		Shape startNode = link.getStartNode();
		Shape endNode = link.getEndNode();
		Port startPort = link.getStartPort();
		Port endPort = link.getEndPort();
		List intermediatePoints = link.getIntermediatePoints();
		double firstX = (double) startNode.getX() + (double) startNode.getW() * startPort.getX()
				+ startPort.getHorizontalOffset();
		double firstY = (double) startNode.getY() + (double) startNode.getH() * startPort.getY()
				+ startPort.getVerticalOffset();
		Point first = new Point((float) firstX, (float) firstY);
		double lastX = (double) endNode.getX() + (double) endNode.getW() * endPort.getX()
				+ endPort.getHorizontalOffset();
		double lastY = (double) endNode.getY() + (double) endNode.getH() * endPort.getY() + endPort.getVerticalOffset();
		Point last = new Point((float) lastX, (float) lastY);
		Point temp1 = intermediatePoints.size() > 0 ? (Point) intermediatePoints.get(0) : last;
		Point temp2 = intermediatePoints.size() > 0
				? (Point) intermediatePoints.get(intermediatePoints.size() - 1)
				: first;
		Point temp3 = null;
		Point temp4 = null;
		temp3 = handleOverlap(startNode, first, temp1);
		temp4 = handleOverlap(endNode, last, temp2);
		points.add(temp3 == null ? first : temp3);
		points.addAll(intermediatePoints);
		points.add(temp4 == null ? last : temp4);
		return points;
	}

	private static Point handleOverlap(Shape shape, Point p1, Point p2) {
		Point a = new Point(shape.getX(), shape.getY());
		Point b = new Point(shape.getX() + shape.getW(), shape.getY());
		Point c = new Point(shape.getX() + shape.getW(), shape.getY() + shape.getH());
		Point d = new Point(shape.getX(), shape.getY() + shape.getH());
		Point[] cps = new Point[4];
		if (!isInsideLine(a, b, p1)) {
			cps[0] = getCrosspoint(p1, p2, a, b);
		}

		if (!isInsideLine(b, c, p1)) {
			cps[1] = getCrosspoint(p1, p2, b, c);
		}

		if (!isInsideLine(c, d, p1)) {
			cps[2] = getCrosspoint(p1, p2, c, d);
		}

		if (!isInsideLine(d, a, p1)) {
			cps[3] = getCrosspoint(p1, p2, d, a);
		}

		Point[] arr$ = cps;
		int len$ = cps.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Point p = arr$[i$];
			if (p != null) {
				return p;
			}
		}

		return null;
	}

	private static boolean isInsideLine(Point p1, Point p2, Point p) {
		double x = (double) p.getX();
		double y = (double) p.getY();
		double x1 = (double) p1.getX();
		double y1 = (double) p1.getY();
		double x2 = (double) p2.getX();
		double y2 = (double) p2.getY();
		if (Double.compare(x1, x2) == 0) {
			return Double.compare(x, x2) == 0;
		} else if (Double.compare(y1, y2) == 0) {
			return Double.compare(y, y2) == 0;
		} else {
			double s1 = x - x1;
			double t1 = y - y1;
			double s2 = x1 - x2;
			double t2 = y1 - y2;
			return Double.compare(s1 / s2, t1 / t2) == 0;
		}
	}

	private static Point getCrosspoint(Point a, Point b, Point c, Point d) {
		float ax = a.getX();
		float ay = a.getY();
		float bx = b.getX();
		float by = b.getY();
		float cx = c.getX();
		float cy = c.getY();
		float dx = d.getX();
		float dy = d.getY();
		if (Math.abs(by - ay) + Math.abs(bx - ax) + Math.abs(dy - cy) + Math.abs(dx - cx) == 0.0F) {
			return cx - ax + (cy - ay) == 0.0F ? new Point(ax, ay) : null;
		} else if (Math.abs(by - ay) + Math.abs(bx - ax) == 0.0F) {
			return (ax - dx) * (cy - dy) - (ay - dy) * (cx - dx) == 0.0F ? new Point(ax, ay) : null;
		} else if (Math.abs(dy - cy) + Math.abs(dx - cx) == 0.0F) {
			return (dx - bx) * (ay - by) - (dy - by) * (ax - bx) == 0.0F ? new Point(cx, cy) : null;
		} else if ((by - ay) * (cx - dx) - (bx - ax) * (cy - dy) == 0.0F) {
			return null;
		} else {
			float x = ((bx - ax) * (cx - dx) * (cy - ay) - cx * (bx - ax) * (cy - dy) + ax * (by - ay) * (cx - dx))
					/ ((by - ay) * (cx - dx) - (bx - ax) * (cy - dy));
			float y = ((by - ay) * (cy - dy) * (cx - ax) - cy * (by - ay) * (cx - dx) + ay * (bx - ax) * (cy - dy))
					/ ((bx - ax) * (cy - dy) - (by - ay) * (cx - dx));
			return (x - ax) * (x - bx) <= 0.0F && (x - cx) * (x - dx) <= 0.0F && (y - ay) * (y - by) <= 0.0F
					&& (y - cy) * (y - dy) <= 0.0F ? new Point(x, y) : null;
		}
	}

	public static String add(String para1, String para2) {
		double d1 = 0.0D;
		double d2 = 0.0D;
		if (StringUtil.isNotEmpty(para1)) {
			d1 = Double.parseDouble(para1);
		}

		if (StringUtil.isNotEmpty(para2)) {
			d2 = Double.parseDouble(para2);
		}

		d1 += d2;
		return String.valueOf(d1);
	}

	public static String bold(String str) {
		return "<b>" + str + "</b>";
	}

	public static double nan2Zero(String str) {
		return !StringUtil.isEmpty(str) && !str.equalsIgnoreCase("NaN") ? Double.parseDouble(str) : 0.0D;
	}

	public static void display(String str) {
		logger.info(str);
	}

	public static int splitLength(String str, String regex) {
		int len = str.split(regex).length;
		return len;
	}

	public static String accumulate(String childrenY, String parentY) {
		double retVal = 0.0D;
		if (!StringUtil.isEmpty(childrenY)) {
			String[] childrenY_ = childrenY.split(",");
			String[] arr$ = childrenY_;
			int len$ = childrenY_.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String y = arr$[i$];
				retVal += nan2Zero(y);
			}
		}

		if (StringUtil.isNotEmpty(parentY)) {
			retVal += nan2Zero(parentY);
		}

		return String.valueOf(retVal);
	}

	public static String min(String para1, String para2) {
		double d1 = 0.0D;
		double d2 = 0.0D;
		if (StringUtil.isNotEmpty(para1)) {
			d1 = Double.parseDouble(para1);
		}

		if (StringUtil.isNotEmpty(para2)) {
			d2 = Double.parseDouble(para2);
		}

		return d1 < d2 ? String.valueOf(d1) : String.valueOf(d2);
	}

	public static String max(String para1, String para2) {
		Integer d1 = Integer.valueOf(0);
		Integer d2 = Integer.valueOf(0);
		if (StringUtil.isNotEmpty(para1)) {
			d1 = Integer.valueOf(Integer.parseInt(para1));
		}

		if (StringUtil.isNotEmpty(para2)) {
			d2 = Integer.valueOf(Integer.parseInt(para2));
		}

		return d1.intValue() > d2.intValue() ? String.valueOf(d1) : String.valueOf(d2);
	}

	public static String calc(String shapeType, String fName, String fX, String fY, String fW, String fH,
			String sPortType, String sPortX, String sPortY, String sPortHOffset, String sPortVOffset, String tName,
			String tX, String tY, String tW, String tH, String tPortType, String tPortX, String tPortY,
			String tPortHOffset, String tPortVOffset, String points) {
		sPortX = !"".equals(sPortX) && sPortX != null ? sPortX : "0.5";
		sPortY = !"".equals(sPortY) && sPortY != null ? sPortY : "0.5";
		tPortX = !"".equals(tPortX) && tPortX != null ? tPortX : "0.5";
		tPortY = !"".equals(tPortY) && tPortY != null ? tPortY : "0.5";
		Link link = new Link();
		if (ShapeType.FREE.getText().equalsIgnoreCase(shapeType)) {
			link.setShapeType(ShapeType.FREE);
		} else if (ShapeType.STRAIGHT.getText().equalsIgnoreCase(shapeType)) {
			link.setShapeType(ShapeType.STRAIGHT);
		} else if (ShapeType.OBLIQUE.getText().equalsIgnoreCase(shapeType)) {
			link.setShapeType(ShapeType.OBLIQUE);
		} else {
			link.setShapeType(ShapeType.ORTHOGONAL);
		}

		float startX = (float) nan2Zero(fX);
		float startY = (float) nan2Zero(fY);
		float startW = (float) nan2Zero(fW);
		float startH = (float) nan2Zero(fH);
		Shape startShape = new Shape(fName, startX, startY, startW, startH);
		float endX = (float) nan2Zero(tX);
		float endY = (float) nan2Zero(tY);
		float endW = (float) nan2Zero(tW);
		float endH = (float) nan2Zero(tH);
		Shape endShape = new Shape(tName, endX, endY, endW, endH);
		PortType startPortType;
		if (PortType.NODE_PART_REFERENCE.getText().equalsIgnoreCase(sPortType)) {
			startPortType = PortType.NODE_PART_REFERENCE;
		} else if (PortType.AUTOMATIC_SIDE.getText().equalsIgnoreCase(sPortType)) {
			startPortType = PortType.AUTOMATIC_SIDE;
		} else {
			startPortType = PortType.POSITION;
		}

		float startPortX = (float) nan2Zero(sPortX);
		float startPortY = (float) nan2Zero(sPortY);
		float startPortVOffset = (float) nan2Zero(sPortVOffset);
		float startPortHOffset = (float) nan2Zero(sPortHOffset);
		Port startPort = new Port(startPortType, (double) startPortX, (double) startPortY, (double) startPortHOffset,
				(double) startPortVOffset, (String) null, false);
		PortType endPortType;
		if (PortType.NODE_PART_REFERENCE.getText().equalsIgnoreCase(tPortType)) {
			endPortType = PortType.NODE_PART_REFERENCE;
		} else if (PortType.AUTOMATIC_SIDE.getText().equalsIgnoreCase(tPortType)) {
			endPortType = PortType.AUTOMATIC_SIDE;
		} else {
			endPortType = PortType.POSITION;
		}

		float endPortX = (float) nan2Zero(tPortX);
		float endPortY = (float) nan2Zero(tPortY);
		float endPortVOffset = (float) nan2Zero(tPortVOffset);
		float endPortHOffset = (float) nan2Zero(tPortHOffset);
		Port endPort = new Port(endPortType, (double) endPortX, (double) endPortY, (double) endPortHOffset,
				(double) endPortVOffset, (String) null, false);
		ArrayList intermediatePoints = new ArrayList();
		if (points != null && !"".equals(points)) {
			String[] linkPoints = points.split(",");
			String[] arr$ = linkPoints;
			int len$ = linkPoints.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String p = arr$[i$];
				String[] point = p.split(":");
				double x = nan2Zero(point[0]);
				double y = nan2Zero(point[1]);
				intermediatePoints.add(new Point((float) x, (float) y));
			}
		}

		link.setStartNode(startShape);
		link.setEndNode(endShape);
		link.setStartPort(startPort);
		link.setEndPort(endPort);
		link.setIntermediatePoints(intermediatePoints);
		List arg55 = calcLinkPoints(link);
		return getPointXml(arg55);
	}

	public static List<Point> caculatePoints(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
      ArrayList list = null;
      switch(1.$SwitchMap$com$hotent$core$bpm$graph$DirEnum[fromShape.getDirectory().ordinal()]) {
      case 1:
         switch(1.$SwitchMap$com$hotent$core$bpm$graph$DirEnum[toShape.getDirectory().ordinal()]) {
         case 1:
            logger.info("Top Top");
            list = caculateTopTop(fromShape, toShape, fromPort, toPort);
            return list;
         case 2:
            logger.info("Top Left");
            list = caculateTopLeft(fromShape, toShape, fromPort, toPort);
            return list;
         case 3:
            logger.info("Top Right");
            list = caculateTopRight(fromShape, toShape, fromPort, toPort);
            return list;
         case 4:
            logger.info("Top Bottom");
            list = caculateTopBottom(fromShape, toShape, fromPort, toPort);
            return list;
         default:
            return list;
         }
      case 2:
         switch(1.$SwitchMap$com$hotent$core$bpm$graph$DirEnum[toShape.getDirectory().ordinal()]) {
         case 1:
            logger.info(" Left Top ");
            list = caculateLeftTop(fromShape, toShape, fromPort, toPort);
            return list;
         case 2:
            logger.info(" Left Left ");
            list = caculateLeftLeft(fromShape, toShape, fromPort, toPort);
            return list;
         case 3:
            logger.info(" Left Right ");
            list = caculateLeftRight(fromShape, toShape, fromPort, toPort);
            return list;
         case 4:
            logger.info(" Left Bottom ");
            list = caculateLeftBottom(fromShape, toShape, fromPort, toPort);
            return list;
         default:
            return list;
         }
      case 3:
         switch(1.$SwitchMap$com$hotent$core$bpm$graph$DirEnum[toShape.getDirectory().ordinal()]) {
         case 1:
            logger.info(" Right Top ");
            list = caculateRightTop(fromShape, toShape, fromPort, toPort);
            return list;
         case 2:
            logger.info(" Right Left ");
            list = caculateRightLeft(fromShape, toShape, fromPort, toPort);
            return list;
         case 3:
            logger.info(" Right Right ");
            list = caculateRightRight(fromShape, toShape, fromPort, toPort);
            return list;
         case 4:
            logger.info(" Right Bottom ");
            list = caculateRightBottom(fromShape, toShape, fromPort, toPort);
            return list;
         default:
            return list;
         }
      case 4:
         switch(1.$SwitchMap$com$hotent$core$bpm$graph$DirEnum[toShape.getDirectory().ordinal()]) {
         case 1:
            logger.info(" Bottom Top ");
            list = caculateBottomTop(fromShape, toShape, fromPort, toPort);
            break;
         case 2:
            logger.info(" Bottom Left ");
            list = caculateBottomLeft(fromShape, toShape, fromPort, toPort);
            break;
         case 3:
            logger.info(" Bottom Right ");
            list = caculateBottomRight(fromShape, toShape, fromPort, toPort);
            break;
         case 4:
            logger.info(" Bottom Bottom ");
            list = caculateBottomBottom(fromShape, toShape, fromPort, toPort);
         }
      }

      return list;
   }

	public static String caculate(Shape fromShape, Shape toShape) {
		return null;
	}

	public static ArrayList<Point> caculateTopTop(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		float tmpy;
		Point tmpx;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		Point p6;
		float tmpx1;
		if (toShape.getBottomRightY() + (float) minLen < fromShape.getY() - (float) minLen) {
			if (toShape.getX() - (float) minLen <= fromX && toShape.getBottomRightX() + (float) minLen >= fromX) {
				tmpy = 0.0F;
				tmpx1 = (fromShape.getY() + toShape.getBottomRightY()) / 2.0F;
				if (toX <= fromX) {
					tmpy = toShape.getBottomRightX() + (float) Offset;
				} else {
					tmpy = toShape.getX() - (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX, tmpx1);
				p3 = new Point(tmpy, tmpx1);
				p4 = new Point(tmpy, toY - (float) Offset);
				p5 = new Point(toX, toY - (float) Offset);
				p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				tmpy = toShape.getY() - (float) Offset;
				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpy);
				p2 = new Point(toX, tmpy);
				p3 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
			}
		} else if (toShape.getY() - (float) minLen < fromShape.getBottomRightY() + (float) minLen) {
			tmpy = toShape.getY() - (float) Offset;
			if (toShape.getY() < fromShape.getY()) {
				tmpy = toShape.getY() - (float) Offset;
			} else {
				tmpy = fromShape.getY() - (float) Offset;
			}

			tmpx = new Point(fromX, fromY);
			p1 = new Point(fromX, tmpy);
			p2 = new Point(toX, tmpy);
			p3 = new Point(toX, toY);
			list.add(tmpx);
			list.add(p1);
			list.add(p2);
			list.add(p3);
		} else if (fromShape.getX() - (float) minLen <= toX && fromShape.getBottomRightX() + (float) minLen >= toX) {
			tmpy = (fromShape.getBottomRightY() + toShape.getY()) / 2.0F;
			tmpx1 = 0.0F;
			if (fromX <= toX) {
				tmpx1 = fromShape.getBottomRightX() + (float) Offset;
			} else {
				tmpx1 = fromShape.getX() - (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(fromX, fromY - (float) Offset);
			p3 = new Point(tmpx1, fromY - (float) Offset);
			p4 = new Point(tmpx1, tmpy);
			p5 = new Point(toX, tmpy);
			p6 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		} else {
			Point tmpy1 = new Point(fromX, fromY);
			tmpx = new Point(fromX, fromY - (float) Offset);
			p1 = new Point(toX, fromY - (float) Offset);
			p2 = new Point(toX, toY);
			list.add(tmpy1);
			list.add(tmpx);
			list.add(p1);
			list.add(p2);
		}

		return list;
	}

	public static ArrayList<Point> caculateTopRight(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpx;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		float tmpy1;
		float tmpx1;
		if (fromX >= toX - (float) minLen) {
			if (toY < fromY - (float) minLen) {
				Point tmpy = new Point(fromX, fromY);
				tmpx = new Point(fromX, toY);
				p1 = new Point(toX, toY);
				list.add(tmpy);
				list.add(tmpx);
				list.add(p1);
			} else if (toX + (float) minLen < fromShape.getX() - (float) minLen) {
				tmpy1 = (fromShape.getX() + toX) / 2.0F;
				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, fromY - (float) Offset);
				p2 = new Point(tmpy1, fromY - (float) Offset);
				p3 = new Point(tmpy1, toY);
				p4 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				tmpy1 = 0.0F;
				if (toShape.getY() < fromY) {
					tmpy1 = toShape.getY() - (float) Offset;
				} else {
					tmpy1 = fromY - (float) Offset;
				}

				tmpx1 = fromShape.getBottomRightX() + (float) Offset;
				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX, tmpy1);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(tmpx1, toY);
				p5 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		} else if (toShape.getBottomRightY() + (float) minLen < fromY - (float) minLen) {
			tmpy1 = (fromY + toShape.getBottomRightY()) / 2.0F;
			tmpx = new Point(fromX, fromY);
			p1 = new Point(fromX, tmpy1);
			p2 = new Point(toX + (float) Offset, tmpy1);
			p3 = new Point(toX + (float) Offset, toY);
			p4 = new Point(toX, toY);
			list.add(tmpx);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			tmpy1 = 0.0F;
			tmpx1 = 0.0F;
			p1 = new Point(fromX, fromY);
			if (toX + (float) Offset >= fromShape.getBottomRightX() + (float) Offset) {
				tmpx1 = toX + (float) Offset;
			} else {
				tmpx1 = fromShape.getBottomRightX() + (float) Offset;
			}

			if (toShape.getY() - (float) Offset <= fromY - (float) Offset) {
				tmpy1 = toShape.getY() - (float) Offset;
			} else {
				tmpy1 = fromY - (float) Offset;
			}

			p2 = new Point(fromX, tmpy1);
			p3 = new Point(tmpx1, tmpy1);
			p4 = new Point(tmpx1, toY);
			p5 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateTopBottom(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		float tmpy;
		Point tmpx;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		if (fromX < toX) {
			if (toY + (float) minLen <= fromY - (float) minLen) {
				tmpy = (fromY + toY) / 2.0F;
				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpy);
				p2 = new Point(toX, tmpy);
				p3 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else if (fromShape.getBottomRightX() + (float) minLen < toShape.getX() - (float) minLen) {
				tmpy = (toShape.getX() + fromShape.getBottomRightX()) / 2.0F;
				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, fromY - (float) Offset);
				p2 = new Point(tmpy, fromY - (float) Offset);
				p3 = new Point(tmpy, toY + (float) Offset);
				p4 = new Point(toX, toY + (float) Offset);
				p5 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				if (fromShape.getY() <= toShape.getY()) {
					tmpy = fromShape.getY() - (float) Offset;
				} else {
					tmpy = toShape.getY() - (float) Offset;
				}

				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpy);
				p2 = new Point(toShape.getBottomRightX() + (float) Offset, tmpy);
				p3 = new Point(toShape.getBottomRightX() + (float) Offset, toY + (float) Offset);
				p4 = new Point(toX, toY + (float) Offset);
				p5 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		} else {
			Point p6;
			float tmpx1;
			if (fromX == toX) {
				if (toY + (float) minLen < fromY - (float) minLen) {
					Point tmpy1 = new Point(fromX, fromY);
					tmpx = new Point(toX, toY);
					list.add(tmpy1);
					list.add(tmpx);
				} else {
					if (fromShape.getY() < toShape.getY()) {
						tmpy = fromShape.getY() - (float) Offset;
					} else {
						tmpy = toShape.getY() - (float) Offset;
					}

					if (fromShape.getX() < toShape.getX()) {
						tmpx1 = fromShape.getX() - (float) Offset;
					} else {
						tmpx1 = toShape.getX() - (float) Offset;
					}

					p1 = new Point(fromX, fromY);
					p2 = new Point(fromX, tmpy);
					p3 = new Point(tmpx1, tmpy);
					p4 = new Point(tmpx1, toY + (float) Offset);
					p5 = new Point(toX, toY + (float) Offset);
					p6 = new Point(toX, toY);
					list.add(p1);
					list.add(p2);
					list.add(p3);
					list.add(p4);
					list.add(p5);
					list.add(p6);
				}
			} else if (toY + (float) minLen < fromY - (float) minLen) {
				tmpy = (fromY + toY) / 2.0F;
				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpy);
				p2 = new Point(toX, tmpy);
				p3 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else if (toShape.getBottomRightX() + (float) minLen < fromShape.getX() - (float) minLen) {
				tmpy = (fromShape.getX() + toShape.getBottomRightX()) / 2.0F;
				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, fromY - (float) Offset);
				p2 = new Point(tmpy, fromY - (float) Offset);
				p3 = new Point(tmpy, toY + (float) Offset);
				p4 = new Point(toX, toY + (float) Offset);
				p5 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				if (fromShape.getY() < toShape.getY()) {
					tmpy = fromShape.getY() - (float) Offset;
				} else {
					tmpy = toShape.getY() - (float) Offset;
				}

				if (fromShape.getX() < toShape.getX()) {
					tmpx1 = fromShape.getX() - (float) Offset;
				} else {
					tmpx1 = toShape.getX() - (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX, tmpy);
				p3 = new Point(tmpx1, tmpy);
				p4 = new Point(tmpx1, toY + (float) Offset);
				p5 = new Point(toX, toY + (float) Offset);
				p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}
		}

		return list;
	}

	public static ArrayList<Point> caculateTopLeft(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpx;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		float tmpy1;
		float tmpx1;
		if (toShape.getX() - (float) minLen > fromX) {
			if (toY < fromShape.getY() - (float) minLen) {
				Point tmpy = new Point(fromX, fromY);
				tmpx = new Point(fromX, toY);
				p1 = new Point(toX, toY);
				list.add(tmpy);
				list.add(tmpx);
				list.add(p1);
			} else if (fromShape.getBottomRightX() + (float) minLen < toShape.getX() - (float) minLen) {
				tmpy1 = (toShape.getX() + fromShape.getBottomRightX()) / 2.0F;
				tmpx = new Point(fromX, fromY);
				p1 = new Point(fromX, fromY - (float) Offset);
				p2 = new Point(tmpy1, fromY - (float) Offset);
				p3 = new Point(tmpy1, toY);
				p4 = new Point(toX, toY);
				list.add(tmpx);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				tmpy1 = 0.0F;
				tmpx1 = 0.0F;
				if (toShape.getY() <= fromShape.getY()) {
					tmpy1 = toShape.getY() - (float) Offset;
				} else {
					tmpy1 = fromShape.getY() - (float) Offset;
				}

				if (toShape.getX() <= fromShape.getX()) {
					tmpx1 = toShape.getX() - (float) Offset;
				} else {
					tmpx1 = fromShape.getX() - (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX, tmpy1);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(tmpx1, toY);
				p5 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		} else if (fromShape.getY() - (float) minLen >= toShape.getBottomRightY() + (float) minLen) {
			tmpy1 = (fromShape.getY() + toShape.getBottomRightY()) / 2.0F;
			tmpx = new Point(fromX, fromY);
			p1 = new Point(fromX, tmpy1);
			p2 = new Point(toX - (float) Offset, tmpy1);
			p3 = new Point(toX - (float) Offset, toY);
			p4 = new Point(toX, toY);
			list.add(tmpx);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			tmpy1 = 0.0F;
			tmpx1 = 0.0F;
			if (toShape.getY() < fromShape.getY()) {
				tmpy1 = toShape.getY() - (float) Offset;
			} else {
				tmpy1 = fromShape.getY() - (float) Offset;
			}

			if (toShape.getX() < fromShape.getX()) {
				tmpx1 = toShape.getX() - (float) Offset;
			} else {
				tmpx1 = fromShape.getX() - (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(fromX, tmpy1);
			p3 = new Point(tmpx1, tmpy1);
			p4 = new Point(tmpx1, toY);
			p5 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateRightTop(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		float tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		Point tmpy1;
		if (fromShape.getBottomRightX() + (float) minLen < toShape.getX() - (float) minLen) {
			if (fromY > toShape.getY() + (float) minLen) {
				tmpy = (toShape.getX() + fromX) / 2.0F;
				p1 = new Point(fromX, fromY);
				p2 = new Point(tmpy, fromY);
				p3 = new Point(tmpy, toY - (float) Offset);
				p4 = new Point(toX, toY - (float) Offset);
				p5 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				tmpy1 = new Point(fromX, fromY);
				p1 = new Point(toX, fromY);
				p2 = new Point(toX, toY);
				list.add(tmpy1);
				list.add(p1);
				list.add(p2);
			}
		} else {
			Point p51;
			float p11;
			if (toShape.getY() - (float) minLen < fromY) {
				tmpy = 0.0F;
				p11 = 0.0F;
				if (toShape.getY() < fromShape.getY()) {
					tmpy = toShape.getY() - (float) Offset;
				} else {
					tmpy = fromShape.getY() - (float) Offset;
				}

				if (toShape.getBottomRightX() >= fromShape.getBottomRightX()) {
					p11 = toShape.getBottomRightX() + (float) Offset;
				} else {
					p11 = fromShape.getBottomRightX() + (float) Offset;
				}

				p2 = new Point(fromX, fromY);
				p3 = new Point(p11, fromY);
				p4 = new Point(p11, tmpy);
				p5 = new Point(toX, tmpy);
				p51 = new Point(toX, toY);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p51);
			} else if (toShape.getY() - (float) minLen < fromShape.getBottomRightY() + (float) minLen) {
				if (toX > fromShape.getBottomRightX() + (float) minLen) {
					tmpy1 = new Point(fromX, fromY);
					p1 = new Point(toX, fromY);
					p2 = new Point(toX, toY);
					list.add(tmpy1);
					list.add(p1);
					list.add(p2);
				} else {
					tmpy = 0.0F;
					p11 = 0.0F;
					if (toShape.getY() < fromShape.getY()) {
						tmpy = toShape.getY() - (float) Offset;
					} else {
						tmpy = fromShape.getY() - (float) Offset;
					}

					if (toShape.getBottomRightX() >= fromShape.getBottomRightX()) {
						p11 = toShape.getBottomRightX() + (float) Offset;
					} else {
						p11 = fromShape.getBottomRightX() + (float) Offset;
					}

					p2 = new Point(fromX, fromY);
					p3 = new Point(p11, fromY);
					p4 = new Point(p11, tmpy);
					p5 = new Point(toX, tmpy);
					p51 = new Point(toX, toY);
					list.add(p2);
					list.add(p3);
					list.add(p4);
					list.add(p5);
					list.add(p51);
				}
			} else if (toX > fromShape.getBottomRightX() + (float) minLen) {
				tmpy1 = new Point(fromX, fromY);
				p1 = new Point(toX, fromY);
				p2 = new Point(toX, toY);
				list.add(tmpy1);
				list.add(p1);
				list.add(p2);
			} else {
				tmpy = (fromShape.getBottomRightY() + toShape.getY()) / 2.0F;
				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX + (float) Offset, fromY);
				p3 = new Point(fromX + (float) Offset, tmpy);
				p4 = new Point(toX, tmpy);
				p5 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		}

		return list;
	}

	public static ArrayList<Point> caculateRightRight(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpx;
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		Point p6;
		float tmpx1;
		float tmpy1;
		if (toShape.getX() - (float) minLen >= fromShape.getBottomRightX() + (float) minLen) {
			if (toShape.getBottomRightY() + (float) minLen >= fromY && toShape.getY() - (float) minLen <= fromY) {
				tmpx1 = (toShape.getX() + fromX) / 2.0F;
				tmpy1 = 0.0F;
				if (fromY > toY) {
					tmpy1 = toShape.getBottomRightY() + (float) Offset;
				} else {
					tmpy1 = toShape.getY() - (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(tmpx1, fromY);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(toX + (float) Offset, tmpy1);
				p5 = new Point(toX + (float) Offset, toY);
				p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				tmpx = new Point(fromX, fromY);
				tmpy = new Point(toX + (float) Offset, fromY);
				p1 = new Point(toX + (float) Offset, toY);
				p2 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
			}
		} else if (toShape.getBottomRightX() + (float) minLen > fromShape.getX() - (float) minLen) {
			tmpx1 = 0.0F;
			if (toShape.getBottomRightX() > fromShape.getBottomRightX()) {
				tmpx1 = toShape.getBottomRightX() + (float) Offset;
			} else {
				tmpx1 = fromShape.getBottomRightX() + (float) Offset;
			}

			if (fromY == toY) {
				tmpy = new Point(fromX, fromY);
				p1 = new Point(tmpx1, fromY);
				p2 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
			} else {
				tmpy = new Point(fromX, fromY);
				p1 = new Point(tmpx1, fromY);
				p2 = new Point(tmpx1, toY);
				p3 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
			}
		} else if (toShape.getBottomRightY() + (float) minLen > fromShape.getY() - (float) minLen
				&& toShape.getY() - (float) minLen <= fromShape.getBottomRightY() + (float) minLen) {
			tmpx1 = (fromShape.getX() + toShape.getBottomRightX()) / 2.0F;
			tmpy1 = 0.0F;
			if (fromY <= toY) {
				tmpy1 = fromShape.getBottomRightY() + (float) Offset;
			} else {
				tmpy1 = fromShape.getY() - (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(fromX + (float) Offset, fromY);
			p3 = new Point(fromX + (float) Offset, tmpy1);
			p4 = new Point(tmpx1, tmpy1);
			p5 = new Point(tmpx1, toY);
			p6 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		} else {
			tmpx = new Point(fromX, fromY);
			tmpy = new Point(fromX + (float) Offset, fromY);
			p1 = new Point(fromX + (float) Offset, toY);
			p2 = new Point(toX, toY);
			list.add(tmpx);
			list.add(tmpy);
			list.add(p1);
			list.add(p2);
		}

		return list;
	}

	public static ArrayList<Point> caculateRightBottom(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		float tmpx1;
		if (toX > fromShape.getBottomRightX() + (float) minLen) {
			if (fromY > toY + (float) minLen) {
				Point tmpx = new Point(fromX, fromY);
				tmpy = new Point(toX, fromY);
				p1 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
			} else {
				tmpx1 = (fromShape.getBottomRightX() + toShape.getX()) / 2.0F;
				tmpy = new Point(fromX, fromY);
				p1 = new Point(tmpx1, fromY);
				p2 = new Point(tmpx1, toY + (float) Offset);
				p3 = new Point(toX, toY + (float) Offset);
				p4 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			}
		} else if (toShape.getY() + (float) minLen < fromShape.getY() - (float) minLen) {
			tmpx1 = (fromShape.getY() + toShape.getBottomRightY()) / 2.0F;
			tmpy = new Point(fromX, fromY);
			p1 = new Point(fromX + (float) Offset, fromY);
			p2 = new Point(fromX + (float) Offset, tmpx1);
			p3 = new Point(toX, tmpx1);
			p4 = new Point(toX, toY);
			list.add(tmpy);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			tmpx1 = 0.0F;
			float tmpy1 = 0.0F;
			if (toShape.getBottomRightX() > fromShape.getBottomRightX()) {
				tmpx1 = toShape.getBottomRightX() + (float) Offset;
			} else {
				tmpx1 = fromShape.getBottomRightX() + (float) Offset;
			}

			if (toShape.getBottomRightY() < fromShape.getBottomRightY()) {
				tmpy1 = fromShape.getBottomRightY() + (float) Offset;
			} else {
				tmpy1 = toShape.getBottomRightY() + (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(tmpx1, fromY);
			p3 = new Point(tmpx1, tmpy1);
			p4 = new Point(toX, tmpy1);
			Point p5 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateRightLeft(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		float tmpx1;
		if (toShape.getX() - (float) minLen > fromShape.getBottomRightX() + (float) minLen) {
			if (toY == fromY) {
				Point tmpx = new Point(fromX, fromY);
				tmpy = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
			} else {
				tmpx1 = (fromShape.getBottomRightX() + toShape.getX()) / 2.0F;
				tmpy = new Point(fromX, fromY);
				p1 = new Point(tmpx1, fromY);
				p2 = new Point(tmpx1, toY);
				p3 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
			}
		} else {
			Point p4;
			Point p5;
			if (toShape.getBottomRightY() + (float) minLen >= fromShape.getY() - (float) minLen
					&& fromShape.getBottomRightY() + (float) minLen >= toShape.getY() - (float) minLen) {
				tmpx1 = 0.0F;
				float tmpy1 = 0.0F;
				if (toShape.getBottomRightX() > fromShape.getBottomRightX()) {
					tmpx1 = toShape.getBottomRightX() + (float) Offset;
				} else {
					tmpx1 = fromShape.getBottomRightX() + (float) Offset;
				}

				if (fromY >= toY) {
					if (toShape.getY() < fromShape.getY()) {
						tmpy1 = toShape.getY() - (float) Offset;
					} else {
						tmpy1 = fromShape.getY() - (float) Offset;
					}
				} else if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
					tmpy1 = toShape.getBottomRightY() + (float) Offset;
				} else {
					tmpy1 = fromShape.getBottomRightY() + (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(tmpx1, fromY);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(toX - (float) Offset, tmpy1);
				p5 = new Point(toX - (float) Offset, toY);
				Point p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				tmpx1 = 0.0F;
				if (toShape.getBottomRightY() < fromShape.getBottomRightY()) {
					tmpx1 = (toShape.getBottomRightY() + fromShape.getY()) / 2.0F;
				} else {
					tmpx1 = (toShape.getY() + fromShape.getBottomRightY()) / 2.0F;
				}

				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX + (float) Offset, fromY);
				p2 = new Point(fromX + (float) Offset, tmpx1);
				p3 = new Point(toX - (float) Offset, tmpx1);
				p4 = new Point(toX - (float) Offset, toY);
				p5 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftTop(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		float tmpx;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		Point tmpx1;
		if (toShape.getBottomRightX() + (float) minLen >= fromShape.getX() - (float) minLen) {
			if (toShape.getY() - (float) minLen >= fromShape.getBottomRightY() + (float) minLen) {
				if (toX > fromShape.getX()) {
					tmpx = (fromShape.getBottomRightY() + toShape.getY()) / 2.0F;
					p1 = new Point(fromX, fromY);
					p2 = new Point(fromX - (float) Offset, fromY);
					p3 = new Point(fromX - (float) Offset, tmpx);
					p4 = new Point(toX, tmpx);
					p5 = new Point(toX, toY);
					list.add(p1);
					list.add(p2);
					list.add(p3);
					list.add(p4);
					list.add(p5);
				} else {
					tmpx1 = new Point(fromX, fromY);
					p1 = new Point(toX, fromY);
					p2 = new Point(toX, toY);
					list.add(tmpx1);
					list.add(p1);
					list.add(p2);
				}
			} else {
				Point p51;
				float p11;
				if (toShape.getY() - (float) minLen > fromY) {
					if (toX > fromShape.getX()) {
						tmpx = 0.0F;
						p11 = 0.0F;
						if (toShape.getX() >= fromShape.getX()) {
							tmpx = fromShape.getX() - (float) Offset;
						} else {
							tmpx = toShape.getX() - (float) Offset;
						}

						if (toShape.getY() < fromShape.getY()) {
							p11 = toShape.getY() - (float) Offset;
						} else {
							p11 = fromShape.getY() - (float) Offset;
						}

						p2 = new Point(fromX, fromY);
						p3 = new Point(tmpx, fromY);
						p4 = new Point(tmpx, p11);
						p5 = new Point(toX, p11);
						p51 = new Point(toX, toY);
						list.add(p2);
						list.add(p3);
						list.add(p4);
						list.add(p5);
						list.add(p51);
					} else {
						tmpx1 = new Point(fromX, fromY);
						p1 = new Point(toX, fromY);
						p2 = new Point(toX, toY);
						list.add(tmpx1);
						list.add(p1);
						list.add(p2);
					}
				} else {
					tmpx = 0.0F;
					p11 = 0.0F;
					if (toShape.getX() >= fromShape.getX()) {
						tmpx = fromShape.getX() - (float) Offset;
					} else {
						tmpx = toShape.getX() - (float) Offset;
					}

					if (toShape.getY() < fromShape.getY()) {
						p11 = toShape.getY() - (float) Offset;
					} else {
						p11 = fromShape.getY() - (float) Offset;
					}

					p2 = new Point(fromX, fromY);
					p3 = new Point(tmpx, fromY);
					p4 = new Point(tmpx, p11);
					p5 = new Point(toX, p11);
					p51 = new Point(toX, toY);
					list.add(p2);
					list.add(p3);
					list.add(p4);
					list.add(p5);
					list.add(p51);
				}
			}
		} else if (toShape.getY() - (float) minLen >= fromY) {
			tmpx1 = new Point(fromX, fromY);
			p1 = new Point(toX, fromY);
			p2 = new Point(toX, toY);
			list.add(tmpx1);
			list.add(p1);
			list.add(p2);
		} else {
			tmpx = (toShape.getBottomRightX() + fromShape.getX()) / 2.0F;
			p1 = new Point(fromX, fromY);
			p2 = new Point(tmpx, fromY);
			p3 = new Point(tmpx, toY - (float) Offset);
			p4 = new Point(toX, toY - (float) Offset);
			p5 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftRight(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		float tmpx1;
		if (toShape.getBottomRightX() + (float) minLen <= fromX - (float) minLen) {
			if (fromY == toY) {
				Point tmpx = new Point(fromX, fromY);
				tmpy = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
			} else {
				tmpx1 = (toShape.getBottomRightX() + fromShape.getX()) / 2.0F;
				tmpy = new Point(fromX, fromY);
				p1 = new Point(tmpx1, fromY);
				p2 = new Point(tmpx1, toY);
				p3 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
			}
		} else {
			Point p4;
			Point p5;
			if (toShape.getBottomRightY() + (float) minLen > fromShape.getY() - (float) minLen
					&& toShape.getY() < fromShape.getBottomRightY() + (float) minLen) {
				tmpx1 = 0.0F;
				float tmpy1 = 0.0F;
				if (toShape.getX() < fromShape.getX()) {
					tmpx1 = toShape.getX() - (float) Offset;
				} else {
					tmpx1 = fromX - (float) Offset;
				}

				if (fromY > toY) {
					if (toShape.getY() < fromShape.getY()) {
						tmpy1 = toShape.getY() - (float) Offset;
					} else {
						tmpy1 = fromShape.getY() - (float) Offset;
					}
				} else if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
					tmpy1 = toShape.getBottomRightY() + (float) Offset;
				} else {
					tmpy1 = fromShape.getBottomRightY() + (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(tmpx1, fromY);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(toX + (float) Offset, tmpy1);
				p5 = new Point(toX + (float) Offset, toY);
				Point p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				tmpx1 = 0.0F;
				if (toShape.getBottomRightY() <= fromShape.getY()) {
					tmpx1 = (toShape.getBottomRightY() + fromShape.getY()) / 2.0F;
				} else {
					tmpx1 = (toShape.getY() + fromShape.getBottomRightY()) / 2.0F;
				}

				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX - (float) Offset, fromY);
				p2 = new Point(fromX - (float) Offset, tmpx1);
				p3 = new Point(toX + (float) Offset, tmpx1);
				p4 = new Point(toX + (float) Offset, toY);
				p5 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftBottom(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpx;
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		float tmpx1;
		float tmpy1;
		if (toShape.getBottomRightX() + (float) minLen < fromShape.getX() - (float) minLen) {
			if (toShape.getBottomRightY() + (float) minLen <= fromY) {
				tmpx = new Point(fromX, fromY);
				tmpy = new Point(toX, fromY);
				p1 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
			} else {
				tmpx1 = (toShape.getBottomRightX() + fromShape.getX()) / 2.0F;
				tmpy1 = toShape.getBottomRightY() + (float) Offset;
				p1 = new Point(fromX, fromY);
				p2 = new Point(tmpx1, fromY);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(toX, tmpy1);
				p5 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		} else if (toShape.getBottomRightY() + (float) minLen < fromShape.getY() - (float) minLen) {
			if (toX <= fromShape.getX()) {
				tmpx = new Point(fromX, fromY);
				tmpy = new Point(toX, fromY);
				p1 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
			} else {
				tmpx1 = (toShape.getBottomRightY() + fromShape.getY()) / 2.0F;
				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX - (float) Offset, fromY);
				p2 = new Point(fromX - (float) Offset, tmpx1);
				p3 = new Point(toX, tmpx1);
				p4 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			}
		} else if (toShape.getBottomRightY() + (float) minLen <= fromY && toX < fromShape.getX() - (float) minLen) {
			tmpx = new Point(fromX, fromY);
			tmpy = new Point(toX, fromY);
			p1 = new Point(toX, toY);
			list.add(tmpx);
			list.add(tmpy);
			list.add(p1);
		} else {
			tmpx1 = 0.0F;
			tmpy1 = 0.0F;
			if (toShape.getX() < fromShape.getX()) {
				tmpx1 = toShape.getX() - (float) Offset;
			} else {
				tmpx1 = fromShape.getX() - (float) Offset;
			}

			if (toShape.getBottomRightY() < fromShape.getBottomRightY()) {
				tmpy1 = fromShape.getBottomRightY() + (float) Offset;
			} else {
				tmpy1 = toShape.getBottomRightY() + (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(tmpx1, fromY);
			p3 = new Point(tmpx1, tmpy1);
			p4 = new Point(toX, tmpy1);
			p5 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftLeft(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpx;
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		Point p6;
		float tmpx1;
		float tmpy1;
		if (toShape.getBottomRightX() + (float) minLen < fromShape.getX() - (float) minLen) {
			if (toShape.getBottomRightY() + (float) minLen >= fromY && toShape.getY() - (float) minLen <= fromY) {
				tmpx1 = (toShape.getBottomRightX() + fromShape.getX()) / 2.0F;
				tmpy1 = 0.0F;
				if (toY >= fromY) {
					tmpy1 = toShape.getY() - (float) Offset;
				} else {
					tmpy1 = toShape.getBottomRightY() + (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(tmpx1, fromY);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(toX - (float) Offset, tmpy1);
				p5 = new Point(toX - (float) Offset, toY);
				p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				tmpx = new Point(fromX, fromY);
				tmpy = new Point(toX - (float) Offset, fromY);
				p1 = new Point(toX - (float) Offset, toY);
				p2 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
			}
		} else if (toShape.getX() - (float) minLen < fromShape.getBottomRightX() + (float) minLen) {
			tmpx1 = 0.0F;
			if (toShape.getX() < fromShape.getX()) {
				tmpx1 = toShape.getX() - (float) Offset;
			} else {
				tmpx1 = fromShape.getX() - (float) Offset;
			}

			tmpy = new Point(fromX, fromY);
			p1 = new Point(tmpx1, fromY);
			p2 = new Point(tmpx1, toY);
			p3 = new Point(toX, toY);
			list.add(tmpy);
			list.add(p1);
			list.add(p2);
			list.add(p3);
		} else if (toY >= fromShape.getY() - (float) minLen && toY <= fromShape.getBottomRightY() + (float) minLen) {
			tmpx1 = (fromShape.getBottomRightX() + toShape.getX()) / 2.0F;
			tmpy1 = 0.0F;
			if (fromY > toY) {
				tmpy1 = fromShape.getY() - (float) Offset;
			} else {
				tmpy1 = fromShape.getBottomRightY() + (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(fromX - (float) Offset, fromY);
			p3 = new Point(fromX - (float) Offset, tmpy1);
			p4 = new Point(tmpx1, tmpy1);
			p5 = new Point(tmpx1, toY);
			p6 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		} else {
			tmpx = new Point(fromX, fromY);
			tmpy = new Point(fromX - (float) Offset, fromY);
			p1 = new Point(fromX - (float) Offset, toY);
			p2 = new Point(toX, toY);
			list.add(tmpx);
			list.add(tmpy);
			list.add(p1);
			list.add(p2);
		}

		return list;
	}

	public static ArrayList<Point> caculateBottomTop(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		float tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		if (toShape.getY() - (float) minLen < fromShape.getBottomRightY() + (float) minLen) {
			Point p41;
			Point p5;
			if (toShape.getBottomRightX() + (float) minLen >= fromShape.getX() - (float) minLen
					&& toShape.getX() - (float) minLen <= fromShape.getBottomRightX() + (float) minLen) {
				tmpy = 0.0F;
				float p11 = 0.0F;
				if (toX > fromX) {
					if (toShape.getBottomRightX() > fromShape.getBottomRightX()) {
						tmpy = toShape.getBottomRightX() + (float) Offset;
					} else {
						tmpy = fromShape.getBottomRightX() + (float) Offset;
					}
				} else if (toShape.getX() < fromShape.getX()) {
					tmpy = toShape.getX() - (float) Offset;
				} else {
					tmpy = fromShape.getX() - (float) Offset;
				}

				if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
					p11 = toShape.getBottomRightY() + (float) Offset;
				} else {
					p11 = fromShape.getBottomRightY() + (float) Offset;
				}

				p2 = new Point(fromX, fromY);
				p3 = new Point(fromX, p11);
				p4 = new Point(tmpy, p11);
				p41 = new Point(tmpy, toY - (float) Offset);
				p5 = new Point(toX, toY - (float) Offset);
				Point p6 = new Point(toX, toY);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p41);
				list.add(p5);
				list.add(p6);
			} else {
				tmpy = 0.0F;
				if (toShape.getBottomRightX() < fromShape.getX()) {
					tmpy = (toShape.getBottomRightX() + fromShape.getX()) / 2.0F;
				} else {
					tmpy = (toShape.getX() + fromShape.getBottomRightX()) / 2.0F;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX, fromY + (float) Offset);
				p3 = new Point(tmpy, fromY + (float) Offset);
				p4 = new Point(tmpy, toY - (float) Offset);
				p41 = new Point(toX, toY - (float) Offset);
				p5 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p41);
				list.add(p5);
			}
		} else if (toX == fromX) {
			Point tmpy1 = new Point(fromX, fromY);
			p1 = new Point(toX, toY);
			list.add(tmpy1);
			list.add(p1);
		} else {
			tmpy = (fromShape.getBottomRightY() + toShape.getY()) / 2.0F;
			p1 = new Point(fromX, fromY);
			p2 = new Point(fromX, tmpy);
			p3 = new Point(toX, tmpy);
			p4 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		}

		return list;
	}

	public static ArrayList<Point> caculateBottomRight(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		float tmpx1;
		if (toY >= fromShape.getBottomRightY() + (float) minLen) {
			if (toShape.getBottomRightX() + (float) minLen <= fromX) {
				Point tmpx = new Point(fromX, fromY);
				tmpy = new Point(fromX, toY);
				p1 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
			} else if (toShape.getY() - (float) minLen > fromShape.getBottomRightY() + (float) minLen) {
				tmpx1 = (fromShape.getBottomRightY() + toShape.getY()) / 2.0F;
				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpx1);
				p2 = new Point(toX + (float) Offset, tmpx1);
				p3 = new Point(toX + (float) Offset, toY);
				p4 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				tmpx1 = 0.0F;
				if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
					tmpx1 = toShape.getBottomRightY() + (float) Offset;
				} else {
					tmpx1 = fromShape.getBottomRightY() + (float) Offset;
				}

				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpx1);
				p2 = new Point(toX + (float) Offset, tmpx1);
				p3 = new Point(toX + (float) Offset, toY);
				p4 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			}
		} else if (toShape.getBottomRightX() + (float) minLen < fromShape.getX() - (float) minLen) {
			tmpx1 = (toShape.getBottomRightX() + fromShape.getX()) / 2.0F;
			tmpy = new Point(fromX, fromY);
			p1 = new Point(fromX, fromY + (float) Offset);
			p2 = new Point(tmpx1, fromY + (float) Offset);
			p3 = new Point(tmpx1, toY);
			p4 = new Point(toX, toY);
			list.add(tmpy);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			tmpx1 = 0.0F;
			float tmpy1 = 0.0F;
			if (toShape.getBottomRightX() > fromShape.getBottomRightX()) {
				tmpx1 = toShape.getBottomRightX() + (float) Offset;
			} else {
				tmpx1 = fromShape.getBottomRightX() + (float) Offset;
			}

			if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
				tmpy1 = toShape.getBottomRightY() + (float) Offset;
			} else {
				tmpy1 = fromShape.getBottomRightY() + (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(fromX, tmpy1);
			p3 = new Point(tmpx1, tmpy1);
			p4 = new Point(tmpx1, toY);
			Point p5 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateBottomBottom(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		Point p5;
		Point p6;
		float tmpx1;
		float tmpy1;
		if (toY + (float) minLen < fromShape.getY() - (float) minLen) {
			if (toX >= fromShape.getX() - (float) minLen && toX <= fromShape.getBottomRightX() + (float) minLen) {
				tmpx1 = (toShape.getBottomRightY() + fromShape.getY()) / 2.0F;
				tmpy1 = 0.0F;
				if (toX < fromX) {
					tmpy1 = fromShape.getX() - (float) Offset;
				} else {
					tmpy1 = fromShape.getBottomRightX() + (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX, fromY + (float) Offset);
				p3 = new Point(tmpy1, fromY + (float) Offset);
				p4 = new Point(tmpy1, tmpx1);
				p5 = new Point(toX, tmpx1);
				p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				Point tmpx = new Point(fromX, fromY);
				tmpy = new Point(fromX, fromY + (float) Offset);
				p1 = new Point(toX, fromY + (float) Offset);
				p2 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
			}
		} else if (toShape.getX() - (float) minLen <= fromX && toShape.getBottomRightX() + (float) minLen >= fromX) {
			if (toShape.getY() - (float) minLen <= fromShape.getBottomRightY() + (float) minLen) {
				tmpx1 = 0.0F;
				if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
					tmpx1 = toShape.getBottomRightY() + (float) Offset;
				} else {
					tmpx1 = fromShape.getBottomRightY() + (float) Offset;
				}

				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpx1);
				p2 = new Point(toX, tmpx1);
				p3 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else {
				tmpx1 = 0.0F;
				tmpy1 = (toShape.getY() + fromY) / 2.0F;
				if (toX < fromX) {
					tmpx1 = toShape.getBottomRightX() + (float) Offset;
				} else {
					tmpx1 = toShape.getX() - (float) Offset;
				}

				p1 = new Point(fromX, fromY);
				p2 = new Point(fromX, tmpy1);
				p3 = new Point(tmpx1, tmpy1);
				p4 = new Point(tmpx1, toY + (float) Offset);
				p5 = new Point(toX, toY + (float) Offset);
				p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}
		} else {
			tmpx1 = 0.0F;
			if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
				tmpx1 = toShape.getBottomRightY() + (float) Offset;
			} else {
				tmpx1 = fromShape.getBottomRightY() + (float) Offset;
			}

			tmpy = new Point(fromX, fromY);
			p1 = new Point(fromX, tmpx1);
			p2 = new Point(toX, tmpx1);
			p3 = new Point(toX, toY);
			list.add(tmpy);
			list.add(p1);
			list.add(p2);
			list.add(p3);
		}

		return list;
	}

	public static ArrayList<Point> caculateBottomLeft(Shape fromShape, Shape toShape, Port fromPort, Port toPort) {
		ArrayList list = new ArrayList();
		float fromX = (float) ((double) fromShape.getX() + (double) fromShape.getW() * fromPort.getX()
				+ fromPort.getHorizontalOffset());
		float fromY = (float) ((double) fromShape.getY() + (double) fromShape.getH() * fromPort.getY()
				+ fromPort.getVerticalOffset());
		float toX = (float) ((double) toShape.getX() + (double) toShape.getW() * toPort.getX()
				+ toPort.getHorizontalOffset());
		float toY = (float) ((double) toShape.getY() + (double) toShape.getH() * toPort.getY()
				+ toPort.getVerticalOffset());
		Point tmpy;
		Point p1;
		Point p2;
		Point p3;
		Point p4;
		float tmpx1;
		if (toY >= fromShape.getBottomRightY() + (float) minLen) {
			if (toShape.getX() - (float) minLen >= fromX) {
				Point tmpx = new Point(fromX, fromY);
				tmpy = new Point(fromX, toY);
				p1 = new Point(toX, toY);
				list.add(tmpx);
				list.add(tmpy);
				list.add(p1);
			} else if (toShape.getY() - (float) minLen > fromShape.getBottomRightY() + (float) minLen) {
				tmpx1 = (fromShape.getBottomRightY() + toShape.getY()) / 2.0F;
				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpx1);
				p2 = new Point(toX - (float) Offset, tmpx1);
				p3 = new Point(toX - (float) Offset, toY);
				p4 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				tmpx1 = 0.0F;
				if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
					tmpx1 = toShape.getBottomRightY() + (float) Offset;
				} else {
					tmpx1 = fromShape.getBottomRightY() + (float) Offset;
				}

				tmpy = new Point(fromX, fromY);
				p1 = new Point(fromX, tmpx1);
				p2 = new Point(toX - (float) Offset, tmpx1);
				p3 = new Point(toX - (float) Offset, toY);
				p4 = new Point(toX, toY);
				list.add(tmpy);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			}
		} else if (toShape.getX() - (float) minLen > fromShape.getBottomRightX() + (float) minLen) {
			tmpx1 = (fromShape.getBottomRightX() + toShape.getX()) / 2.0F;
			tmpy = new Point(fromX, fromY);
			p1 = new Point(fromX, fromY + (float) Offset);
			p2 = new Point(tmpx1, fromY + (float) Offset);
			p3 = new Point(tmpx1, toY);
			p4 = new Point(toX, toY);
			list.add(tmpy);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			tmpx1 = 0.0F;
			float tmpy1 = 0.0F;
			if (toShape.getX() > fromShape.getX()) {
				tmpx1 = fromShape.getX() - (float) Offset;
			} else {
				tmpx1 = toShape.getX() - (float) Offset;
			}

			if (toShape.getBottomRightY() > fromShape.getBottomRightY()) {
				tmpy1 = toShape.getBottomRightY() + (float) Offset;
			} else {
				tmpy1 = fromShape.getBottomRightY() + (float) Offset;
			}

			p1 = new Point(fromX, fromY);
			p2 = new Point(fromX, tmpy1);
			p3 = new Point(tmpx1, tmpy1);
			p4 = new Point(tmpx1, toY);
			Point p5 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	private static String getPointXml(List<Point> list) {
		StringBuffer sb = new StringBuffer();
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			Point p = (Point) i$.next();
			sb.append("\n<omgdi:waypoint x=\"" + p.getX() + "\" y=\"" + p.getY() + "\"></omgdi:waypoint>\n");
		}

		return sb.toString();
	}

	public static String calcLabelPosition(String label) {
		float labelLen = 0.0F;

		int x;
		for (x = 0; x < label.length(); ++x) {
			if (label.charAt(x) > 255) {
				labelLen += 2.0F;
			} else if (Character.isUpperCase(label.charAt(x))) {
				labelLen = (float) ((double) labelLen + 1.5D);
			} else {
				++labelLen;
			}
		}

		x = (int) (labelLen > 16.0F ? -50.0F : -(labelLen / 16.0F + 1.0F) * 100.0F / 2.0F);
		byte y = 0;
		byte width = 100;
		int height = (int) ((labelLen / 16.0F + 1.0F) * 14.0F);
		StringBuffer position = new StringBuffer();
		position.append(" <omgdc:Bounds ");
		position.append("x=\"" + x + "\" ");
		position.append("y=\"" + y + "\" ");
		position.append("width=\"" + width + "\" ");
		position.append("height=\"" + height + "\">");
		position.append("</omgdc:Bounds>");
		return position.toString();
	}

	public static String getPreVlanSumX(String contacts) {
		String[] xy = contacts.split(",");
		int pointx = 0;
		String[] arr$ = xy;
		int len$ = xy.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			String str = arr$[i$];
			if (StringUtil.isNotEmpty(str)) {
				pointx += Integer.parseInt(str);
			}
		}

		return pointx + "";
	}
}