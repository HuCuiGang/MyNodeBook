package com.hcg.search;

import com.hcg.search.Repository.ItemEsRepository;
import com.hcg.search.bean.ItemEs;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchApplicationTests {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private ItemEsRepository itemEsRepository;

	//创建索引和映射
	@Test
	public void indexTest() {
		//创建索引
		elasticsearchTemplate.createIndex(ItemEs.class);

		//创建映射，根据配置自动完成映射
		elasticsearchTemplate.putMapping(ItemEs.class);

	}
	//添加数据
	@Test
	public void add(){
		ItemEs itemEs = new ItemEs(4L,"创维","创维电视",2L,5000L,"sddsadasasd");
		itemEsRepository.save(itemEs);
	}
	//修改数据
	@Test
	public void update(){
		ItemEs itemEs = new ItemEs(4L,"创维","创维电视",2L,6000L,"sddsadasasd");
		itemEsRepository.save(itemEs);
	}
	//基础查询
	@Test
	public void testFind(){
		Iterable<ItemEs> itemEss = itemEsRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
		itemEss.forEach(itemEs -> System.out.println(itemEs));
	}
	//条件查询
	@Test
	public void testQuery(){
		itemEsRepository.findByPriceBetween(500,1500).forEach(itemEs -> {
			System.out.println(itemEs);
		});

		Pageable pageable = new PageRequest(0, 2);
		Page<ItemEs> itemEsPage = itemEsRepository.findByPriceBetween(500, 2000, pageable);
		System.out.println(itemEsPage.getTotalPages());
		System.out.println(itemEsPage.getTotalElements());

		itemEsPage.getContent().forEach(itemEs -> {
			System.out.println(itemEs);
		});
	}

	//高级查询
	@Test
	public void testBaseQuery(){
		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "手机");
		Iterable<ItemEs> search = itemEsRepository.search(matchQueryBuilder);
		search.forEach(System.out::println);
	}
	//原生elasticsearchTemplate查询
	@Test
	public void testBaseQuery2(){
		MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("title", "手机");
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(matchQuery);
		List<ItemEs> itemEs = elasticsearchTemplate.queryForList(nativeSearchQuery, ItemEs.class);

		itemEs.forEach(System.out::println);
	}
	//自定义查询
	@Test
	public void testNativeQuery(){
		//构建查询条件
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		//添加基本的分词查询
		nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title","手机"));
		//执行搜索,获取结果
		Page<ItemEs> page = itemEsRepository.search(nativeSearchQueryBuilder.build());

		System.out.println(page.getTotalElements());
		System.out.println(page.getTotalPages());

		page.forEach(System.out::println);
	}
	@Test
	public void testNativeQuery2(){
		//构建查询条件
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		//显示结果过滤
		nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","title","price"},null));
		//添加基本的分词查询
		nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title","手机"));
		//排序
		nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
		//分页
		nativeSearchQueryBuilder.withPageable(PageRequest.of(0,2));
		//执行搜索，获取结果
		Page<ItemEs> page = itemEsRepository.search(nativeSearchQueryBuilder.build());

		System.out.println(page.getTotalPages());
		System.out.println(page.getTotalElements());

		page.forEach(System.out::println);

	}

	//布尔组合
	@Test
	public void testNativeQuery3(){
		//构建查询条件
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		//显示结果过滤
		nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","title","price"},null));
		//添加布尔组合分词查询
		nativeSearchQueryBuilder.withQuery(
				QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title","手机"))
				.mustNot(QueryBuilders.matchQuery("title","华为"))
				/*.should(QueryBuilders.matchQuery("title","小米"))*/);

		//排序
		nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
		//分页
		nativeSearchQueryBuilder.withPageable(PageRequest.of(0,2));
		//执行搜索，获取结果
		Page<ItemEs> page = itemEsRepository.search(nativeSearchQueryBuilder.build());

		System.out.println(page.getTotalPages());
		System.out.println(page.getTotalElements());

		page.forEach(System.out::println);

	}
	//过滤条件查询
	@Test
	public void testNativeQuery4(){
		//构建查询条件
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		//显示结果过滤
		nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","cid","title","price"},null));
		//添加基本的分词查询
		/*nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());*/
		nativeSearchQueryBuilder.withQuery(boolQuery().must(QueryBuilders.matchQuery("title","手机电视"))
				/*.must(QueryBuilders.matchQuery("title","电视"))*/);
		//过滤条件查询
		nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("cid","2"));
		//排序
		nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
		//分页
		nativeSearchQueryBuilder.withPageable(PageRequest.of(0,2));
		//执行搜索，获取结果
		Page<ItemEs> page = itemEsRepository.search(nativeSearchQueryBuilder.build());

		System.out.println(page.getTotalPages());
		System.out.println(page.getTotalElements());

		page.forEach(System.out::println);

	}


	//聚合(查询分类数量)
	@Test
	public void testNativeQuery5(){
		//分桶
		//构建查询条件
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		//结果过滤，不显示任何结果
		nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));
		//1.添加分桶
		nativeSearchQueryBuilder.addAggregation(AggregationBuilders.range("price").field("price").addRange(500,2500).addRange(4000,5000));

		//2.拿到聚合结果
		AggregatedPage<ItemEs> aggregatedPage= (AggregatedPage<ItemEs>) itemEsRepository.search(nativeSearchQueryBuilder.build());
		//3.通过聚合名字获取对应聚合
		InternalRange internalRange = (InternalRange) aggregatedPage.getAggregation("price");
		//4.获取桶
		List<InternalRange.Bucket> buckets = internalRange.getBuckets();

		for (InternalRange.Bucket bucket:buckets) {
			//获取桶的名字（范围）
			System.out.println(bucket.getKey());
			//获取桶的值(数量)
			System.out.println(bucket.getDocCount());
		}

	}

	@Test
	public void testNativeQuery6(){
		//分桶
		//构建查询条件
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		//结果过滤，不显示任何结果
		nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));
		//1.添加分桶
		/*nativeSearchQueryBuilder.addAggregation(AggregationBuilders.range("price")
				.field("price").addRange(500,2500).addRange(4000,5000));*/
		//嵌套聚合
		nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("cidName").field("cid")
				.subAggregation(AggregationBuilders.avg("priceAvg").field("price")));

		//2.拿到聚合结果
		AggregatedPage<ItemEs> aggregatedPage= (AggregatedPage<ItemEs>) itemEsRepository.search(nativeSearchQueryBuilder.build());
		//3.通过聚合名字获取对应聚合
		LongTerms longTerms = (LongTerms) aggregatedPage.getAggregation("cidName");
		//4.获取桶
		List<LongTerms.Bucket> buckets = longTerms.getBuckets();

		for (LongTerms.Bucket bucket:buckets) {
			//获取桶的名字（范围）和数量
			System.out.println(bucket.getKey()+"共"+bucket.getDocCount());
			//获嵌套桶的值(平均值)
			InternalAvg internalAvg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");

			System.out.println("平均售价："+internalAvg.getValue());
		}
	}
}
