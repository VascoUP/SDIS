package protocol.general;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

import connection.ReceivingSocekt;
import connection.SendingSocket;
import message.general.Message;

public abstract class Protocol {
	
	protected ReceivingSocekt receiver;
	protected SendingSocket sender;
	
	protected Message message;
	
	public Protocol(String rcv_addr, int rcv_port, String snd_addr, int snd_port) throws IOException {
		receiver = new ReceivingSocekt(rcv_addr, rcv_port);
		sender = new SendingSocket(snd_addr, snd_port);
	}
	
	public void socketTimeout(int t) {
		try {
			receiver.setTimeout(t);
		} catch (SocketException e) {
			System.out.println("Couldn't set a timeout");
		}
	}
	
	public byte[] receive() throws IOException {
		DatagramPacket packet = receiver.receive();
		return packet.getData();
	}
	
	public void send() throws IOException {
		byte[] m = message.getMessage();
		sender.send(m);
	}
	
	public void end_protocol() throws IOException {
		receiver.leave();
		sender.leave();
	}
	
	public Message getMessage() {
		return message;
	}
}
