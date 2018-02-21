<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- html <head>标签部分  -->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>蓝源Eloan-P2P平台(系统管理平台)</title>
	<#include "../common/header.ftl"/>
	<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$('#pagination').twbsPagination({
				totalPages : ${pageResult.totalPage},
				startPage : ${pageResult.currentPage},
				visiblePages : 10,
				first : "首页",
				prev : "上一页",
				next : "下一页",
				last : "尾页",
				onPageClick : function(event, page) {
					$("#currentPage").val(page);
					$("#searchForm").submit();
				}
			});
			
			//分组菜单的点击事件,查询分组下的明细
			$(".group_item").click(function(){
				//$("#currentPage").val(1);
				$("#parentId").val($(this).data("dataid"));	//把值放到form的隐藏input中提交查询
				$("#searchForm").submit();
			});
			
			//拿到Form中隐藏input的parentId值,后面两处使用
			var parentIdValue=$("#parentId").val();

            //页面刷新的时候,回显parentid
			if(parentIdValue){
				$(".group_item[data-dataid="+parentIdValue+"]").closest("li").addClass("active");
			}

			//分组名称,用于增加or修改模态框的显示
            var parentName = $("#systemDictionary_group_detail li.active a span").text();

			//增加数据字典明细按钮的点击事件
			$("#addSystemDictionaryItemBtn").click(function(){
				if(parentIdValue){
				    $("#editForm")[0].reset();
					$("#editFormParentId").val(parentIdValue);
                    $("#editFormParentName").val(parentName);
					$("#systemDictionaryItemModal").modal("show");
				}else{
					$.messager.popup("请先选择一个分组!");
				}
			});
			
			//修改or保存Form变成AjaxForm
            $("#editForm").ajaxForm(function(){
                $.messager.confirm("提示","保存成功",function(){
                    $("#searchForm").submit();
                });
            });
            //模态框的保存按钮点击事件
            $("#saveBtn").click(function(){
                $("#editForm").submit();
            });
			
			//修改按钮的点击事件
			$(".edit_Btn").click(function(){
				var json=$(this).data("json");
				$("#systemDictionaryId").val(json.id);
				$("#editFormParentId").val(json.parentId);
                $("#editFormParentName").val(parentName);
				$("#title").val(json.title);
				$("#sequence").val(json.sequence);
				$("#systemDictionaryItemModal").modal("show");
			});

            //为模态框最后一个输入框(顺序输入框)绑定键盘事件,提交表单
            $("#sequence").keydown(function(e){
                if(e.keyCode == 13){	//回车
                    $("#saveBtn").click()			//提交表单
                }
            });
			
		});
		</script>
</head>
<body>
	<div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#assign currentMenu="systemDictionaryItem" />
				<#include "../common/menu.ftl" />
			</div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>数据字典明细管理</h3>
				</div>
				<div class="col-sm-12">
					<!-- 提交分页的表单 -->
					<form id="searchForm" class="form-inline" method="post" action="/systemDictionaryItem_list.do">
						<input type="hidden" id="currentPage" name="currentPage" value="${(qo.currentPage)!1}"/>
						<input type="hidden" id="parentId" name="parentId" value='${(qo.parentId)!""}' />
						<div class="form-group">
						    <label>关键字</label>
						    <input class="form-control" type="text" name="keyword" value="${(qo.keyword!'')}">
						</div>
						<div class="form-group">
							<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
							<a href="javascript:void(-1);" class="btn btn-success" id="addSystemDictionaryItemBtn">添加数据字典明细</a>
						</div>
					</form>
					<div class="row"  style="margin-top:20px;">
						<div class="col-sm-3">
							<ul id="menu" class="list-group">
								<li class="list-group-item">
									<a href="#" data-toggle="collapse" data-target="#systemDictionary_group_detail"><span>数据字典分组</span></a>
									<ul class="in" id="systemDictionary_group_detail">
										<#list systemDictionaryGroups as systemDictionary><!-- id="pg_${systemDictionary.id}" -->
										   <li><a class="group_item" data-dataid="${systemDictionary.id}" href="#"><span>${systemDictionary.title}</span></a></li>
										</#list>
									</ul>
								</li>
							</ul>
						</div>
						<div class="col-sm-9">
							<table class="table">
								<thead>
									<tr>
										<th class="col-sm-5">名称</th>
										<th class="col-sm-4">序列</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<#list pageResult.listData as systemDictionaryItem>
									<tr>
										<td>${systemDictionaryItem.title}</td>
										<td>${systemDictionaryItem.sequence!""}</td>
										<td>
											<a href="javascript:void(-1);" class="edit_Btn" data-json='${systemDictionaryItem.jsonString}'>修改</a> &nbsp;
										</td>
									</tr>
								</#list>
								</tbody>
							</table>
							
							<div style="text-align: center;">
								<ul id="pagination" class="pagination"></ul>
							</div>
				
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="systemDictionaryItemModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">编辑/增加</h4>
	      </div>
	      <div class="modal-body">
	       	  <form id="editForm" class="form-horizontal" method="post" action="systemDictionaryItem_update.do" style="margin: -3px 118px">
				    <input id="systemDictionaryId" type="hidden" name="id" value="" />
			    	<input type="hidden" id="editFormParentId" name="parentId" value="" />
				   	<div class="form-group">
					    <label class="col-sm-3 control-label">分组</label>
					    <div class="col-sm-6">
							<#--这个input不提交,只是显示分组名字,真正提交的是上面隐藏input的parentId-->
					    	<input class="form-control" id="editFormParentName" disabled>
					    </div>
					</div>
				   	<div class="form-group">
					    <label class="col-sm-3 control-label">名称</label>
					    <div class="col-sm-6">
					    	<input type="text" class="form-control" id="title" name="title" placeholder="字典值名称">
					    </div>
					</div>
					<div class="form-group">
					    <label class="col-sm-3 control-label">顺序</label>
					    <div class="col-sm-6">
					    	<input type="text" class="form-control" id="sequence" name="sequence" placeholder="字典值显示顺序">
					    </div>
					</div>
			   </form>
		  </div>
	      <div class="modal-footer">
	      	<a href="javascript:void(0);" class="btn btn-success" id="saveBtn" aria-hidden="true">保存</a>
		    <a href="javascript:void(0);" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>