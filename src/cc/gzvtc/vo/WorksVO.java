package cc.gzvtc.vo;

import java.util.List;

import cc.gzvtc.model.TAttachment;

public class WorksVO {
	private Integer id;
	
	private String photographer;
	
	private String spots;
	
	private String createTime;
	
	private String worksTitle;
	
	private String content;
	
	private List<TAttachment> imgs;
	
	private String path;

	public String getPhotographer() {
		return photographer;
	}

	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}

	public String getSpots() {
		return spots;
	}

	public void setSpots(String spots) {
		this.spots = spots;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getWorksTitle() {
		return worksTitle;
	}

	public void setWorksTitle(String worksTitle) {
		this.worksTitle = worksTitle;
	}

	public List<TAttachment> getImgs() {
		return imgs;
	}

	public void setImgs(List<TAttachment> imgs) {
		this.imgs = imgs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
