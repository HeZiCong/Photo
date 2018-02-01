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
@Table(name = "t_photographer_label")
public class TPhotographerLabel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer labelid;

    private Integer photographerid;

    private Date createtime;

    private String status;

    
    
   
	public TPhotographerLabel(Integer labelid, Integer photographerid, Date createtime, String status) {
		super();
		this.labelid = labelid;
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

    

    public Integer getLabelid() {
		return labelid;
	}

	public void setLabelid(Integer labelid) {
		this.labelid = labelid;
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