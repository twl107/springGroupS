package com.spring.springGroupS.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chatserver")
public class ChatServer {
	// 채팅서버에 접속한 클라이언트 목록들을 저장하기위해 생성
	private static List<Session> userList = new ArrayList<Session>();
	
	private void print(String msg) {
		System.out.printf("[%tT] %s\n", Calendar.getInstance(), msg);
	}
	
	// 채팅서버 최초 접속시 수행
	@OnOpen
	public void handleOpen(Session session) {
		print("클라이언트 연결 : sessionID : " + session.getId());
		userList.add(session);
	}
	
	// 클라이언트에서 접속하면 무조건 처리(메세지/접속/종료)한다.
	@OnMessage
	public void handleMessage(String msg, Session session) {
		// 로그인시 msg  : '1#유저명#메세지@색상'으로 넘어온다.
		// 대화시에는 msg : '2#유저명:메세지@색상'으로 넘어온다.
		// 종료시는  msg : '3#유저명#' 으로 넘어온다.
		
		// msg에 '2#행복천#안녕'또는, '2#행복천#안녕@FF0000'이 넘어왔다고 가장하자.
		
		int index = msg.indexOf("#", 2);	// 
		String no = msg.substring(0, 1);	// 1:최초접속, 2:일반메세지, 3:종료
		String user = msg.substring(2, index);	// 접속 유저 아이디
		String txt = msg.substring(index + 1);	// 전달한 메세지
		
		if(txt.indexOf("@") != -1) {	// 메세지안에 @가 포함되어 있으면 색깔을 변경할수 있도록 처리
			txt = txt.substring(0, txt.lastIndexOf("@")); 
			String chatColor = msg.substring(msg.lastIndexOf("@")+1);
			txt = " <font color=\""+chatColor+"\">"+txt+"</font>";
		}
		
		if (no.equals("1")) { 
			for (Session s : userList) {	
				if (s != session) {
					try {	 
						s.getBasicRemote().sendText("1#" + user + "#");
					} catch (IOException e) {e.printStackTrace();}
				}
			}
		}
		else if(no.equals("2")) {  
			for (Session s : userList) {
				if (s != session) {
					try {
						s.getBasicRemote().sendText("2#" + user + ":" + txt);
					} catch (IOException e) {e.printStackTrace();}
				}
			} 
		}
		else if (no.equals("3")) { 
			for (Session s : userList) {
				if (s != session) { 
					try {
						s.getBasicRemote().sendText("3#" + user + "#");
					} catch (IOException e) {e.printStackTrace();}
				}
			}
			userList.remove(session);	
		}
		
	}

	// 접속 종료시 수행
	@OnClose
	public void handleClose(Session session) {
		System.out.println("Websocket Close");
		userList.remove(session);
	}
	
	// 에러 발생시 수행
	@OnError
	public void handleError(Throwable t) {
		System.out.println("웹소켓 전송 에러입니다.");
	}
}