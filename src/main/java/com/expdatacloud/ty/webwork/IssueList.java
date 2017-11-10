package com.expdatacloud.ty.webwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;

public class IssueList extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(IssueList.class);

    @Override
    public String execute() throws Exception {

        return super.execute(); //returns SUCCESS
    }

	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		return "issueList";
	}
    public String doIssueDetail() throws Exception {
    	return "issueDetail";
    }
}
