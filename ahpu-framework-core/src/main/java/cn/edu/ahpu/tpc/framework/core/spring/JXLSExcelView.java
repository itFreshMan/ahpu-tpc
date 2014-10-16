package cn.edu.ahpu.tpc.framework.core.spring;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * 
 * @author  libinsong1204@gmail.com
 * @date    2011-1-18 上午11:03:34
 * @version 
 */
public class JXLSExcelView extends AbstractView {
	public static final String EXCEL_EXPORT_FILE_NAME = "ExcelExportFileName";
	public static final String EXCEL_TEMPLATE_FILE_NAME = "ExcelTemplateFileName";
	
	public static final String EXCEL_SHEET_NAMES = "ExcelSheetNames";
	public static final String MUTIL_SHEET_DATA_KEY = "MutilSheetDataKey";
	
	/** The content type for an Excel response */
	private static final String CONTENT_TYPE = "application/vnd.ms-excel";
	
	private XLSTransformerExt transformer;
	
	/**
	 * Default Constructor.
	 * Sets the content type of the view to "application/vnd.ms-excel".
	 */
	public JXLSExcelView() {
		transformer = new XLSTransformerExt();
		setContentType(CONTENT_TYPE);
	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = (String)model.get(EXCEL_EXPORT_FILE_NAME);
		String templateName = (String)model.get(EXCEL_TEMPLATE_FILE_NAME);
		List newSheetNames = (List)model.get(EXCEL_SHEET_NAMES);
		
		response.setHeader("content-disposition","attachment; filename="+new String(fileName.getBytes("gb2312"),"ISO8859-1"));
		
		String srcFilePath = "/resources/excel/" + templateName;
		
	  	ServletOutputStream out = response.getOutputStream();
	  	
	  	if(newSheetNames == null)
	  		transformer.transformXLS(request.getSession().getServletContext(), srcFilePath, model, out);
	  	else {
	  		transformer.transformMultipleSheetsList(srcFilePath, (List)model.get(MUTIL_SHEET_DATA_KEY), newSheetNames, out);
	  	}

	  	out.flush();
	}

}