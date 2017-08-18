<div class="pagination">
    <ul>



    <#if page.number!=0>
        <li>
            <a href="article?action=queryall&row=${page.size}&cur=0">首页</a>
        </li>
    </#if>

			

    <#if page.number!=0>
        <li>

            <a href="article?action=queryall&row=${page.size}&cur=${page.number-1}">前一页</a>

        </li>
    </#if>
    <#list 0..page.totalPages-1 as i >
        <li>

            <a href="article?action=queryall&row=${page.size}&cur=${i}">${i+1}</a>

        </li>
    </#list>



        <#if page.number!=page.totalPages-1>


    	<li>
			
				<a href="article?action=queryall&row=${page.size}&cur=${page.number+1}">下一页</a>
        </li>
        </#if>
        <#if page.number!=page.totalPages-1>
    	<li>
			
                <a href="article?action=queryall&row=${page.size}&cur=${page.totalPages-1}">尾页</a>
			

        </li>
        </#if>


    </ul>
</div>