<div id="contentwrapper">
	<div class="main_content">
		<#include "top.ftl">

		
		<div class="row-fluid">
			<div class="span12">
				<h3 class="heading">快来灌水</h3>

				
					<div class="alert alert-error">
						<a class="close" data-dismiss="alert">×</a>
						<strong>操作信息:

						

						<#if user??>
							${info}
                           <#else>
							   欢迎游客
						</#if>

						



						</strong>
						
					</div>
					<#if user??>


					<div class="btn-group sepH_b">
						<button data-toggle="dropdown" class="btn dropdown-toggle" >
							行数 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">


							<li><a href="article?action=queryall&row=5&cur=${page.number}">默认5行</a></li>
							<li><a href="article?action=queryall&row=10&cur=${page.number}">每页10行</a></li>
							<li><a href="article?action=queryall&row=2&cur=${page.number}">每页2行</a></li>
						</ul>
					</div>

					</#if>

				


				<table class="table table-bordered table-striped table_vam"
					id="dt_gal">
					<thead>
						<tr>

							<th class="table_checkbox">序号</th>
							<th style="width: 50px">发布人</th>
							<th style="width: 100px">主帖标题</th>
							<th style="width: 420px">主帖内容</th>
							<th style="width: 60px">发布日期</th>
							<th style="width: 60px">操作</th>
						</tr>
					</thead>
					<tbody>
					<#list page.content as d>
								<tr>

									<td>${d_index+1}</td>
									<td><a href="/upload/.jpg"
										
										title="" class="cbox_single thumbnail">

			 <img src="user?action=pic&id=${d.bbsuser.userid}"
											alt="" style="height: 50px; width: 50px" />

									</a>
									
									
									
									</td>
									<td>
									${d.title}
									
									<a
										href=""></a>
									</td>
									<td>${d.content}</td>
									<td>${d.datetime}</td>
									<td>
											
										<!-- 没登陆，游客 uid=0 -->
												
											<!-- 锚点传值 -->
											<#--<a href="" title="灌水" data-toggle="modal"-->
											<#--id="myp" data-backdrop="static"-->
											<#--onclick="rshow(${data.id},${uid},${data.user.id});">-->
												 <#--<i class="icon-eye-open"></i>-->
											<#---->
									<#--</a>-->
									<#if !user??>
									    <#assign  uid=999/>
									<#else >
									    <#assign  uid=user.userid/>
									</#if>
                                        <a href="#rshow" title="灌水" data-toggle="modal"
                                           id="myp" data-backdrop="static"
                                           onclick="rshow(${d.id},${uid},${d.bbsuser.userid})">

                                            <i class="icon-eye-open"></i>

                                        </a>

											
										
											<!-- 是本人贴可以删除和修改 -->
					             <#if user?? && user.userid==d.bbsuser.userid>
                                            <a
                                                    href="article?action=delz&id=${d.id}"
                                                    title="删除本帖"><i class="icon-trash"></i></a>

								 </#if>





									</td>
										
								</tr>
				</#list>

					</tbody>
				</table>


			</div>
		</div>
		
			<#include "page.ftl">
		

	</div>


</div>