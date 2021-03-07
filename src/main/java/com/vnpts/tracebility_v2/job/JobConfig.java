package com.vnpts.tracebility_v2.job;

import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class JobConfig {

  @Value("${job.startDelay}")
  private Long startDelay;

  @Value("${job.repeatInterval}")
  private Long repeatInterval;

  @Value("${job.description}")
  private String description;

  @Value("${job.key}")
  private String key;

  @Value("${job.startDelay2}")
  private Long startDelay2;

  @Value("${job.repeatInterval2}")
  private Long repeatInterval2;

  @Value("${job.description2}")
  private String description2;

  @Value("${job.key2}")
  private String key2;


  @Bean(name = "emailTriggerBean")
  public SimpleTriggerFactoryBean emailTriggerBean() {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setJobDetail(emailJobDetails().getObject());
    factoryBean.setStartDelay(startDelay);
    factoryBean.setRepeatInterval(repeatInterval);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

  @Bean(name = "notifyTriggerBean")
  public SimpleTriggerFactoryBean notifyTriggerBean() {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setJobDetail(notifyJob().getObject());
    factoryBean.setStartDelay(startDelay2);
    factoryBean.setRepeatInterval(repeatInterval2);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

  @Bean(name = "cronEmailTriggerBean")
  public CronTriggerFactoryBean cronEmailTriggerBean() {
    CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
    stFactory.setJobDetail(emailJobDetails().getObject());
    stFactory.setStartDelay(startDelay);
    stFactory.setCronExpression("* * * ? * *");
    return stFactory;
  }

  @Bean(name = "emailJobDetails")
  public JobDetailFactoryBean emailJobDetails() {
    JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(EmailJob.class);
    jobDetailFactoryBean.setDescription(description);
    jobDetailFactoryBean.setDurability(true);
    jobDetailFactoryBean.setName(key);

    return jobDetailFactoryBean;
  }

  @Bean(name = "notifyJobDetails")
  public JobDetailFactoryBean notifyJob() {
    JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(NotifyJob.class);
    jobDetailFactoryBean.setDescription(description2);
    jobDetailFactoryBean.setDurability(true);
    jobDetailFactoryBean.setName(key2);
    return jobDetailFactoryBean;
  }
}
