/**
 * 
 */
package com.sundray.wnsBpm.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sundray.wnsBpm.dao.QuantifyTaskTypeDao;
import com.sundray.wnsBpm.dao.QuantifyTaskTypeDaoImpl;
import com.sundray.wnsBpm.model.task.QuantifyTaskType;
import com.sundray.wnsBpm.servlet.WnsServletUtil;

/**
 * 任务类型管理servlet，json数据交互
 * <table border=1 style="border-collapse: collapse">
 * 	<tr><th>request</th><th>response</th></tr>
 * 	<tr><td>{opr: "list"}</td><td>{success: true, data: [{id: x, taskType: "yy", amountTitle: "zz"}, ...]}</td></tr>
 * 	<tr><td>{opr: "add", taskType: "xx", amountTitle: "yy"}</td><td>{success: true}</td></tr>
 * 	<tr><td>{opr: "update", id: xx, taskType: "xx", amountTitle: "yy"}</td><td>{success: true}</td></tr>
 * 	<tr><td>{opr: "delete", id: xx}</td><td>{success: true}</td></tr>
 * </table>
 * 
 * @author yuxinghong
 *
 */
public class TaskTypeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		WnsServletUtil.setupJsonReqResp(req, resp);
		
		JSONObject resultJson = new JSONObject();
		PrintWriter out = resp.getWriter();
		
		JSONObject queryCondition = new JSONObject(req.getParameter("queryCondition"));
		String opr = queryCondition.getString("opr");
		
		
		if("list".equals(opr)){
			JSONArray taskTypeJsonArr = getAllTaskTypes();
			resultJson.put("success", true);
			resultJson.put("data", taskTypeJsonArr);
			
		} else if ("add".equals(opr)){
			String taskType = queryCondition.getString("taskType");
			String amountTitle = queryCondition.getString("amountTitle");
			addQuantifyTaskType(taskType, amountTitle);
			resultJson.put("success", true);

		} else if ("delete".equals(opr)){
			Long id = queryCondition.getLong("id");
			deleteQuantifyTaskType(id);
			resultJson.put("success", true);
			
		} else if ("update".equals(opr)){
			Long id = queryCondition.getLong("id");
			String taskType = queryCondition.getString("taskType");
			String amountTitle = queryCondition.getString("amountTitle");
			updateQuantifyTaskType(id, taskType, amountTitle);
			resultJson.put("success", true);
			
		} else {
			resultJson.put("success", false);
			resultJson.put("reason", "不支持的操作类型:[" + opr + "]!");
		}
		
		out.write(resultJson.toString());
	}

	
	private JSONArray getAllTaskTypes(){
		JSONArray taskTypeJsonArr = new JSONArray();
		QuantifyTaskTypeDao taskTypeDao = new QuantifyTaskTypeDaoImpl();
		try{
			for(QuantifyTaskType qtt : taskTypeDao.listAllQuantifyTaskType()){
				JSONObject qttJson = new JSONObject();
				qttJson.put("id", qtt.getId());
				qttJson.put("taskType", qtt.getTaskType());
				qttJson.put("amountTitle", qtt.getAmountTitle());
				taskTypeJsonArr.put(qttJson);
			}
		} finally {
			taskTypeDao.close();
		}
		
		return taskTypeJsonArr;
	}

	
	private void addQuantifyTaskType(String taskType, String amountTitle){
		QuantifyTaskTypeDao taskTypeDao = new QuantifyTaskTypeDaoImpl();
		try{
			taskTypeDao.addQuantifyTaskType(taskType, amountTitle);
		} finally {
			taskTypeDao.close();
		}
	}
	
	
	private void deleteQuantifyTaskType(Long id){
		QuantifyTaskTypeDao taskTypeDao = new QuantifyTaskTypeDaoImpl();
		try{
			taskTypeDao.deleteQuantifyTaskType(id);
		} finally {
			taskTypeDao.close();
		}
	}
	
	
	private void updateQuantifyTaskType(Long id, String taskType, String amountTitle){
		QuantifyTaskTypeDao taskTypeDao = new QuantifyTaskTypeDaoImpl();
		try{
			taskTypeDao.updateQuantifyTaskType(id, taskType, amountTitle);
		} finally {
			taskTypeDao.close();
		}
	}
}
