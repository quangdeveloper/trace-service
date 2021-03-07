package com.vnpts.tracebility_v2.job;

import com.vnpts.tracebility_v2.model.EmailModel;
import com.vnpts.tracebility_v2.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
public class EmailJob implements Job {
  @Autowired
  private EmailService emailService;
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      List<EmailModel> list = emailService.getListEmailSending();
      if (list != null && list.size() > 0) {
        log.info("get email to sending: " + list.size());
        // send for one
        for (EmailModel emailModel : list) {
          emailService.sendEmail(emailModel);
        }
      }
  }
}
