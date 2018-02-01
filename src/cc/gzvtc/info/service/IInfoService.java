package cc.gzvtc.info.service;

import java.util.List;

import cc.gzvtc.common.service.IService;
import cc.gzvtc.model.TInfomation;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
public interface IInfoService extends IService<TInfomation> {
	
	List<TInfomation> getAllInfo();
}
