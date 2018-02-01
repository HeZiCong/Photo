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
@Table(name = "t_photographer_spots")
public class TPhotographerSpots {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer spotsid;

    private Integer photographerid;

    private Date createtime;

    private String status;

    
    

	public TPhotographerSpots(Integer spotsid, Integer photographerid, Date createtime, String status) {
		super();
		this.spotsid = spotsid;
		this.photographerid = photographerid;
		this.createtime = createtime;
		this.status = status;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  

    public Integer getSpotsid() {
		return spotsid;
	}

	public void setSpotsid(Integer spotsid) {
		this.spotsid = spotsid;
	}

	public Integer getPhotographerid() {
		return photographerid;
	}

	public void setPhotographerid(Integer photographerid) {
		this.photographerid = photographerid;
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