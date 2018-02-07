package cc.gzvtc.photographer.service;

import java.util.List;

import cc.gzvtc.common.service.IService;
import cc.gzvtc.model.TLevel;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
public interface ILevelService extends IService<TLevel> {

	List<TLevel> getAllLevel();

}
