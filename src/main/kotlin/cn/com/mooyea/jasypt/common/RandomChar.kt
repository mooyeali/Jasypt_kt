package cn.com.mooyea.jasypt.common

import java.util.*

class RandomChar {
    companion object {
        val random: Random by lazy { Random() }
        val randomChars = charArrayOf(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '@', '#',
            '$', '%', '^', '&', '*', '(', ')', '_', '-', '/', '.', '?'
        )
    }

}

fun main() {
    // 生成不超过16的整型随机数
    val random = RandomChar.random.nextInt(16)
    println(random)
}
