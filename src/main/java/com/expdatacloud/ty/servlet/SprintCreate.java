package com.expdatacloud.ty.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.expdatacloud.ty.util.RestUrlSend;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SprintCreate extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(SprintCreate.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	doPost(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	String originBoardName=req.getParameter("originBoardName");
    	String originBoardId=req.getParameter("originBoardId");
    	RestUrlSend rs=new RestUrlSend();
    	JSONObject jsonBack=null;
    	jsonBack=rs.getSend("/rest/agile/1.0/board/"+originBoardId+"/sprint", req, resp);
    	JSONObject jsonObject=new JSONObject();
    	System.out.println(jsonBack.toString());
    	try {
    		
    		jsonObject.put("name",originBoardName+" Sprint "+(jsonBack.getJSONArray("values").length()+1));  		
    		jsonObject.put("originBoardId",originBoardId);
    		rs.postSend("/rest/agile/1.0/sprint", jsonObject, req, resp);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}