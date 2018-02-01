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
@Table(name = "t_works")
public class TWorks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    
    private String path;

    private Integer spotsid;

    private Integer photographerid;

    private Date createtime;

    private String status;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}