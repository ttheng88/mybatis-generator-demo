package com.demo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description monitor_task
 * @author liangsy
 * @date 2020-08-04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monitor_task")
public class MonitorTask implements Serializable {
    /**
     * 主键，唯一，自增类型
     */
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    /**
     * 结果输出间隔
     */
    @Column(name = "interval")
    private Integer interval;

    /**
     * 任务状态，0：未启动，1：处理中，2：已完成
     */
    @Column(name = "status")
    private Integer status;

    public static final String ID = "id";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String INTERVAL = "interval";

    public static final String STATUS = "status";

    private static final long serialVersionUID = 1L;
}