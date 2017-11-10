package com.expdatacloud.ty.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.ofbiz.core.entity.GenericEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.JiraServiceContext;
import com.atlassian.jira.bc.JiraServiceContextImpl;
import com.atlassian.jira.bc.filter.ProjectSearchRequestService;
import com.atlassian.jira.bc.filter.SearchRequestService;
import com.atlassian.jira.bc.issue.link.IssueLinkService;
import com.atlassian.jira.bc.issue.link.IssueLinkService.AddIssueLinkValidationResult;
import com.atlassian.jira.bc.issue.link.RemoteIssueLinkService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.bc.issue.search.SearchService.ParseResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.permission.GlobalPermissionType;
import com.atlassian.jira.permission.PermissionContext;
import com.atlassian.jira.permission.ProjectPermission;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;
import com.atlassian.jira.sharing.SharePermissionImpl;
import com.atlassian.jira.sharing.type.ShareType;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.query.Query;
import com.expdatacloud.ty.util.RestUrlSend;

/**
 * 获取标签
 * 
 * @ @ @version 1.0
 * @author linjunbiao
 * @created 2017.8.31
 */

public class GetAllLabels extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(GetAllLabels.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// PluginController controller =
		// ComponentAccessor.getComponent(PluginController.class);
		// controller.enablePlugins("com.pyxis.greenhopper.jira");
		// SprintManager testhh=new SprintManagerImpl();
		// Plugin plugin =
		// ComponentAccessor.getPluginAccessor().getEnabledPlugin("com.pyxis.greenhopper.jira");
		//
		//
		
		
		
		resp.getWriter().write("<a href='" + req.getContextPath() + "/login.jsp'>lianjie</a>");
		/*SearchRequestService searchRequestService = ComponentAccessor.getComponent(SearchRequestService.class);
		ProjectSearchRequestService projectSearchRequestService = ComponentAccessor.getComponent(ProjectSearchRequestService.class);
		ComponentAccessor.getProjectManager().getProjectByCurrentKey("TEST");
				ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
				projectSearchRequestService.getSearchRequestsForProject(user, project);
				SearchService searchService = ComponentAccessor.getComponent(SearchService.class);

				JiraServiceContextImpl serviceContext = new JiraServiceContextImpl(user);
				ParseResult parseResult = searchService.parseQuery(user, "project = JRA")
						if (parseResult.isValid()) {

						    // create the search request
						    Query query = parseResult.getQuery();
						    SearchRequest searchRequest = new SearchRequest(query, user, "My filter", "Some description");

						    // set shares
						    SharePermissionImpl sharePerm = new SharePermissionImpl(null, ShareType.Name.GROUP, "jira-administrators", null);
						    searchRequest.setPermissions(new SharedEntity.SharePermissions([sharePerm] as Set));

						    // store the search request
						    searchRequestService.createFilter(serviceContext, searchRequest);*/
		
		/*System.out.println(req.getServletPath()+","+req.getLocalAddr()+","+req.getLocalName()+","+req.getServletContext());
		JSONObject jsonObject1=new JSONObject();
		JSONArray jsonArray1=new JSONArray();
		ArrayList<String> a=new ArrayList<String>();
    	try {
    		jsonObject1.put("name","DellFilter");
    		jsonObject1.put("description","DellFilterljfldjf");
    		jsonObject1.put("jql","project = Dell");
    		jsonObject1.put("favourite","false");
    		
    		jsonObject1.put("name","DellBoardB");
    		jsonObject1.put("type","scrum");
    		jsonObject1.put("filterId","10109");
    		
    		jsonObject1.put("name","DellSprint");  		
    		jsonObject1.put("originBoardId","6");
    		a.add("DELL-1");
    		a.add("DELL-2");
    		jsonObject1.put("issues",a);  		
    		
    		
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    System.out.println("hello world");  */
     
		//SprintManagerImpl sprintManager = JiraUtils.loadComponent(SprintManagerImpl.class);
		//System.out.println(sprintManager + "fff");
		// ServiceOutcome serviceOutcome=sprintManager.getSprint(1);
		// System.out.println(serviceOutcome+"ffggg");
		// ServiceOutcome<Collection<Sprint>> sprints =
		// sprintManager.getAllSprints();
		// System.out.println(sprints.get().size());

		//RapidViewManagerImpl test = ComponentAccessor.getComponentOfType(RapidViewManagerImpl.class);
		//System.out.println(test + "999");
		/*
		 * RapidViewManagerImpl rapidViewManagerImpl =
		 * ComponentAccessor.getComponent(RapidViewManagerImpl.class);
		 * ServiceOutcome<List<RapidView>>
		 * serviceOutcome=rapidViewManagerImpl.getAll();
		 * System.out.println(serviceOutcome.get().size()+"999");
		 * 
		 * String
		 * str="com.atlassian.greenhopper.service.sprint.SprintManagerImpl";
		 */

		/*
		 * User user = ApplicationUsers.toDirectoryUser(ComponentAccessor.
		 * getJiraAuthenticationContext().getLoggedInUser()); ServiceOutcome
		 * <List <Sprint >> listServiceOutcome = new
		 * SprintQueryServiceImpl().getSprints(user,query);
		 */

		/*
		 * GreenHopperCache greenhopperContext =
		 * GetApplicationContext("com.pyxis.greenhopper.jira"); def
		 * sprintManager = greenhopperContext.getBean（“sprintManagerImpl”）;
		 */
		/*
		 * SprintManager sm=ComponentAccessor.getComponent(SprintManager.class);
		 * SprintManagerImpl
		 * smi=ComponentAccessor.getOSGiComponentInstanceOfType(
		 * SprintManagerImpl.class);
		 * System.out.println(smi+"@@@@@@@@@@@@@@"+sm);
		 */

		// ComponentManager componentManager = ComponentManager.getInstance();
		//
		// final PluginAccessor pluginAccessor =
		// ComponentManager.getComponent(PluginAccessor.class);
		// final SprintManagerImpl sprintManager =
		// ComponentManager.getComponent(SprintManagerImpl.class);
		// Plugin plugin =
		// pluginAccessor.getEnabledPlugin("com.pyxis.greenhopper.jira");
		//
		// System.out.println(pluginAccessor+"$$$$$$$$$$$$"+sprintManager);

		// ComponentManager componentManager = ComponentManager.getInstance();

		// final PluginAccessor pluginAccessor =
		// ComponentManager.getComponent(PluginAccessor.class);
		// this.sprintManagerImpl =
		// ComponentManager.getInstance().getComponentInstanceOfType(SprintManagerImpl.class);
		// final SprintManager sprintManager =
		// ComponentManager.getInstance().getComponentInstanceOfType(SprintManager.class);
		// Plugin plugin =
		// pluginAccessor.getEnabledPlugin("com.pyxis.greenhopper.jira");

		// System.out.println(this.sprintManagerImpl+"$$$$$$$$$$$$"+sprintManager+"####"+plugin.getActivePermissions());

		// SprintManager sprintManager =
		// PluginModuleCompilationCustomiser.getGreenHopperBean(sprintManager);
		// System.out.println(pluginAccessor.get+"$$$$$$$$$$$$");
		// def sprintServiceOutcome = sprintManager.getAllSprints()
		//
		// if (sprintServiceOutcome?.valid) {

		/*
		 * PluginAccessor pluginAccessor = componentManager.getPluginAccessor();
		 * try { Class greenHopperClass =
		 * pluginAccessor.getClassLoader().loadClass(
		 * "com.pyxis.greenhopper.GreenHopper");
		 * 
		 * Class<greenHopperClass> greenHopper =
		 * componentManager.getOSGiComponentInstanceOfType(greenHopperClass); }
		 * catch (ClassNotFoundException e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); }
		 */

		// CustomField sprint
		// =ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Sprint");
		// System.out.println(sprint);

		// 面板的相关字段。
		/*
		 * CustomField sprint
		 * =ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName
		 * ("Sprint");
		 * System.out.println("999"+sprint.getConfigurationSchemes().iterator().
		 * next().getName()); Collection<FieldScreen> collection1=
		 * ComponentAccessor.getFieldScreenManager().getFieldScreens();
		 * Iterator<FieldScreen> iterator2=collection1.iterator(); while
		 * (iterator2.hasNext()) { FieldScreen fieldScreen = iterator2.next();
		 * System.out.println(fieldScreen.getName()+"++++++++++");
		 * List<FieldScreenTab>
		 * fieldScreenTabs=ComponentAccessor.getFieldScreenManager().
		 * getFieldScreenTabs(fieldScreen); for(FieldScreenTab
		 * fieldScreenTab:fieldScreenTabs){ List<FieldScreenLayoutItem>
		 * fieldScreenLayoutItems=fieldScreenTab.getFieldScreenLayoutItems();
		 * Iterator<FieldScreenLayoutItem>
		 * iterator3=fieldScreenLayoutItems.iterator();
		 * while(iterator3.hasNext()){ FieldScreenLayoutItem
		 * fieldScreenLayoutItem=iterator3.next();
		 * System.out.println(fieldScreenLayoutItem.getFieldId()+"%%%%%%"+
		 * fieldScreenLayoutItem.getGenericValue()); } } } FieldScreenScheme
		 * fieldScreenScheme=
		 * ComponentAccessor.getIssueTypeScreenSchemeManager().
		 * getFieldScreenScheme(ComponentAccessor.getIssueManager().
		 * getIssueObject(10000L)); Collection<FieldScreenSchemeItem>
		 * collection=fieldScreenScheme.getFieldScreenSchemeItems();
		 * Iterator<FieldScreenSchemeItem> iterator1=collection.iterator();
		 * while(iterator1.hasNext()){ FieldScreenSchemeItem
		 * fieldScreenSchemeItem=iterator1.next();
		 * System.out.println(fieldScreenSchemeItem.getIssueOperationName()+
		 * "-----"+fieldScreenSchemeItem.getFieldScreenId()+"-------"+
		 * fieldScreenSchemeItem.getFieldScreenScheme()+"-----"+
		 * fieldScreenSchemeItem.getFieldScreen()+"-------"+
		 * fieldScreenSchemeItem.getGenericValue()+"-----"+fieldScreenSchemeItem
		 * .getIssueOperation()+"-------"); }
		 */
		

		/*
		 * Clause clause = new SprintQueryServiceImpl().getInSprintClause();
		 * System.out.println("--------"+clause.getName()); SprintManagerImpl
		 * smi=ComponentAccessor.getOSGiComponentInstanceOfType(
		 * SprintManagerImpl.class); ServiceOutcome<Collection<Sprint>>
		 * serviceOutcome= smi.getAllSprints(); Collection<Sprint>
		 * collection=serviceOutcome.get(); Iterator<Sprint>
		 * iterator1=collection.iterator(); while(iterator1.hasNext()){ Sprint
		 * sprint=iterator1.next();
		 * System.out.println(sprint.getName()+"---"+sprint.getId()+"---"+sprint
		 * .getRapidViewId()+"---"+sprint.getSequence()+"---"+sprint.getState()+
		 * "---"+sprint.getStartDate()+"---"+sprint.getEndDate()); }
		 */
		if (ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()) {

			JSONArray jsonArray = new JSONArray();
			// CustomField sprintField =
			// ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Sprint");
			// System.out.println(sprintField.getCustomFieldType().getName()+",-------------------"+sprintField.getCustomFieldType().getKey());
			List<CustomField> customFields = ComponentAccessor.getCustomFieldManager().getGlobalCustomFieldObjects(); // 获取全局字段
																									// ，
																									// 如果字段是labels类型，就放入jsonobect
			Iterator<CustomField> iterator = customFields.iterator();
			while (iterator.hasNext()) {
				JSONObject jsonObject = new JSONObject();
				CustomField customField = iterator.next();

				if ("Labels".equals(customField.getCustomFieldType().getName())) {
					try {

						jsonObject.put("Id", customField.getId());
						jsonObject.put("Name", customField.getName());
						jsonObject.put("FieldName", customField.getFieldName());
						jsonObject.put("NameKey", customField.getNameKey());
						jsonObject.put("Description", customField.getDescription());

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					jsonArray.put(jsonObject);
				}

			}

			// resp.getWriter().write("<script>console.log("+jsonArray.toString()+")</script>");

			resp.getWriter().write(jsonArray.toString());
		} else {
			resp.getWriter().write("请登录！");
		}

	}

}