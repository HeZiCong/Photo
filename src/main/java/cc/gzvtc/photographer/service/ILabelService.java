package cc.gzvtc.photographer.service;

import java.util.List;

import cc.gzvtc.common.service.IService;
import cc.gzvtc.model.TLabel;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
public interface ILabelService extends IService<TLabel> {
	
	List<TLabel> getAllLabel();

}
