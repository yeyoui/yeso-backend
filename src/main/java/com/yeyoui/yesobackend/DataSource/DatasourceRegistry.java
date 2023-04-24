package com.yeyoui.yesobackend.DataSource;

import com.yeyoui.yesobackend.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DatasourceRegistry {
    @Resource
    private UserDatasource userDatasource;
    @Resource
    private PostDatasource postDatasource;
    @Resource
    private PictureDataSource pictureDataSource;

    private Map<String,DataSource> typeDataSourceMap;


    @PostConstruct
    public void initMap(){
        typeDataSourceMap=new HashMap<String,DataSource>(){
            {
                put(SearchTypeEnum.POST.getValue(),postDatasource);
                put(SearchTypeEnum.USER.getValue(),userDatasource);
                put(SearchTypeEnum.PICTURE.getValue(),pictureDataSource);
            }};
    }
    public DataSource getDataSourceByType(String type){
        return typeDataSourceMap.get(type);
    }
}
