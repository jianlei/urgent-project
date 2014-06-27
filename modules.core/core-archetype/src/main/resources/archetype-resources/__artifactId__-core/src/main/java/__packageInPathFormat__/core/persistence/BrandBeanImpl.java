package ${package}.core.persistence;

import ${package}.api.persistence.IBrandBean;
import ${package}.api.persistence.IVersionable;
import org.lightcouch.Document;

import java.util.Date;

/**
 * @类描述：品牌实体
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class BrandBeanImpl extends AbstractDocument implements IBrandBean {


    String name;
    String describe;
    Date createDate;


    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescribe() {
        return describe;
    }

    @Override
    public void setDescribe(String describe) {
        this.describe = describe;
    }
}


