package ${package}.core.biz;

import ${package}.api.biz.IBrandService;
import ${package}.api.persistence.BrandBeanImpl;
import org.lightcouch.Attachment;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class BrandServiceImpl implements IBrandService {

    private static CouchDbClient dbClient;


    public void init() {
        dbClient = new CouchDbClient("carinfo", true, "http", "127.0.0.1", 5984, "admin", "secret");
    }


    @Override
    public BrandBeanImpl addBrand(BrandBeanImpl message) {
        Response response = dbClient.save(message);
        BrandBeanImpl foo = dbClient.find(BrandBeanImpl.class, response.getId());
        return foo;
    }

    @Override
    public BrandBeanImpl updateBrand(BrandBeanImpl message) {
        Response response = dbClient.update(message);
        BrandBeanImpl foo = dbClient.find(BrandBeanImpl.class, response.getId());
        return foo;
    }

    @Override
    public BrandBeanImpl putBrand(BrandBeanImpl message) {
        if (null != message.getRevision()) {
            return updateBrand(message);
        } else {
            return addBrand(message);
        }
    }

    @Override
    public BrandBeanImpl findBrand(String id) {
        BrandBeanImpl foo = dbClient.find(BrandBeanImpl.class, id);
        return foo;
    }

    @Override
    public void deleteBrand(String id) {
        BrandBeanImpl foo = dbClient.find(BrandBeanImpl.class, id);
        dbClient.remove(foo);
    }

    @Override
    public List<BrandBeanImpl> getAllBrand() {
        List resourceList;
        try {
            resourceList = dbClient.view("_all_docs").includeDocs(true).query(BrandBeanImpl.class);

        } catch (Exception e) {
            return new ArrayList<BrandBeanImpl>();
        }
        if (null != resourceList && resourceList.size() <= 0) {
            return new ArrayList<BrandBeanImpl>();
        }
        return resourceList;
    }

    @Override
    public String getAttachmentUrl(BrandBeanImpl message, String fileName) {
        String attachMentUrl = "http://127.0.0.1:5984/carinfo";
        BrandBeanImpl brandBean = message;
        Map<String, Attachment> attachmentMap1 = brandBean.getAttachments();
        for (String key : attachmentMap1.keySet()) {
            if (fileName.equals(key)) {
                attachMentUrl = attachMentUrl + "/" + brandBean.getId() + "/" + fileName;
                break;
            }
        }
        return attachMentUrl;
    }

    @Override
    public void destroy() {
        dbClient.shutdown();
    }
}


