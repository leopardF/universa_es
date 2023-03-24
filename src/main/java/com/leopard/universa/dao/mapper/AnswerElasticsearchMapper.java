package com.leopard.universa.dao.mapper;

import com.leopard.universa.entity.QuestionAnswer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface AnswerElasticsearchMapper extends ElasticsearchRepository<QuestionAnswer, String> {

}
