package com.hotent.core.util;

import com.hotent.core.util.FileUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import org.springframework.web.multipart.MultipartFile;

public class ZipUtil {
	public static void zip(String path) {
		zip(path, Boolean.valueOf(true));
	}

	public static void zip(String path, Boolean isDelete) {
		ZipFile zipFile = null;

		try {
			ZipParameters e = new ZipParameters();
			e.setCompressionMethod(8);
			e.setCompressionLevel(5);
			File file = new File(path);
			if (file.isDirectory()) {
				zipFile = new ZipFile(new File(path + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFolder(path, e);
			} else {
				zipFile = new ZipFile(new File(path.split(".")[0] + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFile(file, e);
			}

			if (isDelete.booleanValue()) {
				FileUtil.deleteDir(file);
			}
		} catch (ZipException arg4) {
			arg4.printStackTrace();
		}

	}

	public static void zipSetPass(String path, Boolean isDelete, String password) {
		ZipFile zipFile = null;

		try {
			ZipParameters e = new ZipParameters();
			e.setCompressionMethod(8);
			e.setCompressionLevel(5);
			e.setEncryptFiles(true);
			e.setEncryptionMethod(0);
			e.setPassword(password);
			File file = new File(path);
			if (file.isDirectory()) {
				zipFile = new ZipFile(new File(path + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFolder(path, e);
			} else {
				zipFile = new ZipFile(new File(path.split(".")[0] + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFile(file, e);
			}

			if (isDelete.booleanValue()) {
				FileUtil.deleteDir(new File(path));
			}
		} catch (ZipException arg5) {
			arg5.printStackTrace();
		}

	}

	public static void unZip(String filePath, String toPath, String password) {
		try {
			unZipFile(new ZipFile(filePath), toPath, password);
		} catch (ZipException arg3) {
			arg3.printStackTrace();
		}

	}

	public static String unZipFile(MultipartFile multipartFile, String toPath) throws Exception {
		String originalFilename = multipartFile.getOriginalFilename();
		String destPath = toPath + originalFilename;
		createFilePath(destPath, originalFilename);
		File file = new File(destPath);
		if (file.exists()) {
			file.delete();
		}

		multipartFile.transferTo(file);
		ZipFile zipFile = new ZipFile(file);
		zipFile.setFileNameCharset("GBK");
		if (zipFile.isEncrypted()) {
			zipFile.setPassword("");
		}

		if (!zipFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法,可能被损坏.");
		} else {
			String fileDir = "";
			zipFile.extractAll(toPath);
			List fileHeaderList = zipFile.getFileHeaders();
			Iterator i$ = fileHeaderList.iterator();

			while (i$.hasNext()) {
				FileHeader fileHeader = (FileHeader) i$.next();
				String dirName = fileHeader.getFileName();
				if (fileHeader.isDirectory()) {
					if (dirName.endsWith("\\")) {
						fileDir = dirName.substring(0, dirName.lastIndexOf("\\"));
					} else if (dirName.endsWith("/")) {
						fileDir = dirName.substring(0, dirName.lastIndexOf("/"));
					} else {
						fileDir = dirName.substring(0, dirName.lastIndexOf(File.separator));
					}
				}
			}

			FileUtil.deleteDir(file);
			return fileDir;
		}
	}

	public static void unZipFile(MultipartFile multipartFile, String toPath, String password) {
		String originalFilename = multipartFile.getOriginalFilename();
		String destPath = toPath + originalFilename;

		try {
			createFilePath(destPath, originalFilename);
			File e = new File(destPath);
			if (e.exists()) {
				e.delete();
			}

			multipartFile.transferTo(e);
			unZipFile(new ZipFile(e), toPath, password);
		} catch (Exception arg5) {
			arg5.printStackTrace();
		}

	}

	public static void unZipFile(ZipFile zipFile, String toPath, String password) {
		try {
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}

			List e = zipFile.getFileHeaders();
			Iterator i$ = e.iterator();

			while (i$.hasNext()) {
				Object o = i$.next();
				FileHeader fileHeader = (FileHeader) o;
				zipFile.extractFile(fileHeader, toPath);
			}
		} catch (ZipException arg6) {
			arg6.printStackTrace();
		}

	}

	public static String createFilePath(String tempPath, String fileName) {
		File file = new File(tempPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		return file.getPath() + File.separator + fileName;
	}
}