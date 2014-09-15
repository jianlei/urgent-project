package com.daren.cooperate.core.biz;

import com.daren.admin.core.util.UtilTools;
import com.daren.cooperate.api.biz.IChatBeanService;
import com.daren.cooperate.api.dao.IChatBasicBeanDao;
import com.daren.cooperate.api.dao.IChatIndexBeanDao;
import com.daren.cooperate.core.model.*;
import com.daren.cooperate.core.util.CookieUtil;
import com.daren.cooperate.core.util.SendMsgByXingeThread;
import com.daren.cooperate.core.util.TimeDifference;
import com.daren.cooperate.core.util.UploadFileUtil;
import com.daren.cooperate.entities.ChatBasicBean;
import com.daren.cooperate.entities.ChatIndexBean;
import com.daren.core.api.IConst;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.core.util.DateUtil;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @类描述：聊天
 * @创建人：xukexin
 * @创建时间：2014/9/4 11:07
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChatBeanServiceImpl extends GenericBizServiceImpl implements IChatBeanService {

    private IChatBasicBeanDao chatBasicBeanDao;
    private IChatIndexBeanDao chatIndexBeanDao;

    public void setChatBasicBeanDao(IChatBasicBeanDao chatBasicBeanDao) {
        this.chatBasicBeanDao = chatBasicBeanDao;
    }

    public void setChatIndexBeanDao(IChatIndexBeanDao chatIndexBeanDao) {
        this.chatIndexBeanDao = chatIndexBeanDao;
    }

    @POST
    @Path("/sendmes")
    @Produces("application/json;charset=utf-8")
    public Map sendmes(@FormParam("chat_type")int chat_type,@FormParam("chat_content") String chat_content,@FormParam("chat_lat")double chat_lat,
                       @FormParam("chat_lng")double chat_lng, @FormParam("to_userid")String to_userid,@FormParam("group_id")String group_id,
                       @FormParam("voice_time")String voice_time,@Context HttpServletRequest request) {
        int result =-1;
        long uid = 0;
        String photo="";
        String head_photo = "";
        String head_spicurl = "";
        String message = "信息发送失败!";
        String pushmessage = "";
        Map returnMap = new HashMap();
        int is_read =0;//0未读 已读
        ChatBasicBean chat =null;
        try {
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
//                chat_lat = chat_lat;
//                chat_lng =chat_lng!=null?chat_lng:0;
                voice_time =voice_time!=null?voice_time:"";
                switch(chat_type){
                    case 1:{
                        pushmessage = "[语音]";
                        break;
                    }
                    case 2:{
                        pushmessage = "[图片]";
                        break;
                    }
                    case 3:{
                        pushmessage = "[视频]";
                        break;
                    }
                    case 4:{
                        pushmessage = "[文件]";
                        break;
                    }
                    case 5:{
                        pushmessage = "[位置]";
                        break;
                    }
                    default : {
                        pushmessage = chat_content;
                    }
                }
                //上传图片
                long currentTimeMillis = System.currentTimeMillis();
                photo = IConst.OFFICE_WEB_PATH_READ+currentTimeMillis;
                head_photo = IConst.OFFICE_WEB_PATH_READ+currentTimeMillis;
                UploadFileUtil.writerFile(request, photo);
                if(group_id==null){//私聊
                    chat = new ChatBasicBean(chat_type, is_read, chat_content,head_photo,  head_spicurl,  Long.parseLong(String.valueOf(to_userid)),user_id,0,0,voice_time);
                    chat.setChat_time(DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    chat = chatBasicBeanDao.save(chat);
                    //.addPrivateLocMessage(chat);
                }else{//群聊
                    chat = new ChatBasicBean(Long.parseLong(String.valueOf(group_id)),chat_type, is_read, chat_content,head_photo,  head_spicurl, user_id,chat_lat,chat_lng,voice_time);
                    chat.setChat_time(DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    chat = chatBasicBeanDao.save(chat);
                }
                if(chat!=null){
                    if(group_id==null){//私聊
                        ChatIndexBean chatIndex = new ChatIndexBean(user_id,Long.parseLong(String.valueOf(to_userid)), UtilTools.getSysDateTime("yyyy-MM-dd HH:mm"));
                        //更新
                        chatBasicBeanDao.update("update ChatIndexBean c " +
                                "set c.chat_count=c.chat_count+1,c.neartime ='"+UtilTools.getSysDateTime("yyyy-MM-dd HH:mm")+"'" +
                                "where c.from_userid = "+user_id+" and c.to_userid = "+to_userid);
                        //查询最近一次聊天记录
                        chatBasicBeanDao.update("update ChatIndexBean c set c.unsearch_userid='' " +
                                " where c.from_userid in("+user_id+","+to_userid+") and c.to_userid in("+to_userid+","+user_id+")");
                        //插入
                        chatIndex = chatIndexBeanDao.save(chatIndex); //.insertChatIndex(chatIndex);
                        //push
                        List<String> userTokenByIdList = chatBasicBeanDao.findByNativeSql("SELECT token " +
                                " FROM sys_user_rel WHERE user_id="+to_userid,String.class);
                        //getUserTokenById(Long.parseLong(to_userid));
                        if(userTokenByIdList!=null){
                            String userTokenById = userTokenByIdList.get(0);
                            JSONObject pushjsoncontent = new JSONObject();
                            pushjsoncontent.put("fuction", 5001);
                            pushjsoncontent.put("message", pushmessage);
                            pushjsoncontent.put("chat_id", chat.getId());
                            pushjsoncontent.put("messageid", Long.parseLong(String.valueOf(user_id)));
                            SendMsgByXingeThread smxt = new SendMsgByXingeThread(userTokenById,3,"","",pushjsoncontent);//3单个token  push
                            Thread thread = new Thread(smxt);
                            thread.start();
                        }
                    }else{//群聊
                        //该条轮组下所有人的消息状态都变成未读.
                        HashMap hm = new HashMap();
                        hm.put("group_id", group_id);
                        hm.put("is_read", 0);//该讨论组中我的消息全变未读
                        ChatIndexBean chatIndex = new ChatIndexBean(user_id,0,Long.parseLong(String.valueOf(group_id)), UtilTools.getSysDateTime("yyyy-MM-dd HH:mm"));
                        chatBasicBeanDao.update("update GroupChatIndexBean g " +
                                "set g.chat_count=g.chat_count+1,g.neartime ='"+UtilTools.getSysDateTime("yyyy-MM-dd HH:mm")+"'",
                                "g.from_userid = "+user_id+
                                "where g.group_id = "+group_id); //.updateGroupChatIndex(chatIndex);//更新
                        chatBasicBeanDao.update("update GroupChatIndexBean g " +
                                "set g.unsearch_userid='' " +
                                "where g.group_id ="+group_id);  //.updateChatIndexGroupSearch(hm);//可查询最近一次聊天记录
                        chatIndex = chatIndexBeanDao.save(chatIndex); //.insertGROUPChatIndex(chatIndex);//插入
                        chatIndexBeanDao.update("update ChatBasicBean c " +
                                "set c.is_read =0 " +
                                "where c.group_id = "+group_id); //.updateGroupAllPeopleLookStatus(hm);
                        //讨论组所有人变有消息
                        hm.put("hasnews", 1);
                        chatIndexBeanDao.update("update GroupChatIndexBean g " +
                                "set g.hasnews =1 " +
                                "where g.group_id ="+group_id);   //.updateGroupAllUserRMessageLookStatus(hm);
                        //push
                        Map m = new HashMap();
                        m.put("user_id", user_id);
                        m.put("group_id", group_id);
                        List<String> userTokenListInGroup = chatIndexBeanDao.findByNativeSql("SELECT tub.token " +
                                "FROM coop_group_basic tg LEFT JOIN sys_user_rel tub ON tub.user_id=tg.manager_user " +
                                "WHERE tg.group_id="+group_id+" AND tub.user_id!="+user_id +
                                "AND tub.token is not null AND tub.token != 'null' " +
                                "UNION SELECT distinct tub.token " +
                                " FROM coop_group_user_rel tgur LEFT JOIN sys_user_rel tub ON tub.user_id=tgur.user_id " +
                                "WHERE tgur.group_id="+group_id+" AND tub.user_id!="+to_userid+
                                "AND tub.token is not null AND tub.token != 'null'",String.class);  //.getUserTokenListInGroup(m);
                        if(userTokenListInGroup!=null&&!userTokenListInGroup.isEmpty()){
                            JSONObject pushjsoncontent = new JSONObject();
                            pushjsoncontent.put("fuction", 5002);
                            pushjsoncontent.put("message", pushmessage);
                            pushjsoncontent.put("chat_id", chat.getId());
                            pushjsoncontent.put("messageid", Long.parseLong(String.valueOf(group_id)));
                            SendMsgByXingeThread smxt = new SendMsgByXingeThread(userTokenListInGroup,1,"","",pushjsoncontent);
                            Thread thread = new Thread(smxt);
                            thread.start();
                        }
                    }
                    returnMap.put("chat_type", chat_type);
                    returnMap.put("chat_content", chat_content!=null?chat_content:"");
                    returnMap.put("chat_attach", head_photo);
                    returnMap.put("picture_url", head_spicurl);
                    result =1;
                    message = "信息发送成功!";
                }
            }
        } catch (Exception e) {
            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            returnMap.put("message", message);
            returnMap.put("result", result);
        }
        return returnMap;
    }

    @Override
    @POST
    @Path("/deleteLastChat")
    @Produces("application/json;charset=utf-8")
    public Map deleteLastChat(@FormParam("to_userid")Long to_userid, @FormParam("group_id")Long group_id,@Context HttpServletRequest request) {
        int result =-1;
        HashMap returnMap = new HashMap();
        try {
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                if(to_userid!=null){
                    chatBasicBeanDao.update("update coop_chat_index " +
                            "set unsearch_userid=Concat(unsearch_userid,"+user_id+",',')" +
                            "where from_userid in("+user_id+","+to_userid+") and to_userid in("+to_userid+","+user_id+")");

                }
                if(group_id!=null){
                    chatBasicBeanDao.update("update coop_group_chat_index " +
                            " set unsearch_userid=Concat(unsearch_userid,"+user_id+",',') " +
                            " where group_id ="+group_id);
                }
            result=1;
            }
        } catch (Exception e) {
            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            returnMap.put("result", result);
        }
        return returnMap;
    }

    @Override
    @GET
    @Path("/getChatList")
    @Produces("application/json;charset=utf-8")
    public Map getChatList(@QueryParam("page")Integer page,@QueryParam("limit") Integer limit,@Context HttpServletRequest request) {
        int result =-1;
        HashMap returnMap = new HashMap();
        ArrayList userList = new ArrayList();
        try {
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            String groupBaseurl = IConst.OFFICE_WEB_PATH_READ;   //Config.getInstance().get("groupBaseurl");
            String groupStoredir = IConst.OFFICE_WEB_PATH_WRITE; //Config.getInstance().get("groupStoredir");
            if(user_id>0){
                HashMap hm = new HashMap();
                if(page==null){
                    page = 1;
                }
                if(limit==null){
                    limit = 15;
                }
                int start = (page-1)*limit;
                int flg=0;
                //根据from_userid看是否为空   空这根据to_userid
                List<ChatIndex> mpl = chatBasicBeanDao.findByNativeSql("SELECT from_userid,0 as to_userid,chat_content," +
                        " 2 as messagetype,neartime,group_id,hasnews,unsearch_userid FROM coop_group_chat_index " +
                        " WHERE group_id in (select group_id from coop_group_basic where manager_user="+user_id +
                        " union select group_id from coop_group_chat_index where user_id="+user_id +
                        " ) Union SELECT from_userid,to_userid,chat_content,3 as messagetype,coop_chat_index.neartime," +
                        " group_id,hasnews,unsearch_userid FROM coop_chat_index " +
                        " WHERE from_userid ="+user_id +" OR to_userid ="+user_id +
                        " And group_id =0 ORDER BY neartime DESC " +
                        " LIMIT "+start+","+limit,ChatIndex.class);
                if(mpl!=null&&!mpl.isEmpty()){
                    Iterator itts=mpl.iterator();
                    while(itts.hasNext()){
                        ChatIndex ci=(ChatIndex)itts.next();
                        if(ci.getMessagetype()==3){//一对一聊天
                            boolean flag = true;
                            StringTokenizer st=new StringTokenizer(ci.getUnsearch_userid(),",");//构造二
                            while(st.hasMoreTokens()){
                                long unsearch_userid = Long.parseLong(st.nextToken());
                                if(user_id==unsearch_userid){
                                    flag=false;
                                }
                            }
                            if(flag){
                                if(user_id==ci.getFrom_userid()){
                                    HashMap hms = new HashMap();
                                    List<ChatListForIndex> teaList = chatBasicBeanDao.findByNativeSql("SELECT coop_chat_basic.id as chat_id," +
                                            " coop_chat_basic.chat_type,coop_chat_basic.chat_content,coop_chat_basic.chat_time as neartime,picture_url as attache_spicurl," +
                                            " coop_chat_basic.to_userid as to_user_id,coop_chat_basic.from_userid as from_user_id," +
                                            " coop_chat_basic.is_read as hasnews " +
                                            " FROM coop_chat_basic WHERE from_userid in("+ci.getTo_userid()+","+ci.getFrom_userid()+") " +
                                            "and to_userid in("+ci.getFrom_userid()+","+ci.getTo_userid()+")"+
                                            " ORDER BY coop_chat_basic.chat_time DESC LIMIT 1",ChatListForIndex.class);
                                    if(teaList!=null && teaList.size()>0){
                                        ChatListForIndex tea = teaList.get(0);
                                        ChatList teat =new ChatList();
                                        teat.setChat_content(tea.getChat_content());
                                        teat.setChat_id(tea.getChat_id());
                                        teat.setNeartime(tea.getNeartime());
                                        teat.setFrom_userid(user_id);
                                        teat.setTo_userid(ci.getTo_userid());
                                        teat.setHasnews(0);
                                        teat.setChat_type(tea.getChat_type());
                                        List<User> npList = chatBasicBeanDao.findByNativeSql("select sys_user.name," +
                                                " sys_user_rel.user_logo as head_spicurl " +
                                                " from sys_user left join sys_user_rel on sys_user_rel.user_id= sys_user.id " +
                                                " where sys_user.id ="+ci.getTo_userid(),User.class);
                                        if(npList!=null){
                                            User np = npList.get(0);
                                            if(np!=null&&np.getHead_spicurl()!=null && np.getHead_spicurl().length()>0){
                                                teat.setLogo(np.getHead_spicurl());
                                            }
                                            String ss = np.getName();
                                            if(np!=null&&np.getName()!=null){
                                                teat.setName(np.getName());
                                            }
                                            userList.add(teat);
                                        }
                                    }
                                }else{
                                    HashMap hms = new HashMap();
                                    List<ChatListForIndex> teaList = chatBasicBeanDao.findByNativeSql("SELECT coop_chat_basic.id as chat_id," +
                                            " coop_chat_basic.chat_type,chat_content,chat_time as neartime,picture_url as attache_spicurl," +
                                            " coop_chat_basic.to_userid as to_user_id,coop_chat_basic.from_userid as from_user_id," +
                                            " coop_chat_basic.is_read as hasnews " +
                                            " FROM coop_chat_basic WHERE from_userid in("+ci.getTo_userid()+","+ci.getFrom_userid()+") " +
                                            " and to_userid in("+ci.getFrom_userid()+","+ci.getTo_userid()+")"+
                                            " ORDER BY coop_chat_basic.chat_time DESC LIMIT 1",ChatListForIndex.class);
                                    if(teaList!=null){
                                        ChatListForIndex tea = teaList.get(0);
                                        ChatList teat =new ChatList();
                                        teat.setChat_content(tea.getChat_content());
                                        teat.setChat_id(tea.getChat_id());
                                        teat.setNeartime(tea.getNeartime());
                                        teat.setTo_userid(ci.getFrom_userid());
                                        teat.setFrom_userid(ci.getTo_userid());
                                        teat.setHasnews(tea.getHasnews()==1?0:1);
                                        teat.setChat_type(tea.getChat_type());
                                        List<User> npList = chatBasicBeanDao.findByNativeSql("select sys_user.name," +
                                                " sys_user_rel.user_logo as head_spicurl " +
                                                " from sys_user left join sys_user_rel on sys_user_rel.user_id= sys_user.id " +
                                                " where sys_user.id ="+ci.getFrom_userid(),User.class);
                                        if(npList!=null){
                                            User np = npList.get(0);
                                            if(np!=null&&np.getHead_spicurl()!=null && np.getHead_spicurl().length()>0){
                                                teat.setLogo(np.getHead_spicurl());
                                            }
                                            if(np!=null&&np.getName()!=null){
                                                teat.setName(np.getName());
                                            }
                                            userList.add(teat);
                                        }
                                    }
                                }
                            }
                        }
                        if(ci.getMessagetype()==2){//查询讨论组前三个人的头像
                            boolean flag = true;
                            StringTokenizer st=new StringTokenizer(ci.getUnsearch_userid(),",");//构造二
                            while(st.hasMoreTokens()){
                                long unsearch_userid = Long.parseLong(st.nextToken());
                                if(user_id==unsearch_userid){
                                    flag=false;
                                }
                            }
                            if(flag){
                                ChatList teat =new ChatList();
                                teat.setGroup_id(ci.getGroup_id());
                                teat.setFrom_userid(ci.getFrom_userid());
                                teat.setTo_userid(ci.getTo_userid());
                                teat.setMessagetype(ci.getMessagetype());
                                hm.put("group_id", ci.getGroup_id());
                                List<ChatListForIndex> grupMessPhotos = chatBasicBeanDao.findByNativeSql("SELECT head_spicurl as attache_spicurl," +
                                        " sys_user.name as chat_voice,coop_group_basic.group_name as chat_content " +
                                        " FROM coop_group_user_rel LEFT JOIN sys_user ON coop_group_user_rel.user_id = sys_user.id " +
                                        " LEFT JOIN coop_group_basic ON coop_group_user_rel.group_id = coop_group_basic.id " +
                                        " WHERE coop_group_user_rel.group_id ="+ci.getGroup_id()+
                                        " LIMIT 3",ChatListForIndex.class);
                                if(grupMessPhotos!=null){
                                    String gphotos ="";
                                    String names ="";
                                    for (int i = 0; i < grupMessPhotos.size(); i++) {
                                        String name = grupMessPhotos.get(i).getChat_voice();
                                        if(grupMessPhotos.get(i).getChat_content()!=null && !grupMessPhotos.get(i).getChat_content().equals("")){
                                            teat.setName(grupMessPhotos.get(i).getChat_content());
                                        }else{
                                            if(name!=null && !name.equals("")){
                                                if(i==grupMessPhotos.size()-1){
                                                    names+=name;
                                                    teat.setName(names);
                                                }else{
                                                    names+=name+"、";
                                                }
                                            }
                                        }
                                    }
                                    File sdir = new File(groupStoredir+ci.getGroup_id()+".png");//检测该讨论组头像是否存在
                                    if(sdir.exists()) {
                                        teat.setLogo(groupBaseurl+ci.getGroup_id()+".png");
                                    }else{
                                        teat.setLogo("");
                                    }
                                    //放在list里和一对已聊天进行一起排序
                                    List<ChatListForIndex> teaList = chatBasicBeanDao.findByNativeSql("SELECT chat_content,chat_type," +
                                            "is_read as hasnews,chat_time as neartime " +
                                            "FROM coop_chat_basic WHERE chat_time in(select MAX(chat_time) from coop_chat_basic " +
                                            "where group_id in ("+ci.getGroup_id()+")) limit 1 ",ChatListForIndex.class);
                                    if(teaList!=null){
                                        ChatListForIndex tea = teaList.get(0);
                                        teat.setChat_content(tea.getChat_content());
                                        teat.setChat_type(tea.getChat_type());
                                        if(ci.getFrom_userid()==user_id){
                                            teat.setHasnews(0);
                                        }else{
                                            List<ChatListForIndex> teagroupunlookmesList = chatBasicBeanDao.findByNativeSql("SELECT hasnews" +
                                                    " FROM coop_group_user_rel WHERE group_id="+ci.getGroup_id()+
                                                    " and user_id="+user_id,ChatListForIndex.class);
                                            if(teagroupunlookmesList!=null){
                                                ChatListForIndex teagroupunlookmes = teagroupunlookmesList.get(0);
                                                if(teagroupunlookmes==null){
                                                    teat.setHasnews(tea.getHasnews()==1?0:1);
                                                }else{
                                                    teat.setHasnews(teagroupunlookmes.getHasnews());
                                                }
                                            }
                                        }
                                        teat.setNeartime(tea.getNeartime());
                                    }
                                    List<ChatListForIndex> group_manager_userList = chatBasicBeanDao.findByNativeSql("",ChatListForIndex.class);
                                    if(group_manager_userList!=null){
                                        ChatListForIndex group_manager_user = group_manager_userList.get(0);
                                        teat.setGroup_creater(group_manager_user.getGroup_manager_user()==user_id?1:0);
                                        userList.add(teat);
                                    }
                                }
                            }

                        }
                        flg++;
                    }
                }
                //对聊天结果进行升序排列
                //放入TreeSet
                TreeSet ts=new TreeSet(new MyChatListComparatorsec());
                Iterator iter = userList.iterator();
                while (iter.hasNext()) {
                    ChatList tea=(ChatList)iter.next();
                    ts.add(tea);
                }
                userList.clear();
                if(mpl!=null&&!mpl.isEmpty()){
                    for (int i = 0; i < mpl.size(); i++) {
                        ChatIndex ci = mpl.get(i);
                        if(ci.getMessagetype()==1){
                            ci.setName("长春志愿者联合会");
                            userList.add(ci);
                        }
                    }
                }
                Iterator ittsss=ts.iterator();
                while(ittsss.hasNext()){
                    ChatList tea=(ChatList)ittsss.next();
                    tea.setNeartime(TimeDifference.getTimeDifdference(tea.getNeartime()));
                    userList.add(tea);
                }
            }
            result=1;
        } catch (Exception e) {
            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            returnMap.put("response", userList);
            returnMap.put("result", result);
        }
        return returnMap;
    }

    @Override
    @GET
    @Path("/getChatMsgList")
    @Produces("application/json;charset=utf-8")
    public Map getChatMsgList(@QueryParam("page")Integer page, @QueryParam("limit")Integer limit,
                              @QueryParam("to_userid")long to_userid,@Context HttpServletRequest request) {
        int result =-1;
        int flg =0;
        HashMap returnMap = new HashMap();
        ArrayList<ChatContent> userList = new ArrayList();
        try {
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                HashMap hm = new HashMap();
                if(page==null){
                    page = 1;
                }
                if(limit==null){
                    limit = 15;
                }
                int start = (page-1)*limit;
                //获取我给别人发的
                List<ChatContent> myChatContentList = chatBasicBeanDao.findByNativeSql("SELECT * FROM( " +
                        " SELECT coop_chat_basic.id as chat_id,from_userid as user_id,chat_content,is_read,voice_time," +
                        " sys_user_rel.head_spicurl as logo,name,chat_time,coop_chat_basic.chat_attach as attache_url," +
                        " coop_chat_basic.picture_url as attache_spicurl,coop_chat_basic.chat_type," +
                        " coop_chat_basic.chat_lat,coop_chat_basic.chat_lng " +
                        " FROM coop_chat_basic LEFT JOIN sys_user ON sys_user.id = coop_chat_basic.from_userid " +
                        " left join sys_user_rel on sys_user_rel.user_id = sys_user.id " +
                        " WHERE from_userid="+user_id+" AND to_userid="+to_userid+" ORDER BY chat_time DESC " +
                        " LIMIT "+start+","+limit+")as b",ChatContent.class);
                if(myChatContentList!=null){
                    Iterator its=myChatContentList.iterator();
                    while(its.hasNext()){
                        ChatContent tea=(ChatContent)its.next();
                        tea.setComMess(false);
                        tea.setChat_time(tea.getChat_time().substring(0, 19));
                        userList.add(tea);
                        flg++;
                    }
                }
                //获取我给别人发的
                List<ChatContent> otherChatToMyContentList = chatBasicBeanDao.findByNativeSql("SELECT * FROM(" +
                        " SELECT coop_chat_basic.id as chat_id,from_userid as user_id,chat_content,is_read,voice_time," +
                        " sys_user_rel.head_spicurl as logo,name,chat_time,coop_chat_basic.chat_attach as attache_url," +
                        " coop_chat_basic.picture_url as attache_spicurl,coop_chat_basic.chat_type," +
                        " coop_chat_basic.chat_lat,coop_chat_basic.chat_lng " +
                        " FROM coop_chat_basic LEFT JOIN sys_user ON sys_user.id = coop_chat_basic.from_userid " +
                        " left join sys_user_rel on sys_user_rel.user_id = sys_user.id " +
                        "WHERE from_userid="+to_userid+" AND to_userid="+user_id+" ORDER BY chat_time DESC " +
                        " LIMIT "+start+","+limit+")as b",ChatContent.class);
                if(otherChatToMyContentList!=null){
                    Iterator itss=otherChatToMyContentList.iterator();
                    while(itss.hasNext()){
                        ChatContent tea=(ChatContent)itss.next();
                        tea.setComMess(true);
                        tea.setChat_time(tea.getChat_time().substring(0, 19));
                        userList.add(tea);
                        flg++;
                    }
                }
                //对聊天结果进行升序排列
                //放入TreeSet
                long ageold = 0;
                long agenew = 0;
                int isone=0;
                long fivem =300000;
                SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                TreeSet ts=new TreeSet(new MyChatComparatorsec());
                Iterator iter = userList.iterator();
                while (iter.hasNext()) {
                    ChatContent tea=(ChatContent)iter.next();
                    ts.add(tea);
                }
                userList.clear();
                Iterator itts=ts.iterator();
                while(itts.hasNext()){
                    ChatContent tea=(ChatContent)itts.next();
                    if(isone ==0){
                        try {
                            ageold = df.parse(tea.getChat_time()).getTime();
                            ageold=ageold+fivem;
                        } catch (Exception e) {
                            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            agenew= df.parse(tea.getChat_time()).getTime();
                            if(agenew<ageold){
                                //tea.setChat_time("");
                            }
                            ageold=agenew+fivem;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    isone++;
                    userList.add(tea);
                }
                //消息消息全变已读
                chatBasicBeanDao.update("update ChatBasicBean c set c.is_read =1" +
                                "where c.from_userid = "+to_userid+" and c.to_userid = "+user_id);
                result=1;
            }
        } catch (Exception e) {
            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            returnMap.put("response", userList);
            returnMap.put("result", result);
        }
        return returnMap;
    }

    @Override
    @GET
    @Path("/getGroupChatList")
    @Produces("application/json;charset=utf-8")
    public Map getGroupChatList(@QueryParam("page")Integer page, @QueryParam("limit")Integer limit, @QueryParam("group_id")long group_id,
                               @Context HttpServletRequest request) {
        int result =-1;
        int flg =0;
        long ageold = 0;
        long agenew = 0;
        int isone=0;
        long fivem =300000;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap returnMap = new HashMap();
        List<ChatContent> myGroupChatContentList = new ArrayList();
        try {
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                HashMap hm = new HashMap();
                if(page==null){
                    page = 1;
                }
                if(limit==null){
                    limit = 10;
                }
                int start = (page-1)*limit;
                //获取我给别人发的
                myGroupChatContentList = chatBasicBeanDao.findByNativeSql("select t.* from(" +
                    "SELECT coop_chat_basic.id as chat_id,from_userid as user_id,chat_content,voice_time,sys_user_rel.head_spicurl as logo,name," +
                    "chat_time,coop_chat_basic.chat_attach as attache_url,coop_chat_basic.picture_url as attache_spicurl," +
                    "coop_chat_basic.chat_type,coop_chat_basic.chat_lat,coop_chat_basic.chat_lng " +
                    " FROM coop_chat_basic LEFT JOIN sys_user ON coop_chat_basic.from_userid = sys_user.id " +
                    " left join sys_user_rel on sys_user_rel.user_id = sys_user.id " +
                    " WHERE coop_chat_basic.group_id ="+group_id+" ORDER BY coop_chat_basic.chat_time DESC " +
                    "LIMIT "+start+","+limit+") t ORDER BY t.chat_time ASC",ChatContent.class);
                returnMap.put("response", myGroupChatContentList);
                Iterator its=myGroupChatContentList.iterator();
                while(its.hasNext()){
                    ChatContent tea=(ChatContent)its.next();
                    tea.setChat_time(tea.getChat_time().substring(0, tea.getChat_time().indexOf(".")));
                    if(tea.getUser_id()==user_id){
                        tea.setComMess(false);//别人发的
                    }else{
                        tea.setComMess(true);//我发的
                    }
                    if(isone ==0){
                        try {
                            ageold = df.parse(tea.getChat_time()).getTime();
                            ageold=ageold+fivem;
                        } catch (Exception e) {
                            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            agenew= df.parse(tea.getChat_time()).getTime();
                            if(agenew < ageold){
                                //tea.setChat_time("");
                            }
                            ageold=agenew+fivem;
                        } catch (Exception e) {
                            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
                            e.printStackTrace();
                        }
                    }
                    isone++;
                }

                //消息变已读
                //消息消息全变已读 讨论组管理员标识
                chatBasicBeanDao.update("update ChatBasicBean c " +
                        " set c.is_read = 1 " +
                        " where c.group_id = "+group_id);
                //我的消息消息全变已读
                chatBasicBeanDao.update("update GroupChatIndexBean g " +
                        " set g.hasnews = 0 " +
                        " where g.user_id = "+user_id+" and g.group_id = "+group_id);
                result=1;
            }
        } catch (Exception e) {
            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            returnMap.put("result", result);
        }
        return returnMap;
    }

    @Override
    @GET
    @Path("/getMsgDetail")
    @Produces("application/json;charset=utf-8")
    public Map getMsgDetail(@QueryParam("chat_id")long chat_id,@Context HttpServletRequest request) {
        int result =-1;
        HashMap returnMap = new HashMap();
        ArrayList userList = new ArrayList();
        try {
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                List<SingleChatContent> teaList = chatBasicBeanDao.findByNativeSql("SELECT * FROM " +
                        " (SELECT coop_chat_basic.id as chat_id,to_userid as user_id,chat_content,voice_time,is_read,chat_time," +
                        " head_spicurl as logo,name,coop_chat_basic.chat_attach as attache_url,coop_chat_basic.picture_url as attache_spicurl," +
                        " coop_chat_basic.chat_type,coop_chat_basic.chat_lat,coop_chat_basic.chat_lng " +
                        " FROM coop_chat_basic LEFT JOIN sys_user ON sys_user.id = coop_chat_basic.from_userid " +
                        " left join sys_user_rel on sys_user_rel.user_id = sys_user.id "+
                        " WHERE coop_chat_basic.id="+chat_id+")as b",SingleChatContent.class);
                if(teaList!=null){
                    SingleChatContent tea = teaList.get(0);
                    tea.setComMess(true);
                    tea.setChat_time(tea.getChat_time().substring(0, 19));
                    userList.add(tea);
                    returnMap.put("response", userList);
                    //此条消息需置为已读
                    chatBasicBeanDao.update("update ChatBasicBean c " +
                            " set c.is_read =0 " +
                            " where c.id = "+chat_id);
                    result=1;
                }
            }
        } catch (Exception e) {
            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            returnMap.put("result", result);
        }
        return returnMap;
    }

    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/updatechatvoiceisread")
    public Map updatechatvoiceisread(@FormParam("chat_id")Long chat_id, @Context HttpServletRequest request) {
        int result = -1;
        HashMap returnMap = new HashMap();
        try {
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                if(chat_id!=null){
                    //消息变已读
                    chatBasicBeanDao.update("update ChatBasicBean c " +
                            " set c.is_read = 1 " +
                            " where c.id = " + chat_id);
                    result = 1;
                }
            }
        } catch (Exception e) {
            returnMap.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            returnMap.put("result", result);
        }
        return returnMap;
    }
}
class MyChatComparatorsec implements Comparator{

    MyChatComparatorsec(){}

    public int compare(Object o1,Object o2){
        if(o1 == null || o2 == null){
            return 1;
        }
        ChatContent told=(ChatContent)o1;
        ChatContent tnew=(ChatContent)o2;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long ageold = 0;
        long agenew = 0;
        try {
            ageold = df.parse(told.getChat_time()).getTime();
            agenew=df.parse(tnew.getChat_time()).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ageold >= agenew){
            return 1;
        }
        return -1;
    }
}
class MyChatListComparatorsec implements Comparator{

    MyChatListComparatorsec(){}

    public int compare(Object o1,Object o2){
        if(o1 == null || o2 == null){
            return 1;
        }
        ChatList told=(ChatList)o1;
        ChatList tnew=(ChatList)o2;
        String ageold = "";
        String agenew = "";
        ageold = told.getNeartime();
        agenew=tnew.getNeartime();
        int nr=agenew.compareTo(ageold);
        if(nr>0){
            return 1;
        }
        if(nr==0){
            return 0;
        }
        return -1;
    }
}
