	<!-- 
								�õ����ǵ�  pageBean �е� ��Ʒ����Ϣ
						 -->
						<s:iterator var="p" value="pageBean.list">
						<li>
							<!--  ��Ʒ��������Ϣ�Ĳ�ѯ���� -->
							<a href="${pageContext.request.contextPath}/product_findByPid.action?pid=<s:property value="#p.pid"/>">
								<img src="${pageContext.request.contextPath}/<s:property value="#p.image"/>" width="170" height="170"  style="display: inline-block;">
								   
								<span style='color:green'>
								 <s:property value="#p.pname"/>
								</span>
								 
								<span class="price">
									�̳Ǽۣ� ��<s:property value="#p.shop_price"/>
								</span>
								 
							</a>
						</li>