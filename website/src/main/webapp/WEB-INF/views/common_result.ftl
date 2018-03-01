<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
        <script>
            var i = 5;
            <#if url??>
				setInterval("fun()", 1000);
				function fun() {
					if (i == 0) {
						window.location.href = "${url}";
						i=5;
						clearInterval(intervalid);
					}
					document.getElementById("secondId").innerHTML = i;
					i--;
				}
			</#if>
        </script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />

		<#--<#assign currentNav="personal" />-->
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />

		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#--<#assign currentMenu = "realAuth" />-->
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="el-tip-info">
						<h3>提示信息</h3>
						<p class="text-info">${msg!""}</p>
						<#if url??>
							<span id="secondId">5</span>秒后自动跳转
							<a href="${url}" class="alert-link">手动跳转</a>
						</#if>
					</div>
				</div>
			</div>
		</div>

		<#include "common/footer-tpl.ftl" />
	</body>
</html>