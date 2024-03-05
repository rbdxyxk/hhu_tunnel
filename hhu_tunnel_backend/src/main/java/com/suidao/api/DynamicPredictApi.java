package com.suidao.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suidao.base.Result;
import com.suidao.base.SqlConf;
import com.suidao.pojo.*;
import com.suidao.service.DynamicTableService;
import com.suidao.service.MonitorDynamicTablenameService;
import com.suidao.service.RealYuzhiService;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/dynamic")
public class DynamicPredictApi {
    @Resource
    MonitorDynamicTablenameService monitorDynamicTablenameService;
    @Resource
    DynamicTableService dynamicTableService;
    @Resource
    RealYuzhiService realYuzhiService;
    //根据前端传过来的字段生成拟合曲线，获得预测日期的值
    @PostMapping ("/predict")
    public Result predictResult(@RequestBody PredictQueryEntity predictQueryEntity){
        //只有方法为4才能执行
        if (predictQueryEntity.getMethod() != 4)
            return Result.fail("只有拟合法可以执行");
        List<Map> predictDatas = new ArrayList<>();
        double divisor = 10000000;
        Long suidaoId = Long.valueOf(predictQueryEntity.getSuidaoId().toString());
        Long firstIndexId = Long.valueOf(predictQueryEntity.getFirstIndexId().toString());
        Long secondIndexId = Long.valueOf(predictQueryEntity.getSecondIndexId().toString());
        Long itemIndxId = Long.valueOf(predictQueryEntity.getItemIndexId().toString());
        String cdwz = predictQueryEntity.getCdwz();
        //首先根据type判断是检测还是监测
        if (predictQueryEntity.getType() == 0){
            //根据字段获取对应时间点的数据
            //根据itemId来获取对应的数据表
            QueryWrapper<MonitorDynamicTablename> monitorDynamicTableQueryWrapper = new QueryWrapper<>();
//        dynamicTableQueryWrapper.eq(SqlConf.firstIndexId, map.get(SysConf.firstIndexId));
//        dynamicTableQueryWrapper.eq(SqlConf.secondIndexId, map.get(SysConf.secondIndexId));
            monitorDynamicTableQueryWrapper.eq(SqlConf.itemId, predictQueryEntity.getItemIndexId());
            MonitorDynamicTablename dynamicTable = monitorDynamicTablenameService.getOne(monitorDynamicTableQueryWrapper);
            if (dynamicTable == null) {
                return Result.fail("该选项暂无数据");
            }
            //数据库选择字段 suidaoId,firstIndexId,secondIndexId,itemIndexId, prop,beginTime,endTime
            predictDatas = dynamicTableService.getPredictDatas(dynamicTable.getTableName(),suidaoId,firstIndexId,secondIndexId,itemIndxId,predictQueryEntity.getBeginTime(),predictQueryEntity.getEndTime(),predictQueryEntity.getProp(),cdwz);
//            return getData(dynamicTable, current, limit, map);
        }
        else {
            // type = 1 是检测项目
            //根据itemId来获取对应的数据表
            QueryWrapper<JianceDynamicTable> dynamicTableQueryWrapper = new QueryWrapper<>();
//        dynamicTableQueryWrapper.eq(SqlConf.firstIndexId, map.get("firstIndexId"));
            dynamicTableQueryWrapper.eq(SqlConf.itemId, predictQueryEntity.getItemIndexId());
            JianceDynamicTable jianceDynamicTable = dynamicTableService.getOne(dynamicTableQueryWrapper);
            if (jianceDynamicTable == null) {
                return Result.fail("该选项暂无数据");
            }

            predictDatas = dynamicTableService.getPredictDatas(jianceDynamicTable.getTableName(),suidaoId,firstIndexId,secondIndexId,itemIndxId,predictQueryEntity.getBeginTime(),predictQueryEntity.getEndTime(),predictQueryEntity.getProp(),cdwz);
        }
        // predictDatas  data y   rq x (localDateTime)
        //将所有rq都转换成时间戳, data是实测表主键，还需要找到实测值
        if (predictDatas == null || predictDatas.size() == 0)
            return Result.fail("暂无数据");
        for (Map map : predictDatas) {
            LocalDateTime rq = (LocalDateTime) map.get("rq");
            Instant instant = rq.atZone(ZoneId.systemDefault()).toInstant();
            long timestamp = instant.getEpochSecond();
            map.replace("rq",(timestamp * 1.0) / divisor );
            Long id = Long.valueOf(map.get("data").toString());
            //根据id去真实值和阈值表中查找数据
            RealYuzhi yuzhi = realYuzhiService.getById(id);
            //获取真实值，如果有很多就选第一个
            if (yuzhi.getRealDataStr() != null){
                Double data = Double.valueOf(yuzhi.getRealDataStr().split(",")[0]);
                map.replace("data",data);
            }
            else {
                map.replace("data",0.0);
            }
        }
        //参数单变量函数  多项式函数
        ParametricUnivariateFunction function = new PolynomialFunction.Parametric();
        //按所需次数依次为 常数项、1次项、二次项......
        double[] guess = {0,1,2,3};

        // 初始化多项式
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);

        // 添加数据点  可以设置个点权重，默认平均权重
        WeightedObservedPoints observedPoints = new WeightedObservedPoints();
        for (Map map : predictDatas) {
            observedPoints.add((double) map.get("rq"), (double) map.get("data"));
        }
//        List<WeightedObservedPoint> weightedObservedPoints = observedPoints.toList();
        //拟合结果:依次为 常数项、1次项、二次项.......
        double[] best = fitter.fit(observedPoints.toList());
        // 曲线 f(x) = a0 + a1 * x + a2 * x^2
        double a0 = best[0],a1 = best[1],a2 = best[2];
        Fx fx = new Fx( a0,a1,a2);
        //获取目标预测日期并且往后再延迟一个月的时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(predictQueryEntity.getPredictTime(),formatter);
        LocalDateTime endDate = startDate.plus(1, ChronoUnit.MONTHS); // 计算结束日期（一个月后）

        List<LocalDateTime> datesWithinMonth = new ArrayList<>();
        LocalDateTime currentDate = startDate;
        List<Double> timeList = new ArrayList<>();
        while (!currentDate.isAfter(endDate)) {
            datesWithinMonth.add(currentDate);
            Instant i = currentDate.atZone(ZoneId.systemDefault()).toInstant();
            timeList.add((i.getEpochSecond() * 1.0) / divisor);
            currentDate = currentDate.plusDays(1); // 递增日期
        }

        //结果列表
        List<Double> funcResult = new ArrayList<>(timeList.size());
        List<String> specificTimeList = new ArrayList<>(timeList.size());
        for (Double time : timeList) {
            double re = fx.cal(time);
            funcResult.add(re);
        }
        for (LocalDateTime localDateTime : datesWithinMonth) {
            specificTimeList.add(formatter.format(localDateTime));
        }
        HashMap<String, List> result = new HashMap<>();
        result.put("timeList",specificTimeList);
        result.put("re",funcResult);
        return Result.ok(result);
    }


}

