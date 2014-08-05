package com.daren.file.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.file.entities.VideoBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUploadVideoService extends IBizService {
    public List<VideoBean> getVideoBeanListByAttach(long id);
}
