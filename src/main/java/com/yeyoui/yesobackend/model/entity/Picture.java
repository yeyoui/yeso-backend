package com.yeyoui.yesobackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * 图片转发
 */
@Data
@AllArgsConstructor
@NonNull
public class Picture {
    private String title;
    private String url;
}
