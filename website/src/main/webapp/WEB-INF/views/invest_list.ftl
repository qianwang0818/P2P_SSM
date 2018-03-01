<#if pageResult.listData?size &gt; 0 >
	<#list pageResult.listData as bidRequest>
		<tr>
			<td>${bidRequest.createUser.username}</td>
			<td>${bidRequest.title}</td>
			<td class="text-info">${bidRequest.currentRate}%</td>
			<td class="text-info">${bidRequest.bidRequestAmount}</td>
			<td>${bidRequest.returnTypeDisplay}</td>
			<td>
				<div class="">
					${bidRequest.persent} %
				</div>
			</td>
			<td><a class="btn btn-danger btn-sm"
				href="/borrow_info.do?id=${bidRequest.id}">查看</a></td>
		</tr>
	</#list>
<#else>
	<tr>
		<td colspan="7" align="center">
			<p class="text-danger">目前没有符合要求的标</p>
		</td>
	</tr>
</#if>

<script type="text/javascript">
	$(function(){
        //重置分页条,否则下面一段设置分页的代码无法生效
		$("#page_container").empty().append($('<ul id="pagination" class="pagination"></ul>'));
		//设置分页条
		$("#pagination").twbsPagination({
			totalPages:${pageResult.totalPage},
			currentPage:${pageResult.currentPage},
			initiateStartPageClick:false,
			onPageClick : function(event, page) {
				$("#currentPage").val(page);
				$("#searchForm").submit();
			}
		});
	});
</script>