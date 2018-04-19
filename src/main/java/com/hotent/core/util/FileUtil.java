package com.hotent.core.util;

import com.hotent.core.encrypt.Base64;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUtil {
	public static void writeFile(String fileName, String content) {
		writeFile(fileName, content, "utf-8");
	}

	public static void writeFile(String fileName, String content, String charset) {
		try {
			createFolder(fileName, true);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), charset));
			out.write(content);
			out.close();
		} catch (IOException arg4) {
			arg4.printStackTrace();
		}

	}

	public static void writeFile(String fileName, InputStream is) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] bs = new byte[512];
		boolean n = false;

		int n1;
		while ((n1 = is.read(bs)) != -1) {
			fos.write(bs, 0, n1);
		}

		is.close();
		fos.close();
	}

	public static String readFile(String fileName) {
		try {
			File e = new File(fileName);
			String charset = getCharset(e);
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));

			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str + "\r\n");
			}

			in.close();
			return sb.toString();
		} catch (IOException arg5) {
			arg5.printStackTrace();
			return "";
		}
	}

	public static boolean isExistFile(String dir) {
		boolean isExist = false;
		File fileDir = new File(dir);
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			if (files != null && files.length != 0) {
				isExist = true;
			}
		}

		return isExist;
	}

	public static String getCharset(File file) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];

		try {
			boolean e = false;
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset;
			}

			if (first3Bytes[0] == -1 && first3Bytes[1] == -2) {
				charset = "UTF-16LE";
				e = true;
			} else if (first3Bytes[0] == -2 && first3Bytes[1] == -1) {
				charset = "UTF-16BE";
				e = true;
			} else if (first3Bytes[0] == -17 && first3Bytes[1] == -69 && first3Bytes[2] == -65) {
				charset = "UTF-8";
				e = true;
			}

			bis.reset();
			if (!e) {
				int loc = 0;

				label74 : do {
					do {
						if ((read = bis.read()) == -1) {
							break label74;
						}

						++loc;
						if (read >= 240 || 128 <= read && read <= 191) {
							break label74;
						}

						if (192 <= read && read <= 223) {
							read = bis.read();
							continue label74;
						}
					} while (224 > read || read > 239);

					read = bis.read();
					if (128 <= read && read <= 191) {
						read = bis.read();
						if (128 <= read && read <= 191) {
							charset = "UTF-8";
						}
					}
					break;
				} while (128 <= read && read <= 191);
			}

			bis.close();
		} catch (Exception arg6) {
			arg6.printStackTrace();
		}

		return charset;
	}

	public static byte[] readByte(InputStream is) {
		try {
			byte[] e = new byte[is.available()];
			is.read(e);
			return e;
		} catch (Exception arg1) {
			arg1.printStackTrace();
			return null;
		}
	}

	public static byte[] readByte(String fileName) {
		try {
			FileInputStream e = new FileInputStream(fileName);
			byte[] r = new byte[e.available()];
			e.read(r);
			e.close();
			return r;
		} catch (Exception arg2) {
			arg2.printStackTrace();
			return null;
		}
	}

	public static boolean writeByte(String fileName, byte[] b) {
		try {
			BufferedOutputStream e = new BufferedOutputStream(new FileOutputStream(fileName));
			e.write(b);
			e.close();
			return true;
		} catch (Exception arg2) {
			arg2.printStackTrace();
			return false;
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();

			for (int i = 0; i < children.length; ++i) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}

	public static void serializeToFile(Object obj, String fileName) {
		try {
			ObjectOutputStream e = new ObjectOutputStream(new FileOutputStream(fileName));
			e.writeObject(obj);
			e.close();
		} catch (IOException arg2) {
			arg2.printStackTrace();
		}

	}

	public static Object deserializeFromFile(String fileName) {
		try {
			File e = new File(fileName);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(e));
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception arg3) {
			arg3.printStackTrace();
			return null;
		}
	}

	public static String inputStream2String(InputStream input, String charset) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(input, charset));
		StringBuffer buffer = new StringBuffer();
		String line = "";

		while ((line = in.readLine()) != null) {
			buffer.append(line + "\n");
		}

		return buffer.toString();
	}

	public static String inputStream2String(InputStream input) throws IOException {
		return inputStream2String(input, "utf-8");
	}

	public static File[] getFiles(String path) {
		File file = new File(path);
		return file.listFiles();
	}

	public static void createFolderFile(String path) {
		createFolder(path, true);
	}

	public static void createFolder(String path, boolean isFile) {
		if (isFile) {
			path = path.substring(0, path.lastIndexOf(File.separator));
		}

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	public static void createFolder(String dirstr, String name) {
		dirstr = StringUtil.trimSufffix(dirstr, File.separator) + File.separator + name;
		File dir = new File(dirstr);
		dir.mkdir();
	}

	public static void renameFolder(String path, String newName) {
		File file = new File(path);
		if (file.exists()) {
			file.renameTo(new File(newName));
		}

	}

	public static ArrayList<File> getDiretoryOnly(File dir) {
      ArrayList dirs = new ArrayList();
      if(dir != null && dir.exists() && dir.isDirectory()) {
         File[] files = dir.listFiles(new FileFilter(){
        	 @Override
        	public boolean accept(File pathname) {
        		return pathname.isDirectory();
        	}
         });

         for(int i = 0; i < files.length; ++i) {
            dirs.add(files[i]);
         }
      }

      return dirs;
   }

	public ArrayList<File> getFileOnly(File dir) {
      ArrayList dirs = new ArrayList();
      File[] files = dir.listFiles(new FileFilter(){
     	 @Override
     	public boolean accept(File pathname) {
     		return pathname.isFile();
     	}
      });

      for(int i = 0; i < files.length; ++i) {
         dirs.add(files[i]);
      }

      return dirs;
   }

	public static boolean deleteFile(String path) {
		File file = new File(path);
		return file.delete();
	}

	public static boolean copyFile(String from, String to) {
		File fromFile = new File(from);
		File toFile = new File(to);
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(fromFile);
			fos = new FileOutputStream(toFile);
			byte[] buf = new byte[4096];

			int e;
			while ((e = fis.read(buf)) != -1) {
				fos.write(buf, 0, e);
			}

			fos.flush();
			fos.close();
			fis.close();
			return true;
		} catch (IOException arg7) {
			arg7.printStackTrace();
			return false;
		}
	}

	public static void backupFile(String filePath) {
		String backupName = filePath + ".bak";
		File file = new File(backupName);
		if (file.exists()) {
			file.delete();
		}

		copyFile(filePath, backupName);
	}

	public static String getFileExt(File file) {
		return file.isFile() ? getFileExt(file.getName()) : "";
	}

	public static String getFileExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return pos > -1 ? fileName.substring(pos + 1).toLowerCase() : "";
	}

	public static void copyDir(String fromDir, String toDir) throws IOException {
		(new File(toDir)).mkdirs();
		File[] file = (new File(fromDir)).listFiles();

		for (int i = 0; i < file.length; ++i) {
			if (file[i].isFile()) {
				String fromFile = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();
				copyFile(fromFile, toFile);
			}

			if (file[i].isDirectory()) {
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
			}
		}

	}

	private static void copyDirectiory(String fromDir, String toDir) throws IOException {
		(new File(toDir)).mkdirs();
		File[] file = (new File(fromDir)).listFiles();

		for (int i = 0; i < file.length; ++i) {
			if (file[i].isFile()) {
				String fromName = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();
				copyFile(fromName, toFile);
			}

			if (file[i].isDirectory()) {
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
			}
		}

	}

	public static String getFileSize(File file) throws IOException {
		if (file.isFile()) {
			FileInputStream fis = new FileInputStream(file);
			int size = fis.available();
			fis.close();
			return getSize((double) size);
		} else {
			return "";
		}
	}

	public static String getSize(double size) {
		DecimalFormat df = new DecimalFormat("0.00");
		double ss;
		if (size > 1048576.0D) {
			ss = size / 1048576.0D;
			return df.format(ss) + " M";
		} else if (size > 1024.0D) {
			ss = size / 1024.0D;
			return df.format(ss) + " KB";
		} else {
			return size + " bytes";
		}
	}

	public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fullPath,
			String fileName) throws IOException {
		ServletOutputStream outp = response.getOutputStream();
		File file = new File(fullPath);
		if (file.exists()) {
			response.setContentType("APPLICATION/OCTET-STREAM");
			String agent = request.getHeader("USER-AGENT");
			String filedisplay;
			if (agent.toUpperCase().indexOf("MSIE") > 0) {
				filedisplay = URLEncoder.encode(fileName, "UTF-8");
			} else {
				filedisplay = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}

			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			FileInputStream in = null;

			try {
				outp = response.getOutputStream();
				in = new FileInputStream(fullPath);
				byte[] e = new byte[1024];
				boolean i = false;

				int i1;
				while ((i1 = in.read(e)) > 0) {
					outp.write(e, 0, i1);
				}

				outp.flush();
			} catch (Exception arg13) {
				arg13.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
					in = null;
				}

				if (outp != null) {
					outp.close();
					outp = null;
					response.flushBuffer();
				}

			}
		} else {
			outp.write("文件不存在!".getBytes("utf-8"));
		}

	}

	public static void downLoad(HttpServletRequest request, HttpServletResponse response, String content,
			String fileName) throws IOException {
		response.setContentType("APPLICATION/OCTET-STREAM");
		String agent = request.getHeader("USER-AGENT");
		String filedisplay;
		if (agent.toUpperCase().indexOf("MSIE") > 0) {
			filedisplay = URLEncoder.encode(fileName, "UTF-8");
		} else {
			filedisplay = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}

		response.setHeader("Content-Disposition", "attachment;filename=" + filedisplay);
		PrintWriter out = response.getWriter();
		out.write(content);
		out.flush();
		out.close();
	}

	public static String getParentDir(String baseDir, String currentFile) {
		File f = new File(currentFile);
		String parentPath = f.getParent();
		String path = parentPath.replace(baseDir, "");
		return path.replace(File.separator, "/");
	}

	public static String getClassesPath() {
		String path = StringUtil.trimSufffix(AppUtil.getRealPath("/"), File.separator) + File.separator + "WEB-INF"
				+ File.separator + "classes" + File.separator;
		return path;
	}

	public static String getRootPath() {
		String rootPath = StringUtil.trimSufffix(AppUtil.getRealPath("/"), File.separator) + File.separator;
		return rootPath;
	}

	public static String readFromProperties(String fileName, String key) {
		String value = "";
		BufferedInputStream stream = null;

		try {
			stream = new BufferedInputStream(new FileInputStream(fileName));
			Properties e = new Properties();
			e.load(stream);
			value = e.getProperty(key);
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

		return value;
	}

	public static boolean saveProperties(String fileName, String key, String value) {
		StringBuffer sb = new StringBuffer();
		boolean isFound = false;
		BufferedReader in = null;

		boolean arg6;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));

			String ex;
			while ((ex = in.readLine()) != null) {
				if (ex.startsWith(key)) {
					sb.append(key + "=" + value + "\r\n");
					isFound = true;
				} else {
					sb.append(ex + "\r\n");
				}
			}

			if (!isFound) {
				sb.append(key + "=" + value + "\r\n");
			}

			writeFile(fileName, sb.toString(), "utf-8");
			arg6 = true;
			return arg6;
		} catch (Exception arg16) {
			arg16.printStackTrace();
			arg6 = false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException arg15) {
					arg15.printStackTrace();
				}
			}

		}

		return arg6;
	}

	public static boolean delProperties(String fileName, String key) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;

		boolean arg4;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));

			String ex;
			while ((ex = in.readLine()) != null) {
				if (!ex.startsWith(key)) {
					sb.append(ex + "\r\n");
				}
			}

			writeFile(fileName, sb.toString(), "utf-8");
			arg4 = true;
			return arg4;
		} catch (Exception arg14) {
			arg14.printStackTrace();
			arg4 = false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException arg13) {
					arg13.printStackTrace();
				}
			}

		}

		return arg4;
	}

	public static List<Class<?>> getAllClassesByInterface(Class<?> interfaceClass, boolean samePackage)
			throws IOException, ClassNotFoundException, IllegalStateException {
		if (!interfaceClass.isInterface()) {
			throw new IllegalStateException("Class not a interface.");
		} else {
			ClassLoader $loader = interfaceClass.getClassLoader();
			String packageName = samePackage ? interfaceClass.getPackage().getName() : "/";
			return findClasses(interfaceClass, $loader, packageName);
		}
	}

	private static List<Class<?>> findClasses(Class<?> interfaceClass, ClassLoader loader, String packageName)
			throws IOException, ClassNotFoundException {
		ArrayList allClasses = new ArrayList();
		String packagePath = packageName.replace(".", "/");
		if (!packagePath.equals("/")) {
			Enumeration path = loader.getResources(packagePath);

			while (path.hasMoreElements()) {
				URL $url = (URL) path.nextElement();
				allClasses.addAll(findResources(interfaceClass, new File($url.getFile()), packageName));
			}
		} else {
			String path1 = loader.getResource("").getPath();
			allClasses.addAll(findResources(interfaceClass, new File(path1), packageName));
		}

		return allClasses;
	}

	private static List<Class<?>> findResources(Class<?> interfaceClass, File directory, String packageName)
			throws ClassNotFoundException {
		ArrayList $results = new ArrayList();
		if (!directory.exists()) {
			return Collections.EMPTY_LIST;
		} else {
			File[] files = directory.listFiles();
			File[] arr$ = files;
			int len$ = files.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				File file = arr$[i$];
				if (file.isDirectory()) {
					if (!file.getName().contains(".")) {
						if (!packageName.equals("/")) {
							$results.addAll(findResources(interfaceClass, file, packageName + "." + file.getName()));
						} else {
							$results.addAll(findResources(interfaceClass, file, file.getName()));
						}
					}
				} else if (file.getName().endsWith(".class")) {
					Class clazz = null;
					if (!packageName.equals("/")) {
						clazz = Class
								.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
					} else {
						clazz = Class.forName(file.getName().substring(0, file.getName().length() - 6));
					}

					if (interfaceClass.isAssignableFrom(clazz) && !interfaceClass.equals(clazz)) {
						$results.add(clazz);
					}
				}
			}

			return $results;
		}
	}

	public static Object cloneObject(Object obj) throws Exception {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(obj);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		return in.readObject();
	}

	public static void downLoadFileByByte(HttpServletRequest request, HttpServletResponse response, byte[] b,
			String fileName) throws IOException {
		ServletOutputStream outp = response.getOutputStream();
		if (b.length > 0) {
			response.setContentType("APPLICATION/OCTET-STREAM");
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				String enableFileName = "=?UTF-8?B?" + new String(Base64.getBase64(fileName)) + "?=";
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
			} else {
				String filedisplay = URLEncoder.encode(fileName, "utf-8");
				response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			}

			outp.write(b);
		} else {
			outp.write("文件不存在!".getBytes("utf-8"));
		}

		if (outp != null) {
			outp.close();
			outp = null;
			response.flushBuffer();
		}

	}
}