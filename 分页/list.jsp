	<!-- 
								拿到我们的  pageBean 中的 商品的信息
						 -->
						<s:iterator var="p" value="pageBean.list">
						<li>
							<!--  商品的详情信息的查询请求 -->
							<a href="${pageContext.request.contextPath}/product_findByPid.action?pid=<s:property value="#p.pid"/>">
								<img src="${pageContext.request.contextPath}/<s:property value="#p.image"/>" width="170" height="170"  style="display: inline-block;">
								   
								<span style='color:green'>
								 <s:property value="#p.pname"/>
								</span>
								 
								<span class="price">
									商城价： ￥<s:property value="#p.shop_price"/>
								</span>
								 
							</a>
						</li>