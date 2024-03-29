package org.owasp.oss.httpserver;

public class OSSHtmlTemplate {

	private String _content;
	private String _userName;
	private String _title;
	private String _leftMenu;
	private String _rightMenu;
	private boolean _login = false;

	public String build() {

		String loginStr = "<span id=\"menu_link\"><a href=\"/login\">login</a></span>";
		String settingStr = "<span id=\"menu_link\"><a href=\"/register\">register</a></span>";
		if (_login) {
			loginStr = "<span id=\"menu_link\"><a href=\"/logout\">logout</a></span>";
			settingStr = "<span id=\"menu_link\"><a href=\"/settings\">my settings</a></span>";
		}

		if (_leftMenu == null || _leftMenu.length() == 0) {
			_leftMenu = "";
		} else {
			_leftMenu = "<div id=\"menu_box\">" + _leftMenu + "</div>";
		}

		_rightMenu = "<div id=\"menu_box\">This is a <a href=\"http://www.owasp.org\"><br /><b>OWASP</b><br /></a> project</div>";

		// +
		// "<span id=\"menu_link\"><a href=\"/edit_profile\">edit profile</a></span>"
		// + "<span id=\"menu_link\"><a href=\"/csr\">csr</a></span>"

		String site = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\" >"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\" >"
				+ "<html>"
				+ "<head>"
				+ "<link type=\"text/css\" href=\"/style.css\" rel=\"stylesheet\" media=\"screen\" />"
				+ "<title>Open Sign Server</title>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+ getJavaScript()
				+ "</head>"
				+ "<body>"
				+ "<div id=\"container\">"
				+ "<div id=\"banner\" ><span id=\"logo\">OpenSign Server</span></div>"
				+ "<h1>"
				+ "<span id=\"menu_link\"><a href=\"/\">home</a></span>"
				+ "<span id=\"menu_link\"><a href=\"/ca\">certificate authority</a></span>"
				+ settingStr
				+ loginStr
				+ "</h1>"
				+ "<div id=\"left\">"
				+ "<div id=\"menu_box\">"
				+ "<h2>"
				+ _title
				+ "</h2>"
				+ "</div>"
				+ _leftMenu
				+ "</div>"
				+ "<div id=\"right\">"
				+ "<div id=\"menu_box\">"
				+ "user: "
				+ _userName
				+ "</div>"
				+ _rightMenu
				+ getAboutBox()
				+ "</div>"				
				+ "<div id=\"content\">"
				+ "<h2>"
				+ "</h2>"
				+ _content
				+ "</div>"
				+ "<div id=\"footer\">"
				+ "<h1>&copy 2008 OpenSign <a onmouseover=\"showAbout();\">About</a></h1></div>"
				+ "</div>"
				+ "</body>" + "</html>";
		return site;

	}

	private String getJavaScript() {
		return "<script>"
				+ "function showAbout() {"
				+ "document.getElementById('menu_box_hide').style.display=\"\";"
				+ "}"
				+ "function hideAbout() {"
				+ "document.getElementById('menu_box_hide').style.display=\"none\";"
				+ "}" + "</script>";
	}

	private String getAboutBox() {
		return "<div id=\"menu_box_hide\" style=\"display: none;\" onmouseover=\"showAbout();\" onmouseout=\"hideAbout();\">"
				+ "<p><b>About</b></p>"
				+ "<p>Authors<br />"
				+ "<a href=\"mailto:philipp.potisk@gmail.com\"> Philipp Potisk</a><br />"
				+ "<a href=\"mailto:techierebel@yahoo.co.uk\">Richard Conway</a><br />"
				+ "</p>"
				+ "<p>Version<br />1.0</p>"
				+ "<p>Release Date<br />26st of October 2008</p>"
				+ "<p>Homepage<br /><a  href=\"https://www.owasp.org/index.php/Project_Information:template_OpenSign_Server_Project\">OpenSign Server</a><p>"
				+ "</div>";
	}

	public void setLogin(boolean value) {
		_login = value;
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

	public void setLeftMenu(String leftMenu) {
		_leftMenu = leftMenu;
	}

	public void setRightMenu(String rightMenu) {
		_rightMenu = rightMenu;
	}
}
