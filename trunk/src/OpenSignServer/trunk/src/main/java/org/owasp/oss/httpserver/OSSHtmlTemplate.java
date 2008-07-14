package org.owasp.oss.httpserver;

public class OSSHtmlTemplate {
	
	private String _content;
	private String _userName;
	private String _title;
	
	public String build() {
		
		String site = 
		"<html>" +
	    "<head>" +
			"<link type=\"text/css\" href=\"/style.css\" rel=\"stylesheet\" media=\"screen\" />" +
			"<title>Open Sign Server</title>" +
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
		"</head>" +
		"<body>" +
		"<div id=\"container\">" +
			"<div id=\"banner\" >" +
				"<span id=\"logo\">OpenSign Server</span>" +
		 		"<h1>" +		 			
		 			"<span id=\"menu_link\"><a href=\"/logout\">logout</a></span>" +
		 			"<span id=\"menu_link\"><a href=\"/\">home</a></span>" +
		 		"</h1>" +
			"</div>" +	
			"<div id=\"left\">" +
				"<h2>" + _title + "</h2>" +		
				"<br />user: " + _userName +
			"</div>" +
			"<div id=\"right\">" +
			
			"</div>" +
			"<div id=\"content\">" +
				"<h2>" + "</h2>" +	
				 _content +
			"</div>" +
		 	  "<div id=\"footer\">" +
		 		"<h1>&copy 2008 Richard, Phil</h1></div>" +
			  "</div>" +
		  "</body>" +
	"</html>";
		return site;
		
	}
	
	public void setContent(String content) {
		_content = content;
	}
	
	public void setUserName(String userName) {
		_userName = userName;
	}
	
	public void setTitle(String title) {
		_title = title;
	}	

}
