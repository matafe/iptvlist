package com.matafe.iptvlist.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.m3u.M3UItemStore;
import com.matafe.iptvlist.sec.SecurityAuthenticator;
import com.matafe.iptvlist.sec.ejb.CleanupGuardTimerBean;

/**
 * Upload Servlet.
 * 
 * @author matafe@gmail.com
 */
@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 5, // 5 MB
	maxRequestSize = 1024 * 1024 * 10) // 10MB
public class UploadServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    
    private static final long serialVersionUID = 1L;

    @Inject
    private M3UItemStore store;

    @Inject
    private SecurityAuthenticator securityAuthenticator;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	{
	    // this should be move to a filter...
	    // or this should be done as a resources.
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    if (username == null || username.isEmpty()) {
		request.setAttribute("errorMessage", "The username is required!");
		request.getRequestDispatcher("/upload.jsp").forward(request, response);
	    }

	    this.securityAuthenticator.authenticate(username, password);
	}

	String fileName = null;
	InputStream is = null;

	for (Part part : request.getParts()) {
	    fileName = getFileName(part);
	    if (!fileName.isEmpty()) {
		// String result = new BufferedReader(new
		// InputStreamReader(part.getInputStream())).lines()
		// .collect(Collectors.joining("\n"));
		is = part.getInputStream();
		//System.out.println(is);
	    }
	}

	if (!fileName.isEmpty()) {
	    try {
		store.load(fileName, is);
		request.setAttribute("message", fileName + " uploaded successfully!");
		request.getRequestDispatcher("/upload.jsp").forward(request, response);
	    } catch (Exception e) {
		request.setAttribute("errorMessage", e.getMessage());
	    }
	} else {
	    request.setAttribute("errorMessage", "No file uploaded. Please select a valid list file");
	    request.getRequestDispatcher("/upload.jsp").forward(request, response);
	}

    }

    private String getFileName(Part part) {
	String contentDisp = part.getHeader("content-disposition");
	System.out.println("content-disposition header= " + contentDisp);	
	
	logger.info("> content-disposition header= " + contentDisp);

	String[] tokens = contentDisp.split(";");
	for (String token : tokens) {
	    if (token.trim().startsWith("filename")) {
		return token.substring(token.indexOf("=") + 2, token.length() - 1);
	    }
	}
	return "";
    }
}
