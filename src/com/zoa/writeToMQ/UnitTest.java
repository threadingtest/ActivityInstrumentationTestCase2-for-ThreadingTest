package com.zoa.writeToMQ;

import java.io.Closeable;
import java.io.IOException;

import org.zeromq.ZMQ;

public class UnitTest implements Closeable {	
	private String m_unitName;	
	private static ZMQ.Context context = null;
	private static ZMQ.Socket sendSocket = null;
	private static char ch = 0;
	private final int timeout = 10000;
	
	public UnitTest(String unitName)
	{
		m_unitName = unitName;
		startUnit();
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		sendEnd();
		unInitial();
		
	}
	
	private void startUnit()
	{
		initial();
		sendStart();
	}
	
	private void initial()
	{	
		context = ZMQ.context(1);
		sendSocket = context.socket(ZMQ.REQ);
		sendSocket.bind("tcp://*:15558");
		sendSocket.setReceiveTimeOut(timeout);
		sendSocket.setSendTimeOut(timeout);
	}
	
	private void sendStart()
	{
		do
		{
			byte[] recv = null;
			boolean sendSucceed = sendSocket.send(m_unitName + ch);
			if(!sendSucceed)
				continue;
			recv = sendSocket.recv();
			if(recv != null)
			{
				String str = new String(recv);
				int index = str.indexOf(" ");
				if(index >= 0)
				{
					String strName = str.substring(index + 1);
					if(strName.equals(m_unitName))
					{
						str = str.substring(0, index);
						WriteCond.getInstance().setTestcaseId(Integer.valueOf(str));
						WriteDD.getInstance().setTestcaseId(Integer.valueOf(str));
						WriteMCDC.getInstance().setTestcaseId(Integer.valueOf(str));
						return;
					}
				}
			}
//			break;
			sendSocket.close();
			context.close();
			context = ZMQ.context(1);
			sendSocket = context.socket(ZMQ.REQ);
			sendSocket.bind("tcp://*:15558");
			sendSocket.setReceiveTimeOut(timeout);	
			sendSocket.setSendTimeOut(timeout);
		}while(true);
	}
		
	private void sendEnd()
	{
		WriteCond.getInstance().setTestcaseId(0);
		WriteDD.getInstance().setTestcaseId(0);
		WriteMCDC.getInstance().setTestcaseId(0);
	}
	
	private void unInitial()
	{
		sendSocket.close();
		context.close();
	}
}