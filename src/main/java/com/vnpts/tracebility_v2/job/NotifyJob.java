package com.vnpts.tracebility_v2.job;

import com.google.firebase.messaging.*;
import com.vnpts.tracebility_v2.dao.NotifyDAO;
import com.vnpts.tracebility_v2.util.TotalConverter;
import com.vnpts.tracebility_v2.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class NotifyJob implements Job {
  @Autowired
  private NotifyDAO notifyDAO;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    List<Map> list = getListNof();
    if (list != null && list.size() > 0) {
      log.info("get notify for send: " + list.size());
      for (Map map : list) {
        SendNotify sendNotify = new SendNotify(map);
        new Thread(sendNotify).start();
      }
    }
  }

  public synchronized List<Map> getListNof() {
    try {
      List<Map> rs = (List<Map>) notifyDAO.getListNotifSend().get("rs");
      if (rs != null && rs.size() > 0) {
        StringBuffer ids = new StringBuffer();
        StringBuffer dates = new StringBuffer();
        for (Map map : rs) {
          ids.append(Utils.getInt(map, "N_ID")).append(",");
          dates.append(TotalConverter.toString((Date) map.get("D_DAY"), "dd/MM/yyyy")).append(",");
        }
        notifyDAO.updateSending(ids.toString(), dates.toString());
      }
      return rs;
    } catch (CloneNotSupportedException | SQLException e) {
      return null;
    }
  }

  class SendNotify implements Runnable {
    Map notifData;

    public SendNotify(Map notifData) {
      this.notifData = notifData;
    }

    @Override
    public void run() {
        sendNotifyData((String) notifData.get("C_TOKEN_LIST_ANDROID"), notifData, false);
        sendNotifyData((String) notifData.get("C_TOKEN_LIST_IOS"), notifData, true);
    }

    void sendNotifyData(String tk, Map notifData, boolean isNotificationObject) {
      try {
        if (tk == null || tk.length() == 0) return;
        tk = tk.substring(0, tk.length() - 1);
        String[] tokenList = tk.split(",");
        if (tokenList.length == 0) return;
        MulticastMessage.Builder builder = MulticastMessage.builder()
            .putData("title", (String) notifData.get("S_TITLE"))
            .putData("body", (String) notifData.get("S_BODY"))
            .putData("data", (String) notifData.get("C_DATA"))
            .putData("type", "" + Utils.getInt(notifData, "N_TYPE_NOF"))
            .putData("id", "" + Utils.getInt(notifData, "N_ID"))
            .putData("day", TotalConverter.toString((Date) notifData.get("D_DAY"), "dd/MM/yyyy"))
            .addAllTokens(Arrays.asList(tokenList));
        if (isNotificationObject) {
          builder.setNotification(new Notification((String) notifData.get("S_TITLE"), (String) notifData.get("S_BODY")));
        }
        MulticastMessage message = builder.build();
        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        log.info("Success: " + response.getSuccessCount() + " --- Fail: " + response.getFailureCount());
        if (response.getFailureCount() > 0) {
          List<SendResponse> responses = response.getResponses();
          for (int i = 0; i < responses.size(); i++) {
            if (!responses.get(i).isSuccessful()) {
              log.error(tokenList[i] + " -- " + responses.get(i).getException().getMessage());
            }
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
  }
}


