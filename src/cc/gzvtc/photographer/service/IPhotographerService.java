package cc.gzvtc.photographer.service;

import java.text.ParseException;
import java.util.List;

import cc.gzvtc.common.service.IService;
import cc.gzvtc.model.TPhotographer;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
public interface IPhotographerService extends IService<TPhotographer> {

	List<String> selectByStartEnd(String start, String end) throws IllegalArgumentException, ParseException;
}
