package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.Attachment;
import cn.edu.ahpu.tpc.framework.web.service.admin.AttachmentService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 *
 * @datetime 2013-2-21 下午08:23:33
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController {
	private static final Logger _logger = LoggerFactory.getLogger(AttachmentController.class);
	
	@Autowired
	private AttachmentService attachmentService;
	
	@RequestMapping("/index")
	public String index(){
		return "admin/attachment";
	}
	
	/**
	 * 下载
	 */
	@RequestMapping("/download/{attachmentType}/{fileCode}")
	public void download(@PathVariable String attachmentType, @PathVariable String fileCode, HttpServletResponse response) {
		try {
			Attachment attachment = attachmentService.queryAttachment(attachmentType, fileCode);
			
			response.setHeader("Content-Type", "application/octet-stream");
			String filePath = attachmentService.getFilePath(attachmentType, fileCode, attachment.getCreateTime());
			File file = new File(filePath);
			
			response.setHeader("Content-Length", String.valueOf(file.length()));  
			response.setHeader("Content-Disposition", "attachment; filename=" + toPathEncoding(response.getCharacterEncoding(), attachment.getFileName()));
			
			FileUtils.copyFile(file, response.getOutputStream());
		} catch (IOException e) {
			_logger.error(e.getMessage());
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/remove/{attachmentType}/{fileCode}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData remove(@PathVariable String attachmentType, @PathVariable String fileCode) {
		Attachment attachment = attachmentService.queryAttachment(attachmentType, fileCode);
		attachmentService.deleteEntity(attachment.getId());
		
		String filePath = attachmentService.getFilePath(attachmentType, fileCode, attachment.getCreateTime());
		
		File file = new File(filePath);
		file.delete();
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 查询
	 */
	@RequestMapping("/queryAttachMents")
	@ResponseBody
	public List<Attachment> queryAttachments(Long dataId,
			String attachmentType) {
		if(dataId == null || !StringUtils.hasText(attachmentType)) {
			return new ArrayList<Attachment>();
		}
		List<Attachment> list = attachmentService.queryAttachmentPage(dataId, attachmentType);
		return list;
	}
	
	protected static final String DEFAULT_FILE_ENCODING = "ISO-8859-1";
	private String toPathEncoding(String origEncoding, String fileName) throws UnsupportedEncodingException{
		return new String(fileName.getBytes(origEncoding), DEFAULT_FILE_ENCODING);
	}
	
	/**
	 * 文件管理，查询附件信息
	 */
	@RequestMapping("/pageQueryAttachments")
	@ResponseBody
	public Pagination<Attachment> pageQueryAttachments(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, String fileName, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<Attachment> paginationRequest = new PaginationRequest<Attachment>(offset/limit, offset, limit);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		if(StringUtils.hasText(fileName))
			paginationRequest.addLikeCondition("fileName", "%" + fileName + "%");
		
		Pagination<Attachment> page = attachmentService.findPage(paginationRequest);
		return page;
	}
}
