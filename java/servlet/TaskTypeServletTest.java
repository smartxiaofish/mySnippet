/**
 * KEEP MOVING, FOLLOW YOUR HEART.
 */

package com.sundray.wnsBpm.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author yuxinghong
 *
 */
public class TaskTypeServletTest {
	
	private HttpServletRequest req;		// mocked httpRequest
	private HttpServletResponse resp;	// mocked httpResponse
	private HttpSession httpSession;	// mocked httpSession
	private StringWriter stringWriter;	// httpResponse output
	
	
	@Before
	public void setup() throws IOException{
		req = mock(HttpServletRequest.class);
		resp = mock(HttpServletResponse.class);
		httpSession = mock(HttpSession.class);
		when(req.getSession()).thenReturn(httpSession);
		resetHttpResponseBuffer();
		
	}
	
	
	@After
	public void teardown(){
		
	}
	
	
	@Test
	public void test() throws ServletException, IOException{
		// mock httpRequest parameters
		when(req.getParameter("queryCondition")).thenReturn("{opr: 'list'}");
		// mock httpSession parameters
		when(httpSession.getAttribute("userId")).thenReturn("11111"); 
		
		// execute servlet post
		new TaskTypeServlet().doPost(req, resp);
		
		// verify in http response
		assertTrue(stringWriter.toString().contains("success"));
	}
	
	
	/**
	 * 重置HttpResponseWriter，一个Test中，包含多个doPost或doGet等，需要执行此方法，
	 * 以免每次响应的数据重复输出到stringWriter中。
	 * @throws IOException
	 */
	private void resetHttpResponseBuffer() throws IOException{
		stringWriter = new StringWriter();
		when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
	}

}
