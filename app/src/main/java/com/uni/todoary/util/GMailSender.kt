package com.uni.todoary.util

import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences
import com.uni.todoary.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.mail.Authenticator
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class GMailSender(private val random_number : Int) : Authenticator(){

    // 보내는 사람 이메일과 비밀번호
    val fromEmail = BuildConfig.TODOARY_EMAIL
    val password = BuildConfig.TODOARY_EMAIL_PW


    // 보내는 사람 계정 확인
    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(fromEmail, password)
    }


    //난수발생
//    var random_number = (1000..9999).random()


    //난수 저장해놓기
//    fun init() {
//        var random_number = random_number.toString()
//        var editor = mSharedPreferences.edit()
//        editor.putString("key", random_number)
//        editor.apply()
//    }


    // 메일 보내기
    fun sendEmail(toEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val props = Properties()
            props.setProperty("mail.transport.protocol", "smtp")
            props.setProperty("mail.host", "smtp.gmail.com")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.socketFactory.fallback", "false")
            props.setProperty("mail.smtp.quitwait", "false")

            // 구글에서 지원하는 smtp 정보를 받아와 MimeMessage 객체에 전달
            val session = Session.getDefaultInstance(props, this@GMailSender)

            // 메시지 객체 만들기
            val message = MimeMessage(session)
            message.sender = InternetAddress(fromEmail)                                 // 보내는 사람 설정
            message.addRecipient(Message.RecipientType.TO, InternetAddress(toEmail))    // 받는 사람 설정
            message.subject = "Todoary 인증메일입니다."                                 // 이메일 제목
            message.setText("인증번호 [$random_number]을 입력해 주세요.")          // 이메일 내용


            // 전송
            Transport.send(message)
//            init()
        }
    }
}