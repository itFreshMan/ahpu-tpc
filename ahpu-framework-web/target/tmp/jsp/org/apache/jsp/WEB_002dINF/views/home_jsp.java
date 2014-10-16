package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.core.support.ProfileApplicationContextInitializer;

public final class home_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/>\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/resources/js/ext/resources/css/ext-all.css\" />\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/resources/js/ext/ext-base.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/resources/js/ext/ext-all.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/resources/js/ext/ext-lang-zh_CN.js\"></script>\r\n");
      out.write("    \r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/resources/css/ext/tab-scroller-menu.css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/resources/css/icons.css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/resources/css/index.css\" />\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/Ext.ux.TabScrollerMenu.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/ST.ux.util.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/Ext.ux.AccordionPanel.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/Ext.ux.TreeCombo.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/ST.ux.ExtField.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/ux/uxVtypes.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/admin/ST.Base.Home.js\"></script>\r\n");
      out.write("\t<link href=\"/resources/css/jc_css.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("\t<!--[if IE 6]>\r\n");
      out.write("\t<script src=\"/resources/js/EvPNG.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t   EvPNG.fix('.top_logo, img,.top_ricon_a,.top_ricon_b,.top_ricon_c');\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<![endif]-->\r\n");
      out.write("  </head>\r\n");
      out.write("  <body>\r\n");
      out.write("   <script type=\"text/javascript\"> \r\n");
      out.write("    function getCurrentTime(){\r\n");
      out.write("    \treturn new Date().toLocaleString();\r\n");
      out.write("    }; \r\n");
      out.write("    setInterval(\"curTime.innerHTML=getCurrentTime();\",1000);\r\n");
      out.write("    </script>\r\n");
      out.write("  \t<input type=\"hidden\" id=\"casPorfile\" value=\"");
      out.print(ProfileApplicationContextInitializer.casProfile );
      out.write("\" />\r\n");
      out.write("  \t<input type=\"hidden\" id=\"CONTEXT_PATH\" value=\"");
      out.print( request.getContextPath() );
      out.write("\" />\r\n");
      out.write(" <div style=\"display: none;\">\r\n");
      out.write("\t   <div id=\"app-header\" >\r\n");
      out.write("\t        <div class=\"top_b\">\r\n");
      out.write("\t\t   \t    <div class=\"fn-left top_logo\"></div>\r\n");
      out.write("\t\t\t    <div class=\"top_r\">\r\n");
      out.write("\t\t\t     \t   <p><span>您好,<b>");
      out.print( SecurityContextUtil.getCurrentUser().getUserName() );
      out.write("</span> 今天是:<span id=\"curTime\"><script>curTime.innerHTML=getCurrentTime();</script></span></b></p>\r\n");
      out.write("\t\t\t       \t   <div class=\"top_ricon\">\r\n");
      out.write("\t\t\t\t            <ul>\r\n");
      out.write("\t\t\t\t                <li class=\"top_ricon_a\"><a href=\"#\" onClick=Home.OpenOnLineWin();>在线用户</a></li>\r\n");
      out.write("\t\t\t\t                <li class=\"top_ricon_b\"><a href=\"#\" onClick=ST.base.PersonConfig.showWin(\"personConfig\");>个人设置</a></li>\r\n");
      out.write("\t\t\t\t                <li class=\"top_ricon_c\"><a href=\"#\" onClick=Home.Logout();>退出系统</a></li>\r\n");
      out.write("\t\t\t\t            </ul>\r\n");
      out.write("\t\t\t\t        </div>\r\n");
      out.write("\t\t\t\t    </div>\r\n");
      out.write("\t\t\t </div>\r\n");
      out.write("\t        <!-- top end -->\r\n");
      out.write("      \r\n");
      out.write("\t    </div> \r\n");
      out.write("\t    <div id=\"home-panel\">\r\n");
      out.write("\t        <p>主页内容</p>\r\n");
      out.write("\t    </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/resources/js/ux/Ext.ux.TabCloseMenu.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/resources/js/admin/ST.Base.PersonConfig.js\"></script>\r\n");
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
