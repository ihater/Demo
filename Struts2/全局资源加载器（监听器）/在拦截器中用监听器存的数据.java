	
//改良2:改良1方案中每次操作都要重新加载当前登录人的可操作资源全信息，每次查询，该操作将成为系统瓶颈
	//改良当前登录人资源加载方式（登录时加载，并将其放入登录人信息中）
	

	//当前拦截器对象由struts创建，因此具有自动装配功能（struts-spring-plugin.jar）
	private ResEbi resEbi;
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;

	public String intercept(ActionInvocation invocation) throws Exception {

		//1.获取本次操作
		//2.判断本次操作是否是被拦截操作
		//3.从session中获取当前登录人信息
		//4.获取当前登录人可执行的所有操作（资源-角色-员工）
		//5.判断当前登录人对应的所有可操作资源中是否包含有本次操作
		

		String actionName  = invocation.getProxy().getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		


		String allRes = ServletActionContext.getServletContext().getAttribute("allRes").toString();			在这里用到了监听器 存的数据
		if(!allRes.contains(allName)){
			return invocation.invoke();
		}
		




		

		//对于登录用户对应的可操作资源在每次登录过程中，均保持不变
		//可以考虑进行一次性加载工作，放入指定范围后，每次使用直接获取		这里是优化之后的，我们登录后用户每次请求都要查询
		//查询时机：登录时查询						所以，我们把用户所拥有的权限，在登录的时候就查询
		//范围：session							放入到 session 中
		//loginEm.getResAll即为当前登录人可进行的所有资源数据字符串.
										我们已经把整个Emp对象放到session
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.EMP_LOGIN_USER_OBJECT_NAME);	

		if(loginEm.getResAll().contains(allName)){					查看这个用户是否拥有操作这个请求的权限
			return invocation.invoke();					getResAll 是我们在 beam 添加的 辅助视图值
		}								存的是我们  该 用户 允许操作的 请求字符串
		
		throw new AppException("对不起，请不要进行非法操作，权限不足！");
	}