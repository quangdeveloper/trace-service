package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.dao.EmailDAO;
import com.vnpts.tracebility_v2.model.EmailModel;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

  @Autowired
  private EmailDAO emailDAO;

  @Autowired
  public JavaMailSender mailSender;

  @Autowired
  Configuration templateMailConfig;


  @PostConstruct
  public void init() {
    System.setProperty("mail.mime.charset", "utf8");
    templateMailConfig.setClassForTemplateLoading(this.getClass(), "/template/email");
  }

  public synchronized List<EmailModel> getListEmailSending() {
    try {
      Map out = emailDAO.getListEmailSending();
      return (List<EmailModel>) out.get("rs");
    } catch (CloneNotSupportedException | SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void sendEmail(EmailModel model) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message,
          MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
          StandardCharsets.UTF_8.name());
      Template t = templateMailConfig.getTemplate(model.getTemplateName());
      String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model.getParams());
      helper.setFrom(model.getFrom());
      helper.setTo(InternetAddress.parse(model.getReceiver()));
      if (model.getCc() != null) helper.setCc(InternetAddress.parse(model.getCc()));
      if (model.getBcc() != null) helper.setBcc(InternetAddress.parse(model.getBcc()));
      helper.setText(html, true);
      helper.setSubject(model.getSubject());
      mailSender.send(message);
      emailDAO.updateSuccess(model.getId());
    } catch (IOException | TemplateException | MessagingException e) {
      e.printStackTrace();
      emailDAO.updateFail(model.getId());
    } catch (CloneNotSupportedException | SQLException e) {
      e.printStackTrace();
    }
  }
}
