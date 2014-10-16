package org.apache.jsp.WEB_002dINF.views.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dict_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/>\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/resources/js/ext/resources/css/ext-all.css\" />\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/resources/js/ext/ext-base.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/resources/js/ext/ext-all.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/resources/js/ext/ext-lang-zh_CN.js\"></script>\r\n");
      out.write("    \r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/resources/css/icons.css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/resources/css/index.css\" />\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/ST.ux.util.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/Ext.ux.TreeCombo.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/ST.ux.ExtField.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/Ext.ux.PagePlugins.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/ST.ux.PlainGrid.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/ST.ux.PlainTree.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/ST.ux.ViewGrid.js\"></script>\r\n");
      out.write("\t\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/admin/ST.Base.Dict.js\"></script>\r\n");
      out.write("\t<style type=\"text/css\">\r\n");
      out.write("\t#ext-gen40 {\r\n");
      out.write("\t\t\t  white-space:nowrap;\r\n");
      out.write("\t\t     }     \r\n");
      out.write("\t</style>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\tExt.onReady(function(){\r\n");
      out.write("\t\t\tExt.override(Ext.Component, {\r\n");
      out.write("\t\t\t    stateful: false\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t    new ST.base.dictView();\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("  </head>\r\n");
      out.write("  <body>\r\n");
      out.write("\t<input type=\"hidden\" id=\"CONTEXT_PATH\" value=\"");
      out.print( request.getContextPath() );
      out.write("\" />\r\n");
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
