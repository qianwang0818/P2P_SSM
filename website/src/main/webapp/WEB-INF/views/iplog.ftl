<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
		<script type="text/javascript" src="/js/plugins-override.js"></script>
		<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script type="text/javascript">
			$(function(){
			    //日期格式化插件WdatePicker
				$(".beginDate,.endDate").click(function(){
				    WdatePicker();
				})

				//给查询按钮绑定点击事件
				$("#query").click(function(){
				    $("#currentPage").val(1);		//因为异步方式加载列表,页面不刷新,所以隐藏域的value不会自动变成1. 过大的页码可能会查不到数据
				    $("#searchForm").submit();
				})

				//回显筛选状态下拉框的状态. 当变为异步列表后,页面不会刷新,没有变化也就无需回显.
                //$("#state option[value=$qo.state}]").attr("selected","selected");

				//把列表内容变为Ajax方式请求HTML文本
                var searchForm = $("#searchForm");
                var gridBody = $("#gridBody");
                searchForm.ajaxForm(function(data){
                    gridBody.hide();
                    gridBody.html(data);
                    gridBody.show(500);
                });
                searchForm.submit();		//进入页面时执行一次异步加载列表,之后每次按查询按钮都会异步加载列表.
			});
		</script>
	</head>
	<body>
	
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<!-- 网页导航 -->
		<#assign currentNav="personal" />
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="ipLog" />
					<#include "common/leftmenu-tpl.ftl" />		
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<form action="/ipLog_ajax.do" name="searchForm" id="searchForm" class="form-inline" method="post">
						<input type="hidden" id="currentPage" name="currentPage" value="1" />
						<div class="form-group">
							<label>时间范围</label>
							<input type="text" class="form-control beginDate" name="beginDate" value='${(qo.beginDate?string("yyyy-MM-dd"))!""}'/>
						</div>
						<div class="form-group">
							<label></label>
							<input type="text" class="form-control endDate" name="endDate" value='${(qo.endDate?string("yyyy-MM-dd"))!""}'/>
						</div>
						<div class="form-group">
						    <label>状态</label>
						    <select class="form-control" name="state" id="state">
						    	<option value="-1">全部</option>
						    	<option value="0">登录失败</option>
						    	<option value="1">登录成功</option>
						    </select>
						</div>
						<div class="form-group">
							<button type="button" id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
						</div>
					</form>
					
					<div class="panel panel-default" style="margin-top: 20px;">
						<div class="panel-heading">
							登录日志
						</div>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>用户</th>
									<th>登录时间</th>
									<th>登录ip</th>
									<th>登录状态</th>
								</tr>
							</thead>
							<tbody id="gridBody">
								<#--非异步刷新时在本页面加载listData-->
								<#--<#list pageResult.listData as iplog>
									<tr>
										<td>${iplog.username}</td>
										<td>${iplog.loginTime ? string("yyyy-MM-dd HH:mm:ss")}</td>
								        <td>${iplog.ip}</td>
								        <td>${iplog.getStateDisplay()}</td>
									</tr>
								</#list>-->
							</tbody>
						</table>

						<#--分页栏-->
						<div id="page_container" style="text-align: center;">
							<ul id="pagination" class="pagination"></ul>
						</div>

					</div>
				</div>
			</div>
		</div>		
						
		<#include "common/footer-tpl.ftl" />
	</body>
</html>