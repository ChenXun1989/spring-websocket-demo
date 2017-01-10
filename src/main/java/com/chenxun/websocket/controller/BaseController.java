/**
 * Project Name:spring-websocket-demo
 * File Name:BaseController.java
 * Package Name:com.chenxun.websocket.controller
 * Date:2017年1月10日下午4:53:33
 * Copyright (c) 2017, Shanghai Law Cloud Technology Co., Ltd. All Rights Reserved.
 *
*/

package com.chenxun.websocket.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.chenxun.websocket.entity.Message;
import com.chenxun.websocket.entity.User;
import com.chenxun.websocket.handler.DefaultWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ClassName:BaseController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年1月10日 下午4:53:33 <br/>
 * @author   陈勋
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@RestController
public class BaseController {
	
	@Resource
	DefaultWebSocketHandler handler;
	
	private static final String BROADCAST_FROM="system";
	
	private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";

	/**
	 * 广播
	 * @param text
	 * @throws IOException
	 */
	@RequestMapping(value = "broadcast")
	public void broadcast(String text) throws IOException {
		Message msg = new Message();
		msg.setDate(new Date());
		msg.setFrom(BROADCAST_FROM);
		msg.setText(text);
		ObjectMapper om=new ObjectMapper();
		om.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
		String json=om.writeValueAsString(msg);
		handler.broadcast(new TextMessage(json));
	}
	
	  /**
	   * 
	   * single:(发送单条信息). <br/>
	   *
	   * @author 陈勋
	   * @param text
	   * @throws IOException
	   * @since JDK 1.7
	   */
		@RequestMapping(value = "single")
		public void single(String text,String from,String to) throws IOException {
			Message msg = new Message();
			msg.setDate(new Date());
			msg.setFrom(from);
			msg.setTo(to);
			msg.setText(text);
			ObjectMapper om=new ObjectMapper();
			om.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
			handler.sendMessageToUser(to,
					new TextMessage(om.writeValueAsString(msg)));
			
		}
		/**
		 * 在线用户列表
		 * users:(这里用一句话描述这个方法的作用). <br/>
		 *
		 * @author 陈勋
		 * @return
		 * @since JDK 1.7
		 */
		@RequestMapping(value = "users")
		public Set<String> users(){
			return  DefaultWebSocketHandler.userSocketSessionMap.keySet();
		} 
		

}

