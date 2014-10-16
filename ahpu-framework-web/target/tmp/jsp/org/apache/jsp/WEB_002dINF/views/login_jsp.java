package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import cn.edu.ahpu.tpc.framework.web.util.admin.LoginParaUtil;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/>\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"./resources/js/ext/resources/css/ext-all.css\" />\r\n");
      out.write("    <script type=\"text/javascript\" src=\"./resources/js/ext/ext-base.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"./resources/js/ext/ext-all.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"./resources/js/ext/ext-lang-zh_CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"./resources/js/ux/ST.ux.Cookie.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"./resources/js/ux/ST.ux.ExtField.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"./resources/js/admin/ST.Base.Register.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"./resources/js/admin/ST.Base.Login.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"> \r\n");
      out.write("\t     if(top!=self){\r\n");
      out.write("\t          if(top.location != self.location)\r\n");
      out.write("\t               top.location=self.location; \r\n");
      out.write("\t     }\r\n");
      out.write("\t     register = ");
      out.print( LoginParaUtil.getRegisterValue());
      out.write(";\r\n");
      out.write("\t     autoLogin = ");
      out.print( LoginParaUtil.getAutoLoginValue());
      out.write(";\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<style type=\"text/css\">\r\n");
      out.write("\t    body {\r\n");
      out.write("\t\t\tbackground:#3d71b8 url(./resources/images/core/login/desktop.jpg) no-repeat left top;\r\n");
      out.write("\t\t    font: normal 12px tahoma, arial, verdana, sans-serif;\r\n");
      out.write("\t\t\tmargin: 0;\r\n");
      out.write("\t\t\tpadding: 0;\r\n");
      out.write("\t\t\tborder: 0 none;\r\n");
      out.write("\t\t\toverflow: hidden;\r\n");
      out.write("\t\t\theight: 100%;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t#ToolBar {\r\n");
      out.write("\t\t\tposition:absolute;\r\n");
      out.write("\t\t\tbottom:0px;\r\n");
      out.write("\t\t\twidth:100%;\r\n");
      out.write("\t\t\theight:60px;\r\n");
      out.write("\t\t\ttext-align:center;\r\n");
      out.write("\t\t\toverflow:hidden;\r\n");
      out.write("\t\t\tZ-index:100000\r\n");
      out.write("\t\t}\r\n");
      out.write("\t</style>\r\n");
      out.write("  </head>\r\n");
      out.write("  <body>\r\n");
      out.write("\t<div>\r\n");
      out.write("\t\t<div id=\"login\"></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div id=\"ToolBar\">\r\n");
      out.write("\t\t推荐使用浏览器：<a target=\"_blank\" href=\"https://www.google.com/intl/zh-CN/chrome/browser/\">Chrome</a>、\r\n");
      out.write("\t\t<a target=\"_blank\" href=\"http://www.mozilla.org/en-US/firefox/new/\">Firefox</a>\r\n");
      out.write("\t\t、<a target=\"_blank\" href=\"http://www.opera.com/zh-cn\">Opera</a>\r\n");
      out.write("\t\t或者<a target=\"_blank\" href=\"http://windows.microsoft.com/zh-cn/internet-explorer/download-ie\">IE9&10</a></br>\r\n");
      out.write("\t\t<img alt=\"Chrome\" src=\"/resources/images/core/login/chrome.png\">\r\n");
      out.write("\t\t<img alt=\"Firefox\" src=\"/resources/images/core/login/firefox.png\">\r\n");
      out.write("\t\t<img alt=\"Opera\" src=\"/resources/images/core/login/opera.png\">\r\n");
      out.write("\t\t<img alt=\"IE\" src=\"/resources/images/core/login/IE.png\">\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div style=\"display: none\">\r\n");
      out.write("\t\tAJAX-AccessDeniedException\r\n");
      out.write("\t</div>\r\n");
      out.write("  </body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
