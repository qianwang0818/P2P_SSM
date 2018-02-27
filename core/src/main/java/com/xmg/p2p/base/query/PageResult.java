package com.xmg.p2p.base.query;

import java.util.ArrayList;
import java.util.List;

import com.xmg.p2p.base.constant.CommonConstant;
import lombok.Getter;

/**
 * 分页查询结果对象,等价于自定义pageBean
 * day03_10
 */

@SuppressWarnings("all")
@Getter
public class PageResult {

	private List listData;// 当前页的结果集数据:查询
	private Integer totalCount;// 总数据条数:查询

	private Integer currentPage = 1;
	private Integer pageSize = CommonConstant.PAGESIZE;

	private Integer prevPage;// 上一页
	private Integer nextPage;// 下一页
	private Integer totalPage;// 总页数

	public PageResult(String s) {

	}

    // 如果总数据条数为0,返回一个空集
	public static PageResult empty(Integer pageSize) {
		return new PageResult(new ArrayList<>(), 0, 1, pageSize);
	}

	/**避免前端分页条报错: 如果查询到零记录,会调用empty方法,因此currentPage=1. 此时TotalPage=0 就会有逻辑问题*/
	public int getTotalPage() {
		return totalPage == 0 ? 1 : totalPage;
	}

	/**
	 * @param listData
	 * @param totalCount
	 * @param currentPage
	 * @param pageSize
	 */
	public PageResult(List listData, Integer totalCount, Integer currentPage, Integer pageSize) {
		this.listData = listData;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		// ----------------------------------------
		this.totalPage = (this.totalCount % this.pageSize == 0) ? (this.totalCount / this.pageSize) : (this.totalCount / this.pageSize + 1);

		this.prevPage = this.currentPage - 1 >= 1 ? this.currentPage - 1 : 1;
		this.nextPage = this.currentPage + 1 <= this.totalPage ? this.currentPage + 1 : this.totalPage;
	}
}
