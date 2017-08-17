package com.sirier.dao;

import com.sirier.domain.WorkOrderManage;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * IWorkOrderDao的实现类,但是不能implementIWorkOrderDao这个接口,约定如此
 * Created by Sirierx on 2017/8/17.
 */

public class IWorkOrderDaoImpl {
    @PersistenceContext //专门在dao中注入管理类的注解
    private EntityManager entityManager;

    /**
     * 按条件查询索引库,查询方式为HibernateSearch,故使用的是其api
     */
    public Page<WorkOrderManage> pageQueryByConditionSearch(
            Pageable pageRequest, String conditionName, String conditionValue) {
        // lucene 索引分页查询分为三种
        // 其一精确词条查询--->QueryParser
        // 其二模糊查询-->WildcardQuery
        // 其三组合条件查询-->BooleanQuery
        try {
            //1-获取全文检索manager,需要注入sessionFactory/entityManager
            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
                    .getFullTextEntityManager(entityManager);
            //2-精确查询,需要指定分词器对象-->指定ik分词器
            QueryParser queryParser = new QueryParser(Version.LUCENE_31, conditionName, new
                    IKAnalyzer());

            //3-完全匹配
            Query query1 = queryParser.parse(conditionValue);
            //4-模糊匹配 需要封装Term对象,传入条件,通配字符是** 不是%%
            WildcardQuery query2 = new WildcardQuery(new Term(conditionName, "*" + conditionValue
                    + "*"));
            //5-组合查询
            BooleanQuery booleanQuery = new BooleanQuery();
            booleanQuery.add(query1, BooleanClause.Occur.SHOULD);// should 表示 或者关系 or
            booleanQuery.add(query2, BooleanClause.Occur.SHOULD);// should 表示 或者关系 or

            //6-创建全文检索Query,指定查询的方式
            FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(booleanQuery,
                    WorkOrderManage.class);
            //7-查询总结果集个数
            int totalCounts = fullTextQuery.getResultSize();

            //8-设置分页属性,进行结果集的分页设置
            fullTextQuery.setFirstResult(pageRequest.getOffset());// 起始记录数
            fullTextQuery.setMaxResults(pageRequest.getPageSize());// 每页显示记录数

            //9-正式获取结果集数据
            List<WorkOrderManage> resultList = fullTextQuery.getResultList();// rows

            //10封装到page对象-->通过接口PageImpl接口,把数据,条件,total封装成对象
            Page<WorkOrderManage> pageData = new PageImpl<WorkOrderManage>(resultList, pageRequest,
                    totalCounts);
            return pageData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("索引查询失败" + e);
        }
    }
}