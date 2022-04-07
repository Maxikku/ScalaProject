package com.example.ScalaProject.services.saas.impl

import cats.effect.IO
import com.example.ScalaProject.config.EmailConfig
import com.example.ScalaProject.services.saas.EmailService
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.{Attachments, Content, Email}
import com.sendgrid.{Method, Request, SendGrid}
import org.apache.commons.io.FilenameUtils


import java.io.{File, FileInputStream}

class EmailServiceImpl(emailConfig: EmailConfig) extends EmailService {

  override def sendEmail(title: String,
                         emailTo: String,
                         contentHtml: String,
                         files: List[File]): IO[Unit] = IO {
    val sendGrid = new SendGrid(emailConfig.sendGridApiKey)
    val mail = new Mail(
      new Email(emailConfig.emailFrom),
      title,
      new Email(emailTo),
      new Content("text/html", contentHtml)
    )
    files.foreach { file =>
      val attachment = new Attachments
      val fileData = org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file))
      attachment.setContent(new String(fileData, 0, file.length.asInstanceOf[Int], "UTF-8"))
      attachment.setType(s"application/${FilenameUtils.getExtension(file.getName)}")
      attachment.setFilename(file.getName)
      attachment.setDisposition("attachment")
      mail.addAttachments(attachment)
    }
    val request = new Request()
    request.setMethod(Method.POST)
    request.setEndpoint("mail/send")
    request.setBody(mail.build())
    sendGrid.api(request)
  }.flatMap { response =>
    response.getStatusCode match {
      case 200 | 202 => IO(println("Email sent successfully"))
      case code => IO.raiseError(new Throwable(s"Email was not sent,code: $code"))
    }
  }
}
