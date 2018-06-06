package com.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CacheUtils {

	public static boolean checkHeaderCache(long adddays, long modelLastModifiedDate, HttpServletRequest request, HttpServletResponse response) {
		// com.jdon.jivejdon.presentation.filter.ExpiresFilter
		request.setAttribute("myExpire", adddays);

		// convert seconds to ms.
		long adddaysM = adddays * 1000;
		long header = request.getDateHeader("If-Modified-Since");
		long now = System.currentTimeMillis();
		if (header > 0 && adddaysM > 0) {
			if (modelLastModifiedDate > header) {
				// adddays = 0; // reset
				response.setStatus(HttpServletResponse.SC_OK);
				return true;
			}
			if (header + adddaysM > now) {
				// during the period happend modified
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return false;
			}
		}

		// if over expire data, see the Etags;
		// ETags if ETags no any modified
		String previousToken = request.getHeader("If-None-Match");
		if (previousToken != null && previousToken.equals(Long.toString(modelLastModifiedDate))) {
			// not modified
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		// if th model has modified , setup the new modified date
		response.setHeader("ETag", Long.toString(modelLastModifiedDate));
		setRespHeaderCache(adddays, request, response);
		return true;

	}
	
	public static boolean setRespHeaderCache(long adddays, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("myExpire", adddays);

		long adddaysM = adddays * 1000;
		String maxAgeDirective = "max-age=" + adddays + ", must-revalidate";
		response.setHeader("Cache-Control", maxAgeDirective);
		response.setStatus(HttpServletResponse.SC_OK);
		response.addDateHeader("Last-Modified", System.currentTimeMillis());
		response.addDateHeader("Expires", System.currentTimeMillis() + adddaysM);
		return true;
	}
}
