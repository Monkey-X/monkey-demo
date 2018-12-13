package com.example.xlc.monkey.utils;

/**
 * @author:xlc
 * @date:2018/12/11
 * @descirbe:设置和获取android设备的唯一标识
 *
 * 方案1 APP首次使用时，创建UUID，并保存到sp中，以后再次使用时，直接从sharePreference取出来即可
 * 方案2 APP首次使用时，创建UUID，并保存到SD卡中，以后再次使用时，直接从SD卡取出来即可
 * 方案3 根据硬件标识来创建唯一的数据，imei + Android_id + serial + 硬件uuid
 * AndroidId 无需权限，极个别设备获取不到数据或者得到错误数据
 * serial  无需权限，极个别设备获取不到数据
 * IMEI 需要权限
 * Build.BOARD 主板名称，无需权限，同型号设备相同
 * Build.BRAND 产商名称，无需权限，同型号设备相同
 * Build.HARDWARE 硬件名称，无需权限，同型号设备相同
 */
public class DeviceIdUtil {
}
