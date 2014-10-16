package cn.edu.ahpu.tpc.framework.core.spring;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.context.support.ServletContextResource;

/**
 * 
 * @author  libinsong1204@gmail.com
 * @date    2011-1-18 上午11:03:34
 * @version 
 */
public class XLSTransformerExt extends XLSTransformer {
	
	@SuppressWarnings("rawtypes")
	public void transformXLS(ServletContext servletContext, String srcFilePath, Map beanParams, OutputStream os) {
    	try {
    		ServletContextResource resource = new ServletContextResource(servletContext, srcFilePath);
	        Workbook workbook = transformXLS(resource.getInputStream(), beanParams);
	        workbook.write(os);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 未完成
     * http://mengshuangcom.javaeye.com/blog/890071
     * 
     * @param srcFilePath
     * @param objects
     * @param newSheetNames
     * @param os
     */
	@SuppressWarnings("rawtypes")
	public void transformMultipleSheetsList(String srcFilePath, List objects, List newSheetNames, OutputStream os) {
    	try {
	        InputStream is = new BufferedInputStream(new FileInputStream(srcFilePath));
	        Workbook workbook = transformMultipleSheetsList(is, objects, newSheetNames, "map", new HashMap(), 0);
	        workbook.write(os);
	        is.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}