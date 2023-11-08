package cn.com.mooyea.jasypt.fxml.service

import cn.com.mooyea.jasypt.fxml.entity.JasyptRecordEntity
import com.baomidou.mybatisplus.extension.service.IService

/**
 * IJasyptRecordService
 *
 * Copyright (C), 星期三,08,11月,2023
 *  * Info:
 *  *      Author       :    mooye
 *  *      Work_Email   :    lidy@skyvis.com.cn
 *  *      E-mail       :    mooyeali@yeah.net
 *  *      Date         :    2023/11/8
 *  *      Version      :    v_1.0.0
 *  *      Description  :    历史记录 service 层接口
 *  * History:
 *  *      Author      :      mooye
 *  *      Time        :      21:49 2023/11/8
 *  *      Version     :      v_1.0.0
 *  *      Description :      历史记录 service 层接口
 *  *
 *@author mooye
 *
 */
interface IJasyptRecordService: IService<JasyptRecordEntity> {
    /**
     * 保存历史记录
     *
     * @param clearStr 明文
     * @param saltStr 盐值
     * @param algorithmStr 加密算法
     * @param cipherStr 密文
     */
    fun saveRecord(clearStr: String, saltStr: String, algorithmStr: String, cipherStr: String)

    /**
     * 查询历史记录
     *
     * @param clearStr 明文
     * @param saltStr 盐值
     * @param algorithmStr 加密算法
     * @return 历史记录
     */
    fun queryRecord(clearStr: String?, saltStr: String?, algorithmStr: String?): List<JasyptRecordEntity>

}
