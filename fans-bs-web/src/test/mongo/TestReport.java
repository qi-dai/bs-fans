package mongo;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.domain.enu.PostLevel;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.enu.PostType;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PostImg;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by shengyanpeng on 2016/3/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-config-mongo.xml"})
public class TestReport {

    private static final Logger logger = LoggerFactory.getLogger(TestReport.class);
    public static final String POST_COLLECTION = "post";
    private static Gson PARSER = GsonUtil.getGson();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test() {
        Set<String> colls = this.mongoTemplate.getCollectionNames();
        for (String coll : colls) {
            System.out.println("CollectionName=" + coll);
        }
        DB db = this.mongoTemplate.getDb();
        System.out.println("db=" + db.toString());
    }

    @Test
     public void testUpsert(){
        String id = "56f2a808b1484132229c6f8c";

        ConcernUser concernUser = new ConcernUser();
        concernUser.setTime(new Date());
        concernUser.setConcern(1);
        concernUser.setUserCode(1234L);

        Update insert = new Update();
        Update.AddToSetBuilder builder = insert.addToSet("concernUsers");
        builder.each(JSON.parse(PARSER.toJson(concernUser)));

        Update update = new Update();
        update.set("concernUsers.$.concern",concernUser.getConcern());
        update.set("concernUsers.$.time", concernUser.getTime());

        Query  insertQuery = Query.query(Criteria.where("_id").is(id));
        Query updateQuery = Query.query(Criteria.where("_id").is(id).and("concernUsers.userCode").is(concernUser.getUserCode()));

         int reslut = this.mongoTemplate.updateFirst(updateQuery,update,POST_COLLECTION).getN();
         System.out.println(reslut);

        reslut = this.mongoTemplate.upsert(insertQuery,insert,POST_COLLECTION).getN();

        //mongoTemplate.findAndModify() 谁更好？
        //int reslut = this.mongoTemplate.getCollection(POST_COLLECTION).findAndModify().updateFirst(query, update,POST_COLLECTION).getN();
        System.out.println(reslut);
    }

    @Test
    public void createPost(){
        long startTime = System.currentTimeMillis();
        PostInfo postInfo = new PostInfo();
        createPost(postInfo);
        /*for(int i=0;i<10;i++){
            DBObject dbObject = new BasicDBObject();
            Map<String,Object> params = new HashMap<String, Object>(20);
            PostInfo.class
            //DBObject dbObject = (DBObject) JSON.parse(PARSER.toJson(postInfo));
            dbObject.putAll();
            int result = this.mongoTemplate.getCollection(POST_COLLECTION).insert(dbObject).getN();
            System.out.println(result);
        }*/
        System.out.println("cost:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void createPostByMap(){
        long startTime = System.currentTimeMillis();
        PostInfo postInfo = new PostInfo();
        createPost(postInfo);
        for(int i=0;i<10;i++){
            DBObject dbObject = new BasicDBObject();
            Map<String,Object> params = new HashMap<String, Object>(20);
            params.put("userCode",123456101);
            int result = this.mongoTemplate.getCollection(POST_COLLECTION).insert(dbObject).getN();
            System.out.println(result);
        }
        System.out.println("cost:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void updatePost(){
        String posdId = "56f268bf28459a384d1db612";
        Query query = Query.query(Criteria.where("_id").is(posdId));
        Update update = new Update();
        update.set("publishDate", new Date());

        System.out.println(this.mongoTemplate.upsert(query, update, POST_COLLECTION).getN());
    }

    @Test
    public void queryPost(){
        String posdId = "56f268bf28459a384d1db612";
        DBObject id = new BasicDBObject("_id",new ObjectId(posdId));
        DBObject keys = new BasicDBObject();
        setPostKeys(keys);
        DBObject dbObject = this.mongoTemplate.getCollection(POST_COLLECTION).findOne(id, keys);
        String dbString = JSON.serialize(dbObject);
        Date date = new Date();
        //date.dbObject.get("publishDate");
        //PostInfo postInfo = PARSER.fromJson(dbString, PostInfo.class);
        System.out.println(dbObject.get("publishDate"));
    }

    @Test
    public void queryPostByPage(){
        //DBObject query = new BasicDBObject("$size","praiseUsers");
        DBObject keys = new BasicDBObject();
        keys.put("title", 1);
        keys.put("createDate", 1);
        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(POST_COLLECTION).find(new BasicDBObject(),keys).skip(0).limit(10);
            while (cursor.hasNext()){
               DBObject dbObject =  cursor.next();
               System.out.println(JSON.serialize(dbObject));
            }
        } finally {
            if(null != cursor){
                cursor.close();
            }
        }

    }



    @Test
    public void  addReplyPostInfo(){


        String postId = "56f268bf28459a384d1db60c";
        ReplyPostInfo replyPostInfo = new ReplyPostInfo();
        replyPostInfo.setTitle("aa");
        replyPostInfo.setMedias(new ArrayList<String>());
        replyPostInfo.setContent("huifu");
        replyPostInfo.setReplyTime(new Date());

        Query query = Query.query(Criteria.where("_id").is(postId));
        Update update = new Update();
        Update.AddToSetBuilder bulider = update.addToSet("replyPostInfos");
        bulider.each(JSON.parse(PARSER.toJson(replyPostInfo)));
        this.mongoTemplate.updateFirst(query, update, POST_COLLECTION);
    }


    @Test
    public void queryReplyPostInfoByPage(){
        String postId = "56f268bf28459a384d1db60c";
        Query query = Query.query(Criteria.where("_id").is(postId));
        DBObject queryObject = new BasicDBObject("_id",new ObjectId(postId));
        DBObject keys = new BasicDBObject();
        keys.put("replyPostInfos", new BasicDBObject("$slice", new Integer[]{0, 3}));//new Integer[]{0,3}
        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(POST_COLLECTION).find(queryObject, keys);
            while (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                System.out.println(JSON.serialize(dbObject));
            }
        } finally {
            if(null != cursor){
                cursor.close();
            }
        }
    }

    @Test
    public void createIndex(){
        DBObject index = new BasicDBObject("replyPostInfos.replyTime",-1);
        this.mongoTemplate.getCollection(POST_COLLECTION).createIndex(index);
    }

    @Test
    public void updateReplyPostInfo(){
        Gson gson = new Gson();

        String postId = "56f1f3e52845769d003dd0d0";
        ReplyPostInfo replyPostInfo = new ReplyPostInfo();
        replyPostInfo.setTitle("sss");
        replyPostInfo.setContent("KKKKK");
        replyPostInfo.setReplyTime(new Date());

        Update update = new Update();
        update.set("replyPostInfos.$.content", replyPostInfo.getContent());
        update.set("replyPostInfos.$.replyTime", replyPostInfo.getReplyTime().getTime());
        Query query = Query.query(Criteria.where("_id").is(postId).and("replyPostInfos.title").is(replyPostInfo.getTitle()));

        this.mongoTemplate.updateFirst(query, update, POST_COLLECTION);
    }

    @Test
    public void deletePost(){
       /* Query query = Query.query(Criteria.where("_id").is(""));
        this.mongoTemplate.findAllAndRemove(query, PostInfo.class, POST_COLLECTION);*/
        String postId = "56f1f3e52845769d003dd0d0";
        DBObject dbObject = new BasicDBObject("_id",new ObjectId(postId));
         this.mongoTemplate.getCollection(POST_COLLECTION).remove(dbObject);
    }

    private void setPostKeys(DBObject keys){
        keys.put("title",1);
        keys.put("type",1);
        keys.put("content",1);
        keys.put("userCode",1);
        keys.put("imgs",1);
        keys.put("videos",1);
        keys.put("musics",1);
        keys.put("others",1);
        keys.put("createDate",1);
        keys.put("publishDate",1);
        keys.put("status",1);
        keys.put("level", 1);
    }
    @Test
    public void testCount(){
        String postId = "56f268bf28459a384d1db60c";
        DBObject object = new BasicDBObject("_id",new ObjectId(postId));
        DBObject keys = new BasicDBObject();
        keys.put("replyPostInfos.title",1);

        DBObject postObject = this.mongoTemplate.getCollection(POST_COLLECTION).findOne(object,keys);
        System.out.println(postObject);
        PostInfo postInfo = PARSER.fromJson(JSON.serialize(postObject), PostInfo.class);
        System.out.println(Long.valueOf(postInfo.getReplyPostInfos().size()));
    }
    private void createPost(PostInfo postInfo){

        postInfo.setTitle("测试帖子");
        postInfo.setType(PostType.TEXT_MESSAGE);
        postInfo.setContent("测试帖子的内容");
        postInfo.setUserCode(123456101L);
        PostImg img1 = new PostImg();
        img1.setIndex(1);
        img1.setImgUrl("http://y1.ifengimg.com/a/2016_13/d653ddbb945c9b0.jpg");
        PostImg img2 = new PostImg();
        img2.setIndex(2);
        img2.setImgUrl("http://y1.ifengimg.com/a/2016_13/d653ddbb945c9b0.jpg");
        List<PostImg> imgs = new ArrayList<PostImg>();
        imgs.add(img1);
        imgs.add(img2);
        postInfo.setImgs(imgs);
        postInfo.setVideos(null);
        postInfo.setMusics(null);
        postInfo.setOthers(null);
        postInfo.setCreateDate(new Date());
        postInfo.setPublishDate(new Date());
        postInfo.setStatus(PostStatus.NORMAL);
        postInfo.setLevel(PostLevel.COMMON);
        List<Integer> operatorList = new ArrayList<Integer>();
        operatorList.add(1111);
        operatorList.add(2222);
        postInfo.setOperatorList(operatorList);
    }
}
