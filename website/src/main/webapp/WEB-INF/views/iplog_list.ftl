<#--day08_07 异步请求的登录日志列表HTML文本-->
<#if pageResult?? && pageResult.listData?size &gt; 0 >
    <#list pageResult.listData as iplog>
        <tr>
            <td>${iplog.username}</td>
            <td>${iplog.loginTime ? string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>${iplog.ip}</td>
            <td>${iplog.getStateDisplay()}</td>
        </tr>
    </#list>
<#else>
	<tr>
        <td colspan="7" align="center">
            <p class="text-danger">没有找到日志</p>
        </td>
    </tr>
</#if>


<script type="text/javascript">
    $(function(){
        //重置分页条,否则下面一段设置分页的代码无法生效
        $("#page_container").empty().append($('<ul id="pagination" class="pagination"></ul>'));

        //设置分页条
        $("#pagination").twbsPagination({
            totalPages: ${pageResult.totalPage},
            currentPage:${pageResult.currentPage},
            initiateStartPageClick:false,       //在插件初始化时,触发点击事件,默认true. 感觉设置不设置都一样
            visiblePages: 10,
            onPageClick: function(event, page){
                $("#currentPage").val(page);
                $("#searchForm").submit();
            },
            startPage: ${pageResult.currentPage},
            first:"首页",
            prev:"上一页",
            next:"下一页",
            last:"尾页"
        })
    });
</script>