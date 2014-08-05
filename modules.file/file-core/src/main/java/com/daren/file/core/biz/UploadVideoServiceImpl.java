package com.daren.file.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.file.api.biz.IUploadVideoService;
import com.daren.file.api.dao.IVideoBenaDao;
import com.daren.file.entities.VideoBean;

import java.util.List;

/**
 * @类描述：应急演练
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadVideoServiceImpl extends GenericBizServiceImpl implements IUploadVideoService {
    private IVideoBenaDao videoBenaDao;

    public void setVideoBenaDao(IVideoBenaDao videoBenaDao) {
        this.videoBenaDao = videoBenaDao;
        super.init(videoBenaDao, VideoBean.class.getName());
    }

    public List<VideoBean> getVideoBeanListByAttach(long id) {
        return videoBenaDao.getVideoBeanListByAttach(id);
    }
}


