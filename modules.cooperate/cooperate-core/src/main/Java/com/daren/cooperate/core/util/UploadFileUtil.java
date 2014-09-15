package com.daren.cooperate.core.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/14 11:17
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UploadFileUtil {

    public static boolean writerFile(HttpServletRequest request,String savePath){
        boolean flag = false;
        ServletContext sc=null;
        try {
            request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    System.out.println("表单参数名:" + item.getFieldName() + "，表单参数值:" + item.getString("UTF-8"));
                } else {
                    if (item.getName() != null && !item.getName().equals("")) {
                        System.out.println("上传文件的大小:" + item.getSize());
                        System.out.println("上传文件的类型:" + item.getContentType());
                        // item.getName()返回上传文件在客户端的完整路径名称
                        System.out.println("上传文件的名称:" + item.getName());
                        File tempFile = new File(item.getName());
                        //上传文件的保存路径
                        File file = new File(sc.getRealPath("/") + savePath, tempFile.getName());
                        item.write(file);
                        request.setAttribute("upload.message", "上传文件成功！");
                    }else{
                        request.setAttribute("upload.message", "没有选择上传文件！");
                    }
                }
            }
            flag = true;
        }catch(FileUploadException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("upload.message", "上传文件失败！");
        }
        return flag;
    }
}
