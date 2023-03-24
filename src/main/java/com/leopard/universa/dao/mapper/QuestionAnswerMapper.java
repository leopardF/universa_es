package com.leopard.universa.dao.mapper;

import com.leopard.universa.entity.QuestionAnswer;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 权限用户
 *
 * @author leopard
 */
@org.apache.ibatis.annotations.Mapper
public interface QuestionAnswerMapper extends Mapper<QuestionAnswer> {

    @Insert({
            "insert into question_answer (id, content_detail, ",
            "created_time, ginseng_info, ",
            "ginseng_level, ginseng_status, ",
            "have_company, level, ",
            "parent_id, platform_type, ",
            "publish_time, publisher, ",
            "service_button, service_flag, ",
            "service_tip, service_url, ",
            "source, title, updated_time, ",
            "type, content)",
            "values (#{id,jdbcType=INTEGER}, #{contentDetail,jdbcType=VARCHAR}, ",
            "#{createdTime,jdbcType=TIMESTAMP}, #{ginsengInfo,jdbcType=VARCHAR}, ",
            "#{ginsengLevel,jdbcType=INTEGER}, #{ginsengStatus,jdbcType=INTEGER}, ",
            "#{haveCompany,jdbcType=INTEGER}, #{level,jdbcType=INTEGER}, ",
            "#{parentId,jdbcType=INTEGER}, #{platformType,jdbcType=VARCHAR}, ",
            "#{publishTime,jdbcType=TIMESTAMP}, #{publisher,jdbcType=VARCHAR}, ",
            "#{serviceButton,jdbcType=VARCHAR}, #{serviceFlag,jdbcType=INTEGER}, ",
            "#{serviceTip,jdbcType=VARCHAR}, #{serviceUrl,jdbcType=VARCHAR}, ",
            "#{source,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, #{updatedTime,jdbcType=TIMESTAMP}, ",
            "#{type,jdbcType=INTEGER}, #{content,jdbcType=LONGVARCHAR})"
    })
    int insertData(QuestionAnswer record);

    @Select({
            "select",
            "id, content_detail, created_time, ginseng_info, ginseng_level, ginseng_status, ",
            "have_company, level, parent_id, platform_type, publish_time, publisher, service_button, ",
            "service_flag, service_tip, service_url, source, title, updated_time, type, content",
            "from question_answer",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results(id = "base_map", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "content_detail", property = "contentDetail"),
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "ginseng_info", property = "ginsengInfo"),
            @Result(column = "ginseng_level", property = "ginsengLevel"),
            @Result(column = "ginseng_status", property = "ginsengStatus"),
            @Result(column = "have_company", property = "haveCompany"),
            @Result(column = "level", property = "level"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "platform_type", property = "platformType"),
            @Result(column = "publish_time", property = "publishTime"),
            @Result(column = "publisher", property = "publisher"),
            @Result(column = "service_button", property = "serviceButton"),
            @Result(column = "service_flag", property = "serviceFlag"),
            @Result(column = "service_tip", property = "serviceTip"),
            @Result(column = "service_url", property = "serviceUrl"),
            @Result(column = "source", property = "source"),
            @Result(column = "title", property = "title"),
            @Result(column = "updated_time", property = "updatedTime"),
            @Result(column = "type", property = "type"),
            @Result(column = "content", property = "content")
    })
    QuestionAnswer findDetailQuestionAnswer(Integer id);

    @Select({
            "select",
            "id, content_detail, created_time, ginseng_info, ginseng_level, ginseng_status, ",
            "have_company, level, parent_id, platform_type, publish_time, publisher, service_button, ",
            "service_flag, service_tip, service_url, source, title, updated_time, type, content",
            "from question_answer",
            "where parent_id = #{parentId}  and ginseng_status = #{ginsengStatus}  ",
            " order by ginseng_status asc , have_company asc "
    })
    @ResultMap("base_map")
    public List<QuestionAnswer> findListQuestionAnswer(@Param("parentId") int parentId, @Param("ginsengStatus") int ginsengStatus);


    @Select({
            "<script>",
            "select",
            "id, content_detail, created_time, ginseng_info, ginseng_level, ginseng_status, ",
            "have_company, level, parent_id, platform_type, publish_time, publisher, service_button, ",
            "service_flag, service_tip, service_url, source, title, updated_time, type, content",
            "from question_answer",
            "where 1 = 1 ",
            " <if test = 'parentIds != null and parentIds.size != 0' > ",
            " and parent_id in ",
            "<foreach item=\"item\" collection=\"parentIds\" separator=\",\" open=\"(\" close=\")\" index=\"\">",
            " #{item , jdbcType=NUMERIC}",
            "</foreach>",
            "</if> ",
            "</script>"
    })
    @ResultMap("base_map")
    public List<QuestionAnswer> getRouteByParentId(@Param("parentIds") List<Integer> parentIds);


    @Select({
            "select",
            "id, content_detail, created_time, ginseng_info, ginseng_level, ginseng_status, ",
            "have_company, level, parent_id, platform_type, publish_time, publisher, service_button, ",
            "service_flag, service_tip, service_url, source, title, updated_time, type, content",
            "from question_answer"
    })
    @ResultMap("base_map")
    public List<QuestionAnswer> findAnswerAll();


}
