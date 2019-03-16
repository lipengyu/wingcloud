package top.wingcloud.stream.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 词云map算子实现
 * In:source数据
 * Out:Tuple2<时间,商品类别>
 */
public class WordCloudMap implements MapFunction<String, Tuple2<Long, String>> {
    private static Logger logger = LoggerFactory.getLogger(WordCloudMap.class);

    @Override
    public Tuple2<Long, String> map(String value) throws Exception {
        JSONObject jsonObject = JSON.parseObject(value);
        String shoptype = jsonObject.getString("shoptype");
        String shoptime = jsonObject.getString("shoptime");
        Long time = 0L;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(shoptime);
            time = parse.getTime();
        } catch (ParseException e) {
            logger.error("时间解析异常--->time：" + time, e.getCause());
        }

        return new Tuple2<>(time, shoptype);
    }
}
