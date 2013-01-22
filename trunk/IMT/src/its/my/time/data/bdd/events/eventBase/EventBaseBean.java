package its.my.time.data.bdd.events.eventBase;

import java.util.Calendar;

public class EventBaseBean {

	private String details;
	private Calendar hDeb;
	private Calendar hFin;
	private int id;
	private String title;
	private long cid;
	private int typeId;
	private int detailsId;
	private boolean isAllDay;

	public EventBaseBean() {
		super();
		this.details = "";
		this.hDeb = null;
		this.hFin = null;
		this.id = -1;
		this.title = "";
		this.cid = -1;
		this.typeId = -1;
		this.detailsId = -1;
		this.isAllDay = false;
	}

	public boolean isAllDay() {
		return this.isAllDay;
	}

	public void setAllDay(boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

	public String getDetails() {
		return this.details;
	}

	public Calendar gethDeb() {
		return this.hDeb;
	}

	public Calendar gethFin() {
		return this.hFin;
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void sethDeb(Calendar hDeb) {
		this.hDeb = hDeb;
	}

	public void sethFin(Calendar hFin) {
		this.hFin = hFin;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCid() {
		return this.cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public int getTypeId() {
		return this.typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getDetailsId() {
		return this.detailsId;
	}

	public void setDetailsId(int detailsId) {
		this.detailsId = detailsId;
	}
}