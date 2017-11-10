package com.expdatacloud.ty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.issue.fields.SearchableField;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

public class GetIssueField extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetIssueField.class);

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		if (ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()) {

			FieldManager fm = ComponentAccessor.getFieldManager(); // 获取fieldmanager
			Set<SearchableField> set = fm.getAllSearchableFields(); // 通过fieldmanager获取所有字段
			Iterator<SearchableField> it = set.iterator(); // 遍历所有字段
			SearchableField sf = null; // 字段类pojo
			JSONArray jsonarray = new JSONArray();
			while (it.hasNext()) {
				sf = it.next();
				// list.add(sf.getName()); // 将遍历的字段pojo放入list
				JSONObject jsonobject = new JSONObject();
				try {
					jsonobject.put(sf.getName(), sf.getName());
					jsonarray.put(jsonobject);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			writer.println("<script>console.log(" + jsonarray.toString() + ")</script>");
		}

	}
}