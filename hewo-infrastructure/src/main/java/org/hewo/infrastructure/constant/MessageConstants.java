package org.hewo.infrastructure.constant;

import java.util.HashMap;
import java.util.Map;

public class MessageConstants {
    public static final Map<Integer, String> CODE_MAP = new HashMap<>();

    public MessageConstants() {
    }

    static {
        CODE_MAP.put(0, "success");
        CODE_MAP.put(10001, "服务器内部错误");
        CODE_MAP.put(10002, "服务器内部错误，与数据库有关的错误");
        CODE_MAP.put(10003, "NULL值错误");
        CODE_MAP.put(10101, "参数检验不通过");
        CODE_MAP.put(10102, "参数不能为空");
        CODE_MAP.put(10103, "用户不正确");
        CODE_MAP.put(10004, "没有提供TOKEN");
        CODE_MAP.put(10005, "TOKEN不正确");
        CODE_MAP.put(10006, "TOKEN过期失效");
        CODE_MAP.put(20001, "批量操作失败");
        CODE_MAP.put(10105, "不支持上传该文件类型");
        CODE_MAP.put(10107, "审核失败");
        CODE_MAP.put(10108, "反审核失败");
        CODE_MAP.put(10109, "作废失败");
        CODE_MAP.put(10106, "上传文件失败");
    }
}
