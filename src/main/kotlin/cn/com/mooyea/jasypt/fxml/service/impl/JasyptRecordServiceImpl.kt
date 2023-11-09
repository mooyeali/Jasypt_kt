package cn.com.mooyea.jasypt.fxml.service.impl

import cn.com.mooyea.jasypt.annotations.Slf4k
import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.fxml.entity.JasyptRecordEntity
import cn.com.mooyea.jasypt.fxml.mapper.JasyptRecordEntityMapper
import cn.com.mooyea.jasypt.fxml.service.IJasyptRecordService
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.Resource

/**
 * JasyptRecordServiceImpl
 *
 * Copyright (C), 星期三,08,11月,2023
 *  * Info:
 *  *      Author       :    mooye
 *  *      Work_Email   :    lidy@skyvis.com.cn
 *  *      E-mail       :    mooyeali@yeah.net
 *  *      Date         :    2023/11/8
 *  *      Version      :    v_1.0.0
 *  *      Description  :    历史记录 Service 层实现类
 *  * History:
 *  *      Author      :      mooye
 *  *      Time        :      21:50 2023/11/8
 *  *      Version     :      v_1.0.0
 *  *      Description :      历史记录 Service 层实现类
 *  *
 *@author mooye
 *
 */
@Service
@Slf4k
class JasyptRecordServiceImpl: ServiceImpl<JasyptRecordEntityMapper, JasyptRecordEntity>(), IJasyptRecordService {
    @Resource
    lateinit var mapper: JasyptRecordEntityMapper

    override fun saveRecord(clearStr: String, saltStr: String, algorithmStr: String, cipherStr: String) {
        val entity = JasyptRecordEntity(UUID.randomUUID().toString().replace("-", ""), clearStr, saltStr, algorithmStr, cipherStr)
        if (this.save(entity)) {
            Slf4k.log.info("保存记录成功")
        }else{
            Slf4k.log.warn("保存记录失败")
        }
    }

    override fun queryRecord(clearStr: String?, saltStr: String?, algorithmStr: String?): List<JasyptRecordEntity> {
        var query = KtQueryChainWrapper(mapper, JasyptRecordEntity::class.java)
        Slf4k.log.info("clearStr:{}", clearStr)
        if (!clearStr.isNullOrEmpty()) {
            query = query.like(JasyptRecordEntity::cleartext, clearStr)
        }
        if (!saltStr.isNullOrEmpty()) {
            query = query.like(JasyptRecordEntity::salt, saltStr)
        }
        if (!algorithmStr.isNullOrEmpty()) {
            query = query.eq(JasyptRecordEntity::algorithm, algorithmStr)
        }
        return query.list()
    }

}
