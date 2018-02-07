package cc.gzvtc.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Table(name = "t_photographer_level")
public class TPhotographerLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer levelid;

    private Integer photographer;

    private Date createtime;

    private String status;

    
    
	public TPhotographerLevel(Integer levelid, Integer photographer, Date createtime, String status) {
		super();
		this.levelid = levelid;
		this.photographer = photographer;
		this.createtime = createtime;
		this.status = status;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public Integer getLevelid() {
		return levelid;
	}

	public void setLevelid(Integer levelid) {
		this.levelid = levelid;
	}

	public Integer getPhotographer() {
		return photographer;
	}

	public void setPhotographer(Integer photographer) {
		this.photographer = photographer;
	}

	public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}