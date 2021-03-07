package com.vnpts.tracebility_v2.config.quartz;

import com.vnpts.tracebility_v2.job.JobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Properties;

@Configuration
@Import(JobConfig.class)
@Slf4j
public class QuartzConfig {

  @Value("${org.quartz.scheduler.instanceName}")
  private String instanceName;

  @Value("${org.quartz.scheduler.instanceId}")
  private String instanceId;

  @Value("${org.quartz.threadPool.threadCount}")
  private String threadCount;

  @Value("${org.quartz.scheduler.instanceName2}")
  private String instanceName2;

  @Value("${org.quartz.scheduler.instanceId2}")
  private String instanceId2;

  @Value("${org.quartz.threadPool.threadCount2}")
  private String threadCount2;

  @Autowired
  @Qualifier("emailTriggerBean")
  private SimpleTriggerFactoryBean cronEmailTrigger;

  @Autowired
  @Qualifier("notifyTriggerBean")
  private SimpleTriggerFactoryBean notifyTriggerBean;

  @Bean
  public org.quartz.spi.JobFactory jobFactory(ApplicationContext applicationContext) {
    QuartzJobFactory JobFactory = new QuartzJobFactory();
    JobFactory.setApplicationContext(applicationContext);
    return JobFactory;
  }

  @Bean(name="schedule1")
  public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();

    factory.setOverwriteExistingJobs(true);
    factory.setJobFactory(jobFactory(applicationContext));

    Properties quartzProperties = new Properties();
    quartzProperties.setProperty("org.quartz.scheduler.instanceName", instanceName);
    quartzProperties.setProperty("org.quartz.scheduler.instanceId", instanceId);
    quartzProperties.setProperty("org.quartz.threadPool.threadCount", threadCount);

    factory.setQuartzProperties(quartzProperties);
    factory.setTriggers(cronEmailTrigger.getObject());
    log.info("starting jobs...");

    return factory;
  }

  @Bean(name="schedule2")
  public SchedulerFactoryBean schedulerFactoryBean2(ApplicationContext applicationContext) {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setOverwriteExistingJobs(true);
    factory.setJobFactory(jobFactory(applicationContext));
    Properties quartzProperties = new Properties();
    quartzProperties.setProperty("org.quartz.scheduler.instanceName", instanceName2);
    quartzProperties.setProperty("org.quartz.scheduler.instanceId", instanceId2);
    quartzProperties.setProperty("org.quartz.threadPool.threadCount", threadCount2);
    factory.setQuartzProperties(quartzProperties);
    factory.setTriggers(notifyTriggerBean.getObject());
    log.info("starting jobs 2...");

    return factory;
  }
}
