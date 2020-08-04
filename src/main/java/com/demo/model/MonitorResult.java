package com.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description monitor_result
 * @author liangsy
 * @date 2020-08-04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monitor_result")
public class MonitorResult implements Serializable {
    /**
     * 主键，唯一，自增类型
     */
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 监测任务id
     */
    @Column(name = "task_id")
    private Integer task_id;

    /**
     * 监测站id
     */
    @Column(name = "station_id")
    private Integer station_id;

    /**
     * 解算坐标X
     */
    @Column(name = "x")
    private BigDecimal x;

    /**
     * 解算坐标Y
     */
    @Column(name = "y")
    private BigDecimal y;

    /**
     * 解算坐标Z
     */
    @Column(name = "z")
    private BigDecimal z;

    /**
     * 解算时间
     */
    @Column(name = "time")
    private Date time;

    public static final String ID = "id";

    public static final String TASK_ID = "task_id";

    public static final String STATION_ID = "station_id";

    public static final String X = "x";

    public static final String Y = "y";

    public static final String Z = "z";

    public static final String TIME = "time";

    private static final long serialVersionUID = 1L;
}