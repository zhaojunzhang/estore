package cn.itcast.utils;

import java.util.UUID;

/**
 * 文件上传工具类
 * @author samsung
 *
 */

public class UploadUtils {
	//根据uuid唯一文件名，hash算法生成分散目录
	public static String generateRandomDir(String uuidname){
		int hashcode=uuidname.hashCode();
		int d1=hashcode&0xf;
		int d2=(hashcode>>4)&0xf;
		return "/"+d1+"/"+d2;
		
	}
	//生成唯一UUID名称
	public static String generateUUIDName(String fileName){
		String ext = fileName.substring(fileName.lastIndexOf("."));
		
		return UUID.randomUUID().toString()+ext;
		
	}
	//切割文件名之前  路径
	public static String subFileName(String fileName){
		//c:\aa\bb\cc\1.jpg---查找最后一个\
		int index = fileName.lastIndexOf("\\");
		if(index!=1){
			//找到了
			return fileName.substring(index+1);
		}
		return fileName;
	}
	//校验图片  是不是图片格式----根据MIME  类型
	public static boolean checkImgType(String contentType){
		if(contentType.startsWith("image/")){
			return true;
			
		}else{
			return false;
		}
	}

}
