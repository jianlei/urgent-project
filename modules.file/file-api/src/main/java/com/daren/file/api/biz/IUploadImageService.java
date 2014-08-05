package com.daren.file.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.file.entities.ImageBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUploadImageService extends IBizService {
    public List<ImageBean> getImageBeanListByAttach(long id);
}
