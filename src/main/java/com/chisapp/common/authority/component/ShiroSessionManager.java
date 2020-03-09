package com.chisapp.common.authority.component;

import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ShiroSessionManager {

	private final String REDIS_SESSION_KEY = "repeatLoginUser";
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 单点登录
	 * 此方法将当前登录账户与已经登录状态的账户进行匹配
	 * 如果匹配成功 则将已登录账户的 Session ID 存入到重复登录记录
	 * @param subject
	 */
	public void singleLogin(Subject subject) {
		User currentUser = (User) subject.getPrincipal();
		List<Session> authenticatedSessions = getAuthenticatedSessions();

		for(Session session : authenticatedSessions) {
			Subject authenticatedSubject = new Subject.Builder().session(session).buildSubject();
			User authenticatedUser = (User) authenticatedSubject.getPrincipal();

			if(currentUser.getId().equals(authenticatedUser.getId())) {
				if(!subject.getSession().getId().equals(session.getId())) {
					stringRedisTemplate.opsForSet().add(REDIS_SESSION_KEY, session.getId().toString());
				}
			}

		}

	}

	/**
	 * 判断用户是否重复登录
	 * @param subject
	 * @return
	 */
	public Boolean isRepeatLogin (Subject subject) {
		// 获取当前用户的 session ID
		String currentSessionId = subject.getSession().getId().toString();
		return stringRedisTemplate.opsForSet().isMember(REDIS_SESSION_KEY, currentSessionId);
	}

	/**
	 * 清除用户重复登录状态
	 * @param subject
	 */
	public void removeRepeatLogin (Subject subject) {
		// 获取当前用户的 session ID
		String currentSessionId = subject.getSession().getId().toString();
		// 从强制下线记录中将该用户删除
		stringRedisTemplate.opsForSet().remove(REDIS_SESSION_KEY, currentSessionId);
		// 删除当前 session
		getDefaultSessionManager().getSessionDAO().delete(subject.getSession());
	}

	/**
	 * 获取 DefaultSecurityManager
	 * @return
	 */
	private DefaultSecurityManager getDefaultSecurityManager() {
		return (DefaultSecurityManager) SecurityUtils.getSecurityManager();
	}

	/**
	 * 获取 DefaultSessionManager
	 * @return
	 */
	private DefaultSessionManager getDefaultSessionManager() {
		return (DefaultSessionManager)getDefaultSecurityManager().getSessionManager();
	}

	/**
	 * 获取 已经登陆的 Session 集合
	 * @return
	 */
	private List<Session> getAuthenticatedSessions() {
		 Collection<Session> activeSessions = getDefaultSessionManager().getSessionDAO().getActiveSessions();
		 List<Session> authenticatedSessions = new ArrayList<Session>();

		 for(Session session : activeSessions) {
			 Subject subject = new Subject.Builder().session(session).buildSubject();
			 if(subject.isAuthenticated()) {
				 authenticatedSessions.add(session);
			 }
		 }

		return authenticatedSessions;
	}

}
