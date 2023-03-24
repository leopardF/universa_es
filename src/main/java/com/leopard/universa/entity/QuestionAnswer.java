package com.leopard.universa.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leopard
 */
@Document(indexName = "universa_question")
@Data
public class QuestionAnswer implements Serializable {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String contentDetail;

    private Date createdTime;

    @Field(type = FieldType.Text)
    private String ginsengInfo;

    @Field(type = FieldType.Integer)
    private Integer ginsengLevel;

    @Field(type = FieldType.Integer)
    private Integer ginsengStatus;

    @Field(type = FieldType.Integer)
    private Integer haveCompany;

    @Field(type = FieldType.Integer)
    private Integer level;

    @Field(type = FieldType.Integer)
    private Integer parentId;

    @Field(type = FieldType.Text)
    private String platformType;

    private Date publishTime;

    @Field(type = FieldType.Text)
    private String publisher;

    @Field(type = FieldType.Text)
    private String serviceButton;

    @Field(type = FieldType.Integer)
    private Integer serviceFlag;

    @Field(type = FieldType.Text)
    private String serviceTip;

    @Field(type = FieldType.Text)
    private String serviceUrl;

    @Field(type = FieldType.Text)
    private String source;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    private Date updatedTime;

    @Field(type = FieldType.Integer)
    private Integer type;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

}