package com.chance.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chance.common.utils.JsonUtils;
import com.chance.mapper.TbItemParamItemMapper;
import com.chance.pojo.TbItemParamItem;
import com.chance.pojo.TbItemParamItemExample;
import com.chance.pojo.TbItemParamItemExample.Criteria;
import com.chance.service.ItemParamItemService;

/**
 * 获取具体商品规格参数的信息
 * 
 * @author Chance
 *
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public String getItemParamByItemId(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();

		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);

		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.size() == 0) {
			return "";
		}

		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		// 把规格json参数转换成java对象
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		
		// 转换成html格式
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for (Map m1 : jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
			sb.append("        </tr>\n");
			
			List<Map> list2 = (List<Map>) m1.get("params");
			
			for (Map m2 : list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
				sb.append("            <td>" + m2.get("v") + "</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}
}
