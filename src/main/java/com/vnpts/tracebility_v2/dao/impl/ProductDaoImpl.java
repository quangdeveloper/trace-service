package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ProductDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProductDaoImpl extends BaseDAO implements ProductDAO {
  @Autowired
  private CheckService checkService;

  @Override
  public Map createProduct(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_productName", "pi_info");
    inParams = Utils.changeClob(inParams, "pi_info");
    inParams.putIfAbsent("pi_productBarCode", "");
    if (!"".equals(inParams.get("pi_productBarCode"))) {
      if (!((String)inParams.get("pi_productBarCode")).matches("^\\d{13}$")) {
        return Utils.returnMap("331");
      }
      if (!checkBarcode((String)inParams.get("pi_productBarCode"))) {
        return Utils.returnMap("332");
      }
    }
    Map out = this.getClone().callProcedure(PK_PRODUCT + "create_product", inParams);
    if (out.get("PO_UPLOAD_KEY") != null) {
      checkService.setKeyUpload((String) out.get("PO_UPLOAD_KEY"));
    }
    return out;
  }

  @Override
  public Map editProduct(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_productName", "pi_info");
    inParams = Utils.changeClob(inParams, "pi_info");
    inParams = Utils.changeArr(inParams, true, true, "|", "pi_removeImg");
    inParams.putIfAbsent("pi_productBarCode", "");
    if (!"".equals(inParams.get("pi_productBarCode"))) {
      if (!((String)inParams.get("pi_productBarCode")).matches("^\\d{13}$")) {
        return Utils.returnMap("331");
      }
      if (!checkBarcode((String)inParams.get("pi_productBarCode"))) {
        return Utils.returnMap("332");
      }
    }
    Map out = this.getClone().callProcedure(PK_PRODUCT + "edit_product", inParams);
    if (out.get("PO_UPLOAD_KEY") != null) {
      checkService.setKeyUpload((String) out.get("PO_UPLOAD_KEY"));
    }
    return out;
  }

  @Override
  public Map cbbType(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCT + "cbb_type", inParams);
  }

  @Override
  public Map cbbOrigin(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCT + "cbb_origin", inParams);
  }

  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_keyword");
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    return this.getClone().callFunction(PK_PRODUCT + "get_list", inParams);
  }

  @Override
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCT + "get_by_id", inParams);
  }

  @Override
  public Map getImg(long id, long productID) throws CloneNotSupportedException, SQLException {
    Map inParams = new HashMap();
    inParams.put("pi_id", id);
    inParams.put("pi_product_id", productID);
    return this.getClone().callProcedure(PK_PRODUCT + "get_img_product", inParams);
  }


  @Override
  public Map getProductProfileByPlan(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_PRODUCT + "get_product_profile_by_plan", inParams);
  }

  @Override
  public Map getListByCompany(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCT + "get_list_by_company", inParams);
  }

  @Override
  public Map getProductCbb(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCT + "cbb_for_diary_list", inParams);
  }

  public static boolean checkBarcode(String barcode) {
    try {
      EAN13Bean bean = new EAN13Bean();
      BitmapCanvasProvider canvas = new BitmapCanvasProvider(
          null, "image/x-png", 600, BufferedImage.TYPE_INT_ARGB, false, 0);
      bean.generateBarcode(canvas, barcode);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
