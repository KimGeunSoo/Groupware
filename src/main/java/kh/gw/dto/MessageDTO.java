package kh.gw.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MessageDTO {
	private int msg_seq;
	private String msg_sender;
	private String msg_receiver;
	private Date msg_send_date;
	private Date msg_receive_date;
	private String msg_title;
	private String msg_contents;
	private String msg_sender_name;
	private String msg_receiver_name;
	private List<MultipartFile> attfiles;
	private String msg_receive_date_str;
	private String msg_sender_date_str;
	
	public MessageDTO() {}

	public MessageDTO(int msg_seq, String msg_sender, String msg_receiver, Date msg_send_date, Date msg_receive_date,
			String msg_title, String msg_contents, String msg_sender_name, String msg_receiver_name,
			List<MultipartFile> attfiles, String msg_receive_date_str, String msg_sender_date_str) {
		super();
		this.msg_seq = msg_seq;
		this.msg_sender = msg_sender;
		this.msg_receiver = msg_receiver;
		this.msg_send_date = msg_send_date;
		this.msg_receive_date = msg_receive_date;
		this.msg_title = msg_title;
		this.msg_contents = msg_contents;
		this.msg_sender_name = msg_sender_name;
		this.msg_receiver_name = msg_receiver_name;
		this.attfiles = attfiles;
		this.msg_receive_date_str = msg_receive_date_str;
		this.msg_sender_date_str = msg_sender_date_str;
	}

	public int getMsg_seq() {
		return msg_seq;
	}

	public void setMsg_seq(int msg_seq) {
		this.msg_seq = msg_seq;
	}

	public String getMsg_sender() {
		return msg_sender;
	}

	public void setMsg_sender(String msg_sender) {
		this.msg_sender = msg_sender;
	}

	public String getMsg_receiver() {
		return msg_receiver;
	}

	public void setMsg_receiver(String msg_receiver) {
		this.msg_receiver = msg_receiver;
	}

	public Date getMsg_send_date() {
		return msg_send_date;
	}

	public void setMsg_send_date(Date msg_send_date) {
		this.msg_send_date = msg_send_date;
	}

	public Date getMsg_receive_date() {
		return msg_receive_date;
	}

	public void setMsg_receive_date(Date msg_receive_date) {
		this.msg_receive_date = msg_receive_date;
	}

	public String getMsg_title() {
		return msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

	public String getMsg_contents() {
		return msg_contents;
	}

	public void setMsg_contents(String msg_contents) {
		this.msg_contents = msg_contents;
	}

	public String getMsg_sender_name() {
		return msg_sender_name;
	}

	public void setMsg_sender_name(String msg_sender_name) {
		this.msg_sender_name = msg_sender_name;
	}

	public String getMsg_receiver_name() {
		return msg_receiver_name;
	}

	public void setMsg_receiver_name(String msg_receiver_name) {
		this.msg_receiver_name = msg_receiver_name;
	}

	public List<MultipartFile> getAttfiles() {
		return attfiles;
	}

	public void setAttfiles(List<MultipartFile> attfiles) {
		this.attfiles = attfiles;
	}

	public String getMsg_receive_date_str() {
		return msg_receive_date_str;
	}

	public void setMsg_receive_date_str(String msg_receive_date_str) {
		this.msg_receive_date_str = msg_receive_date_str;
	}

	public String getMsg_sender_date_str() {
		return msg_sender_date_str;
	}

	public void setMsg_sender_date_str(String msg_sender_date_str) {
		this.msg_sender_date_str = msg_sender_date_str;
	}

	
	
}
