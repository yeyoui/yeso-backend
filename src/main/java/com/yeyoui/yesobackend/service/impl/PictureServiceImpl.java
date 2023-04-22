package com.yeyoui.yesobackend.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeyoui.yesobackend.common.ErrorCode;
import com.yeyoui.yesobackend.exception.BusinessException;
import com.yeyoui.yesobackend.model.entity.Picture;
import com.yeyoui.yesobackend.service.PictureService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> listPicture(String searchText, long pageNum, long pageSize) {
        long current=(pageNum-1)*pageSize;
        //无参数查询
        if(StringUtils.isBlank(searchText)) searchText = "sky";
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%d", searchText, current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Elements elements = doc.select(".iuscp.isv");
        ArrayList<Picture> pictures = new ArrayList<>();
        long picCnt=0;
        for (Element element : elements) {
            //获取图片地址
            if(++picCnt>pageSize) break;;
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl=(String)map.get("murl");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            pictures.add(new Picture(title, murl));
        }
        //封装Page对象
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictures);
        return picturePage;
    }
}
