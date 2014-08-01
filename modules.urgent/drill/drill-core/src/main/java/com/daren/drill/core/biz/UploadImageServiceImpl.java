package com.daren.drill.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.drill.api.biz.IUploadImageService;
import com.daren.drill.api.dao.IImageBeanDao;
import com.daren.drill.entities.ImageBean;

import java.util.List;

/**
 * Created by Administrator on 14-8-1.
 */
public class UploadImageServiceImpl extends GenericBizServiceImpl implements IUploadImageService {
    private IImageBeanDao imageBeanDao;

    public void setImageBeanDao(IImageBeanDao imageBeanDao) {
        this.imageBeanDao = imageBeanDao;
        super.init(imageBeanDao, ImageBean.class.getName());
    }

    public List<ImageBean> getImageBeanListByAttach(long id) {
        return imageBeanDao.getImageBeanListByAttach(id);
    }
}
