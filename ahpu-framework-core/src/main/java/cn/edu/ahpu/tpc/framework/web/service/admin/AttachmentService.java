package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.ahpu.tpc.framework.web.dao.admin.AttachmentDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.Attachment;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;
import cn.edu.ahpu.common.uuid.RandomUniqueIdGenerator;

/**
 *
 * @datetime 2013-2-21 下午08:23:33
 * @author libinsong1204@gmail.com
 */
@Service
public class AttachmentService extends BaseServiceImpl<Attachment, Long> {
	private static final Logger _logger = LoggerFactory.getLogger(AttachmentService.class);
	
	@Autowired
	private AttachmentDao attachmentDao;
	
	@Value("${file.store.path}")
	private String fileStorePath;
	
	@Override
	public HibernateBaseDao<Attachment, Long> getHibernateBaseDao() {
		return this.attachmentDao;
	}
	
	/**
	 * 保存
	 */
	@Transactional
	public String upload(Long dataId, String descn, String attachmentType, MultipartFile multipartFile) {
		String fileCode = RandomUniqueIdGenerator.getNewString();
		try {
			Date createTime = new Date();
			String filePath = getFilePath(attachmentType, fileCode, createTime);
			
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(filePath));
			
			Attachment attachment = new Attachment();
			String fileType = multipartFile.getContentType();
	    	Long fileSize = multipartFile.getSize();
	    	String fileName = multipartFile.getOriginalFilename(); 
	    	
	    	attachment.setFileName(fileName);
	    	attachment.setFileSize(fileSize);
	    	attachment.setFileType(fileType);
	    	attachment.setAttachmentType(attachmentType);
	    	attachment.setCreateTime(createTime);
	    	attachment.setDataId(dataId);
	    	attachment.setDescn(descn);
	    	attachment.setFileCode(fileCode);
			
			attachmentDao.save(attachment);
		} catch (IOException e) {
			_logger.error(e.getMessage());
		}
		return fileCode;
	}
	
	/**
	 * 根据dataId查询附件
	 */
	public List<Attachment> queryAttachmentPage(Long dataId, String attachmentType) {
		return attachmentDao.findByNamedParam(new String[]{"attachmentType", "dataId"}, 
				new Object[]{attachmentType, dataId});
	}
	
	public Attachment queryAttachment(String type, String fileCode) {
		List<Attachment> list = attachmentDao.findByNamedParam(
				new String[]{"attachmentType", "fileCode"}, new Object[]{type, fileCode});
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public String getFilePath(String attachmentType, String fileCode, Date date) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		int year = calendar.get(java.util.Calendar.YEAR);
		int month = calendar.get(java.util.Calendar.MONTH)   +   1;     
		int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		String filePath = fileStorePath + File.separator + attachmentType + File.separator + year +
				File.separator + month + File.separator + day + File.separator + fileCode;
		
		return filePath;
	}
}
