package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ProductDAO {
  Map createProduct(Map inParams) throws CloneNotSupportedException, SQLException;

  Map editProduct(Map inParams) throws CloneNotSupportedException, SQLException;

  Map cbbType(Map inParams) throws CloneNotSupportedException, SQLException;

  Map cbbOrigin(Map inParams) throws CloneNotSupportedException, SQLException;

  Map getList(Map inParams) throws CloneNotSupportedException, SQLException;

  Map getByID(Map inParams) throws CloneNotSupportedException, SQLException;

  Map getImg(long id, long productID) throws CloneNotSupportedException, SQLException;

  Map getProductProfileByPlan(Map inParams) throws CloneNotSupportedException, SQLException;

  Map getListByCompany(Map inParams) throws CloneNotSupportedException, SQLException;

  Map getProductCbb(Map inParams) throws CloneNotSupportedException, SQLException;
}
