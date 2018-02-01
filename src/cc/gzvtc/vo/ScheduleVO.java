package cc.gzvtc.vo;

import java.util.List;
import java.util.Map;

public class ScheduleVO {

	List<Map<String,Object>> attempt;

	List<Map<String,Object>> schedule;

	public List<Map<String, Object>> getAttempt() {
		return attempt;
	}

	public void setAttempt(List<Map<String, Object>> attempt) {
		this.attempt = attempt;
	}

	public List<Map<String, Object>> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Map<String, Object>> schedule) {
		this.schedule = schedule;
	}


	
}
