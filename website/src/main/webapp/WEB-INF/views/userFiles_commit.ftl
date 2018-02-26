<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script type="text/javascript">
			$(function(){
			    /*把表单变为AjaxForm*/
				$("#editForm").ajaxForm(function(){
					window.location.reload();
				});
				/*确定提交按钮点击事件*/
				$("#submitFileType").click(function(){
					$("#editForm").submit();
				});
			})
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl"/>
		<#assign currentNav="personal" />
		<#include "common/navbar-tpl.ftl" />

		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="userFile"/>
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-heading">
							用户认证文件信息
						</div>
					</div>
					<div class="row">
					  <form method="POST" action="/userFile_selectType.do" id="editForm">

				    <#--每一个待选类型的图片-->
					<#list userFiles as userFile>
					<div class="col-sm-6 col-md-4">
					<div class="thumbnail">
					  <img src="${userFile.file}" />
					  <div class="caption">
						<p>
							<input type="hidden" name="ids" value="${userFile.id}" />
							<select class="form-control" name="fileTypes" style="width: 180px" autocomplate="off">

								<#--材料类型下拉框-->
								<#list fileTypes as item>
									<option value="${item.id}">${item.title}</option>
								</#list>

							</select>
						</p>
					  </div>
					</div>
					</div>
					</#list>

					  </form>
					</div>
					<div class="row">
						<a href="javascript:;" id="submitFileType" class="btn btn-success">确定提交</a>
					</div>
				</div>
			</div>
		</div>		
		<#include "common/footer-tpl.ftl" />		
	</body>
</html>