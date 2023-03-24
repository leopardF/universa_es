package com.leopard.universa.service;

import com.alibaba.fastjson.JSONObject;
import com.leopard.universa.config.EsConfig;
import com.leopard.universa.config.constant.Response;
import com.leopard.universa.dao.mapper.AnswerElasticsearchMapper;
import com.leopard.universa.dao.mapper.QuestionAnswerMapper;
import com.leopard.universa.entity.QuestionAnswer;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 智能问答
 *
 * @author Tencent_Go
 */
@Service
@Slf4j
public class QuestionAnswerService {


    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Autowired
    private AnswerElasticsearchMapper answerElasticsearchMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private RestHighLevelClient client;

    /**
     * 添加问答数据
     *
     * @param answer
     * @return
     */
    public Response<Boolean> addAnswer(QuestionAnswer answer) {
        //注：这里没做ES和mysql数据同步操作，所以调用ES的save方法，只是在ES库中添加
        //为了完成两边数据同步，所以同时引用插入，实际开发不推荐这样写
        questionAnswerMapper.insertData(answer);
        answerElasticsearchMapper.save(answer);

        return Response.success(true);
    }

    /**
     * 查找问答数据，并高亮显示
     * （ES 6.X 版本前方式）
     *
     * @param content
     * @return
     */
    public Response<List<QuestionAnswer>> findAnswerByTitle(String content) {

        // 定义高亮字段
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("title").preTags("<span>").postTags("</span>");
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("content").preTags("<span>").postTags("</span>");
        // 构建查询内容
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(content);
        // 查询的字段
        queryBuilder.field("title").field("content");

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
                .withHighlightFields(titleField, contentField).build();
        long count = elasticsearchRestTemplate.count(searchQuery, QuestionAnswer.class);
        System.out.println("系统查询个数：--》" + count);
        if (count == 0) {
            return Response.success(new ArrayList<>());
        }

        Iterable<QuestionAnswer> searchResult = answerElasticsearchMapper.search(queryBuilder);
        Iterator<QuestionAnswer> iterator = searchResult.iterator();
        List<QuestionAnswer> list = new ArrayList<QuestionAnswer>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        System.out.println("查询到总个数：" + list.size());
        return Response.success(list);
    }


    /**
     * 查找问答数据，并高亮显示
     * （ES 7.X 版本后方式）
     *
     * @param content
     * @return
     */
    public Response<List<QuestionAnswer>> findAnswerByTitle2(String content) {

        List<QuestionAnswer> esProductTOS = new ArrayList<>();
        //1.构建检索条件
        SearchRequest searchRequest = new SearchRequest();
        //2.指定要检索的索引库
        searchRequest.indices("universa_question");
        //3.指定检索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.query(QueryBuilders.multiMatchQuery(content, "content"));

        //4.结果高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(true); //如果该属性中有多个关键字 则都高亮
        highlightBuilder.field("content");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        sourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, EsConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        org.elasticsearch.search.SearchHit[] hits = response.getHits().getHits();
        for (org.elasticsearch.search.SearchHit hit : hits) {
            //如果不做高亮，则可以直接转为json，然后转为对象
//            String value = hit.getSourceAsString();
//            ESProductTO esProductTO = JSON.parseObject(value, ESProductTO.class);
            //解析高亮字段
            //获取当前命中的对象的高亮的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            log.info("json-hit: " + JSONObject.toJSONString(hit));


            //查询精确后的匹配分数值
            float score = hit.getScore();
            log.info("hit score : " + score);


            HighlightField productName = highlightFields.get("content");
            String newName = "";
            if (productName != null) {
                //获取该高亮字段的高亮信息
                Text[] fragments = productName.getFragments();
                //将前缀、关键词、后缀进行拼接
                for (Text fragment : fragments) {
                    newName += fragment;
                }
            }
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //将高亮后的值替换掉旧值
            sourceAsMap.put("content", newName);
            String json = JSONObject.toJSONString(sourceAsMap);
            QuestionAnswer esProductTO = JSONObject.parseObject(json, QuestionAnswer.class);
            esProductTOS.add(esProductTO);
        }
        return Response.success(esProductTOS);
    }


    /**
     * 更新问答时间，同步已有数据到ES
     */
    public Response updateAllAnswerForTime() {

        List<QuestionAnswer> answerList = questionAnswerMapper.findAnswerAll();
        for (QuestionAnswer answer : answerList) {
            answer.setCreatedTime(new Date());
            answerElasticsearchMapper.save(answer);
        }
        return Response.success(1);
    }

    /**
     * 预设推荐
     *
     * @return JSONArray
     */
    public Response defaultRecommend() {

        //TODO 临时数据结构，后期存入缓存或是数据库
        QuestionAnswer detailQuestionAnswer = questionAnswerMapper.findDetailQuestionAnswer(11);

        return Response.success(detailQuestionAnswer);
    }


}
