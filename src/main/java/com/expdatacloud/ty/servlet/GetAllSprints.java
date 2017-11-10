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

public class GetAllSprints extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetAllSprints.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	try {
			RestUrlSend rs=new RestUrlSend();
			JSONArray allSprints= new JSONArray();
			JSONArray boards= new JSONArray();
			JSONArray boardSprints= new JSONArray();
			JSONObject jsonBack=null;
			ArrayList<Integer> boardIds= new ArrayList<Integer>();
			//获取所有面板
			jsonBack=rs.getSend("/rest/agile/1.0/board", req, resp);
			boards=jsonBack.getJSONArray("values");
			for(int i=0;i<boards.length();i++){
				JSONObject board = (JSONObject) boards.get(i);
				//获取每个面板下的所有sprint
				jsonBack=rs.getSend("/rest/agile/1.0/board/"+board.get("id")+"/sprint", req, resp);
				if(jsonBack==null){
					continue;
				}else{
					boardSprints=jsonBack.getJSONArray("values");
					if(boardSprints.length()==0){
						continue;
					}else{
						for(int j=0;j<boardSprints.length();j++){
							JSONObject sprint=(JSONObject)boardSprints.get(j);
							if("closed".equals(sprint.get("state"))){
								continue;
							}else{
								allSprints.put(sprint);
							}
						}
					}
					
				}
				
			}
	         resp.getWriter().write(allSprints.toString());
	        	
		//	resp.getWriter().write("<script>console.log("+allSprints.toString()+")</script>");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}