package com.e3.smsqueue.action;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.e3.smsqueue.H2DAO;
import com.e3.smsqueue.StoreType;
import com.e3.smsqueue.entity.JSonUtils;
import com.e3.smsqueue.entity.SMS;
import com.e3.smsqueue.entity.SMSStore;
import com.e3.smsqueue.entity.SMSStoreFactory;
import com.e3.smsqueue.smsutil.SmsStringUtils;
import com.e3.smsqueue.webutil.stripes.BaseActionBean;
import com.e3.smsqueue.webutil.stripes.JsonResolution;

@UrlBinding("/service/sms/{event}")
public class SMSAction extends BaseActionBean {
	private static final Logger log = Logger.getLogger(SMSAction.class
			.getName());
	private String receiver;
	private String content;
	private String smsBox;
	private String successMsg;
	private String requestId;
	private boolean returnInputPage = false;
	private SMSStore inStore = SMSStoreFactory.getStore(StoreType.IN_BOX);
	private SMSStore outStore = SMSStoreFactory.getStore(StoreType.OUT_BOX);

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSmsBox(String smsBox) {
		this.smsBox = smsBox;
	}

	public void setReturnInputPage(boolean returnInputPage) {
		this.returnInputPage = returnInputPage;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	@HandlesEvent("put")
	public Resolution put() throws IOException, InterruptedException {
		boolean flag = false;
		if (SmsStringUtils.isBlank(receiver) || SmsStringUtils.isBlank(content)
				|| SmsStringUtils.isValidSmsBox(smsBox)) {
			this.getContext().getResponse()
					.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		if (StoreType.isInStore(smsBox)) {
			flag = inStore.put(new SMS(receiver, null, content, requestId));
			return ok();
		} else {
			flag = outStore.put(new SMS(receiver, null, content, requestId));
			log.log(Level.INFO, "[put {0}] receiver:{1}  content:{2}",
					new String[] { flag ? "Success" : "Failure", receiver,
							content });
			H2DAO.updateStatus(receiver, "DeliveredToNetwork", requestId);

			return new StreamingResolution("text/x-json", new StringReader(
					"{\"status\":0}"));

		}
	}

	private Resolution ok() {
		this.getContext().getResponse().setStatus(HttpServletResponse.SC_OK);
		if (returnInputPage) {
			String tips = SmsStringUtils.isNotBlank(successMsg) ? "alert('"
					+ successMsg + "');" : "";
			return new StreamingResolution("text/html", new StringReader(
					"<html><body><script>" + tips
							+ "history.go(-1);</script></body></html>"));
		}
		return new StreamingResolution("text/x-json", new StringReader(
				SmsStringUtils.isBlank(successMsg) ? "{\"status\": \"OK\"}"
						: successMsg));
	}

	@HandlesEvent("get")
	public Resolution get() throws IOException {
		List<SMS> smsPackage;
		if (!SmsStringUtils.isValidSmsBox(smsBox)) {
			this.getContext().getResponse()
					.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		if (StoreType.isInStore(smsBox))
			smsPackage = inStore.getOnePackage();
		else
			smsPackage = outStore.getOnePackage();
		log.log(Level.INFO, "[get] retrive {0} sms from {1}", new Object[] {
				smsPackage.size(),
				this.getContext().getRequest().getRemoteAddr() });
		for (SMS sms : smsPackage) {
			H2DAO.updateStatus(sms.getReceiver(), "DeliveredToTerminal",
					sms.getRequestId());
		}

		return new JsonResolution(JSonUtils.toJSON(smsPackage));
	}

	@HandlesEvent("query")
	public Resolution query() throws IOException {
		if (SmsStringUtils.isBlank(receiver)) {
			this.getContext().getResponse()
					.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		return new StreamingResolution("text/x-json", new StringReader(
				"{\"status\":" + H2DAO.getSMSStatus(requestId) + "}"));
	}

	@HandlesEvent("clean")
	public Resolution clean() throws IOException {
		int size;
		if (SmsStringUtils.isValidSmsBox(smsBox)) {
			this.getContext().getResponse()
					.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		if (StoreType.isInStore(smsBox))
			size = inStore.clearQueue();
		else
			size = outStore.clearQueue();
		return new StreamingResolution("text/plain", new StringReader("" + size
				+ " sms Sending queue is cleared"));
	}

	@DefaultHandler
	@HandlesEvent("status")
	public Resolution status() throws IOException {
		log.log(Level.INFO, "[status] check status from {0}", this.getContext()
				.getRequest().getRemoteAddr());
		return ok();
	}

	@HandlesEvent("deliverystatus")
	public Resolution deliverystatus() throws IOException {
		log.log(Level.INFO, "[deliverystatus] check status from {0}", this
				.getContext().getRequest().getRemoteAddr());
		return new JsonResolution(JSonUtils.toJSON(H2DAO.getSMSStatus(requestId)));
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
