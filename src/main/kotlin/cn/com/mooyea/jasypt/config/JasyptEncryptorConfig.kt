package cn.com.mooyea.jasypt.config

import cn.com.mooyea.jasypt.annotations.Slf4k
import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@Slf4k
class JasyptEncryptorConfig {
    @Value("\${jasypt.encryptor.password:#{'JASYpt'}}")
    private lateinit var password: String

    @Value("\${jasypt.encryptor.algorithm:#{'PBEWITHHMACSHA512ANDAES_256'}}")
    private lateinit var algorithm: String

    @Value("\${jasypt.encryptor.keyObtentionIterations:#{'1000'}}")
    private lateinit var keyObtentionIterations: String

    @Value("\${jasypt.encryptor.poolSize:#{'1'}}")
    private lateinit var poolSize: String

    @Value("\${jasypt.encryptor.providerName:#{'SunJCE'}}")
    private lateinit var providerName: String

    @Value("\${jasypt.encryptor.satGeneratorClassName:#{'org.jasypt.salt.RandomSaltGenerator'}}")
    private lateinit var saltGeneratorClassName: String

    @Value("\${jasypt.encryptor.ivGeneratorClassName:#{'org.jasypt.iv.RandomIvGenerator'}}")
    private lateinit var ivGeneratorClassName: String

    @Value("\${jasypt.encryptor.stringOutputType:#{'base64'}}")
    private lateinit var stringOutputType: String
    @Bean(name = ["customEncryption"])
    fun customEncryption(): StringEncryptor {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        config.setPassword(password)
        config.algorithm = algorithm
        config.setKeyObtentionIterations(keyObtentionIterations)
        config.setPoolSize(poolSize)
        config.providerName = providerName
        config.setSaltGeneratorClassName(saltGeneratorClassName)
        config.setIvGeneratorClassName(ivGeneratorClassName)
        config.stringOutputType = stringOutputType
        encryptor.setConfig(config)
        return encryptor
    }
}
