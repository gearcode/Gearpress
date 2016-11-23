package com.gearcode.gearpress.controller;

import com.gearcode.gearpress.util.FileUploadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Controller
@RequestMapping("/ueUpload")
public class UEUploadController {

	private static final Logger logger = LoggerFactory.getLogger(UEUploadController.class);

    @Value("${upload.root}")
    private String uploadRoot;
    
    @Value("${upload.preview}")
    private String uploadPreview;
    
    private static final HashMap<String, Object> config = new HashMap<String, Object>(){{
    	//上传图片配置项
		put("imageActionName", "uploadimage"); /* 执行上传图片的action名称 */
		put("imageFieldName", "upfile"); /* 提交的图片表单名称 */
		put("imageMaxSize", 20480000); /* 上传大小限制，单位B */
		put("imageAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"}); /* 上传图片格式显示 */
		put("imageCompressEnable", true); /* 是否压缩图片,默认是true */
		put("imageCompressBorder", 1600); /* 图片压缩最长边限制 */
		put("imageInsertAlign", "none"); /* 插入的图片浮动方式 */
		put("imageUrlPrefix", ""); /* 图片访问路径前缀 */
		put("imagePathFormat", "/ueditor/php/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}"); /* 上传保存路径,可以自定义保存路径和文件名格式 */
        /* {filename} 会替换成原文件名,配置这项需要注意中文乱码问题 */
        /* {rand:6} 会替换成随机数,后面的数字是随机数的位数 */
        /* {time} 会替换成时间戳 */
        /* {yyyy} 会替换成四位年份 */
        /* {yy} 会替换成两位年份 */
        /* {mm} 会替换成两位月份 */
        /* {dd} 会替换成两位日期 */
        /* {hh} 会替换成两位小时 */
        /* {ii} 会替换成两位分钟 */
        /* {ss} 会替换成两位秒 */
        /* 非法字符 \ : * ? " < > | */
        /* 具请体看线上文档: fex.baidu.com/ueditor/#use-format_upload_filename */
		
		// 列出指定目录下的图片
		put("imageManagerActionName", "listimage"); /* 执行图片管理的action名称 */
		put("imageManagerListPath", ""); /* 指定要列出图片的目录 */
		put("imageManagerListSize", 20); /* 每次列出文件数量 */
		put("imageManagerUrlPrefix", ""); /* 图片访问路径前缀 */
		put("imageManagerInsertAlign", "none"); /* 插入的图片浮动方式 */
		put("imageManagerAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"}); /* 列出的文件类型 */
	}};
    
	@RequestMapping()
	@ResponseBody
	public Object index(@RequestParam(value="action", required=true) String action, HttpServletRequest request) {
		/*
		 * 返回配置
		 */
		if(action.equals("config")) {
			return config;
		}
		
		/*
		 * 上传图片
		 */
		if(action.equals("uploadimage")) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = mRequest.getFile("upfile");
			try {
				String dir = "/" + mRequest.getParameter("folder");
				String name = FileUploadUtils.uploadRandomName(file, uploadRoot + dir);
				
				HashMap<String, Object> result = new HashMap<String, Object>();
				result.put("state", "SUCCESS");
				result.put("url", uploadPreview + dir + "/" + name);
				result.put("title", name);
				result.put("type", FileUploadUtils.getExtName(name));
				result.put("original", name);
				result.put("size", file.getSize());
				
				return result;
			} catch (IllegalStateException e) {
				logger.error(e.getLocalizedMessage(), e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			return "ERROR";
		}
		
		/*
		 * 列出图片
		 */
		if(action.equals("listimage")) {
			final String folder = ServletRequestUtils.getStringParameter(request, "folder", "");
			
			String imagesRootPath = uploadRoot + File.separator + folder;
			
			//获取图片列表
			File imagesDir = new File(imagesRootPath);
			final HashSet<String> allowSet = new HashSet<String>();
			CollectionUtils.addAll(allowSet, (String[]) config.get("imageManagerAllowFiles"));
			//文件列表
			final ArrayList<HashMap<String, Object>> files = new ArrayList<HashMap<String, Object>>();
			//
			imagesDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, final String name) {
					//判断符合格式的文件
					if(allowSet.contains(FileUploadUtils.getExtName(name).toLowerCase())) {
						files.add(new HashMap<String, Object>(){{
							put("url", uploadPreview + "/" + folder + "/" + name);
							put("mtime", "mtime");
						}});
					}
					return true;
				}
			});
			
			final int start = ServletRequestUtils.getIntParameter(request, "start", 0);
			return new HashMap<String, Object>(){{
				put("state", "SUCCESS");
				put("list", files);
				put("start", start);
				put("total", files.size());
			}};
		}
		
		return null;
	}
    
}
