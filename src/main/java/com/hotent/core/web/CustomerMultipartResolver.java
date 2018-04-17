package com.hotent.core.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport.MultipartParsingResult;

public class CustomerMultipartResolver extends CommonsMultipartResolver {
	public static String SizeLimitExceededException = "SizeLimitExceededException";

	protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		String encoding = this.determineEncoding(request);
		FileUpload fileUpload = this.prepareFileUpload(encoding);

		try {
			List ex = ((ServletFileUpload) fileUpload).parseRequest(request);
			return this.parseFileItems(ex, encoding);
		} catch (SizeLimitExceededException arg4) {
			request.setAttribute(SizeLimitExceededException, "true");
			return this.parseFileItems(new ArrayList(), encoding);
		} catch (FileUploadException arg5) {
			throw new MultipartException("Could not parse multipart servlet request", arg5);
		}
	}
}