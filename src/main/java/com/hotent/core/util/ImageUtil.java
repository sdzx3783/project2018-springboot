package com.hotent.core.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageUtil {
	public static Log log = LogFactory.getLog(ImageUtil.class);

	public static Image loadImage(byte[] imagedata) {
		Image image = Toolkit.getDefaultToolkit().createImage(imagedata);
		return image;
	}

	public static Image loadImage(String filename) {
		return Toolkit.getDefaultToolkit().getImage(filename);
	}

	public static BufferedImage loadImage(File file) {
		BufferedImage bufferedImage = null;

		try {
			bufferedImage = ImageIO.read(file);
			return bufferedImage;
		} catch (IOException arg2) {
			arg2.printStackTrace();
			throw new RuntimeException(arg2);
		}
	}

	public static ImageReader getImageReader(InputStream is, String formatName) throws IOException {
		Iterator readers = ImageIO.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) readers.next();
		ImageInputStream iis = null;

		try {
			iis = ImageIO.createImageInputStream(is);
		} catch (IOException arg5) {
			arg5.printStackTrace();
			throw new RuntimeException(arg5);
		}

		reader.setInput(iis, true);
		return reader;
	}

	public static ImageReader getImageReader(File file) {
		String formatName = getFileSuffix(file);
		Iterator readers = ImageIO.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) readers.next();
		ImageInputStream iis = null;

		try {
			iis = ImageIO.createImageInputStream(file);
		} catch (IOException arg5) {
			arg5.printStackTrace();
			throw new RuntimeException(arg5);
		}

		reader.setInput(iis, true);
		return reader;
	}

	private static String getFileSuffix(File file) {
		String fileName = file.getName();
		int index = fileName.indexOf(".");
		String formatName = fileName.substring(index + 1);
		return formatName;
	}

	public static void cutImage(int x, int y, int width, int height, File file, File output) {
		String formatName = getFileSuffix(file);
		Iterator readers = ImageIO.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) readers.next();
		ImageInputStream iis = null;

		try {
			iis = ImageIO.createImageInputStream(file);
		} catch (IOException arg13) {
			arg13.printStackTrace();
			throw new RuntimeException(arg13);
		}

		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle sourceRegion = new Rectangle(x, y, width, height);
		param.setSourceRegion(sourceRegion);

		try {
			BufferedImage e = reader.read(0, param);
			ImageIO.write(e, getFileSuffix(file), output);
		} catch (IOException arg12) {
			arg12.printStackTrace();
			throw new RuntimeException(arg12);
		}
	}

	public static InputStream cutImage(int x, int y, int width, int height, ImageReader reader) {
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle sourceRegion = new Rectangle(x, y, width, height);
		param.setSourceRegion(sourceRegion);

		try {
			ByteArrayOutputStream e = new ByteArrayOutputStream();
			BufferedImage bufferedImage = reader.read(0, param);
			ImageIO.write(bufferedImage, "png", e);
			ByteArrayInputStream bais = new ByteArrayInputStream(e.toByteArray());
			return bais;
		} catch (IOException arg9) {
			arg9.printStackTrace();
			throw new RuntimeException(arg9);
		}
	}

	public static ImageIcon getImageIcon(File file) {
		String filename = file.getAbsolutePath();
		return new ImageIcon(filename);
	}

	public static void createWaterMark(File srcFile, File waterFile, File compositeFile) throws IOException {
		Image theImg = (new ImageIcon(srcFile.getAbsolutePath())).getImage();
		Image waterImg = (new ImageIcon(waterFile.getAbsolutePath())).getImage();
		int width = theImg.getWidth((ImageObserver) null);
		int height = theImg.getHeight((ImageObserver) null);
		int w = waterImg.getWidth((ImageObserver) null);
		int h = waterImg.getHeight((ImageObserver) null);
		BufferedImage bimage = new BufferedImage(width, height, 1);
		Graphics2D g = bimage.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawImage(theImg, 0, 0, (ImageObserver) null);
		g.setComposite(AlphaComposite.getInstance(3, 0.4F));
		width -= w;
		height -= h;
		g.drawImage(waterImg, width, height, (ImageObserver) null);
		g.dispose();
		FileOutputStream fos = null;
		ImageOutputStream ios = null;
		JPEGImageWriter imageWriter = null;

		try {
			fos = new FileOutputStream(compositeFile);
			imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
			ios = ImageIO.createImageOutputStream(fos);
			imageWriter.setOutput(ios);
			IIOMetadata e = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(bimage), (ImageWriteParam) null);
			JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
			jpegParams.setCompressionMode(2);
			jpegParams.setCompressionQuality(1.0F);
			imageWriter.write(e, new IIOImage(bimage, (List) null, (IIOMetadata) null), (ImageWriteParam) null);
		} catch (Exception arg18) {
			arg18.printStackTrace();
			throw new RuntimeException(arg18);
		} finally {
			ios.close();
			fos.close();
			imageWriter.dispose();
		}

	}

	public static InputStream createRectangle(InputStream inputStream, int x, int y, int w, int h) {
		BufferedImage bimage = null;

		try {
			bimage = ImageIO.read(inputStream);
		} catch (IOException arg9) {
			arg9.printStackTrace();
			throw new RuntimeException(arg9);
		}

		Graphics2D g = bimage.createGraphics();
		g.setComposite(AlphaComposite.getInstance(3, 0.4F));
		g.setColor(Color.RED);
		g.drawRect(x, y, w, h);
		g.dispose();

		try {
			ByteArrayOutputStream e = new ByteArrayOutputStream();
			ImageIO.write(bimage, "PNG", e);
			ByteArrayInputStream bais = new ByteArrayInputStream(e.toByteArray());
			return bais;
		} catch (Exception arg8) {
			arg8.printStackTrace();
			throw new RuntimeException(arg8);
		}
	}

	public static void main(String[] args) throws Exception {
		testCompositeFile();
	}

	public static void testCompositeFile() throws IOException {
		File srcFile = new File("e:\\origin.jpg");
		File waterFile = new File("e:\\logo.jpg");
		File compositeFile = new File("e:\\temp.jpg");
		createWaterMark(srcFile, waterFile, compositeFile);
	}

	public static void tsetReaderImageIconTime() {
		String dir = "F:\\picture\\";
		long start = System.currentTimeMillis();
		File[] files = (new File(dir)).listFiles();
		File[] end = files;
		int len$ = files.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			File item = end[i$];
			ImageIcon imageIcon = getImageIcon(item);
			int width = imageIcon.getIconWidth();
			int height = imageIcon.getIconHeight();
			log.info("图片的宽度：" + width);
			log.info("图片的高度：" + height);
		}

		long arg10 = System.currentTimeMillis();
		log.info("所花时间：" + (arg10 - start) / 1000L + "秒");
	}

	public static void testCutImage() throws IOException {
		File file = new File("e:\\vehicle_examine_info.png");
		File output = new File("e:\\vehicle_examine_info.png");
		ImageReader reader = getImageReader(file);
		byte imageIndex = 0;
		int width = reader.getWidth(imageIndex) / 2;
		int height = reader.getHeight(imageIndex);
		cutImage(0, 0, width, height, file, output);
	}

	public static void testReaderImageTime() throws IOException {
		String dir = "F:\\picture\\";
		long start = System.currentTimeMillis();
		File[] files = (new File(dir)).listFiles();
		File[] end = files;
		int len$ = files.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			File item = end[i$];
			ImageReader reader = getImageReader(item);
			byte imageIndex = 0;
			int width = reader.getWidth(imageIndex);
			int height = reader.getHeight(imageIndex);
			log.info("图片的宽度：" + width);
			log.info("图片的高度：" + height);
		}

		long arg11 = System.currentTimeMillis();
		log.info("所花时间：" + (arg11 - start) / 1000L + "秒");
	}

	public static String doCompressByByte(byte[] b, String newFile, int width, int height, float quality,
			boolean percentage) {
		if (b.length > 0 && width > 0) {
			ByteArrayInputStream in_nocode = new ByteArrayInputStream(b);
			String newImage = null;

			try {
				BufferedImage e = ImageIO.read(in_nocode);
				int new_w = e.getWidth((ImageObserver) null);
				int new_h = e.getHeight((ImageObserver) null);
				if (percentage) {
					double tag = (double) e.getWidth((ImageObserver) null) / (double) width + 0.1D;
					new_w = (int) ((double) e.getWidth((ImageObserver) null) / tag);
					new_h = (int) ((double) e.getHeight((ImageObserver) null) / tag);
				} else {
					if (height <= 0) {
						return null;
					}

					int tag1 = new_w / 40;
					if (width > 0) {
						new_w = width;
					} else {
						new_w /= 40;
						if (new_w <= 0) {
							new_w = 40;
						}
					}

					if (new_h > 0) {
						new_h = height;
					} else {
						new_h = height / tag1 * 30;
						if (new_h <= 0) {
							new_h = 30;
						}
					}
				}

				BufferedImage tag2 = new BufferedImage(new_w, new_h, 1);
				tag2.getGraphics().drawImage(e, 0, 0, new_w, new_h, (ImageObserver) null);
				newImage = newFile;
				FileOutputStream out = new FileOutputStream(newFile);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag2);
				jep.setQuality(quality, true);
				encoder.encode(tag2, jep);
				out.close();
				e.flush();
			} catch (FileNotFoundException arg14) {
				arg14.printStackTrace();
			} catch (IOException arg15) {
				arg15.printStackTrace();
			}

			return newImage;
		} else {
			return null;
		}
	}

	public static String doCompressByPath(String oldFile, String newFile, int width, int height, float quality,
			boolean percentage) {
		if (oldFile != null && width > 0 && height > 0) {
			String newImage = null;

			try {
				File e = new File(oldFile);
				if (!e.exists()) {
					return null;
				}

				BufferedImage srcFile = ImageIO.read(e);
				int new_w = width;
				int new_h = height;
				if (percentage) {
					double tag = (double) srcFile.getWidth((ImageObserver) null) / (double) width + 0.1D;
					double encoder = (double) srcFile.getHeight((ImageObserver) null) / (double) height + 0.1D;
					double rate = tag > encoder ? tag : encoder;
					new_w = (int) ((double) srcFile.getWidth((ImageObserver) null) / rate);
					new_h = (int) ((double) srcFile.getHeight((ImageObserver) null) / rate);
				}

				BufferedImage tag1 = new BufferedImage(new_w, new_h, 1);
				tag1.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, (ImageObserver) null);
				newImage = newFile;
				FileOutputStream out = new FileOutputStream(newFile);
				JPEGImageEncoder encoder1 = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag1);
				jep.setQuality(quality, true);
				encoder1.encode(tag1, jep);
				out.close();
				srcFile.flush();
			} catch (FileNotFoundException arg16) {
				arg16.printStackTrace();
			} catch (IOException arg17) {
				arg17.printStackTrace();
			}

			return newImage;
		} else {
			return null;
		}
	}

	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;

		try {
			file = new File(outputFile);
			FileOutputStream e1 = new FileOutputStream(file);
			stream = new BufferedOutputStream(e1);
			stream.write(b);
		} catch (Exception arg12) {
			arg12.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException arg11) {
					arg11.printStackTrace();
				}
			}

		}

		return file;
	}
}